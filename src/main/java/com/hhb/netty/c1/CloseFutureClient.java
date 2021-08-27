package com.hhb.netty.c1;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import lombok.extern.slf4j.Slf4j;

import java.net.InetSocketAddress;
import java.util.Scanner;

/**
 * @description: 键盘键入值，当是q的时候退出
 * @Author: huangsan
 * @Date: 2021/8/25 下午9:07
 */
@Slf4j
public class CloseFutureClient {
    public static void main(String[] args) throws InterruptedException {
        EventLoopGroup group = new NioEventLoopGroup();
        ChannelFuture channelFuture = new Bootstrap()
                .group(group)
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel nc) throws Exception {
                        nc.pipeline().addLast(new LoggingHandler(LogLevel.DEBUG))
                                .addLast(new StringEncoder());
                    }
                })
                .connect(new InetSocketAddress("localhost", 8080));
        Channel channel = channelFuture.sync().channel();
        log.debug("{}", channel);
        //键盘键入
        new Thread(() -> {
            Scanner scanner = new Scanner(System.in);
            while (true) {
                String line = scanner.nextLine();
                if (line.equals("q")) {
                    channel.close();
                    log.debug("关闭之后进行一些操作。。。");//不一定在close之后，因为close是异步的。
                    break;
                }
                channel.writeAndFlush(line);
            }
        }, "jr").start();

        //先将closeFuture获取到，变成同步之后在进行一些操作即可。
        ChannelFuture closeFuture = channel.closeFuture();
//        closeFuture.sync();
//        log.debug("关闭之后进行一些操作2。。。");
        closeFuture.addListener(new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture future) throws Exception {
                log.debug("关闭之后进行一些操作3。。。");
                group.shutdownGracefully();
            }
        });
    }
}
