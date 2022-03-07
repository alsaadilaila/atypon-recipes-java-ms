package com.atypon.recipes.exception;

public class IngredientNotFoundException extends RuntimeException {

    public IngredientNotFoundException(String errorMessage, Throwable err) {
        super(errorMessage, err);
    }

    public IngredientNotFoundException(String errorMessage) {
        super(errorMessage);
    }

    public IngredientNotFoundException() {
        super();
    }

}
