package com.myprojects.productcatalogservice.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FakeStoreProductDto {
    private Long id;
    private String description;
    private String title;
    private String category;
    private String image;
    private Double price;
}
