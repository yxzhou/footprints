package geometry;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;

/**
 *
 * This problem was asked by Google.
 *
 * Given a set of points (x, y) on a 2D cartesian plane, find the two closestPair points.
 * For example, given the points [(1, 1), (-1, -1), (3, 4), (6, 1), (-1, -6), (-4, -3)], return [(-1, -1), (1, 1)]
 *
 * Thoughts:
 * 1 Check every pair.
 *   Let's say it's n points, total it's n*(n-1) pair, calculate the pair's distance sqrt((x1 - x2)^2 + (y1 - y2)^2), and get the minimal,
 *   Time complexity O(n*n)
 * 2 Divider and Conquer
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

public class ClosestPair2D {
    class Point{
        private int id;
        private int x;
        private int y;

        Point(int id, int x, int y){
            this.id = id;
            this.x = x;
            this.y = y;
        }

        public int getId(){
            return id;
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }
    }

    /**
     *
     *
     * @return
     */
    public double closestPair(Point[] points){
        //sort by x
        int[] orderedByX = Arrays.stream(points).sorted(Comparator.comparing(Point::getX)).map(Point::getId).mapToInt(Integer::intValue).toArray();

        //sort by y
        int[] orderedByY = Arrays.stream(points).sorted(Comparator.comparing(Point::getY)).map(Point::getId).mapToInt(Integer::intValue).toArray();

        return closestPair(points, orderedByX, 0, points.length - 1, orderedByY);
    }

    //divide and conquer
    private double closestPair(final Point[] points, int[] orderedByX, int from, int to, int[] orderedByY){
        if(from >= to){
            return Integer.MAX_VALUE;
        }else if(from + 1 == to){
            return getDistance(points[orderedByX[from]], points[orderedByX[to]]);
        }

        int mid = from + (to - from) / 2;
        double dl = closestPair(points, orderedByX, from, mid, orderedByY);
        double dr = closestPair(points, orderedByX, mid + 1, to, orderedByY);

        double d = Math.min(dl, dr);
        d = closestPairInStrip(points, orderedByX, mid, d, orderedByY);

        return d;
    }

    private double closestPairInStrip(Point[] points, int[] orderedByX, int mid, double distance, int[] orderedByY){
        //get all points in the strip
        Set<Integer> strip = new HashSet<>();

        double basisLeft = points[orderedByX[mid]].x - distance;
        for(int l = mid - 1; l >= 0 && basisLeft < points[orderedByX[l]].x; l--){
            strip.add(l);
        }

        double basisRight = points[orderedByX[mid]].x + distance;
        for(int r = mid + 1; r < orderedByX.length && points[orderedByX[r]].x < basisRight; r++){
            strip.add(r);
        }

        //order the points in the strip by y
        int[] stripOrderedByY = Arrays.stream(orderedByY).filter(strip::contains).toArray();

        double min = distance;
        //note: time complexity O(n), not O(n^2)
        for(int i = 0; i < stripOrderedByY.length; i++){
            for(int j = i + 1; j < stripOrderedByY.length && points[stripOrderedByY[j]].y - points[stripOrderedByY[i]].y < min; j++){
                min = Math.min(min, getDistance(points[stripOrderedByY[j]], points[stripOrderedByY[i]]));
            }
        }

        return min;
    }

    private double getDistance(Point p1, Point p2 ){
        int diffx = p1.x - p2.x;
        int diffy = p1.y - p2.y;
        return Math.sqrt( diffx * diffx + diffy * diffy );
    }

    @Test
    public void test() {
        int[][] points = {{1, 1}, {-1, -1}, {3, 4}, {6, 1}, {-1, -6}, {-4, -3}};

        Assert.assertEquals(Math.sqrt(8), closestPair(build(points)), 0.000001);

    }

    private Point[] build(int[][] points){
        Point[] result = new Point[points.length];

        for(int i = 0; i < points.length; i++){
            result[i] = new Point(i, points[i][0], points[i][1]);
        }

        return result;
    }
}
