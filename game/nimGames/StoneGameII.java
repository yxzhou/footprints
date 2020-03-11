package fgafa.game.nimGames;

/**
 * Leetcode #1140
 *
 * Alex and Lee continue their games with piles of stones.  There are a number of piles arranged in a row, and each pile has a positive integer number of stones piles[i].  The objective of the game is to end with the most stones.
 *
 * Alex and Lee take turns, with Alex starting first.  Initially, M = 1.
 *
 * On each player's turn, that player can take all the stones in the first X remaining piles, where 1 <= X <= 2M.  Then, we set M = max(M, X).
 *
 * The game continues until all the stones have been taken.
 *
 * Assuming Alex and Lee play optimally, return the maximum number of stones Alex can get.
 *
 *
 *
 * Example 1:
 *
 * Input: piles = [2,7,9,4,4]
 * Output: 10
 * Explanation:  If Alex takes one pile at the beginning, Lee takes two piles, then Alex takes 2 piles again. Alex can get 2 + 4 + 4 = 10 piles in total. If Alex takes two piles at the beginning, then Lee can take all three piles left. In this case, Alex get 2 + 7 = 9 piles in total. So we return 10 since it's larger.
 *
 *
 * Constraints:
 *
 * 1 <= piles.length <= 100
 * 1 <= piles[i] <= 10 ^ 4
 *
 *
 */

public class StoneGameII {

    public int stoneGameII(int[] piles) {
        if(piles == null || piles.length == 0){
            return 0;
        }

        int n = piles.length;
        int[] sums = new int[n + 1];
        for(int i = n - 1; i >= 0; i--){
            sums[i] = sums[i + 1] + piles[i];
        }

        int[][] cache = new int[n][n];
        return maxGet(piles, 0, 1, sums, cache);
    }

    private int maxGet(int[] piles, int i, int m, int[] sums, int[][] cache){
        int n = piles.length;
        int dm = (m << 1);
        if(n - i <= dm){
            return sums[i];
        }

        if(cache[i][m] > 0){
            return cache[i][m];
        }

        int min = Integer.MAX_VALUE;
        for(int x = 1; x <= dm; x++){
            min = Math.min(min, maxGet(piles, i + x, Math.max(m, x), sums, cache ));
        }

        cache[i][m] = sums[i] - min;
        return cache[i][m];
    }

}
