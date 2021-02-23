package fgafa.datastructure.LFU;

import org.junit.Assert;

import java.util.HashMap;
import java.util.Map;

/**
 *  Problem:
 *    same as LFUCacheImpl
 *
 *
 *  Thoughts:
 *    LFUCacheImpl is HashMap + Heap, the Time complexity: set(), O(logn); get(), O(logn)
 *    can it be better?
 *
 *    Refer to: An O(1) algorithm for implementing the LFU cache eviction scheme (_http://dhruvbird.com/lfu.pdf)
 *    Note, here it's only clean one when it's over capacity
 *
 *
 *  Todo:
 *
 */

public class LFUCacheImpl44 {

    class Node {
        int key;
        int value;
        int freq;

        Node pre;
        Node next;

        Node(int key, int value){
            this.key = key;
            this.value = value;
            this.freq = 0;

            pre = this;
            next = this;
        }

    }

    int capacity;
    Map<Integer, Node> datas; // <key, nodes>

    Map<Integer, Node> freqs;  //<freq, head of nodes>
    int minFreq;

    public LFUCacheImpl44(int capacity) {
        this.capacity = capacity;
        this.datas = new HashMap(capacity * 2);

        this.freqs = new HashMap<>(capacity * 2);
        this.minFreq = 1;
    }

    public int get(int key) {
        if(!datas.containsKey(key)){
            return -1;
        }

        Node curr = datas.get(key);

        increaseFrequent(curr, true);

        return curr.value;
    }

    public void put(int key, int value) {
        if(datas.containsKey(key)){
            Node curr = datas.get(key);

            curr.value = value;

            increaseFrequent(curr, true);

        }else{
            if(datas.size() == capacity && freqs.containsKey(minFreq)){
                Node removeNode = freqs.get(minFreq).pre;
                remove(removeNode);
                datas.remove(removeNode.key);
            }

            if(datas.size() < capacity){
                Node curr = new Node(key, value);
                datas.put(key, curr);

                increaseFrequent(curr, false);
                minFreq = curr.freq;
            }
        }
    }

    private void increaseFrequent(Node curr, boolean remove){
        if(remove){
            remove(curr);
        }

        curr.freq++;
        freqs.putIfAbsent(curr.freq, new Node(curr.freq, curr.freq));

        Node head = freqs.get(curr.freq);

        curr.next = head.next;
        curr.pre = head;

        head.next.pre = curr;
        head.next = curr;
    }

    private void remove(Node curr){
        curr.pre.next = curr.next;
        curr.next.pre = curr.pre;

        Node head = freqs.get(minFreq);
        if(head.next == head){
            minFreq++;
        }
    }

    /**   **/
    //@Test
    //public void testLFUCache(){
    public static void main(String[] args){
        System.out.println("=======start=======");

        //["LFUCache","put","put","get","put","get","get","put","get","get","get"]
        //[ [2],      [1,1],[2,2], [1],  [3,3],[2],  [3], [4,4], [1],  [3],  [4]]
        //Expect:[null,null,null,   1,    null,-1,    3,   null, -1,    3,    4]

        LFUCacheImpl44 cache = new LFUCacheImpl44(2);

        Assert.assertTrue(cache.get(5) == -1);
        cache.put(1, 1);
        cache.put(2, 2);
        Assert.assertTrue(1 == cache.get(1));


//        cache.put(1, 1);
//        Assert.assertTrue(2 == cache.get(2));
//        Assert.assertTrue(1 == cache.get(1));
//        Assert.assertTrue(2 == cache.get(2));

        cache.put(3, 3);
        Assert.assertTrue(-1 == cache.get(2));
        Assert.assertTrue(3 == cache.get(3));

        cache.put(4, 4);  // evict (3)
        Assert.assertTrue(cache.get(1) == -1);

        //Assert.assertTrue(cache.get(2) == 22);
        Assert.assertTrue(cache.get(3) == 3);
        Assert.assertTrue(cache.get(2) == -1);
        Assert.assertTrue(cache.get(4) == 4);

        System.out.println("=======end=======");

    }

}


