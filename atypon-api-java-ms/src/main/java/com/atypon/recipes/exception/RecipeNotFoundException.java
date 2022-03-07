package com.atypon.recipes.exception;

public class RecipeNotFoundException extends RuntimeException {

    public RecipeNotFoundException(String errorMessage, Throwable err) {
        super(errorMessage, err);
    }

    public RecipeNotFoundException() {
        super();
    }

}
