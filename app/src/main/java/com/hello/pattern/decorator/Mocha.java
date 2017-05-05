package com.hello.pattern.decorator;

public class Mocha extends CondimentDecorator {
    private Beverage mBeverage;

    public Mocha(Beverage beverage) {
        this.mBeverage = beverage;
    }

    @Override
    public String getDescription() {
        return mBeverage.getDescription() + ", Mocha";
    }

    @Override
    public double cost() {
        return 1.0 + mBeverage.cost();
    }

}
