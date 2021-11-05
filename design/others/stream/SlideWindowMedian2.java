package design.others.stream;

/**
 * Another solution on SlideWindowMedian1,  minHeap + maxHeap, TreeSet
 * 
 */

import java.util.TreeSet;

public class SlideWindowMedian2 {

    class MyMedian {
        class Node{
            int id;
            int value;
            
            Node(int id, int value){
                this.id = id;
                this.value = value;
            }
        }

        //all node's value in maxHeap is smaller than that in minHeap
        //Here both are sorted in ascending order. the min is the first, the max is the last
        TreeSet<Node> bigger = new TreeSet<>((a, b) -> a.value == b.value? a.id - b.id : a.value - b.value ); 
        TreeSet<Node> smaller = new TreeSet<>((a, b) -> a.value == b.value? a.id - b.id : a.value - b.value ); 

        public void add(int id, int num) {
            smaller.add(new Node(id, num));

            rebalance();
        }

        /**
         * Time O(1)
         * 
         * @param id
         * @param num 
         */
        public void delete(int id, int num) {
            Node toDelete = new Node(id, num);
            //if (!bigger.isEmpty() && bigger.first().value <= num) {
                bigger.remove(toDelete);
            //} else {
                smaller.remove(toDelete);
            //}

            rebalance();
        }
        
        private void rebalance(){
            if (bigger.isEmpty() && smaller.isEmpty()) {
                return; // for case: nums = {1, 2}, k = 1
            }

            while (smaller.size() >= bigger.size()) {
                bigger.add(smaller.pollLast());
            }
            
            while (bigger.size() > smaller.size()) {
                smaller.add(bigger.pollFirst());
            }
        }

        public int findMedian() {

            if (0 == smaller.size()) {
                throw new IllegalArgumentException("No data input");
                //}else if(minHeap.size() == maxHeap.size()){
                //    return ((double)minHeap.peek() + maxHeap.peek()) / 2;
            } else {
                return smaller.last().value;
            }
        }
    }
    
    public int[] medianSlidingWindow(int[] nums, int k) {
        if(null == nums || 0 == nums.length || k <= 0){
            return new int[0]; 
        }
        
        MyMedian median = new MyMedian();
        int n = nums.length;
        int[] result = new int[n - k + 1];

        for (int i = 0, j = 0, s = k - 1; i < nums.length; i++) {
            median.add(i, nums[i]);

            if (i >= s) {
                result[j++] = median.findMedian();
                median.delete(i - s, nums[i - s]);
            }
        }
        
        return result;
    }
    
    public static void main(String[] args) {
        
        // see SlideWindowMedian1

    }
    
}

