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
    public void merge_X(int A[], int m, int B[], int n) {
        if(null == B || 0 >= n)
        	return; // needn't merge
        
        if(0 >= m){ //copy B to A
        	for(int i=0; i<n; i++){
        		A[i] = B[i];
        	}
        }
        
        //iterator check, from right to left
        int j=n-1;
        int k=m+n-1;
        for(int i=m-1; i>=0 && j>=0; k--){
        	if(A[i] >= B[j]){
        		A[k] = A[i--];
        	}else{
        		A[k] = B[j--];
        	}
        }
        
        while(j>=0){
    		A[k--] = B[j--];
        }
    }
	
  public void merge(int A[], int m, int B[], int n) {
    if(B == null || B.length == 0 || n > B.length)
      return ;
    
    if( m == 0){
        for(int j=0; j<n; j++){
            A[j] = B[j];
        }
    }
      
    
    int index = m+n-1;  // for the merged result
    int i=m-1;  // for A
    int j=n-1;  // for B
    while(i>=0 && j>=0){
      if(A[i] >= B[j] )
        A[index -- ] = A[i--];
      else
        A[index -- ] = B[j--];
    }
    
    if(i < 0){
      while(index >= 0 && j>=0)
        A[index -- ] = B[j--];
    }
  }
  
  public void mergeSortedArray_n(int[] A, int m, int[] B, int n) {
      // 
      
      int j = n - 1;
      for(int i = m -1, k = m + n - 1; j >= 0 && i >= 0; k--){
          if(A[i] > B[j]){
              A[k] = A[i];
              i--;
          }else{
              A[k] = B[j];
              j--;
          }
      }
      
      while(j >= 0){
          A[j] = B[j];
          j--;
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
    sv.merge(A[0], 1, B[0], 1);
  }

}
