package com.cucumber007.prototypes.cases.dagger2_scopes_case.dagger;

import com.cucumber007.prototypes.cases.dagger2_scopes_case.objects.FeatureObject;

import dagger.Module;
import dagger.Provides;

@Module
public class FeatureModule {

    @Provides
    @FeatureScope
    FeatureObject provideFeatureObject() {
        return new FeatureObject();
    };
}
