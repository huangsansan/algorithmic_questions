package com.hhb.juc.M06.D21;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 生产者消费者模式
 * 题目：一个初始值为零的变量，两个线程对其交替操作，一个+1，一个-1，来5轮
 * <p>
 * 在判断的时候不使用if而用while，防止虚假唤醒
 * As in the one argument version, interrupts and spurious wakeups are
 * possible, and this method should always be used in a loop:
 * <pre>
 *     synchronized (obj) {
 *         while (&lt;condition does not hold&gt;)
 *             obj.wait();
 *         ... // Perform action appropriate to condition
 *     }
 * </pre>
 *
 * 在下面的demo中换成signal会产生死锁（等待验证）
 *
 * 问题：如果是公平锁，下面使用signal方法是没有问题的，如果是非公平锁，使用该方法会出现都WAITING的状态
 *      如果换成signalAll则不会出现任何问题
 */
public class BK_BlockingQueueCase {
    public static volatile int flag = 0;
    private static ReentrantLock lock = new ReentrantLock(false);
    private static Condition condition = lock.newCondition();


    public void doPro() {
        try {
            lock.lock();
            while (flag != 0) {
//                System.out.println("1123");
                condition.await();
            }
            flag++;
            System.out.println(Thread.currentThread().getName() + "生产者生产了" + flag);
            condition.signalAll();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public void doCon() {
        Object o = new Object();
        try {
            lock.lock();
            System.out.println(Thread.currentThread().getName() + "获取到了锁，并且flag为" + flag);
//            Thread.sleep(1000);
            while (flag == 0) {
//                System.out.println("2");
                condition.await();
            }
            flag--;
            System.out.println(Thread.currentThread().getName() + "消费者消费了" + flag);
            condition.signalAll();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            System.out.println(Thread.currentThread().getName() + "释放了锁");
            lock.unlock();
        }
    }


    public static void main(String[] args) {
        BK_BlockingQueueCase aCase = new BK_BlockingQueueCase();

        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                aCase.doPro();
            }
        }, "t1").start();

        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                aCase.doCon();
            }
        }, "t2").start();

        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                aCase.doPro();
            }
        }, "t3").start();

        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                aCase.doCon();
            }
        }, "t4").start();

    }
}
