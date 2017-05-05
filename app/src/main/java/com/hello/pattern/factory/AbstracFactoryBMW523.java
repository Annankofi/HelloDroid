package com.hello.pattern.factory;

public class AbstracFactoryBMW523 implements AbstractFactory {

	@Override
	public BMW createBMW() {
		return new BMW523();
	}

	@Override
	public Engine createEngine() {
		return new Engine523();
	}

	@Override
	public AirCondition createAirCondition() {
		return new AirCondition523();
	}

}
