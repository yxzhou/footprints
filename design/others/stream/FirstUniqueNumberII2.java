/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package design.others.stream;

import java.util.HashMap;

/**
 *
 * 
 */
public class FirstUniqueNumberII2 {
    class Node{
        int value;

        Node pre;
        Node next;
    }

    Node dummy;
    HashMap<Integer, Node> map;
    
    public FirstUniqueNumberII2(){
        dummy = new Node();
        dummy.next = dummy;
        dummy.pre = dummy;

        map = new HashMap<>();
    }

    /**
     * @param num: next number in stream
     */
    public void add(int num) {
        if(map.containsKey(num)){
            Node toRemove = map.get(num);

            if(toRemove != null){
                toRemove.pre.next = toRemove.next;
                toRemove.next.pre = toRemove.pre;

                toRemove.next = null;
                toRemove.pre = null;
                map.put(num, null);
            }
            
        } else {
            Node toAdd = new Node();
            toAdd.value = num;

            toAdd.next = dummy;
            toAdd.pre = dummy.pre;
            dummy.pre.next = toAdd;
            dummy.pre = toAdd;

            map.put(num, toAdd);
        }
    }

    /**
     * @return the first unique number in stream
     */
    public int firstUnique() {
        return dummy.next != dummy? dummy.next.value : -1;
    }
}
