package com.hello.anr;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class ServiceAnr extends Service {

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);

        try {
            Thread.sleep(80000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
