/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package datastructure.trie;

import org.junit.Assert;
import util.Misc;

/**
 *
 * Given a non-empty array of numbers, a0, a1, a2, … , an-1, where 0 ≤ ai < 2^31.
 * Find the maximum result of ai XOR aj, where 0 ≤ i, j < n.
 *
 * Example1
 * Input: [3, 10, 5, 25, 2, 8]
 * Output: 28
 * Explanation: The maximum result is 5 ^ 25 = 28 
 * 
 * Example2
 * Input: [2,4]
 * Output: 6 
 * 
 * Challenge 
 *   Could you do this in O(n) runtime?
 * 
 * Thoughts:
 *   m1) naive, total there is n^2 pair, time O(n^2)
 *   m2) xor is to check the difference on bit 
 * 
 */
public class MaximumXOR {

    /**
     * @param nums: 
     * @return the maximum result of ai XOR aj, where 0 ≤ i, j < n
     */
    public int findMaximumXOR(int[] nums) {
        assert nums == null || nums.length == 0;

        int max = 0;

        TrieNode root = new TrieNode();

        for(int num : nums){
            add(root, num);
        } 

        for(int num : nums){
            max = Math.max(max, num ^ get(root, num));
        }

        return max;
    }

    private void add(TrieNode root, int num){
        TrieNode node = root;

        int b;
        for(int i = 31; i >= 0; i--){
            b = ((num >> i) & 1); 
            if( node.nexts[b] == null  ){
                node.nexts[b] = new TrieNode();
            }
            
            node = node.nexts[b];
        }

        node.x = num;
    }

    private int get(TrieNode root, int num){
        TrieNode node = root;

        int b;
        for(int i = 31; i >= 0; i--){
            b = ((num >> i) & 1) ^ 1; 
            if( node.nexts[b] != null  ){
                node = node.nexts[b];
            }else {
                node = node.nexts[b ^ 1];
            }
        }

        return node.x;
    }

    class TrieNode{
        TrieNode[] nexts = new TrieNode[2];

        int x;
    }
    
    
    public static void main(String[] args){
        int[][][] inputs = {
            {{3}, {0}},
            {{2, 4}, {6}},
            {{8,10,2}, {10}},
            {{3,10,5,25,2,8}, {28}},
            {{32,18,33,42,29,20,26,36,15,46}, {62}}    
        };
        
        MaximumXOR sv = new MaximumXOR();
        
        for(int[][] input : inputs){
            System.out.println(String.format("\nInput: %s", Misc.array2String(input[0]) ));
            
            Assert.assertEquals("" , input[1][0], sv.findMaximumXOR(input[0]));
        }
        
    }
}
