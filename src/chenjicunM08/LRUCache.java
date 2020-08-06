package chenjicunM08;

import org.omg.PortableServer.THREAD_POLICY_ID;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

/**
 * ["LRUCache","put","put","get","put","get","put","get","get","get"]
 * [[2],[1,1],[2,2],[1],[3,3],[2],[4,4],[1],[3],[4]]
 * 1,2
 * 2,1
 */
public class LRUCache {
    private int capacity;
    private int size;
    private Node head,tail;
    private Map<Integer,Node> cacheMap;

    public LRUCache(int capacity) {
        this.capacity = capacity;
        this.size = 0;
        this.cacheMap = new HashMap(capacity);
        this.head = new Node();
        this.tail = new Node();
        this.head.next = this.tail;
        this.tail.prev = this.head;

    }

    public int get(int key) {
        if (this.cacheMap.containsKey(key)) {
            Node node = this.cacheMap.get(key);
            // 删除节点
            removeNode(node);
            // 移动到头
            addToHead(node);
            return node.key;
      }
        return -1;
    }

    public void put(int key, int value) { Node node = new Node(key,value);
        if (this.cacheMap.containsKey(key)) {
            // 删除节点
            removeNode(node);
            // 移动到头
            addToHead(node);

        } else {
            addToHead(node);
            size++;
            if (this.size > this.capacity) {
                Node tail = removeTail();
                this.cacheMap.remove(tail.key);
                size--;
            }

        }
        this.cacheMap.put(key,node);
    }

    // 删除节点
    private void removeNode(Node node) {
        node.next.prev = node.prev;
        node.prev.next = node.next;
    }
    // 添加到头结点
    private void addToHead(Node node) {
        node.prev = head;
        node.next = head.next;
        head.next.prev = node;
        head.next = node;

    }
    // 删除尾节点
    private Node removeTail() {
        Node node = tail.prev;
        removeNode(node);
        return node;
    }
}
class Node {
    int key;
    int value;
    Node prev;
    Node next;
    public Node() {}
    public Node(int _key, int _value) {key = _key; value = _value;}
}