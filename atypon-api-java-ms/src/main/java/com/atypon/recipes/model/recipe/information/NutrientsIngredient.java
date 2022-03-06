package com.atypon.recipes.model.recipe.information;

import lombok.Data;

import java.util.List;

@Data
public class NutrientsIngredient {
    private List<NutrientIngredient> nutrients;
    private List<NutrientIngredient> ingredients;
}
