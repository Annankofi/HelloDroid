package com.hello.cases.thread;

import android.os.Handler;
import android.os.HandlerThread;
import android.util.Log;

import com.hello.cases.AnnanInstrumentationTestCase;

public class HandlerThreadTest extends AnnanInstrumentationTestCase {
    private static final String TAG = HandlerThreadTest.class.getSimpleName();

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

    public void testHandleThread() {
        Log.d(TAG, "testHandleThread: ");
        HandlerThread handlerThread = new HandlerThread("handler_thread");
        //start musb be run before handlerThread.getLooper()
        // or will throw android.os.MessageQueue android.os.Looper.mQueue' on a null object reference
        // Handler handler = new Handler(handlerThread.getLooper());
        //handlerThread.start();

        handlerThread.start();
        Handler handler = new Handler(handlerThread.getLooper());

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                //Only the original thread that created a view hierarchy can touch its views.
                //run handler_thread
                Log.d(TAG, "run " + Thread.currentThread().getName());
            }
        }, 1000);
        delay(10000);
    }

}
