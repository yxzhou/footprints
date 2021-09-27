package datastructure.map;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class MyHashMap {
    class Entity{
        int key;
        int value;

        Entity(int key, int value){
            this.key = key;
            this.value = value;
        }
    }

    int capacity;
    int base;
    int size;

    int limit;
    static float LOAD_FACTOR;

    List<Entity>[] buckets;


    /** Initialize your data structure here. */
    public MyHashMap() {
        this.capacity = 1024;
        this.base = capacity - 1;
        this.size = 0;

        this.LOAD_FACTOR = 0.75f;
        this.limit = (int)(capacity * LOAD_FACTOR);

        this.buckets = new LinkedList[capacity];
    }

    /** value will always be non-negative. */
    public void put(int key, int value) {
        if(size == limit){
            resize();
        }

        int p = hash(key);

        if(buckets[p] == null){
            buckets[p] = new LinkedList<>();
        }

        for(Entity entity : buckets[p]){
            if(key == entity.key){

                entity.value = value;
                return;
            }
        }

        size++;
        buckets[p].add(new Entity(key, value));
    }

    /** Returns the value to which the specified key is mapped, or -1 if this map contains no mapping for the key */
    public int get(int key) {
        int p = hash(key);

        if(buckets[p] == null){
            return -1;
        }

        for(Entity entity : buckets[p]){
            if(key == entity.key){
                return entity.value;
            }
        }

        return -1;
    }

    /** Removes the mapping of the specified value key if this map contains a mapping for the key */
    public void remove(int key) {
        int p = hash(key);

        if(buckets[p] == null){
            return;
        }

        Iterator<Entity> itr = buckets[p].iterator();
        while(itr.hasNext()){
            if(key == itr.next().key){
                itr.remove();
                size--;
                return;
            }
        }
    }

    private int hash(int key){
        return key & base;
    }

    private void resize(){

    }

}
