package com.hello.pattern.adapter;

import android.util.Log;

public class Phone {
	private static final String TAG = Phone.class.getSimpleName();
	public void call(){
		Log.d(TAG,"call");
	}
	public void sms(){
		Log.d(TAG,"sms");
	}
	public void takeAlong(){
		Log.d(TAG,"takeAlong");
	}
}
