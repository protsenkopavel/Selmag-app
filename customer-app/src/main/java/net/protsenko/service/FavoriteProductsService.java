package net.protsenko.service;

import net.protsenko.entity.FavoriteProduct;
import reactor.core.publisher.Mono;

public interface FavoriteProductsService {

    Mono<FavoriteProduct> addProductToFavorites(int productId);

    Mono<Void> removeProductFromFavorites(int productId);

    Mono<FavoriteProduct> findFavoriteProductByProduct(int productId);

}
