package com.hello.pattern.builder;

public class ConcreteBuilder extends Builder {

	Productor mProductor;

	public ConcreteBuilder() {
		mProductor = new Productor();
	}

	@Override
	public void buildPart1() {
		Part part1 = new Part();
		part1.setName("part1");
		mProductor.setPart1(part1);
	}

	@Override
	public void buildPart2() {
		Part part1 = new Part();
		part1.setName("part1");
		mProductor.setPart1(part1);
	}

	public Productor getProductor() {
		return mProductor;
	}
}
