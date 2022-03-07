package com.atypon.recipes.service;

import com.atypon.recipes.exception.IngredientNotFoundException;
import com.atypon.recipes.filter.SearchRecipesFilters;
import com.atypon.recipes.model.Recipe;
import com.atypon.recipes.model.RecipeInformation;
import com.atypon.recipes.model.RecipeResults;
import com.atypon.recipes.model.info.*;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static com.atypon.recipes.TestUtil.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ExcludeIngredientServiceTest {

    private SpoonacularServiceApiCalls mockApiCalls;
    private ExcludeIngredientService excludeIngredientService;

    @BeforeEach
    public void setUp() {
        mockApiCalls = mock(SpoonacularServiceApiCalls.class);
        excludeIngredientService = new ExcludeIngredientService(mockApiCalls);
        ResponseEntity<RecipeResults> pastaRecipe = new ResponseEntity<>(getRecipeResults(), HttpStatus.OK);
        SearchRecipesFilters filters = SearchRecipesFilters.builder().titleMatch("Pasta").build();
        when(mockApiCalls.searchRecipe("", filters, 1)).thenReturn(pastaRecipe);

        ArrayList<Nutrient> nutrients = new ArrayList<>(List.of(getNutrient("Calories", "kcal", 10.0)));
        ArrayList<NutrientIngredient> recipeIngredients = new ArrayList<>(List.of(getNutrientIngredient("1", "egg", "g", 1.0), getNutrientIngredient("2", "flour", "g", 1.0)));
        Nutrition nutritionRecipe = getNutrition(nutrients, recipeIngredients);

        ArrayList<Nutrient> nutrientsEgg = new ArrayList<>(List.of(getNutrient("Calories", "kcal", 1.0)));
        ArrayList<NutrientIngredient> ingredientsEgg = new ArrayList<>(List.of(getNutrientIngredient("1", "egg", "g", 1.0)));
        Nutrition nutritionEgg = getNutrition(nutrientsEgg, ingredientsEgg);

        ArrayList<Nutrient> nutrientsFlour = new ArrayList<>(List.of(getNutrient("Calories", "kcal", 1.0)));
        ArrayList<NutrientIngredient> ingredientsFlour = new ArrayList<>(List.of(getNutrientIngredient("2", "flour", "g", 1.0)));
        Nutrition nutritionFlour = getNutrition(nutrientsFlour, ingredientsFlour);

        ResponseEntity<RecipeInformation> pastaInfo = new ResponseEntity<>(new RecipeInformation("1", "Pasta", nutritionRecipe), HttpStatus.OK);
        when(mockApiCalls.getRecipeInformationById("1")).thenReturn(pastaInfo);
        ResponseEntity<IngredientInformation> eggIngredient = new ResponseEntity<>(new IngredientInformation("1", "egg", nutritionEgg), HttpStatus.OK);
        ResponseEntity<IngredientInformation> flourIngredient = new ResponseEntity<>(new IngredientInformation("2", "flour", nutritionFlour), HttpStatus.OK);
        when(mockApiCalls.getIngredientInformation("1", 1.0)).thenReturn(eggIngredient);
        when(mockApiCalls.getIngredientInformation("2", 1.0)).thenReturn(flourIngredient);
    }

    @Test
    public void return_calories_When_exclude_ingredient() {
        Double calories = excludeIngredientService.excludeIngredient("Pasta", getIngredientsToExclude());
        Assertions.assertEquals(9.0, calories);
    }

    @Test
    public void throw_ingredient_not_found_exception_When_exclude_ingredient_not_within_recipe() {
        assertThrows(IngredientNotFoundException.class, () -> {
            excludeIngredientService.excludeIngredient("Pasta", getNonIngredientsToExclude());
        });
    }

    @NotNull
    private Ingredients getIngredientsToExclude() {
        Ingredients ingredients = new Ingredients();
        ingredients.getIngredients().add("flour");
        return ingredients;
    }

    @NotNull
    private Ingredients getNonIngredientsToExclude() {
        Ingredients ingredients = new Ingredients();
        ingredients.getIngredients().add("flourr");
        return ingredients;
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