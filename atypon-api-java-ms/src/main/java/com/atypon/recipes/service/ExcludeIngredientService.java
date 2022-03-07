package com.atypon.recipes.service;

import com.atypon.recipes.exception.IngredientNotFoundException;
import com.atypon.recipes.exception.RecipeNotFoundException;
import com.atypon.recipes.filter.SearchRecipesFilters;
import com.atypon.recipes.model.Recipe;
import com.atypon.recipes.model.RecipeInformation;
import com.atypon.recipes.model.RecipeResults;
import com.atypon.recipes.model.info.IngredientInformation;
import com.atypon.recipes.model.info.Ingredients;
import com.atypon.recipes.model.info.Nutrient;
import com.atypon.recipes.model.info.NutrientIngredient;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.function.Predicate;

import static java.util.Objects.isNull;

@Service
@AllArgsConstructor
@NoArgsConstructor
public class ExcludeIngredientService {

    public static final Predicate<Nutrient> nutrientPredicate = n -> "Calories".equals(n.getName());

    @Autowired
    private SpoonacularServiceApiCalls apiCalls;

    private RecipeInformation getRecipe(String title) {
        SearchRecipesFilters filters = SearchRecipesFilters.builder().titleMatch(title).build();
        ResponseEntity<RecipeResults> searchedRecipe = apiCalls.searchRecipe("", filters, 1);
        if (!isNull(searchedRecipe) && !isNull(searchedRecipe.getBody()) && !searchedRecipe.getBody().getResults().isEmpty()) {
            Recipe result = searchedRecipe.getBody().getResults().get(0);
            ResponseEntity<RecipeInformation> info = apiCalls.getRecipeInformationById(result.getId());
            return info.getBody();
        } else {
            throw new RecipeNotFoundException();
        }
    }

    public Double excludeIngredient(String title, Ingredients ingredientsToExclude) {
        Double recipeTotalCalories = 0.0;
        RecipeInformation recipeInformation = getRecipe(title);
        Optional<Nutrient> nutrientOptional = recipeInformation.getNutrition().getNutrients().stream().filter(nutrientPredicate).findFirst();
        if (nutrientOptional.isPresent()) {
            recipeTotalCalories = recipeInformation.getTotalCalories();
            for (String ingredient : ingredientsToExclude.getIngredients()) {
                Double amount = excludeIngredient(recipeInformation, ingredient);
                recipeTotalCalories -= amount;
            }
        }
        return recipeTotalCalories;
    }

    private Double excludeIngredient(RecipeInformation recipeInformation, String ingredient) {
        Predicate<NutrientIngredient> nutrientIngredientPredicate = n -> n.getName().equals(ingredient);
        Optional<NutrientIngredient> optional = recipeInformation.getNutrition().getIngredients().stream().filter(nutrientIngredientPredicate).findFirst();
        if (optional.isPresent()) {
            NutrientIngredient nutrientIngredient = optional.get();
            ResponseEntity<IngredientInformation> ingredientInformation = apiCalls.getIngredientInformation(nutrientIngredient.getId(), nutrientIngredient.getAmount());
            return getExcludedAmount(ingredientInformation);
        } else {
            throw new IngredientNotFoundException("Ingredient to exclude not found in the current recipe");
        }
    }

    private Double getExcludedAmount(ResponseEntity<IngredientInformation> ingredientInformation) {
        return ingredientInformation.getBody().getTotalCalories();
    }
}
