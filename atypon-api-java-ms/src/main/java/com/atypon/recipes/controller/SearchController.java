package com.atypon.recipes.controller;

import com.atypon.recipes.model.Ingredients;
import com.atypon.recipes.model.RecipeResults;
import com.atypon.recipes.model.RecipesWithDefinedIngredients;
import com.atypon.recipes.model.recipe.information.RecipeInformation;
import com.atypon.recipes.service.RecipeInformationService;
import com.atypon.recipes.service.SearchRecipesByIngredientsService;
import com.atypon.recipes.service.SearchRecipesService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping(path = "/recipes/")
@AllArgsConstructor
public class SearchController {
    private final SearchRecipesService searchRecipesService;
    private final SearchRecipesByIngredientsService searchRecipesByIngredientsService;
    private final RecipeInformationService recipeInformationService;

    @GetMapping(path = "/search/")
    public ResponseEntity<RecipeResults> searchRecipes(Model model, @RequestParam(name = "q", required = false) String phrase) {
        RecipeResults searchResult = new RecipeResults();
        if (phrase != null && !phrase.isEmpty()) {
            searchResult = searchRecipesService.searchByRecipe(phrase);
            model.addAttribute("q", phrase);
            model.addAttribute("recipes", searchResult);
        }
        return ResponseEntity.ok(searchResult);
    }

    @GetMapping(path = "/findByIngredients/")
    public ResponseEntity<List<RecipesWithDefinedIngredients>> findRecipeByIngredients(Model model, @RequestParam(name = "q", required = false) String phrase) {
        List<RecipesWithDefinedIngredients> searchResult = new ArrayList<>();
        if (phrase != null && !phrase.isEmpty()) {
            Ingredients ingredients = new Ingredients(phrase);
            searchResult = searchRecipesByIngredientsService.searchRecipe(ingredients);
            model.addAttribute("q", phrase);
            model.addAttribute("searchResults", searchResult);
            model.addAttribute("total", searchResult.size());
        }
        return ResponseEntity.ok(searchResult);
    }

    @GetMapping(path = "/details/{recipeId}")
    public ResponseEntity<RecipeInformation> recipesInformation(Model model, @PathVariable Long recipeId) {
        RecipeInformation recipeInformation = recipeInformationService.recipeInformationById(recipeId);
        model.addAttribute("q", recipeId);
        model.addAttribute("recipe", recipeInformation);
        return ResponseEntity.ok(recipeInformation);
    }
}