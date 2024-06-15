package net.protsenko.manager.service;

import lombok.RequiredArgsConstructor;
import net.protsenko.manager.entity.Product;
import net.protsenko.manager.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DefaultProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Override
    public List<Product> findAllProducts() {
        return this.productRepository.findAll();
    }
}
