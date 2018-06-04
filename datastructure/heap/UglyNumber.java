package fgafa.datastructure.heap;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

public class UglyNumber {

    /**
	 * Ugly number is a number that only have factors 3, 5 and 7.
	 * 
	 * Design an algorithm to find the Kth ugly number. The first 5 ugly numbers
	 * are 3, 5, 7, 9, 15 ...
	 * 
	 * Example: If K=4, return 9.
	 * 
	 * Challenge O(K log K) or O(K) time.
	 * 
	 * @param k
	 *            : The number k.
	 * @return: The kth prime number as description.
	 */
    public long kthPrimeNumber(int k) {
        //check
        if(k < 1){
            return 0;
        }
        
        int[] primes = new int[3];
        primes[0] = 3;
        primes[1] = 5;
        primes[2] = 7;
        
        List<Queue<Long>> queues = new ArrayList<>(3);
        PriorityQueue<Pair> minHeap = new PriorityQueue<>();
        
        for(int i = 0; i < 3; i++){
        	queues.add(new LinkedList<>());
        	minHeap.add(new Pair(i, primes[i]));
        }
        
        Pair curr;
        long ret = 0L;
        for(int i = 0; i<k; i++){
            curr = minHeap.poll();
            ret = curr.value;
            
            for(int j = curr.index; j < 3; j++){
            	queues.get(j).add(ret * primes[j]);
            }
            
            minHeap.add(new Pair(curr.index, queues.get(curr.index).poll()));
        }
        
        return ret;
    }
    
    class Pair implements Comparable<Pair> {
        int index;
        long value;

        Pair(int index,
             long val) {
            this.index = index;
            this.value = val;
        }

        public int compareTo(Pair other) {
            return Long.compare(this.value, other.value);
        }
    }
	
    
    /**
     * Write a program to find the nth super ugly number.

        Super ugly numbers are positive numbers whose all prime factors are in the given prime list primes of size k. 
        For example, [1, 2, 4, 7, 8, 13, 14, 16, 19, 26, 28, 32] is the sequence of the first 12 super ugly numbers given primes = [2, 7, 13, 19] of size 4.
        
        Note:
        (1) 1 is a super ugly number for any given primes.
        (2) The given numbers in primes are in ascending order.
        (3) 0 < k ≤ 100, 0 < n ≤ 106, 0 < primes[i] < 1000.
     */
    public int nthSuperUglyNumber(int n, int[] primes) {
        if(null == primes || 0 == primes.length || n < 1){
            return 1;
        }
        
        int[] uglyNumber = new int[n];
        uglyNumber[0] = 1;
        PriorityQueue<Node> q = new PriorityQueue<Node>();
        for (int i = 0; i < primes.length; i++){
            q.add(new Node(0, primes[i], primes[i]));
        }

        for (int i = 1; i < n; i++) {
            Node cur = q.peek();
            uglyNumber[i] = cur.val;
            
            do {
                cur = q.poll();
                cur.val = uglyNumber[++cur.index] * cur.prime;
                q.add(cur);
            } while (!q.isEmpty() && q.peek().val == uglyNumber[i]);
        }
        return uglyNumber[n - 1];
    }

    class Node implements Comparable<Node> {
        int index;
        int val;
        int prime;

        Node(int index,
             int val,
             int prime) {
            this.val = val;
            this.index = index;
            this.prime = prime;
        }

        public int compareTo(Node x) {
            return this.val - x.val;
        }
    }
    
    public int nthSuperUglyNumber_n(int n, int[] primes) {
        if(null == primes || 0 == primes.length || n < 1){
            return 1;
        }
         
        int k = primes.length;
        int[] index = new int[k];
         
        List<Integer> result = new ArrayList<>();
        result.add(1);
         
        for (int i = 1; i < n; i++) {
            int curr = Integer.MAX_VALUE;
            for (int j = 0; j < k; j++) {
                curr = Math.min(curr, primes[j] * result.get(index[j]));
            }
             
            result.add(curr);
             
            // update the index
            for (int j = 0; j < k; j++) {
                if (primes[j] * result.get(index[j]) == curr) {
                    index[j]++;
                }
            }
        }
         
        return result.get(result.size() - 1);
    }
    
	public static void main(String[] args) {
		
		UglyNumber sv = new UglyNumber();
		for(int k = 0; k< 8; k++){
			System.out.println(String.format("Input: %d, \t Output:%d", k, sv.kthPrimeNumber(k)));
		}

//		int n = 10;
//		int[] primes = {3,5,7}; //{3,5,7,11,19,23,29,41,43,47};
//		
//		sv.nthSuperUglyNumber(n, primes);
		
	}

}
