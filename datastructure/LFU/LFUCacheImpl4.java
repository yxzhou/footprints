package datastructure.LFU;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

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

public class LFUCacheImpl4<K, V> implements LFUCache<K, V>{
    private final int capacity;
    private final int evictNum;
    Map<K, Node> cache;
    Map<Integer, LinkedHashSet<K>> counts; // key is the frequent count, value is the LinkedHashSet<K>
    int minCount = 0;

    @SuppressWarnings("unchecked")
    public LFUCacheImpl4(int capacity, int evictNum){
        if (capacity <= 0 || evictNum <= 0 || evictNum > capacity) {
            throw new IllegalArgumentException("Eviction number or Capacity is illegal.");
        }

        this.capacity = capacity;
        this.evictNum = evictNum;

        cache = new HashMap<>(capacity);
        counts = new HashMap<>(capacity); //
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
                remove(counts.get(minCount).iterator().next());
            }

            cache.put(key, curr);

            minCount = 0;
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
            }
        }

        curr.count += diff;
        if(!counts.containsKey(curr.count)){
            counts.put(curr.count, new LinkedHashSet<>());
        }

        counts.get(curr.count).add(key);

        while(!counts.containsKey(minCount) && minCount < capacity){
            minCount++;
        }
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
        }
    }


    boolean IDLE_TIME = false;

    private void cleanup(){
        PriorityQueue<Integer> maxHeap = new PriorityQueue<>(evictNum, Collections.reverseOrder());

        int limit= evictNum;
        for(Iterator<Integer> iterator = counts.keySet().iterator(); iterator.hasNext(); ){
            int frequence = iterator.next();
            if(limit > 0){
                maxHeap.add(frequence);
                limit--;
            }else{
                if(frequence < maxHeap.peek()){
                    maxHeap.poll();
                    maxHeap.add(frequence);
                }
            }
        }

        int base = maxHeap.peek();

        while(!maxHeap.isEmpty()){
            remove(counts.get(maxHeap.poll()).iterator().next());
        }

        if(IDLE_TIME){ //todo: restart count, to clean up the node that was used very frequently before

        }
    }

}


