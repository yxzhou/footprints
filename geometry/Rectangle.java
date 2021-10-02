/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package geometry;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * _https://www.lintcode.com/problem/820
 * 
 * Give a set, if you can find four points that make up a rectangle that is parallel to the coordinate axis and outputs "YES" or "NO".
 * 
 * Notes: 
 *   The number of points in the set is less than 2000, and the coordinate range is [-10000000,10000000].
 * 
 * Example 1:
 * Input: [[0,0],[0,1],[1,1],[1,0]]
 * Output: "YES"
 * Explanation: We can find four points that make up a rectangle which is parallel to the coordinate axis.
 * 
 * Example 2:
 * Input: [[0,0],[0,1],[1,1],[2,0]]
 * Output: "NO"
 * Explanation: We can not find four points that meet the conditions.
 * 
 */
public class Rectangle {
    
    public String rectangle(Point[] pointSet) {
        Map<Integer, List<Integer>> lines = new HashMap<>();
        Set<String> set = new HashSet<>();
        
        for(Point p : pointSet){
            set.add(p.x + " " + p.y);

            lines.putIfAbsent(p.y, new ArrayList<>());
            lines.get(p.y).add(p.x);
        }

        List<Integer> yList = new ArrayList<>(lines.keySet());
        Collections.sort(yList);

        int y2;
        int x1;
        int x2;
        List<Integer> xList;
        for(Integer y1 : yList){
            xList = lines.get(y1);
            //Collections.sort(xList);

            for(int i = 0, n = xList.size(); i < n; i++ ){
                for(int j = i + 1; j < n; j++){
                    x1 = xList.get(i);
                    x2 = xList.get(j);

                    if(x1 == x2){
                        continue;
                    }

                    y2 = y1 + Math.abs(x2 - x1);

                    if(set.contains(x1 + " " + y2) && set.contains(x2 + " " + y2)){
                        return "YES";
                    } 
                }
            }
        }

        return "NO";
    }
    
    public String rectangle_n(Point[] pointSet) {
        Map<Integer, List<Integer>> lines = new HashMap<>();
        Set<String> set = new HashSet<>();
        
        for(Point p : pointSet){
            set.add(p.x + " " + p.y);

            lines.putIfAbsent(p.y, new ArrayList<>());
            lines.get(p.y).add(p.x);
        }

        List<Integer> yList = new ArrayList<>(lines.keySet());
        Collections.sort(yList);

        int y2;
        int x1;
        int x2;
        List<Integer> xList;
        for(Integer y1 : yList){
            xList = lines.get(y1);
            Collections.sort(xList);

            for(int i = 0, n = xList.size(); i < n; i++ ){
                for(int j = i + 1; j < n; j++){
                    x1 = xList.get(i);
                    x2 = xList.get(j);

                    if(x1 == x2){
                        continue;
                    }

                    y2 = y1 + x2 - x1;

                    if(set.contains(x1 + " " + y2) && set.contains(x2 + " " + y2)){
                        return "YES";
                    } 
                }
            }
        }

        return "NO";
    }
    
    /**
     * Definition for a point.
     */
    class Point {
        int x;
        int y;
        Point() { x = 0; y = 0; }
        Point(int a, int b) { x = a; y = b; }
    }
 
    
}
