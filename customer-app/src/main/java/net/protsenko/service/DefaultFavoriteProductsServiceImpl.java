package net.protsenko.service;

import lombok.RequiredArgsConstructor;
import net.protsenko.entity.FavoriteProduct;
import net.protsenko.repository.FavoriteProductRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DefaultFavoriteProductsServiceImpl implements FavoriteProductsService {

    private final FavoriteProductRepository favoriteProductRepository;

    @Override
    public Mono<FavoriteProduct> addProductToFavorites(int productId) {
        return favoriteProductRepository.save(new FavoriteProduct(UUID.randomUUID(), productId));
    }

    @Override
    public Mono<Void> removeProductFromFavorites(int productId) {
        return this.favoriteProductRepository.deleteByProductId(productId);
    }

    @Override
    public Mono<FavoriteProduct> findFavoriteProductByProduct(int productId) {
        return this.favoriteProductRepository.findByProductId(productId);
    }

}
