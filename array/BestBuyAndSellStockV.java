/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package array;

import java.util.PriorityQueue;

/**
 * Given a stock n-day price, you can only trade at most once a day, you can choose to buy a stock or sell a stock or give up the trade today, output the maximum profit can be achieved.
 * 1≤n≤10000
 * 
 * Example 1:
 * Input: [1,2,10,9]
 * Output: 16
 * Explanation: you can buy in day 1,2 and sell in 3,4.
 *   profit:-1-2+10+9 = 16 
 * 
 * Example 2:
 * Input: [9,5,9,10,5]
 * Output: 5
 * Explanation: you can buy in day 2 and sell in 4.
 *   profit:-5 + 10 = 5
 * 
 */
public class BestBuyAndSellStockV {
    
    public int getAns(int[] a) {
        if (a == null || a.length < 2) {
            return 0;
        }

        PriorityQueue<Integer> q = new PriorityQueue<>();
        int res = 0;
        for (int i : a) {
            if (q.size() > 0 && i > q.peek()) {

                res += i - q.poll();

                q.offer(i);
            }
            q.offer(i);
        }
        return res;
    }
}
