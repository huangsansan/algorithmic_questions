package BK2020.M06.D11;

/**
 * @description: DCL+volatile 实现最终单例模式
 * @Author: huangsan
 * @Date: 2020/6/11 6:25 下午
 */
public class SingletonDemo {

    private static volatile SingletonDemo instance = null;

    private SingletonDemo() {
        System.out.println(Thread.currentThread().getName() + "执行空参构造！！！");
    }

//    public synchronized static SingletonDemo init() {
//        if (instance == null) {
//            instance = new SingletonDemo();
//        }
//        return instance;
//    }

    //DCL double check lock 双端检锁机制 （需要增加volatile防止指令重排）
    public static SingletonDemo init() {
        if (instance == null) {
            synchronized (SingletonDemo.class) {
                if (instance == null) {
                    instance = new SingletonDemo();
                }
            }
        }
        return instance;
    }

    public static void main(String[] args) {
//        System.out.println(SingletonDemo.init() == SingletonDemo.init());
//        System.out.println(SingletonDemo.init() == SingletonDemo.init());
//        System.out.println(SingletonDemo.init() == SingletonDemo.init());

        for (int i = 1; i <= 10; i++) {
            new Thread(() -> {
                SingletonDemo.init();
            }, String.valueOf(i)).start();
        }

    }
}
