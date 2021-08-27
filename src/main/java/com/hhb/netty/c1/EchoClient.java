package com.hhb.netty.c1;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringEncoder;
import lombok.extern.slf4j.Slf4j;

import java.net.InetSocketAddress;
import java.nio.charset.Charset;
import java.util.Scanner;

/**
 * @description:
 * @Author: huangsan
 * @Date: 2021/8/26 下午8:47
 */
@Slf4j
public class EchoClient {
    public static void main(String[] args) throws InterruptedException {
        EventLoopGroup group = new NioEventLoopGroup();
        //1、启动类
        Channel channel = new Bootstrap()
                //2、添加EventLoop
                .group(group)
                //3、选择客户端channel实现
                .channel(NioSocketChannel.class)
                //4、添加处理器handler
                .handler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel nc) throws Exception {
                        nc.pipeline().addLast(new ChannelInboundHandlerAdapter() {
                            @Override
                            public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                                ByteBuf buf = (ByteBuf) msg;
                                log.info("char1:{}", buf.toString(Charset.defaultCharset()));
                            }
                        });
                    }
                })
                //5、连接到服务器
                .connect(new InetSocketAddress("localhost", 8080)).sync().channel();

        new Thread(() -> {
            Scanner scanner = new Scanner(System.in);
            while (true) {
                String line = scanner.nextLine();
                if (line.equals("q")) {
                    channel.close();
                    log.debug("关闭之后进行一些操作。。。");//不一定在close之后，因为close是异步的。
                    break;
                }
                channel.writeAndFlush(ByteBufAllocator.DEFAULT.buffer(10).writeBytes(line.getBytes()));
            }
        }, "echo").start();
        ChannelFuture closeFuture = channel.closeFuture();
        closeFuture.sync();
        group.shutdownGracefully();
    }
}
