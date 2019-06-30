package com.wang.demo4any.time;

import org.junit.Test;

import java.time.Instant;

public class InstantTest {

    @Test
    public void test() {
        Instant instant = Instant.now();
        System.out.println("instant = " + instant);

        long epochMilli = instant.toEpochMilli();
        System.out.println("epochMilli = " + epochMilli);

        long currentTimeMillis = System.currentTimeMillis();
        System.out.println("currentTimeMillis = " + currentTimeMillis);

        Instant instant1 = Instant.ofEpochMilli(epochMilli);
        System.out.println("instant1 = " + instant1);
    }
}
