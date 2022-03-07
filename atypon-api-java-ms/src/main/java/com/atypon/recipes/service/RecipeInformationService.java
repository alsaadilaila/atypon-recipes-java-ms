package com.atypon.recipes.service;

import com.atypon.recipes.exception.RecipeNotFoundException;
import com.atypon.recipes.model.RecipeInformation;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import static java.util.Objects.isNull;

@Service
@AllArgsConstructor
@NoArgsConstructor
public class RecipeInformationService {

    @Autowired
    private SpoonacularServiceApiCalls apiCalls;

    public RecipeInformation getRecipeInformation(String title) {
        ResponseEntity<RecipeInformation> recipeInformation = apiCalls.getRecipeInformationByName(title);
        if (isNull(recipeInformation)) {
            throw new RecipeNotFoundException();
        }
        return recipeInformation.getBody();
    }

    public RecipeInformation getRecipeInformationById(String id) {
        ResponseEntity<RecipeInformation> recipeInformation = apiCalls.getRecipeInformationById(id);
        if (isNull(recipeInformation)) {
            throw new RecipeNotFoundException();
        }
        return recipeInformation.getBody();
    }
}
