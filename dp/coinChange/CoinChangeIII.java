package fgafa.dp.coinChange;

import fgafa.util.Misc;

import java.util.ArrayList;
import java.util.List;

/**
 * Q1, Find all possible solutions recursively
 * 
 * Given a target number, and a series of candidate numbers, print out all combinations, 
 * so that the sum of candidate numbers equals to the target.
 *
 * Here order is not important, so don’t print the duplicated combination.
 *
 * e.g. target is 7, candidate is 2,3,6,7 output should be 7 and 3+2+2 (but not print 2+3+2, 2+2+3)
 * 
 * 
 */

public class CoinChangeIII {
  
  public List<String> getAllCombinations_R(int target, int[] candidates){
    //check
    
    //try the solution recursively
    List<String> allPossibleChanges = new ArrayList<String>();
    getAllCombinations_R(target, candidates, 0, new String(), allPossibleChanges);
    
    return allPossibleChanges;
  }

  private void getAllCombinations_R(int target, int[] candidates, int startIx, String currSolution, List<String> allPossibleChanges){

    int tmpTarget;
    String tmpSolution;
    for(int i=startIx; i<candidates.length; i++ ){
      tmpTarget = target - candidates[i];
      tmpSolution = currSolution + candidates[i] + "+";
      
      if(tmpTarget < 0)
        continue;
      if(tmpTarget == 0){
        // got a possibility
        allPossibleChanges.add(tmpSolution);
        continue;
      }else{
        //target not reached, continue the solution recursively 
        getAllCombinations_R(tmpTarget, candidates, i, tmpSolution, allPossibleChanges);
        //getAllCombinations_R(tmpTarget, candidates, i+1, tmpSolution);  // if Each number from the candidate set may be used only once in the combination
      }
    }
  }
   
  
  public static void main(String[] args) {  
      CoinChangeIII sv = new CoinChangeIII();
      
      // the candidate is in descend order
      int[][] coinValue = {{ 25, 21, 10, 5, 1 }, {10, 1, 2, 7, 6, 1, 5}, { 25, 10, 5, 1 }, {8, 5, 1}};  
      // the target for making change. 
      int[] target = {65, 8, 67, 20};  
     
      for(int i = 0; i<target.length; i++){
          List<String> allPossibleChanges = sv.getAllCombinations_R(target[i], coinValue[i]);
        System.out.println("\n--getAllCombinations_R of "+ target[i] + " from " + Misc.array2String(coinValue[i]) );
        Misc.printArrayList(allPossibleChanges);
             
      }


      for(int i = 0; i<target.length; i++){
        //System.out.println("\n--getOptimalChange_DP of "+ target[i] + " from " + Misc.array2String(coinValue[i]) + " is: " + sv.getOptimalChange_DP(target[i], coinValue[i]));
        //System.out.println("--getOptimalChange_Greedy of "+ target[i] + " from " + Misc.array2String(coinValue[i]) + " is: " + sv.getOptimalChange_Greedy(target[i], coinValue[i]));
      }

  } 

}
