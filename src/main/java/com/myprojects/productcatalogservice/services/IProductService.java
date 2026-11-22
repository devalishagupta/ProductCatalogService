package com.myprojects.productcatalogservice.services;

import com.myprojects.productcatalogservice.models.Product;

import java.util.List;

public interface IProductService {
    public List<Product> getAllProducts();
    public Product getProductById(Long productId);
}
