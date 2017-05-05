package com.hello.pattern.strategy;

public class AddStrategy implements IStrategy {

	@Override
	public int calculate(int a, int b) {
		return a + b;
	}

}
