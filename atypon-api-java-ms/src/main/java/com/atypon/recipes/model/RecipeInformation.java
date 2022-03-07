package com.atypon.recipes.model;

import com.atypon.recipes.exception.IngredientNotFoundException;
import com.atypon.recipes.model.info.CommonInformation;
import com.atypon.recipes.model.info.Nutrient;
import com.atypon.recipes.model.info.Nutrition;
import com.atypon.recipes.service.ExcludeIngredientService;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.annotation.PostConstruct;
import java.util.Optional;

@Data
@NoArgsConstructor
public class RecipeInformation extends CommonInformation {

    public RecipeInformation(String id, String name, Nutrition nutrition) {
        super(id, name, nutrition);
        super.setTotalCalories(getTotalCalories());
    }

    @JsonProperty("title")
    @Override
    public String getName() {
        return super.getName();
    }

    @PostConstruct
    public Double getTotalCalories() {
        Optional<Nutrient> nutrient = super.getNutrition().getNutrients()
                .stream()
                .filter(ExcludeIngredientService.nutrientPredicate)
                .findFirst();
        if (nutrient.isPresent()) {
            return nutrient.get().getAmount();
        } else {
            throw new IngredientNotFoundException("Recipe's calories info not found");
        }
    }
}
