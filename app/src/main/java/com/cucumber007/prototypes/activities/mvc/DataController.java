package com.cucumber007.prototypes.activities.mvc;

import android.content.Context;

import com.cucumber007.prototypes.MyApplication;

import java.util.Observer;

public class DataController {

    private static DataController instance = new DataController();

    private Context context;
    private IDatabaseModel database;

    private DataController() {
        context = MyApplication.getContext();
        database = MyApplication.getDatabaseModel();
    }


    public static DataController getInstance() {
        return instance;
    }

    public void saveData(String data) {
        database.saveString(data);
    }

    public void saveDataAsync(String data, final IDatabaseModel.IDatabaseCallback callback) {
        database.asyncSaveString(data, new IDatabaseModel.IDatabaseCallback() {
            @Override
            public void onFinish() {
                callback.onFinish();
            }
        });
    }

    public void setStringDataObserver(Observer observer) {
        database.addStringObserver(observer);
    }
}
