/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package datastructure.stack;

import java.util.TreeSet;

/**
 * Continue on MaxStackII, the m2
 * TreeSet<Node>, Node is DoubleLinkedNode, it's to store the sequence. TreeSet is to store the max.
 * 
 */
public class MaxStackII2 {
    
    class Node {
        int id;  //sequence id
        int value;

        Node pre;
        Node next;
    }
    
    TreeSet<Node> treeSet;
    Node dummy;
    int id;

    public MaxStackII2() {
        treeSet = new TreeSet<>((a, b) -> a.value == b.value? a.id - b.id : a.value - b.value ); 
        id = 0;
        
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
        toAdd.id = id++;
        
        toAdd.next = dummy.next;
        toAdd.pre = dummy;
                
        dummy.next.pre = toAdd;
        dummy.next = toAdd;
        
        treeSet.add(toAdd);
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
        return treeSet.last().value;
    }

    /*
     * @return: An integer
     */
    public int popMax() {
        Node toRemove = treeSet.last();
        
        removeNode(toRemove.pre, toRemove, toRemove.next);
        
        return toRemove.value;
    }
    
    private void removeNode(Node pre, Node toRemove, Node next){
        pre.next = next;
        next.pre = pre;
        
        treeSet.remove(toRemove);

        toRemove.next = null;
        toRemove.pre = null;
    }
}
