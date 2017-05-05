package com.hello.pattern.singleton;

import android.util.Log;

public class LazySingleton {
    private static final String TAG = LazySingleton.class.getSimpleName();
    // Do no initialized
    private static LazySingleton mSingleton = null;

    private LazySingleton() {
    }

    ;

    public static LazySingleton getInstance1() {
        if (mSingleton == null) {
            mSingleton = new LazySingleton();
        }
        return mSingleton;
    }

    public synchronized static LazySingleton getInstance2() {
        if (mSingleton == null) {
            mSingleton = new LazySingleton();
        }
        return mSingleton;
    }

    // http://www.iteye.com/topic/652440
    // http://www.iteye.com/topic/260515
    private volatile static LazySingleton mSingleton3 = null;

    public static LazySingleton getInstance3() {
        if (mSingleton3 == null) {
            synchronized (LazySingleton.class) {
                if (mSingleton3 == null) {
                    mSingleton3 = new LazySingleton();
                }
            }
        }
        return mSingleton3;
    }

    private static class LazyHolder {
        private static final LazySingleton INSTANCE = new LazySingleton();
    }

    public static final LazySingleton getInstance4() {
        return LazyHolder.INSTANCE;
    }

    public void sayHello() {
        Log.d(TAG, "Hello LazySingleton");
    }
}
