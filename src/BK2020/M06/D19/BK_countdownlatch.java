package BK2020.M06.D19;

import java.util.concurrent.CountDownLatch;

/**
 * @description:
 * @Author: huangsan
 * @Date: 2020/6/19 2:53 下午
 */
public class BK_countdownlatch {
    public static void main(String[] args) {
        CountDownLatch countDownLatch = new CountDownLatch(6);

        for (int i = 1; i <= 6; i++) {
            new Thread(() -> {
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName() + "国被灭");
                countDownLatch.countDown();

            }, BK_CityEnum.getNameByEcode(i)).start();
        }

        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("--------------大秦一统天下");

    }
}
