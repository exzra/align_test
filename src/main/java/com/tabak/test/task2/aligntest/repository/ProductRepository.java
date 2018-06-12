package com.tabak.test.task2.aligntest.repository;

import com.tabak.test.task2.aligntest.model.Product;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends CrudRepository<Product, Long> {

    List<Product> findByName(String name);

    List<Product> findByBrand(String brand);

    List<Product> findByNameAndBrand(String name, String brand);

    List<Product> findAllByQuantityIsLessThan(Long value);
}
