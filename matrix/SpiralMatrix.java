package matrix;

import java.util.ArrayList;
import java.util.List;

import algs4.stdlib.In;
import java.util.Collections;
import org.junit.Assert;
import util.Misc;

/**
 * _https://www.lintcode.com/problem/374
 * 
 * Given a matrix (2D array) of m x n elements (m rows, n columns), 
 * write a function that prints the elements in the array in a spiral manner
 *
 * e.g. input:
 *   a1, a2, a3, a4
 *   b1, b2, b3, b4
 *   c1, c2, c3, c4
 *   
 * define the top-left as (0,0), so the output is  
 *  from top-left to top-right, [(0, 0),(0, cols-1))  (inclusive (0,0), exclusive (0, cols-1) )  
 *  from top-right to bottom-right, [(0, cols-1),(rows-1, cols-1))
 *  from bottom-right to bottom-left, [(rows-1, cols-1),(rows-1,0))
 *  from bottom-left to top-left, [(rows-1, 0), (0,0))  
 *  
 * output: a1, a2, a3, a4, b4, c4, c3, c2, c1, b1, b2, b3   
 * 
 * 
 * 
 */


public class SpiralMatrix {


    public List<Integer> spiralOrder(int[][] matrix) {
        if(matrix == null || matrix.length == 0){
            return Collections.EMPTY_LIST;
        }

        int n = matrix.length;  // the number of row
        int m = matrix[0].length;  //the number of column

        List<Integer> result = new ArrayList<>(n * m);

        int o = 0;  //Coordinate origin,  the top_left point
        int r;
        int c;
        for( n--, m-- ; o < n && o < m; o++, n--, m--  ){

            //traversal the first row from left to right, both sides inclusive
            for( c = o; c <= m; c++){
                result.add(matrix[o][c]);
            }

            //traversal the last column from up to down, both sides exclusive
            for( r = o + 1; r < n; r++){
                result.add(matrix[r][m]);
            }

            //traversal the last row from right to left, both side inclusive
            for( c = m; c >= o; c-- ){
                result.add(matrix[n][c]);
            }

            //traversal the first column from down to up, both side exclusive  
            for( r = n - 1; r > o; r--){
                result.add(matrix[r][o]);
            }
            
        }

        if(o == n){ // single row
            for( c = o; c <= m; c++){
                result.add(matrix[o][c]);
            }
        }else if(o == m){ //single column
            for( r = o; r <= n; r++){
                result.add(matrix[r][m]);
            }
        }

        return result;
    }

    public List<Integer> spiralOrder_2(int[][] matrix) {
        List<Integer> result = new ArrayList<>();
        if (matrix == null || matrix.length == 0 || matrix[0].length == 0) {
            return result;
        }
        
        int i, j;
        int left = 0;
        int right = matrix[0].length - 1;
	int up = 0;
        int down = matrix.length - 1;
        
        while (left <= right && up <= down) {
            for (j = left; j <= right && up <= down; j++) {
                result.add(matrix[up][j]);
            }
            up++;

            for (i = up; i <= down && left <= right; i++) {
                result.add(matrix[i][right]);
            }
            right--;

            for (j = right; j >= left && up <= down; j--) {
                result.add(matrix[down][j]);
            }
            down--;

            for (i = down; i >= up && left <= right; i--) {
                result.add(matrix[i][left]);
            }
            left++;
        }
        return result;
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        System.out.println("------------Start-------------- ");

        //input from file
        String inputF = "./src/testData/matrix_spiral_input.txt";
        In in = new In(inputF);
        
        int caseNum = in.readInt();
        List<int[][]> cases = new ArrayList<>();

        for (int k = 0; k < caseNum; k++) {
            int rowsNum = in.readInt();
            int colsNum = in.readInt();

            int[][] aInt = new int[rowsNum][colsNum];

            for (int i = 0; i < rowsNum; i++) {
                for (int j = 0; j < colsNum; j++) {
                    aInt[i][j] = in.readInt();
                }
            }

            cases.add(aInt);
        }

        String outputF = "./src/testData/matrix_spiral_output.txt";
        In in2 = new In(outputF);

        int[][] outputs = new int[caseNum][];
        for (int i = 0; i < caseNum; i++) {            
            outputs[i] = split(in2.readLine(), " ");
        }

        SpiralMatrix sv = new SpiralMatrix();
                   
        for (int i = 0; i < caseNum; i++) {
            int[][] matrix = cases.get(i);

            //print out the original matrix
            System.out.println(String.format("\n--%d-\n%s ", i, Misc.array2String(matrix) ));
            
            Assert.assertEquals(Misc.array2String(outputs[i]).toString(), Misc.array2String(sv.spiralOrder(matrix)).toString());
            
        }

        System.out.println("\n------------End-------------- ");
    }
    
    private static int[] split(String s, String regex){
        if(s.trim().length() == 0){
            return new int[0];
        }
        
        String[] tokens = s.split(regex);
        
        int[] result = new int[tokens.length];
        
        for(int i = 0; i < tokens.length; i++){
            result[i] = Integer.parseInt(tokens[i]);
        }
        
        return result;
    }

}
