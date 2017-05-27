package com.hello.cases;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CalculatorTest {

    Calculator mCalculator;
    @Before
    public void setUp() throws Exception {
        mCalculator = new Calculator();
    }

    @Test
    public void sum() throws Exception {
        System.out.println("sum");
        assertEquals(6d, mCalculator.sum(1d, 5d), 0d);
    }

    @Test
    public void sub() throws Exception {
        System.out.println("sub");
        assertEquals(6d, mCalculator.sub(10d, 5d), 0d);
    }

}