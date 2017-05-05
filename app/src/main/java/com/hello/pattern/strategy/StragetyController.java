package com.hello.pattern.strategy;

public class StragetyController implements IStrategy {
	IStrategy mStrategy;

	public StragetyController(IStrategy mStrategy) {
		super();
		this.mStrategy = mStrategy;
	}

	public IStrategy getStrategy() {
		return mStrategy;
	}

	public void setStrategy(IStrategy strategy) {
		this.mStrategy = strategy;
	}

	@Override
	public int calculate(int a, int b) {
		return mStrategy.calculate(a, b);
	}

}
