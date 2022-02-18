package geometry;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Assert;
import util.Misc;


/**
 * 
 * This problem was asked by Google.
 *
 * Given a set of points (x, y) on a 2D cartesian plane, find the two closestPair points.
 * For example, given the points [(1, 1), (-1, -1), (3, 4), (6, 1), (-1, -6), (-4, -3)], return [(-1, -1), (1, 1)]
 *
 * Thoughts:
 * m1) Check every pair.
 *   Let's say it's n points, total it's n*(n-1) pair, calculate the pair's distance sqrt((x1 - x2)^2 + (y1 - y2)^2), and get the minimal,
 *   Time complexity O(n*n)
 * 
 * m2) Divider and Conquer
 *   refer to https://www.geeksforgeeks.org/closest-pair-of-points-using-divide-and-conquer-algorithm/
 *   Time complexity O(n * logn ).
 *      T(n) = T(n/2) + T(n/2) + O(n)  => T(n) = O(n * logn)
 *
 *
 * Further:
 *   If it's 3 dimension,  same with Divider and Conquer
 *     T(n, d) = T(n/2) + T(n/2) + T(n, d - 1)
 *     T(n, 1) = O(1)  ??
 *     T(n, 2) = O(n * logn)
 *     T(n, 3) = O(n * logn * logn)
 *
 *  => T(n, d) = n * (logn)^(d - 1)
 */

public class ClosestPair2D {    /**
     *
     *
     * @return
     */
    public double closestPair(Point[] points) {
        //sort by x
        Arrays.sort(points, (a, b) -> Integer.compare(a.x, b.x) );

        return closestPair(points, 0, points.length - 1);
    }

    //divide and conquer
    private double closestPair(final Point[] points, int from, int to) {
        if (from >= to) {
            return Integer.MAX_VALUE;
        } else if (from + 1 == to) {
            return getDistance(points[from], points[to]);
        }

        int mid = from + (to - from) / 2;
        double dl = closestPair(points, from, mid );
        double dr = closestPair(points, mid + 1, to);

        double d = Math.min(dl, dr);
        d = closestPairInStrip(points, mid, d);

        return d;
    }

    private double closestPairInStrip(Point[] points, int mid, double distance) {
        //get all points in the strip
        Set<Point> strip = new HashSet<>();

        double basisLeft = points[mid].x - distance;
        for (int l = mid - 1; l >= 0 && basisLeft < points[l].x; l--) {
            strip.add(points[l]);
        }

        double basisRight = points[mid].x + distance;
        for (int r = mid + 1; r < points.length && points[r].x < basisRight; r++) {
            strip.add(points[r]);
        }

        //order the points in the strip by y
        List<Point> orderedByY = new ArrayList<>(strip);

        double min = distance;
        //note: time complexity O(n), not O(n^2)
        for (int i = 0, n = orderedByY.size(); i < n; i++) {
            for (int j = i + 1; j < n && orderedByY.get(j).y - orderedByY.get(i).y < min; j++) {
                min = Math.min(min, getDistance(orderedByY.get(j), orderedByY.get(i)));
            }
        }

        return min;
    }

    private double getDistance(Point p1, Point p2) {
        int diffx = p1.x - p2.x;
        int diffy = p1.y - p2.y;
        return Math.sqrt(diffx * diffx + diffy * diffy);
    }

    public static void main(String[] args) {
        int[][][][] inputs = {
            //{
            //  points[]
            //  {{ closest_distance * closest_distance }}
            //},
            {
                {{1, 1}, {-1, -1}, {3, 4}, {6, 1}, {-1, -6}, {-4, -3}},
                {{8}} // {1, 1} and {-1, -1}
            },
            {
                {{1,2},{2,3},{3,1},{4,2},{5,2}},
                {{1}} //{4,2},{5,2}
            },
            {
                {{1,2},{2,3},{3,1},{4,2},{5,7}},
                {{2}} //{1,2},{2,3}
            }    
        };
        
        ClosestPair2D sv = new ClosestPair2D();
        
        for(int[][][] input : inputs){
            System.out.println(String.format("\n[%s]", Misc.array2String(input[0]) ));
            
            Assert.assertEquals(Math.sqrt(input[1][0][0]), sv.closestPair(Point.build(input[0])), 0.000001);
        }
    }

}
