package BK2020.M07.D26;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * newFixedThreadPool：执行长期任务，性能比较好
 * newSingleThreadExecutor：一个任务一个任务的执行
 * newCachedThreadPool：执行短期异步小程序或者负载较轻的服务
 *
 * 注意：以上三种在开发中均不使用
 * newFixedThreadPool与newSingleThreadExecutor 使用的阻塞队列为LinkedBlockingQueue，允许等待队列长度为Integer.MAX_VALUE，可能造成堆积大量请求，造成OOM
 * newCachedThreadPool 默认maximumPoolSize的size为Integer.MAX_VALUE，允许创建线程数两大，可能会创建大量线程，产生OOM
 *
 * threadPoolExecutor 的七大参数：
 * 1、corePoolSize：默认的常驻核心线程数
 * 2、maximumPoolSize：默认支持的最大的线程数
 * 3、keepAliveTime：与unit搭配，当请求量下降的时候关闭多余的线程，直到小于corePoolSize
 * 4、unit：时间单位
 * 5、BlockingQueue<Runnable> workQueue：阻塞队列，理解为候客区。任务队列，存放等待的任务
 * 6、ThreadFactory threadFactory：线程工厂，用于创建线程，一般是默认
 * 7、RejectedExecutionHandler handler：拒绝策略，当达到最大的线程数，且阻塞队列也满了的时候。一种拒绝策略
 *
 */
public class BK_Executors {

    public static void main(String[] args) {
        ExecutorService threadPool = Executors.newFixedThreadPool(5);
//        ExecutorService threadPool = Executors.newSingleThreadExecutor();
//        ExecutorService threadPool = Executors.newCachedThreadPool();

        try {
            for (int i = 1; i <= 10; i++) {
                threadPool.execute(() -> {
                    System.out.println(Thread.currentThread().getName() + "执行了");
                });
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            threadPool.shutdown();
        }
    }
}
