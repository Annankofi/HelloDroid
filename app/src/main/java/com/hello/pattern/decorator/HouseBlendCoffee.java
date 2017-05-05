package com.hello.pattern.decorator;

public class HouseBlendCoffee extends Beverage {

	public HouseBlendCoffee() {
		mDescription = "HouseBlendCoffee:";
	}
	@Override
	public double cost() {
		return 12;
	}
}
