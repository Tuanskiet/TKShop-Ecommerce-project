package com.poly.TKShop.service;

import com.poly.TKShop.entity.product.Product;
import com.poly.TKShop.entity.product.ProductImage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductService {
    Product createNewProduct(Product product);

    List<Product> getAllProduct();

    Page<Product> getProductsWithSortAndPagination(Pageable pageable);

    Page<Product> getProductsByCategory(String category,Pageable pageable);

    Page<Product> getProductsByProductType(String productType, Pageable pageable);

    Product deleteProduct(Integer id);

    Product updateProduct(Integer id, Product newProduct);

    Product uploadProductImage(Integer id, ProductImage productImage);
}
