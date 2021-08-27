package com.hhb.netty.c1;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.Charset;

/**
 * @description:
 * @Author: huangsan
 * @Date: 2021/8/26 上午10:28
 */
@Slf4j
public class TestPipeline {
    public static void main(String[] args) {
        new ServerBootstrap()
                .group(new NioEventLoopGroup(), new NioEventLoopGroup(2))
                .channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel nc) throws Exception {
                        nc.pipeline().addLast("handler1", new ChannelInboundHandlerAdapter() {//自定义handler，打印输出
                            @Override
                            public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                                log.info("执行了handler1");
                                ctx.fireChannelRead(msg);
                            }
                        }).addLast("handler1.5", new ChannelOutboundHandlerAdapter() {//自定义handler，打印输出
                            @Override
                            public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
                                log.info("执行了handler1.5-出战");
                                super.write(ctx, msg, promise);
                            }
                        }).addLast("handler2", new ChannelInboundHandlerAdapter() {//自定义handler，打印输出
                            @Override
                            public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                                log.info("执行了handler2");
                                nc.writeAndFlush(ctx.alloc().buffer().writeBytes(new byte[]{1,2}));
                                ctx.fireChannelRead(msg);
                            }
                        }).addLast("handler2.5", new ChannelInboundHandlerAdapter() {//自定义handler，打印输出
                            @Override
                            public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                                log.info("执行了handler2.5");
                                ctx.fireChannelRead(msg);
                            }
                        }).addLast("handler3", new ChannelOutboundHandlerAdapter() {//自定义handler，打印输出
                            @Override
                            public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
                                log.info("执行了handler3-出战");
                                super.write(ctx, msg, promise);
                            }
                        }).addLast("handler4", new ChannelOutboundHandlerAdapter() {//自定义handler，打印输出
                            @Override
                            public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
                                log.info("执行了handler4-出战");
                                super.write(ctx, msg, promise);
                            }
                        }).addLast("handler5", new ChannelInboundHandlerAdapter() {//自定义handler，打印输出
                            @Override
                            public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                                log.info("执行了handler5");
                                ctx.fireChannelRead(msg);
                            }
                        }).addLast("handle6", new ChannelInboundHandlerAdapter() {//自定义handler，打印输出
                            @Override
                            public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                                log.info("执行了handler6");
                            }
                        });
                        ;
                    }
                })
                //7绑定端口
                .bind(8080);
    }
}
