package com.hello.pattern.facade;

import android.util.Log;

public class DrawerTwo {
	private static final String TAG = DrawerTwo.class.getSimpleName();
	public void open() {
		Log.d(TAG, "open");
		getFile();
	}
	private void getFile() {
		Log.d(TAG, "getFile");
	}
}
