package datastructure.unionFind;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 *
 * Given n nodes labeled from 0 to n - 1 and a list of undirected edges (each edge is a pair of nodes), write a function
 * to check whether these edges make up a valid tree.
 *
 * Example 
 * Given n = 5 and edges = [[0, 1], [0, 2], [0, 3], [1, 4]], 
 * return true.
 *
 * Given n = 5 and edges = [[0, 1], [1, 2], [2, 3], [1, 3], [1, 4]], 
 * return false.
 *
 * Note You can assume that no duplicate edges will appear in edges. Since all edges are undirected, [0, 1] is the same
 * as [1, 0] and thus will not appear together in edges.
 *
 */

public class GraphValidTree {

    /**
     * @param n an integer
     * @param edges a list of undirected edges
     * @return true if it's a valid tree, or false
     */
    public boolean validTree(int n, int[][] edges) {
        //check, to Tree, n vertices only have n-1 edges
        if (edges == null || n - 1 != edges.length) {
            return false;
        }

        int[] parent = new int[n];

        for (int i = 0; i < n; i++) {
            parent[i] = i;
        }

        for (int[] pair : edges) {
            if (find(parent, pair[0]) == find(parent, pair[1])) {// has loop
                return false;
            }

            union(parent, pair[0], pair[1]);
        }

        return true;
    }
	
    private void union(int[] parent, int p, int q) {
        int pRoot = find(parent, p);
        int qRoot = find(parent, q);

        if (pRoot == qRoot) {
            return;
        }

        parent[qRoot] = pRoot;
    }

    private int find(int[] parent, int p) {

        while (parent[p] != p) {
            p = parent[p];
        }

        return p;
    }

    /**
     * validTree_topological not work
     */
    public boolean validTree_topological(int n, int[][] edges) {
        if (null == edges || n < 2 || n - 1 != edges.length) {
            return false;
        }

        List<Integer>[] neighbors = new ArrayList[n];
        int[] inDegrees = new int[n]; //default all are 0
        for (int[] edge : edges) {
            if (neighbors[edge[0]] == null) {
                neighbors[edge[0]] = new ArrayList<>();
            }
            neighbors[edge[0]].add(edge[1]);
            inDegrees[edge[1]]++;

            if (neighbors[edge[1]] == null) {
                neighbors[edge[1]] = new ArrayList<>();
            }
            neighbors[edge[1]].add(edge[0]);
            inDegrees[edge[0]]++;
        }

        Queue<Integer> queue = new LinkedList<>();
        int count = 0;
        for (int i = 0; i < n; i++) {
            if (inDegrees[i] == 0) {
                queue.add(i);
                count++;
            }
        }

        int curr;
        while (!queue.isEmpty()) {
            curr = queue.poll();

            if (null == neighbors[curr]) {
                continue;
            }

            for (Integer next : neighbors[curr]) {
                inDegrees[next]--;

                if (inDegrees[next] == 0) {
                    queue.add(next);
                    count++;
                }
            }
        }

        return count == n;
    }

    @Test
    public void test() {
        //Assert.assertTrue(validTree_topological(5, new int[][]{{0, 1}, {0, 2}, {0, 3}, {1, 4}}));
        Assert.assertFalse(validTree_topological(5, new int[][]{{0, 1}, {0, 4}, {1, 4}, {2, 3}}));
    }
}
