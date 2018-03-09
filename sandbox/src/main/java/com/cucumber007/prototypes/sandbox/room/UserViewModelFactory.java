package com.cucumber007.prototypes.sandbox.room;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

public class UserViewModelFactory extends ViewModelProvider.NewInstanceFactory {
    private int userId;


    public UserViewModelFactory(int userId) {
        this.userId = userId;
    }

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        return (T) new UserViewModel(userId);
    }
}
