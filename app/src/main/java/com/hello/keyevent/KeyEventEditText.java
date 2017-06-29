package com.hello.keyevent;

import android.content.Context;
import android.text.method.KeyListener;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.EditText;


public class KeyEventEditText extends EditText {
    private static final String TAG = KeyEventEditText.class.getSimpleName();
    public KeyEventEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void setKeyListener(KeyListener input) {
        super.setKeyListener(input);
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
    public boolean onKeyMultiple(int keyCode, int repeatCount, KeyEvent event) {
        Log.d(TAG, "onKeyMultiple keyCode:" + keyCode + " event:" + event.toString());
        return super.onKeyMultiple(keyCode, repeatCount, event);
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        Log.d(TAG, "onKeyUp keyCode:" + keyCode + " event:" + event.toString());
        return super.onKeyUp(keyCode, event);
    }

    @Override
    public boolean onKeyShortcut(int keyCode, KeyEvent event) {
        Log.d(TAG, "onKeyShortcut keyCode:" + keyCode + " event:" + event.toString());
        return super.onKeyShortcut(keyCode, event);
    }

    @Override
    public void setOnKeyListener(OnKeyListener l) {
        Log.d(TAG, "setOnKeyListener ");
        super.setOnKeyListener(l);
    }

    @Override
    public KeyEvent.DispatcherState getKeyDispatcherState() {
        return super.getKeyDispatcherState();
    }

    @Override
    public boolean dispatchKeyEventPreIme(KeyEvent event) {
        Log.d(TAG, "dispatchKeyEventPreIme  event:" + event.toString());
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
    public boolean onKeyLongPress(int keyCode, KeyEvent event) {
        Log.d(TAG, "onKeyLongPress event:" + event.toString());
        return super.onKeyLongPress(keyCode, event);
    }

}
