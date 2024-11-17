package com.cosmo.cats.api.featuretoggle;

import com.cosmo.cats.api.domain.Wearer;
import com.cosmo.cats.api.service.exception.FeatureIsDisabledException;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
@RequiredArgsConstructor
public class FeatureToggleAspect {
    private final FeatureToggleService featureToggleService;

    @Before("execution(* com.cosmo.cats.api.service.ProductService.getProductsByWearer(..)) && args(wearer)")
    public void handleWearerFeature(Wearer wearer) {
        switch (wearer) {
            case CATS -> {
                if (!featureToggleService.checkCosmoCats()) {
                    throw new FeatureIsDisabledException("Feature for 'CATS' is disabled");
                }
            }
            case KITTIES -> {
                if (!featureToggleService.checkKittyProducts()) {
                    throw new FeatureIsDisabledException("Feature for 'KITTIES' is disabled");
                }
            }
            default -> {
            }
        }
    }
}
