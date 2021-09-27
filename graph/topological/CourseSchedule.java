package graph.topological;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Set;


public class CourseSchedule {
	
	/**
	 * There are a total of n courses you have to take, labeled from 0 to n - 1.
	 * 
	 * Some courses may have prerequisites, for example to take course 0 you
	 * have to first take course 1, which is expressed as a pair: [0,1]
	 * 
	 * Given the total number of courses and a list of prerequisite pairs, is it
	 * possible for you to finish all courses?
	 * 
	 * For example:
	 * 
	 * Input: 2, [[1,0]] 
	 * There are a total of 2 courses to take. 
	 * To take course 1 you should have finished course 0. So it is possible.
	 * 
	 * Input: 2, [[1,0],[0,1]] 
	 * There are a total of 2 courses to take. 
	 * To take course 1 you should have finished course 0, and to take course 0 you
	 * should also have finished course 1. So it is impossible.
	 */
    public boolean canFinish(int numCourses,
                             int[][] prerequisites) {
        if (null == prerequisites || 0 == prerequisites.length) {
            return true;
        }

        // Traversal all edges
        int[] inDegrees = new int[numCourses]; // default all are 0
        Map<Integer, Set<Integer>> pool = new HashMap<Integer, Set<Integer>>();
        Set<Integer> neighbors;
        for (int[] edge : prerequisites) {

            neighbors = pool.get(edge[1]);
            if (null == neighbors) {
                neighbors = new HashSet<Integer>();
                pool.put(edge[1], neighbors);
            }

            if (!neighbors.contains(edge[0])) { // ** for case there are duplicated pair
                inDegrees[edge[0]]++;
                neighbors.add(edge[0]);
            }

        }

        // find out all nodes that inDegree is 0
        Queue<Integer> queue = new LinkedList<Integer>();
        for (int i = 0; i < inDegrees.length; i++) {
            if (0 == inDegrees[i]) {
                queue.add(i);
            }
        }

        int curr;
        while (!queue.isEmpty()) {
            curr = queue.poll();

            if (pool.containsKey(curr)) {
                for (int neighbor : pool.get(curr)) {
                    if (1 == inDegrees[neighbor]) {
                        queue.add(neighbor);
                    }
                    inDegrees[neighbor]--;
                }
            }
        }

        // check if there is loop
        for (int inDegree : inDegrees) {
            if (0 < inDegree) {
                return false;
            }
        }

        return true;
    }
	
	/**
	 * There are a total of n courses you have to take, labeled from 0 to n - 1.
	 * 
	 * Some courses may have prerequisites, for example to take course 0 you
	 * have to first take course 1, which is expressed as a pair: [0,1]
	 * 
	 * Given the total number of courses and a list of prerequisite pairs,
	 * return the ordering of courses you should take to finish all courses.
	 * 
	 * There may be multiple correct orders, you just need to return one of
	 * them. If it is impossible to finish all courses, return an empty array.
	 * 
	 * For example:
	 * 
	 * Input: 2, [[1,0]] 
	 * There are a total of 2 courses to take. To take course 1 you
	 * should have finished course 0. So the correct course order is [0,1]
	 * 
	 * Input: 4, [[1,0],[2,0],[3,1],[3,2]] 
	 * There are a total of 4 courses to take. To take course 3 you should have
	 * finished both courses 1 and 2. Both courses 1 and 2 should be taken 
	 * after you finished course 0. So one correct course order is [0,1,2,3]. 
	 * Another correct ordering is[0,2,1,3].
     *
     *
     *
	 */

    public int[] findOrder(int numCourses,
                           int[][] prerequisites) {
        // check input
        if (null == prerequisites) {
            return new int[0];
        }

        // Traversal all edges
        Map<Integer, Set<Integer>> pool = new HashMap<>();
        int[] inDegrees = new int[numCourses]; // default all are 0
        Set<Integer> neighbors;
        for (int[] edge : prerequisites) {
            neighbors = pool.get(edge[1]);

            if (null == neighbors) {
                neighbors = new HashSet<>();
                pool.put(edge[1], neighbors);
            }

            if (!neighbors.contains(edge[0])) { // ** for case there are duplicated pair
                neighbors.add(edge[0]);
                inDegrees[edge[0]]++;
            }
        }

        // topological sort, by the vertice whose inDegree is 0
        Queue<Integer> queue = new LinkedList<>();
        for (int vertice = 0; vertice < inDegrees.length; vertice++) {
            if (0 == inDegrees[vertice]) {
                queue.add(vertice);
            }
        }

        int[] result = new int[numCourses];
        int i = 0;
        int curr;
        while (!queue.isEmpty()) {
            curr = queue.poll();
            result[i++] = curr;

            if (pool.containsKey(curr)) {
                for (int neighbor : pool.get(curr)) {
                    inDegrees[neighbor]--;

                    if (0 == inDegrees[neighbor]) {
                        queue.add(neighbor);
                    }
                }
            }
        }

        // check if there is loop
        for (int inDegree : inDegrees) {
            if (0 < inDegree) {
                return new int[0];
            }
        }

        return result;
    }

}