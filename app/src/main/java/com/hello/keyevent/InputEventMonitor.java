package com.hello.keyevent;

import android.content.Context;
import android.hardware.input.InputManager;
import android.util.Log;
import android.view.InputEvent;
import android.view.InputEventReceiver;
import android.view.KeyEvent;
import android.view.MotionEvent;


/**
 *
 */
public class InputEventMonitor {
    private static final String TAG = InputEventMonitor.class.getSimpleName();
    private InputEventReceiver mInputReceiver;
    private Context mContext;

    public InputEventMonitor(Context context) {
        mContext = context;
    }

    public void start() {
        Log.d(TAG, "start");
        InputManager im = (InputManager) mContext.getSystemService(Context.INPUT_SERVICE);
        if (mInputReceiver != null) {
            mInputReceiver.dispose();
        }
        //OpenApi名字可以随便取
        mInputReceiver = new InputEventReceiver(im.monitorInput("OpenApi"), mContext.getMainLooper()) {
            @Override
            public void onInputEvent(InputEvent e) {
                Log.d(TAG, "onInputEvent: " + e.toString());
                try {
                    if (e instanceof MotionEvent) {
                        Log.d(TAG, "onInputEvent: MotionEvent");

                    } else if (e instanceof KeyEvent) {
                        Log.d(TAG, "onInputEvent: KeyEvent");
                    }
                } catch (Exception ex) {
                    Log.e(TAG, "InputEventReceiver init failed!", ex);
                } finally {
                    finishInputEvent(e, false);
                }
            }
        };
    }

    public void stop() {
        if (mInputReceiver != null) {
            Log.d(TAG, "stop input receiver ");
            mInputReceiver.dispose();
        }
    }

}
