package com.atypon.recipes.filter;

import lombok.Builder;
import lombok.Data;

import static java.util.Objects.isNull;

@Data
@Builder
public class SearchRecipesFilters {
    private String titleMatch;
    private Boolean vegetarian;

    public String getQueryString() {
        String query = "";
        if (!isNull(titleMatch) && !titleMatch.isBlank()) {
            query = "titleMatch=" + titleMatch + "&";
        }
        if (!isNull(vegetarian)) {
            query += "vegetarian=" + vegetarian + "&";
        }
        if (query.length() > 0) {
            return query.substring(0, query.length() - 1);
        }
        return query;
    }
}
