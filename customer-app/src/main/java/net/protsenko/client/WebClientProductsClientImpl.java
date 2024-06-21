package net.protsenko.client;

import lombok.RequiredArgsConstructor;
import net.protsenko.entity.Product;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class WebClientProductsClientImpl implements ProductsClient {

    private final WebClient webClient;

    @Override
    public Flux<Product> findAllProducts(String filter) {
        return this.webClient.get()
                .uri("/catalogue-api/products?filter={filter}", filter)
                .retrieve()
                .bodyToFlux(Product.class);
    }

    @Override
    public Mono<Product> findProduct(int id) {
        return this.webClient.get()
                .uri("/catalogue-api/products/{productId}", id)
                .retrieve()
                .bodyToMono(Product.class);
    }

}
