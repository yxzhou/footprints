/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dfsbfs.permutationAndCombination;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;
import util.Misc;

/**
 *
 * Given a list of numbers with duplicate numbers in it. Find all unique permutations of it.
 * 
 * Example 1:
 * Input: nums = [1,1] 
 * Output:
 * [ 
 *   [1,1] 
 * ] 
 * Explanation: The different arrangement of [1,1] is only [1,1].
 * 
 * Example 2:
 * Input: nums = [1,2,2] 
 * Output:
 * [ 
 *   [1,2,2], 
 *   [2,1,2], 
 *   [2,2,1] 
 * ] 
 * Explanation: The different arrangements of [1,2,2] are [1,2,2],[2,1,2]and [2,2,1].
 * 
 * Challenge
 *   Using recursion to do it is acceptable. If you can do it without recursion, that would be great!
 * 
 * 
 * Solutions:   refer to Permutations
 *   1) recursive,  DFS  
 *      add a Set<> to filter the duplicate 
 * 
 *   2) non-recursively, BFS  Queue  insert,  
 *      add a Set<> to filter the duplicate 
 * 
 */
public class PermutationsII {
    public List<List<Integer>> permuteUnique_swap(int[] nums) {
        if(nums == null){
            return Collections.EMPTY_LIST;
        }

        List<List<Integer>> result = new ArrayList<>();

        permuteUnique_swap(nums, 0, result);

        return result;
    }

    private void permuteUnique_swap(int[] num, int i, List<List<Integer>> result) {
        if (i == num.length) {
            List<Integer> tmp = new ArrayList<>(num.length);
            for (int j : num) {
                tmp.add(j);
            }

            result.add(tmp);
            return;
        }

        Set<Integer> set = new HashSet<>(); // for removing the duplicates
        for (int j = i; j < num.length; j++) {
            if (set.contains(num[j])) {
                continue;
            }
            
            set.add(num[j]);
            swap(num, j, i);
            permuteUnique_swap(num, i + 1, result);
            swap(num, i, j);
        }
    }
    
    private void swap(int[] num, int i, int j) {
        if(i == j){
            return;
        }
        
        int tmp = num[i];
        num[i] = num[j];
        num[j] = tmp;
    }

    public List<List<Integer>> permuteUnique_insert(int[] num) {
        if (null == num) {
            return Collections.EMPTY_LIST;
        }
        
        Queue<List<Integer>> queue = new LinkedList<>();
        queue.add(new ArrayList<>());

        List<Integer> top;
        List<Integer> next;
        Set<List<Integer>> set = new HashSet<>();
        for (int i = 0; i < num.length; i++) {
            for (int j = queue.size(); j > 0; j--) {
                top = queue.poll();
                
                for (int k = top.size(); k >= 0; k--) {
                    next = new ArrayList<>(top);
                    next.add(k, num[i]);
                    
                    if(set.contains(next)){
                        continue;
                    }
                    
                    set.add(next);
                    queue.add(next);
                }
            }
        }

        return new ArrayList<>(queue);
    }
    
    
    public static void main(String[] args) {
        PermutationsII sv = new PermutationsII();

        int[][] input = {{1}, {1, 2}, {1, 2, 3}, {1, 2, 2},
        {1, 2, 2, 2}, {1, 2, 2, 2, 3}, {0, 1, 0, 0, 9},
        {3, 3, 0, 0, 2, 3, 2}};

        //for (int i = 0; i < input.length; i++) {
        for (int i = 2; i < 4; i++) {    
            System.out.println("\nInput:" + Misc.array2String(input[i]));

            Misc.printListList(sv.permuteUnique_swap(input[i]));
            Misc.printListList(sv.permuteUnique_insert(input[i]));
        }

    }

}
