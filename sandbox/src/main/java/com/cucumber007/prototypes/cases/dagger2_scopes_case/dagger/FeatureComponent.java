package com.cucumber007.prototypes.cases.dagger2_scopes_case.dagger;

import dagger.Component;

@Component(dependencies = AppComponent.class, modules = {FeatureModule.class})
@FeatureScope
public interface FeatureComponent {
}
