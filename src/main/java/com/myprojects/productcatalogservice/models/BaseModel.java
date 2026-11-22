package com.myprojects.productcatalogservice.models;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class BaseModel {
    private Long id;
    private String name;
    private State state; // For soft delete purpose
    private Date createdAt;
    private Date updatedAt;
}
