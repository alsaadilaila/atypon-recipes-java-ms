package com.atypon.recipes.service;

import com.atypon.recipes.model.recipe.information.RecipeInformation;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class RecipeInformationService {

    private final SpoonacularServiceApiCalls apiCalls;

    public RecipeInformation recipeInformationById(String id) {
        ResponseEntity<RecipeInformation> recipeInformation = apiCalls.getRecipeInformation(id);
        return recipeInformation.getBody();
    }
}
