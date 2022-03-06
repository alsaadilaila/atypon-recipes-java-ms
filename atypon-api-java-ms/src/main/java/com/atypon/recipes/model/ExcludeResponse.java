package com.atypon.recipes.model;

public class ExcludeResponse {

    private final Double totalCalories;

    public ExcludeResponse(Double totalCalories) {
        this.totalCalories = totalCalories;
    }

    public Double getTotalCalorie() {
        return totalCalories;
    }
}