package com.hello.cases.thread;


import android.util.Log;

public class SleepThread extends Thread {
    private static final String TAG = SleepThread.class.getSimpleName();
    /**
     * Default sleep time
     */
    private static final long DEFAULT_SLEEP = 1000;

    private long mSleepTime = DEFAULT_SLEEP;

    private boolean mNeedLoop = false;

    private boolean isFirstRun = true;

    /**
     * Reflects whether this Thread has already been started
     */
    private boolean hasBeenStarted = false;

    public SleepThread(String threadName) {
        super(threadName);
    }

    public void setSleepTime(long sleepTime) {
        checkNotStarted();
        mSleepTime = sleepTime;
    }

    public void setLoop(boolean needLoop) {
        checkNotStarted();
        mNeedLoop = needLoop;
    }

    @Override
    public void run() {
        Log.d(TAG, "run being");
        while (isFirstRun || (mNeedLoop && !isInterrupted())) {
            isFirstRun = false;
            try {
                //name:SleepThread state:RUNNABLE isAlive:true isInterrupted:false
                Log.d(TAG, "run and " + toString());
                sleep(mSleepTime);
            } catch (InterruptedException e) {
                e.printStackTrace();
                //isInterrupted will be cleared here
                //to set interrupte state
                //interrupt();
            }
            Log.d(TAG, "run and mNeedLoop:" + mNeedLoop + " isInterrupted:" + isInterrupted());
        }
        Log.d(TAG, "run end");
    }

    @Override
    public String toString() {
        return "name:" + getName() + " state:" + getState() + " isAlive:" + isAlive() + " isInterrupted:" + isInterrupted();
    }

    @Override
    public synchronized void start() {
        super.start();
        hasBeenStarted = true;
    }

    /**
     * some method must be call before start called
     */
    private void checkNotStarted() {
        if (hasBeenStarted) {
            throw new IllegalThreadStateException("Must be called before start");
        }
    }
}
