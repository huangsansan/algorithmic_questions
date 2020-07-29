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
 * hhb理解：corePoolSize默认给0，每次去扩容
 * maximumPoolSize的数量考虑是哪种情况
 * --CPU密集型：该任务需要大量的运算，没有阻塞，CPU全速运行。这种类型的任务只有到真正的多核CPU才能得到加速（通过多线程），单核CPu无论配置多少都是一个CPu在运算。
 *             一般公式CPU+1个线程的线程池
 * --IO密集型：有两种，第二种是大厂经验配置
 *          1、IO密集型的任务的线程并不是一直在进行运算，有的IO等待。所以配置尽可能多的线程数。一般CPU核数*2的线程数
 *          2、配置为CPU核数/（1-阻塞系数），阻塞系数在0.8-0.9之间  例如8核 8/（1-0.9）= 80个线程数
 *
 */
public class BK_Executors {

    public static void main(String[] args) {
//        ExecutorService threadPool = Executors.newFixedThreadPool(5);
//        ExecutorService threadPool = Executors.newSingleThreadExecutor();
        ExecutorService threadPool = Executors.newCachedThreadPool();

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
