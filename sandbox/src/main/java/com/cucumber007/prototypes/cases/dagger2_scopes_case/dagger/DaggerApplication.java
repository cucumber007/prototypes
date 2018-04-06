package com.cucumber007.prototypes.cases.dagger2_scopes_case.dagger;

import android.annotation.SuppressLint;

import com.cucumber007.prototypes.menu.ContextApplication;

@SuppressLint("Registered")
public class DaggerApplication extends ContextApplication {

    private AppComponent appComponent;
    private FeatureComponent featureComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        //todo inject context here
        appComponent = DaggerAppComponent.builder()
                .appModule(new AppModule())
                .build();
    }

    public AppComponent getAppComponent() {
        return appComponent;
    }

    //todo inject
    public FeatureComponent getFeatureComponent() {
        if (featureComponent == null) {
            //featureComponent = appComponent.addFeatureComponent(new FeatureModule());
        }
        return featureComponent;
    }

    public void destroyFeatureComponent() {
        featureComponent = null;
    }

}
