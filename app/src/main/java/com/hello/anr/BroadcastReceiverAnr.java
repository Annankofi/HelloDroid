package com.hello.anr;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class BroadcastReceiverAnr extends BroadcastReceiver {
    private static final String TAG = BroadcastReceiverAnr.class.getSimpleName();

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(TAG, "onReceiver and intent = " + intent);
//        try {
//            Thread.sleep(22000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        Log.d(TAG, "onReceiver end");
//      while (true) {
//      ;
//  }
    }
}