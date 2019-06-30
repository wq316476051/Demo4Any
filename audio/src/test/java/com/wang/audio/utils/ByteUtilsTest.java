package com.wang.audio.utils;

import org.junit.Test;

import static org.junit.Assert.*;

public class ByteUtilsTest {

    @Test
    public void bytesToLong() {
        System.out.println("currentTimeMillis = " + System.currentTimeMillis());
        byte[] bytes = ByteUtils.longToBytes(System.currentTimeMillis());

        long time = ByteUtils.bytesToLong(bytes);
        System.out.println("time = " + time);
    }

    @Test
    public void bytesToLong1() {
    }
}