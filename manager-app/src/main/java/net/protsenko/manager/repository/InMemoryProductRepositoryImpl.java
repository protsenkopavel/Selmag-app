package net.protsenko.manager.repository;

import net.protsenko.manager.entity.Product;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.IntStream;

@Repository
public class InMemoryProductRepositoryImpl implements ProductRepository {

    private final List<Product> products = Collections.synchronizedList(new LinkedList<>());

    public InMemoryProductRepositoryImpl() {
        IntStream.range(1, 4)
                .forEach(i -> this.products.add(
                        new Product(i, "Товар № " + i, "Описание товара № " + i)
                ));
    }

    @Override
    public List<Product> findAll() {
        return Collections.unmodifiableList(this.products);
    }
}
