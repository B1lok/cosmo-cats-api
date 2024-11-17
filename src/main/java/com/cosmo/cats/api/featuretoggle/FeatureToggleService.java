package com.cosmo.cats.api.featuretoggle;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class FeatureToggleService {
    @Value("${feature.cosmo-cats.enabled}")
    private boolean isCosmoCatsEnabled;
    @Value("${feature.kittyProducts.enabled}")
    private boolean isKittyProductsEnabled;

    public boolean checkCosmoCats(){
        return isCosmoCatsEnabled;
    }
    public boolean checkKittyProducts(){
        return isKittyProductsEnabled;
    }
}
