package geometry;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import org.junit.Assert;

import util.Misc;

/**
 * _https://www.lintcode.com/problem/186
 * 
 * Given n points on a 2D plane, find the maximum number of points that lie on the same straight line.
 *   point.x and point.y ranges from -1000 to 1000 
 * 
 * Example 1:
 * Input:(1,2),(3,6),(0,0),(1,3). Output:3 
 * 
 * Example 2:
 * Input:(1,2),(2,3),(3,2). Output:2
 * 
 */

public class MaxPointsOnALine {
    /**
     * Given n points on a 2D plane, find the maximum number of points that lie on the same straight line.
     * 
     * Time O(n^2) Space O(n)
     * 
     * @param points: an array of point
     * @return An integer
     */
    public int maxPoints(Point[] points) {
        if (points == null) {
            return 0;
        }

        int n = points.length;

        int max = 1;

        Map<Double, Integer> map;
        int diffX;
        int diffY;
        Double k;
        int duplicate;  //
        for (int i = 0; i < n; i++) {
            map = new HashMap<>();
            duplicate = 1; // default it's itself

            for (int j = i + 1; j < n; j++) {
                diffX = points[i].x - points[j].x;
                diffY = points[i].y - points[j].y;

                if (diffX == 0 && diffY == 0) {
                    duplicate++;
                } else {
                    k = (diffY == 0 ? null : (double) diffX / diffY); // HashMap permits null as key
                    map.put(k, map.getOrDefault(k, 0) + 1);
                }
            }

            max = Math.max(max, duplicate);
            for (int count : map.values()) {
                max = Math.max(max, count + duplicate);
            }
        }

        return max;
    }

    public static void main(String[] args) {
        MaxPointsOnALine sv = new MaxPointsOnALine();

        int[][][] inputs = {
            null, //0
            {{1, 1}}, // 1
            {{1, 1}, {1, 2}}, // 2
            {{1, 1}, {2, 1}}, // 2
            {{1, 1}, {1, 1}}, // 2
            {{1, 1}, {1, 2}, {2, 1}, {1, 1}, {2, 2}, {3, 3}}, // 4
            {{1, 1}, {1, 1}, {1, 1}}, // 3
            {{2, 3}, {3, 3}, {-5, 3}}, // 3
            {{0, 0}, {4, 5}, {1, 1}, {3, 6}, {2, 2}} // 3
        };
        
        int[] expects = {
            0,
            1,
            2,
            2,
            2,
            4,
            3,
            3,
            3
        };

        for (int i = 0; i < inputs.length; i++ ) {
            System.out.println("\n Input:" + Misc.array2String(inputs[i]));

            Point[] points = Point.build(inputs[i]);
            Assert.assertEquals(expects[i], sv.maxPoints(points));

        }

    }


}
