package BK2020.M06.D16;

import java.util.concurrent.locks.ReentrantLock;

/**
 * 可重入锁（递归锁）：指的是同一个线程外层函数获取到锁，内存递归函数仍需要该锁的代码可直接执行，也就是说线程可以进入任意一个它已经拥有的锁所同步着的代码块。
 * reentrylock 与 synchronized都是可重入锁（递归锁）
 */
public class BK_ReentryLock implements Runnable {

    ReentrantLock reentrantLock = new ReentrantLock();

    public void get1() {
        reentrantLock.lock();
        reentrantLock.lock();
        System.out.println(Thread.currentThread().getName() + "执行了get1");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        get2();
        reentrantLock.unlock();
        reentrantLock.unlock();
    }

    public void get2() {
        reentrantLock.lock();
        System.out.println(Thread.currentThread().getName() + "执行了get2");
        reentrantLock.unlock();
    }

    @Override
    public void run() {
        get1();
    }

    public static void main(String[] args) {
        BK_ReentryLock bkReentryLock = new BK_ReentryLock();

        //验证reentrantLock是可重入锁（递归锁）
//        new Thread(bkReentryLock, "t1").start();
//        new Thread(bkReentryLock, "t2").start();

        //验证synchronized是可重入锁
        new Thread(new Runnable() {
            @Override
            public void run() {
                get3();
            }
        },"t3").start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                get3();
            }
        },"t4").start();
    }

    public synchronized static void get3(){
        System.out.println(Thread.currentThread().getName() + "执行了get3");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        get4();
    }

    public synchronized static void get4(){
        System.out.println(Thread.currentThread().getName() + "执行了get4");
    }
}
