package com.poly.TKShop.repository;

import com.poly.TKShop.entity.Role;
import com.poly.TKShop.entity.product.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {

}
