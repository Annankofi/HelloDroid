package com.hello.keyevent;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;

import com.hello.droid.R;


public class KeyEventPrinterActivity extends Activity {

    private static final String TAG = KeyEventPrinterActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_key_event_printer);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        Log.d(TAG, "onKeyDown: keyCode:" + keyCode + " event:" + event.toString());
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        Log.d(TAG, "onKeyUp: keyCode:" + keyCode + " event:" + event.toString());
        return super.onKeyUp(keyCode, event);
    }

    @Override
    public boolean onKeyMultiple(int keyCode, int repeatCount, KeyEvent event) {
        Log.d(TAG, "onKeyMultiple: keyCode:" + keyCode + " repeatCount:" + repeatCount + " event:" + event.toString());
        return super.onKeyMultiple(keyCode, repeatCount, event);
    }

    @Override
    public boolean onKeyLongPress(int keyCode, KeyEvent event) {
        Log.d(TAG, "onKeyLongPress: keyCode:" + keyCode + " event:" + event.toString());
        return super.onKeyLongPress(keyCode, event);
    }

    @Override
    public boolean onKeyShortcut(int keyCode, KeyEvent event) {
        Log.d(TAG, "onKeyShortcut: keyCode:" + keyCode + " event:" + event.toString());
        return super.onKeyShortcut(keyCode, event);
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        Log.d(TAG, "dispatchKeyEvent: " + event.toString());
        return super.dispatchKeyEvent(event);
    }

    @Override
    public boolean dispatchKeyShortcutEvent(KeyEvent event) {
        Log.d(TAG, "dispatchKeyShortcutEvent: " + event.toString());
        return super.dispatchKeyShortcutEvent(event);
    }

    @Override
    public void takeKeyEvents(boolean get) {
        Log.d(TAG, "takeKeyEvents: " + get);
        super.takeKeyEvents(get);
    }

}
