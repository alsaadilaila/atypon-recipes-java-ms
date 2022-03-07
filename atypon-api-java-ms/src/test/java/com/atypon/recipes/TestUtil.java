package com.atypon.recipes;

import com.atypon.recipes.model.info.Nutrient;
import com.atypon.recipes.model.info.NutrientIngredient;
import com.atypon.recipes.model.info.Nutrition;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public final class TestUtil {

    @NotNull
    public static Nutrition getNutrition(ArrayList<Nutrient> nutrients, ArrayList<NutrientIngredient> ingredients) {
        return new Nutrition(nutrients, ingredients);
    }

    @NotNull
    public static Nutrient getNutrient(String type, String unit, double amount) {
        return new Nutrient(type, amount, unit);
    }

    @NotNull
    public static NutrientIngredient getNutrientIngredient(String id, String type, String unit, double amount) {
        return new NutrientIngredient(id, type, amount, unit);
    }

}
