package com.atypon.recipes.model.recipe;

import lombok.Data;

import java.util.List;

@Data
public class RecipeRandomResponse {

    private List<RecipeRandom> recipes;
}
