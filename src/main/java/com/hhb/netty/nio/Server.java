package com.hhb.netty.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * @description:
 * @Author: huangsan
 * @Date: 2021/8/24 下午4:07
 */
public class Server {
    public static void main(String[] args) throws IOException {

        ServerSocketChannel ssc = ServerSocketChannel.open();
        ssc.configureBlocking(false);//变更为非阻塞，默认是阻塞的
        ssc.bind(new InetSocketAddress(8080));
        ByteBuffer buffer = ByteBuffer.allocate(10);
        //accept就是建立与客户端的通信，SocketChannel用来和客户端通信
        List<SocketChannel> list = new ArrayList<>();
        while (true) {
            SocketChannel sc = ssc.accept();//会阻塞
            if (sc != null) {
                sc.configureBlocking(false);
                System.out.println("当前连接：" + sc);
                list.add(sc);
            }
            //遍历所有的客户端去进行操作
            for (SocketChannel channel : list) {
                int read = channel.read(buffer);//原来会阻塞，更改configureBlocking不会
                if (read > 0) {
                    //切换为读模式
                    buffer.flip();
                    String message = StandardCharsets.UTF_8.decode(buffer).toString();
                    System.out.println("消息为：" + message);
                    buffer.clear();
                }
            }
        }
    }
}
