package com.atypon.recipes.model.info;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Nutrient extends CommonNutrient {
    public Nutrient(String type, double amount, String unit) {
        super(type, amount, unit);
    }
}
