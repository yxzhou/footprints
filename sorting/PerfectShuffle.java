package fgafa.sorting;

import fgafa.util.Misc;

/*
 * 
 * Q1 Given a Data Structure having first n integers and next n chars. A = i1 i2 i3 ... iN c1 c2 c3 ... cN.
 * Write an in-place algorithm to rearrange the elements of the array as A = i1 c1 i2 c2 ... in cn
 * i.e.
 *  input {1, 5, 2, 15, -5, -8, -1, -3}
 * output {1, -5, 5, -8, 2, -1, 15, -3}
 * 
 * Solution 1: brute-force
 *    {1, 5, 2, 15, -5, -8, -1, -3}
 *  =>{1, -5, 5, 2, 15, -8, -1, -3}    O(n)
 *  =>{1, -5, 5, -8, 2, 15, -1, -3}    O(n - 1)
 *  ---
 *  
 *  Time O(n*n)  Space O(1)
 * 
 * Solution 2:  interleaveSort_DC(int[] arr, int n) 
 * 
   *   Divide and Conquer
   *   [X,Y|A,B] => [X,A|Y,B] 
   *   
   *   a[4n] => a[n]b[n]c[n]d[n]            half= 2n,   quarter = n
   *   a[4n+1] => a[n]b[n]c[n]d[n+1]        half= 2n,   quarter = n
   *   a[4n+2] => a[n]b[n+1]c[n]d[n+1]      half= 2n+1, quarter = n
   *   a[4n+3] => a[n]b[n+1]c[n]d[n+2]      half= 2n+1, quarter = n
   *   
 *  Time O(nlogn)  Space O(1)
 * 
 * Solution 3: 
 *   
 * 
 * Solution 4:
 *   
 *   Time O(n)  Space O(n)
 * 
 * 
 * 
 * Q2 Given a int array, it include negative and positive. 
 * Write an in-place algorithm to rearrange the array, put the negative in the left, positive in the right. 
 * Note: keep the sequence in negative and positive. 
 * i.e.  
 *  input {1, 5, -5, -8, 2, -1, 15}
 * output {-5, -8, -1, 1, 5, 2, 15}  
 * 
 * Solution:  midSplit_mergeSort(int[] arr)
 *  Time O(nlogn) ?  Space O(1)
 * 
 * 
 */
public class PerfectShuffle
{
  
  /*
   * ie.
   *  input {1, -5, 5, -8, 2, -1, 15}
   * output {-5, -8, -1, 1, 5, 2, 15}
   * 
   * Time O(nlogn)?  Space O(1)
   */
  public void midSplit_mergeSort(int[] arr){
    //check
    if(arr == null || arr.length ==0)
      return;
    
    midSplit_mergeSort(arr, 0, arr.length);
  }
  
  private int midSplit_mergeSort(int[] arr, int start, int end){
    int ret = start;
    int diff = end - start;
    
    if(diff == 2 ){  // 2 node,  {a, b}={-1, 1}
      int next = start + 1;
      if(arr[start] < 0 && arr[next] > 0){   // {a, b}={-1, 1}
          swap(arr, start, next);
          ret = next;
      }else if( arr[start] > 0 && arr[next] < 0){
          ret = next;
      }else if(arr[start] > 0 && arr[next] > 0){
          ret = end;
      }
      return ret;
    } else if(diff == 1){  // 1 node  
      if(arr[start] > 0)
        ret = start + 1;
      
      return ret;  //should not happened  
    }
    
    int mid = start + diff / 2;
    int left = midSplit_mergeSort(arr, start, mid);
    int right = midSplit_mergeSort(arr, mid, end);
  
    if(left < mid && right > mid)
      rotate(arr, left, right, right - mid);
    
    return left + (right - mid);
  }  
  
  /*
   * cp from Rotate.rotate(arr, k)
   */
  private void rotate(int[] arr, int start, int end, int k){
    //check
    if(k == 0)
      return ;
    
    
    int n = end - start;
    
    reverse(arr, start, start + n - 1);
    reverse(arr, start, start + k - 1);
    reverse(arr, start + k, start + n - 1);
    
  }

  private void reverse(int[] str, int start, int end){
    while(start < end){
      Misc.swap(str, start++, end--);
    }
    
  }
  
  /*
   * ie.
   *  input A={1, 5, 2, 15, -5, -8, -1, -3}, n = 4
   * output A={1, -5, 5, -8, 2, -1, 15, -3}
   * 
   * Solution:
   *   [X,Y|A,B] => [X,A|Y,B] 
   *   
   *   a[4n] => a[n]b[n]c[n]d[n]            half= 2n,   quarter = n
   *   a[4n+1] => a[n]b[n]c[n]d[n+1]        half= 2n,   quarter = n
   *   a[4n+2] => a[n]b[n+1]c[n]d[n+1]      half= 2n+1, quarter = n
   *   a[4n+3] => a[n]b[n+1]c[n]d[n+2]      half= 2n+1, quarter = n
   *   
   *   Time O(nlogn) ?  Space O(1)
   */
  public void interleaveSort_DC(int[] arr, int n){
    //check
    if(arr == null || arr.length == 0 || arr.length != 2*n )
      return;
    
    intersectionSort(arr, 0, arr.length);
    
  }
  private void intersectionSort(int[] arr, int start, int end){
    int diff = end - start;
    if(diff == 1 || diff == 2){  // 2 node,  {a, b} or 3 node, {a, b, c}
      return;
    }
          
    
    int quarter = diff / 4;
    int half = diff / 2;
    
    swap(arr, start + quarter, start + half, start + half, start + half + quarter);
    
    if(diff > 4){ 
      intersectionSort(arr, start, start + quarter * 2);
      intersectionSort(arr, start+ quarter * 2, end);
    }
    
  } 

  private void swap(int[] arr, int iStart, int iEnd, int jStart, int jEnd){
    for(int i = iStart, j = jStart; i< iEnd && j< jEnd; i++, j++ ){
      swap(arr, i, j);
    }  
    
    if(iEnd - iStart == jEnd - jStart + 1){
      int j = jStart;
      while(j < jEnd){
        swap(arr, j-1, j);
        j++;
      }
    }

  }
  
  private void swap(int[] arr, int i, int j){
    int tmp = arr[i];
    arr[i] = arr[j];
    arr[j] = tmp;
  }
  


  

  
  /**
   * @param args
   */
  public static void main(String[] args) {
    PerfectShuffle service = new PerfectShuffle();
    
    int[][] arr = {null, {1, 5, -5, -8}, {1, 5, 2, -5, -8, -1}, {1, 5, 2, 15, -5, -8, -1, -3}, {1, 5, 2, 15, 6, -5, -8, -1, -3, -2}};
    int[] n = {1, 2, 3, 4, 5};
    
    for(int i=0; i< n.length; i++){
      System.out.println("\n input: A="+ Misc.array2String(arr[i])+"\tn:"+n[i]);
      service.interleaveSort_DC(arr[i], n[i]);
      
      System.out.println("output: A="+ Misc.array2String(arr[i]));
      
      service.midSplit_mergeSort(arr[i]);
      System.out.println("output: A="+ Misc.array2String(arr[i]));
      
    }
    
    
    

  }

}
