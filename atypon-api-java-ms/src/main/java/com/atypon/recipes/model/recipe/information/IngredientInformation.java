package com.atypon.recipes.model.recipe.information;

import lombok.Data;

@Data
public class IngredientInformation {
    private Long id;
    private String name;
    private NutrientsIngredient nutrition;
}
