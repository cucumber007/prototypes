package com.cucumber007.prototypes.reusables;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;


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

    public static void logDebug(Boolean bool) {
        logDebug(""+bool);
    }

    public static void logDebug(String... strings) {
        logDebug(' ', strings);
    }

    public static void logDebug(Integer... integers) {
        logDebug(' ', integers);
    }

    public static void logDebug(Character delim, String... strings) {
        String log = "";
        for (int i = 0; i < strings.length; i++) {
            log += strings[i] + delim;
        }
        logcat(log);
    }

    public static void logDebug(Character delim, Integer... integers) {
        String log = "";
        for (int i = 0; i < integers.length; i++) {
            log += integers[i] + "" + delim;
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


}
