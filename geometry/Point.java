/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package geometry;

/**
 *
 * @author yuanxi
 */
public class Point {
    int x;
    int y;
    
    Point(){
        x = 0;
        y = 0;
    }

    Point(int a, int b) {
        x = a;
        y = b;
    }
    
    public static Point build(int[] points) {
        return new Point(points[0], points[1]);
    }
        
    public static Point[] build(int[][] points) {
        if (null == points || 0 == points.length) {
            return null;
        }

        Point[] ret = new Point[points.length];
        for (int i = 0; i < points.length; i++) {
            ret[i] = new Point(points[i][0], points[i][1]);
        }
        return ret;
    }
    
    public static String toString(Point[] points){
        StringBuilder sb = new StringBuilder();
        
        for(Point p : points){
            sb.append("[").append(p.x).append(", ").append(p.y).append("]");
        }
        
        return sb.toString();
    }
    
    @Override
    public int hashCode(){
        return x ^ y;
    }
    
    @Override
    public boolean equals(Object obj){
        if(obj instanceof Point){
            Point other = (Point) obj;
            
            return this.x == other.x && this.y == other.y;
        }
        
        return false;
    }
    
}
