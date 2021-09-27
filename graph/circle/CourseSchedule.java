package graph.circle;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class CourseSchedule {

    public boolean canFinish(int numCourses, int[][] prerequisites) {

        Map<Integer, List<Integer>> map = new HashMap<>();//<courseId, courseId's prerequistites>
        for(int[] edge : prerequisites){
            map.putIfAbsent(edge[0], new LinkedList<>());
            map.get(edge[0]).add(edge[1]);
        }

        int[] states = new int[numCourses]; // 0, default; 1, checking, 2 checked

        for(int courseId = 0; courseId < numCourses; courseId++){
            if(dfs(map, courseId, states) ){
                return false;
            }
        }

        return true;
    }

    //return true if there is a circle
    private boolean dfs(Map<Integer, List<Integer>> map, int courseId, int[] states){
        if(states[courseId] == 2){
            return false;
        }
        if(states[courseId] == 1){
            return true;
        }

        states[courseId] = 1;

        if(map.containsKey(courseId)){
            for(int next : map.get(courseId)){
                if(dfs(map, next, states)){
                    return true;
                }
            }
        }

        states[courseId] = 2;
        return false;
    }

}
