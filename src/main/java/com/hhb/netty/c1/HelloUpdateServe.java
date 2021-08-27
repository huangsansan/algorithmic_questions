package com.hhb.netty.c1;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.Charset;
import java.util.Date;

/**
 * @description: 针对EventLoopGroup有两点细分
 * @Author: huangsan
 * @Date: 2021/8/25 下午6:05
 */
@Slf4j
public class HelloUpdateServe {
    public static void main(String[] args) {

        //创建一个新的线程组去处理比较耗时的handler
        EventLoopGroup defaultEventLoopGroup = new DefaultEventLoopGroup();

        new ServerBootstrap()
                //细分1：这里的EventLoopGroup可以传递parent和children，所以将parent和children分开来设置。
                // parent只负责serverSocketChannel上的accept操作，children只负责socketChannel上的读写
                .group(new NioEventLoopGroup(), new NioEventLoopGroup(1))
                .channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel nc) throws Exception {
                        //6、添加具体的handler
                        nc.pipeline().addLast("handler1", new ChannelInboundHandlerAdapter() {//自定义handler，打印输出
                            @Override
                            public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                                ByteBuf buf = (ByteBuf) msg;
                                log.info("char1:{}", buf.toString(Charset.defaultCharset()));
                                ctx.fireChannelRead(msg);
                            }
                        })
                                //在一个channel的handler管道中，有很多handler需要处理，如果某一个handler操作费时，会影响eventloop去selector执行其他
                                // 的channel，所以可以将耗时的handler交给新的EventLoopGroup去处理，在addLast中指定新的组
                                .addLast(defaultEventLoopGroup, "handler2", new ChannelInboundHandlerAdapter() {//自定义handler，打印输出
                            @Override
                            public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                                ByteBuf buf = (ByteBuf) msg;
                                log.info("char2:{}", buf.toString(Charset.defaultCharset()));
                            }
                        });
                    }
                })
                //7绑定端口
                .bind(8080);
    }
}
