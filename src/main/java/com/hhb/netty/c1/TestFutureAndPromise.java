package com.hhb.netty.c1;

import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.EventLoop;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.util.concurrent.DefaultPromise;
import io.netty.util.concurrent.FutureListener;
import io.netty.util.concurrent.GenericFutureListener;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.*;

/**
 * 这里主要是验证netty中的future与promise两个方法
 */
@Slf4j
public class TestFutureAndPromise {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
//        jdkFuture();
//        nettyFuture();
//        nettyPromise();
    }

    private static void nettyPromise() throws InterruptedException, ExecutionException {
        EventLoopGroup group = new NioEventLoopGroup(1);
        EventLoop eventLoop = group.next();
        DefaultPromise<Integer> promise = new DefaultPromise<>(eventLoop);
        new Thread(() -> {
            log.info("thread run ....");
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            promise.setSuccess(1000);
//            promise.setFailure()
        }, "1").start();
        log.info("main run ....");
        promise.sync();
        log.info("main get result ....");
        System.out.println(promise.get());
    }

    private static void nettyFuture() {
        EventLoopGroup group = new NioEventLoopGroup(1);
        EventLoop next = group.next();
        io.netty.util.concurrent.Future<Integer> future = next.submit(() -> {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return 222222;
        });
//        future.sync();
//        System.out.println(future.getNow());
        future.addListener(new FutureListener() {
            @Override
            public void operationComplete(io.netty.util.concurrent.Future future) throws Exception {
                System.out.println(future.get());
            }
        });
    }

    private static void jdkFuture() throws InterruptedException, ExecutionException {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Future<Integer> future = executor.submit(() -> {
            return 1;
        });
        Integer integer = future.get();
        System.out.println(integer);
        executor.shutdown();
    }
}
