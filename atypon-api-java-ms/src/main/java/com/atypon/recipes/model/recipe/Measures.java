package com.atypon.recipes.model.recipe;

import com.atypon.recipes.model.recipe.information.Metric;
import com.atypon.recipes.model.recipe.information.Us;
import lombok.Data;

@Data
public class Measures {

    private Us us;
    private Metric metric;

}
