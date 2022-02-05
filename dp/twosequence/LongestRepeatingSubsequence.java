/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dp.twosequence;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import junit.framework.Assert;

/**
 * __https://www.lintcode.com/problem/581
 * 
 *
 * Given a string, find length of the longest repeating subsequence such that the two subsequence don’t have same string
 * character at same position, i.e., any ith character in the two subsequences should not have the same index in the
 * original string. 
 * 
 * Example 1: 
 * Input:"aab"  
 * Output:1 
 * Explanation: The two subsequence are a(first) and a(second).
 * Note that b cannot be considered as part of subsequence as it would be at same index in both. 
 * 
 * Example 2: 
 * Input:"abc"
 * Output:0 
 * Explanation: There is no repeating subsequence
 * 
 * Thoughts:
 *   It's not as same as the LongestCommonSequence, that has two independent input string. Here it only has one input 
 *   string and it's required that the two subsequence don't have same string.  
 *  
 *   example s = "abcabcbb",  define boolean[][] visited , f[0][1] as the longest repeating subsequence 
 *   step 1, find all pair,  
 *     pair #1, s[0] = s[3] = 'a',  f[0][1] = 1 + f[1][4] 
 *     pair #2, s[1] = s[4] = 'b',  f[0][1] = 1 + f[2][5] 
 *     pair #3, s[1] = s[6] = 'b',  f[0][1] = 1 + f[2][7] 
 *     pair #4, s[1] = s[7] = 'b',  f[0][1] = 1 + f[2][8] 
 *     pair #5, s[2] = s[5] = 'c',  f[0][1] = 1 + f[3][6] 
 * 
 * 
 */
public class LongestRepeatingSubsequence {

    /**
     * refer to LongestCommonSequence, 
     * Wrong!! for case {"aab", "a"},  It's "a" instead of "ab"
     * Because it is required that the two subsequence don't have same character at the same position, 
     *  
     * 
     * @param s
     * @return 
     */
    public int longestRepeatingSubsequence_Wrong(String s) {
        if (null == s || 0 == s.length() ) {
            return 0;
        }

        int n = s.length();
        
        int[][] f = new int[n + 1][n + 1]; //default all are 0
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= n; j++) {
                if (i != j && s.charAt(i - 1) == s.charAt(j - 1)) {
                    f[i][j] = f[i - 1][j - 1] + 1;
                } else {
                    f[i][j] = Math.max(f[i][j - 1], f[i - 1][j]);
                }
            }
        }

        return f[n][n];
    }
    
    /**
     * 
     * 
     * @param s
     * @return 
     */
    public int longestRepeatingSubsequence(String s) {
        if (null == s || 0 == s.length() ) {
            return 0;
        }

        int n = s.length();
    
        return dfs(s.toCharArray(), 0, 1, new boolean[n], new int[n][n]);
    }
    
    private int dfs(char[] arr, int i, int j, boolean[] visited, int[][] cache){
        int n = arr.length;
        if(i >= n || j >= n){
            return 0;
        }
        
        if(cache[i][j] > 0){
            return cache[i][j];
        }
        
        for(int l = i; l < n; l++){
            if(visited[l]){
                continue;
            }
            for(int r = Math.max(j, l + 1); r < n; r++){
                if(visited[r]){
                    continue;
                }
                
                if(arr[l] == arr[r]){
                    visited[l] = visited[r] = true; 
                    cache[i][j] = Math.max(cache[i][j], dfs(arr, l + 1, r + 1, visited, cache) + 1);
                    visited[l] = visited[r] = false; 
                }
            }
        }
        
        return cache[i][j];
    }
    
    /**
     * Wrong!!  for case {"abccbabb", "abb"}
     * 
     * 
     * @param s
     * @return 
     */
    public int longestRepeatingSubsequence_Wrong2(String s) {
        if (null == s || 0 == s.length() ) {
            return 0;
        }
    
        Map<Character, Queue<Integer>> map = new HashMap<>();
        char c;
        for(int i = 0; i < s.length(); i++){
            c = s.charAt(i);
            
            map.computeIfAbsent(c, x -> new LinkedList<>()).add(i);
        }
        
        PriorityQueue<Queue<Integer>> minHeap = new PriorityQueue<>( (a, b) -> Integer.compare( a.peek(), b.peek() ) );
        for(Queue<Integer> queue : map.values()){
            if(queue.size() > 1){
                minHeap.add(queue);
            }
        }
        
        int count = 0;
        //int x = -1;
        int y = -1;
        int ny;
        Queue<Integer> top;
        while(!minHeap.isEmpty()){
            top = minHeap.poll();
            
            if(top.size() > 1){
                top.poll();
                ny = top.poll();
                
                if(ny > y){
                    count++;
                }else{
                    y = ny;
                }
            }
            
            if(top.size() > 1){
                minHeap.add(top);
            }
        }
        
        return count;
    }
    
    class Node{
        
    }

    
    public static void main(String[] args) {

        String[][] inputs = {            
            {"aab", "a"},
            {"abc", ""},
            {"bbb", "b"}, 
            {"abab","ab"},
            {"abccbabcd", "abc"},
            {"abcabcbb", "abcb"}, 
            {"abccbabb", "abb"}, 
            {"aabebcdd", "abd"},
            {"bccbb", "cb"}
        };

        LongestRepeatingSubsequence sv = new LongestRepeatingSubsequence();

        for (String[] input : inputs) {
            System.out.println(String.format("\n Input %s, \tOutput %s", input[0], input[1]));
            
            
            //Assert.assertEquals(input[1].length(), sv.longestRepeatingSubsequence_Wrong(input[0]));
            Assert.assertEquals(input[1].length(), sv.longestRepeatingSubsequence(input[0]));
            //Assert.assertEquals(input[1].length(), sv.longestRepeatingSubsequence_n(input[0]));
        }
    }
}
