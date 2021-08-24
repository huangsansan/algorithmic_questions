package chenjicunM08;

import java.util.LinkedList;

/**
 * 数组实现的顺序队列
 *      head = 0，tail = n,
 *      出队是暂时不用数据迁移，在入队操作完成并且到达队尾的时候进行批量迁移
 *      head = tail的时候队空
 *      tail = n 表示队满
 *      
 * 链表实现的链式队列
 *      head 指向第一个节点，tail指向最后一个节点
 *      head.next = tail 时队空
 *      比较size判断队满
 *      
 * 首位相连的循环队列
 *      head 指向第一个，tail只想最后一个
 *      head = tail时 队空
 *      (tail+1)%n = head 时队满
 *      
 * 阻塞队列
 * 并发队列
 *      
 */
public class CircularQueue {
    private String[] items;
    private int n=0;
    private int head=0;
    private int tail = 0;
    
    private CircularQueue(int capacity) {
        n = capacity;
        items = new String[capacity];
    }
    
    private String  getOne() {
        if (head == tail) return null;
        String  res = items[head];
        head = (head+1)%n;
        return res;

    }
    private Boolean putOne(String item) {
        if ((tail+1)%n == head) return false;
        items[tail] = item;
        tail = (tail+1)%n;
        return true;
    }
    
}
