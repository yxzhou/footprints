/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package graph.traversal;

import java.util.ArrayList;
import java.util.List;
/**
 *
 *
 */
public class Barycentre2  {

    public int getBarycentre(int[] x, int[] y) {
        //x.length is edge number, n = x.length + 1 is vertice number, the vertice is from 1 to n
        int edges = x.length;
        List<List<Integer>> tree = new ArrayList<>(); 
        for(int i = 0; i < edges + 2; i++){
            tree.add(new ArrayList<>());
        }
        
        for (int i = 1; i < edges; i++) {
            tree.get(x[i]).add(y[i]);
            tree.get(y[i]).add(x[i]);
        }

        int[] grevityCerter = {0, Integer.MAX_VALUE};//{the center of gravity, the number of nodes of the gravity certer's largest subtree}
        dfs(1, 0, edges + 1, tree, grevityCerter);

        return grevityCerter[0];
    }

    /**
     *
     * @param curr
     * @param pre
     * @param tree
     * @param grevityCerter
     * @return the verticeNum of curr, excludes the pre
     */
    private int dfs(int curr, int pre, int vertice, List<List<Integer>> tree, int[] grevityCerter) {

        int subtreeNode;
        int total = 1; // the total of vertice number on edge pre-curr, curr inclusive
        int largestSubtree = 0; //the nodes number of curr's the largest sub-tree
        for (int next : tree.get(curr)) {
            if (next == pre) {
                continue;
            }

            subtreeNode = dfs(next, curr, vertice, tree, grevityCerter);
            total += subtreeNode;
            largestSubtree = Math.max(largestSubtree, subtreeNode);
        }

        largestSubtree = Math.max(largestSubtree, vertice - total); // graph.size() - total is the subtree of curr, on edge curr-pre
        if (grevityCerter[1] > largestSubtree || (grevityCerter[1] == largestSubtree && grevityCerter[0] > curr)) {
            grevityCerter[0] = curr;
            grevityCerter[1] = largestSubtree;
        }

        return total;
    }

    
    public static void main(String[] args){
        int edges = 3;
        List<List<Integer>> tree = new ArrayList<>(edges + 2); 
        for(int i = 0; i < edges + 2; i++){
            tree.add(new ArrayList<>(edges));
        }
        
        System.out.println( " " + tree.size() + " " + tree.get(2).size() );
    } 
    
}
