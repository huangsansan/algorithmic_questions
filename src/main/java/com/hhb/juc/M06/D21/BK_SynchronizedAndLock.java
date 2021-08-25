package com.hhb.juc.M06.D21;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 *
 * synchronized与lock的区别：
 * 1、原始构造：
 *    synchronized：是java关键字属于jvm层面，底层实现是monitor，查看class文件可以看到，增加synchronized的地方其实就是
 *                  monitorenter（其实wait/notify依赖于monitor对象，只有在同步代码块或方法中才能调用wait/notify）
 *                  monitorexit（会有两个monitorexit，一个是异常退出一个是正常退出，所以不会出现死锁）
 *    lock：是具体的类，API层面的锁
 * 2、使用方法：
 *    synchronized：不需要用户去手动释放锁，当synchronized代码执行完，系统会自动释放锁
 *    lock：需要手动释放，如果没有释放就容易造成死锁，需要lock/unlock配合try/finally来实现
 * 3、等待是否可中断：
 *    synchronized：不可中断，除非抛出异常或正常结束
 *    reentrylock：1、设置超时方法trylock（long time，timeunit unit）
 *                 2、lockInterruptibly（）代码块中调用interrupt（）方法可中断
 * 4、加锁是否公平：
 *    reentrylock可以实现公平锁
 * 5、锁绑定多个条件condition
 *    synchronized：不支持
 *    reentrylock：可以实现分组唤醒线程，指定精确唤醒。
 *
 *
 * 验证第五点：
 * 题目：多线程之间顺序调用，A线程调用5次，B线程调用10次，C线程调用15次，a-b-c循环5次
 *
 */
public class BK_SynchronizedAndLock {

//    public volatile int count = 5;

    public volatile int flag=1;

    public static ReentrantLock lock = new ReentrantLock();
    public Condition ac = lock.newCondition();
    public Condition bc = lock.newCondition();
    public Condition cc = lock.newCondition();

    public void doSomethingA() {
        lock.lock();
        try {
            while (flag!=1){
                ac.await();
            }
            for (int i = 1; i <= 5; i++) {
                System.out.println(Thread.currentThread().getName() + "执行了" + i + "次");
            }
            flag=2;
            bc.signal();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
    public void doSomethingB() {
        lock.lock();
        try {
            while (flag!=2){
                bc.await();
            }
            for (int i = 1; i <= 10; i++) {
                System.out.println(Thread.currentThread().getName() + "执行了" + i + "次");
            }
            flag=3;
            cc.signal();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
    public void doSomethingC() {
        lock.lock();
        try {
            while (flag!=3){
                cc.await();
            }
            for (int i = 1; i <= 15; i++) {
                System.out.println(Thread.currentThread().getName() + "执行了" + i + "次");
            }
            flag=1;
            ac.signal();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public static void main(String[] args) {
//        BK_SynchronizedAndLock synchronizedAndLock = new BK_SynchronizedAndLock();
//        new Thread(() -> {
//            for (int j = 1; j <= 5; j++) {
//                synchronizedAndLock.doSomethingA();
//            }
//        }, "A").start();
//
//        new Thread(() -> {
//            for (int j = 1; j <= 5; j++) {
//                synchronizedAndLock.doSomethingB();
//            }
//        }, "B").start();
//
//        new Thread(() -> {
//            for (int j = 1; j <= 5; j++) {
//                synchronizedAndLock.doSomethingC();
//            }
//        }, "C").start();
        
        lock.lock();
        
//        new Thread(() -> {
////            try {
////                System.out.println(lock.tryLock(5, TimeUnit.SECONDS));
////                System.out.println("我获取了锁");
////            } catch (InterruptedException e) {
////                e.printStackTrace();
////            } finally {
////                System.out.println("我释放了");
////                lock.unlock();
////            }
////        }, "children1").start();
        Thread ct2 = new Thread(() -> {
            try {
                lock.lockInterruptibly();
                System.out.println("我是一个等待的代码");
            } catch (InterruptedException e) {
                System.out.println("我竟然被中断了，我擦。。。");
            }
        }, "children2");

        ct2.start();

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ct2.interrupt();


        lock.unlock();
        System.out.println("main结束");

    }
}
