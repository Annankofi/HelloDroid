package com.hello.pattern.factory;

public class BMW320Factory implements BMWFactory {

    @Override
    public BMW create() {
        return new BMW320();
    }
}
