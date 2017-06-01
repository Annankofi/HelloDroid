package com.hello.cases;

import android.test.InstrumentationTestCase;


public class AnnanInstrumentationTestCase extends InstrumentationTestCase {

    /**
     * to block the main thread and delay finish the test case
     * @param time
     */
    public void delayFinish(long time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public void delay(long time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
