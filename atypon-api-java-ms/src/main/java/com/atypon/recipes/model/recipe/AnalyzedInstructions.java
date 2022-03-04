package com.atypon.recipes.model.recipe;

import lombok.Data;

import java.util.List;

@Data
public class AnalyzedInstructions {

    private String name;
    private List<Step> steps;
}
