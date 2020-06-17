package BK2020.M06.D17;

import java.util.concurrent.atomic.AtomicReference;

/**
 * @Description: 自旋锁，手写自旋锁实现，仿照Atomic底层实现
 *
 *         int var5;
 *         do {
 *             var5 = this.getIntVolatile(var1, var2);
 *         } while(!this.compareAndSwapInt(var1, var2, var5, var5 + var4));
 *
 *         return var5;
 *
 * @Author: huangsan
 * @Date: 2020/6/17
 */
public class BK_caslock implements Runnable {

    AtomicReference<Thread> reference = new AtomicReference<>();

    public void myLock() {
        System.out.println("初始化线程：" + reference.get());
        System.out.println(Thread.currentThread().getName() + "加锁");
        while (!reference.compareAndSet(null, Thread.currentThread())) {
//            System.out.println(Thread.currentThread()+"等待");
        }
    }

    public void myUnlock() {
        reference.compareAndSet(Thread.currentThread(), null);
        System.out.println(Thread.currentThread().getName() + "解锁");
    }

    @Override
    public void run() {
        myLock();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        myUnlock();
    }

    public static void main(String[] args) {
        //验证1
//        BK_caslock bk_caslock = new BK_caslock();
//        new Thread(bk_caslock,"t1").start();
//        try {
//            Thread.sleep(1000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        new Thread(bk_caslock,"t2").start();

        //验证2
        BK_caslock caslock = new BK_caslock();
        new Thread(() -> {
            caslock.myLock();
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            caslock.myUnlock();
        }, "t1").start();

        new Thread(() -> {
            caslock.myLock();
            caslock.myUnlock();
        }, "t2").start();
    }

}
