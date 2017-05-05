package com.hello.system;

import android.app.Activity;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.ActionMode;
import android.view.WindowManager;

import com.hello.droid.R;


public class AdjustSizeActivity extends Activity {
    private static final String TAG = AdjustSizeActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG,"onCreate");
        setContentView(R.layout.activity_adjust_size);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
               // getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
                getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
            }
        }, 5000);
    }

    @Override
    public void reportFullyDrawn() {
        Log.d(TAG,"reportFullyDrawn");
        super.reportFullyDrawn();
    }

    @Override
    public void onActionModeFinished(ActionMode mode) {
        Log.d(TAG,"onActionModeFinished");
        super.onActionModeFinished(mode);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        Log.d(TAG,"onConfigurationChanged");
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public void onWindowAttributesChanged(WindowManager.LayoutParams params) {
        Log.d(TAG,"onWindowAttributesChanged");
        super.onWindowAttributesChanged(params);
    }

}
