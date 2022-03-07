package com.atypon.recipes.model.info;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CommonInformation {
    private String id;
    private String name;
    private Nutrition nutrition;
    private Double totalCalories;

    public CommonInformation(String id, String name, Nutrition nutrition) {
        this.id = id;
        this.name = name;
        this.nutrition = nutrition;
    }
}
