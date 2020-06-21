package BK2020.M06.D19;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.*;

/**
 * cyclic（可循环的）barrier(屏障) 让一组线程达到一个屏障后阻塞，直到最后一个线程达到屏障时，才会开门 （收集七龙珠，召唤神龙）
 *
 * 跑马项目可以等到看完线程池再看
 */
public class BK_cyclicbarrier implements Runnable{

    private static final int FINISH_LINE = 75;
    private static List<Horse> horses = new ArrayList<Horse>();
    private static ExecutorService exec = Executors.newCachedThreadPool();

    public static void main(String[] args) throws BrokenBarrierException, InterruptedException {
//        sevenPointDemo();
        CyclicBarrier barrier = new CyclicBarrier(7, new BK_cyclicbarrier());

        for(int i = 0; i < 7; i++) {
            Horse horse = new Horse(barrier);
            horses.add(horse);
            horse.run();
            exec.execute(horse);
        }
    }

    //七龙珠demo
    public static void sevenPointDemo(){
        CyclicBarrier cyclicBarrier = new CyclicBarrier(7, () -> {
            System.out.println("集齐七颗龙珠，召唤神龙");
        });

        for (int i = 1; i <= 7; i++) {
            final int temp = i;
            new Thread(() -> {
                try {
                    Thread.sleep(500);
                    System.out.println("收集到第" + Thread.currentThread().getName() + "颗龙珠");
                    cyclicBarrier.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }
            }, String.valueOf(i)).start();
        }
    }


    @Override
    public void run() {
        StringBuilder s = new StringBuilder();
        //打印赛道边界
        for(int i = 0; i < FINISH_LINE; i++) {
            s.append("=");
        }
        System.out.println(s);
        //打印赛马轨迹
        for(Horse horse : horses) {
            System.out.println(horse.tracks());
        }
        //判断是否结束
        for(Horse horse : horses) {
            if(horse.getStrides() >= FINISH_LINE) {
                System.out.println(horse + "won!");
                exec.shutdownNow();
                return;
            }
        }
        //休息指定时间再到下一轮
        try {
            TimeUnit.MILLISECONDS.sleep(2000);
        } catch(InterruptedException e) {
            System.out.println("barrier-action sleep interrupted");
        }
    }
}

class Horse implements Runnable {

    private static int counter = 0;
    private final int id = counter++;
    private int strides = 0;
    private static Random rand = new Random(47);
    private static CyclicBarrier barrier;

    public Horse(CyclicBarrier b) { barrier = b; }

    @Override
    public void run() {
        try {
            while(!Thread.interrupted()) {
                synchronized(this) {
                    //赛马每次随机跑几步
                    strides += rand.nextInt(3);
                }
                barrier.await();
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public String tracks() {
        StringBuilder s = new StringBuilder();
        for(int i = 0; i < getStrides(); i++) {
            s.append("*");
        }
        s.append(id);
        return s.toString();
    }

    public synchronized int getStrides() { return strides; }
    public String toString() { return "Horse " + id + " "; }

}
