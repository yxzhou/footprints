package datastructure.hash;

/**
 * 
 * The size of the hash table is not determinate at the very beginning. If the total size of keys is too large (e.g. size >= capacity / 10), we should double the size of the hash table and rehash every keys. Say you have a hash table looks like below:
 * 
 * size=3, capacity=4
 * 
 * [null, 21, 14, null]
 *        ↓    ↓
 *        9   null
 *        ↓
 *       null
 * The hash function is:
 * 
 * int hashcode(int key, int capacity) {
 *     return key % capacity;
 * }
 * here we have three numbers, 9, 14 and 21, where 21 and 9 share the same position as they all have the same hashcode 1 (21 % 4 = 9 % 4 = 1). We store them in the hash table by linked list.
 * 
 * rehashing this hash table, double the capacity, you will get:
 * 
 * size=3, capacity=8
 * 
 * index:   0    1    2    3     4    5    6   7
 * hash : [null, 9, null, null, null, 21, 14, null]
 * Given the original hash table, return the new hash table after rehashing .
 * 
 * Have you met this question in a real interview? Yes
 * Example
 * Given [null, 21->9->null, 14->null, null],
 * 
 * return [null, 9->null, null, null, null, 21->null, 14->null, null]
 * 
 * Note
 * For negative integer in hash table, the position can be calculated as follow:
 * 
 * C++/Java: if you directly calculate -4 % 3 you will get -1. You can use function: a % b = (a % b + b) % b to make it is a non negative integer.
 * Python: you can directly use -1 % 3, you will get 2 automatically.
 *
 */
public class Rehash {

	 

    /**
     * @param hashTable: A list of The first node of linked list
     * @return: A list of The first node of linked list which have twice size
     */    
    public ListNode[] rehashing(ListNode[] hashTable) {
        //check
        if(null == hashTable || 0 == hashTable.length){
            return hashTable;
        }
        
        int newSize = hashTable.length << 1;
        ListNode[] result = new ListNode[newSize];
        int hashcode;
        ListNode next;
        for(ListNode node : hashTable){
            while(null != node){
                next = node.next;
                
                hashcode = hashcode(node.val, newSize);
                node.next = result[hashcode];
                result[hashcode] = node;
                
                node = next;
            }
        }
        
        return result;
    }
    
    public ListNode[] rehashing_2(ListNode[] hashTable) {
        //check
        if(null == hashTable || 0 == hashTable.length){
            return hashTable;
        }
        
        int newSize = hashTable.length << 1;
        ListNode[] result = new ListNode[newSize];
        int hashcode;
        ListNode next;
        for(ListNode node : hashTable){
            while(null != node){
                next = node.next;
                
                node.next = null;
                hashcode = hashcode(node.val, newSize);
                
                if(null == result[hashcode]){
                	result[hashcode] = node;
                }else{
                	ListNode tmp = result[hashcode];
                	while(null != tmp.next){
                		tmp = tmp.next;
                	}
                	tmp.next = node;
                }
                
                node = next;
            }
        }
        
        return result;
    }
    
    private int hashcode(int key, int capacity){
        int result = key % capacity;
        if(result < 0){
            result = (result + capacity) % capacity;
        }
        
        return result;
    }
	    
    
    /**
     * Definition for ListNode */
     public class ListNode {
         int val;
         ListNode next;
         ListNode(int x) {
             val = x;
             next = null;
         }
     }
}


