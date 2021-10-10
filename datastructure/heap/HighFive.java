/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datastructure.heap;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

/**
 *
 * Each student has two attributes ID and scores. Find the average of the top five scores for each student.
 * 
 * Example 1:
 * Input: [[1,91],[1,92],[2,93],[2,99],[2,98],[2,97],[1,60],[1,58],[2,100],[1,61]]
 * Output:
 *   1: 72.40
 *   2: 97.40
 
 * 
 */
public class HighFive {
    /** Definition for a Record */
    class Record {
        public int id, score;

        public Record(int id, int score) {
            this.id = id;
            this.score = score;
        }
    }
 
    
    /**
     * @param results a list of <student_id, score>
     * @return find the average of 5 highest scores for each person
     * Map<Integer, Double> (student_id, average_score)
     */
    public Map<Integer, Double> highFive(Record[] results) {
        if(results == null){
            return Collections.EMPTY_MAP;
        }

        Map<Integer, PriorityQueue<Integer>> minHeaps = new HashMap<>();
        PriorityQueue<Integer> pq;
        for(Record data : results){
            minHeaps.putIfAbsent(data.id, new PriorityQueue<>());
            pq = minHeaps.get(data.id);

            pq.add(data.score);
            if(pq.size() > 5){
                pq.poll();
            }
        }

        Map<Integer, Double> map = new HashMap<>();
        double sum = 0d;
        for(Integer id : minHeaps.keySet()){
            pq = minHeaps.get(id);

            if(pq.size() < 5){
                continue;
            }

            sum = 0d;
            while(!pq.isEmpty()){
                sum += pq.poll();
            }

            map.put(id, sum / 5);
        }

        return map;
    }
    
}
