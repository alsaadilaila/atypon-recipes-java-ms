package com.atypon.recipes.model.info;

import lombok.Data;

import java.util.List;

@Data
public class Ingredient {
    private Long id;
    private String name;
    private List<Nutrient> nutrition;
}
