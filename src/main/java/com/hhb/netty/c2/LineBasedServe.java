package com.hhb.netty.c2;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.FixedLengthFrameDecoder;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * @description:
 * @Author: huangsan
 * @Date: 2021/8/27 下午5:24
 */
@Slf4j
public class LineBasedServe {
    public static void main(String[] args) {
        new ServerBootstrap()
                .group(new NioEventLoopGroup(), new NioEventLoopGroup(2))
                .channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel nc) throws Exception {
//                        nc.pipeline().addLast(new LineBasedFrameDecoder(210) {
//                            @Override
//                            public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
//                                ByteBuf byteBuf = (ByteBuf) msg;
//                                log.info("ByteBuf:{}", byteBuf);
//                                super.channelRead(ctx, msg);
//                            }
//                        });
                        ByteBuf b1 = ByteBufAllocator.DEFAULT.buffer(2).writeBytes("-".getBytes());
                        ByteBuf b2 = ByteBufAllocator.DEFAULT.buffer(2).writeBytes("+".getBytes());
                        nc.pipeline().addLast(new DelimiterBasedFrameDecoder(30, b1,b2) {
                            @Override
                            public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                                ByteBuf byteBuf = (ByteBuf) msg;
                                log.info("ByteBuf:{}", byteBuf);
                                super.channelRead(ctx, msg);
                            }
                        });
                        nc.pipeline().addLast(new LoggingHandler(LogLevel.DEBUG));
                    }
                })
                //7绑定端口
                .bind(8080);
    }
}
