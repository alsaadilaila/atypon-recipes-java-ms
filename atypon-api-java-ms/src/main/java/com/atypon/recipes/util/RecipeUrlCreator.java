package com.atypon.recipes.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class RecipeUrlCreator {

    @Value("${atypon.recipes.http.auth-token-header-name}")
    private String headerName;

    @Value("${atypon.recipes.http.auth-token}")
    private String key;

    public String createURLWithKey(String url) {
        return url + "&" + headerName + "=" + key;
    }
}
