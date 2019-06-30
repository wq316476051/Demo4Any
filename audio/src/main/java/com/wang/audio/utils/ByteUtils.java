package com.wang.audio.utils;

import java.nio.ByteBuffer;

/**
 * Big endian 大端模式，符合人类的正常思维
 * 内存地址：
 *      低位 ----- 高位
 * byte[]存储：
 *      高位 ----- 低位
 */
public class ByteUtils {

    private ByteUtils() {}

    /**
     * Convert 4 bytes to int.
     */
    public static int bytesToInt(byte[] bytes) {
        return bytesToInt(bytes, 0);
    }

    /**
     * Convert 4 bytes to int.
     */
    public static int bytesToInt(byte[] bytes, int offset) {
        return (bytes[offset] & 0xFF) << 24
                | (bytes[1 + offset] & 0xFF) << 16
                | (bytes[2 + offset] & 0xFF) << 8
                | bytes[3 + offset] & 0xFF;
    }

    public static byte[] intToBytes(int i) {
        return new byte[] {
                (byte) ((i >> 24) & 0xFF),
                (byte) ((i >> 16) & 0xFF),
                (byte) ((i >> 8) & 0xFF),
                (byte) (i & 0xFF)
        };
    }

    //byte 数组与 long 的相互转换
    public static byte[] longToBytes(long value) {
        ByteBuffer buffer = ByteBuffer.allocate(8);
        buffer.putLong(0, value);
        return buffer.array();
    }

    public static long bytesToLong(byte[] bytes) {
        return bytesToLong(bytes, 0);
    }

    public static long bytesToLong(byte[] bytes, int offset) {
        ByteBuffer buffer = ByteBuffer.allocate(8);
        buffer.put(bytes, offset, 8);
        buffer.flip();
        return buffer.getLong();
    }
}
