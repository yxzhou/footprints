package linkedlist;

import java.util.LinkedList;

import java.util.Queue;

import org.junit.Test;

/**
 *
 * This problem was asked by Amazon.
 *
 * Given a linked list, remove all consecutive nodes that sum to zero. Print out the remaining nodes.
 *
 * For example, suppose you are given the input 3 -> 4 -> -7 -> 5 -> -6 -> 6.
 * In this case, you should first remove 3 -> 4 -> -7, then -6 -> 6, leaving only 5.
 *
 */

public class ConsecutiveNodesSum {

    class Node{
        int value;
        Node next;

        int sum = 0;
    }

    public Node filter(Node node){

        Node dummy = new Node();

        Queue<Node> queue = new LinkedList<>();
        queue.offer(dummy);
        dummy.next = node;

        boolean flag = true;
        while( node != null ){
            flag = true;
            for(int i = queue.size(); i > 0; i--){
                Node top = ((LinkedList<Node>) queue).pop();

                if(flag){
                    top.sum += node.value;

                    if(top.sum == 0){
                        top.next = node.next;
                        flag = false;
                    }
                }
            }

            if(flag) {
                queue.offer(node);
            }

            node = node.next;
        }

        return dummy.next;
    }

    @Test
    public void test(){
        
    }

}
