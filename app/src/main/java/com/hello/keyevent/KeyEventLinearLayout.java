package com.hello.keyevent;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.LinearLayout;


public class KeyEventLinearLayout extends LinearLayout {
    private static final String TAG = KeyEventLinearLayout.class.getSimpleName();
    public KeyEventLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void setOnKeyListener(OnKeyListener l) {
        Log.d(TAG, "setOnKeyListener ");
        super.setOnKeyListener(l);
    }

    @Override
    public boolean onKeyPreIme(int keyCode, KeyEvent event) {
        Log.d(TAG, "onKeyPreIme keyCode:" + keyCode + " event:" + event.toString());
        return super.onKeyPreIme(keyCode, event);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        Log.d(TAG, "onKeyDown keyCode:" + keyCode + " event:" + event.toString());
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onKeyLongPress(int keyCode, KeyEvent event) {
        Log.d(TAG, "onKeyLongPress keyCode:" + keyCode + " event:" + event.toString());
        return super.onKeyLongPress(keyCode, event);
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        Log.d(TAG, "onKeyUp keyCode:" + keyCode + " event:" + event.toString());
        return super.onKeyUp(keyCode, event);
    }

    @Override
    public boolean onKeyMultiple(int keyCode, int repeatCount, KeyEvent event) {
        Log.d(TAG, "onKeyMultiple keyCode:" + keyCode + " event:" + event.toString());
        return super.onKeyMultiple(keyCode, repeatCount, event);
    }

    @Override
    public boolean onKeyShortcut(int keyCode, KeyEvent event) {
        Log.d(TAG, "onKeyShortcut keyCode:" + keyCode + " event:" + event.toString());
        return super.onKeyShortcut(keyCode, event);
    }

    @Override
    public boolean dispatchKeyEventPreIme(KeyEvent event) {
        Log.d(TAG, "dispatchKeyEventPreIme event:" + event.toString());
        return super.dispatchKeyEventPreIme(event);
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        Log.d(TAG, "dispatchKeyEvent event:" + event.toString());
        return super.dispatchKeyEvent(event);
    }

    @Override
    public boolean dispatchKeyShortcutEvent(KeyEvent event) {
        Log.d(TAG, "dispatchKeyShortcutEvent event:" + event.toString());
        return super.dispatchKeyShortcutEvent(event);
    }

    @Override
    public KeyEvent.DispatcherState getKeyDispatcherState() {
        Log.d(TAG, "getKeyDispatcherState");
        return super.getKeyDispatcherState();
    }

}
