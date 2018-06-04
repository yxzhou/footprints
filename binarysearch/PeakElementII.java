package fgafa.binarysearch;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * There is an integer matrix which has the following features:

	The numbers in adjacent positions are different.
	The matrix has n rows and m columns.
	For all i < m, A[0][i] < A[1][i] && A[n - 2][i] > A[n - 1][i].
	For all j < n, A[j][0] < A[j][1] && A[j][m - 2] > A[j][m - 1].
	We define a position P is a peek if:
	
	A[j][i] > A[j+1][i] && A[j][i] > A[j-1][i] && A[j][i] > A[j][i+1] && A[j][i] > A[j][i-1]
	Find a peak element in this matrix. Return the index of the peak.
	
	Example
	Given a matrix:
	[
	  [1 ,2 ,3 ,6 ,5],
	  [16,41,23,22,6],
	  [15,17,24,21,7],
	  [14,18,19,20,10],
	  [13,14,11,10,9]
	]
	return index of 41 (which is [1,1]) or index of 24 (which is [2,2])
	
	Note
	The matrix may contains multiple peeks, find any of them.
	
	Challenge
	Solve it in O(n+m) time.
	
	If you come up with an algorithm that you thought it is O(n log m) or O(m log n), can you prove it is actually O(n+m) or propose a similar but O(n+m) algorithm?
 *
 */

public class PeakElementII {

    /**
     * @param A: An integer matrix
     * @return: The index of the peak
     */
    /*
     * wrong!
     * 
     *  0 0 0 0 0
     *  0 1 2 7 0
     *  0 4 3 9 0
     *  0 6 7 8 0 
     *  0 0 0 0 0
     * 
     * Time O(n + m)
     */
    public List<Integer> findPeakII_wrong(int[][] A) {
    	List<Integer> result = new ArrayList<Integer>();
        //check
    	if(null == A || 0 == A.length || 0 == A[0].length){
    		return result;
    	}
    	
    	int m = A.length - 1;
    	int n = A[0].length - 1;
    	int diffOnRow;
    	int diffOnCol;
    	for(int row = 1, col = 1; row < m && col < n;  ){
    		diffOnRow = A[row][col] - A[row + 1][col];
    		diffOnCol = A[row][col] - A[row][col + 1];
    		
    		if( diffOnRow > 0 && diffOnCol > 0 ){
    			result.add(row);
    			result.add(col);
    			break;
    		}else if(diffOnRow > diffOnCol){
    			col++;
    		}else{
    			row++;
    		}
    	}
    	
    	return result;
    }
    
    
    /*
     * Time O(nlogm),  n is row number, m is column number
     */
    public List<Integer> findPeakII_2(int[][] A) {
        List<Integer> result = new ArrayList<Integer>();
        //check
        if(null == A || 0 == A.length || 0 == A[0].length){
            return result;
        }
        
        int m = A[0].length;
        int up = 0;
        int down = A.length - 1;
        int midRow;
        int col = 0;
        while(up < down){
            midRow = up + (down - up) / 2;
            
            col = 0;
            for(int i = 1; i < m; i++){
                if(A[midRow][col] < A[midRow][i]){
                    col = i;
                }
            }
            
            if(A[midRow][col] > A[midRow + 1][col] && A[midRow][col] > A[midRow - 1][col]){
                result.add(midRow);
                result.add(col);
                break;
            }else if(A[midRow][col] > A[midRow + 1][col]){
                up = midRow;
            }else{
                down = midRow;
            }
        }
        
        return result;
    }
    
    
    /*
     * Time O(n + m) 
     */
    public List<Integer> findPeakII_3(int[][] A) {
        List<Integer> result = new ArrayList<Integer>();
        //check
        if(null == A || 0 == A.length || 0 == A[0].length){
            return result;
        }
        
        int n = A.length;
        int m = A[0].length;
        
        int up = 0;
        int down = n;
        int left = 0;
        int right = m;
        int midRow;
        int midCol;
        int row = 0;
        int col = 0;
        while(up < down && left < right){
            //by row
            midRow = up + (down - up) / 2;
            
            col = left;
            for(int i = left; i < right; i++){
                if(A[midRow][col] < A[midRow][i]){
                    col = i;
                }
            }
            
            if(A[midRow][col] > A[midRow + 1][col] && A[midRow][col] > A[midRow - 1][col]){
                result.add(midRow);
                result.add(col);
                break;
            }else if(A[midRow][col] < A[midRow + 1][col]){
                up = midRow;
            }else{
                down = midRow;
            }
            
            //by column
            midCol = left + (right - left) / 2;
            
            row = up;
            for(int i = up; i < down; i++){
                if(A[row][midCol] < A[i][midCol]){
                    row = i;
                }
            }
            
            if(A[row][midCol] > A[row][midCol + 1] && A[row][midCol] > A[row][midCol - 1]){
                result.add(row);
                result.add(midCol);
                break;
            }else if(A[row][midCol] < A[row][midCol + 1]){
                left = midCol;
            }else{
                right = midCol;
            }
        }
        
        return result;
    }
}
