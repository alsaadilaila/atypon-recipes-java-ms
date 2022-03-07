package com.atypon.recipes.model;

import lombok.Data;

import java.util.List;

@Data
public class RecipeResults {
    private List<Recipe> results;
    private Integer number;
    private Integer totalResults;
}
