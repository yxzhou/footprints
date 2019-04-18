package fgafa.datastructure.hash.myHashSet;

import org.junit.Assert;
import org.junit.Test;

import java.util.LinkedList;
import java.util.Queue;

public class MyHashSet1 {

    int capacity = 512;
    int limit;  // capacity * factor
    int base;

    Queue<Integer>[] keys; //
    int size = 0;  // amount of the key


    /** Initialize your data structure here. */
    public MyHashSet1() {
        keys = new LinkedList[capacity];

        init();
    }

    public void add(int key) {
        if(contains(key)){
            return;
        }

        if(size == limit){
            resize();
        }

        size++;
        int i = key & base;

        if(keys[i] == null){
            keys[i] = new LinkedList<>();
        }

        keys[i].add(key);
    }

    public void remove(int key) {
        int i = key & base;
        if(keys[i] != null){
            int n;
            for(int j = keys[i].size(); j > 0; j--){
                n = keys[i].poll();
                if(n == key) {
                    size--;
                }else{
                    keys[i].add(n);
                }
            }
        }

    }

    /** Returns true if this set contains the specified element */
    public boolean contains(int key) {
        int i = key & base;
        if(keys[i] != null){
            for(Integer n : keys[i]){
                if(n == key){
                    return true;
                }
            }
        }

        return false;
    }

    private void resize(){
        int newCapacity = (capacity << 1);
        int newBase = newCapacity - 1;
        Queue<Integer>[] newKeys = new LinkedList[newCapacity];

        for(Queue<Integer> list : keys){
            if(list == null){
                continue;
            }

            for(int key : list){
                int i = key & newBase;

                if(newKeys[i] == null){
                    newKeys[i] = new LinkedList<>();
                }

                newKeys[i].add(key);
            }
        }

        capacity = newCapacity;
        keys = newKeys;
        init();
    }

    private void init(){
        this.limit = (int)(capacity * 0.75);
        this.base = capacity - 1;
    }


    @Test public void test(){

        MyHashSet1 hashSet = new MyHashSet1();
        hashSet.add(1);
        hashSet.add(2);
        Assert.assertTrue(hashSet.contains(1));    // returns true
        Assert.assertEquals(false, hashSet.contains(3));    // returns false (not found)
        hashSet.add(2);
        Assert.assertTrue(hashSet.contains(2));    // returns true
        hashSet.remove(2);
        Assert.assertEquals(false, hashSet.contains(2));    // returns false (already removed)


    }
}
