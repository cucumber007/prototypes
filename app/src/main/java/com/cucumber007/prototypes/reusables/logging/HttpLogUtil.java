package com.cucumber007.prototypes.reusables.logging;

import okhttp3.logging.HttpLoggingInterceptor;

public class HttpLogUtil {

    public static HttpLoggingInterceptor getFacebookHttpInterceptor() {
        return getHttpInterceptor("fbhttp", HttpLoggingInterceptor.Level.BASIC);
    }

    public static HttpLoggingInterceptor getHttpInterceptor() {
        return getHttpInterceptor("http", HttpLoggingInterceptor.Level.BODY);
    }

    public static HttpLoggingInterceptor getHttpInterceptor(String tag, HttpLoggingInterceptor.Level level) {

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor(message -> {
            LogUtil.logcat(tag, "" + message);
        });
        logging.setLevel(level);
        return logging;
    }

}
