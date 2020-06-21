package BK2020.M06.D19;

import java.util.concurrent.Semaphore;

/**
 * semaphore(信号量) 主要用于两个目的：一个是用于多个共享资源的互斥，另一个是用于并发线程的控制
 * acquire：占用通道（可以传数字，代表占用多少）不传默认为1
 * release：释放通道（可以传递数字，代表释放多少）不传默认为1
 */
public class BK_semaphore {

    public static void main(String[] args) {
        Semaphore semaphore = new Semaphore(3,true);
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                try {
                    semaphore.acquire(3);
                    System.out.println(Thread.currentThread().getName() + "抢到车位");
                    Thread.sleep(500);
                    System.out.println(Thread.currentThread().getName() + "开走了");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    semaphore.release(3);
                }
            }, String.valueOf(i)).start();
        }
    }
}
