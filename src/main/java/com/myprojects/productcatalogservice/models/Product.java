package com.myprojects.productcatalogservice.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Product extends BaseModel{
    private String description;
    private Category category;
    private Double price;
    private String imageUrl;
    private Integer quantity;
}
