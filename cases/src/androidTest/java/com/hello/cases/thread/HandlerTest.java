package com.hello.cases.thread;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

import com.hello.cases.AnnanInstrumentationTestCase;

public class HandlerTest extends AnnanInstrumentationTestCase {
    private static final String TAG = HandlerTest.class.getSimpleName();

    private static final int MSG_NORMAL = 0;
    /**
     * the msg will be executed long time
     */
    private static final int MSG_TIME_COMSUMING = 1;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    public void testHandleMessage() {
        Log.d(TAG, "testHandleThread: ");
        mMainHandler.sendEmptyMessage(MSG_NORMAL);
        mMainHandler.sendEmptyMessage(MSG_TIME_COMSUMING);
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Log.d(TAG, "removeMessage MSG_TIME_COMSUMING");
        mMainHandler.removeMessages(MSG_TIME_COMSUMING);
        mMainHandler.sendEmptyMessage(MSG_NORMAL);
        delay(1000);
    }

    public void testHandleInSubThread() {
        ContainHandlerThread t = new ContainHandlerThread();
        t.start();
        t.sendEmptyMessage(1);
        delay(1000);
        t.sendEmptyMessage(1);
        delay(10000);
    }

    class ContainHandlerThread extends Thread {
        private Handler handler;

        public void sendEmptyMessage(int what) {
            if (handler != null) {
                handler.sendEmptyMessage(what);
            } else {
                Log.d(TAG, "sendEmptyMessage but handler is null");
            }
        }

        @Override
        public void run() {
            super.run();
            Log.d(TAG, "run and prepare");
            Looper.prepare();
            handler = new Handler() {
                @Override
                public void handleMessage(Message msg) {
                    super.handleMessage(msg);
                    Log.d(TAG, "handleMessage what:" + msg.what + " arg1:" + msg.arg1 + " arg2:" + msg.arg2);
                    //should not update ui here
                    //btn.setText("1");
                }
            };
            Log.d(TAG, "run and loop");
            Looper.loop();
        }
    }

    /**
     * callback
     * must set mainLooper here ,or it will cause empty test suite
     */
    private Handler mMainHandler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Log.d(TAG, "handleMessage what:" + msg.what + " arg1:" + msg.arg1 + " arg2:" + msg.arg2);

            switch (msg.what) {
                case MSG_NORMAL:

                    break;
                case MSG_TIME_COMSUMING:
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    break;
            }
            Log.d(TAG, "handleMessage end");
        }
    };


}
