/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package easy;

import junit.framework.Assert;

/**
 * Tournament Scheduling
 * 
 * https://www.lintcode.com/problem/879
 *
 * During the NBA playoffs, we always arrange the rather strong team to play with the rather weak team, like make the
 * rank 1 team play with the rank nth team, which is a good strategy to make the contest more interesting. Now, you're
 * given n teams, and you need to output their final contest matches in the form of a string.
 *
 * The n teams are given in the form of positive integers from 1 to n, which represents their initial rank. (Rank 1 is
 * the strongest team and Rank n is the weakest team.) We'll use parentheses () and commas , to represent the contest
 * team pairing - parentheses () for pairing and commas , for partition. During the pairing process in each round, you
 * always need to follow the strategy of making the rather strong one pair with the rather weak one.
 *
 *
 * Notes:
 *   The n is in range [2, 2^12]. 
 *   We ensure that the input n can be converted into the form 2^k, where k is a positive integer.
 *
 * Example 1: 
 * Input: 2 
 * Output: "(1,2)"
 *
 * Example 2: 
 * Input: 4 
 * Output: "((1,4),(2,3))"
 *
 *
 * Example 3:
 * Input: 8 
 * Output: "(((1,8),(4,5)),((2,7),(3,6)))" 
 * Explanation: 
 * First round: (1,8),(2,7),(3,6),(4,5) 
 * Second round: ((1,8),(4,5)),((2,7),(3,6)) 
 * Third round: (((1,8),(4,5)),((2,7),(3,6)))
 *
 * Thoughts:
 *   example n = 8. 
 *   step 0, init, new int[]{1, 2, 3, 4, 5, 6, 7, 8}
 *   step 1, window = 1, [1-8, 2-7, 3-6, 4-5]
 *   step 2, window = 2, [18-45, 27-36]
 *   step 3, window = 4
 *    
 * followup:  if n is not 2^k, example n = 6, how to output the final contest matches? 
 *   step 0, init, new int[]{1, 2, 3, 4, 5, 6, -1, -1},  (1, -1) match means 1 wins directly without contest.  
 *   step 1, same as  
 */
public class ContestMatches {
    /**
     * 
     * 
     * @param n: a integer, denote the number of teams
     * @return the contest match
     */
    public String findContestMatch(int n) {

        int[] curr = new int[n]; // 0-based indexing
        for(int i = 0; i < n; i++){
            curr[i] = i + 1;
        }

        int[] next = new int[n];
        int[] tmp;
        int j;
        for(int w = 1, end = n / 2; w < end; w *= 2){

            j = 0;
            for(int l = 0, r = n - w; l < r; l += w, r -= w ){
                for(int i = l; i < l + w; i++){
                    next[j++] = curr[i];
                }
                for(int i = r; i < r + w; i++){
                    next[j++] = curr[i];
                }
            }

            tmp = curr;
            curr = next;
            next = tmp;
        }

        StringBuilder result = new StringBuilder();
        build(curr, 0, n - 1, result);

        return result.toString();
    }

    private void build(int[] arr, int l, int r, StringBuilder result){
        if(l == r){
            result.append(arr[l]);
            return;
        }

        int mid = l + (r - l) / 2;
        result.append("(");
        build(arr, l, mid, result);
        result.append(",");
        build(arr, mid + 1, r, result);
        result.append(")");
    }
    
    
    public String findContestMatch_2(int n) {
        String[] s = new String[n];
        
        for(int i = 0; i < n; i++){
            s[i] = Integer.toString(i + 1);
        }
        
        for( ; n > 1;  n/= 2){
            for(int i = 0; i < n / 2; i++){
                s[i] = String.format("(%s,%s)", s[i], s[n - i - 1]); 
            }
        }
        
        return s[0];
    }
    
    public static void main(String[] args){
        String[][] inputs = {
            {"2","(1,2)"},
            {"4","((1,4),(2,3))"},
            {"8","(((1,8),(4,5)),((2,7),(3,6)))"},
            {"16","((((1,16),(8,9)),((4,13),(5,12))),(((2,15),(7,10)),((3,14),(6,11))))"}
        };
        
        
        ContestMatches sv = new ContestMatches();
        
        for(String[] input : inputs){
            System.out.println(String.format("\n n = %s\n %s", input[0], input[1]));
            
            Assert.assertEquals("n = " + input[0], input[1], sv.findContestMatch(Integer.parseInt(input[0])));
            Assert.assertEquals("n = " + input[0], input[1], sv.findContestMatch_2(Integer.parseInt(input[0])));
        }
        
    }
}
