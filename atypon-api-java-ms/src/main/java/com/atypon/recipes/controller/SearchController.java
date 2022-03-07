package com.atypon.recipes.controller;

import com.atypon.recipes.filter.SearchRecipesFilters;
import com.atypon.recipes.model.RecipeInformation;
import com.atypon.recipes.model.RecipeResults;
import com.atypon.recipes.service.RecipeInformationService;
import com.atypon.recipes.service.SearchRecipesService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.constraints.NotBlank;

@Validated
@Controller
@RequestMapping()
@AllArgsConstructor
public class SearchController {
    private final SearchRecipesService searchRecipesService;
    private final RecipeInformationService recipeInformationService;

    @GetMapping(path = "/recipes/search/")
    public ResponseEntity<RecipeResults> searchRecipes(
            Model model,
            @RequestParam(name = "q", required = false) String q,
            @RequestParam(name = "title", required = false) String title,
            @RequestParam(name = "vegetarian", required = false) Boolean vegetarian,
            @RequestParam(name = "number", required = false) Integer number) {
        SearchRecipesFilters filters = SearchRecipesFilters.builder().titleMatch(title).vegetarian(vegetarian).build();
        RecipeResults searchResult = searchRecipesService.searchByRecipe(q, filters, number);
        model.addAttribute("q", title);
        model.addAttribute("recipes", searchResult);
        return ResponseEntity.ok(searchResult);
    }

    @GetMapping(path = "/recipe")
    public ResponseEntity<RecipeInformation> recipeInformation(
            Model model, @NotBlank @RequestParam() String title) {
        RecipeInformation recipeInformation = recipeInformationService.getRecipeInformation(title);
        model.addAttribute("title", title);
        model.addAttribute("info", recipeInformation);
        return ResponseEntity.ok(recipeInformation);
    }

    @GetMapping(path = "/{id}/recipe/")
    public ResponseEntity<RecipeInformation> recipeInformationById(
            Model model, @NotBlank @PathVariable String id) {
        RecipeInformation recipeInformation = recipeInformationService.getRecipeInformationById(id);
        model.addAttribute("id", id);
        model.addAttribute("info", recipeInformation);
        return ResponseEntity.ok(recipeInformation);
    }
}