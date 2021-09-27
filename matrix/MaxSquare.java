package matrix;

import util.Misc;

import java.util.Stack;

public class MaxSquare {

	/**
	 * 
	 * Given a 2D binary matrix filled with 0's and 1's, find the largest square containing all 1's and return its area.

		Example
		For example, given the following matrix:
		
		1 0 1 0 0
		1 0 1 1 1
		1 1 1 1 1
		1 0 0 1 0
		Return 4.
	 */
    /**
     * @param matrix: a matrix of 0 and 1
     * @return: an integer
     */	
    
	/* Time O(n * m)  Space O(n),  space can be min(m, n)*/
    public int maxSquare(int[][] matrix) {
        //check
    	if(null == matrix || 0 == matrix.length || 0 == matrix[0].length){
    		return 0;
    	}
    	
    	int max = 0;
    	int rowsNum = matrix.length;
    	int colsNum = matrix[0].length;
    	int[] heights = new int[colsNum]; //default all are 0
    	for(int i = 0; i < rowsNum; i++){
    		for(int j = 0; j < colsNum; j++){
    			if(0 == matrix[i][j]){
    				heights[j] = 0;
    			}else{
    				heights[j]++;
    			}
    		}
    		
    		max = Math.max(max, maxEdge(heights));
    	}

    	return max * max;
    }
    
    /**
     * get the max square edge
     * */
    private int maxEdge(int[] heights){
    	Stack<Integer> stack = new Stack<Integer>(); // store the index
    	int max = 0;
    	int pop;
    	int size = heights.length;
    	for(int i = 0; i < size; ){
    		if(stack.empty() || heights[i] >= heights[stack.peek()]){
    			stack.push(i);
    			i++;
    		}else if(heights[i] < heights[stack.peek()]){
    			pop = stack.pop();
    			max = Math.max(max, Math.min(heights[pop], stack.empty() ? i : i - stack.peek() - 1));
    		}
    	}
    	
    	while(!stack.empty()){
			pop = stack.pop();
			max = Math.max(max, Math.min(heights[pop], stack.empty() ? size : size - stack.peek() - 1));
    	}
    	
    	return max;
    }

	public int maxSquare_n(int[][] matrix) {
		if(null == matrix || 0 == matrix.length || 0 == matrix[0].length ){
			return 0;
		}

		int rowNumber = matrix.length;
		int columnNumber = matrix[0].length;

		int[] heights = new int[columnNumber + 1];
		int max = 0;

		for(int row = 0; row < rowNumber; row++ ){
			for(int column = 0; column < columnNumber; column++ ){
				if(matrix[row][column] == 1){
					heights[column]++;
				}else{
					heights[column] = 0;
				}
			}

			Stack<Integer> stack = new Stack<>();
			for(int i = 0; i <= columnNumber; ){
				if(stack.isEmpty() || heights[stack.peek()] <= heights[i]){
					stack.add(i);
					i++;
				}else{ //heights[stack.peek()] > heights[i]
					int top = stack.pop();

					max = Math.max( max, Math.min(heights[top], stack.isEmpty() ? i : i - stack.peek() - 1) );
				}
			}
		}

		return max * max;
	}

    /**
     * 当我们判断以某个点为正方形右下角时最大的正方形时， 那它的上方， 左方和左上方三个点也一定是某个正方形的右下角，
     * 否则该点为右下角的正方形最大就是它自己了。 这是定性的判断， 那具体的最大正方形边长呢？ 我们知道， 该点为右下角的正方形的最大边长，
     * 最多比它的上方， 左方和左上方为右下角的正方形的边长多1， 最好的情况是是它的上方 ， 左方和左上方为右下角的正方形的大小都一样的，
     * 这样加上该点就可以构成一个更大的正方形。 但如果它的上方， 左方和左上方为右下角的正方形的大小不一样 ， 合起来就会缺了某个角落，
     * 这时候只能取那三个正方形中最小的正方形的边长加1
     */
    /*DP, Time O(n * m)  Space O(n*m), can be optimized to O(n)*/
    public int maxSquare_dp(int[][] matrix) {
        if (matrix == null || matrix.length == 0 || matrix[0].length == 0){
            return 0;            
        }
     
        int m = matrix.length;
        int n = matrix[0].length;
        int[][] t = new int[m][n];//default all are 0
     
        //top row
        for (int i = 0; i < m; i++) {
            t[i][0] = matrix[i][0] == 1? 1 : 0;
        }
     
        //left column
        for (int j = 0; j < n; j++) {
            t[0][j] = matrix[0][j] == 1? 1 : 0;
        }
     
        //cells inside
        for (int i = 1; i < m; i++) {
            for (int j = 1; j < n; j++) {
                if (matrix[i][j] == 1) {
                    t[i][j] = Math.min(Math.min(t[i - 1][j], t[i - 1][j - 1]),t[i][j - 1]) + 1; //**
                } 
            }
        }
     
        //get maximal length
        int max = 0;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                max = Math.max(max, t[i][j]);
            }
        }
     
        return max * max;
    }

    public int maxSquare_dp_n(int[][] matrix) {
        if (matrix == null || matrix.length == 0 || matrix[0].length == 0){
            return 0;            
        }
     
        int m = matrix.length;
        int n = matrix[0].length;
        int[][] t = new int[m+1][n+1];//default all are 0
     
        //get maximal length
        int max = 0;
        for (int i = m - 1; i >= 0; i--) {
            for (int j = n - 1; j >= 0; j--) {
                if (matrix[i][j] == 1) {
                    t[i][j] = Math.min(Math.min(t[i + 1][j], t[i + 1][j + 1]),t[i][j + 1]) + 1; //**
                    max = Math.max(max, t[i][j]);
                } 
            }
        }
     
        return max * max;
    }
    
	public static void main(String[] args) {
		MaxSquare sv = new MaxSquare();
		
		int[][][] matrix = { 
		        //{{0}},
				{
					{ 1, 1, 1 },
					{ 1, 1, 1 },
					{ 1, 1, 1 },
					{ 1, 0, 0 }
				},

				{
					{ 1, 0, 1, 0, 0 }, 
					{ 1, 0, 1, 1, 1 },
					{ 1, 1, 1, 1, 1 }, 
					{ 1, 0, 0, 1, 0 }
				},
				
				{
					{ 0, 1, 0, 1, 1, 0 }, 
					{ 1, 0, 1, 0, 1, 1 },
					{ 1, 1, 1, 1, 1, 0 }, 
					{ 1, 1, 1, 1, 1, 1 },
					{ 0, 0, 1, 1, 1, 0 }, 
					{ 1, 1, 1, 0, 1, 1 }
				}, 
				
				{
					{ 1, 1, 1, 1, 1, 0, 1, 1, 0 }, 
					{ 0, 0, 1, 1, 1, 0, 1, 1, 0 },
					{ 0, 0, 0, 1, 0, 0, 0, 1, 1 },
					{ 0, 1, 1, 1, 1, 0, 0, 0, 1 },
					{ 1, 0, 1, 1, 0, 0, 1, 1, 0 },
					{ 1, 0, 0, 0, 1, 1, 0, 1, 0 }
				}
		};

		for(int i = 0; i < matrix.length; i++){
		    System.out.println( Misc.array2String(matrix[i]) );
		    System.out.println(sv.maxSquare(matrix[i]));
			System.out.println(sv.maxSquare_n(matrix[i]));
		    
		    System.out.println(sv.maxSquare_dp(matrix[i]));
		    System.out.println(sv.maxSquare_dp_n(matrix[i]));
		}
	}

}
