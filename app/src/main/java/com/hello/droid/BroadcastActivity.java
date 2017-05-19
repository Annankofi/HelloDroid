package com.hello.droid;

import android.app.ListActivity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Message;
import android.util.Log;


public class BroadcastActivity extends ListActivity {
    private static final String TAG = BroadcastActivity.class.getSimpleName();


    public static final String DEBUG_ACTION = "debug.action.hello";
    public static final String EXTRA_MSG_WHAT = "msg";
    public static final String EXTRA_ARG1 = "arg1";
    public static final String EXTRA_ARG2 = "arg2";
    private boolean mHasRegisted = false;

    public static final int MSG_MULTI_DIALOG_HELLO_WORLD = 100;
    public static final int MSG_MULTI_DIALOG_HELLO_DROID = 101;


    /**
     * adb shell am broadcast -a "debug.action.hello" --ei "msg" 100 --ei "arg1" 0 --ei "arg2" 0
     *
     * @param context
     */
    protected void registerBroadcast(Context context) {
        if (!mHasRegisted) {
            Log.d(TAG, "registerDebugBroadcast");
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction(DEBUG_ACTION);
            context.registerReceiver(mDebugReceiver, intentFilter);
        }
    }

    protected void unregisterBroadcast(Context context) {
        if (mHasRegisted) {
            Log.d(TAG, "unregisterBroadcast");
            context.unregisterReceiver(mDebugReceiver);
        }
    }

    /**
     * adb shell am broadcast -a "debug.action.hello" --ei "msg" 100 --ei "arg1" 0 --ei "arg2" 0
     *
     * @param context
     */
    private BroadcastReceiver mDebugReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(DEBUG_ACTION)) {
                int msgWhat = intent.getIntExtra(EXTRA_MSG_WHAT, 0);
                int arg1 = intent.getIntExtra(EXTRA_ARG1, 0);
                int arg2 = intent.getIntExtra(EXTRA_ARG2, 0);
                Message msg = new Message();
                msg.what = msgWhat;
                msg.arg1 = arg1;
                msg.arg2 = arg2;
                Log.d(TAG, "onReceive: " + intent.getAction() + " what:" + msg.what + " arg1:" + msg.arg1 + "  arg2:" + msg.arg1);
                onMessage(msg);
            }
        }
    };

    protected void onMessage(Message msg) {
    }

}

