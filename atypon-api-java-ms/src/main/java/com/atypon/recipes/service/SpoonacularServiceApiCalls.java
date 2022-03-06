package com.atypon.recipes.service;

import com.atypon.recipes.model.RecipeResults;
import com.atypon.recipes.model.recipe.information.IngredientInformation;
import com.atypon.recipes.model.recipe.information.RecipeInformation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Service
@AllArgsConstructor
public class SpoonacularServiceApiCalls {
    private final static String SEARCH_URL = "https://api.spoonacular.com/recipes/search";
    private final static String RECIPE_INFORMATION_URL = "https://api.spoonacular.com/recipes";
    private final static String RECIPE_INGREDIENT_URL = "https://api.spoonacular.com/food/ingredients/";

    private final RestTemplate restTemplate;
    private final RecipeUrlCreator recipeUrlCreator;

    private String recipeSearch(String recipe) {
        return "?query=" + recipe + "&number=1&includeNutrition=true";
    }

    public ResponseEntity<RecipeResults> searchRecipe(String recipe) {
        String urlWithKey = recipeUrlCreator.createURLWithKey(SEARCH_URL + recipeSearch(recipe));
        return restTemplate.exchange(urlWithKey, HttpMethod.GET, null, RecipeResults.class);
    }

    public ResponseEntity<RecipeInformation> getRecipeInformation(String id) {
        String urlWithKey = recipeUrlCreator.createURLWithKey(RECIPE_INFORMATION_URL + informationUrl(id) + "includeNutrition=true");
        return restTemplate.exchange(urlWithKey, HttpMethod.GET, null, RecipeInformation.class);
    }

    public ResponseEntity<IngredientInformation> getIngredientInformation(String id, Double amount) {
        System.out.println("id=" + id);
        String urlWithKey = recipeUrlCreator.createURLWithKey(RECIPE_INGREDIENT_URL + informationUrl(id) + "amount=" + amount);
        return restTemplate.exchange(urlWithKey, HttpMethod.GET, null, IngredientInformation.class);
    }

    private String informationUrl(String id) {
        return "/" + id + "/information?";
    }
}