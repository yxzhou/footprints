package fgafa.dailyCoding;

/**
 * An XOR linked list is a more memory efficient doubly linked list. Instead of each node holding next and prev fields,
 * it holds a field named both, which is an XOR of the next node and the previous node.
 *
 * Implement an XOR linked list; it has an add(element) which adds the element to the end, and a get(index) which returns the node at index.

 * If using a language that has no pointers (such as Python), you can assume you have access to get_pointer and dereference_pointer functions that converts between nodes and memory addresses.
 *
 * Tags: Google, Data Structure,
 *
 * /

/** assume it provides the follwing */
class Node<V>{
    V value;
    long both;

    Node(V value){
        this.value = value;
        both = 0L;
    }
}

class Converter {
    static long get_pointer(Node node) {
        //todo
        return -1;
    }

    static Node dereference_pointer(long memoryAddress) {
        //todo
        return null;
    }
}

/** the following is the implementation */
public class XORLinkedList<V> {
    Node header = null;
    Node tail = null;
    int size = 0;

    void add(V element){
        Node newNode = new Node(element);

        if(size == 0){
            header = newNode;
        }else if(size > 1){
            header.both ^= tail.both ^ newNode.both;
            tail.both ^= header.both ^ newNode.both;
        }

        tail = newNode;
        size++;
    }

    Node get(int index){
        if(index >= size){
            return null;
        }

        Node curr = header;
        for(Node pre = tail, next = null ; index > 0; index--){
            next = Converter.dereference_pointer(Converter.get_pointer(pre) ^ curr.both);

            pre = curr;
            curr = next;
        }

        return curr;
    }

    boolean isEmpty(){
        return size == 0;
    }
}
