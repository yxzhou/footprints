package fgafa.array;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


/**
 * Give you an integer matrix (with row size n, column size m)，find the longest increasing continuous subsequence in this matrix. (The definition of the longest increasing continuous subsequence here can start at any row or column and go up/down/right/left any direction).

    Example
    Given a matrix:
    
    [
    [1 ,2 ,3 ,4 ,5],
    [16,17,24,23,6],
    [15,18,25,22,7],
    [14,19,20,21,8],
    [13,12,11,10,9]
    ]
    return 25
    
    Challenge
    O(nm) time and memory.
 */

public class LongestIncreasingContinuousSequenceInMetrix {


    public int longestIncreasingContinuousSubsequenceII(int[][] matrix) {
        //check
    	if(null == matrix || 0 == matrix.length || 0 == matrix[0].length){
    		return 0;
    	}
    	
    	int m = matrix.length;
    	int n = matrix[0].length;
    	int max = 0;
    	
    	int[][] mem = new int[m][n];
    	int[][] diffs = {{1, 0},{-1, 0},{0, 1},{0, -1}};
    	for(int i = 0; i < m; i++){
    		for(int j = 0; j < n; j++){
    			max = Math.max(max, dfs(matrix, mem, i, j, diffs));
    		}
    	}
    	
    	return max;
    }
    
    //memorize search
    private int dfs(int[][] A, int[][] mem, int i, int j, int[][] diffs){
    	if(0 < mem[i][j]){
    		return mem[i][j];
    	}
    	
    	int newI, newJ;
    	for(int[] d : diffs){
    		newI = i + d[0];
    		newJ = j + d[1];
    		if(newI >= 0 && newI < A.length && newJ >= 0 && newJ < A[0].length){
    			if(A[i][j] < A[newI][newJ]){
    				mem[i][j] = Math.max(mem[i][j], dfs(A, mem, newI, newJ, diffs));
    			}
    		}
    	}
    	
    	return ++mem[i][j];
    }
    

    //topological sort
    public int longestIncreasingContinuousSubsequenceII_2(int[][] matrix) {
        //check
        if(null == matrix || 0 == matrix.length || 0 == matrix[0].length){
            return 0;
        }
        
        int m = matrix.length;
        int n = matrix[0].length;
        
        List<Node> list = new ArrayList<>(m);
        for(int i = 0; i < m; i++){
            for(int j = 0; j < n; j++){
                list.add(new Node(matrix[i][j], i, j));
            }
        }

        Collections.sort(list);
        
        int[][] mem = new int[m][n]; // default all are 0
        int[][] diffs = {{1, 0},{-1, 0},{0, 1},{0, -1}};
        int newI;
        int newJ;
        Node curr;
        for(int i = list.size() - 1; i >= 0; i--){
            curr = list.get(i);
            for(int[] d : diffs){
                newI = curr.i + d[0];
                newJ = curr.j + d[1];
                if(newI >= 0 && newI < matrix.length && newJ >= 0 && newJ < matrix[0].length){
                    if(matrix[curr.i][curr.j] < matrix[newI][newJ]){
                        mem[curr.i][curr.j] = Math.max(mem[curr.i][curr.j], mem[newI][newJ] + 1);
                    }
                }
            }
        }
        
        curr = list.get(0);
        return mem[curr.i][curr.j] + 1;
    }
    
    class Node implements Comparable<Node>{
        int value;
        int i; //row number
        int j; //col number
        
        Node(int value, int i, int j){
            this.value = value;
            this.i = i;
            this.j = j;
        }
        
        @Override
        public int compareTo(Node o){
            return (int)(this.value - o.value);
        }
    }
    
    public static void main(String[] args){
        int[][] matrix = {
                    {1 ,2 ,3 ,4 ,5},
                    {16,17,24,23,6},
                    {15,18,25,22,7},
                    {14,19,20,21,8},
                    {13,12,11,10,9}
        };
        
        LongestIncreasingContinuousSequenceInMetrix sv = new LongestIncreasingContinuousSequenceInMetrix();
        
        System.out.println(sv.longestIncreasingContinuousSubsequenceII(matrix) + "\t" 
                    + sv.longestIncreasingContinuousSubsequenceII_2(matrix));
    }
    
}
