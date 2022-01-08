/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package geometry;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.junit.Assert;
import util.Misc;

/**
 *_https://www.lintcode.com/problem/908
 * 
 * Given n points on a 2D plane, find if there is such a line parallel to y-axis that reflect the given points.
 * 
 * Example1
 * Input: [[1,1],[-1,1]]
 * Output: true
 * 
 * Example2
 * Input: [[1,1],[-1,-1]]
 * Output: false
 * 
 * Challenge: Could you do better than O(n^2)?
 * 
 */
public class LineReflection {
    /**
     * @param points: n points on a 2D plane
     * @return if there is such a line parallel to y-axis that reflect the given points
     */
    public boolean isReflected(int[][] points) {
        if(points == null || points.length < 2){
            return true;
        }

        Map<Integer, List<Integer>> y = new HashMap<>(); //< y_value, List<x_value> >
        for(int[] point : points){
            y.putIfAbsent(point[1], new LinkedList<>());
            y.get(point[1]).add(point[0]);
        }

        Float x = null;
        float half;
        for(List<Integer> xList : y.values()){
            Collections.sort(xList);

            for(int l = 0, r = xList.size() - 1; l <= r;  ){
                while(l < r && xList.get(l) == xList.get(l + 1) ){
                    l++;
                }

                while(l < r && xList.get(r) == xList.get(r - 1) ){
                    r--;
                }

                half = ((float)xList.get(l) + xList.get(r)) / 2;
                if(x != null && x != half ){
                    return false;
                }else{
                    x = half;
                    l++;
                    r--;
                }
            }
        }

        return true;
    }

    public boolean isReflected_n(int[][] points) {
        if (points == null || points.length < 2) {
            return true;
        }
        
        Map<Integer, Set<Integer>> y = new HashMap<>(); //< y_value, List<x_value> >
        int max = Integer.MIN_VALUE;
        int min = Integer.MAX_VALUE;
        for(int[] point : points){
            max = Math.max(max, point[0]);
            min = Math.min(min, point[0]);
            
            y.putIfAbsent(point[1], new HashSet<>());
            y.get(point[1]).add(point[0]);
        }
        
        int sum = max + min; // (max + min) / 2 is the line
        for(Set<Integer> set : y.values()){
            for(int x : set){
                if(!set.contains(sum - x) ){ // x != sum/2 && !set.contains(sum - x)
                    return false;
                }
            }
        }
        
        return true;
    }
    
    public static void main(String[] args){
        LineReflection sv = new LineReflection();
        
        int[][][] inputs = {
            {},
            {{1, 1}, {-1, -1}},
            {{1, 1}, {-1, 1}},
            {{1, 1}, {1, 1}, {1, 1}},
            {{0, 0}, {1, 0}, {3, 0}}
        
        };
        
        boolean[] expects = {
            true,
            false,
            true,
            true,
            false
        };
        
        for(int i = 0; i < expects.length; i++){
            System.out.println(i + "----------" +  Misc.array2String(inputs[i]));
            
            Assert.assertEquals(expects[i], sv.isReflected_n(inputs[i]));
            Assert.assertEquals(expects[i], sv.isReflected(inputs[i]));
        }
    }
}
