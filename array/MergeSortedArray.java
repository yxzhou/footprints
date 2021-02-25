package fgafa.array;

import java.util.ArrayList;

public class MergeSortedArray
{
	
	/**
	 * 
	 * Given two sorted integer arrays A and B, merge B into A as one sorted array.
	 * 
	 * Note:
	 * You may assume that A has enough space to hold additional elements from B. 
	 * The number of elements initialized in A and B are m and n respectively.
	 * 
	 *
	 */
    public void mergeSortedArray_n(int A[], int m, int B[], int n) {
        if(A == null || B == null || n <= 0){
            return;
        }
        
        //iterator check, from right to left
        int a = m - 1;
        int b = n - 1;
        for( int c = m + n - 1; a >= 0 && b >= 0; c-- ){
            if(A[a] <= B[b] ){
                A[c] = B[b];
                b--;
            }else{
                A[c] = A[a];
                a--;
            }
        }

        if(b >= 0){
            System.arraycopy(B, 0, A, 0, b + 1);
        }
    }
  
	/**
	 * Merge two given sorted integer array A and B into a new sorted integer
	 * array.
	 * 
	 * Example A=[1,2,3,4], B=[2,4,5,6]   return [1,2,2,3,4,4,5,6]
	 * 
	 * Challenge How can you optimize your algorithm if one array is very large
	 * and the other is very small?
	 */
	public ArrayList<Integer> mergeSortedArray(ArrayList<Integer> A, ArrayList<Integer> B) {
      //check
      if(null == A){
    	  if(null == B){
    		  return new ArrayList<Integer>();
    	  }else{
        	  return new ArrayList<Integer>(B);
    	  }
      }
      
      if(null == B){
    	  return new ArrayList<Integer>(A);
      }
      
      if(A.size() < B.size()){
    	  return mergeSortedArray(B, A);
      }
      
      ArrayList<Integer> result = new ArrayList<Integer>(A.size() + B.size());
      int i = 0;
      int j = 0;
      while( i < A.size() && j < B.size()){
    	  if(A.get(i) < B.get(j)){
    		  result.add(A.get(i));
    		  i++;
    	  }else{
    		  result.add(B.get(j));
    		  j++;
    	  }
      }
      
      if(j < B.size()){
    	  result.addAll(B.subList(j, B.size()));
      }else{ // i < A.size()
    	  result.addAll(A.subList(i, A.size()));
      }
      
      return result;
  }
  
  
  /**
   * @param args
   */
  public static void main(String[] args) {
    int[][] A = {{1, 0}};
    int[][] B = {{2}};
        
    MergeSortedArray sv = new MergeSortedArray();
    sv.mergeSortedArray_n(A[0], 1, B[0], 1);
  }

}
