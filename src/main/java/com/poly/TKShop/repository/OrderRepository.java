package com.poly.TKShop.repository;

import com.poly.TKShop.entity.cart.CartDetails;
import com.poly.TKShop.entity.order.OrderDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderDetailsRepository extends JpaRepository<OrderDetails, Integer> {
}
