package com.atypon.recipes.model.recipe.information;

import lombok.Data;

@Data
public class NutrientIngredient {

    private String name;
    private Double amount;
    private String unit;
}
