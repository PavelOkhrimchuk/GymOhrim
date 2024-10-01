package com.gymohrim.repository;

import com.gymohrim.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Integer> {
    Product findByBarcode(String barcode);
}

