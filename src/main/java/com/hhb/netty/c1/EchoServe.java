package com.hhb.netty.c1;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.Charset;

/**
 * @description:
 * @Author: huangsan
 * @Date: 2021/8/26 下午8:47
 */
@Slf4j
public class EchoServe {
    public static void main(String[] args) {
        new ServerBootstrap()
                .group(new NioEventLoopGroup(), new NioEventLoopGroup(2))
                .channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel nc) throws Exception {
                        //6、添加具体的handler
                        nc.pipeline().addLast("handler1", new ChannelInboundHandlerAdapter() {//自定义handler，打印输出
                            @Override
                            public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                                ByteBuf buf = (ByteBuf) msg;
                                log.info("服务端接收信息：{}", buf);
                                log.info("h1:{}", buf.toString(Charset.defaultCharset()));
                                ctx.writeAndFlush(buf);
                                ctx.fireChannelRead(msg);
                            }
                        });
                    }
                })
                //7绑定端口
                .bind(8080);
    }
}
