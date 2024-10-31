package com.techyourchance.unittestingfundamentals.exercise3;

import org.hamcrest.CoreMatchers;
import org.junit.Assert;

import com.techyourchance.unittestingfundamentals.example3.Interval;

import org.junit.Before;
import org.junit.Test;

public class IntervalsAdjacencyDetectorTest {
    IntervalsAdjacencyDetector iad;

    @Before
    public void setup() throws Exception {
        iad = new IntervalsAdjacencyDetector();
    }

    @Test
    public void test1() {
        Assert.assertThat(iad.isAdjacent(new Interval(-100, 8), new Interval(8, 42))
                , CoreMatchers.is(true));
    }

    @Test
    public void test2() {
        Assert.assertThat(iad.isAdjacent(new Interval(42, 100), new Interval(0, 42))
                , CoreMatchers.is(true));
    }

    @Test
    public void test3() {
        Assert.assertThat(iad.isAdjacent(new Interval(-100, 6), new Interval(8, 42))
                , CoreMatchers.is(false));
    }

    @Test
    public void test4() {
        Assert.assertThat(iad.isAdjacent(new Interval(-100, 8), new Interval(0, 42))
                , CoreMatchers.is(false));
    }

    @Test
    public void test5() {
        Assert.assertThat(iad.isAdjacent(new Interval(-1, 8), new Interval(-100, 42))
                , CoreMatchers.is(false));
    }

    @Test
    public void test6() {
        Assert.assertThat(iad.isAdjacent(new Interval(-100, 42), new Interval(-1, 8))
                , CoreMatchers.is(false));
    }

    @Test
    public void test7() {
        Assert.assertThat(iad.isAdjacent(new Interval(-100, 42), new Interval(-100, 42))
                , CoreMatchers.is(false));
    }

    @Test
    public void test8() {
        Assert.assertThat(iad.isAdjacent(new Interval(-100, 41), new Interval(0, 42))
                , CoreMatchers.is(false));
    }

    @Test
    public void test9() {
        Assert.assertThat(iad.isAdjacent(new Interval(-10, 42), new Interval(-100, -30))
                , CoreMatchers.is(false));
    }
}