package fgafa.game.nimGames;

/**
 * Leetcode #877
 *   Similar with Leetcode #486
 *   see CoinInLineII
 *
 * Alex and Lee play a game with piles of stones.  There are an even number of piles arranged in a row, and each pile has a positive integer number of stones piles[i].
 *
 * The objective of the game is to end with the most stones.  The total number of stones is odd, so there are no ties.
 *
 * Alex and Lee take turns, with Alex starting first.  Each turn, a player takes the entire pile of stones from either the beginning or the end of the row.  This continues until there are no more piles left, at which point the person with the most stones wins.
 *
 * Assuming Alex and Lee play optimally, return True if and only if Alex wins the game.
 *
 *
 *
 * Example 1:
 *
 * Input: [5,3,4,5]
 * Output: true
 * Explanation:
 * Alex starts first, and can only take the first 5 or the last 5.
 * Say he takes the first 5, so that the row becomes [3, 4, 5].
 * If Lee takes 3, then the board is [4, 5], and Alex takes 5 to win with 10 points.
 * If Lee takes the last 5, then the board is [3, 4], and Alex takes 4 to win with 9 points.
 * This demonstrated that taking the first 5 was a winning move for Alex, so we return true.
 *
 *
 * Note:
 * 2 <= piles.length <= 500
 * piles.length is even.
 * 1 <= piles[i] <= 500
 * sum(piles) is odd.
 *
 *
 *
 * Thoughts:
 *
 *
 */

public class StoneGame {

    /**
     *  see CoinInLineII.java
     *
     */
    public boolean stoneGame(int[] piles) {
        int n = piles.length;

        int[] sums = new int[n + 1];
        int[] f = new int[n + 1];

        for(int w = 0; w < n; w++ ){
            for(int l = 0, r = l + w; r < n; l++, r++){
                f[l] = Math.max(piles[l] + sums[l + 1] - f[l + 1], sums[l] - f[l] + piles[r]);

                sums[l] += piles[r];
            }
        }

        return f[0] * 2 > sums[0];
    }


    /**
     *  There are an even number of piles arranged in a row, and the total number of stones is odd
     *
     *  So, if it's 2 piles, {v1, v2},  because v1+v2 is odd, so v1 != v2, the player who go first will win.
     *  if it's 4 piles, {v1, v2, v3, v4}, v1 + v3 != v2 + v4,
     *     if v1 + v3 > v2 + v4, the player who go first will win, because he can pick v1, then pick v3.
     *
     */
    public boolean stoneGame_x(int[] piles) {
        return true;
    }

}
