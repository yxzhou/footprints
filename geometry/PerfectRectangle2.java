
package geometry;

import java.util.HashMap;
import java.util.Map;

/**
 * _https://www.lintcode.com/problem/1264
 * 
 * Thoughts:
 *   s0: find out the perfect rectangle, the four vertex appear once, and the other vertex appear twice or four times, 
 *   and the area is matching
 *   s1: if there is a perfect rectangle, all input rectangles can share the point only when it's in different position. 
 *   and only four vertex appear once, and the other vertex appear twice and four times. 
 * 
 */
public class PerfectRectangle2 {
    /**
     * @param rectangles: N axis-aligned rectangles
     * @return if they all together form an exact cover of a rectangular region
     */
    public boolean isRectangleCover(int[][] rectangles) {

        Map<String, Integer> positions = new HashMap<>(); //<Point, position>
        String point;
        int position; 
        for(int[] rect : rectangles ){            
            for(int i = 0; i < 4; i++){
                point = getPoint(rect[i / 2 * 2], rect[(i * 2 + 1) % 4]);
                position = positions.getOrDefault(point, 0);

                //all rectangles can not share the same vertex in the same position
                if((position & (1 << i)) > 0){
                    return false;
                }

                positions.put(point, position | (1 << i));
            }
        }

        //only four vertex appear once, and the other vertex appear twice and four times.
        int flag = 0;
        int count;
        for(int p :  positions.values()){
            count = count(p);

            if(count == 1){
                if(flag > 3){
                    return false;
                }

                flag++;
            }else if(count != 2 && count != 4){
                return false;
            }
        } 

        return true;
    }

    private String getPoint(int x, int y){
        return String.format("%d,%d", x, y);
    }

    private int count(int p){
        int count = 0;

        while(p > 0){
            count++;
            p &= (p - 1);
        }

        return count;
    }
}
