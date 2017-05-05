package com.hello.pattern.builder;

public class Director {
    private Builder mBuilder;

    public Director(Builder mBuilder) {
        super();
        this.mBuilder = mBuilder;
    }

    public void construct() {
        mBuilder.buildPart1();
        mBuilder.buildPart2();
    }
}
