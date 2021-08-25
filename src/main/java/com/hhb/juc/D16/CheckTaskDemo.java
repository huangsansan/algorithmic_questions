package com.hhb.juc.D16;

import java.util.concurrent.*;

/**
 * @description:
 * @Author: huangsan
 * @Date: 2020/9/16 8:36 下午
 */
public class CheckTaskDemo {
    public static void main(String[] args) {
        ThreadPoolExecutor taskCheckPool = new ThreadPoolExecutor(4,
                10,
                2,
                TimeUnit.SECONDS, new SynchronousQueue<>());

        CountDownLatch countDownLatch = new CountDownLatch(2);

        Future<Integer> future1 = taskCheckPool.submit(() -> {
            System.out.println(Thread.currentThread().getName() + "执行了");
            try {
                Thread.sleep(2000);
                taskCheckPool.shutdownNow();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //校验虚拟资源充足性
            System.out.println(Thread.currentThread().getName() + "执行结束了");
            countDownLatch.countDown();
            return 1;
        });
        Future<Integer> future2 = taskCheckPool.submit(() -> {
            System.out.println(Thread.currentThread().getName() + "执行了");
            //校验虚拟设备IP冲突
            try {
                Thread.sleep(123000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + "执行结束了");
            countDownLatch.countDown();
            return 2;
        });
        try {
            countDownLatch.await();
            Integer integer = future1.get();
            Integer integer2 = future2.get();

            System.out.println("1-"+integer);
            System.out.println("2-"+integer2);


            int c = (integer + integer2) % 4;
            System.out.println(c);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {

        }

        taskCheckPool.shutdown();

        System.out.println("over");
    }
}
