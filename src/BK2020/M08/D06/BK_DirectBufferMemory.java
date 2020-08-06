package BK2020.M08.D06;

import java.nio.ByteBuffer;

/**
 * 写NIO程序的时候常使用ByteBuffer来读取和写入数据，这是一种基于通道（chnnel）与缓冲区（buffer）的IO
 * 他可以使用Native直接分配堆外内存，然后通过存储在java堆中的DirectByteBuffer对象最为这块内存的引用
 * 这样可以显著提高性能，因为避免了Java堆和Native栈中来回复制数据
 * ByteBuffer.allocate(capability)：第一种分配JVM堆内存，属于GC管辖。由于需要copy所以速度相对较慢
 * ByteBuffer.allocateDirect(capability)：第二种分配本地内存，不属于GC管辖，速度相对较快
 * <p>
 * 但由于不断分配内存，堆内存不怎么使用就不会执行GC，DirectByteBuffer就不会被回收，这时候堆内存充足，本地内存没了
 * 再次尝试分配内存时会报出该异常
 *
 * Exception in thread "main" java.lang.OutOfMemoryError: Direct buffer memory
 */
public class BK_DirectBufferMemory {
    public static void main(String[] args) {
        System.out.println("配置的" + sun.misc.VM.maxDirectMemory() / (double) 1024 / 1024 + "MB");
        ByteBuffer.allocateDirect(6 * 1024 * 1024);
    }
}
