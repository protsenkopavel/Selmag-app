package net.protsenko.feedback.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import net.protsenko.feedback.controller.payload.NewFavouriteProductPayload;
import net.protsenko.feedback.entity.FavouriteProduct;
import net.protsenko.feedback.service.FavouriteProductsService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("feedback-api/favourite-products")
@RequiredArgsConstructor
public class FavouriteProductsRestController {

    private final FavouriteProductsService favouriteProductsService;

    @GetMapping
    public Flux<FavouriteProduct> findFavouriteProducts(Mono<JwtAuthenticationToken> jwtAuthenticationTokenMono) {
        return jwtAuthenticationTokenMono
                .flatMapMany(token -> this.favouriteProductsService.findFavouriteProducts(token.getToken().getSubject()));
    }

    @GetMapping("by-product-id/{productId:\\d+}")
    public Mono<FavouriteProduct> findFavouriteProductByProductId(@PathVariable("productId") int productId,
                                                                  Mono<JwtAuthenticationToken> jwtAuthenticationTokenMono) {
        return jwtAuthenticationTokenMono
                .flatMap(token -> this.favouriteProductsService.findFavouriteProductByProduct(productId, token.getToken().getSubject()));
    }

    @PostMapping
    public Mono<ResponseEntity<FavouriteProduct>> addProductToFavourites(
            Mono<JwtAuthenticationToken> jwtAuthenticationTokenMono,
            @Valid @RequestBody Mono<NewFavouriteProductPayload> payloadMono,
            UriComponentsBuilder uriComponentsBuilder
    ) {
        return Mono.zip(jwtAuthenticationTokenMono, payloadMono)
                .flatMap(tuple -> this.favouriteProductsService
                        .addProductToFavourites(tuple.getT2().productId(), tuple.getT1().getToken().getSubject()))
                .map(favouriteProduct -> ResponseEntity
                        .created(uriComponentsBuilder.replacePath("feedback-api/favourite-products")
                                .build(favouriteProduct.getId()))
                        .body(favouriteProduct));

    }

    @DeleteMapping("by-product-id/{productId:\\d+}")
    public Mono<ResponseEntity<Void>> removeProductFromFavourites(Mono<JwtAuthenticationToken> jwtAuthenticationTokenMono,
                                                                  @PathVariable("productId") int productId) {
        return jwtAuthenticationTokenMono
                .flatMap(token -> this.favouriteProductsService
                        .removeProductFromFavourites(productId, token.getToken().getSubject()))
                .then(Mono.just(ResponseEntity.noContent().build()));
    }

}
