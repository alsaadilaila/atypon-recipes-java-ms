package com.atypon.recipes.model.info;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class NutrientIngredient extends CommonNutrient {
    private String id;

    public NutrientIngredient(String id, String name, Double amount, String unit) {
        super(name, amount, unit);
        this.id = id;
    }
}
