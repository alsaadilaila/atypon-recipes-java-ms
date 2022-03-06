package com.atypon.recipes.controller;

import com.atypon.recipes.model.ExcludeResponse;
import com.atypon.recipes.model.Ingredients;
import com.atypon.recipes.service.ExcludeIngredientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping()
public class ExcludeController {

    @Autowired
    private ExcludeIngredientService excludeIngredientService;

    @GetMapping("/exclude")
    public ExcludeResponse exclude(String recipeTitle, Ingredients ingredients) {
        return new ExcludeResponse(excludeIngredientService.excludeIngredient(recipeTitle, ingredients));
    }
}