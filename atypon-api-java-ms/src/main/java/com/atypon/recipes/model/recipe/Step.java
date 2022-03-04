package com.atypon.recipes.model.recipe;

import lombok.Data;

import java.util.List;

@Data
public class Step {

    private Integer number;
    private String step;
    private List<IngredientsForRandomRecipe> ingredients;
    private List<Equipment> equipment;
    private StepLength length;

}