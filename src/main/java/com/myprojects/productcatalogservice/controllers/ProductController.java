package com.myprojects.productcatalogservice.controllers;

import com.myprojects.productcatalogservice.dtos.CategoryDto;
import com.myprojects.productcatalogservice.dtos.ProductDto;
import com.myprojects.productcatalogservice.models.Product;
import com.myprojects.productcatalogservice.services.IProductService;
import com.myprojects.productcatalogservice.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {
    @Autowired
    IProductService productService;

    @GetMapping
    public ResponseEntity<List<ProductDto>> getAllProducts(){
        List<Product> products = productService.getAllProducts();
        if(products == null){
            return ResponseEntity
                    .status(HttpStatus.NO_CONTENT)
                    .body(null);
        }
        List<ProductDto> productDtos = new ArrayList<>();
        for(Product product: products){
            ProductDto productDto = from(product);
            productDtos.add(productDto);
        }
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(productDtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDto> getProductById(@PathVariable("id") Long productId){
        Product product = productService.getProductById(productId);
        if(product == null){
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(null);

        }
        ProductDto productDto = from(product);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(productDto);
    }

    public ProductDto from(Product product){
        ProductDto productDto = new ProductDto();
        productDto.setId(product.getId());
        productDto.setDescription(product.getDescription());
        productDto.setImageUrl(product.getImageUrl());
        productDto.setPrice(product.getPrice());
        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setId(product.getCategory().getId());
        categoryDto.setName(product.getCategory().getName());
        categoryDto.setDescription(product.getCategory().getDescription());
        productDto.setCategoryDto(categoryDto);
        return productDto;
    }

}
