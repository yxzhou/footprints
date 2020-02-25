package fgafa.datastructure.heap;

import javafx.util.Pair;

import java.util.*;

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


	    PriorityQueue<Pair<Integer, Integer>> minHeap_2 = new PriorityQueue<>((p1, p2) -> Integer.compare(p1.getValue(), p2.getValue()));


		List<Iterator<int[]>> its = new LinkedList();
		PriorityQueue<int[]> maxHeap_3 = new PriorityQueue<>((a1, a2) -> Integer.compare(a2[1], a1[1]) );

	}

}
