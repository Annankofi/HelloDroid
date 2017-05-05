package com.hello.system;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.WindowManager;

import com.hello.droid.R;

public class AdjustPanActivity extends Activity {
    private static final String TAG = AdjustPanActivity.class.getSimpleName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG," onCreate");
       setContentView(R.layout.activity_adjust_pan);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
            }
        }, 5000);
    }
}
