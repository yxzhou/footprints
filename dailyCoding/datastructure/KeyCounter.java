package dailyCoding.datastructure;

import java.util.HashSet;
import java.util.Map;

import java.util.HashMap;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;

/**
 *
 * This problem was asked by Dropbox.
 *
 * Create a data structure that performs all the following operations in O(1) time:
 *
 * plus: Add a key with value 1. If the key already exists, increment its value by one.
 * minus: Decrement the value of a key. If the key's value is currently 1, remove it.
 * get_max: Return a key with the highest value.
 * get_min: Return a key with the lowest value.
 *
 */

public class KeyCounter<K> {

    class Node{
        Node next;
        Node prev;

        int value;
        Set<K> keys;

        Node(int value){
            this.value = value;
            keys = new HashSet<>();
        }
    }

    Map<K, Integer> key2counter = new HashMap();
    Map<Integer, Node> counter2Keys = new HashMap<>();

    Node dummy;

    public KeyCounter(){
        dummy = new Node(0);

        dummy.next = dummy;
        dummy.prev = dummy;
    }

    public int plus(K key){

        Node curr = dummy;
        if(key2counter.containsKey(key)){
            curr = counter2Keys.get(key2counter.get(key));
        }

        int currPlusOne = curr.value + 1;

        key2counter.put(key, currPlusOne);

        if(!counter2Keys.containsKey(currPlusOne)){
            //build a new Node
            Node added = new Node(currPlusOne);

            //insert the new Node between curr and curr.next
            insert(curr, curr.next, added);
            counter2Keys.put(currPlusOne, added);
        }

        curr.keys.remove(key);
        if(curr.value > 0 && curr.keys.isEmpty()){
            remove(curr);
            counter2Keys.remove(curr.value);
        }

        counter2Keys.get(currPlusOne).keys.add(key);

        return currPlusOne;
    }

    public int minus(K key){
        if(!key2counter.containsKey(key)){
            return -1; // not found
        }

        Node curr = counter2Keys.get(key2counter.get(key));
        int currMinusOne = curr.value - 1;

        if(currMinusOne == 0){
            key2counter.remove(key);
        }else{
            key2counter.put(key, currMinusOne);
        }

        if(currMinusOne > 0 && !counter2Keys.containsKey(currMinusOne)){
            //build a new Node
            Node added = new Node(currMinusOne);

            //insert the new Node between currNode.prev and currNode
            insert(curr.prev, curr, added);
            counter2Keys.put(currMinusOne, added);
        }

        curr.keys.remove(key);
        if(curr.value > 0 && curr.keys.isEmpty()){
            remove(curr);
            counter2Keys.remove(curr.value);
        }

        if(currMinusOne > 0) {
            counter2Keys.get(currMinusOne).keys.add(key);
        }

        return currMinusOne;
    }

    public int getMax(){
        return dummy.prev.value;
    }

    public int getMin(){
        return dummy.next.value;
    }

    private void insert(Node curr, Node next, Node added){
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

        KeyCounter<String> sv = new KeyCounter();

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
