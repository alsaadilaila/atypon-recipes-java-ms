package com.atypon.recipes.service;

import com.atypon.recipes.exception.RecipeNotFoundException;
import com.atypon.recipes.filter.SearchRecipesFilters;
import com.atypon.recipes.model.RecipeInformation;
import com.atypon.recipes.model.RecipeResults;
import com.atypon.recipes.model.info.IngredientInformation;
import com.atypon.recipes.util.RecipeUrlCreator;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import static java.util.Objects.isNull;

@Service
@AllArgsConstructor
@NoArgsConstructor
public class SpoonacularServiceApiCalls {
    private final static String SEARCH_URL = "https://api.spoonacular.com/recipes/complexSearch";
    private final static String RECIPE_INFORMATION_URL = "https://api.spoonacular.com/recipes";
    private final static String RECIPE_INGREDIENT_URL = "https://api.spoonacular.com/food/ingredients/";

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private RecipeUrlCreator recipeUrlCreator;

    private String recipeSearch(String q, SearchRecipesFilters filters, Integer number) {
        String fullQuery;
        if (!isNull(q) && !q.isBlank()) {
            fullQuery = "?query=" + q + "&" + filters.getQueryString();
        } else {
            fullQuery = "?" + filters.getQueryString();
        }
        if (!isNull(number)) {
            fullQuery += "&number=" + number;
        }
        return fullQuery;
    }

    public ResponseEntity<RecipeResults> searchRecipe(String q, SearchRecipesFilters filters, Integer number) {
        String urlWithKey = recipeUrlCreator.createURLWithKey(SEARCH_URL + recipeSearch(q, filters, number));
        return restTemplate.exchange(urlWithKey, HttpMethod.GET, null, RecipeResults.class);
    }

    public ResponseEntity<RecipeInformation> getRecipeInformationById(String id) {
        String urlWithKey = recipeUrlCreator.createURLWithKey(RECIPE_INFORMATION_URL + informationUrl(id) + "includeNutrition=true");
        return restTemplate.exchange(urlWithKey, HttpMethod.GET, null, RecipeInformation.class);
    }

    public ResponseEntity<RecipeInformation> getRecipeInformationByName(String name) {
        ResponseEntity<RecipeResults> response = searchRecipe("", SearchRecipesFilters.builder().titleMatch(name).build(), 1);
        if (!isNull(response.getBody()) && !isNull(response.getBody().getResults())) {
            String id = response.getBody().getResults().get(0).getId();
            return getRecipeInformationById(id);
        } else {
            throw new RecipeNotFoundException();
        }
    }

    public ResponseEntity<IngredientInformation> getIngredientInformation(String id, Double amount) {
        String urlWithKey = recipeUrlCreator.createURLWithKey(RECIPE_INGREDIENT_URL + informationUrl(id) + "amount=" + amount);
        return restTemplate.exchange(urlWithKey, HttpMethod.GET, null, IngredientInformation.class);
    }

    private String informationUrl(String id) {
        return "/" + id + "/information?";
    }
}