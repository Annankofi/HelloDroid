package com.hello.pattern.factory;
public class BMW523Factory implements BMWFactory {

	@Override
	public BMW create() {
		return new BMW523();
	}
}
