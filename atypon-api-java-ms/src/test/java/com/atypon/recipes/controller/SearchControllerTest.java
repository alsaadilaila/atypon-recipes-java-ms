package com.atypon.recipes.controller;

import com.atypon.recipes.exception.RecipeNotFoundException;
import com.atypon.recipes.filter.SearchRecipesFilters;
import com.atypon.recipes.model.Recipe;
import com.atypon.recipes.model.RecipeInformation;
import com.atypon.recipes.model.RecipeResults;
import com.atypon.recipes.model.info.Nutrient;
import com.atypon.recipes.model.info.NutrientIngredient;
import com.atypon.recipes.model.info.Nutrition;
import com.atypon.recipes.service.RecipeInformationService;
import com.atypon.recipes.service.SearchRecipesService;
import com.atypon.recipes.service.SpoonacularServiceApiCalls;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static com.atypon.recipes.TestUtil.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class SearchControllerTest {

    private SearchRecipesService searchRecipesService;
    private RecipeInformationService recipeInformationService;

    @BeforeEach
    public void setUp() {
        SpoonacularServiceApiCalls mockApiCalls = mock(SpoonacularServiceApiCalls.class);
        searchRecipesService = new SearchRecipesService(mockApiCalls);
        recipeInformationService = new RecipeInformationService(mockApiCalls);

        ArrayList<Nutrient> nutrients = new ArrayList<>(List.of(getNutrient("Calories", "kcal", 10.0)));
        ArrayList<NutrientIngredient> recipeIngredients = new ArrayList<>(List.of(getNutrientIngredient("1", "egg", "g", 1.0), getNutrientIngredient("2", "flour", "g", 1.0)));
        Nutrition nutritionRecipe = getNutrition(nutrients, recipeIngredients);
        ResponseEntity<RecipeInformation> pastaInfo = new ResponseEntity<>(new RecipeInformation("1", "Pasta", nutritionRecipe), HttpStatus.OK);
        when(mockApiCalls.getRecipeInformationById("1")).thenReturn(pastaInfo);
        when(mockApiCalls.getRecipeInformationByName("Pasta")).thenReturn(pastaInfo);

        ResponseEntity<RecipeResults> pastaRecipe = new ResponseEntity<>(getRecipeResults(), HttpStatus.OK);
        SearchRecipesFilters filters = SearchRecipesFilters.builder().titleMatch("Pasta").build();
        when(mockApiCalls.searchRecipe("", filters, 1)).thenReturn(pastaRecipe);

        ResponseEntity<RecipeResults> pastaRecipePlus = new ResponseEntity<>(getRecipeResults(), HttpStatus.OK);
        SearchRecipesFilters filtersPlus = SearchRecipesFilters.builder().titleMatch("PastaPlus").vegetarian(true).build();
        when(mockApiCalls.searchRecipe("", filtersPlus, 1)).thenReturn(pastaRecipePlus);

    }

    @Test
    public void return_recipe_info_When_search_for_existing_recipe() {
        SearchRecipesFilters filters = SearchRecipesFilters.builder().titleMatch("Pasta").build();
        RecipeResults recipe = searchRecipesService.searchByRecipe("", filters, 1);
        assertEquals("1", recipe.getResults().get(0).getId());
    }

    @Test
    public void return_recipe_info_When_search_for_existing_recipe_with_filter() {
        SearchRecipesFilters filters = SearchRecipesFilters.builder().titleMatch("PastaPlus").vegetarian(true).build();
        RecipeResults recipe = searchRecipesService.searchByRecipe("", filters, 1);
        assertEquals("1", recipe.getResults().get(0).getId());
    }

    @Test
    public void throw_recipe_not_found_exception_When_search_for_non_existing_recipe() {
        SearchRecipesFilters filters = SearchRecipesFilters.builder().titleMatch("Pastaaa").build();
        assertThrows(RecipeNotFoundException.class, () -> {
            searchRecipesService.searchByRecipe("", filters, 1);
        });
    }

    @Test
    public void return_recipe_info_When_get_for_existing_recipe() {
        RecipeInformation recipe = recipeInformationService.getRecipeInformation("Pasta");
        assertEquals("1", recipe.getId());
        assertEquals("Pasta", recipe.getName());
        assertEquals(getExpectedNutrition(), recipe.getNutrition());
    }

    @Test
    public void throw_recipe_not_found_recipe_When_get_for_non_existing_recipe() {
        assertThrows(RecipeNotFoundException.class, () -> {
            recipeInformationService.getRecipeInformation("xx");
        });
    }

    @NotNull
    private Nutrition getExpectedNutrition() {
        ArrayList<Nutrient> nutrients = new ArrayList<>(List.of(getNutrient("Calories", "kcal", 10.0)));
        ArrayList<NutrientIngredient> recipeIngredients = new ArrayList<>(List.of(getNutrientIngredient("1", "egg", "g", 1.0), getNutrientIngredient("2", "flour", "g", 1.0)));
        return new Nutrition(nutrients, recipeIngredients);
    }

    @NotNull
    private RecipeResults getRecipeResults() {
        RecipeResults recipeResults = new RecipeResults();
        ArrayList<Recipe> results = new ArrayList<>();
        results.add(new Recipe("1", "Pasta", true));
        recipeResults.setResults(results);
        return recipeResults;
    }
}