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
 * @author yuanxi
 */
public class LRUCache3 {
    class Node{
        int key;
        int value;

        Node next;
        Node pre;
    }

    Map<Integer, Node> datas;
    int capacity;

    Node dummy;

    /*
    * @param capacity: An integer
    */
    public LRUCache3(int capacity) {
        this.datas = new HashMap<>(capacity);
        this.capacity = capacity;

        dummy = new Node();
        dummy.pre = dummy;
        dummy.next = dummy;
    }

    /**
     *  Time O(1)
     * @param key: An integer
     * @return: the value if it's exists, otherwise -1 
     */
    public int get(int key) {
        if(!datas.containsKey(key)){
            return -1;
        }

        Node curr = datas.get(key); 
        if(dummy.next.key != key){
            remove(curr.pre, curr, curr.next);
            insert(dummy, curr, dummy.next);
        }

        return curr.value;
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
        Node curr = datas.get(key); 

        if(curr == null){
            curr = new Node();
            curr.key = key;
            datas.put(key, curr);

            if(datas.size() > capacity ){
                Node toDelete = dummy.pre;
                remove(toDelete.pre, toDelete, dummy);

                datas.remove(toDelete.key);
            }

            insert(dummy, curr, dummy.next);
        }else if(dummy.next.key != key){
            remove(curr.pre, curr, curr.next);
            insert(dummy, curr, dummy.next);
        }
        
        curr.value = value;
    }

    private void remove(Node pre, Node curr, Node next){
        next.pre = pre;
        pre.next = next;
        curr.next = null;
        curr.pre = null;
    }

    private void insert(Node pre, Node curr, Node next){
        curr.next = next;
        curr.pre = pre;
        next.pre = curr;
        pre.next = curr;
    }
}
