package com.cucumber007.prototypes.activities.mvc;

import java.util.Observer;

public interface IDatabaseModel {
    void saveString(String value);
    void asyncSaveString(String value, IDatabaseCallback callback);
    void addStringObserver(Observer observer);

    public interface IDatabaseCallback{
        void onFinish();
    }

}
