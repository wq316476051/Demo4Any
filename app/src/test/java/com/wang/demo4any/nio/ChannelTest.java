package com.wang.demo4any.nio;

import org.junit.Test;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

/**
 * 1. Channel
 *      FileChannel
 *
 * 2. Channels  : Channel & Stream 转换
 *
 * 3. 属性
 *
 * 4. 方法
 *      truncate(long size)     截断文件，截断到 size
 */
public class ChannelTest {

    /*
    继承关系：
    Closeable                   close();
    Channel                     boolean isOpen();
        ReadableByteChannel     int read(ByteBuffer dst)
        WritableByteChannel     int write(ByteBuffer src)

    ByteChannel extends ReadableByteChannel, WritableByteChannel

    SeekableByteChannel extends ByteChannel

    FileChannel extends AbstractInterruptibleChannel
        implements SeekableByteChannel, GatheringByteChannel 聚集, ScatteringByteChannel 分散
     */
    @Test
    public void test2() {
        try {
            Path path = Paths.get("ccc.txt");
            try (FileChannel channel = FileChannel.open(path, StandardOpenOption.WRITE, StandardOpenOption.CREATE)) {
                ByteBuffer buffer = ByteBuffer.allocate(1024);
                buffer.put("hello".getBytes(StandardCharsets.UTF_8));
                buffer.flip();
                channel.write(buffer);
                channel.truncate(channel.size() - 1);
            }
            try (FileChannel channel = FileChannel.open(path, StandardOpenOption.WRITE)) {
                channel.truncate(channel.size() - 1);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test() {
        try {
//            Path path = Files.createFile(Paths.get("aaa.txt"));
//            try (FileChannel channel = FileChannel.open(path, StandardOpenOption.WRITE)) {
//                ByteBuffer buffer = ByteBuffer.allocate(1024);
//                buffer.put("hello".getBytes(StandardCharsets.UTF_8));
//                buffer.flip();
//                channel.write(buffer);
//            }
            Path path = Paths.get("aaa.txt");
            try (FileChannel channel = FileChannel.open(path, StandardOpenOption.READ, StandardOpenOption.WRITE)) {

                System.out.println("size 1 = " + channel.size());
                FileChannel truncate = channel.truncate(2);
                System.out.println("size 2 = " + truncate.size());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
