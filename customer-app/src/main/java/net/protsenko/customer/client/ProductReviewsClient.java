package net.protsenko.customer.client;

import lombok.RequiredArgsConstructor;
import net.protsenko.customer.entity.ProductReview;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ProductReviewsClient {

    Flux<ProductReview> findProductReviewsByProductId(Integer productId);

    Mono<ProductReview> createProductReview(Integer productId, Integer rating, String review);

}
