package BK2021.M08.D23;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;

/**
 * @description: 黏包与半包，当一次网络通信传递数据的过程中将多个数据放在一个包中发送叫黏包，
 * 当我们缓冲区不够存储了，创建一个新的包来发送剩余的部分，导致发送的内容被截断，这种现象叫半包。
 * @Author: huangsan
 * @Date: 2021/8/23 下午4:47
 */
public class ByteBufferDemo {

    public static void main(String[] args) {
        System.out.println(Paths.get("words.txt").getRoot().toString());

        //黏包与半包的demo
//        ByteBuffer buffer = ByteBuffer.allocate(32);
//        buffer.put("Hello,word\nI'am hhb\nHo".getBytes());
//        split(buffer);
//        buffer.put("w are you\n".getBytes());
//        split(buffer);


//        fsRead();
//        jzWrite();
//        ByteBuffer buffer = ByteBuffer.allocate(10);
//        buffer.put(new byte[]{'a','b','c','d'});
//        buffer.flip();
//        System.out.println("0: "+(char)buffer.get());
//        System.out.println("1: "+(char)buffer.get());
//        buffer.mark();
//        System.out.println("2: "+(char)buffer.get());
//        System.out.println("3: "+(char)buffer.get());
//        buffer.reset();
//        System.out.println("9: "+(char)buffer.get());
//        ByteBuffer hello_word = StandardCharsets.UTF_8.encode("hello word");
//        System.out.println("00: "+(char)hello_word.get());
//        ByteBuffer wrap = ByteBuffer.wrap(new byte[]{'a', 'b', 'c', 'd'});

    }

    public static void split(ByteBuffer buffer) {
        //首先改成读模式
        buffer.flip();
        for (int i = 0; i < buffer.limit(); i++) {
            //get i 不会改变position的位置
            if (buffer.get(i) == '\n') {
                int index = i - buffer.position() + 1;
                ByteBuffer target = ByteBuffer.allocate(index);
                for (int j = 0; j < index; j++) {
                    //每次读都会改变position的位置，所以可以用j=0
//                    target.put(buffer.get());
                    System.out.print((char)buffer.get());
                }
                System.out.println();
            }
        }
        buffer.compact();//更改为写模式，但是会保留之前未读的部分，区别与clear
    }


    /**
     * @Description: 分散读取的方式，读取一个文件
     * @Author: huangsan
     * @Date: 2021/8/23
     */
    public static void fsRead() {
        try {
            FileChannel channel = new RandomAccessFile("words.txt", "r").getChannel();
            ByteBuffer b1 = ByteBuffer.allocate(3);
            ByteBuffer b2 = ByteBuffer.allocate(3);
            ByteBuffer b3 = ByteBuffer.allocate(5);
            channel.read(new ByteBuffer[]{b1, b2, b3});
            b1.flip();
            b2.flip();
            b3.flip();

            System.out.println(StandardCharsets.UTF_8.decode(b1).toString());
            System.out.println(StandardCharsets.UTF_8.decode(b2).toString());
            System.out.println(StandardCharsets.UTF_8.decode(b3).toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * @Description: 集中写
     * @Author: huangsan
     * @Date: 2021/8/23
     */
    public static void jzWrite() {
        ByteBuffer b1 = ByteBuffer.wrap(new byte[]{'h', 'e', 'l', 'l', 'o'});
        ByteBuffer b2 = ByteBuffer.allocate(3);
        b2.put(new byte[]{'h', 'h', 'b'});
        b2.flip();//切换为读模式，不然下面写的时候读取不到数据。
        ByteBuffer b3 = StandardCharsets.UTF_8.encode("hahaha");
        FileChannel channel = null;
        try {
            channel = new RandomAccessFile("words.txt", "rw").getChannel();
            channel.write(new ByteBuffer[]{b1, b2, b3});
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
