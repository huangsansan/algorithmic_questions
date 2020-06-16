package BK2020.M06.D16;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * 1、arraylist是线程不安全的，因为add方法没有加锁，底层是数组，默认长度为10，如果容量不够grow改数组，运算符>>（偶数相当于/2）
 * 2、解决arraylist线程不安全的三种方法
 *    --Vector:底层实现是加锁，效率低
 *    --Collections.synchronizedList:查看add方法增加了synchronized，但是注意使用Iterator方式便利的时候，没有加锁，需要自己在额外枷锁
 *    --CopyOnWriteArrayList:写时复制，读写分离。每次扩容扩容一个(每次往一个容器中添加一个元素，不在当前容器中直接添加，而是对当前容器进行copy
 *      在copy的容器上操作，添加完之后将原容器的引用指向新的容器，读的时候不需要加锁，读的是原容器的数据，读写分离，效率高)底层实现为如下
 *       public boolean add(E e) {
 *         final ReentrantLock lock = this.lock;
 *         lock.lock();
 *         try {
 *             Object[] elements = getArray();
 *             int len = elements.length;
 *             Object[] newElements = Arrays.copyOf(elements, len + 1);
 *             newElements[len] = e;
 *             setArray(newElements);
 *             return true;
 *         } finally {
 *             lock.unlock();
 *         }
 *     }
 * 3、hashset是线程不安全的，底层实现是hashmap，查看源码可知调用add方法时如下，其中PRESENT为一个object对象
 *     public boolean add(E e) {
 *         return map.put(e, PRESENT)==null;
 *     }
 * 4、解决hashset线程不安全的两种方法
 *    --Collections.synchronizedSet
 *    --CopyOnWriteArraySet:其实底层还是CopyOnWriteArrayList
 * 5、hashmap是线程不安全的两种方法
 *    --Collections.synchronizedMap
 *    --ConcurrentHashMap：实现思路，在key对应的数组值为空的时候使用CAS进行插入，如果key对应的值不为空，然后使用synchronized在后续操作向链表或红黑树插入数据。
 *
 * 6、深入理解hashmap
 *    --java8之后hashmap的结构为：数组+链表+红黑树
 *      i=hash【return (key == null) ? 0 : (h = key.hashCode()】 ^ (h >>> 16);）&（n-1）
 *      i就是数组的索引，可以理解成存储key的方式，value的值会被存储到链表中，如果hash之后的i是相等的，则会存储到对应的链表中，如果链表长度大于8会存储到红黑树中
 *    --整个put的过程
 *      -首先在put之前会调用resize对table初始化（懒加载）
 *      -判断是否为null，如果为null调用resize扩容
 *      -根据key计算出的i进行插入，判断table[i]是否为null
 *      -如果为null直接插入，如果不为null，判断key是否存在存在就直接覆盖，不存在往链表中存储，如果链表长度大于8向红黑树中插入数据
 *      -插入后判断是否需要扩容，需要则扩容，不需要则结束
 *    --扩容机制
 *      -如果没有给定初始化值，则默认为16，在数据大于16*0.75（加载因子）=12的时候进行扩容
 *      -如果给定初始值，有两种情况。
 *          -1000 阈值是小于1000的，则数据大于阈值时进行扩容，实际map的长度也是1000；在放入第769个元素的时候扩容
 *          -10000 阈值大于10000是12888，map实际长度与为16384 ，在放入第10001的时候不会扩容，在放入第12289个元素的时候扩容
 *     --为什么数组大小为2的n次幂
 *       在进行获取i的时候有一个n-1，假设长度为16，16-1=15=1111。这样在做与运算的时候不会出现某一位为空永远都使用不到的情况
 *       假设n-1之后是1110，那么0001，0011，0101，1001，1011，0111，1101这些永远不会被使用，造成资源浪费
 *     --hashmap是如何解决hash冲突的，在Jdk1.7中使用4次位运算和5次异或运算（9次扰动）；1.8中使用2次扰动
 *       -第一次扰动：使用(h = key.hashCode()】 ^ (h >>> 16)来保证32位全部参与到运算中
 *       -第二次扰动：上述值与n-1做了与运算
 *       -两次扰动保证32位都参与了运算，已经达到了高低位共同参与的目标。
 *     --什么是hash冲突
 *       在两个不同的key进行hash运算后是相同的hash值，我们叫做hash碰撞
 *     --java7与java8中hashmap的区别：java8使用了两次扰动，数据结构增加了红黑树，尾插法
 *
 *
 *
 * */
public class BK_collection_security {

    public static void main(String[] args) {
//        listSecurity();//arraylist线程不安全解决
//        setSecurity();//hashset线程不安全解决
//         mapSecurity();//hashmap线程不安全解决

        HashMap<Object, Object> map = new HashMap<>(1);
        map.put("1","1");
        map.put("2","2");
        System.out.println(map.size());


    }

    public static void mapSecurity(){

//        Map<Object, Object> map = Collections.synchronizedMap(new HashMap<>());
        ConcurrentHashMap<Object, Object> map = new ConcurrentHashMap<>();
        for (int i = 0; i < 30; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    map.put(UUID.randomUUID().toString().substring(0,8),UUID.randomUUID().toString().substring(0,8));
                    System.out.println(map);
                }
            }).start();
        }

    }

    public static void setSecurity(){

//        HashSet<Object> set = new HashSet<>();
//        Set<Object> set = Collections.synchronizedSet(new HashSet<>());
        CopyOnWriteArraySet<Object> set = new CopyOnWriteArraySet<>();

        for (int i = 0; i < 30; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    set.add(UUID.randomUUID().toString().substring(0,8));
                    System.out.println(set);
                }
            }).start();
        }

    }

    public static void listSecurity(){
//        List<Object> list = new ArrayList<>();
//        Vector<Object> list = new Vector<>();
//        List<Object> list = Collections.synchronizedList(new ArrayList<>());
        CopyOnWriteArrayList<Object> list = new CopyOnWriteArrayList<>();

        for (int i = 0; i < 30; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    list.add(UUID.randomUUID().toString().substring(0,8));
                    System.out.println(list);
                }
            }).start();
        }

    }
}
