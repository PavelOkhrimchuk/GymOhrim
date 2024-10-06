package com.gymohrim.repository;

import com.gymohrim.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Integer> {
    Product findByBarcode(String barcode);

    @Query(value = "SELECT * FROM product p WHERE SIMILARITY(p.name, :query) > 0.3 ORDER BY SIMILARITY(p.name, :query) DESC", nativeQuery = true)
    List<Product> findByFullTextSearch(@Param("query") String query);
}

