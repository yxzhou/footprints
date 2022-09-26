/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sweepLine;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import org.junit.Assert;
import sweepLine.interval.Interval;
import util.Misc;


/**
 * _https://www.lintcode.com/problem/850/
 * 
 *
 * We are given a list schedule of employees, which represents the working time for each employee.
 *
 * Each employee has a list of non-overlapping Intervals, and these intervals are in sorted order.
 *
 * Return the list of finite intervals representing common, positive-length free time for all employees, also in sorted
 * order.
 *
 * The Intervals is an 1d-array. Each two numbers shows an interval. For example, [1,2,8,10] represents that the
 * employee works in [1,2] and [8,10].
 *
 * Also, we wouldn't include intervals like [5, 5] in our answer, as they have zero length.
 * 
 * Notes:
 *   1.schedule and schedule[i] are lists with lengths in range [1, 100].
 *   2.0 <= schedule[i].start < schedule[i].end <= 10^8.
 * 
 * 
 * Example 1:
 * Input：schedule = [[1,2,5,6],[1,3],[4,10]]
 * Output：[(3,4)]
 * Explanation:
 * There are a total of three employees, and all common free time intervals would be [-inf, 1], [3, 4], [10, inf]. We 
 * discard any intervals thatcontain inf as they aren't finite.
 * 
 * Example 2:
 * Input：schedule = [[1,3,6,7],[2,4],[2,5,9,12]]
 * Output：[(5,6),(7,9)]
 * Explanation：
 * There are a total of three employees, and all common free time intervals would be [-inf, 1], [5, 6], [7, 9],[12,inf].
 * We discard any intervals that contain inf as they aren't finite.
 * 
 * 
 * Thoughts:
 * define n as the total number of intervals 
 * s1, merge intervals with PriorityQueue<Interval>
 * Time complexity O(n*logn), Space O(n)
 * 
 * s2, sweepLine,  TreeMap<x, change> 
 * Time complexity O(n * logn) Space O(n)
 * 
 * s2 is better, expecially when there are duplicated starts and ends
 * 
 */
public class EmployeeFreeTime {
    /**
     * @param schedule: a list schedule of employees
     * @return Return a list of finite intervals 
     */
    public List<Interval> employeeFreeTime(int[][] schedule) {
        
        if(schedule == null){
            return Collections.EMPTY_LIST;
        }

        TreeMap<Integer, Integer> changes = new TreeMap<>();
        int x;
        for(int[] employee : schedule){
            for(int i = 0; i < employee.length; i+=2){
                x = employee[i];
                changes.put(x, changes.getOrDefault(x, 0) + 1);

                x = employee[i + 1];
                changes.put(x, changes.getOrDefault(x, 0) - 1);
            }
        }

        List<Interval> result = new ArrayList<>();

        int count = 0;
        int start = 0;
        for(Map.Entry<Integer, Integer> change : changes.entrySet()){
            count += change.getValue();

            if(count == 0 && start == 0){
                start = change.getKey();
                
            }else if(count > 0 && start > 0){ 
                result.add(new Interval(start, change.getKey()));
                start = 0;
            }// else ((count == 0 && start != -1) || (count > 0 && start == -1))
        }

        return result;
    }
    
    public static void main(String[] args){
        EmployeeFreeTime sv = new EmployeeFreeTime();
        
        int[][][][] inputs = {
            //{schedule, expect}
            {
                {{1, 2, 5, 6}, {1, 3}, {4, 10}},
                {{3, 4}}
            },
            {
                {{1, 3, 6, 7}, {2, 4}, {2, 5, 9, 12}},
                {{5, 6}, {7, 9}}
            }
        };
        
        for(int[][][] input : inputs){
            System.out.println(String.format(" Input: %s \nOutput: %s", Misc.array2String(input[0]).toString(), Misc.array2String(sv.employeeFreeTime(input[0])).toString() ));
            
            
            Assert.assertArrayEquals(Interval.build(input[1]), sv.employeeFreeTime(input[0]).toArray());
        }
    }
}
