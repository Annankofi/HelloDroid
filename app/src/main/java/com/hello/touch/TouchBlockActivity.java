package com.hello.touch;

import android.app.Activity;
import android.os.Bundle;

public class TouchBlockActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new BlockTouchView(this));
    }
}
