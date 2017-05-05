package com.hello.pattern.adapter;

import android.util.Log;

public class XiaoMiWrapper implements Usb {
	private static final String TAG =XiaoMiWrapper.class.getSimpleName();
	private Phone mPhone;

	public XiaoMiWrapper(Phone phone) {
		this.mPhone = phone;
	}

	@Override
	public void store() {
		Log.d(TAG, "store");
	}

	@Override
	public void takeAlong() {
		mPhone.takeAlong();
	}
}
