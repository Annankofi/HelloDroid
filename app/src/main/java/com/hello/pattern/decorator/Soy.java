package com.hello.pattern.decorator;

public class Soy extends CondimentDecorator {
    private Beverage mBeverage;

    public Soy(Beverage beverage) {
        this.mBeverage = beverage;
    }

    @Override
    public String getDescription() {
        return mBeverage.getDescription() + ", Soy";
    }

    @Override
    public double cost() {
        return 1.5 + mBeverage.cost();
    }

}
