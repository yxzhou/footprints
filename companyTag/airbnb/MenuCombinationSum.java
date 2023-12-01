/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package companyTag.airbnb;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.junit.Assert;
import util.Misc;

/**
 *
 * Given a menu (list of items prices), find all possible combinations of items that sum a particular value K. 
 * (A variation of the typical 2sum/Nsum questions).
 * 
 * 点菜，菜价格为double，问如何正好花完手里的钱
 * 解法：把菜单价格*100转成整数，题目转换成
 * 
 * Thoughts:
 *  Need to clarify:
 *   1 can the price be convert to int after multiple 100 (from dollar to cent) ?
 *     if it cannot, need pay attention to the double equal
 *   2 can the price been used multiple times ?
 * 
 *  Define N as the length of candidate prices, T as target (money * 100)
 * 
 *  S1 DFS 
 *  All possible combinations formed by candidates are 2^n. This solution is adding the number from candidates 
 *  iteratively by recursion. Once, it reach the end that is the sum equals to target or is larger than the target. 
 * 
 *  It can be optimized by sorting the prices. when current candidate is bigger, the rest all are bigger.  
 *  
 *  Define M as the minimal candidates, so (T/M + 1) is the maximal height of decision tree. The total nodes of the tree
 *  could be up to N^(T/M + 1)
 * 
 *  Time complexity O(N * logN + N^(T/M + 1))
 *  Space complexity is O(T/M), the longest combination includes T/M candidates. ignoring the space for the output.
 * 
 *  Example: prices = {1, 1, 1, 1} target=2, the output is C(4, 2) {1, 1}, the decision tree
 *                            4
 *            /          /          \         \
 *         1st 1        2nd 1      3rd 1    4th 1
 *     /   |    \       /    \       \
 *  2nd 1 3rd 1 4th 1  3rd 1 4th 1  4th 1
 * 
 *  height = T/M + 1 = 2 + 1 = 3
 *  Time complexity  4^h,  space complexity is T/M = 2, 
 *  Space for output: total it's C(N, T/M) = N^(T/M) combination, every combination is T/M, so it's O( N^(T/M) * T/M )
 * 
 *  S2 backpack, DP
 *  
 *  better on time, worse on space. because it uses more space for cache. 
 *  The sum of possible combinations is in [0, T] 
 *  
 *  Time complexity O(N * T)  space is T * space_for_output
 *  
 * 
 */
public class MenuCombinationSum {
    
    /**
     * DFS
     * 
     * @param prices
     * @param target
     * @return the combination of items that sum to the money
     */
    public List<List<Double>> menuOrder(double[] prices, double target) {
        
        if (prices == null) {
            return Collections.EMPTY_LIST;
        }

        List<List<Double>> result = new ArrayList<>();
        int n = prices.length;
        int[] nums = new int[n];
        for (int i = 0; i < n; i++) {
            nums[i] = (int) (prices[i] * 100);
        }
        
        Arrays.sort(nums);

        helper(result, new ArrayList<>(), nums, 0, (int) (target * 100));

        return result;
    }

    private void helper(List<List<Double>> result, List<Integer> path, int[] prices, int index, int money) {
        if (money == 0) {
            List<Double> tmp = new ArrayList<>(path.size());
            for(int p : path){
                tmp.add((double) p / 100);
            }
            
            result.add(tmp);
            return;
        }

        int n = prices.length;
        if (index == n || money < prices[index]) {
            return;
        }

        for (int i = index; i < n; i++) {
//            if (i > index && prices[i] == prices[i - 1]) { //avoid duplicate, how about prices={ 1, 1, 2}, target=3
//                continue;
//            }

            path.add(prices[i]);

            helper(result, path, prices, i + 1, money - prices[i]);

            path.remove(path.size() - 1);
        }
    }
    
    public static void main(String[] args){
        double[][][][] inputs = {
            //{ {prices, money}, expects }
            {
                {{10.02, 1.11, 2.22, 3.01, 4.02, 2.00, 5.03}, {7.03}},
                {{2.00, 5.03}}
            },
            {
                {{10.02, 1.11, 2.22, 3.01, 4.02, 2.00, 5.03, 2.00, 1.80}, {7.03}},
                {{2.00, 5.03}, {2.00, 5.03}, {1.80, 2.22, 3.01}}
            }
        };
        
        MenuCombinationSum sv = new MenuCombinationSum();
        
        MenuCombinationSum2 sv2 = new MenuCombinationSum2();
        
        List<List<Double>> result;
        List<List<Double>> expect;
        for(double[][][] input : inputs){
            System.out.println(String.format("prices: %s, target = %.2f", Arrays.toString(input[0][0]), input[0][1][0] ));
            
            expect = Misc.convert(input[1]);
            
            //Misc.printListList(sv.menuOrder(input[0][0], input[0][1][0]));
            //Misc.printListList(sv2.menuOrder_Array(input[0][0], input[0][1][0]));
            Misc.printListList(sv2.menuOrder_Map(input[0][0], input[0][1][0]));
            
            result = sv.menuOrder(input[0][0], input[0][1][0]);
            for(List<Double> sub : result){
                Collections.sort(sub);
            }
            
            Assert.assertTrue("sv1", expect.size() == result.size() &&  expect.containsAll(result) && result.containsAll(expect) );            
            
            result = sv2.menuOrder_Array(input[0][0], input[0][1][0]);
            for(List<Double> sub : result){
                Collections.sort(sub);
            }
            
            Assert.assertTrue("sv2.menuOrder_Array", expect.size() == result.size() &&  expect.containsAll(result) && result.containsAll(expect) );
            
            result = sv2.menuOrder_Map(input[0][0], input[0][1][0]);
            for(List<Double> sub : result){
                Collections.sort(sub);
            }
            
            Assert.assertTrue("sv2.menuOrder_Map", expect.size() == result.size() &&  expect.containsAll(result) && result.containsAll(expect) );
            
        }
    }
    
}
