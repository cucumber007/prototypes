package com.cucumber007.prototypes.activities._architecture.mvp;

public class TestModel {

    private static int i = 0;

    public static String getText() {
        return "Model Text "+i;
    }

    public static void modifyText(Callback callback) {
        i++;
        callback.ok();
    }

    public interface Callback {
        void ok();
    }

}
