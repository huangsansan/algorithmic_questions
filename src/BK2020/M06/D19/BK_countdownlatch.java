package BK2020.M06.D19;

import java.util.concurrent.CountDownLatch;

/**
 * @description:
 * @Author: huangsan
 * @Date: 2020/6/19 2:53 下午
 */
public class BK_countdownlatch {
    public static void main(String[] args) {
        CountDownLatch countDownLatch = new CountDownLatch(10);

        for (int i = 1; i <= 10; i++) {
            new Thread(() -> {
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("学生" + Thread.currentThread().getName() + "离开教室");
                countDownLatch.countDown();

            }, String.valueOf(i)).start();
        }

        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("--------------班长锁门，离开教室");
    }
}
