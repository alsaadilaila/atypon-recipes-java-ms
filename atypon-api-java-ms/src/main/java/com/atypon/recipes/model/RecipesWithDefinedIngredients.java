package com.atypon.recipes.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
public class RecipesWithDefinedIngredients {

    private Long id;
    private String title;
    private String image;
    private String imageType;
    private Integer usedIngredientCount;
    private Integer missedIngredientCount;
    private List<RecipeIngredients> missedIngredients;
    private List<RecipeIngredients> usedIngredients;
    private List<RecipeIngredients> unusedIngredients;
    private Integer likes;

}
