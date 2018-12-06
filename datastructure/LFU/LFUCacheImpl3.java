package fgafa.datastructure.LFU;

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
 *    Continue on LFUCacheImpl2
 *
 *    Cleanup() can be done in the other thread when it's over capacity and idle. and if so, it can remove the minHeap operation
 *    when set() and get(). Let's say it need cleanup n/5 with a maxHeap, the time complexity is O(mlogn). Totally average it's O(1)
 *
 *
 *  Todo:
 *    same as LFUCacheImpl
 */

public class LFUCacheImpl3<K, V> implements LFUCache<K, V>{
    private final int capacity;
    private final int evictNum;
    Map<K, Node> cache;
    Map<Integer, HashSet<K>> counts; // key is the frequent count, value is the HashSet<K>

    ExecutorService cleanupExecutor;
    AtomicBoolean isCleaning = new AtomicBoolean(false);

    @SuppressWarnings("unchecked")
    public LFUCacheImpl3(int capacity, int evictNum){
        if (capacity <= 0 || evictNum <= 0 || evictNum > capacity) {
            throw new IllegalArgumentException("Eviction number or Capacity is illegal.");
        }

        this.capacity = capacity;
        this.evictNum = evictNum;

        cache = new HashMap<>(capacity);
        counts = new HashMap<>(capacity); //

        cleanupExecutor = Executors.newFixedThreadPool(1);
    }

    @Override
    public void finalize() {
        cleanupExecutor.shutdown();

        try {
            cleanupExecutor.awaitTermination(1, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        System.out.println("All tasks are finished!");
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

            if(!isCleaning.get() && cache.size() >= capacity){
                CompletableFuture.runAsync(()->{
                    if(!isCleaning.get() && cache.size() >= capacity){
                        try {
                            isCleaning.set(true);
                            cleanup();
                        }finally {
                            isCleaning.set(false);
                        }
                    }
                });
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
            }
        }

        curr.count += diff;
        if(!counts.containsKey(curr.count)){
            counts.put(curr.count, new HashSet<>());
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

