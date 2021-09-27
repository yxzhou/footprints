package dailyCoding.graph;

// Suppose we have some input data describing a graph of relationships between parents and children over multiple generations. The data is formatted as a list of (parent, child) pairs, where each individual is assigned a unique integer identifier.

// For example, in this diagram, 3 is a child of 1 and 2, and 5 is a child of 4:

// 1   2   4
//  \ /   / \
//   3   5   8
//    \ / \   \
//     6   7   10



// Write a function that takes the graph, as well as two of the individuals in our dataset, as its inputs and returns true if and only if they share at least one ancestor.

// Sample input and output:
// hasCommonAncestor(parentChildPairs, 3, 8) => false
// hasCommonAncestor(parentChildPairs, 5, 8) => true
// hasCommonAncestor(parentChildPairs, 6, 8) => true
// hasCommonAncestor(parentChildPairs, 1, 3) => false

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Queue;


public class FindParents {

    public static void main(String[] args) {
        int[][] parentChildPairs = new int[][]{
                {1, 3}, {2, 3}, {3, 6}, {5, 6}, {5, 7},
                {4, 5}, {4, 8}, {8, 10}
        };

        FindParents sv = new FindParents();

        List<List<Integer>> result = sv.findNodesWithZeroAndOneParents(parentChildPairs);

        System.out.println(" = start == ");

        for (List<Integer> list : result) {
            System.out.print(" [ ");
            for (Integer n : list) {

                System.out.print(n + ", ");
            }

            System.out.println(" ] ");
        }

        System.out.println(sv.hasCommonAncestor(parentChildPairs, 3, 8));
        System.out.println(sv.hasCommonAncestor(parentChildPairs, 5, 8));
        System.out.println(sv.hasCommonAncestor(parentChildPairs, 6, 8));
        System.out.println(sv.hasCommonAncestor(parentChildPairs, 1, 3));
    }

    public boolean hasCommonAncestor(int[][] parentChildPairs, int child1, int child2) {
        if (parentChildPairs == null) {
            return false;
        }

        Map<Integer, Set<Integer>> mapper = new HashMap<>();

        for (int[] pair : parentChildPairs) {
            if (!mapper.containsKey(pair[1])) {
                mapper.put(pair[1], new HashSet<>());
            }

            mapper.get(pair[1]).add(pair[0]);
        }

        if (!mapper.containsKey(child1) || !mapper.containsKey(child2)) {
            return false;
        }

        Set<Integer> child1Ancestors = findAncestor(mapper, child1);
        Set<Integer> child2Ancestors = findAncestor(mapper, child2);

        for (Integer child1Ancestor : child1Ancestors) {
            if (child2Ancestors.contains(child1Ancestor)) {
                return true;
            }
        }

        return false;
    }

    private Set<Integer> findAncestor(Map<Integer, Set<Integer>> childParentsMap, Integer child) {
        Set<Integer> ancestors = new HashSet<>(childParentsMap.get(child));
        Queue<Integer> queue = new LinkedList<>(childParentsMap.get(child));

        while (!queue.isEmpty()) {
            int ancestor = queue.poll();

            if (!childParentsMap.containsKey(ancestor)) {
                continue;
            }

            for (Integer n : childParentsMap.get(ancestor)) {
                if (!ancestors.contains(n)) {
                    ancestors.add(n);
                    queue.add(n);
                }
            }
        }

        return ancestors;
    }

    public List<List<Integer>> findNodesWithZeroAndOneParents(int[][] parentChildPairs) {

        List<List<Integer>> result = new ArrayList<>();
        result.add(new ArrayList<>());
        result.add(new ArrayList<>());

        if (parentChildPairs == null) {
            return result;
        }

        int size = parentChildPairs.length;
        Map<Integer, Integer> indegrees = new HashMap<>(size);
        Set<Integer> parents = new HashSet<>();

        for (int[] pair : parentChildPairs) {
            parents.add(pair[0]);

            if (!indegrees.containsKey(pair[1])) {
                indegrees.put(pair[1], 1);
            } else {
                indegrees.put(pair[1], indegrees.get(pair[1]) + 1);
            }
        }

        List<Integer> oneParents = result.get(1);
        for (Integer key : indegrees.keySet()) {
            if (indegrees.get(key) == 1) {
                oneParents.add(key);
            }
        }

        List<Integer> zeroParents = result.get(0);
        for (Integer node : parents) {
            if (!indegrees.containsKey(node)) {
                zeroParents.add(node);
            }
        }

        return result;
    }
}
