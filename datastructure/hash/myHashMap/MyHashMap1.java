package fgafa.datastructure.hash.myHashMap;

import org.junit.Assert;
import org.junit.Test;

import java.util.LinkedList;
import java.util.Queue;

/**
 *
 * Design a HashMap without using any built-in hash table libraries.
 *
 * To be specific, your design should include these functions:
 *
 * put(key, value) : Insert a (key, value) pair into the HashMap. If the value already exists in the HashMap, update the value.
 * get(key): Returns the value to which the specified key is mapped, or -1 if this map contains no mapping for the key.
 * remove(key) : Remove the mapping for the value key if this map contains the mapping for the key.
 *
 * Example:
 *
 * MyHashMap hashMap = new MyHashMap();
 * hashMap.put(1, 1);
 * hashMap.put(2, 2);
 * hashMap.get(1);            // returns 1
 * hashMap.get(3);            // returns -1 (not found)
 * hashMap.put(2, 1);          // update the existing value
 * hashMap.get(2);            // returns 1
 * hashMap.remove(2);          // remove the mapping for 2
 * hashMap.get(2);            // returns -1 (not found)
 *
 * Note:
 *
 * All keys and values will be in the range of [0, 1000000].
 * The number of operations will be in the range of [1, 10000].
 * Please do not use the built-in HashMap library.
 *
 */

public class MyHashMap1 {

    int capacity = 1024;
    Queue<Entity>[] pool;
    int size = 0;

    int limit;
    int base;

    class Entity{
        int key;
        int value;

        Entity(int key, int value){
            this.key = key;
            this.value = value;
        }
    }

    /** Initialize your data structure here. */
    public MyHashMap1() {
        pool = new Queue[capacity];

        init();
    }

    /** value will always be non-negative. */
    public void put(int key, int value) {
        if(size == limit){
            resize();
        }

        int i = key & base;

        if(pool[i] == null){
            pool[i] = new LinkedList<>();
        }

        boolean found = false;
        for(Entity entity : pool[i]){
            if(entity.key == key){
                entity.value = value;
                found = true;
            }
        }

        if(!found){
            pool[i].add(new Entity(key, value));
            size++;
        }

    }

    /** Returns the value to which the specified key is mapped, or -1 if this map contains no mapping for the key */
    public int get(int key) {
        int i = key & base;

        if(pool[i] != null){
            for(Entity entity : pool[i]){
                if(entity.key == key){
                    return entity.value;
                }
            }
        }

        return -1;
    }

    /** Removes the mapping of the specified value key if this map contains a mapping for the key */
    public void remove(int key) {
        int i = key & base;

        if(pool[i] != null){
            Entity entity;
            for(int j = pool[i].size(); j > 0; j--){
                entity = pool[i].poll();

                if(entity.key == key){
                    size--;
                }else{
                    pool[i].add(entity);
                }
            }
        }

    }

    private void resize(){
        int newCapacity = (capacity << 1);
        int newBase = newCapacity - 1;

        Queue<Entity>[] newPool = new Queue[newCapacity];

        for(Queue<Entity> queue : pool){
            if(queue == null){
                continue;
            }

            for(Entity entity : queue){
                int i = entity.key & newBase;

                if(newPool[i] == null){
                    newPool[i] = new LinkedList<>();
                }

                newPool[i].add(entity);
            }
        }

        capacity = newCapacity;
        pool = newPool;
        init();
    }

    private void init(){
        this.limit = (int)(capacity * 0.75);
        this.base = capacity - 1;
    }

    @Test public void test(){
        MyHashMap1 hashMap = new MyHashMap1();
        hashMap.put(1, 1);
        hashMap.put(2, 2);
        Assert.assertEquals(1, hashMap.get(1));            // returns 1
        Assert.assertEquals(-1, hashMap.get(3));            // returns -1 (not found)
        hashMap.put(2, 1);          // update the existing value
        Assert.assertEquals(1, hashMap.get(2));            // returns 1
        hashMap.remove(2);          // remove the mapping for 2
        Assert.assertEquals(-1, hashMap.get(2));            // returns -1 (not found)
    }

}

/**
 * Your MyHashMap object will be instantiated and called as such:
 * MyHashMap obj = new MyHashMap();
 * obj.put(key,value);
 * int param_2 = obj.get(key);
 * obj.remove(key);
 */
