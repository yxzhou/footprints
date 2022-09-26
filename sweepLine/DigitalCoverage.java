/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sweepLine;

import sweepLine.interval.Interval;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.junit.Assert;
import util.Misc;


/**
 *  _https://www.lintcode.com/problem/1397
 * 
 * Given some intervals, what is the number with the most coverage times? If there are multiple numbers, output the
 * smallest number.
 *
 * Notes:
 *   the number of the interval is not more than 10^5.
 *   the left and right endpoints of the interval are greater than 0 not more than 10^5.
 * 
 * Example 1:
 * Input:intervals = [(1,7),(2,8)]
 * Output:2
 * Explanation:2 is covered 2 times, and is the number of 2 times the smallest number.

 * Example 2:
 * Input:intervals = [(1,3),(2,3),(3,4)]
 * Output:3
 * Explanation:3 is covered 3 times. 
 * 
 * 
 */
public class DigitalCoverage {
    /**
     * @param intervals: The intervals
     * @return The answer
     */
    public int digitalCoverage(List<Interval> intervals) {
        if(intervals == null){
            return 0;
        }

        List<int[]> list = new ArrayList<>();
        for(Interval it : intervals){
            list.add(new int[]{it.start, 1});
            list.add(new int[]{it.end, -1});
        }

        Collections.sort(list, (a1, a2) -> a1[0] == a2[0] ? a2[1] - a1[1] : a1[0] - a2[0] );

        int count = 0;
        int max = 0;
        int r = 0;

        for(int[] a : list){
            count += a[1];
            if(max < count){
                max = count;
                r = a[0];
            }
        }

        return r;
    }
    
    /**
     * @param intervals: The intervals
     * @return The answer
     */
    public int digitalCoverage_n(List<Interval> intervals) {
        if(intervals == null){
            return 0;
        }

        int[] counts = new int[100005];
        for(Interval it : intervals){
            counts[it.start]++;
            counts[it.end + 1]--;
        }

        int count = 0;
        int max = 0;
        int r = 0;

        for(int i = 1, n = counts.length; i < n; i++){
            count += counts[i];
            if(max < count){
                max = count;
                r = i;
            }
        }

        return r;
    }
    
    
    /**
     * @param intervals: The intervals
     * @return The answer
     */
    public int digitalCoverage_x(List<Interval> intervals) {
        if(intervals == null){
            return 0;
        }
        
        TreeMap<Integer, Integer> map = new TreeMap<>();//map<the start or end, the change which 1 on start, -1 on end>
        
        for(Interval it: intervals){
            map.put(it.start, map.getOrDefault(it.start, 0) + 1);
            map.put(it.end + 1, map.getOrDefault(it.end + 1, 0) - 1);
        }
        
        int result = 0;
        int sum = 0;
        int max = 0;
        for(Map.Entry<Integer, Integer> entry : map.entrySet()){
            sum += entry.getValue();
            
            if(max < sum){
                max = sum;
                result = entry.getKey();
            }
            
        }
        
        return result;
    }
    
    
    public static void main(String[] args){
        
        int[][][][] inputs = {
            {
                {{1,7},{2,8}},
                {{2}}
            },
            {
                {{1,3},{2,3},{3,4}},
                {{3}}
            }
        };
        
        DigitalCoverage sv = new DigitalCoverage();
        
        for(int[][][] input : inputs){
            System.out.println(String.format("-Input-: %s", Misc.array2String(input[0]) ));
            
            Assert.assertEquals(input[1][0][0], sv.digitalCoverage(Arrays.asList(Interval.build(input[0]))));
            Assert.assertEquals(input[1][0][0], sv.digitalCoverage_n(Arrays.asList(Interval.build(input[0]))));
            Assert.assertEquals(input[1][0][0], sv.digitalCoverage_x(Arrays.asList(Interval.build(input[0]))));
        }
    }
        
}
