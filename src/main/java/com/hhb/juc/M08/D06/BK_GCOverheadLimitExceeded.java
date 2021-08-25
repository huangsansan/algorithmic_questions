package com.hhb.juc.M08.D06;

import java.util.ArrayList;

/**
 * GC回收时间过长，当98%的时间都在做GC，并且回收不到2%的内存，会抛出异常。（GC的作用微乎其微）
 *
 * [Full GC (Ergonomics) [PSYoungGen: 1024K->1020K(1536K)] [ParOldGen: 4070K->4070K(4096K)]
 * 5094K->5091K(5632K), [Metaspace: 3665K->3665K(1056768K)], 0.0401611 secs] [Times: user=0.49 sys=0.00, real=0.04 secs]
 * Exception in thread "main" java.lang.OutOfMemoryError: GC overhead limit exceeded
 */
public class BK_GCOverheadLimitExceeded {
    public static void main(String[] args) {
        ArrayList<Integer> list = new ArrayList<>();

        while (true) {
            try {
                list.add(new Integer("1"));
            } catch (Throwable e) {
                e.printStackTrace();
            } finally {
                System.out.println(list.size());
            }
        }
    }
}
