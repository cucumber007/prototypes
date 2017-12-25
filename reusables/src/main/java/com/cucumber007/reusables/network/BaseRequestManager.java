package com.cucumber007.reusables.network;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;


import com.cucumber007.reusables.logging.HttpLogUtil;
import com.cucumber007.reusables.logging.LogUtil;

import java.net.ConnectException;
import java.net.NoRouteToHostException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public abstract class BaseRequestManager<T> {

    private boolean testServer = false;

    private Retrofit retrofit;
    private T service;
    private static String cookie;

    public static final String KEY_COOKIE = "cookie";

    public BaseRequestManager(Class<T> serviceClass) {
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.followRedirects(true);
        httpClient.followSslRedirects(true);
        httpClient.interceptors().add(HttpLogUtil.getHttpInterceptor());

        httpClient.interceptors().add((chain) -> {
            Request original = chain.request();

            if (cookie == null) cookie = "";
            Request.Builder requestBuilder = original.newBuilder()
                    .header("Cookie", cookie)
                    .method(original.method(), original.body());
            Request request = requestBuilder.build();
            return chain.proceed(request);
        });

        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(getServerUrl())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create());
        addCallAdapters(builder);
        retrofit = builder.client(httpClient.build()).build();
        service = retrofit.create(serviceClass);

        loadAndKeepCookie();
    }

    public T getService() {
        return service;
    }

    protected abstract String getServerUrl();
    protected abstract Context getContext();

    protected void addCallAdapters(Retrofit.Builder builder) {
        builder.addConverterFactory(GsonConverterFactory.create());
    }

    ///////////////////////////////////////////////////////////////////////////
    // Error Handling
    ///////////////////////////////////////////////////////////////////////////

    public static  <T> Observable.Transformer<T, T> applySchedulers() {
        return observable -> observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public void handleError(Throwable t) {
        if (isNetworkError(t)) {
            LogUtil.makeToast("No internet connection");
            LogUtil.logDebug("No internet connection");
            return;
        }
        LogUtil.makeToast("Unknown error");
        LogUtil.logDebug(t.getClass()+" "+t.getMessage());
    }

    public static boolean isNetworkError(Throwable t) {
        return t instanceof SocketTimeoutException
                ||
                t instanceof UnknownHostException
                ||
                t instanceof ConnectException
                ||
                t instanceof NoRouteToHostException
                ||
                (t instanceof RuntimeException && t.getMessage().contains("Looper.prepare()"));
    }

    ///////////////////////////////////////////////////////////////////////////
    // Misc
    ///////////////////////////////////////////////////////////////////////////

    public void setCookie(String cookie) {
        if(!BaseRequestManager.cookie.equals(cookie)) {
            BaseRequestManager.cookie = cookie;
            SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(getContext()).edit();
            editor.putString(KEY_COOKIE, cookie);
            editor.apply();
        }
    }

    public String getCookie() {
        if(cookie != null && cookie.length() == 0) return null;
        return cookie;
    }

    public boolean isLoggedIn() {
        if(getCookie() != null) return true;
        else if (loadAndKeepCookie() != null) return true;
        else return false;
    }

    public String loadAndKeepCookie() {
        cookie = PreferenceManager.getDefaultSharedPreferences(getContext())
                .getString(KEY_COOKIE, null);
        return getCookie();
    }

    public void logout() {
        cookie = null;
        PreferenceManager.getDefaultSharedPreferences(getContext()).edit().remove(KEY_COOKIE).commit();
    }

}