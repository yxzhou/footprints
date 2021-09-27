package linkedlist;

import java.util.HashMap;
import java.util.Map;

/*
 * A linked list is given such that each node contains an additional random pointer which could point to any node in the list or null.
 * 
 * Return a deep copy of the list.
 * 
 * 
 * testcases:
 *   1) input null
 *   2) input a->b->c (a->c, b->null, c->a)
 * 
 */
public class CloneRandomList {
    public RandomListNode copyRandomList_wrong(RandomListNode head) {
        //check
    	if(null == head)
    		return null;
    	
    	// insert the new Node,  a->b->c (a->c, b->null) to a->A->b->B->c->C (a->c, A->c, b->null, B->null )
    	RandomListNode curr = head;
    	while( null != curr){
    		RandomListNode newNode = new RandomListNode(curr.label);
    		
    		newNode.next = curr.next;
    		newNode.random = curr.random;
    		
    		curr.next = newNode;
    		curr = newNode.next;
    	}
    	
    	//split the oldNodes and newNodes
    	//a->A->b->B->c->C (a->c, A->c, b->null, B->null ) TO a->b->c (a->c, b->null) and A->B->C (A->C, B->null)
    	RandomListNode newHead = head.next;
    	RandomListNode oldNode = head;
    	RandomListNode newNode = newHead;
    	while( null != newNode){
    	    if(null != newNode.random)
    			newNode.random = newNode.random.next;
    			
    		oldNode.next = newNode.next;
    		
    		if( null != newNode.next )
    		    newNode.next = newNode.next.next;
    		
    		oldNode = oldNode.next;
    		newNode = newNode.next;
    	}
    	
    	return newHead;
    }
    
    /**
     * @param head: The head of linked list with a random pointer.
     * @return: A new head of a deep copy of the list.
     */
    public RandomListNode copyRandomList_n(RandomListNode head) {
        //check
        if(null == head){
            return head;
        }
        
        RandomListNode oldNode = head;
        RandomListNode newNode;
        
        //clone the nodes
        while(oldNode != null){
            newNode = new RandomListNode(oldNode.label);
            
            newNode.next = oldNode.next;
            oldNode.next = newNode;
            
            oldNode = newNode.next;
        }
        
        //clone the random relationship
        oldNode = head;
        while(oldNode != null){
            newNode = oldNode.next;
            
            if(oldNode.random != null){
                newNode.random = oldNode.random.next;	
            }
            
            oldNode = newNode.next;
        }
        
        //split
        oldNode = head;
        RandomListNode newHead = head.next;
        while(oldNode != null){
            newNode = oldNode.next;
            oldNode.next = newNode.next;
            
            oldNode = oldNode.next;
            
            if(oldNode != null){
                newNode.next = oldNode.next;	
            }

        }
        
        return newHead;
    }
    
    
    public RandomListNode copyRandomList_hash(RandomListNode head) {
        //check
    	if(null == head){
            return null;
    	}
    	
    	// mirror the new Nodes,  a->b->c (a->c, b->null) to A->B->C and has the mapping of oldNode and newNode (A->a, B->b, C->c )
    	Map<RandomListNode, RandomListNode> hm = new HashMap<>();

    	RandomListNode newHead = new RandomListNode(0);
		
    	RandomListNode oldNode = head;
		RandomListNode newNode = newHead;
    	while( null != oldNode ){
    		newNode.next = new RandomListNode(oldNode.label);
    		newNode = newNode.next;
    		
    		hm.put(oldNode, newNode);
    		
    		oldNode = oldNode.next;
    	}
    	
    	//set the random pointer
    	for(oldNode = head ; null != oldNode ; oldNode = oldNode.next){
    		if(null != oldNode.random){
    		    hm.get(oldNode).random = hm.get(oldNode.random);
    		}
    	}
    	
    	return newHead.next;
    }
    
    /**
     * Definition for singly-linked list with a random pointer.
     */
	class RandomListNode {
		int label;
		RandomListNode next, random;

		RandomListNode(int x) {
			this.label = x;
		}
	};

}
