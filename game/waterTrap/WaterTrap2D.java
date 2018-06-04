package fgafa.game.waterTrap;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.Stack;

/**
 * 
 * Given n x m non-negative integers representing an elevation map 2d where the
 * area of each cell is 1 x 1, compute how much water it is able to trap after
 * raining.
 *
 *Example
 *Given 5*4 matrix
 *
 *[12,13,0,12]
 *[13,4,13,12]
 *[13,8,10,12]
 *[12,13,12,12]
 *[13,13,13,13]
 *
 *return 14.
 *
 */
public class WaterTrap2D {

    /**
     * @param heights: a matrix of integers
     * @return: an integer
     */
    public int trapRainWater(int[][] heights) {
        // check, the matrix must be 3+ rows and 3+ columns
        if( null == heights ){
            return 0;
        }
        int rowNum = heights.length;
        int colNum = heights[0].length;
        if(3 > rowNum || 3 > colNum){
            return 0;
        }
        
        Map<Integer, Set<String>> cells = new HashMap<>();
        Set<String> tmp;
        for(int row = 1; row < rowNum - 1; row++){
            for(int col = 1; col < colNum - 1; col++){
                tmp = cells.get(heights[row][col]);
                
                if(null == tmp){
                    tmp = new HashSet<>();
                    cells.put(heights[row][col], tmp);
                }
                
                tmp.add(build(row, col));
            }
        }
        
        List<Integer> cellHeights = new ArrayList<>(cells.keySet());
        Collections.sort(cellHeights);
        
        int sum = 0;
        int[] p;
        Set<String> set;
        int lowestBorder;
        for(Integer height : cellHeights){
        	tmp = cells.get(height);
            if(null != tmp){
            	for(String xy : tmp){
            		p = split(xy);
            		if(height == heights[p[0]][p[1]]){
            			set = new HashSet<>();
            			lowestBorder = findMinBorderHeight(heights, set, xy);
            			
            			if(lowestBorder > height){
                			sum += set.size() * (lowestBorder - height);
                			
                			for(String cell : set){
                				p = split(cell);
                				heights[p[0]][p[1]] = lowestBorder;
                			}
            			}
            		}
            	}
            }
        }
        
        return sum;
    }
    
    //dfs with stack
    private int findMinBorderHeight(int[][] heights, Set<String> set, String s){
    	int lowestBorder = Integer.MAX_VALUE;
    	Stack<String> stack = new Stack<>();
    	stack.add(s);
    	
    	int[] p;
    	while(!stack.isEmpty()){
    		s = stack.pop();
    		set.add(s);
    		
    		p = split(s);
        	if(0 == p[0] || heights.length == p[0] + 1 || 0 == p[1] || heights[0].length == p[1] + 1){
        		lowestBorder = Math.min(lowestBorder, heights[p[0]][p[1]]);
        		continue;
        	}
        	
        	//upper
    		if(heights[p[0]][p[1]] == heights[p[0] - 1][p[1]]){
    			s = build(p[0] - 1, p[1]);
    			if(!set.contains(s)){	
        			stack.add(s);
    			}
    		}else if(heights[p[0]][p[1]] < heights[p[0] - 1][p[1]]){// <
    			lowestBorder = Math.min(lowestBorder, heights[p[0] - 1][p[1]]);
    		}else{// >
    			return -1;
    		}
    		
        	//down
    		if(heights[p[0]][p[1]] == heights[p[0] + 1][p[1]]){
    			s = build(p[0] + 1, p[1]);
    			if(!set.contains(s)){
    				stack.add(s);
    			}
    		}else if(heights[p[0]][p[1]] < heights[p[0] + 1][p[1]]){
    			lowestBorder = Math.min(lowestBorder, heights[p[0] + 1][p[1]]);
    		}else{ // >
    			return -1;
    		}
    		//left
    		if(heights[p[0]][p[1]] == heights[p[0]][p[1] - 1]){
    			s = build(p[0], p[1] - 1);
    			if(!set.contains(s)){
    				stack.add(s);
    			}
    		}else if(heights[p[0]][p[1]] < heights[p[0]][p[1] - 1]){// <
    			lowestBorder = Math.min(lowestBorder, heights[p[0]][p[1] - 1]);
    		}else{// >
    			return -1;
    		}
    		//right
    		if(heights[p[0]][p[1]] == heights[p[0]][p[1] + 1]){
    			s = build(p[0], p[1] + 1);
    			if(!set.contains(s)){
    				stack.add(s);
    			}
    		}else if(heights[p[0]][p[1]] < heights[p[0]][p[1] + 1]){// <
    			lowestBorder = Math.min(lowestBorder, heights[p[0]][p[1] + 1]);
    		}else{ // >
    			return -1;
    		}
    	}
    		
    	return lowestBorder;
    }
    
    private String build(int x, int y){
    	return x + "," + y;
    }
    private int[] split(String xy){
    	int i = xy.indexOf(",");
    	int[] ret = new int[2];
    	ret[0] = Integer.valueOf(xy.substring(0, i));
    	ret[1] = Integer.valueOf(xy.substring(i+1));
    	
    	return ret;
    }
    
    
	public static void main(String[] args) {
		int[][] matric = {
						{12,13,0,12}, 
						{13,4,13,12}, 
						{13,8,10,12}, 
						{12,13,12,12}, 
						{13,13,13,13} };
		
		WaterTrap2D sv = new WaterTrap2D();
		
		System.out.println(sv.trapRainWater_x(matric));
		System.out.println(sv.trapRainWater(matric));


		String s1 = 1 + "," + 2;
		String s2 = 1 + "," + 2;
		Set<String> set1 = new HashSet<>();
		set1.add(s1);
		System.out.println(set1.contains(s2));
		
		int[] a1 = {1, 2};
		int[] a2 = {1, 2};
		Set<int[]> set2 = new HashSet<>();
	    set2.add(a1);
	    System.out.println(set2.contains(a2));
	}

	
	class Cell implements Comparable<Cell> {
		public int x, y, h;

		Cell(int xx, int yy, int hh) {
			x = xx;
			y = yy;
			h = hh;
		}

		@Override
		public int compareTo(Cell o) {
			return h - o.h;
		}
	}

	/*
	 * from outside to inside. flooding method, BFS
	 * 
	 * Time O(m * n * log(m + n) ), space O(m * n)
	 */
	public int trapRainWater_x(int[][] heights) {
		// check, the matrix must be 3+ rows and 3+ columns
		if (null == heights) {
			return 0;
		}
		int rowNum = heights.length;
		int colNum = heights[0].length;
		if (3 > rowNum || 3 > colNum) {
			return 0;
		}

		// init
		int[] dx = { 1, -1, 0, 0 };
		int[] dy = { 0, 0, 1, -1 };

		PriorityQueue<Cell> q = new PriorityQueue<>((rowNum + colNum) * 2);

		boolean[][] isVisited = new boolean[rowNum][colNum];//default all are false
		
		//check 4 edges in the outside 
		for (int row = 0; row < rowNum; row++) {
			q.offer(new Cell(row, 0, heights[row][0]));
			q.offer(new Cell(row, colNum - 1, heights[row][colNum - 1]));
			isVisited[row][0] = true;
			isVisited[row][colNum - 1] = true;
		}
		for (int col = 0; col < colNum; col++) {
			q.offer(new Cell(0, col, heights[0][col]));
			q.offer(new Cell(rowNum - 1, col, heights[rowNum - 1][col]));
			isVisited[0][col] = true;
			isVisited[rowNum - 1][col] = true;
		}
		
		//flooding
		int ans = 0;
		while (!q.isEmpty()) {
			Cell curr = q.poll();

			for (int i = 0; i < 4; i++) {
				int x = curr.x + dx[i];
				int y = curr.y + dy[i];
				if (0 <= x && x < rowNum && 0 <= y && y < colNum
						&& !isVisited[x][y]) {

					isVisited[x][y] = true;
					q.offer(new Cell(x, y, Math.max(curr.h, heights[x][y])));
					ans = ans + Math.max(0, curr.h - heights[x][y]);
				}
			}
		}
		return ans;
	}
	 
}
