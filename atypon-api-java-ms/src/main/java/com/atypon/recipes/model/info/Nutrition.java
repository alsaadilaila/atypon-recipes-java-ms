package com.atypon.recipes.model.info;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Nutrition {
    private List<Nutrient> nutrients;
    private List<NutrientIngredient> ingredients;

    @Override
    public boolean equals(Object o) {
        Nutrition nutrition = (Nutrition) o;
        if (this.getNutrients().size() == nutrition.getNutrients().size()) {
            List<Nutrient> nutrientList = this.getNutrients();
            for (int i = 0, thisSize = nutrientList.size(); i < thisSize; i++) {
                Nutrient thisN = nutrientList.get(i);
                Nutrient otherN = nutrition.getNutrients().get(i);
                if (!thisN.getName().equals(otherN.getName()) || !thisN.getAmount().equals(otherN.getAmount()) || !thisN.getUnit().equals(otherN.getUnit())) {
                    return false;
                }
            }
        }
        if (this.getIngredients().size() == nutrition.getIngredients().size()) {
            List<NutrientIngredient> nutrientList = this.getIngredients();
            for (int i = 0, thisSize = nutrientList.size(); i < thisSize; i++) {
                NutrientIngredient thisN = nutrientList.get(i);
                NutrientIngredient otherN = nutrition.getIngredients().get(i);
                if (!thisN.getId().equals(otherN.getId()) || !thisN.getName().equals(otherN.getName()) || !thisN.getAmount().equals(otherN.getAmount()) || !thisN.getUnit().equals(otherN.getUnit())) {
                    return false;
                }
            }
        }
        return true;
    }

}
