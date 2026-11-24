package com.myprojects.productcatalogservice.controllers;

import com.myprojects.productcatalogservice.dtos.CategoryDto;
import com.myprojects.productcatalogservice.dtos.ProductDto;
import com.myprojects.productcatalogservice.models.Category;
import com.myprojects.productcatalogservice.models.Product;
import com.myprojects.productcatalogservice.services.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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

    @PutMapping("/{id}")
    public ResponseEntity<ProductDto> editProduct(@PathVariable("id") Long productId, @RequestBody ProductDto productDto){
        Product product = from(productDto);
        Product editedProduct = productService.editProduct(productId, product);

        ProductDto editedProductDto = from(editedProduct);
        return ResponseEntity.ok(editedProductDto);
    }

    public ProductDto from(Product product){
        ProductDto productDto = new ProductDto();
        productDto.setId(product.getId());
        productDto.setName(product.getName());
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

    public Product from(ProductDto productDto){
        Product product = new Product();
        product.setId(productDto.getId());
        product.setImageUrl(productDto.getImageUrl());
        product.setDescription(productDto.getDescription());
        product.setName(productDto.getName());
        product.setPrice(productDto.getPrice());
        Category category = new Category();
        if(productDto.getCategoryDto() != null) {
            category.setName(productDto.getCategoryDto().getName());
            category.setDescription(productDto.getCategoryDto().getDescription());
            category.setId(productDto.getCategoryDto().getId());
        }
        product.setCategory(category);
        return product;
    }
}
