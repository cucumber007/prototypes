package com.cucumber007.prototypes.cases.dagger2_scopes_case.dagger;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Scope;

@Scope
@Retention(RetentionPolicy.RUNTIME)
public @interface FeatureScope {
}
