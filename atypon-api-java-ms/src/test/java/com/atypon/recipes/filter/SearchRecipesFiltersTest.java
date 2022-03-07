package com.atypon.recipes.filter;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SearchRecipesFiltersTest {

    private SearchRecipesFilters searchRecipesFilters;

    @BeforeEach
    void setUp() {
    }

    @Test
    public void return_query_string_When_search_recipe() {
        searchRecipesFilters = new SearchRecipesFilters("Pasta", null);
        assertEquals("titleMatch=Pasta", searchRecipesFilters.getQueryString());
    }

    @Test
    public void return_query_string_more_filters_When_search_recipe() {
        searchRecipesFilters = new SearchRecipesFilters("Pasta", true);
        assertEquals("titleMatch=Pasta&vegetarian=true", searchRecipesFilters.getQueryString());
    }

}