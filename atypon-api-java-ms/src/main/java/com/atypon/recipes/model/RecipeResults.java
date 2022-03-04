package com.atypon.recipes.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
public class RecipeResults {

    private List<Recipe> results;
    private String baseUri;
    private Integer offset;
    private Integer number;
    private Integer totalResults;
    private Integer processingTimeMs;
    private Long expires;
    private Boolean isStale;

}
