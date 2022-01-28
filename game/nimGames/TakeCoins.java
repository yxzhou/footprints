/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package game.nimGames;

/**
 * _https://www.lintcode.com/problem/1399
 *
 * There are n coins in a row, each time you want to take a coin from the left or the right side. Take a total of K times
 * and write an algorithm to maximize the sum of coins you can get.
 *
 * Constraints:
 *   1 <= k <= n <= 100000。 
 *   The value of the coin is not greater than 10000。 
 * 
 * Example 1:
 * Input: list = [5,4,3,2,1], k = 2 
 * Output :9 
 * Explanation:Take two coins from the left. 
 * 
 * Example 2:
 * Input: list = [5,4,3,2,1,6], k = 3, 
 * Output:15 
 * Explanation:Take two coins from the left and one from the right.
 * 
 * Thoughts:
 *   The final states is taking x times from left and k-x times from right. x is [0, k]
 * 
 */
public class TakeCoins {
    
    /**
     * 
     * @param list: The coins
     * @param k: The k
     * @return The answer
     */
    public int takeCoins(int[] list, int k) {
        int sum = 0;
        for(int i = 0; i < k; i++){
            sum += list[i];
        }

        int max = sum;
        for(int l = k - 1, r = list.length - 1; l >= 0; l--, r--){
            sum += list[r] - list[l];

            max = Math.max(max, sum);
        } 

        return max;
    }
    
}
