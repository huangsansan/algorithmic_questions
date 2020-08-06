package BK2020.M06.D21;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

/**
 * @description: （升级版）阻塞队列实现生产者消费者
 * @Author: huangsan
 * @Date: 2020/6/22 11:36 上午
 */
public class BK_BlockingQueueCaseUpdate {


    public volatile boolean FLAG = true;

    public AtomicInteger atomicInteger = new AtomicInteger(10);

    public BlockingQueue<String> blockingQueue = null;

    public BK_BlockingQueueCaseUpdate(BlockingQueue<String> blockingQueue) {
        this.blockingQueue = blockingQueue;
    }

    public void doProducter() {
        try {
            String msg;
            while (FLAG) {
                msg = atomicInteger.incrementAndGet() + "";
                boolean offer = blockingQueue.offer(msg, 2L, TimeUnit.SECONDS);
                if (offer) {
                    System.out.println(Thread.currentThread().getName() + "生产了" + msg);
                } else {
                    System.out.println(Thread.currentThread().getName() + "生产超时");
                }
                TimeUnit.SECONDS.sleep(1);
            }
            System.out.println("boss叫停，生产者结束");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void doConsumer() {
        try {
            String msg;
            while (true) {//由FLAG改为true，验证消费者全部消费后，才停止
                TimeUnit.SECONDS.sleep(2);
                msg = blockingQueue.poll(2L, TimeUnit.SECONDS);
                if (msg == null || "".equals(msg)) {
                    FLAG = false;
                    System.out.println("获取消息超时了，消费者停止");
                    return;
                }
                System.out.println(Thread.currentThread().getName() + "消费了" + msg);
                System.out.println("队列中剩余的消息有：" + blockingQueue.toString());
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
//        try {
//            String msg;
//            while (FLAG) {
//                msg = blockingQueue.poll(2L, TimeUnit.SECONDS);
//                if (msg == null || "".equals(msg)) {
//                    FLAG = false;
//                    System.out.println("获取消息超时了，消费者停止");
//                    return;
//                }
//                System.out.println(Thread.currentThread().getName() + "消费了" + msg);
//            }
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
    }

    public void doStop() {
        this.FLAG = false;
    }

    public static void main(String[] args) {

//        BK_BlockingQueueCaseUpdate queueCaseUpdate = new BK_BlockingQueueCaseUpdate(new SynchronousQueue<>());
        BK_BlockingQueueCaseUpdate queueCaseUpdate = new BK_BlockingQueueCaseUpdate(new ArrayBlockingQueue<>(10));

        new Thread(() -> {
            queueCaseUpdate.doConsumer();
        }, "t2").start();

        new Thread(() -> {
            queueCaseUpdate.doProducter();
        }, "t1").start();


        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        queueCaseUpdate.doStop();
        System.out.println("boss 通知结束了");

    }
}
