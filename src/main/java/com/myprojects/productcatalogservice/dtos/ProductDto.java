package com.myprojects.productcatalogservice.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductDto {
    private Long id;
    private String description;
    private CategoryDto categoryDto;
    private Double price;
    private String imageUrl;
}
