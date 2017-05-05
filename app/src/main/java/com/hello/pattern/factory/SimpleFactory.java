package com.hello.pattern.factory;

public class SimpleFactory {
	public BMW createBMW(int type) {
		BMW bmw = null;
		switch (type) {
		case BMW.BMW_320:
			bmw = new BMW320();
			break;
		case BMW.BMW_523:
			bmw = new BMW523();
			break;
		default:
			break;
		}
		return bmw;
	}

	public static BMW create(int type) {
		BMW bmw = null;
		switch (type) {
		case BMW.BMW_320:
			bmw = new BMW320();
			break;
		case BMW.BMW_523:
			bmw = new BMW523();
			break;
		default:
			break;
		}
		return bmw;
	}
}
