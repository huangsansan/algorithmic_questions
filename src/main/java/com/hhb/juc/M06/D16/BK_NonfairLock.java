package com.hhb.juc.M06.D16;

import java.util.concurrent.locks.ReentrantLock;

/**
 * 公平锁：不允许加塞，先获取锁先执行，按顺序执行。
 * 非公平锁：允许加塞，上来先获取锁，如果拿不到在去排队顺序执行
 *
 * 默认ReentrantLock与synchronized都是非公平锁
 */
public class BK_NonfairLock implements Runnable {

    private int cout = 100;

    private ReentrantLock lock = new ReentrantLock(true);//公平

    public static void main(String[] args) {

        BK_NonfairLock nonfairLock = new BK_NonfairLock();

        new Thread(nonfairLock, "t1").start();
        new Thread(nonfairLock, "t2").start();
        new Thread(nonfairLock, "t3").start();
    }

    @Override
    public void run() {
        while (true) {
            lock.lock();
            if (cout <= 100 && cout > 0) {
                cout--;
            } else {
                lock.unlock();
                break;
            }
            System.out.println(Thread.currentThread().getName() + "执行" + cout);
            lock.unlock();
        }
    }
}
