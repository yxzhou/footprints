/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package geometry;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.junit.Assert;
import util.Misc;

/**
 * _https://www.lintcode.com/problem/966
 * 
 */
public class ClosestPair2DII {
    
    class Point{
        double x;
        double y;

        Point(double x, double y){
            this.x = x;
            this.y = y;
        }
    }

    /**
     * @param x: the list of coordinate x
     * @param y: the list of coordinate x
     * @return: find the closest pair of points and return the distance
     */
    boolean xFirst;
    public double getClosestDistance(double[] x, double[] y) {
        
        int n = x.length;
        Point[] points = new Point[n];
        Set<Double> xs = new HashSet<>();
        Set<Double> ys = new HashSet<>();

        for(int i = 0; i < n; i++){
            points[i] = new Point(x[i], y[i]);

            xs.add(x[i]);
            ys.add(y[i]);
        }

        xFirst = ( xs.size() >= ys.size() );

        if(xFirst){
            Arrays.sort(points, (a, b) -> Double.compare(a.x, b.x) );
        }else{
            Arrays.sort(points, (a, b) -> Double.compare(a.y, b.y) );
        }
        
        return closestPair(points, 0, n - 1);
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
        d = closestPairInStrip(points, from, to, mid, d);

        return d;
    }

    private double closestPairInStrip(Point[] points, int from, int to, int mid, double distance) {
        //get all points in the strip
        Set<Point> strip = new HashSet<>();

        double diff;
        for(int i = from; i <= to; i++){
            if(xFirst){
                diff = points[i].x - points[mid].x;
            }else { 
                diff = points[i].y - points[mid].y;
            }

            if(Math.abs(diff) < distance){
                strip.add(points[i]);
            }
        }

        //order the points in the strip by y
        List<Point> pointsInStrip = new ArrayList<>(strip);
        Collections.sort(pointsInStrip, (a, b) -> xFirst? Double.compare(a.y, b.y) : Double.compare(a.x, b.x) );

        double min = distance;
        //note: time complexity O(n), not O(n^2)
        for (int i = 0, n = pointsInStrip.size(); i < n; i++) {
            for (int j = i + 1; j < n; j++) {

                if(xFirst){
                    diff = pointsInStrip.get(j).y - pointsInStrip.get(i).y;
                }else { 
                    diff = pointsInStrip.get(j).x - pointsInStrip.get(i).x;
                }

                if(diff < min){
                    min = Math.min(min, getDistance(pointsInStrip.get(j), pointsInStrip.get(i)));
                }
            }
        }

        return min;
    }

    private double getDistance(Point p1, Point p2) {
        double diffx = p1.x - p2.x;
        double diffy = p1.y - p2.y;
        return Math.sqrt(diffx * diffx + diffy * diffy);
    }

   public static void main(String[] args) {
        double[][][] inputs = {
            //{
            //  points[]
            //  {{ closest_distance * closest_distance }}
            //},
            {
                {1.0,-1.0,3.0,6.0,-1.0,-4.0},
                {1.0,-1.0,4.0,1.0,-6.0,-3.0},
                {2.82} // {1, 1} and {-1, -1}
            },
            {
                {1.0,2.0,3.0,4.0,5.0},
                {2.0,3.0,1.0,2.0,2.0},
                {1.00} //{4.0,2.0},{5.0,2.0}
            },
            {
                {1.0,2.0,3.0,4.0,5.0},
                {2.0,3.0,1.0,2.0,7.0},
                {1.41} //{1.0,2.0},{2.0,3.0}
            },
            {
                {-19.6,9.7,-17.5,-20.0,-7.3,11.1,8.9,18.1,18.2,-3.0},
                {-0.7,-18.2,-3.6,4.7,15.8,14.8,-7.8,17.7,-5.9,4.2},
                {3.58}
            }
        };
        
        ClosestPair2DII sv = new ClosestPair2DII();
        
        for(double[][] input : inputs){
            System.out.println(String.format("\n[%s]\n[%s]", Misc.array2String(input[0]), Misc.array2String(input[1]) ));
            
            Assert.assertEquals(input[2][0], sv.getClosestDistance(input[0], input[1]), 0.01);
        }
    }
}
