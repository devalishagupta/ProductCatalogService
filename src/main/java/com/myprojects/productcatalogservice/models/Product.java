package com.myprojects.productcatalogservice.models;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Product extends BaseModel{
    private String description;
    private Category category;
    private Double price;
    private String imageUrl;
    private Integer quantity;
}
