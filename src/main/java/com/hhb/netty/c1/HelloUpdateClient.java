package com.hhb.netty.c1;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringEncoder;
import lombok.extern.slf4j.Slf4j;

import java.net.InetSocketAddress;

/**
 * @description:
 * 需要注意：在idea的debug模式下的断点有两种模式
 * @Author: huangsan
 * @Date: 2021/8/25 下午6:05
 */
@Slf4j
public class HelloUpdateClient {
    public static void main(String[] args) throws InterruptedException {
        //1、启动类
        ChannelFuture channelFuture = new Bootstrap()
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
                .connect(new InetSocketAddress("localhost", 8080));

        //同步方式
//        channelFuture.sync().channel().writeAndFlush("123");
        //异步方式
        channelFuture.addListener(new ChannelFutureListener() {
            @Override
            //在nio线程连接建立好以后会调用operationComplete
            public void operationComplete(ChannelFuture future) throws Exception {
                Channel channel = future.channel();
                channel.writeAndFlush("456");
            }
        });
    }
}
