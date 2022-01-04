/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package leetcode.google;

import java.util.HashMap;
import java.util.Map;
import org.junit.Assert;

/**
 *_https://www.lintcode.com/problem/1237
 * 
 * Given n points in the plane that are all pairwise distinct, a "boomerang" is a tuple of points (i, j, k) such that
 * the distance between i and j equals the distance between i and k (the order of the tuple matters).
 *
 * Find the number of boomerangs. You may assume that n will be at most 500 and coordinates of points are all in the
 * range [-10000, 10000] (inclusive).
 *
 * Example:
 * Input: [[0,0],[1,0],[2,0]]  Output: 2
 * Explanation: The two boomerangs are [[1,0],[0,0],[2,0]] and [[1,0],[2,0],[0,0]]
 * 
 */
public class NumberOfBoomerangs {
    /**
     * @param points: a 2D array
     * @return: the number of boomerangs
     */
    public int numberOfBoomerangs_n(int[][] points) {
        if(points == null ){
            return 0;
        }

        int n = points.length;

        int[][] distances = new int[n][n];
        int r;
        for(int i = 0; i < n; i++){
            for(int j = i + 1; j < n; j++ ){
                r = distance(points[i], points[j]);
                distances[i][j] = r;
                distances[j][i] = r;
            }
        }

        int result = 0;
        Map<Integer, Integer> circle; 
        for(int i = 0; i < n; i++){
            circle = new HashMap<>(); 

            for(int j = 0; j < n; j++ ){
                r = distances[i][j];
                circle.put( r, circle.getOrDefault(r, 0) + 1 );
            }

            for(int count : circle.values() ){
                result += count * (count - 1);
            }
        }

        return result;
    }
    
    public int numberOfBoomerangs(int[][] points) {
        if(points == null ){
            return 0;
        }

        int n = points.length;
        int result = 0;
        
        Map<String, Map<Integer, Integer>> circles = new HashMap<>(); //Map<center point, <radius, count of points>>
        for(int i = 0; i < n; i++){
            circles.put(points[i][0] + " " + points[i][1] , new HashMap<>());
        }

        Map<Integer, Integer> circle; 
        String center1;
        String center2;
        int r;
        for(int i = 0; i < n; i++){
            center1 = points[i][0] + " " + points[i][1];
            circle = circles.get(center1); 

            for(int j = i + 1; j < n; j++ ){
                r = distance(points[i], points[j]);
                circle.put( r, circle.getOrDefault(r, 0) + 1 );

                center2 = points[j][0] + " " + points[j][1];
                circles.get(center2).put( r, circles.get(center2).getOrDefault(r, 0) + 1 );
            }

            for(int count : circle.values() ){
                result += count * (count - 1);
            }

            circles.remove(center1);
        }

        return result;
    }
    
    

    private int distance(int[] p1, int[] p2){
//        int xDiff = p1[0] - p2[0];
//        int yDiff = p1[1] - p2[1];
//
//        return xDiff * xDiff + yDiff*yDiff;
        return (int)(Math.pow(p1[0] - p2[0], 2) + Math.pow(p1[1] - p2[1], 2));
    }
    
    public static void main(String[] args){
        NumberOfBoomerangs sv = new NumberOfBoomerangs();
                
        int[][][] inputs = {
            {{0,0},{1,0},{2,0}},
            {{0,0},{1,0},{1,1},{0,1}}
        };
        
        int[] expects = {
            2, 
            8
        };
        
        for(int i = 0; i < inputs.length; i++ ){
            Assert.assertEquals(expects[i], sv.numberOfBoomerangs_n(inputs[i]));
            Assert.assertEquals(expects[i], sv.numberOfBoomerangs(inputs[i]));
        }
    }
}
