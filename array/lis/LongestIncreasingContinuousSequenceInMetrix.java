package array.lis;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


/**
 * Given an integer matrix. Find the longest increasing continuous subsequence in this matrix and return the length of it.
 * The longest increasing continuous subsequence here can start at any position and go up/down/left/right.
 * 
 * Example
    Given a matrix:
    
    [
    [1 ,2 ,3 ,4 ,5],
    [16,17,24,23,6],
    [15,18,25,22,7],
    [14,19,20,21,8],
    [13,12,11,10,9]
    ]
    return 25
    
 *  Challenge  O(nm) time and memory.
 */

public class LongestIncreasingContinuousSequenceInMetrix {

    /**
     * 
     * DFS, memorize search
     * Time O(n*m) Space O(n*m)
     * 
     * @param matrix
     * @return 
     */
    public int longestIncreasingContinuousSubsequenceII_DFS(int[][] matrix) {
        if (null == matrix || 0 == matrix.length) {
            return 0;
        }

        int m = matrix.length;
        int n = matrix[0].length;
        int[][] depths = new int[m][n];
        
        int max = 0;
        for (int r = 0; r < m; r++) {
            for (int c = 0; c < n; c++) {
                max = Math.max(max, dfs(matrix, r, c, depths));
            }
        }

        return max;
    }

    final int[][] diffs = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};

    private int dfs(int[][] matrix, int r, int c, int[][] depths) {
        if (depths[r][c] > 0) {
            return depths[r][c];
        }

        int nr;
        int nc;
        for (int[] d : diffs) {
            nr = r + d[0];
            nc = c + d[1];
            
            if (nr >= 0 && nr < matrix.length && nc >= 0 && nc < matrix[0].length && matrix[r][c] < matrix[nr][nc]) {
                depths[r][c] = Math.max(depths[r][c], dfs(matrix, nr, nc, depths));
            }
        }

        return ++depths[r][c];
    }
    

    /**
     * BFS,  topological sort
     * Time O(mn * logmn) Space O(mn)
     * 
     * @param matrix
     * @return 
     */
    public int longestIncreasingContinuousSubsequenceII_topological(int[][] matrix) {
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

        int newI;
        int newJ;
        Node curr;
        for(int i = list.size() - 1; i >= 0; i--){
            curr = list.get(i);
            for(int[] d : diffs){
                newI = curr.i + d[0];
                newJ = curr.j + d[1];
                if(newI >= 0 && newI < matrix.length && newJ >= 0 && newJ < matrix[0].length && matrix[curr.i][curr.j] < matrix[newI][newJ]){
                    mem[curr.i][curr.j] = Math.max(mem[curr.i][curr.j], mem[newI][newJ] + 1);
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
        
        System.out.println(String.format("25, \t %b \t %b",  
                25 == sv.longestIncreasingContinuousSubsequenceII_DFS(matrix), 
                25 ==sv.longestIncreasingContinuousSubsequenceII_topological(matrix) ));
    }
    
}
