/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package geometry;

import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Random;
import org.junit.Assert;
import util.Misc;

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
 *   m1) Put all distances in Heap and get the k smallest distances, Time O(n * logn )
 *   m2) Keep k distances in Heap,  Time O(n * logk )
 *   m3) quick select, O(n + klogk)
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
        if(points == null || points.length < k || k == 0 ){
            return new Point[0];
        }

        PriorityQueue<Point> maxHeap = new PriorityQueue<>(Comparator
                .comparing( (Point p) -> calDistance(p, origin))
                .thenComparingInt(p -> p.x)
                .thenComparingInt(p -> p.y)
                .reversed()
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
    
    /**
     *  O(n + klogk)
     * 
     * @param points
     * @param origin
     * @param k
     * @return 
     */
    public Point[] kClosest_quickSelect(Point[] points, Point origin, int k) {
        if(points == null || points.length < k || k == 0 ){
            return new Point[0];
        }
     
        Comparator<Point> cmp = Comparator.comparing((Point p) -> calDistance(p, origin))
                .thenComparingInt(p -> p.x)
                .thenComparingInt(p -> p.y);
        
        //Quick Select
        int left = 0;
        int right = points.length - 1;
        
        int pivot; 
        while (left < right) {
            pivot = quickSelect(points, left, right, cmp);

            if (k == pivot) {
                break;
            } else if (k < pivot) {
                right = pivot - 1;
            } else {
                left = pivot + 1;
            }
        }
 
        Arrays.sort(points, 0, k, cmp);
        return Arrays.copyOf(points, k);
    }
    
    private int quickSelect(Point[] points, int start, int end, Comparator<Point> cmp) {
        int pivot = start + new Random().nextInt(end - start + 1);

        swap(points, pivot, start);
        pivot = start;
        start++;
        while (start < end) {
            if (cmp.compare(points[start], points[pivot]) < 0) {
                start++;
            } else {
                swap(points, start, end);
                end--;
            }
        }

        if (cmp.compare(points[start], points[pivot]) > 0) {
            start--;
        }
        swap(points, pivot, start);
        return start;
    }
    
    private void swap(Point[] points, int i, int j) {
        Point tmp = points[i];
        points[i] = points[j];
        points[j] = tmp;
    }
    
    public static void main(String[] args){
        int[][][][] inputs = {
            {
                {{4, 6}, {4, 7}, {4, 4}, {2, 5}, {1, 1}},
                {{0, 0}, {3}},
                {{1, 1}, {2, 5}, {4, 4}}
            },
            {
                {{0, 0}, {0, 9}},
                {{3, 1}, {1}},
                {{0, 0}}
            },
            {
                {{0, -12}, {5, 2}, {2, 5}, {0, -5}, {1, 5}, {2, -2}, {5, -4}, {3, 4}, {-2, 4}, {-1, 4}, {0, -5}, {0, -8}, {-2, -1}, {0, -11}, {0, -9}},
                {{-10, 9}, {3}},
                {{-2, 4}, {-1, 4}, {1, 5}}
            }
        };
        
        KClosestPoints sv = new KClosestPoints();
        
        for(int[][][] input : inputs){
            System.out.println(String.format("\npoints: [%s], origin = [%s], k = %d", Misc.array2String(input[0], true), Misc.array2String(input[1][0]), input[1][1][0]  ));
            
            Assert.assertEquals(Misc.array2String(input[2], true), Point.toString(sv.kClosest(Point.build(input[0]), Point.build(input[1][0]), input[1][1][0])));
            
            Assert.assertEquals(Misc.array2String(input[2], true), Point.toString(sv.kClosest_quickSelect(Point.build(input[0]), Point.build(input[1][0]), input[1][1][0])));  
        }
    }
}
