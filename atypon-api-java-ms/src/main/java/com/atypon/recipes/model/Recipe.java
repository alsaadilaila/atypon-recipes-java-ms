package com.atypon.recipes.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
public class Recipe {

    private Long id;
    private String title;
    private Integer readyInMinutes;
    private Integer servings;
    private String image;
    private List<String> imageUrls;


}
