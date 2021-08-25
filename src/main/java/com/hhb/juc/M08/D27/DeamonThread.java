package com.hhb.juc.M08.D27;

/**
 * @description:
 * @Author: huangsan
 * @Date: 2020/8/28 9:30 下午
 */
public class DeamonThread extends Thread {

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(99999999);//模拟IO等待
            } catch (InterruptedException e) {
                System.out.println("强制中断");
            }
            break;
        }
    }

}
