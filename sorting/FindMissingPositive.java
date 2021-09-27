package sorting;

import util.Misc;

/**
 * 
 * Given an unsorted integer array, find the first missing positive integer.
 * 
 * For example,
 * Given [1,2,0] return 3,
 * and [3,4,-1,1] return 2.
 * 
 * Your algorithm should run in O(n) time and uses constant space.
 * 
 *
 */

public class FindMissingPositive
{

  /**
   * {a1, a2, a3 ---}  ==> {1, 2, --, }
   * 
   * if there are n numbers, then the max positive number not present in the array is n+1. 
   * this happens when numbers 1 through n are all present. 
   * We could do something like a count sort. for each number x in [1,n],
   * we would like to move it to position x-1, then scan the array again and see which number is missing.
   * for numbers greater than n and less than 1, we just leave them at their original position.
   * 
   * @param A
   * @param n
   * @return
   */
  public int firstMissingPositive(int A[], int n) {

    for(int i=0;i<n;i++){
        while(A[i]!=i+1){
          /*
           * A[i] <= 0 || A[i] > n,  the numbers greater than n and less than 1, we just leave them at their original position.
           * A[i] == A[A[i]-1], the numbers are same in position A[i]-1 and i, so leave them.  
           */
            if(A[i] < 1 || A[i] > n || A[i] == A[A[i]-1]) break; 
           
            swap(A, i, A[i] - 1); 
        }
    }
    
    for(int i=0;i<n;i++){
        if(A[i]!=i+1)   return i+1;
    }
    return n+1;
 }
  
	/*
	 * try to make the A in order,  
	 *   index  0,  1,   2,  3,  --
	 *   value  1,  2,   3,  4, ---
	 * the point is to put the right value in the right position
	 */
	public int firstMissingPositive(int A[]) {
		//check
		if(null == A || 0 == A.length)
			return 1;

		int position;
		for (int i = 0; i < A.length; ) {
			position = A[i] - 1;
			// swap when A[i] is a "right value", and it's in the right position, and it's not equal to value on the right position.
			if (A[i] > 0 && A[i] < A.length &&  position != i && A[position] != A[i])  
				swap(A, i, position);
			else
				i++;
		}

		for (int i = 0; i < A.length; i++) {
			if (A[i] != i + 1)
				return i + 1;
		}
		return A.length + 1;
	}
	
    public int firstMissingPositive_n(int[] A) {
        //check
        if(null == A || 0 == A.length){
            return 1;
        }
        
        int position;
        int size = A.length;
        for(int i = 0; i < size; ){
        	position = A[i] - 1;
            if(A[i] > 0 && position < size && A[i] != A[position]){
                swap(A, i, position);
            }else{
                i++;
            }
        }
        
        for(int i = 0; i < size; i++){
            if( A[i] != i + 1 ){
                return i + 1;
            }
        }
        
        return size + 1;
    }
	
    public int firstMissingPositive_XOR(int[] A) {
        //TODO
        return -1;
    }
    
  /**
   * Step 1: Disregard all negatives (put all the positives in the front of the array)
   * Step 2: Say the number of positive numbers is: N
   * we can divide the array into two parts: <=N and >N (the part <=N is first, the >N is second)
   * Step 3: For every number x in the array <=N, swap it with the number at index x. Do not swap repeats.
   * Step 4: Scan through the numbers and find the first number that does not match its index.
   * 
   * @param arr
   * @return
   */
  
  protected int findMissedMinUInt_qickSort(int[] arr){
    int result = -1;
    
    //check
    if(arr == null || arr.length == 0) 
      return result;
    
    //travel arr to get the max
    int maxValue = Integer.MIN_VALUE, pivot = 1, indexfor1 = Integer.MIN_VALUE ;
    for(int i=0; i< arr.length; i++){
      maxValue = max(maxValue, arr[i]);
      if(arr[i] == pivot)
        indexfor1 = i;
    }
    if(indexfor1 == Integer.MIN_VALUE)
      return 1;
    if(maxValue ==1 )
      return 2;  // if max<=0, return 1,  if max==1, return 2;
    
    //split by 1
    int leftValue = pivot, rightValue = maxValue;
    int leftIndex = 0, rightIndex = arr.length-1, pivotIndex = 0;
    indexfor1 = partition(arr, pivot, leftIndex, rightIndex);
    leftIndex = indexfor1;
    
    while(leftIndex < rightIndex){
      pivot = leftValue + (rightValue - leftValue) /2;
      
      pivotIndex = partition(arr, pivot, leftIndex, rightIndex);
            
      if(pivotIndex - leftIndex + 1 < pivot - leftValue ) {// there must be a or some missing in [leftIndex, pivotIndex], value is [leftValue, pivot]       
        rightIndex = pivotIndex - 1;
        rightValue = pivot-1;
        
      }else{   // filter the duplicate in [leftIndex, pivotIndex] to check if missing
        
        
        rightIndex = pivotIndex - 1;
        rightValue = pivot - 1;
      }
    }
        
    
    if(pivotIndex == pivot - 1 + indexfor1){
      result = pivot + 1;
      
      if(result == maxValue)
        result ++;
      
    }else{
      result = pivot - 1;
    }
    
    return result;
  }
   
  /*
   * 
   */
  private int findMissedMinUInt_qickSort(int[] arr, int begin, int end, int minValue, int maxValue){
    int result = 0;
    
    int pivot = minValue + (maxValue - minValue) /2;
    int pivotIndex = partition(arr, pivot, begin, end);
          
    if(pivotIndex - begin + 1 < pivot - minValue ) {// there must be a or some missing in [leftIndex, pivotIndex], value is [leftValue, pivot]       
      return findMissedMinUInt_qickSort(arr, begin, pivotIndex - 1, minValue, pivot-1);
      
    }else{   // filter the duplicate in [leftIndex, pivotIndex] to check if missing
      
      
//      rightIndex = pivotIndex - 1;
//      rightValue = pivot - 1;
    }
    
    return result;
  }
  
  
  /**
   * split teh array with pivot
   * 
   * @param array
   * @param pivot
   * @param begin
   * @param end
   * @return pivotIndex, all item in [pivotIndex, end] are bigger than pivot  
   */
  private int partition(int[] array, int pivot, int begin, int end) {
   
    int pivotIndex;
    for (int i = pivotIndex = begin; i < end; i++) {
        if (array[i] <= pivot){ 
          if(pivot == i)
            pivot ++;
          else
            swap(array, pivotIndex++, i);
        }
//        else if(array[i] == pivot){
//          swap(array, i, end);
//          i--;
//        }  
    }
    //swap(array, pivotIndex, end);     
    return pivotIndex;
  }
  
  /*
   * swap ith and jth in the array. 
   */
  private void swap(int[] arr, int i, int j){
    int tmp = arr[i];
    arr[i] = arr[j];
    arr[j] = tmp;
    
  }
  
  private int max(int x, int y){
    return (x>y)? x : y; 
  }
  
  /**
   * @param args
   */
  public static void main(String[] args) {
    FindMissingPositive service = new FindMissingPositive();
    
    int[][] ints = {//null, 
                    {}, {1}, {2}
                   //, {1,0,1,0}
                   , {0}, {1,1}, {3,2}, {1, 3}, {1,2,4}, {0, 3, 2}, {1,2,0}, {3,4,-1,1}};
    for(int i=0; i<ints.length; i++){
      System.out.println("\n input: "+ Misc.array2String(ints[i]));
      //System.out.println("Miss:   " + service.findMissedMinUInt_qickSort(ints[i]));
      
      //System.out.println("output: "+ Misc.array2String(ints[i]));
      
      System.out.println("Miss:   " + service.firstMissingPositive(ints[i], ints[i].length));
      
      System.out.println("Miss:   " + service.firstMissingPositive(ints[i]));
    }

  }

}
