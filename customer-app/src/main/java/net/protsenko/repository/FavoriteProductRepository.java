package net.protsenko.repository;

import net.protsenko.entity.FavoriteProduct;
import reactor.core.publisher.Mono;

public interface FavoriteProductRepository {

    Mono<FavoriteProduct> save(FavoriteProduct favoriteProduct);

    Mono<Void> deleteByProductId(int productId);

    Mono<FavoriteProduct> findByProductId(int productId);
}
