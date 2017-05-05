package com.hello.pattern.decorator;

public class EspressoCoffee extends Beverage {
	public EspressoCoffee() {
		mDescription = "EspressoCoffee:";
	}

	@Override
	public double cost() {
		return 10.0;
	}
}
