package BK2020.M06.D21;

import org.omg.CORBA.ARG_IN;

import java.util.concurrent.*;

/**
 * 阻塞队列 : 父类是Queue，与List都属于Collection，在某些情况下会挂起线程，一旦满足条件线程会被自动唤醒
 * 当队列是空的时候，从队列获取元素会被阻塞
 * 当队列是满的时候，向队列中插入元素会被阻塞
 *
 * 为什么需要BlockingQueue：不需要哦我们关心什么时候阻塞线程，什么时候唤醒线程，阻塞队列已经实现了
 *
 * BlockingQueue的实现类与List类似实现有：ArrayBlockingQueue（由数组结构组成的有界阻塞队列）,LinkedBlockingQueue（由链表组成的有界（默认Integer.MAX_VALUE）阻塞队列）
 * 还需要掌握：SynchronousQueue（不存储元素的阻塞队列，就是单个元素的队列）
 *
 * 还有PriorityBlockingQueue（支持优先级排序的无界阻塞队列）、DelayQueue（使用优先级队列实现的延迟无界阻塞队列）
 * 、LinkedTransferQueue（由链表组成的无界阻塞队列）、LinkedBlockingDeque（由链表结构组成的双向阻塞队列）
 *
 * 在哪里使用：1、生产者消费者。2、线程池。3、消息中间件
 *
 *
 * 方法：插入/移除/检查
 * 1、抛出异常组：add/remove/element（查看队首元素）
 * 2、特殊值组：offer/poll/peek（查看队首元素）
 * 3、阻塞组：put/take/不可用
 * 4、超时组：offer(e,time,unit)/poll(time,unit)/不可用
 *
 * SynchronousQueue:
 * iterator() 永远返回空，因为里面没东西。
 * peek() 永远返回null。
 * put() 往queue放进去一个element以后就一直wait直到有其他thread进来把这个element取走。
 * offer() 往queue里放一个element后立即返回，如果碰巧这个element被另一个thread取走了，offer方法返回true，认为offer成功；否则返回false。
 * offer(2000, TimeUnit.SECONDS) 往queue里放一个element但是等待指定的时间后才返回，返回的逻辑和offer()方法一样。
 * take() 取出并且remove掉queue里的element（认为是在queue里的。。。），取不到东西他会一直等。
 * poll() 取出并且remove掉queue里的element（认为是在queue里的。。。），只有到碰巧另外一个线程正在往queue里offer数据或者put数据的时候，该方法才会取到东西。否则立即返回null。
 * poll(2000, TimeUnit.SECONDS) 等待指定的时间然后取出并且remove掉queue里的element,其实就是再等其他的thread来往里塞。
 * isEmpty()永远是true。
 * remainingCapacity() 永远是0。
 * remove()和removeAll() 永远是false。
 *
 */
public class BK_BlockingQueue {

    public static void main(String[] args) {
        //抛出异常组：add/remove/element
//        ArrayBlockingQueueDemo();
        //特殊值组：offer/poll/peek
//        ArrayBlockingQueueDemo2();
        //阻塞组：put/take/不可用
//        ArrayBlockingQueueDemo3();
        //超时组：offer(e,time,unit)/poll(time,unit)/不可用
//        ArrayBlockingQueueDemo4();
        //SynchronousQueue的demo
        SynchronousQueueDemo();

    }

    public static void SynchronousQueueDemo(){
        SynchronousQueue<Integer> queue = new SynchronousQueue<>();
        new Thread(() -> {
            for (int i = 1; i < 5; i++) {
                try {
                    System.out.println(Thread.currentThread().getName() + "添加" + i);
                    queue.put(i);
//                    Thread.sleep(2000);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, "t1").start();

        new Thread(() -> {
            for (int i = 1; i < 5; i++) {
                try {
                    Thread.sleep(2000);
                    Integer take = queue.take();
                    System.out.println(Thread.currentThread().getName() + "获取到" + take);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, "t2").start();
    }

    public static void ArrayBlockingQueueDemo4(){
        ArrayBlockingQueue<Integer> arrayBlockingQueue = new ArrayBlockingQueue<>(3);
        try {
            arrayBlockingQueue.offer(1,2L,TimeUnit.SECONDS);
            arrayBlockingQueue.offer(2,2L,TimeUnit.SECONDS);
            arrayBlockingQueue.offer(3,2L,TimeUnit.SECONDS);
            System.out.println(arrayBlockingQueue.toString());
            System.out.println(arrayBlockingQueue.poll(2L,TimeUnit.SECONDS));
            System.out.println(arrayBlockingQueue.poll(2L,TimeUnit.SECONDS));
            System.out.println(arrayBlockingQueue.poll(2L,TimeUnit.SECONDS));
            System.out.println(arrayBlockingQueue.poll(2L,TimeUnit.SECONDS));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    public static void ArrayBlockingQueueDemo3(){
        ArrayBlockingQueue<Integer> arrayBlockingQueue = new ArrayBlockingQueue<>(3);

        new Thread(()->{
            try {
                Thread.sleep(3000);
                arrayBlockingQueue.take();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

        try {
            arrayBlockingQueue.put(1);
            arrayBlockingQueue.put(2);
            arrayBlockingQueue.put(3);
            arrayBlockingQueue.put(4);//会被阻塞
            System.out.println(arrayBlockingQueue.toString());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void ArrayBlockingQueueDemo2(){
        ArrayBlockingQueue<Integer> arrayBlockingQueue = new ArrayBlockingQueue<>(3);
        System.out.println(arrayBlockingQueue.offer(1));
        System.out.println(arrayBlockingQueue.offer(2));
        System.out.println(arrayBlockingQueue.offer(3));
        System.out.println(arrayBlockingQueue.offer(4));
        System.out.println(arrayBlockingQueue.toString());
//        System.out.println(arrayBlockingQueue.poll());
//        System.out.println(arrayBlockingQueue.poll());
        System.out.println(arrayBlockingQueue.toString());

        System.out.println(arrayBlockingQueue.peek());//查看队首元素

    }

    public static void ArrayBlockingQueueDemo(){
        ArrayBlockingQueue<Integer> arrayBlockingQueue = new ArrayBlockingQueue<>(3);
        System.out.println(arrayBlockingQueue.add(1));
        System.out.println(arrayBlockingQueue.add(2));
        System.out.println(arrayBlockingQueue.add(3));
        System.out.println();
        System.out.println(arrayBlockingQueue.remove());
//        System.out.println(arrayBlockingQueue.remove());
//        System.out.println(arrayBlockingQueue.remove());
        System.out.println(arrayBlockingQueue.toString());
        System.out.println(arrayBlockingQueue.element());//查看队首元素
    }
}
