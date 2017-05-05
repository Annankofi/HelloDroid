package com.hello.pattern.decorator;

public abstract class Beverage {
	protected String mDescription = "unknow beverage";

	public String getDescription() {
		return mDescription;
	}

	public abstract double cost();
}
