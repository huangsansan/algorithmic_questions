package com.hhb.juc.M07.D29;

/**
 * 手写一个死锁demo
 *
 * 查看死锁状态：1、jps -l 查看当前进程
 *             2、jstack 进程ID 查看当前进程堆栈信息
 */
public class BK_Deadlock_case {
    public static void main(String[] args) {

        MyThread thread = new MyThread("A", "B");
        MyThread thread2 = new MyThread("B", "A");

        new Thread(thread, "AAA").start();
        new Thread(thread2, "BBB").start();

    }

    static class MyThread implements Runnable {

        private String lockA;
        private String lockB;

        public MyThread(String lockA, String lockB) {
            this.lockA = lockA;
            this.lockB = lockB;
        }

        @Override
        public void run() {
            synchronized (lockA) {
                System.out.println(Thread.currentThread().getName() + "获取了锁lockA，等待获取锁lockB");
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                synchronized (lockB) {
                    System.out.println(Thread.currentThread().getName() + "获取了锁lockB，等待获取锁lockA");
                }
            }
        }
    }
}
