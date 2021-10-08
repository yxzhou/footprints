/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datastructure.unionFind;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * There is a list composed by sets. If two sets have the same elements, merge them. Returns the last remaining collection.
 * 
 * Notes:
 *   The number of sets n <=1000.
 *   The number of elements for each set m <= 100.
 *   The element must be a non negative integer and not greater than 100000.
 * 
 * Example 1:
 * Input :list = [[1,2,3],[3,9,7],[4,5,10]]
 * Output:2 .
 * Explanation:There are 2 sets of [1,2,3,9,7] and [4,5,10] left.
 * 
 * Example 2:
 * Input:list = [[1],[1,2,3],[4],[8,7,4,5]]
 * Output :2
 * Explanation:There are 2 sets of [1,2,3] and [4,5,7,8] left.
 * 
 */
public class SetUnion {
    /**
     * @param sets: Initial set list
     * @return: The final number of sets
     */
    public int setUnion(int[][] sets) {
        if(sets == null ){
            return 0;
        }

        Map<Integer, Integer> parents = new HashMap<>();
        int count = 0;
        int p;
        int q;
        for(int[] set : sets){
             if(set.length == 0){
                 continue;
             }

            p = find(parents,set[0]);
            if(p == -1){
                p = set[0];
                count++;
            }
            
            for(int x : set){
                q = find(parents, x);
                if(q == -1 || p == q){
                    parents.put(x, p);
                    continue;
                }

                count--;
                p = union(parents, p, q);
            }

        }

        return count;
    }

    private int find(Map<Integer, Integer> parents, int x ){
        if(!parents.containsKey(x)){
            return -1;
        }

        int p;
        while( (p = parents.get(x)) != x ){
            //compression
            parents.put(x, parents.get(p));
            x = parents.get(p);
        }

        return p;
    }

    private int union(Map<Integer, Integer> parents, int p, int q){
        parents.put(p, q);
        return q;
    }
}
