/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package geometry;

import java.util.PriorityQueue;

/**
 * _https://www.lintcode.com/problem/612
 *
 * Given some points and an origin in two-dimensional space,Find k points from points which are closest to origin
 * Euclidean.Return to the answer from small to large according to Euclidean distance. If two points have the same
 * Euclidean distance, they are sorted by x values. If the x value is the same, then we sort it by the y value.
 *
 * Example 1:
 * Input: points = [[4,6],[4,7],[4,4],[2,5],[1,1]], origin = [0, 0], k = 3 
 * Output: [[1,1],[2,5],[4,4]] 
 * 
 * Example 2: 
 * Input: points = [[0,0],[0,9]], origin = [3, 1], k = 1 
 * Output: [[0,0]] 
 * 
 * Challenge 
 *   O(nlogn) is OK, but can you think of a solution to O(nlogk)？
 *
 * Thoughts:
 *   The Euclidean distance of (x1, y1) and (x2, y2) is Math.sqrt( (x1 - x2)^2 + (y1 - y2)^2 )
 *   The closest points are the smallest distances, 
 *   Put all distances in Heap and get the k smallest distances, Time O(n * logn )
 *   Keep k distances in Heap,  Time O(n * logk )
 * 
 * 
 */
public class KClosestPoints {
    
    /**
     * Time O(n * logk )
     * 
     * @param points: a list of points
     * @param origin: a point
     * @param k: An integer
     * @return the k closest points
     */
    public Point[] kClosest(Point[] points, Point origin, int k) {
        if(points == null || points.length < k){
            return new Point[0];
        }

        PriorityQueue<Point> maxHeap = new PriorityQueue<>((p, g) -> {
                
                int diff = Long.compare(calDistance(g, origin) , calDistance(p, origin) );
                
                if(diff == 0){
                    return g.x == p.x ? Integer.compare(g.y, p.y) : Integer.compare(g.x, p.x);
                }

                return diff;
            }
        );

        for(Point p : points){
            maxHeap.add(p);

            if(maxHeap.size() > k){
                maxHeap.poll();
            }
        }

        Point[] result = new Point[k];
        for(int i = k - 1; i >= 0; i--){
            result[i] = maxHeap.poll();
        }

        return result;
    }

    private long calDistance(Point p, Point g){
        return (long)(Math.pow((p.x - g.x), 2) + Math.pow((p.y - g.y), 2));
    }
}
