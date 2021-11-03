package graph.topological;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

/**
 * There are a total of n courses you have to take, labeled from 0 to n - 1.
 *
 * Some courses may have prerequisites, for example to take course 0 you have to first take course 1, which is expressed
 * as a pair: [0,1]
 *
 * Given the total number of courses and a list of prerequisite pairs, is it possible for you to finish all courses?
 *
 * For example:
 *Input: 2, [[1,0]] There are a total of 2 courses to take. To take course 1 you should have finished course 0. So it
 * is possible.
 *
 * Input: 2, [[1,0],[0,1]] There are a total of 2 courses to take. To take course 1 you should have finished course 0,
 * and to take course 0 you should also have finished course 1. So it is impossible.
 */
public class CourseSchedule {
	
    public boolean canFinish(int numCourses, int[][] prerequisites) {
        if(prerequisites == null ){
            return true;
        }

        
        int[] inDegrees = new int[numCourses];
        Map<Integer, List<Integer>> requisites = new HashMap<>();
        for(int[] pair : prerequisites){
            inDegrees[pair[0]]++;

            requisites.putIfAbsent(pair[1], new ArrayList<>());
            requisites.get(pair[1]).add(pair[0]);
        }

        Queue<Integer> queue = new LinkedList<>();
        int count = 0;

        for(int i = 0; i < numCourses; i++){
            if(inDegrees[i] == 0){
                queue.add(i);
            }
        }

        int top;
        while(!queue.isEmpty()){
            top = queue.poll();
            count++;

            if(!requisites.containsKey(top)){
                continue;
            }

            for(int next : requisites.get(top)){
                inDegrees[next]--;

                if(inDegrees[next] == 0){
                    queue.add(next);
                }
            }
        }

        return count == numCourses;
    }

}