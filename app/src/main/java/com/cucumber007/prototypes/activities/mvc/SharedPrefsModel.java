package com.cucumber007.prototypes.activities.mvc;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;

import java.util.Observable;
import java.util.Observer;

public class SharedPrefsModel implements IDatabaseModel {

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private StringObservable stringObservable;

    public SharedPrefsModel(Context context) {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        editor = sharedPreferences.edit();
        stringObservable = new StringObservable();
    }

    public String loadString() {
        return sharedPreferences.getString("data", null);
    }


    @Override
    public void saveString(String value) {
        editor.putString("data", value);
        editor.apply();
        stringObservable.setChanged();
        stringObservable.notifyObservers();
    }


    @Override
    public void asyncSaveString(final String value, final IDatabaseCallback callback) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                try {
                    Thread.sleep(2000);
                    editor.putString("data", value);
                    editor.apply();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return null;
            }


            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                stringObservable.setChanged();
                stringObservable.notifyObservers();
                callback.onFinish();
            }
        }.execute();
    }


    @Override
    public void addStringObserver(Observer observer) {
        stringObservable.addObserver(observer);
    }

    public class StringObservable extends Observable {

        public String getData() {
            return loadString();
        }

        @Override
        public void setChanged() {
            super.setChanged();
        }
    }
}
