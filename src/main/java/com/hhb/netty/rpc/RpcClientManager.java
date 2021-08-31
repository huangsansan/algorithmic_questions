package com.hhb.netty.rpc;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.util.concurrent.DefaultPromise;
import io.netty.util.concurrent.FutureListener;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Proxy;
import java.net.InetSocketAddress;
import java.util.concurrent.FutureTask;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @description:
 * @Author: huangsan
 * @Date: 2021/8/30 下午7:31
 */
@Slf4j
public class RpcClientManager {


    private static AtomicInteger atomicInteger = new AtomicInteger(1);

    public volatile static Channel channel;


    public static void main(String[] args) throws InterruptedException {
        HelloService proxy = getProxy(HelloService.class);
        new Thread(() -> {
            String s1 = proxy.helloWord("金高银");
            System.out.println("返回：" + s1);
        }, "t1").start();
        new Thread(() -> {
            String s2 = proxy.helloWord("孔刘");
            System.out.println("返回：" + s2);
        }, "t2").start();
        new Thread(() -> {
            String s3 = proxy.helloWord("黄三");
            System.out.println("返回：" + s3);
        }, "t3").start();
    }

    /**
     * 动态代理实现service方法的自动封装message
     */
    public static <T> T getProxy(Class<T> service) {
        Object o = Proxy.newProxyInstance(service.getClassLoader(), new Class[]{service}, (proxy, method, arg) -> {
            int increment = atomicInteger.getAndIncrement();
            System.out.println("开始执行了：" + increment);
            RpcRequestMessage rpcRequestMessage = new RpcRequestMessage(
                    increment,
                    service.getName(),
                    method.getName(),
                    method.getReturnType(),
                    method.getParameterTypes(),
                    arg
            );
            ChannelFuture future = getChannel().writeAndFlush(rpcRequestMessage);

            //指定异步接收结果的线程，将当前的eventLoop传递进去，当异步写数据的时候在通过map拿到该eventLoop进行写数据。
            DefaultPromise<Object> promise = new DefaultPromise<>(getChannel().eventLoop());
            RpcResponseMessageHandler.map.put(increment, promise);

            //等待promise中有数据
            promise.await();
            System.out.println("有结果了：" + increment);
            if (promise.isSuccess()) {
                return promise.getNow();
            } else {
                try {
                    promise.get();
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    return "结果异常";
                }
            }
        });
        return (T) o;
    }

    /**
     * 双端检测机制 单例模式创建channel
     */
    public static Channel getChannel() {
        if (channel == null) {
            synchronized (RpcClientManager.class) {
                if (channel == null) {
                    initChannel();
                }
            }
        }
        return channel;
    }

    public static void initChannel() {
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
//                            nc.pipeline().addLast(LOGGING_HANDLER);
                            nc.pipeline().addLast(MESSAGE_HANDLER);
                            //业务部分
                            nc.pipeline().addLast(responseMessageHandler);
                        }
                    });

            channel = bootstrap.connect(new InetSocketAddress("localhost", 8080)).sync().channel();

            channel.closeFuture().addListener(future -> {
                group.shutdownGracefully();
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
