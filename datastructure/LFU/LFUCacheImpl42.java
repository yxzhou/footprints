package datastructure.LFU;

import org.junit.Assert;

import java.util.*;

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

public class LFUCacheImpl42{

    class Node{
        int key;
        int value;
        int freq; //frequent number

        Node(int key, int value){
            this.key = key;
            this.value = value;
            this.freq = 1;
        }
    }

    int capacity;
    Map<Integer, Node> datas; //<key, Node>
    Map<Integer, LinkedHashSet<Integer>> freqs; //<freq, LinkedHashSet<key>>
    int minFreq;

    public LFUCacheImpl42(int capacity) {
        this.capacity = capacity;
        this.minFreq = 1;

        datas = new HashMap<>();
        freqs = new HashMap<>();
    }

    public int get(int key) {
        if(!datas.containsKey(key)){
            return -1;
        }

        Node curr = datas.get(key);
        
        freqs.get(curr.freq).remove(curr.key);
        curr.freq++;

        add(curr);

        return curr.value;
    }

    public void set(int key, int value) {
        Node curr = datas.get(key);

        if(curr == null){
            if(datas.size() == capacity){
                int toRemove = freqs.get(minFreq).iterator().next();

                freqs.get(minFreq).remove(toRemove);
                datas.remove(toRemove);
            }

            curr = new Node(key, value);
            minFreq = 1;

            datas.put(key, curr);
        }else{
            freqs.get(curr.freq).remove(curr.key) ;

            curr.value = value;
            curr.freq++;
        }

        add(curr);
    }

    private void add(Node curr){
        freqs.putIfAbsent(curr.freq, new LinkedHashSet<>() );
        freqs.get(curr.freq).add(curr.key);

        while(!freqs.containsKey(minFreq) || freqs.get(minFreq).isEmpty()){
            minFreq++;
        }

    }

    /**   **/
    //@Test
    //public void testLFUCache(){
    public static void main(String[] args){
        System.out.println("=======start=======");

        LFUCacheImpl42 cache = new LFUCacheImpl42(3);

        Assert.assertTrue(cache.get(5) == -1);
        cache.set(2, 2);
        //Assert.assertTrue(2 == cache.get(2));

        //cache.put(2, 22);
        //Assert.assertTrue(22 == cache.get(2));

        cache.set(1, 1);
        Assert.assertTrue(2 == cache.get(2));
        Assert.assertTrue(1 == cache.get(1));
        Assert.assertTrue(2 == cache.get(2));

        cache.set(3, 3);
        cache.set(4, 4);  // evict (3)
        Assert.assertTrue(cache.get(3) == -1);

        //Assert.assertTrue(cache.get(2) == 22);
        Assert.assertTrue(cache.get(2) == 2);
        Assert.assertTrue(cache.get(1) == 1);
        Assert.assertTrue(cache.get(4) == 4);

        System.out.println("=======end=======");

    }

}


