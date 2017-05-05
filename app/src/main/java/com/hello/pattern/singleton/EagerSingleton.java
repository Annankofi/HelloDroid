package com.hello.pattern.singleton;

import android.util.Log;

public class EagerSingleton {
    private static final String TAG = EagerSingleton.class.getSimpleName();
    private static EagerSingleton mInstance = new EagerSingleton();

    private EagerSingleton() {
    }

    public static EagerSingleton getInstance() {
        return mInstance;
    }

    public void sayHello() {
        Log.d(TAG, "Hello EagerSingleton");
    }
}
