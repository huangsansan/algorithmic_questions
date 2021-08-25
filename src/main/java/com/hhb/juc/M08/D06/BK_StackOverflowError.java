package com.hhb.juc.M08.D06;

/**
 * Exception in thread "main" java.lang.StackOverflowError
 */
public class BK_StackOverflowError {

    public static void main(String[] args) {
        Demo();
    }

    public static void Demo() {
        Demo();
    }
}
