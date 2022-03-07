package com.atypon.recipes.model.info;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Ingredients {

    private List<String> ingredients = new ArrayList<>();

    public Ingredients(String input) {
        String[] ingredientsArray = input.split(",");
        for (String s : ingredientsArray) {
            ingredients.add(s.trim().toLowerCase().replaceAll(" ", ""));
        }
    }

    public String compose() {
        return String.join(",+", ingredients);
    }
}

