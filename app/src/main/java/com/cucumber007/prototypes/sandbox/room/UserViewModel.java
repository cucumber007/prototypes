package com.cucumber007.prototypes.sandbox.room;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.cucumber007.prototypes.menu.PrototypesApplication;
import com.cucumber007.prototypes.sandbox.room.User;
import com.cucumber007.prototypes.sandbox.room.UserDao;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


public class UserViewModel extends ViewModel {

    private UserDao userDao;
    private LiveData<User> userLiveData;

    public UserViewModel(int userId) {
        userDao = PrototypesApplication.getDatabase().userDao();
        /*userLiveData = Observable.just(userDao.get(userId))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).blockingFirst();*/
        userLiveData = userDao.get(userId);
    }

    public LiveData<User> getUserLiveData() {
        return userLiveData;
    }

}
