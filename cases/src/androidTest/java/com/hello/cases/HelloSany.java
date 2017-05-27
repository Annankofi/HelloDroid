package com.hello.cases;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;

/**
 * 注明使用了RunWith(AndroidJUnit4.class) 同junit即test目录下的测试方法
 * 只不过这里需要运行到模拟器或者真机,可以使用android的类 如Log
 * 测试方法需要@Test
 */
@RunWith(AndroidJUnit4.class)
public class HelloSany {
    private static final String TAG = HelloSany.class.getSimpleName();

    @Before
    public void setUp() throws Exception {
        Log.d(TAG, "setUp");

    }

    @After
    public void tearDown() {
        Log.d(TAG, "tearDown");
    }

    @Test
    public void useAppContext() throws Exception {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

        assertEquals("com.hello.cases", appContext.getPackageName());
    }

    @Test
    public void add() throws Exception {
        Log.d(TAG, "jsonObjectTest");
        assertEquals(6, 4 + 2);
    }
}
