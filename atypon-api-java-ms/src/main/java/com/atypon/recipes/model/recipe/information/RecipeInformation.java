package com.atypon.recipes.model.recipe.information;

import com.atypon.recipes.model.recipe.ExtendedIngredient;
import lombok.Data;

import java.util.List;

@Data
public class RecipeInformation {
    private List<ExtendedIngredient> extendedIngredients;
    private Long id;
    private String title;
    private NutrientsIngredient nutrition;

}
