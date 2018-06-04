package fgafa.dfsbfs.permutationAndCombination;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;

import fgafa.util.Misc;

/**
 * 
 * Given two integers n and k, return all possible combinations of k numbers out of 1 ... n.
 * 
 * For example,
 * If n = 4 and k = 2, a solution is:
 * 
 * [
 * [2,4],
 * [3,4],
 * [2,3],
 * [1,2],
 * [1,3],
 * [1,4],
 * ]
 * 
 *
 */
public class Combinations
{

  public List<List<Integer>> combine_recur(int n, int k) {
    List<List<Integer>> result = new ArrayList<>();
    
    combine(result, n, k, new Stack<Integer>());
    
    return result;
  }
  
  private void combine(List<List<Integer>> result, int n, int k, Stack<Integer> stack){
    if(k == 0){
      addStack(result, stack);
      return;
    }
    
    for(int i=n; i>=k; i-- ){
      stack.add(i);
      combine(result, i-1, k-1, stack);
      stack.pop();
    }
  }
  
  private void addStack(List<List<Integer>> result, Stack<Integer> stack ){
    ArrayList<Integer> list = new ArrayList<>();
    
    Stack<Integer> stackTmp = (Stack<Integer>)stack.clone();
    while(!stackTmp.isEmpty()){
      list.add(stackTmp.pop());
    }
      
    result.add(list);
  }
  
  
  /**
   * 
   */
  public List<List<Integer>> combine_recur2(int n, int k) {
      if (n<=0||k<=0){
          return null;
      }
      
      List<List<Integer>> result=new ArrayList<>();

      ArrayList<Integer> subResult=new ArrayList<Integer>();
      buildResult(1, 0, k, n, subResult, result);
      
      return result;
  }
  
  // DFS classical format
  private void buildResult(int start, int currentNum, int k, int n, List<Integer> subResult, List<List<Integer>> result)
  {
      if (currentNum==k){
          result.add(new ArrayList<Integer>(subResult));
          return;
      }
      
      for (int i=start; i<=n; i++){
          subResult.add(i);
          buildResult(i+1, currentNum+1, k, n, subResult, result);
          subResult.remove(subResult.size()-1);
      }
  }  
  
  
  
  /*
   * 
   */
  public List<List<Integer>> combine_iterative(int n, int k) {
	Queue<List<Integer>> result = new LinkedList<>();
    
    List<Integer> list;
    int end = n-k+1;
    for(int i=1; i<= end; i++){
      list = new ArrayList<Integer>();
      list.add(i);
      
      result.add(list);
    }
          
    ArrayList<Integer> listTmp;
    for(int i = 1; i< k; i++){
      for(int j = result.size(); j>0; j-- ){
        list = result.poll();
        
        for(int p = list.get(list.size() - 1) + 1; p <= end + i; p++  ){
          listTmp = new ArrayList<Integer>(list);
          listTmp.add(p);
          result.add(listTmp);
        }
      }      
    }
    
    return new ArrayList<>(result);
  }
  
  
  /**
   * @param args
   */
  public static void main(String[] args) {
    System.out.println("==========start=========="+System.currentTimeMillis());
    int n = 5;
    int k = 2;

    Combinations sv = new Combinations();
    //List<List<Integer>> result = sv.combine_recur(n, k);
    List<List<Integer>> result = sv.combine_recur2(n, k);
    //List<List<Integer>> result = sv.combine_iterative(n, k);
    
    Misc.printListList(result);
    
    System.out.println("===========end==========="+System.currentTimeMillis());
  }

}
