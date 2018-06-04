package fgafa.dp.sequence;

import fgafa.util.Misc;

/**
 * 
 * 
 * 
 *
 */
public class EggsDrop
{
  /*
   * Function to get minimum number of trails needed in worst case with n eggs
   * and k floors
   * 
   *   k ==> Number of floors
   *   n ==> Number of Eggs
   *   eggDrop(n, k) ==> Minimum number of trails needed to find the critical floor in worst case.
   *   eggDrop(n, k) = 1 + min{max(eggDrop(n - 1, x - 1), eggDrop(n, k - x)):  x in {1, 2, ..., k}}
   * 
   * Time Complexity: O(nk^2)   Auxiliary Space: O(nk)
   */
  public int eggDrop_DP(int n, int k) {
    /*
     * A 2D table where entery eggFloor[i][j] will represent minimum number of
     * trials needed for i eggs and j floors.
     */
    int[][] eggFloor = new int[n + 1][k + 1];
    int res;
    int i, j, x;

    // We need one trial for one floor and0 trials for 0 floors
    for (i = 1; i <= n; i++) {
      eggFloor[i][1] = 1;
      eggFloor[i][0] = 0;
    }

    // We always need j trials for one egg and j floors.
    for (j = 1; j <= k; j++){
      eggFloor[1][j] = j;
    }

    // Fill rest of the entries in table using optimal substructure
    // property
    for (i = 2; i <= n; i++) {
      for (j = 2; j <= k; j++) {
        eggFloor[i][j] = Integer.MAX_VALUE;
        for (x = 1; x <= j; x++) {
          res = 1 + Math.max(eggFloor[i - 1][x - 1], eggFloor[i][j - x]);  // key point
          if (res < eggFloor[i][j]){
            eggFloor[i][j] = res;
          }
        }
      }
    }

    System.out.println(Misc.array2String(eggFloor));
    
    // eggFloor[n][k] holds the result
    return eggFloor[n][k];
  }



  public static void main(String[] args) {
    EggsDrop sv = new EggsDrop();
    
    int n = 2, k = 100;
    System.out.println("\nMinimum number of trials in worst case with "+n+" eggs and"+k+" floors: ");
    System.out.println(" ==" + sv.eggDrop_DP(n, k));

  }

}
