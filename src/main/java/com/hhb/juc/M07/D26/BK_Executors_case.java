package com.hhb.juc.M07.D26;

import java.util.concurrent.*;

/**
 * 手写一个线程池：
 *
 *
 * JDK默认的四种拒绝策略：
 * AbortPolicy：抛出异常的。直接抛出RejectedExecutionException
 * CallerRunsPolicy：不会抛弃任务也不会抛出异常，将任务回退给调用着执行
 * DiscardOldestPolicy：对其队列中最前面的任务（等待最久的任务），然后把当前任务加入队列，再次提交当前任务。
 * DiscardPolicy：丢弃最新的任务，不抛任何异常。（如果允许任务丢失，是最好的解决方案）
 */
public class BK_Executors_case {

    public static void main(String[] args) {
        System.out.println("CPU核数:" + Runtime.getRuntime().availableProcessors());

        ExecutorService threadPool = new ThreadPoolExecutor(2, 5, 1L,
                TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(3),
                Executors.defaultThreadFactory(),
                new ThreadPoolExecutor.AbortPolicy());

        try {
            for (int i = 1; i <= 16; i++) {
                threadPool.execute(() -> {
                    System.out.println(Thread.currentThread().getName() + "执行了");
                });
//                try {
//                    Thread.sleep(200);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            threadPool.shutdown();
        }
    }
}
