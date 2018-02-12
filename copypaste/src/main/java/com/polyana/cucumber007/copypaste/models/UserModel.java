package com.polyana.cucumber007.copypaste.models;


import android.preference.PreferenceManager;

import com.cucumber007.reusables.models.objects.User;
import com.polyana.cucumber007.copypaste.ContextApplication;
import com.polyana.cucumber007.copypaste.network.RequestManager;
import com.cucumber007.reusables.objects.LoginParams;
import com.google.gson.Gson;

import java.util.List;

import rx.Observable;

public class UserModel {
    private static UserModel instance = new UserModel();

    public static UserModel getInstance() {
        return instance;
    }

    private User user;
    public static final String KEY_USER = "user";

    private UserModel() {
    }

    public Observable<User> getLoginObservable(LoginParams loginParams) {
        return RequestManager.getService().login(loginParams)
                .compose(RequestManager.applySchedulers()).map(response -> {
                    List<String> cookies = response.headers().toMultimap().get("set-cookie");
                    for (String value : cookies) {
                        if(value.startsWith("sessionid")) {
                            RequestManager.setCookie(value.split(";")[0]);
                            setUser(response.body());
                            return response.body();
                        }
                    }
                    return response.body();
                });
    }

    public void saveUserToSharedPreferences() {
        String json = new Gson().toJson(user);
        PreferenceManager.getDefaultSharedPreferences(ContextApplication.getContext()).edit().putString(KEY_USER, json).apply();
    }

    public void setUser(User user) {
        this.user = user;
        saveUserToSharedPreferences();
    }

    public User getUser() {
        if (user == null) {
            String json = PreferenceManager.getDefaultSharedPreferences(ContextApplication.getContext()).getString(KEY_USER, null);
            user = new Gson().fromJson(json, User.class);
        }
        return user;
    }

    public boolean isLoggedIn() {
        return RequestManager.isLoggedIn();
    }

    public void logout() {
        PreferenceManager.getDefaultSharedPreferences(ContextApplication.getContext()).edit().remove(KEY_USER).apply();
        //todo
        //FacebookModel.getInstance(ContextApplication.getContext()).logout();
        RequestManager.logout();
    }
}
