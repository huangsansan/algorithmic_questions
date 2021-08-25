package com.hhb.juc.M08.D06;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.WeakHashMap;

/**
 * 四种引用类型
 */
public class BK_Reference {

    public static void main(String[] args) {
        //强引用
//        StrongReference();
        //软引用
//        SoftReference();
        //软引用内存不够
//        NotEnoughSoftReference();
        //弱引用
//        WeakReference();
        //引用队列
        ReferenceQueueDemo();


//        HashMapDemo();
//        WeakHashMapDemo();
    }

    public static void ReferenceQueueDemo() {
        Object o = new Object();
        ReferenceQueue<Object> queue = new ReferenceQueue<>();
        WeakReference reference = new WeakReference<>(o, queue);
        o = null;
        System.gc();
        System.out.println(reference.get());
        System.out.println(queue.poll());

    }


    public static void HashMapDemo() {
        HashMap<Integer, Object> map = new HashMap<>();
        Integer key = new Integer(2);
        String value = "1";
        map.put(key, value);
        System.out.println(map.size());
        key = null;
        System.gc();
        System.out.println(map.size());
    }

    public static void WeakHashMapDemo() {
        WeakHashMap<Integer, Object> map = new WeakHashMap<>();
        Integer key = new Integer(2);
        String value = "2";
        map.put(key, value);
        System.out.println(map);
        key = null;
        System.gc();
        try {
            Thread.sleep(300);//疑问 直接打印size会出现1的可能
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(map + "\t" + map.size());
    }


    public static void WeakReference() {
        Object o = new Object();
        WeakReference reference = new WeakReference<>(o);
        o = null;//置空
        System.out.println(o);
        System.out.println(reference.get());//不为空
        System.gc();
        System.out.println(o);
        System.out.println(reference.get());//不为空
    }

    public static void StrongReference() {
        Object o = new Object();
        Object o2 = o;//引用赋值
        o = null;//置空
        System.gc();
        System.out.println(o);
        System.out.println(o2);//不为空
    }

    public static void SoftReference() {
        Object o = new Object();
        SoftReference reference = new SoftReference<>(o);
        o = null;//置空
        System.gc();
        System.out.println(o);
        System.out.println(reference.get());//不为空
    }

    public static void NotEnoughSoftReference() {
        Object o = new Object();
        SoftReference reference = new SoftReference<>(o);
        o = null;//置空
        System.gc();
        try {
            Byte[] bytes = new Byte[20 * 1024 * 1024];
        } catch (Error e) {
            e.printStackTrace();
        } finally {
            System.out.println(o);
            System.out.println(reference.get());//不为空
        }
    }
}
