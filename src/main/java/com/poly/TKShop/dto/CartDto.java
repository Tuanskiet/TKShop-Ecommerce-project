package com.poly.TKShop.dto.request;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class CartDetailRequest {
    private Integer productId;
    private String name;
    private BigDecimal price;
    private Integer quantity;
    private String size;
    private String color;
    private String imgUrl;
}
