package com.hhb.netty.c1;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoop;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import lombok.extern.slf4j.Slf4j;

/**
 * @description:
 * @Author: huangsan
 * @Date: 2021/8/24 下午9:08
 */
@Slf4j
public class HelloServe {
    public static void main(String[] args) {
        //1、启动类，负责组装netty组件，启动服务器
        new ServerBootstrap()
                //2、类似于之前的BossEventLoop和WorkerEventLoop（主要是初始化选择器和线程）
                .group(new NioEventLoopGroup())
                //3、选择服务器上的ServerSocketChannel实现
                .channel(NioServerSocketChannel.class)
                //4、boss负责处理连接，worker（child）负责处理读写。决定了worker（child）能执行哪些操作（handler）
                .childHandler(
                        //5、通道的初始化，ChannelInitializer主要负责加载别的handler
                        new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel nc) throws Exception {
                        //6、添加具体的handler
                        nc.pipeline().addLast(new StringDecoder());//将ByteBuf转化为字符串（解码）
                        nc.pipeline().addLast(new ChannelInboundHandlerAdapter() {//自定义handler，打印输出
                            @Override
                            public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                                System.out.println(msg);
                            }
                        });
                    }
                })
                //7绑定端口
                .bind(8080);
    }
}
