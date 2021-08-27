package com.hhb.netty.c2;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import lombok.extern.slf4j.Slf4j;

import java.net.InetSocketAddress;
import java.util.Random;

/**
 * @description:
 * @Author: huangsan
 * @Date: 2021/8/27 下午5:24
 */
@Slf4j
public class LineBasedClient {
    public static void main(String[] args) throws InterruptedException {
        NioEventLoopGroup group = new NioEventLoopGroup(2);
        new Bootstrap()
                //2、添加EventLoop
                .group(group)
                //3、选择客户端channel实现
                .channel(NioSocketChannel.class)
                //4、添加处理器handler
                .handler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel nc) throws Exception {
                        nc.pipeline().addLast(new LoggingHandler(LogLevel.DEBUG));
//                        nc.pipeline().addLast(new ChannelInboundHandlerAdapter() {
//                            @Override
//                            public void channelActive(ChannelHandlerContext ctx) throws Exception {
//                                ByteBuf buffer = ctx.alloc().buffer(10);
//                                for (int i = 0; i < 10; i++) {
//                                    StringBuffer sb = new StringBuffer();
//                                    int count = new Random().nextInt(200);
//                                    log.info("基数：{}", count);
//                                    for (int j = 0; j < count; j++) {
//                                        sb.append("a");
//                                    }
//                                    sb.append("\n");
//                                    buffer.writeBytes(sb.toString().getBytes());
//                                }
//                                ctx.writeAndFlush(buffer);
//                                log.info("ByteBuf:{}", buffer);
//                                super.channelActive(ctx);
//                            }
//                        });
                        nc.pipeline().addLast(new ChannelInboundHandlerAdapter() {
                            @Override
                            public void channelActive(ChannelHandlerContext ctx) throws Exception {
                                ByteBuf buffer = ctx.alloc().buffer(10);
                                for (int i = 0; i < 10; i++) {
                                    StringBuffer sb = new StringBuffer();
                                    int count = new Random().nextInt(20)+1;
                                    log.info("基数：{}", count);
                                    for (int j = 0; j < count; j++) {
                                        sb.append("a");
                                    }
                                    if (i % 2 == 0) {
                                        sb.append("-");
                                    } else {
                                        sb.append("+");
                                    }
                                    buffer.writeBytes(sb.toString().getBytes());
                                }
                                ctx.writeAndFlush(buffer);
                                log.info("ByteBuf:{}", buffer);
                                super.channelActive(ctx);
                            }
                        });
                    }
                }).connect(new InetSocketAddress("localhost", 8080)).sync().channel();
    }
}
