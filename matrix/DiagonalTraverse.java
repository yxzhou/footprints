/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package matrix;

import java.util.LinkedList;
import java.util.List;
import org.junit.Assert;
import util.Misc;

/**
 * _https://www.lintcode.com/problem/1205/
 *
 * Given a matrix of M x N elements (M rows, N columns), return all elements of the matrix in diagonal order as shown in
 * the following example.
 *
 * The total number of elements of the given matrix will not exceed 10,000.
 * 
 * Example 1:
 * 
 * Input:
 * [
	[ 1, 2, 3 ],
	[ 4, 5, 6 ],
	[ 7, 8, 9 ]
 * ]
 * Output: [1,2,4,7,5,3,6,8,9]
 * 
 * Example 2:
 * 
 * Input:
 * [
	[1,2,3,4],
	[5,6,7,8],
	[9,10,11,12],
	[13,14,15,16]]
 * Output: [1,2,5,9,6,3,4,7,10,13,14,11,8,12,15,16]
 * 
 */
public class DiagonalTraverse {
    /**
     * @param matrix: a 2D array
     * @return the elements of the matrix in diagonal order
     */
    public int[] findDiagonalOrder(int[][] matrix) {
        if(matrix == null || matrix.length == 0 || matrix[0].length == 0){
            return new int[0];
        }
        
        int m = matrix.length;
        int n = matrix[0].length;
        
        int[] result = new int[m * n];
        int i = 0;

        int r;
        int c;
        int j;
        int diff; 
        for(int d = 0; d < m + n - 1 ; d++){ //diagonal from 0 to n
            r = ( d < n? 0 : d + 1 - n);
            c = d - r;

            if((d & 1) == 1){
                j = i;
                diff = 1;
            }else{
                j = i + Math.min(m - r, c + 1) - 1;
                diff = -1;
            }

            i += Math.min(m - r, c + 1);

            for( ; r < m && c >= 0; r++, c--){
                result[j] = matrix[r][c];
                j += diff;
            }
        }

        return result;
    }
    
    public int[] findDiagonalOrder_2(int[][] matrix) {
        if(matrix == null || matrix.length == 0 || matrix[0].length == 0){
            return new int[0];
        }
        
        int m = matrix.length;
        int n = matrix[0].length;
        
        int[] result = new int[m * n];
        int i = 0;
        
        boolean flag = true; 
        for(int r = 0, c = 0; r < m  && c < n; ){
            if(flag){ // true, from bottom-left to up-right
                while(r > 0 && c < n - 1){
                    result[i++] = matrix[r][c];
                    r--;
                    c++;
                }
                
                result[i++] = matrix[r][c];
                if(c == n - 1){
                    r++;
                }else{
                    c++;
                }
            }else{ // false, from up-right to bottom-left
                while(r < m - 1 && c > 0){
                    result[i++] = matrix[r][c];
                    r++;
                    c--;
                }
                
                result[i++] = matrix[r][c];
                if(r == m - 1){
                    c++;
                }else{
                    r++;
                }
            }
            
            flag = !flag;
        }
        
        return result;
    }
    
    public int[] findDiagonalOrder_3(int[][] matrix) {
        if(matrix == null || matrix.length == 0 || matrix[0].length == 0){
            return new int[0];
        }
        
        int m = matrix.length;
        int n = matrix[0].length;
        
        List<Integer>[] result = new LinkedList[m + n - 1];
        for(int d = 0; d < result.length; d++){
            result[d] = new LinkedList<>();
        }
        
        int sum;
        for(int r = 0; r < m; r++){
            for(int c = 0; c < n; c++){
                sum = r + c;
                
                if((sum & 1) == 0){ //from bottom-left to up-right
                    result[sum].add(0, matrix[r][c]);
                }else{ //from up-right to bottom-left
                    result[sum].add(matrix[r][c]);
                }
            }
        }
        
        int[] ret = new int[m * n];
        int i = 0;
        for( sum = 0; sum < result.length; sum++){
            for(int x : result[sum]){
                ret[i++] = x;
            }
        }
        
        return ret;
    }
    
    
    public static void main(String[] args){
        
        int[][][][] inputs = {
            {
                {{ 1, 2, 3 },{ 4, 5, 6 },{ 7, 8, 9 }},
                {{1, 2, 4, 7, 5, 3, 6, 8, 9}}
            }, 
            {
                {{1,2},{3,4},{5,6}},
                {{1, 2, 3, 5, 4, 6}}
            },
            {
                {{ 1, 2, 3 },{ 4, 5, 6 }},
                {{1, 2, 4, 5, 3, 6}}
            },
            {
                {{1,2,3,4},{5,6,7,8},{9,10,11,12},{13,14,15,16}},
                {{1, 2, 5, 9, 6, 3, 4, 7, 10, 13, 14, 11, 8, 12, 15, 16}}
            }
        
        };
        
        DiagonalTraverse sv = new DiagonalTraverse();
        
        for(int[][][] input : inputs){
            System.out.println(Misc.array2String(input[0]));
            
            System.out.println(String.format("\t-except---- %s, \n findDiagonalOrder: %s", Misc.array2String(input[1][0]), Misc.array2String(sv.findDiagonalOrder(input[0])) ));
            
            Assert.assertArrayEquals(input[1][0], sv.findDiagonalOrder(input[0]));
            Assert.assertArrayEquals(input[1][0], sv.findDiagonalOrder_2(input[0]));
            Assert.assertArrayEquals(input[1][0], sv.findDiagonalOrder_3(input[0]));
            
        }                
        
    }
    
}
