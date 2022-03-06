package com.atypon.recipes.model.recipe.information;

import lombok.Data;

@Data
public class Nutrient {
    private String name;
    private Double amount;
    private String unit;
    private Nutrition nutrition;
}
