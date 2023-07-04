package com.poly.TKShop.repository;

import com.poly.TKShop.entity.cart.CartDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartDetailsRepository extends JpaRepository<CartDetails, Integer> {
}
