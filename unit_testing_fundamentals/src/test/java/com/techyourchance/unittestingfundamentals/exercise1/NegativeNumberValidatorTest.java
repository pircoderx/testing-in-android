package com.techyourchance.unittestingfundamentals.exercise1;

import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest;

public class NegativeNumberValidatorTest {
    NegativeNumberValidator nnv;

    @Before
    public void setup() {
        nnv = new NegativeNumberValidator();
    }

    @Test
    public void test1() {
        Assert.assertThat(nnv.isNegative(-100), CoreMatchers.is(true));
    }

    @Test
    public void test2() {
        Assert.assertThat(nnv.isNegative(42), CoreMatchers.is(false));
    }

    @Test
    public void test3() {
        Assert.assertThat(nnv.isNegative(0), CoreMatchers.is(false));
    }
}