package com.atypon.recipes.service;

import com.atypon.recipes.exception.RecipeNotFoundException;
import com.atypon.recipes.filter.SearchRecipesFilters;
import com.atypon.recipes.model.RecipeResults;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import static java.util.Objects.isNull;

@Service
@AllArgsConstructor
@NoArgsConstructor
public class SearchRecipesService {

    @Autowired
    private SpoonacularServiceApiCalls apiCalls;

    public RecipeResults searchByRecipe(String q, SearchRecipesFilters filters, Integer number) {
        ResponseEntity<RecipeResults> searchRecipeResults = apiCalls.searchRecipe(q, filters, number);
        if (isNull(searchRecipeResults)) {
            throw new RecipeNotFoundException();
        } else {
            return searchRecipeResults.getBody();
        }
    }
}
