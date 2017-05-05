package com.hello.pattern.factory;

public class AbstracFactoryBMW320 implements AbstractFactory {

	@Override
	public BMW createBMW() {
		return new BMW320();
	}

	@Override
	public Engine createEngine() {
		return new Engine320();
	}

	@Override
	public AirCondition createAirCondition() {
		return new AirCondition320();
	}

}
