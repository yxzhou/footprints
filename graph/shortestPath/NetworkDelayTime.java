package graph.shortestPath;


import java.util.*;

/**
 * Leetode #743
 *
 * There are N network nodes, labelled 1 to N.
 *
 * Given times, a list of travel times as directed edges times[i] = (u, v, w), where u is the source node, v is the
 * target node, and w is the time it takes for a signal to travel from source to target.
 *
 * Now, we send a signal from a certain node K. How long will it take for all nodes to receive the signal?
 * If it is impossible, return -1.
 *
 *
 *
 * Example 1:
 *
 *
 *
 * Input: times = [[2,1,1],[2,3,1],[3,4,1]], N = 4, K = 2
 * Output: 2
 *
 *
 * Note:
 *
 * N will be in the range [1, 100].
 * K will be in the range [1, N].
 * The length of times will be in the range [1, 6000].
 * All edges times[i] = (u, v, w) will have 1 <= u, v <= N and 0 <= w <= 100.
 *
 */

public class NetworkDelayTime {


    public int networkDelayTime(int[][] times, int N, int K) {

        //build the graph
        Map<Integer, List<int[]>> graph = new HashMap<>();
        for(int[] triple : times){
            graph.computeIfAbsent(triple[0] - 1, k -> new LinkedList<>()).add(new int[]{triple[1] - 1, triple[2]});
        }

        //dijkstra
        int[] costs = new int[N];
        Arrays.fill(costs, Integer.MAX_VALUE);

        int start = K - 1;
        costs[start] = 0;

        PriorityQueue<int[]> queue = new PriorityQueue<>( (a1, a2) -> Integer.compare(a1[1], a2[1]) );
        queue.offer(new int[]{start, 0});

        Set<Integer> visited = new HashSet<>(N);
        int curr;
        int cost;
        while(visited.size() < N && !queue.isEmpty()){
            curr = queue.poll()[0];

            visited.add(curr);

            if(!graph.containsKey(curr)){
                continue;
            }

            for(int[] next : graph.get(curr)){
                if(visited.contains(next[0])){
                   continue;
                }

                cost = costs[curr] + next[1];
                if(costs[next[0]] > cost){
                    costs[next[0]] = cost;

                    queue.add(new int[]{next[0], costs[next[0]]});
                }

            }
        }

        int max = 0;
        for(int c : costs){
            max = Math.max(max, c);
        }

        return max == Integer.MAX_VALUE ? -1 : max;
    }



}
