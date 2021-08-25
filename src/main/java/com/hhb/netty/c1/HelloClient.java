package com.hhb.netty.c1;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringEncoder;
import lombok.extern.slf4j.Slf4j;

import java.net.InetSocketAddress;

/**
 * @description:
 * @Author: huangsan
 * @Date: 2021/8/24 下午9:08
 */
@Slf4j
public class HelloClient {
    public static void main(String[] args) throws InterruptedException {
        //1、启动类
        new Bootstrap()
                //2、添加EventLoop
                .group(new NioEventLoopGroup())
                //3、选择客户端channel实现
                .channel(NioSocketChannel.class)
                //4、添加处理器handler
                .handler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel nc) throws Exception {
                        nc.pipeline().addLast(new StringEncoder());
                    }
                })
                //5、连接到服务器
                .connect(new InetSocketAddress("localhost", 8080))
                .sync()
                .channel()
                //6、向服务器发送数据
                .writeAndFlush("hello netty");
    }
}
