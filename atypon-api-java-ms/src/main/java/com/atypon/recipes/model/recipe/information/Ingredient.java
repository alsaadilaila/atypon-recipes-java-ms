package com.atypon.recipes.model.recipe.information;

import lombok.Data;

import java.util.List;

@Data
public class Ingredient {
    private Long id;
    private String name;
    private List<NutrientIngredient> nutrition;
}
