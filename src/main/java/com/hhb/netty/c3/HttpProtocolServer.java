package com.hhb.netty.c3;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.http.*;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import lombok.extern.slf4j.Slf4j;

import static io.netty.handler.codec.http.HttpHeaderNames.CONTENT_LENGTH;

/**
 * @description:
 * @Author: huangsan
 * @Date: 2021/8/30 下午2:50
 */
@Slf4j
public class HttpProtocolServer {
    public static void main(String[] args) {

        new ServerBootstrap()
                .group(new NioEventLoopGroup(), new NioEventLoopGroup(2))
                .channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel nc) throws Exception {
                        nc.pipeline().addLast(new LoggingHandler(LogLevel.DEBUG));
                        nc.pipeline().addLast(new HttpServerCodec());
                        nc.pipeline().addLast(new SimpleChannelInboundHandler<DefaultHttpRequest>() {
                            @Override
                            protected void channelRead0(ChannelHandlerContext ctx, DefaultHttpRequest msg) throws Exception {
                                System.out.println(msg.uri());
                                DefaultFullHttpResponse response = new DefaultFullHttpResponse(msg.protocolVersion(), HttpResponseStatus.OK);
                                response.content().writeBytes("<h1>hello http<h1>".getBytes());
                                response.headers().setInt(CONTENT_LENGTH, "<h1>hello http<h1>".toString().length());
                                ctx.writeAndFlush(response);
                            }
                        });
                    }
                })
                //7绑定端口
                .bind(8080);
    }
}
