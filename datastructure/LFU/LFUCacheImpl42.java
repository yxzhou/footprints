package fgafa.datastructure.LFU;

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

    class Entity{
        int key;
        int value;
        int frequent;

        Entity(int key, int value){
            this.key = key;
            this.value = value;
            this.frequent = 0;
        }
    }

    int capacity;
    Map<Integer, Entity> datas; // <key, value>

    Map<Integer, LinkedHashSet<Integer>> frequents;  //<frequent, List<key>>
    int minFrequent;

    public LFUCacheImpl42(int capacity) {
        this.capacity = capacity;
        this.datas = new HashMap(capacity * 2);

        this.frequents = new HashMap<>();
        this.minFrequent = 0;
    }

    public int get(int key) {
        if(!datas.containsKey(key)){
            return -1;
        }

        Entity result = datas.get(key);

        increaseFrequent(result);

        return result.value;
    }

    public void put(int key, int value) {
        if(datas.containsKey(key)){
            datas.get(key).value = value;
        }else{
            if(datas.size() == capacity){
                Iterator<Integer> itr = frequents.get(minFrequent).iterator();
                int toRemoveKey = itr.next();
                itr.remove();

                datas.remove(toRemoveKey);
            }

            datas.put(key, new Entity(key, value));
            minFrequent = 1;
        }

        increaseFrequent(datas.get(key));
    }

    private void increaseFrequent(Entity entity){

        frequents.computeIfPresent(entity.frequent, (k, v) -> {v.remove(entity.key); return v.isEmpty() ? null : v;});

        entity.frequent++;
        frequents.computeIfAbsent(entity.frequent, x->new LinkedHashSet<>()).add(entity.key);

        while( !frequents.containsKey(minFrequent) || frequents.get(minFrequent).isEmpty() ){
            minFrequent++;
        }
    }

    /**   **/
    //@Test
    //public void testLFUCache(){
    public static void main(String[] args){
        System.out.println("=======start=======");

        LFUCacheImpl42 cache = new LFUCacheImpl42(3);

        Assert.assertTrue(cache.get(5) == -1);
        cache.put(2, 2);
        //Assert.assertTrue(2 == cache.get(2));

        //cache.put(2, 22);
        //Assert.assertTrue(22 == cache.get(2));

        cache.put(1, 1);
        Assert.assertTrue(2 == cache.get(2));
        Assert.assertTrue(1 == cache.get(1));
        Assert.assertTrue(2 == cache.get(2));

        cache.put(3, 3);
        cache.put(4, 4);  // evict (3)
        Assert.assertTrue(cache.get(3) == -1);

        //Assert.assertTrue(cache.get(2) == 22);
        Assert.assertTrue(cache.get(2) == 2);
        Assert.assertTrue(cache.get(1) == 1);
        Assert.assertTrue(cache.get(4) == 4);

        System.out.println("=======end=======");

    }

}


