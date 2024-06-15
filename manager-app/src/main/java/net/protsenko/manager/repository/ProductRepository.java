package net.protsenko.manager.repository;

import net.protsenko.manager.entity.Product;

import java.util.List;

public interface ProductRepository {

    List<Product> findAll();

}
