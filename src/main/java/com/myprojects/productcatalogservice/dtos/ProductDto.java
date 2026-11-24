package com.myprojects.productcatalogservice.dtos;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ProductDto {
    private Long id;
    private String name;
    private String description;
    private CategoryDto categoryDto;
    private Double price;
    private String imageUrl;
}
