package com.atypon.recipes.controller;

import com.atypon.recipes.model.RecipeResults;
import com.atypon.recipes.model.recipe.information.RecipeInformation;
import com.atypon.recipes.service.RecipeInformationService;
import com.atypon.recipes.service.SearchRecipesService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping()
@AllArgsConstructor
public class SearchController {
    private final SearchRecipesService searchRecipesService;
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

    @GetMapping(path = "/details/{recipeId}")
    public ResponseEntity<RecipeInformation> recipesInformation(Model model, @PathVariable String recipeId) {
        RecipeInformation recipeInformation = recipeInformationService.recipeInformationById(recipeId);
        model.addAttribute("q", recipeId);
        model.addAttribute("recipe", recipeInformation);
        return ResponseEntity.ok(recipeInformation);
    }
}