package dailyCoding.graph;

import java.util.*;

import util.Misc;
import org.junit.Test;

/**
 *
 * We're given a hashmap with a key courseId and value a list of courseIds, which represents that the prerequsite of courseId is courseIds. Return a sorted ordering of courses such that we can finish all courses.

 Return null if there is no such ordering.

 For example, given {'CSC300': ['CSC100', 'CSC200'], 'CSC200': ['CSC100'], 'CSC100': []}, should return ['CSC100', 'CSC200', 'CSCS300'].
 *
 * Tags: airbnb
 *
 */

public class Courses {

    public List<String> courses(Map<String, List<String>> prerequsites){

        if(prerequsites == null || 0 == prerequsites.size()){
            return null;
        }

        int size = prerequsites.size();
        String[] courseId2Names = new String[size];
        Map<String, Integer> courseName2Ids = new HashMap<>(size);
        int id = 0;
        for(String courseName : prerequsites.keySet()) {
            courseId2Names[id] = courseName;
            courseName2Ids.put(courseName, id);
            id++;
        }

        int[] indegrees = new int[size];
        Map<Integer, List<Integer>> nexts = new HashMap<>(size);

        Queue<Integer> queue = new LinkedList<>();

        for(String courseName : prerequsites.keySet()){
            int childId = courseName2Ids.get(courseName);
            indegrees[childId] = prerequsites.get(courseName).size();

            if(indegrees[childId] == 0){
                queue.add(childId);
            }

            for(String prerequsite : prerequsites.get(courseName)){
                int parentId = courseName2Ids.get(prerequsite);
                if(!nexts.containsKey(parentId)){
                    nexts.put(parentId, new ArrayList<>());
                }

                nexts.get(parentId).add(childId);
            }
        }

        List<String> sequence = new ArrayList<>(size);

        while(!queue.isEmpty()){
            int courseId = queue.poll();
            sequence.add(courseId2Names[courseId]);

            if(!nexts.containsKey(courseId)){
                continue;
            }

            for(Integer childId : nexts.get(courseId)){
                if(indegrees[childId] == 1){
                    queue.add(childId);
                }else{
                    indegrees[childId]--;
                }
            }
        }

        return size == sequence.size()? sequence : null;
    }

    @Test public void test(){

        Map<String, List<String>> prerequsites = new HashMap<>();
        prerequsites.put("CSC300", Arrays.asList(new String[]{"CSC100", "CSC200"}));
        prerequsites.put("CSC200", Arrays.asList(new String[]{"CSC100"}));
        prerequsites.put("CSC100", Arrays.asList(new String[]{}));

        Misc.printList(courses(prerequsites));

    }
}
