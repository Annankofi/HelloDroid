package com.hello.cases;

import android.content.Intent;
import android.os.SystemClock;
import android.test.InstrumentationTestCase;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class HelloActivityTest extends InstrumentationTestCase {
    private static final String TAG = HelloActivityTest.class.getSimpleName();

    /**
     * The activity will be tested
     */
    private HelloActivity mActivity;
    private TextView mTvHello;
    private EditText mEtHello;
    private Button mBtnHello;
    private static final String TEST_PACKAGE_NAME = "com.hello.cases";

    /**
     * Sets up the fixture, for example, open a network connection. This method is called before a test is executed
     * @throws Exception
     */

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        Log.d(TAG, "setUp");
        Intent intent = new Intent();
        intent.setClassName(TEST_PACKAGE_NAME, HelloActivity.class.getName());
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mActivity = (HelloActivity) getInstrumentation().startActivitySync(intent);
        mTvHello = (TextView) mActivity.findViewById(R.id.tv_hello);
        mEtHello = (EditText) mActivity.findViewById(R.id.et_hello);
        mBtnHello = (Button) mActivity.findViewById(R.id.btn_hello);
    }

    /**
     * Make sure all resources are cleaned up and garbage collected before moving on to the next test.
     * Subclasses that override this method should make sure they call super.tearDown() at the end of
     * the overriding method
     *
     * @throws Exception
     */
    @Override
    protected void tearDown() throws Exception {
        Log.d(TAG, "tearDown");
        mActivity.finish();
        super.tearDown();
    }

    public  void testClick(){
        Log.d(TAG, "testClick");
        SystemClock.sleep(1500);
        //UI Operations must run in main Thread
        getInstrumentation().runOnMainSync(new PerformClick(mBtnHello));
        SystemClock.sleep(3000);
        assertEquals("Hello Sany", mTvHello.getText().toString());
    }

    /**
     * 点击
     */
    private class PerformClick implements Runnable {
        Button btn;
        public PerformClick(Button button) {
            btn = button;
        }

        public void run() {
            btn.performClick();
        }
    }

    public void testAdd() {
        Log.d(TAG, "jsonObjectTest");
        assertEquals(3, mActivity.add(1, 2));
    }
}
