package com.wang.demo4any.nio;

import org.junit.Test;

import java.nio.ByteBuffer;

/**
 * 1. Buffer
 *      ByteBuffer  : HeapByteBuffer（JVM 堆）、MappedByteBuffer（内存映射文件）
 *      CharBuffer
 *      ShortBuffer
 *      IntBuffer
 *      LongBuffer
 *      FloatBuffer
 *      DoubleBuffer
 *
 * 2. 属性
 *      capacity    最大容量：无法修改
 *      position    位置索引：下一个要读取的位置
 *      limit       限制：不可以访问的第一个索引
 *      mark        标记：记录一个位置索引，用reset()回到mark地址
 *
 * 3. 方法
 *      allocate()          配置内存空间
 *      allocateDirect()    分配内核映射文件
 *
 *      get()       读取，position位置跟随移动（有index 的例外）
 *      put()       写入，position位置跟随移动（有index 的例外）
 *
 *      flip()      翻转：写转读的调换。  limit = position; position = 0; mark = -1;
 *          一般用于写之后，读。
 *      rewind()    倒带：重新读或重新写。position = 0; mark = -1;
 *      clear()     清空：position = 0; limit = capacity; mark = -1;
 *          一般用于读之后，重新写。
 *
 *      mark()      标记        mark = position;
 *      reset()     回到标记    position = mark; 如果mark<0，会报错。
 *
 *      remaining()     剩余空间
 *      hasRemaining()  是否还有剩余空间
 *
 *      capacity()
 *      position()
 *      position(int newPosition)
 *      limit()
 *      limit(int newLimit)
 *
 *      compact()       压紧：把可用数据移动到从0开始
 *      duplicate()     拷贝所有数据。数据相互影响
 *      slice()         拷贝position 到 limit之间的内容。数据相互影响
 *      order()         Java中默认的是大端排序
 *
 *      array()         返回其中的数组
 */
public class BufferTest {

    @Test
    public void attributes() {
        ByteBuffer buffer = ByteBuffer.allocate(1024);

        buffer.get(3);
        buffer.put(3, (byte) 4);

        buffer.flip();
        buffer.rewind();
        /*
        limit = position; position = 0; mark = -1;
        position = 0; mark = -1;
        position = 0; limit = capacity; mark = -1;
         */
        buffer.clear();

        buffer.mark();
        buffer.reset();

        buffer.remaining();
        buffer.hasRemaining();

        byte[] array = buffer.array();

        buffer.arrayOffset();
        buffer.compact();
        buffer.duplicate();
        buffer.order();
        buffer.slice();
        buffer.position(3);
    }
}
