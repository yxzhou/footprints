package fgafa.leetcode.airbnb;

import java.util.*;

/**
 *
 * 有向图最少点遍历：每选中一个点，则可从该点到达的所有点都算作被遍历了 求最少选中多少个点可以遍历全图
 *
 * refer:  TraversalLevalOrder
 *
 */

public class LeastVertice {

    public List<Integer> leastVertice(boolean[][] edges){
        List<Integer> result = new ArrayList<>();

        if(null == edges || 0 == edges.length){
            return result;
        }

        int n = edges.length;
        boolean[] visited = new boolean[n]; // default all are false

        List<Integer> orders = new ArrayList<>(); //store the topological order

        for(int i = 0; i < n; i++){
            if(!visited[i]){
                preDFS(edges, visited, orders, i);
            }
        }

        Arrays.fill(visited, false);

        int id = 0;
        List<Set<Integer>> components = new ArrayList<>();
        for(int i = n - 1; i >= 0; i--){
            if(!visited[orders.get(i)]){
                dfs(edges, visited, components, orders.get(i), id);
                id++;
            }
        }

        int[] inDegree = new int[n];
        Set<Integer>[] next = new HashSet[n];
        for(int from = 0; from < id; from++){
            for(int to = 0; to < id; to++){
                if(from == to){
                    continue;
                }

                boolean found = false;
                for(int x : components.get(from)){
                    if(found) {
                        break;
                    }

                    for(int y : components.get(to)){
                        if(edges[x][y]){
                            inDegree[to]++;

                            next[from].add(to);

                            found = true;
                            break;
                        }
                    }
                }
            }
        }

//        for(int i = 0; i < id; i++){
//            if(inDegree[i] == 0){
//                result.add(components.get(i).begin());
//            }
//        }

        return result;
    }

    private void preDFS(boolean[][] edges, boolean[] visited, List<Integer> orders, int index){
        visited[index] = true;
        orders.add(index);

        for(int i = 0; i < edges.length; i++){
            if(edges[index][i] && !visited[i]){
                preDFS(edges, visited, orders, i);
            }
        }
    }

    private void dfs(boolean[][] edges, boolean[] visited, List<Set<Integer>> components, int index, int id){
        visited[index] = true;

        if(components.size() == id){
            components.add(new HashSet<>());
        }
        components.get(id).add(index);

        for(int i = 0; i < edges.length; i++){
            if(edges[i][index] && !visited[i]){
                dfs(edges, visited, components, i, id);
            }
        }
    }
}
















