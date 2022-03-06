package com.atypon.recipes.service;

import com.atypon.recipes.model.Ingredients;
import com.atypon.recipes.model.Recipe;
import com.atypon.recipes.model.RecipeResults;
import com.atypon.recipes.model.recipe.information.IngredientInformation;
import com.atypon.recipes.model.recipe.information.NutrientIngredient;
import com.atypon.recipes.model.recipe.information.RecipeInformation;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static java.util.Objects.isNull;

@Service
@AllArgsConstructor
public class ExcludeIngredientService {

    private final SpoonacularServiceApiCalls apiCalls;

    public Double excludeIngredient(String recipe, Ingredients ingredientsToExclude) {
        Double totalCalories = 0.0;
        ResponseEntity<RecipeResults> searchedRecipe = apiCalls.searchRecipe(recipe);
        if (!isNull(searchedRecipe) && !isNull(searchedRecipe.getBody()) && !searchedRecipe.getBody().getResults().isEmpty()) {
            Recipe result = searchedRecipe.getBody().getResults().get(0);
            ResponseEntity<RecipeInformation> info = apiCalls.getRecipeInformation(result.getId());
            RecipeInformation body = info.getBody();
            if (!isNull(body) && !isNull(body.getNutrition()) && !isNull(body.getNutrition().getNutrients())) {
                Optional<NutrientIngredient> nutrient = body.getNutrition().getNutrients().stream().filter(n -> "Calories".equals(n.getName())).findFirst();
                if (nutrient.isPresent()) {
                    NutrientIngredient nutr = nutrient.get();
                    totalCalories = nutr.getAmount();
                    System.out.println("R:" + result.getTitle() + "-" + result.getId() + "-" + totalCalories);
                    for (String ingredient : ingredientsToExclude.getIngred()) {
                        Optional<NutrientIngredient> full = body.getNutrition().getIngredients().stream().filter(n -> n.getName().equals(ingredient)).findFirst();
                        if (full.isPresent()) {
                            ResponseEntity<IngredientInformation> ingredientInformation = apiCalls.getIngredientInformation(full.get().getId(), full.get().getAmount());
                            IngredientInformation ingredientInformationBody = ingredientInformation.getBody();
                            if (!isNull(ingredientInformationBody)) {
                                Optional<NutrientIngredient> optionalNutrientIngredient = ingredientInformationBody.getNutrition().getNutrients().stream().filter(n -> "Calories".equals(n.getName())).findFirst();
                                if (optionalNutrientIngredient.isPresent()) {
                                    Double amount = optionalNutrientIngredient.get().getAmount();
                                    System.out.println("INGRED:" + optionalNutrientIngredient.get().getName() + "-ID:" + optionalNutrientIngredient.get().getId() + "-C:" + optionalNutrientIngredient.get().getAmount());
                                    totalCalories -= amount;
                                }
                            }
                        }
                    }
                }
            }
        }
        return totalCalories;
    }
}
