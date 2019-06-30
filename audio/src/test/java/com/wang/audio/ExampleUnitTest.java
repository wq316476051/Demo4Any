package com.wang.audio;

import android.util.Log;

import com.wang.audio.utils.ByteUtils;

import org.junit.Test;

import java.util.Arrays;
import java.util.stream.Stream;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void test_bytesUtils() {

        // size = ((size + pagesize -1) & ~(pagesize -1);
        int size = 53;
        int pageSize = 4;
        size = (size + pageSize - 1) & ~(pageSize - 1);
        System.out.println("size = " + size); // 56 是 4 的整数倍
    }
}