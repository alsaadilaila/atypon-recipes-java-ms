package com.atypon.recipes.model.info;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommonNutrient {
    private String name;
    private Double amount;
    private String unit;
}
