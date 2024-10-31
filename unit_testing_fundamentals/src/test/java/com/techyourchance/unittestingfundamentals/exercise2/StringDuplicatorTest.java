package com.techyourchance.unittestingfundamentals.exercise2;

import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class StringDuplicatorTest {
    StringDuplicator sd;

    @Before
    public void setup() {
        sd = new StringDuplicator();
    }

    @Test
    public void test1() {
        Assert.assertThat(sd.duplicate(""), CoreMatchers.is(""));
    }

    @Test
    public void test2() {
        Assert.assertThat(sd.duplicate("abc123"), CoreMatchers.is("abc123abc123"));
    }
}