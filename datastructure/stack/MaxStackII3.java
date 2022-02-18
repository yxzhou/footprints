/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package datastructure.stack;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

/**
 * Continue on MaxStackII, the m2
 * TreeSet<Node>, Node is DoubleLinkedNode, it's to store the sequence. TreeSet is to store the max.
 * 
 */
public class MaxStackII3 {
    
    class Node {
        int id;  //sequence id
        int value;

        Node pre;
        Node next;
    }
    
    TreeMap<Integer, List<Node>> treeMap;
    Node dummy;

    public MaxStackII3() {
        treeMap = new TreeMap<>( ); 
        
        dummy = new Node();
        dummy.next = dummy;
        dummy.pre = dummy;
    }

    /*
     * @param number: An integer
     * @return: nothing
     */
    public void push(int x) {
        Node toAdd = new Node();
        toAdd.value = x;
        
        toAdd.next = dummy.next;
        toAdd.pre = dummy;
                
        dummy.next.pre = toAdd;
        dummy.next = toAdd;
        
        treeMap.computeIfAbsent(x, a -> new ArrayList<>()).add(toAdd);
    }

    public int pop() {
        Node toRemove = dummy.next;
                
        removeNode(dummy, toRemove, toRemove.next);
        
        return toRemove.value;
    }

    /*
     * @return: An integer
     */
    public int top() {
        return dummy.next.value;
    }

    /*
     * @return: An integer
     */
    public int peekMax() {
        return treeMap.lastKey();
    }

    /*
     * @return: An integer
     */
    public int popMax() {
        List<Node> list = treeMap.lastEntry().getValue();
        Node toRemove = list.get(list.size() - 1);
        
        removeNode(toRemove.pre, toRemove, toRemove.next);
        
        return toRemove.value;
    }
    
    
    private void removeNode(Node pre, Node toRemove, Node next){
        pre.next = next;
        next.pre = pre;
        
        int v = toRemove.value;
        if(treeMap.get(v).size() == 1 ){
            treeMap.remove(v);
        }else{
            List<Node> list =  treeMap.get(v);
            list.remove(list.size() - 1);
        }

        toRemove.next = null;
        toRemove.pre = null;
    }
}
