package fgafa.array.subSum;

/*
 *Given an array S of n integers, are there elements a, b, c, and d in S such that a + b + c + d = target? 
 *Find all unique quadruplets in the array which gives the sum of target.
 *
 *Note:
 *Elements in a quadruplet (a,b,c,d) must be in non-descending order. (ie, a ≤ b ≤ c ≤ d)
 *The solution set must not contain duplicate quadruplets.
 *    For example, given array S = {1 0 -1 0 -2 2}, and target = 0.
 *
 *    A solution set is:
 *    (-1,  0, 0, 1)
 *    (-2, -1, 1, 2)
 *    (-2,  0, 0, 2)
 * 
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import fgafa.util.Misc;

public class FourSum extends ThreeSum
{

    /*Time O(nlogn) + O(n^3), Space O(1)*/
  public List<List<Integer>> sumOf4(int[] arr, int target) {
    List<List<Integer>> list = new ArrayList<>(); 
    HashSet<ArrayList<Integer>> set = new HashSet<ArrayList<Integer>>();

    // check
    if (arr == null || arr.length < 4)
      return list;

    // remove Integer.MAX_VALUE and Integer.MIN_VALUE
    //int newArrayLen = removeIntegerMaxMin(arr, arr.length);
    int newArrayLen = arr.length;
        
    // sorting arr to in non-descending order.
    //sort_mergeSort(arr, newArrayLen);
    Arrays.sort(arr);

    // check the duplicate and merge them. And found the valid tripletes from
    // the duplicate.
//    newArrayLen = mergeDuplicate(arr, list, newArrayLen);

    // get the tripletes from the new no-duplicate list.
    sumOf4(arr, newArrayLen, set, target);

    list.addAll(set);
    return list;
    
 }
  /*Time O(n^3), Space O(1)*/
  private void sumOf4(int[] arr, int length,
      HashSet<ArrayList<Integer>> set, int target) {
    //check
    if(length < 3){
      return;
    }
    
    int left = 0;
    int right = length - 1;

    while (left < right) {
      
      sumOf3(arr, left, right, set, target);
      
      left++;
    }
  }
  
  /*Time O(n^2), Space O(1)*/
  private void sumOf3(int[] arr, int start, int end,
      HashSet<ArrayList<Integer>> set, int target) {
    
    target = target - arr[start]; 
        
    for (int i = start + 1;i < end; i++) {
      int j = i + 1;
      int k = end;
      while (j < k) {
        int sum_two = arr[i] + arr[j];
        
        if (sum_two + arr[k] < target) {
          j++;
          
        } else if (sum_two + arr[k] > target) {
          k--;
          
        } else {
          ArrayList<Integer> subList = new ArrayList<Integer>();
          subList.add(arr[start]);
          
          subList.add(arr[i]);
          subList.add(arr[j]);
          subList.add(arr[k]);
          
          set.add(subList);
          
          j++;
          k--;

        }
      }
    }
  }
  
  /*Time O(nlogn) + O(n^3), Space O(1)*/
    public List<List<Integer>> sumOf4_X(int[] num,
                                        int target) {
        List<List<Integer>> list = new ArrayList<>();

        if (num == null || num.length < 4){
            return list;
        }

        Arrays.sort(num);

        // get the tripletes from the new no-duplicate list.
        int targetOf3, targetOf2, sumOf2;
        for (int i = 0; i < num.length - 3; i++) {
            // if(i > 0 && num[i] == num[i-1]){
            // continue;
            // }

            targetOf3 = target - num[i];
            for (int j = i + 1; j < num.length - 2; j++) {
                // if(j > i+1 && num[j] == num[j-1]){
                // continue;
                // }

                targetOf2 = targetOf3 - num[j];
                for (int k = j + 1, l = num.length - 1; k < l;) {
                    sumOf2 = num[k] + num[l];
                    if (sumOf2 < targetOf2) {
                        k++;
                    } else if (sumOf2 > targetOf2) {
                        l--;
                    } else { // sum_2 == target_2
                        list.add(build(num[i], num[j], num[k], num[l]));

                        // while(k < l && num[k] == num[++k]);
                        // while(k < l && num[l] == num[--l]);
                    }
                }

            }
        }

        return list;
    }
  
  private List<Integer> build(int i, int j, int k, int l ){
	  List<Integer> list = new ArrayList<>();
	  list.add(i);
	  list.add(j);
	  list.add(k);
	  list.add(l);
	  
	  return list;
  }
  
  /*Time O(nlogn) + O(n^2), Space O(n^2)
   * worst case, Time O(nlogn) + O(n^2)
   * */
  public List<List<Integer>> sumOf4_hash(int[] num, int target){
      List<List<Integer>> list = new ArrayList<>(); 

      if (num == null || num.length < 4)
        return list;
          
      Arrays.sort(num);
      
      HashMap<Integer, List<Pair>> sumOf2 = new HashMap<>();
      
      for(int i=0 ; i < num.length; i++) {
          for(int j = i+1; j< num.length; j++){
              int sum = num[i] + num[j];
              
              if(!sumOf2.containsKey(sum)){
                  sumOf2.put(sum, new ArrayList<>());
              }
              
              sumOf2.get(sum).add(new Pair(i, j));
          }
      }
      
      for(int key : sumOf2.keySet()){
          if(key > target - key){
              continue;
          }
          
          List<Pair> left = sumOf2.get(key);
          List<Pair> right = sumOf2.get(target - key);;
          
          for(int i = 0; i < left.size(); i++){
              for(int j = 0; j < right.size(); j++){
                  if(!isOverlap(left.get(i), right.get(j))){
                      list.add(sort(left.get(i), right.get(j)));
                  }
              }
          }
      }
      
      return list;
  }
  
  private List<Integer> sort(Pair one, Pair other){
      if(one.i > other.i){
          return sort(other, one);
      }
      
      List<Integer> result = new ArrayList<>();
      result.add(one.i);
      
      if(one.j > other.i){
          result.add(other.i);
          
          if(one.j > other.j){
              result.add(other.j);
              result.add(one.j);
          }else{
              result.add(one.j);
              result.add(other.j);
          }
      }else{
          result.add(one.j);
          result.add(other.i);
          result.add(other.j);
      }
      
      return result;
  }
  
  private boolean isOverlap(Pair one, Pair other){
      if(one.i == other.i || one.i == other.j || one.j == other.i || one.j == other.j){
          return true;
      }
      
      return false;
  }
  
  class Pair{
      int i;
      int j;
      
      Pair(int i, int j){
          this.i = i;
          this.j = j;
      }
  }
  
  /**
   * @param args
   */
  public static void main(String[] args) {
    FourSum sv = new FourSum();
    int[][] arr = {
        {0, 1},
        {1,1,-2},
        {-1, 0, 1},
        {-2, 0, 1, 1, 2, 0},
        {-1, 1, -1, 1, 1},
        {0, 0, 0, 0,0},
        {1,0,-1,0,-2,2},
        {-1, 0, 1, 2, -1, -4},
        {-1, 0, 1, 2, -1, -4, 0, 0, Integer.MAX_VALUE, Integer.MIN_VALUE, -1, 2, -3, -2}};

    int[] target = {0};
    
    List<List<Integer>> list;
    for (int i = 1; i < arr.length; i++) {
      System.out.println("\nThe original array is: "
          + Misc.array2String(arr[i]));

      //Misc.printIntArrayListArrayList( sv.sumOf4(arr[i], target[0]));
      
      Misc.printListList( sv.sumOf4_X(arr[i], target[0]));
    }

  }

}
