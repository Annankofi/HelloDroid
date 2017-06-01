package com.hello.cases.thread;


import android.util.Log;

import com.hello.cases.AnnanInstrumentationTestCase;

public class ThreadTest extends AnnanInstrumentationTestCase {
    private static final String TAG = ThreadTest.class.getSimpleName();

    private SleepThread mThread;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        Log.d(TAG, "setUp");
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
        Log.d(TAG, "tearDown");
    }

    public void testLifecycle() {
        mThread = new SleepThread("SleepThread");
        mThread.setSleepTime(2000);
        //name:SleepThread state:NEW isAlive:false isInterrupted:false
        Log.d(TAG, "threadLifecycleTest " + mThread.toString());
        mThread.start();
        Log.d(TAG, "threadLifecycleTest " + mThread.toString());
        //mainThread
        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //name:SleepThread state:TERMINATED isAlive:false isInterrupted:false
        Log.d(TAG, "threadLifecycleTest " + mThread.toString());
        //IllegalThreadStateException: Thread already started
        //a thread can only be started once no recycling
        //mThread.start();
    }

    public void testInterruptSleep() {
        mThread = new SleepThread("SleepThread");
        mThread.setSleepTime(800);
        mThread.setLoop(true);
        mThread.start();
        delay(1000);
        Log.d(TAG, "testInterruptSleep and call interrupt");
        //sorry that although InterruptedException is taken but isInterrupted() return false
        mThread.interrupt();
        // Log.d(TAG, "testInterruptSleep and call interrupted");
        //sorry it will no call InterruptedException
        //mThread.interrupted();
        Log.d(TAG, "testInterruptSleep " + mThread.toString());
        //to block main thread and delay the test case to finish
        delayFinish(8000);
        Log.d(TAG, "testInterruptSleep " + mThread.toString());
    }

    public void testJoin() {
        /** case1 **/
        mThread = new SleepThread("SleepThread");
        mThread.setSleepTime(800);
        mThread.setLoop(false);
        mThread.start();
        Log.d(TAG, "testJoin before join");
        try {
            //The current thread(MainThread) will be blocked here until mThread finished or mThread interrupt called
            mThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Log.d(TAG, "testJoin after join");

        /** case2 **/
        mThread = new SleepThread("SleepThread-Case2");
        mThread.setSleepTime(2000);
        mThread.setLoop(false);
        mThread.start();
        Log.d(TAG, "testJoin before join");
        try {
            //The current thread(MainThread) will be blocked here longest 500ms
            mThread.join(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Log.d(TAG, "testJoin after join");


        /** case3 **/
        //s2 will be started after s1 finished
        SleepThread s1 = new SleepThread("S1");
        SleepThread s2 = new SleepThread("S2");
        s1.start();
        try {
            s1.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        s2.start();
    }

    public void testWait() {

       /*
        String lock = "strLock";
       try {
          //IllegalMonitorStateException: object not locked by thread before wait()
            lock.wait();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //modified as follow
         try {
           synchronized(lock){
            lock.wait();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        */
        new ConsumeAndProductThread().start();
        delay(40000);
    }
}
