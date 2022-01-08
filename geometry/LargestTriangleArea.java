/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package geometry;

/**
 * _https://www.lintcode.com/problem/1005
 * 
 * You have a list of points in the plane. Return the area of the largest triangle that can be formed by any 3 of the
 * points.
 * 
 * Notes:
 *   3 <= points.length <= 50.
 *   Points do not repeat.
 *   -50 <= points[i][j] <= 50.
 *   The result error is within 10 ^(-6) and can be considered correct.
 * 
 * Example 1
 * Input: points = [[0,0],[0,1],[1,0],[0,2],[2,0]]
 * Output: 2
 * Explanation: The five points are show in the figure below. The red triangle is the largest.
 * 
 */
public class LargestTriangleArea {
    /**
     * @param points: List[List[int]]
     * @return: return a double
     */
    public double largestTriangleArea(int[][] points) {
        int max = 0;

        int n = points.length;
        for(int i = 0; i < n; i++){
            for(int j = i + 1; j < n; j++ ){
                for(int k = j + 1; k < n; k++){
                    max = Math.max(max, area(points[i], points[j], points[k]));
                }
            }
        }

        return (double)max / 2;
    }

    /**
     * 
     * 
     * @return triangle's area * 2 
     */
    private int area(int[] p1, int[] p2, int[] p3){
        return Math.abs( p1[0] * (p2[1] - p3[1]) + p2[0] * (p3[1] - p1[1]) + p3[0] * (p1[1] - p2[1]) );
    }
}
