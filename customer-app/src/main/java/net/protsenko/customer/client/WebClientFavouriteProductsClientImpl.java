package net.protsenko.customer.client;

import lombok.RequiredArgsConstructor;
import net.protsenko.customer.client.exception.ClientBadRequestException;
import net.protsenko.customer.client.payload.NewFavouriteProductPayload;
import net.protsenko.customer.entity.FavouriteProduct;
import org.springframework.http.ProblemDetail;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@RequiredArgsConstructor
public class WebClientFavouriteProductsClientImpl implements FavouriteProductsClient {

    private final WebClient webClient;

    @Override
    public Flux<FavouriteProduct> findFavouriteProducts() {
        return this.webClient
                .get()
                .uri("/feedback-api/favourite-products")
                .retrieve()
                .bodyToFlux(FavouriteProduct.class);
    }

    @Override
    public Mono<FavouriteProduct> findFavouriteProductByProductId(int productId) {
        return this.webClient
                .get()
                .uri("/feedback-api/favourite-products/by-product-id/{productId}", productId)
                .retrieve()
                .bodyToMono(FavouriteProduct.class)
                .onErrorComplete(WebClientResponseException.NotFound.class);
    }

    @Override
    public Mono<FavouriteProduct> addProductToFavourites(int productId) {
        return this.webClient
                .post()
                .uri("/feedback-api/favourite-products")
                .bodyValue(new NewFavouriteProductPayload(productId))
                .retrieve()
                .bodyToMono(FavouriteProduct.class)
                .onErrorMap(WebClientResponseException.BadRequest.class,
                        exception -> new ClientBadRequestException(exception,
                                ((List<String>) exception.getResponseBodyAs(ProblemDetail.class)
                                        .getProperties().get("errors"))));
    }

    @Override
    public Mono<Void> removeProductFromFavourites(int productId) {
        return this.webClient
                .delete()
                .uri("/feedback-api/favourite-products/by-product-id/{productId}", productId)
                .retrieve()
                .toBodilessEntity()
                .then();
    }
}
