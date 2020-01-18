package fgafa.dailyCoding.datastructure;

import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

public class KeyCounter3<K> {

    class Node{
        Node next;
        Node prev;

        int value;
        int count;

        Node(int value){
            this.value = value;
            this.count = 0;
        }
    }

    Map<K, Node> key2counter = new HashMap();

    Node dummy;

    public KeyCounter3(){
        dummy = new Node(0);

        dummy.next = dummy;
        dummy.prev = dummy;
    }

    public int plus(K key){
        Node curr = dummy;
        if(key2counter.containsKey(key)){
            curr = key2counter.get(key);
        }

        Node currPlusOne = curr.next;
        if(currPlusOne.value != curr.value + 1){
            //insert the new Node after the curr
            currPlusOne = new Node(curr.value + 1);
            insertAfter(curr, currPlusOne);
        }

        key2counter.put(key, currPlusOne);
        currPlusOne.count++;

        curr.count--;
        if(curr.value > 0 && curr.count == 0){
            remove(curr);
        }

        return currPlusOne.value;
    }

    public int minus(K key){
        if(!key2counter.containsKey(key)){
            return -1; // not found
        }

        Node curr = key2counter.get(key);

        Node currMinusOne = curr.prev;
        if(currMinusOne.value != curr.value - 1){
            //insert the new Node between currNode.prev and currNode
            currMinusOne = new Node(curr.value - 1);
            insertAfter(curr.prev, currMinusOne);
        }

        if(currMinusOne.value == 0){
            key2counter.remove(key);
        }else{
            key2counter.put(key, currMinusOne);
            currMinusOne.count++;
        }

        curr.count--;
        if(curr.value > 0 && curr.count == 0){
            remove(curr);
        }

        return currMinusOne.value;
    }

    public int getMax(){
        return dummy.prev.value;
    }

    public int getMin(){
        return dummy.next.value;
    }

    private void insertAfter(Node curr, Node added){
        Node next = curr.next;

        added.prev = curr;
        added.next = next;
        next.prev = added;
        curr.next = added;
    }

    private void remove(Node deleted){
        Node prev = deleted.prev;
        Node next = deleted.next;

        prev.next = next;
        next.prev = prev;
    }


    @Test
    public void test(){

        KeyCounter3<String> sv = new KeyCounter3();

        Assert.assertTrue( -1 == sv.minus("a"));
        Assert.assertTrue( 1 == sv.plus("a"));
        Assert.assertTrue( 1 == sv.getMax());
        Assert.assertTrue( 1 == sv.getMin());


        Assert.assertTrue( 1 == sv.plus("b"));
        Assert.assertTrue( 2 == sv.plus("a"));
        Assert.assertTrue( 3 == sv.plus("a"));
        Assert.assertTrue( 3 == sv.getMax());
        Assert.assertTrue( 1 == sv.getMin());

        Assert.assertTrue( 0 == sv.minus("b"));
        Assert.assertTrue( 2 == sv.minus("a"));
        Assert.assertTrue( 2 == sv.getMax());
        Assert.assertTrue( 2 == sv.getMin());
    }

}
