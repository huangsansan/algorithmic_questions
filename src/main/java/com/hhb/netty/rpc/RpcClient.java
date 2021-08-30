package com.hhb.netty.rpc;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import lombok.extern.slf4j.Slf4j;

import java.net.InetSocketAddress;
import java.util.Random;

/**
 * @description:
 * @Author: huangsan
 * @Date: 2021/8/30 下午7:31
 */
@Slf4j
public class RpcClient {
    public static void main(String[] args) throws InterruptedException {
        LoggingHandler LOGGING_HANDLER = new LoggingHandler(LogLevel.DEBUG);
        MessageCodecSharable MESSAGE_HANDLER = new MessageCodecSharable();

        RpcResponseMessageHandler responseMessageHandler = new RpcResponseMessageHandler();

        NioEventLoopGroup group = new NioEventLoopGroup(2);
        try {
            Bootstrap bootstrap = new Bootstrap()
                    //2、添加EventLoop
                    .group(group)
                    //3、选择客户端channel实现
                    .channel(NioSocketChannel.class)
                    //4、添加处理器handler
                    .handler(new ChannelInitializer<NioSocketChannel>() {
                        @Override
                        protected void initChannel(NioSocketChannel nc) throws Exception {
                            nc.pipeline().addLast(new LengthFieldBasedFrameDecoder(1024, 12, 4, 0, 0));
                            nc.pipeline().addLast(LOGGING_HANDLER);
                            nc.pipeline().addLast(MESSAGE_HANDLER);
                            //业务部分
                            nc.pipeline().addLast(responseMessageHandler);
                        }
                    });

            Channel channel = bootstrap.connect(new InetSocketAddress("localhost", 8080)).sync().channel();

            ChannelFuture future = channel.writeAndFlush(new RpcRequestMessage(
                    1,
                    "com.hhb.netty.rpc.HelloService",
                    "helloWord",
                    String.class,
                    new Class[]{String.class},
                    new Object[]{"黄三"}
            ));
            future.addListener(promise -> {
                if (!promise.isSuccess()) {
                    throw new RuntimeException(promise.cause());
                 }
            });

        } catch (Exception e) {
            e.printStackTrace();
        } finally {

        }
    }
}
