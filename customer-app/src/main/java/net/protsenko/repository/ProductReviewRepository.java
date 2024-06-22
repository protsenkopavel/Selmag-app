package net.protsenko.repository;

import net.protsenko.entity.ProductReview;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ProductReviewRepository {

    Mono<ProductReview> save(ProductReview productReview);

    Flux<ProductReview> findAllByProductId(int productId);
}
