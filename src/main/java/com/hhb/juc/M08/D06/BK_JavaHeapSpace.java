package com.hhb.juc.M08.D06;

import java.util.Random;

/**
 * Exception in thread "main" java.lang.OutOfMemoryError: Java heap space
 */
public class BK_JavaHeapSpace {

    public static void main(String[] args) {
//        System.gc();
//        Byte[] bytes = new Byte[6 * 1024 * 1024];
        String a = "aaa";
        while (true) {
            a += a + new Random().nextInt(7777777) + new Random().nextInt(66666666);
            a.intern();
        }
    }

}
