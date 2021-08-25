package com.hhb.juc.M06.D17;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @Description: 读写锁
 * <p>
 * 写锁：独占+原子 一个整体不可分割，且对其他可见
 * <p>
 * 读锁：【读锁与读锁不互斥，读锁与写锁互斥，写锁与写锁互斥】，这句话点出了读锁出现的必要性， 读和写是互斥的！也就是我在写的时候，你就不要读，能我写完了，
 * 你才能读！读锁就是为了和写锁互斥才存在的！
 * @Author: huangsan
 * @Date: 2020/6/17
 */
public class BK_rwlock {

    private volatile Map<String, Object> map = new HashMap<>();

    private ReentrantReadWriteLock reLock = new ReentrantReadWriteLock();

    public void put(String key, String value) {
        reLock.writeLock().lock();
        try {
            System.out.println(Thread.currentThread().getName() + "写入" + key);
            try {
                Thread.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            map.put(key, value);
            System.out.println(Thread.currentThread().getName() + "写入完成");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            reLock.writeLock().unlock();
        }
    }

    public void get(String key) {
        reLock.readLock().lock();
        try {
            System.out.println(Thread.currentThread().getName() + "读取开始");
//        try {
//            Thread.sleep(300);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
            System.out.println(Thread.currentThread().getName() + "读取完成，读取到：" + map.get(key));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            reLock.readLock().unlock();
        }
    }

    public static void main(String[] args) {

        BK_rwlock rwlock = new BK_rwlock();

        for (int i = 1; i <= 10; i++) {
            final int fi = i;
            new Thread(() -> {
                rwlock.put(fi + "", fi + "");
            }, "线程t1-" + String.valueOf(i)).start();
        }

//        try {
//            Thread.sleep(1000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }

        for (int i = 1; i <= 10; i++) {
            final int fi = i;
            new Thread(() -> {
                rwlock.get(fi + "");
            }, "线程t2-" + String.valueOf(i)).start();
        }
    }

}
