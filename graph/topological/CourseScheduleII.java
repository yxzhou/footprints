/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package graph.topological;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

/**
 *
 * There are a total of n courses you have to take, labeled from 0 to n - 1. Some courses may have prerequisites, for
 * example to take course 0 you have to first take course 1, which is expressed as a pair: [0,1]
 *
 * Given the total number of courses and a list of prerequisite pairs, return the ordering of courses you should take to
 * finish all courses.
 *
 * There may be multiple correct orders, you just need to return one of them. If it is impossible to finish all courses,
 * return an empty array.
 *
 * Example 1:
 *Input: n = 2, prerequisites = [[1,0]] 
 * Output: [0,1] 
 * 
 * Example 2:
 * Input: n = 4, prerequisites = [[1,0],[2,0],[3,1],[3,2]] 
 * Output: [0,1,2,3] or [0,2,1,3]
 *
 */
public class CourseScheduleII {
    public int[] findOrder(int numCourses, int[][] prerequisites) {
        if (prerequisites == null || numCourses < 1) {
            return new int[0];
        }

        int[] inDegrees = new int[numCourses];
        Map<Integer, List<Integer>> nexts = new HashMap<>(); // assume there is no duplicate in prerequisites

        for (int[] pair : prerequisites) {
            inDegrees[pair[0]]++;

            nexts.putIfAbsent(pair[1], new ArrayList<>());
            nexts.get(pair[1]).add(pair[0]);
        }

        Queue<Integer> queue = new LinkedList<>();
        for (int i = 0; i < numCourses; i++) {
            if (inDegrees[i] == 0) {
                queue.add(i);
            }
        }

        int[] result = new int[numCourses];
        int k = 0;
        int top;
        while (!queue.isEmpty()) {
            top = queue.poll();
            result[k++] = top;

            if (nexts.get(top) == null) {
                continue;
            }

            for (int next : nexts.get(top)) {
                inDegrees[next]--;
                if (inDegrees[next] == 0) {
                    queue.add(next);
                }
            }
        }

        return k == numCourses ? result : new int[0];
    }
}
