package com.poly.TKShop.service;

import com.poly.TKShop.dto.CartDto;
import com.poly.TKShop.entity.cart.CartDetails;

import java.util.List;
import java.util.Map;

public interface CartDetailsService {

    CartDetails createNewCartDetails(String username, CartDto cartDto);

    Map<String, String> incrementCartDetails(Integer idCartDetails);
    void decrementCartDetails(Integer idCartDetails);

    List<CartDto> getListCartsByUser(String username);

    CartDetails deleteCartDetails(Integer idCartDetails);
}
