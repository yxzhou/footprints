package fgafa.game.nimGames;

/**
 * Similar with Leetcode #486
 *
 * There are n coins in a line. (Assume n is even).
 * Two players take turns to take a coin from one of the ends of the line until there are no more coins left.
 * The player with the larger amount of money wins.
 *
 * Would you rather go first or second? Does it matter?
 * Assume that you go first, describe an algorithm to compute the maximum amount of money you can win.
 *
 *
 * Thoughts:
 * From top to bottom:
 *
 * to arr(0, n - 1), when player1 to pick, he want to get max(arr, 0, n)
 *     if player1 pick arr[0], arr(1, n-1) left, obviously player2 want to get max(arr, 1, n - 1),
 *        so player1 will get arr[0] + total(arr, 1, n - 1) - max(arr, 1, n - 1)
 *     or player1 pick arr[n - 1], arr(0, n - 2) left,  player2 want to get max(arr, 0, n - 2),
 *        so player1 will get arr[n - 1] + total(arr, 0, n - 2) - max(arr, 0, n - 2)
 *
 * DP solution is:
 *   define sums[i][j] as the sum of arr[i, j]
 *   define max[i][j] as the max get from arr[i, j]
 *
 *   max[i][i] = arr[i];
 *   max[i][j] = Math.max(arr[i] + sums[i + 1][j] - max[i + 1][j], sums[i][j - 1] - max[i][j - 1] + arr[j])
 *
 *   return max[0][n - 1]
 *
 * The decision tree is
 *                    (arr, 0, n - 1)
 *                    /             \
 *             (1, n - 1)        (0, n - 2)
 *        - - -                 - - -
 *    (0, 1)   (1, 2) - - -              (n - 2, n - 1)
 *
 * From bottom to top:
 *   define sums[w][i] as the sum from arr[i, i + w]
 *   define mg[w][i] as the max get from arr[i, i + w]
 *
 *   init:
 *   sums[0][i] = 0;
 *   mg[0][i] = 0;
 *
 *   for(int w = 1; w <= n; w++){
 *       for(int l = 0, r = l + w; r < n; r++, l++ ){
 *           mg[w][l] = Math.max( arr[l] + sums[w - 1][l + 1] - mg[w - 1][l + 1], sums[w - 1][l] - mg[w - 1][l] + arr[r]);
 *           sums[w][l] = sums[w - 1][l] + arr[r];
 *
 *       }
 *   }
 *
 *   Optimize:
 *     If the total number of stones is odd, the player who go first will win. ( see StoneGame.class )
 *
 */

public class CoinInLineII{

    /*  Time O(n*n), Space O(n*n)*/
    public boolean firstWillWin(int[] nums) {
        //check
        if (null == nums || 0 == nums.length) {
            return false; // or throw exception
        }

        int n = nums.length;

        int[][] sums = new int[n][n];
        int[][] maxGets = new int[n][n];

        for(int l = 0; l < n; l++){
            sums[l][l] = nums[l];
            for(int r = l + 1; r < n; r++){
                sums[l][r] = sums[l][r - 1] + nums[r];
            }
        }

        max(nums, 0, n - 1, sums, maxGets);

        return (maxGets[0][n - 1] << 1) >= sums[0][n - 1];
    }

    private int max(int[] nums, int l, int r, int[][] sums, int[][] maxGets){
        if(maxGets[l][r] > 0){
            return maxGets[l][r];
        }

        if(l == r){
            maxGets[l][r] = nums[l];
        }else if(l + 1 == r){
            maxGets[l][r] = Math.max(nums[l], nums[r]);
        }else{
            maxGets[l][r] = Math.max(nums[l] + sums[l + 1][r] - max(nums, l + 1, r, sums, maxGets),  nums[r] + sums[l][r - 1] - max(nums, l, r - 1, sums, maxGets));
        }

        return maxGets[l][r];
    }

    /*  Time O(n*n), Space O(n*n)*/
    public boolean firstWillWin_dp(int[] values) {
        //check
        if(null == values || 0 == values.length){
            return false; // or throw exception
        }
        
        int size = values.length;
        
        if(size < 3){
            return true;
        }
        
        long[][] totalSum = new long[size][size]; // totalSum[i][j] is the sum of from i to i+j
        long[][] max = new long[size][size]; // max[i][j] is the max take from i to i+j if you are the first player
        
        for(int i = 0; i < size; i++){
            totalSum[i][0] = values[i];
            max[i][0] = values[i];
        }
        
        for(int width = 1; width < size; width++){
            for(int index = size - 1 - width; index >= 0; index--){
                totalSum[index][width] = totalSum[index + 1][width - 1] + totalSum[index][0];
                max[index][width] = totalSum[index][width] - Math.min( max[index + 1][width], max[index][width - 1] );
            }
        }
        
        return max[0][size - 1] * 2 > totalSum[0][size - 1];
    }

    /*  Time O(n*n), Space O(n)*/
    public boolean firstWillWinII_dp_n(int[] nums) {
        //check
        if(null == nums || 0 == nums.length){
            return false; // or throw exception
        }
        
        int n = nums.length;

        int[] sums = new int[n + 1];
        int[] mg = new int[n + 1];

        for(int w = 0; w < n; w++){
            for(int l = 0, r = l + w; r < n; l++, r++){
                mg[l] = Math.max(nums[l] + sums[l + 1] - mg[l + 1], sums[l] - mg[l] + nums[r] );
                sums[l] += nums[r];
            }
        }

        return (mg[0] << 1) >= sums[0];
    }

    public boolean PredictTheWinner(int[] nums) {
        int n = nums.length;

        int[] dp = new int[n];
        for (int s = n; s >= 0; s--) {
            for (int e = s + 1; e < n; e++) {
                int a = nums[s] - dp[e];
                int b = nums[e] - dp[e - 1];
                dp[e] = Math.max(a, b);
            }
        }
        return dp[n - 1] >= 0;
    }

  /**
   * @param args
   */
  public static void main(String[] args) {
    // TODO Auto-generated method stub

  }

}
