package com.myprojects.productcatalogservice.controllers;

import com.myprojects.productcatalogservice.models.Product;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    @GetMapping
    public List<Product> getAllProducts(){
        Product product = new Product();
        product.setId(1L);
        product.setName("iPhone 18");
        List<Product> products = new ArrayList<>();
        products.add(product);
        return products;
    }

    @GetMapping("/{id}")
    public Product getProductById(@PathVariable("id") Long productId){
        Product product= new Product();
        product.setId(productId);
        return product;
    }

}
