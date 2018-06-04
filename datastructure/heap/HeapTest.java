package fgafa.datastructure.heap;

import java.util.Collections;
import java.util.Comparator;
import java.util.PriorityQueue;

public class HeapTest {

	@SuppressWarnings("unchecked")
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		PriorityQueue<Integer> minHeap=new PriorityQueue<Integer>();
		minHeap.poll();
		minHeap.isEmpty();

		
		int size = 10;
		PriorityQueue<Integer> maxHeap=new PriorityQueue<Integer>(size, new Comparator<Integer>(){
			@Override
			public int compare( Integer x, Integer y )
		    {
		        return y - x;
		    }
		});
		
	    PriorityQueue<Integer> maxHeap_2 = 
	                    new PriorityQueue<Integer>(Collections.reverseOrder());
	}

}
