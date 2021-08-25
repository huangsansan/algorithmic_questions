package com.hhb.juc.M08.D27;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * @description:
 * @Author: huangsan
 * @Date: 2020/8/27 1:50 下午
 */
public class ThreadPoolDeamonDemo {
    public static void main(String[] args) {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(5, 20, 2, TimeUnit.SECONDS, new SynchronousQueue<>());

        List<Future> list = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            Future<String> future = executor.submit(() -> {
                try {
                    System.out.println(Thread.currentThread().getName());
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return "1";
            });
            list.add(future);
        }

        for (Future future : list) {
            while (true) {
                if (future.isDone()) {
                    try {
                        System.out.println(future.get());
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    } finally {
                        future.cancel(true);
                    }
                    break;
                }
            }
        }

        executor.shutdown();
    }
}
