package fgafa.datastructure.LFU;

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
 *    In LFUCacheImpl, it's O(logn), the n is all the node(key, value), the minHeap store all the node's frequent count.
 *    In fact, lots of node's frequent count are same, it means there are lots of duplicate in the Heap.
 *    Let's suppose there are m unique frequent count, the time complexity can be O(logm) (m << n)
 *
 *    Cleanup() can be done in the other thread when it's over capacity and idle. and if so, it can remove the minHeap operation
 *    when set() and get(). Let's say it need cleanup n/5 with a maxHeap, the time complexity is O(mlogn). Totally average it's O(1)
 *
 *
 *  Todo:
 *    same as LFUCacheImpl
 */

public class LFUCacheImpl2<K, V> implements LFUCache<K, V>{
    private final int capacity;
    private final int evictNum;
    Map<K, Node> cache;
    Map<Integer, HashSet<K>> counts; // key is the frequent count, value is the HashSet<K>
    PriorityQueue<Integer> minHeap; // here the minimum heap stores the frequent count.

    @SuppressWarnings("unchecked")
    public LFUCacheImpl2(int capacity, int evictNum){
        if (capacity <= 0 || evictNum <= 0 || evictNum > capacity) {
            throw new IllegalArgumentException("Eviction number or Capacity is illegal.");
        }

        this.capacity = capacity;
        this.evictNum = evictNum;

        cache = new HashMap<>(capacity);
        counts = new HashMap<>(capacity); //
        minHeap = new PriorityQueue<>();
    }

    private class Node<K, V>{
        K key;
        V value;
        int count;

        Node(K key, V value){
            this.key = key;
            this.value = value;
            this.count = 0;
        }
    }


    @Override public synchronized void set(K key, V value){

        if(cache.containsKey(key)){
            cache.get(key).value = value;
        }else{
            Node curr = new Node(key, value);

            if(cache.size() >= capacity){
                cleanup();
            }

            cache.put(key, curr);
        }

        increase(key, 1);
    }

    @Override public synchronized V get(K key){
        if(!cache.containsKey(key)){
            return null;
        }

        increase(key, 1);

        return (V)cache.get(key).value;
    }

    private void increase(K key, int diff){
        if(!cache.containsKey(key)) {
            return;
        }

        Node curr = cache.get(key);

        //remove the old
        if(counts.containsKey(curr.count)) {
            counts.get(curr.count).remove(key);

            if(counts.get(curr.count).isEmpty()){
                counts.remove(curr.count);
                minHeap.remove(curr.count);
            }
        }

        curr.count += diff;
        if(!counts.containsKey(curr.count)){
            counts.put(curr.count, new HashSet<>());
            minHeap.add(curr.count);
        }

        counts.get(curr.count).add(key);
    }

    private void remove(K key){
        if(!cache.containsKey(key)){
            return;
        }

        Node forRemove = cache.get(key);
        cache.remove(key);

        if(!counts.containsKey(forRemove.count)){
            return;
        }

        counts.get(forRemove.count).remove(key);

        if(counts.get(forRemove.count).isEmpty()){
            counts.remove(forRemove.count);
            minHeap.remove(forRemove.count);
        }
    }

    boolean IDLE_TIME = false;

    private void cleanup(){
        for(int i = 0; i < evictNum; i++){
            remove(counts.get(minHeap.peek()).iterator().next());
        }

        if(IDLE_TIME){ //todo: restart count, to clean up the node that was used very frequently before
            int minimum = minHeap.peek();
            PriorityQueue<Integer> newMinHeap = new PriorityQueue<>(minHeap.size());

            while(!minHeap.isEmpty()){
                int frequence = minHeap.poll();
                int newFrequence = frequence - minimum;

                newMinHeap.add(newFrequence);
                counts.put(newFrequence, counts.remove(frequence));
            }
            this.minHeap = newMinHeap;

            for(Node node : cache.values()){
                node.count -= minimum;
            }
        }
    }

}

