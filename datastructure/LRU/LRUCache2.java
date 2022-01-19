/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datastructure.LRU;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * Map<key, Node> + Double-linked-list<Node>
 */
public class LRUCache2 {
    //double linked list node
    class Node{
        int key;
        int value;

        Node pre;
        Node next;

        Node(int key, int value){
            this.key = key;
            this.value = value;
        }
    }

    int capacity;
    Map<Integer, Node> map;  //<key, Node>
    Node header;

    /*
    * @param capacity: An integer
    */
    public LRUCache2(int capacity) {
        assert capacity > 0;

        this.capacity = capacity;
        map = new HashMap<>();

        header = new Node(-1, -1);
        header.pre = header;
        header.next = header;
    }

    /**
     *  Time O(1)
     * @param key: An integer
     * @return the value if it's exists, otherwise -1 
     */
    public int get(int key) {
        if(!map.containsKey(key)){
            return -1;
        }

        Node result = map.get(key);

        if(result.pre != header){
            remove(result);
            insertFirst(result);
        }

        return result.value;
    }

    /**
     * update the value if the key exists, or insert it. 
     * when the cache reached its capacity, invalidate the least recently used item before inserting a new item.
     * 
     * Time O(1)
     * 
     * @param key: An integer
     * @param value: An integer
     * @return: nothing
     */
    public void set(int key, int value) {
        Node curr = map.get(key);
        if(curr == null){
            curr = new Node(key, value);
            map.put(key, curr);
        }
        curr.value = value;
        
        if(curr.pre != header){
            if(curr.pre != null){
                remove(curr);
            }

            insertFirst(curr);
        }

        while(map.size() > capacity){
            Node toRemove = header.pre;
            remove(toRemove);
            map.remove(toRemove.key);
        }
    }

    private void remove(Node curr){
        Node pre = curr.pre;
        Node next = curr.next;

        pre.next = next;
        next.pre = pre;
        
        curr.next = null;
        curr.pre = null;
    }

    private void insertFirst(Node curr){
        Node next = header.next;
        curr.next = next;
        next.pre = curr;
        
        header.next = curr;
        curr.pre = header;
    }
}
