package datastructure.heap;

import util.Misc;

import java.util.*;

/**
 * 
 * _https://leetcode.com/problems/the-skyline-problem/
 * 
 * A city's skyline is the outer contour of the silhouette formed by all the
 * buildings in that city when viewed from a distance. Now suppose you are given
 * the locations and height of all the buildings as shown on a cityscape photo
 * (Figure A), write a program to output the skyline formed by these buildings
 * collectively (Figure B).
 *
 * The geometric information of each building is represented by a triplet of
 * integers [Li, Ri, Hi], where Li and Ri are the x coordinates of the left and
 * right edge of the ith building, respectively, and Hi is its height. It is
 * guaranteed that 0 ≤ Li, Ri ≤ INT_MAX, 0 < Hi ≤ INT_MAX, and Ri - Li > 0. You
 * may assume all buildings are perfect rectangles grounded on an absolutely
 * flat surface at height 0.
 * 
 * For instance, the dimensions of all buildings in Figure A are recorded as: [
 * [2 9 10], [3 7 15], [5 12 12], [15 20 10], [19 24 8] ] .
 * 
 * The output is a list of "key points" (red dots in Figure B) in the format of
 * [ [x1,y1], [x2, y2], [x3, y3], ... ] that uniquely defines a skyline. A key
 * point is the left endpoint of a horizontal line segment. Note that the last
 * key point, where the rightmost building ends, is merely used to mark the
 * termination of the skyline, and always has zero height. Also, the ground in
 * between any two adjacent buildings should be considered part of the skyline
 * contour.
 * 
 * For instance, the skyline in Figure B should be represented as:[ [2 10], [3
 * 15], [7 12], [12 0], [15 10], [20 8], [24, 0] ].
 * 
 * Notes:
 * The number of buildings in any input list is guaranteed to be in the range [0, 10000]. 
 * The input list is already sorted in ascending order by the left x position Li. 
 * The output list must be sorted by the x position. 
 * There must be no consecutive horizontal lines of equal height in the output skyline. 
 * For instance, [...[2 3], [4 5], [7 5], [11 5], [12 7]...] is not acceptable; 
 * the three lines of height 5 should be merged into one in the final output as such: [...[2 3], [4 5], [12 7], ...]
 */
public class Skyline {

	/**
	 * it's only work when all the coordinates of the buildings are integer less than 10000. 
	 */
    public List<int[]> getSkyline(int[][] buildings) {
    	List<int[]> ret = new ArrayList<>();
    	
    	//check
    	if(null == buildings || 0 == buildings.length){
    		return ret;
    	}
    	
    	//main
    	final int MAX_X = 10000;
    	int[] heights = new int[MAX_X + 1];
    	for(int[] building : buildings ){
    		for(int x = building[0]; x< building[1]; x++){
    			if(heights[x] < building[2]){
    				heights[x] = building[2];
    			}
    		}
    	}
    	
    	int[] leftTop = new int[2]; // default both are 0 ??
		leftTop[0] = 0;
		leftTop[1] = 0;
    	
		for (int x = 0; x < heights.length; x++) {
			if (leftTop[1] != heights[x]) {
				leftTop[0] = x;
				leftTop[1] = heights[x];
				ret.add(Arrays.copyOf(leftTop, 2)); 
			}
		}
    	
    	//return
    	return ret;
    }
    

	/**
	 * it's only work when the number of buildings are not too much. 
	 */
	public List<int[]> getSkyline_2(int[][] buildings) {
    	List<int[]> res = new ArrayList<>();
    	
    	//check
    	if(null == buildings || 0 == buildings.length){
    		return res;
    	}
    	
    	//order by x (x_left and x_right)
		List<int[]> bl = new ArrayList<int[]>();
		for (int[] b : buildings) {
			bl.add(new int[] { b[0], b[2] });
			bl.add(new int[] { b[1], -b[2] });
		}

		Collections.sort(bl, new Comparator<int[]>() {
			@Override
			public int compare(int[] a, int[] b) {
				if (a[0] != b[0])
					return a[0] - b[0]; // x in order to ascend
				else
					return b[1] - a[1]; // y in order to descend
			}
		});
		
		//build a height max heap
		Queue<Integer> heightHeap = new PriorityQueue<>(11,
				new Comparator<Integer>() {
					@Override
					public int compare(Integer a, Integer b) {
						return b - a;
					}
				});
		int pre = 0, cur = 0; //for merging [4 5], [7 5] into one in the final output
		for (int[] b : bl) {
			if (b[1] > 0) { // it's the x_left
				heightHeap.add(b[1]);
			} else { // it's the x_right
				heightHeap.remove(-b[1]);
			}
			
			cur = heightHeap.isEmpty() ? 0 : heightHeap.peek();
			if (cur != pre) {
				res.add(new int[] { b[0], cur });
				pre = cur;
			}
		}
		return res;
	}

    /**
     * @param buildings: A list of lists of integers
     * @return: Find the outline of those buildings
     */
    public ArrayList<ArrayList<Integer>> buildingOutline(int[][] buildings) {
        ArrayList<ArrayList<Integer>> result = new ArrayList<ArrayList<Integer>>();
        
        //check
        if(null == buildings || 0 == buildings.length){
            return result;
        }
           
        int max = 0;
        for(int[] building : buildings){
            max = Math.max(max, building[1]); // building[1] > building[0]
        }
        
        int[] heights = new int[max + 1]; //default all are 0
        for(int[] building : buildings){
            for(int i = building[0]; i < building[1]; i++){
                heights[i] = Math.max(heights[i], building[2]);
            }
        }
        
        int height = heights[0];
        int start = 0;
        for(int i = 0; i<heights.length; i++){
            if(heights[i] != height){
                if(height != 0 && start < i){
                    ArrayList<Integer> list = new ArrayList<Integer>();
                    list.add(start);
                    list.add(i);
                    list.add(height);
                    
                    result.add(list);
                }
                
                start = i;
                height = heights[i];
            }
        }
        
        return result;
    }	
	
    /**
     * @param buildings: A list of lists of integers
     * @return: Find the outline of those buildings
     */
    public ArrayList<ArrayList<Integer>> buildingOutline_2(int[][] buildings) {
        ArrayList<ArrayList<Integer>> result = new ArrayList<ArrayList<Integer>>();
        
        //check
        if(null == buildings || 0 == buildings.length){
            return result;
        }
           
        //order by x (x_left and x_right)
        List<int[]> list = new ArrayList<int[]>();
        for(int[] b : buildings){
            list.add(new int[]{b[0], b[2]});
            list.add(new int[]{b[1], -b[2]});
        }
        
        Collections.sort(list, new Comparator<int[]>(){
            @Override
            public int compare(int[] a, int[] b){
                if(a[0] == b[0]){
                    return a[1] - b[1];
                }else{
                    return a[0] - b[0];
                }
            }
        });
        
        //build a height max heap
        PriorityQueue<Integer> maxHeap = new PriorityQueue<Integer>(11, new Comparator<Integer>(){
            @Override
            public int compare(Integer a, Integer b){
                return b - a;
            }
        });
        
        int currHeight;        
        int preHeight = 0;
        int start = 0;
        for (int[] pair : list) {
            if (pair[1] > 0) { // it's x_left
                maxHeap.add(pair[1]);
            } else {
                maxHeap.remove(-pair[1]);
            }

            currHeight = maxHeap.isEmpty() ? 0 : maxHeap.peek();

            if (currHeight != preHeight) {
                if (preHeight != 0 && start < pair[0]) {

                    ArrayList<Integer> tmp = new ArrayList<Integer>();
                    tmp.add(start);
                    tmp.add(pair[0]);
                    tmp.add(preHeight);

                    result.add(tmp);
                }

                start = pair[0];
                preHeight = currHeight;
            }
        }
        
        return result;
    }


	public List<List<Integer>> getSkyline_20200206(int[][] buildings) {

		int n = buildings.length;

		int[][] edges = new int[n * 2][2]; // edges[i]={x, height}

		int i = 0;
		for(int[] b : buildings){
			edges[i++] = new int[]{b[0], b[2]};
			edges[i++] = new int[]{b[1], -b[2]};
		}

		Arrays.sort(edges, (e1, e2) -> { return e1[0] == e2[0] ? e2[1] - e1[1] : e1[0] - e2[0];});

		PriorityQueue<Integer> maxHeap = new PriorityQueue<>(Collections.reverseOrder());

		List<List<Integer>> result = new LinkedList<>();

		int pre = 0;
		int curr = 0;
		for(int[] e : edges){

			if(e[1] > 0){
				maxHeap.add(e[1]);
			}else if(e[1] < 0){
				maxHeap.remove(-e[1]);
			}else{
				continue;
			}

			curr = maxHeap.isEmpty()? 0 : maxHeap.peek();

			if(curr != pre){
				pre = curr;

				result.add(Arrays.asList(e[0], curr));
			}

		}

		return result;
	}



	public static void main(String[] args) {
		int[][] input = {{2, 9, 10}, {3, 7, 15}, {5, 12, 12}, {15, 20, 10}, {19, 24, 8} };
		int[][] expect ={{2, 10}, {3, 15}, {7, 12}, {12, 0}, {15, 10}, {20, 8}, {24, 0}};
		
		Skyline sv = new Skyline();

		System.out.println(" Input: " + Misc.array2String(input));
		System.out.println("Output: ");
		Misc.printIntArrayList(sv.getSkyline(input));
		System.out.println();
		Misc.printIntArrayList(sv.getSkyline_2(input));
		
	    System.out.println();
	    Misc.printListList(sv.buildingOutline(input));
	        
	    System.out.println();
	    Misc.printListList(sv.buildingOutline_2(input));
	    
	    
	    int[][] input2 = {{4,67,187}, {3,80,65}, {49,77,117},{67,74,9},{6,42,92},{48,67,69},{10,13,58},{47,99,152},{66,99,53},{66,71,34},{27,63,2},{35,81,116},{47,49,10},{68,97,175},{20,33,53},{24,94,20},{74,77,155},{39,98,144},{52,89,84},{13,65,222},{24,41,75},{16,24,142},{40,95,4},{6,56,188},{1,38,219},{19,79,149},{50,61,174},{4,25,14},{4,46,225},{12,32,215},{57,76,47},{11,30,179},{88,99,99},{2,19,228},{16,57,114},{31,69,58},{12,61,198},{70,88,131},{7,37,42},{5,48,211},{2,64,106},{49,73,204},{76,88,26},{58,61,215},{39,51,125},{13,38,48},{74,99,145},{4,12,8},{12,33,161},{61,95,190},{16,19,196},{3,84,8},{5,36,118},{82,87,40},{8,44,212},{15,70,222},{16,25,176},{9,100,74},{38,78,99},{23,77,43},{45,89,229},{7,84,163},{48,72,1},{31,88,123},{35,62,190},{21,29,41},{37,97,81},{7,49,78},{83,84,132},{33,61,27},{18,45,1},{52,64,4},{58,98,57},{14,22,1},{9,85,200},{50,76,147},{54,70,201},{5,55,97},{9,42,125},{31,88,146}};
	    int[][] expect2 = {{1,2,219},{2,19,228},{19,45,225},{45,89,229},{89,95,190},{95,97,175},{97,99,152},{99,100,74}};
	    
	       System.out.println();
	        Misc.printListList(sv.buildingOutline(input2));
	            
	        System.out.println();
	        Misc.printListList(sv.buildingOutline_2(input2));
	    
	}

}
