package com.hhb.juc.M06.D30;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * callable 与 runable的区别：1、callable有返回值。2、callable抛出异常
 */
public class BK_Callable {

    public static void main(String[] args) {

        System.out.println(Runtime.getRuntime().availableProcessors());

        FutureTask<Integer> integerFutureTask = new FutureTask<Integer>(new MyThread());
        new Thread(integerFutureTask).start();


        System.out.println("main is over");


//        while ()

//        System.out.println(integerFutureTask.isDone());
        System.out.println(integerFutureTask.isCancelled());

        try {
            System.out.println("callable return " + integerFutureTask.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        System.out.println(integerFutureTask.isDone());
//        System.out.println(integerFutureTask.isCancelled());
    }

}

class MyThread implements Callable<Integer> {

    @Override
    public Integer call() throws Exception {
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("come in callable");
        return 1024;
    }
}