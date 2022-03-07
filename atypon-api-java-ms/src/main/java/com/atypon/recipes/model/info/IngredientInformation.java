package com.atypon.recipes.model.info;

import com.atypon.recipes.exception.IngredientNotFoundException;
import com.atypon.recipes.service.ExcludeIngredientService;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Optional;

@Data
@NoArgsConstructor
public class IngredientInformation extends CommonInformation {

    public IngredientInformation(String id, String name, Nutrition nutrition) {
        super(id, name, nutrition);
        super.setTotalCalories(getTotalCalories());
    }

    public Double getTotalCalories() {
        Optional<Nutrient> nutrient = super.getNutrition().getNutrients()
                .stream()
                .filter(ExcludeIngredientService.nutrientPredicate)
                .findFirst();
        if (nutrient.isPresent()) {
            return nutrient.get().getAmount();
        } else {
            throw new IngredientNotFoundException("Ingredient's calories info not found");
        }
    }
}
