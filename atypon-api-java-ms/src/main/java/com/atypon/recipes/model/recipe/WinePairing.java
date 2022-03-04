package com.atypon.recipes.model.recipe;

import lombok.Data;

import java.util.List;

@Data
public class WinePairing {

    public List<String> pairedWines;
    public String pairingText;
    public List<ProductMatch> productMatches;
}
