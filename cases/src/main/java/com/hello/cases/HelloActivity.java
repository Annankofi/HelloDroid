package com.hello.cases;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class HelloActivity extends Activity {

    private static final String TAG = HelloActivity.class.getSimpleName();
    private TextView mTvHello;
    private EditText mEtHello;
    private Button mBtnHello;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hello);
        mTvHello = (TextView)findViewById(R.id.tv_hello);
        mEtHello = (EditText)findViewById(R.id.et_hello);
        mBtnHello = (Button)findViewById(R.id.btn_hello);
        mBtnHello.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mEtHello.getText().equals("Hello Annan")) {
                    Log.d(TAG, "onClick and set Text:Hello Annan");
                    mTvHello.setText("Hello Annan");
                } else {
                    Log.d(TAG, "onClick and set Text:Hello World");
                    mTvHello.setText("Hello World");
                }
            }
        });
    }

    public int add(int a, int b) {
        return a + b;
    }
}
