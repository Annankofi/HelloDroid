package com.hello.pattern.adapter;

import android.util.Log;

public class XiaoMi extends Phone implements Usb {
	private static final String TAG =XiaoMi.class.getSimpleName();
	@Override
	public void store() {
		Log.d(TAG, "store");
	}
	@Override
	public void call() {
		Log.d(TAG, "call");
	}
}
