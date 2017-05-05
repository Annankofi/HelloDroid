package com.hello.pattern.facade;

import android.util.Log;

public class DrawerOne {
	private static final String TAG = DrawerOne.class.getSimpleName();
	public void open() {
		Log.d(TAG, "open");
		getKey();
	}
	private void getKey() {
		Log.d(TAG, "getKey");
	}
}
