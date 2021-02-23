package fgafa.datastructure.LFU;

import java.util.HashMap;
import java.util.PriorityQueue;
import java.util.Map;


/**
 *
 * Implement an LFU (Least Frequently Used) cache. It should be able to be initialized with a cache size n, and contain the following methods:

 set(key, value): sets key to value. If there are already n items in the cache and we are adding a new item, then it should also remove the least frequently used item. If there is a tie, then the least recently used key should be removed.
 get(key): gets the value at key. If no such key exists, return null.

 Each operation should run in O(1) time.
 *
 *
 *  Thoughts:
 *    LRU (Least Recently Used) cache,  linkedHashMap
 *    LFU, HashMap + Heap,
 *
 *    HashMap stores the (key, value) for get()
 *    Heap stores the freq count.
 *
 *    Every get(key): find the node with key and increase the node's count
 *    Every put(key, value):
 *       if the node is existed, increase the node's count;
 *       if not existed, create the node.  if over capacity, release some nodes, and decrease the remain node's count.
 *
 *    Time complexity: set(), O(logn); get(), O(logn)
 *
 *  Todo:
 *    How to define "Least Frequently Used"? need it measure in a period, such as 5 minutes? Here it's just do count.
 */

public class LFUCacheImpl<K, V> implements LFUCache<K, V>{
    private final int capacity;
    private final int evictNum;
    Map<K, Node> cache;
    PriorityQueue<Node> counts; // minimum heap

    @SuppressWarnings("unchecked")
    public LFUCacheImpl(int capacity, int evictNum){
        if (capacity <= 0 || evictNum <= 0 || evictNum > capacity) {
            throw new IllegalArgumentException("Eviction number or Capacity is illegal.");
        }

        this.capacity = capacity;
        this.evictNum = evictNum;

        cache = new HashMap<>(capacity);
        counts = new PriorityQueue<>(capacity); // minimum heap
    }

    private class Node<K, V> implements Comparable<Node<K, V>>{
        K key;
        V value;
        int count;

        Node(K key, V value){
            this.key = key;
            this.value = value;
            this.count = 1;
        }

        @Override public int compareTo(Node<K, V> o) {
            return Integer.compare(this.count, o.count);
        }
    }

    boolean IDLE_TIME = false;

    @Override public synchronized void set(K key, V value){
        Node curr = cache.get(key);
        if(curr == null){
            curr = new Node(key, value);

            if(cache.size() >= capacity){
                for(int i = 0; i < evictNum; i++){
                    Node forRemove = counts.remove();
                    cache.remove(forRemove.key);
                }

                if(IDLE_TIME){ //todo: restart count, to clean up the node that was used very frequently before
                    int minimum = counts.peek().count;
                    PriorityQueue<Node> newCounts = new PriorityQueue<>(cache.size());

                    while(!counts.isEmpty()){
                        Node top = counts.poll();
                        top.count -= minimum;
                        newCounts.add(top);
                    }
                    this.counts = newCounts;
                }
            }

            cache.put(key, curr);
        }else{
            counts.remove(curr);

            curr.count++;
            curr.value = value;
        }

        counts.add(curr);
    }

    @Override public synchronized V get(K key){
        if(!cache.containsKey(key)){
            return null;
        }

        Node curr = cache.get(key);
        counts.remove(curr);    //it's O(n) in Jdk, logically it can be done in O(logn)

        curr.count++;
        counts.add(curr);

        return (V)curr.value;
    }

}
