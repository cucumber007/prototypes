package com.cucumber007.prototypes.reusables.logging;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.cucumber007.prototypes.reusables.ContextApplication;

import java.util.List;


public class LogUtil {

    //todo inject
    private static class BuildConfig {
        public static boolean DEBUG_MODE = false;
    }

    private static class Crashlytics {
        public static void logException(Throwable throwable) {};
        public static void log(String throwable) {};
    }

    private static String TAG = "cutag";

    public static void logError(Throwable throwable) {
        if(!BuildConfig.DEBUG_MODE) Crashlytics.logException(throwable);
        throwable.printStackTrace();
        logcat(throwable.getMessage());
    }

    public static void logError(Throwable throwable, String message) {
        if (!BuildConfig.DEBUG_MODE) {
            Crashlytics.log(message);
            Crashlytics.logException(throwable);
        }
        throwable.printStackTrace();
        logcat(throwable.getMessage());
    }

    @SafeVarargs
    public static <T> void logDebug(Character delim, T... vars) {
        String log = "";
        for (int i = 0; i < vars.length; i++) {
            log += vars[i].toString() + delim;
        }
        logcat(log);
    }

    @SafeVarargs
    public static <T> void logDebug(String title, T... vars) {
        String log = title+ " : ";
        for (int i = 0; i < vars.length; i++) {
            log += vars[i].toString() + " ";
        }
        logcat(log);
    }

    @SafeVarargs
    public static <T> void logDebug(boolean condition, String title, T... vars) {
        if (condition) {
            String log = title + " : ";
            for (int i = 0; i < vars.length; i++) {
                log += vars[i].toString() + " ";
            }
            logcat(log);
        }
    }


    public static void logListDebug(String name, List<Object> list) {
        String log = name + "\n";
        for (int i = 0; i < list.size(); i++) {
            log += list.get(i) + "\n";
        }
        logcat(log);
    }

    public static void logListDebug(List<Object> list) {
        String log = "";
        for (int i = 0; i < list.size(); i++) {
            log += list.get(i) + "\n";
        }
        logcat(log);
    }

    public static void logcat(String message) {
        Log.d(TAG, message);
    }

    public static void logcat(String tag, String message) {
        Log.d(tag, message);
    }

    public static void makeToast(Context context, String text) {
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
    }

    public static void makeToast(String text) {
        makeToast(ContextApplication.getContext(), text);
    }

    public static void makeToast(Context context, int stringId) {
        Toast.makeText(ContextApplication.getContext(), ContextApplication.getContext().getResources().getString(stringId), Toast.LENGTH_SHORT).show();
    }

    public static void makeToast(int stringId) {
        makeToast(ContextApplication.getContext(), stringId);
    }

    public static void makeToastWithDebug(String text, String debugText) {
        if(!BuildConfig.DEBUG_MODE) makeToast(text);
        else makeToast(debugText);
    }

    public static void makeToastWithDebug(int stringId, String debugText) {
        if(!BuildConfig.DEBUG_MODE) makeToast(stringId);
        else makeToast(debugText);
    }



}
