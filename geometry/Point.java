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

    Point(int a, int b) {
        x = a;
        y = b;
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
}
