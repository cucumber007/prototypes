package com.cucumber007.prototypes.sandbox.callbacks_state;


import rx.Observable;

public class LargeString {

    public static String get(int size) {
        StringBuilder stringBuilder = new StringBuilder(size);
        Observable.just('A').repeat(size).subscribe(stringBuilder::append);
        return stringBuilder.toString();
    }
}
