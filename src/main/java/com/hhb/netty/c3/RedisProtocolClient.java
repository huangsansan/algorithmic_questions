package com.hhb.netty.c3;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.redis.RedisEncoder;
import io.netty.handler.codec.redis.RedisMessage;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import lombok.extern.slf4j.Slf4j;

import java.net.InetSocketAddress;

@Slf4j
public class RedisProtocolClient {
    public static void main(String[] args) throws InterruptedException {
        NioEventLoopGroup group = new NioEventLoopGroup(1);
        byte[] LINE = {13, 10};//回车与换行
        new Bootstrap()
                .group(group)
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel nc) throws Exception {
                        nc.pipeline().addLast(new LoggingHandler(LogLevel.DEBUG));
                        nc.pipeline().addLast(new ChannelInboundHandlerAdapter() {
                            @Override
                            public void channelActive(ChannelHandlerContext ctx) throws Exception {
                                //set name huangsansan
                                //del name
                                //*2（有俩字符） $3（字符长度为3） del（字符内容） $4（字符长度4） name（字符内容）
                                ByteBuf buffer = ctx.alloc().buffer();
                                buffer.writeBytes("*2".getBytes());
                                buffer.writeBytes(LINE);
                                buffer.writeBytes("$3".getBytes());
                                buffer.writeBytes(LINE);
                                buffer.writeBytes("del".getBytes());
                                buffer.writeBytes(LINE);
                                buffer.writeBytes("$4".getBytes());
                                buffer.writeBytes(LINE);
                                buffer.writeBytes("name".getBytes());
                                buffer.writeBytes(LINE);
                                ctx.writeAndFlush(buffer);
                                super.channelActive(ctx);
                            }
                        });
                    }
                }).connect(new InetSocketAddress("localhost", 6379)).sync().channel();
    }
}
