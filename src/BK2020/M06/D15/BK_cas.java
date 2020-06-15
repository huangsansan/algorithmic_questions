package BK2020.M06.D15;

import java.util.Stack;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.atomic.AtomicStampedReference;

/**
 * 验证Atomic类 如何保证并发：
 * 1、追溯源码返现底层实现是unsafe与自旋锁
 * 2、unsafe类说明：是CAS的核心类，unsafe类直接操作java无法访问的底层系统（相当于后门）
 *  unsafe直接操作特定内存数据，unsafe存在于jvm的sun.misc包中，其内部方法像C一样可以直接操作内存指针
 *  因此CAS操作执行的就是底层的unsafe类的方法。
 *  注意：unsafe类中的所有方法都是native修饰的，也就是说unsafe直接操作操作系统底层资源
 *  3、其中的valueoffset就是内存地址偏移量，因为unsafe是根据内存地址偏移量来获取数据的
 *  4、其中的变量value是用volatile修饰的  private volatile int value; 保证了数据的可见性
 *
 *  什么是CAS：
 *  1、cas全程compare-and-swap 比较并交换，属于CPU并发原语，判断内存中的某一个位置的值是否为期望值，如果是则更新新的值。
 *  2、由于CAS是原语，在执行的过程中不可以被打断，所以保证了原子性。
 *
 *  3、 public final int getAndAddInt(Object var1, long var2, int var4) {
 *         int var5;
 *         do {
 *             var5 = this.getIntVolatile(var1, var2); //根据当前对象与指针获取最新数据，如果当前值==最新值，那么将当前值（最新值）+var4，如果false继续执行
 *         } while(!this.compareAndSwapInt(var1, var2, var5, var5 + var4));
 *
 *         return var5;
 *     }
 *  4、与synchronized相比较，提升了并发性（没有加锁，保证了一致性与并发性）
 *  5、cas的缺点：1、如果比较时间长的话影响性能，CPU开销大
 *               2、只能保证一个共享变量的原子操作，如果多变量还是用synchronized
 *               3、引来ABA问题
 *  6、什么是ABA问题：
 *     假设两个线程，一个线程A操作慢，一个线程B操作快，当A执行的时候，B执行了多次，第一次将值改为其他，之后又改回来，对于A线程来说在比较内存中的值的时候是没有变化的
 *     也就是说B更新了多次变量，而A变量看到的值还是原来的，直接判读为true就进行了赋值。
 *
 *  7、原子引用：AtomicStampedReference
 *  8、ABA问题：假设一个场景，判断账户中的钱少于20快增加10快，每次增加后，用户又花了10快，下一个现场判断还是小于20，继续增加。产生ABA问题
 *
 */
public class BK_cas {

    public static void main(String[] args) {
        System.out.println(123);
        //验证CAS
//        showCAS();

        //原子引用
        //AtomicReference<User> reference = new AtomicReference<User>();

        //ABA问题
        AtomicReference<Integer> reference = new AtomicReference<>(1);

//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                reference.compareAndSet(1, 2);
//                System.out.println("变量更新为" + 2);
//                reference.compareAndSet(2, 1);
//                System.out.println("变量更新为" + 1);
//            }
//        }).start();
//
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    Thread.sleep(2000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//                reference.compareAndSet(1, 999);
//                System.out.println("变量更新为" + reference);
//
//            }
//        }).start();

        //解决ABA问题
        AtomicStampedReference<Integer> stampedReference = new AtomicStampedReference<Integer>(11,0);

        new Thread(new Runnable() {
            @Override
            public void run() {
                int stamp = stampedReference.getStamp();
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                stampedReference.compareAndSet(11, 22, stampedReference.getStamp(), stamp + 1);
                System.out.println("变量更新为" + stampedReference.getReference());
                stampedReference.compareAndSet(22, 11, stampedReference.getStamp(), stamp + 1);
                System.out.println("变量更新为" + stampedReference.getReference());
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                int stamp = stampedReference.getStamp();
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                ;
                System.out.println("变更是否成功" + stampedReference.compareAndSet(11, 999, stamp, stamp + 1));
                System.out.println("变量更新为" + stampedReference.getReference());

            }
        }).start();

    }

    public static void showCAS(){
        AtomicInteger integer = new AtomicInteger(1);
        System.out.println(integer.compareAndSet(1, 1000));
        System.out.println(integer.compareAndSet(1000, 2000));
    }

}
