
package geometry;

import java.util.HashMap;
import java.util.Map;

/**
 * _https://www.lintcode.com/problem/1264
 * 
 * Given N axis-aligned rectangles where N > 0, determine if they all together form an exact cover of a rectangular region.
 *
 * Each rectangle is represented as a bottom-left point and a top-right point. For example, a unit square is represented
 * as [1,1,2,2]. (coordinate of bottom-left point is (1, 1) and top-right point is (2, 2))

    Example 1:
    Input rectangles = [
      [1,1,3,3],
      [3,1,4,2],
      [3,2,4,4],
      [1,3,2,4],
      [2,3,3,4]
    ]
    Output：true
    Explanation：All 5 rectangles together form an exact cover of a rectangular region.

    * Example 2:
    Input rectangles = [
      [1,1,2,3],
      [1,3,2,4],
      [3,1,4,2],
      [3,2,4,4]
    ]
    Output：false
    Explanation：Because there is a gap between the two rectangular regions.

    * Example 3:
    Input rectangles = [
      [1,1,3,3],
      [3,1,4,2],
      [1,3,2,4],
      [3,2,4,4]
    ]
    Output：false
    Explanation：Because there is a gap in the top center.

    * Example 4:
    Input rectangles = [
      [1,1,3,3],
      [3,1,4,2],
      [1,3,2,4],
      [2,2,4,4]
    ]
    Output：false
    Explanation：Because two of the rectangles overlap with each other.
 *
 * Thoughts:
 *   s0: find out the perfect rectangle, the four vertex appear once, and the other vertex appear twice or four times, 
 *   and the area is matching
 *   s1: if there is a perfect rectangle, all input rectangles can share the point only when it's in different position. 
 *   and only four vertex appear once, and the other vertex appear twice and four times. 
 * 
 */
public class PerfectRectangle {
    /**
     * @param rectangles: N axis-aligned rectangles
     * @return if they all together form an exact cover of a rectangular region
     */
    public boolean isRectangleCover(int[][] rectangles) {
        //init the possible rectangular region
        // a bottom-left point and a top-right point
        //{minX, minY, maxX, maxY}
        int[] whole = {Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MIN_VALUE, Integer.MIN_VALUE}; 
        long area = 0;

        Map<Point, Integer> counts = new HashMap<>(); //<Point, count>
        for(int[] rect : rectangles ){
            area += (long)(rect[2] - rect[0]) * (rect[3] - rect[1]);

            whole[0] = Math.min(whole[0], rect[0]);
            whole[1] = Math.min(whole[1], rect[1]);
            whole[2] = Math.max(whole[2], rect[2]);
            whole[3] = Math.max(whole[3], rect[3]);
            
            for(int i = 0; i < 4; i++){
                Point p = new Point(rect[i / 2 * 2], rect[(i * 2 + 1) % 4]);

                counts.put(p, counts.getOrDefault(p, 0) + 1);
            }
        }

        //the four points of the possible rectangular region has to appear only once 
        for(int i = 0; i < 4; i++){
            Point p = new Point(whole[i / 2 * 2], whole[(i * 2 + 1) % 4]);

            if(counts.getOrDefault(p, 0) != 1){
                return false;
            }
            counts.remove(p);
        }

        //the area has to be same
        if(area != (long)(whole[2] - whole[0])*(whole[3] - whole[1])){
            return false;
        }

        //the other points has to appear twice or fours
        for(int c :  counts.values()){
            if(c != 2 && c != 4){
                return false;
            }
        } 

        return true;
    }

    class Point{
        int x;
        int y;

        Point(int x, int y){
            this.x = x;
            this.y = y;
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
}
