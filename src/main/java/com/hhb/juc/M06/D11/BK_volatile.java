package com.hhb.juc.M06.D11;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @description: 主要讲解volatile关键字内容以及使用
 * <p>
 * volatile是java虚拟机提供的轻量级的同步机制
 * 1、保证可见性 （下面验证可见性：增加volatile之后主线程可见）
 * 2、不保证原子性
 * 3、禁止指令重排
 * @Author: huangsan
 * @Date: 2020/6/11 1:48 下午
 */
class MyData {
    volatile int data = 0; //如果用Integer是引用类型，更改后引用地址更改，即使不加volatile主线程也立即改变

    public void change() {
        this.data = 1;
    }

    public void numberPlus() {
        this.data++;
    }

    AtomicInteger data2 = new AtomicInteger(0);

    public void numberPlus2() {
        data2.getAndIncrement();
    }
}

public class BK_volatile {

    public static void main(String[] args) {
        AtomicityDemo();
    }


    //验证原子性(不保证原子性)
    //运行值小于期望着的原因，多个线程同时获取到主物理内存中的变量，在自己的工作空间中执行+1操作，查看字节码文件得知在
    //++的过程被拆分成三段：获取值、加一、写回值 三个步骤，出现在重复写入的问题，1号线程写了2，2好线程恰好也写2，导致数据丢失
    public static void AtomicityDemo() {

        MyData myData = new MyData();

        for (int i = 1; i <= 10; i++) {
            new Thread(() -> {
                for (int j = 0; j < 1000; j++) {
                    myData.numberPlus();
                    myData.numberPlus2();
                }
            }, String.valueOf(i)).start();
        }

        while (Thread.activeCount() > 2) {
            Thread.yield();
        }

        System.out.println("最终int结果：" + myData.data);
        System.out.println("最终atomicInteger结果：" + myData.data2);

    }


    //验证可见性
    public static void VisibilityDemo() {
        MyData myData = new MyData();
        System.out.println(myData.data);

        new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + "线程获取到值为：" + myData.data);
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            myData.change();
            System.out.println(Thread.currentThread().getName() + "线程更改值为：" + myData.data);
        }, "A").start();

        while (myData.data == 0) {

        }

        System.out.println("main线程执行了，获取到的值为" + myData.data);

    }
}
