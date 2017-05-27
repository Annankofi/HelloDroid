package com.hello.cases;


import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class HelloAnnan {
    private static final String TAG = HelloAnnan.class.getSimpleName();

    @Before
    public void setUp() {
        System.out.println("Say Hello");
    }

    @After
    public void tearDown() {
        System.out.println("Say GoogleBye");
    }

    @Test
    public void addition() throws Exception {
        System.out.println("addition");
        assertEquals(4, 2 + 2);
    }

    @Test
    public void calculate() throws Exception {
        System.out.println("calculate");
        for (int i = 3; i < 10000; i++) {
            if (i % 3 == 2 && i % 5 == 4 && i % 7 == 6 && i % 9 == 8 && i % 11 == 0) {
                System.out.println("result: " + i);
                break;
            }
        }
        //assertEquals(4, 2 + 2);
    }

}
