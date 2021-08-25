package com.hhb.juc.M08.D27;

/**
 * @description:
 * @Author: huangsan
 * @Date: 2020/8/28 9:34 下午
 */
public class TimeOutThread extends Thread {

    @Override
    public void run() {
        try {
            Thread.sleep(3000);
            System.out.println("超时结束了");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        DeamonThread t1 = new DeamonThread();
        TimeOutThread t2 = new TimeOutThread();
        t1.setDaemon(true);
        t1.start();
        t2.start();
    }
}
