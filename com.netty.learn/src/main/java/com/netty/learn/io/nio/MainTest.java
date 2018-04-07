package com.netty.learn.io.nio;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class MainTest {

    private static final int SIZE = 1024;

    public static void main(String[] args) throws IOException {
        //获取文件通道---输出流
        FileChannel outChannel = new FileOutputStream("C:\\Users\\Mloong\\Desktop\\data.txt").getChannel();
        //wrap----将字节数组包装到缓冲区中（不再复制底层数组，而是将字节数组存于ByteBuffer的储存器中）
        // wrap---> new HeapByteBuffer(array, offset, length) 堆字节缓冲区
        outChannel.write(ByteBuffer.wrap("baiyunpeng".getBytes()));
        outChannel.close();

        FileChannel rwChannle = new RandomAccessFile("C:\\Users\\Mloong\\Desktop\\data.txt", "rw").getChannel();
        rwChannle.position(rwChannle.size());
        rwChannle.write(ByteBuffer.wrap(" lalalalala".getBytes()));
        rwChannle.close();


        FileChannel inChannel = new FileInputStream("C:\\Users\\Mloong\\Desktop\\data.txt").getChannel();
        //ByteBuffer buffer = ByteBuffer.allocate(SIZE);
        ByteBuffer buffer = ByteBuffer.allocateDirect(SIZE);
        inChannel.read(buffer);
        buffer.flip();
        while (buffer.hasRemaining()){
            System.out.print((char) buffer.get());
        }

    }
}
