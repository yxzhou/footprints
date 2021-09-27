package dailyCoding2.square;

import java.util.Arrays;

import org.junit.Assert;
import org.junit.Test;

/**
 *
 * This problem was asked by Square.
 *
 * You are given a histogram consisting of rectangles of different heights. These heights are represented in an input list, such that [1, 3, 2, 5] corresponds to the following diagram:
 *
 *       x
 *       x
 *   x   x
 *   x x x
 * x x x x
 * Determine the area of the largest rectangle that can be formed only from the bars of the histogram. For the diagram above, for example, this would be six, representing the 2 x 3 area at the bottom right.
 *
 */

public class MaxRectangleArea {

    /**
     * Time complexity O( max(heights[i]) * heights.length )
     */
    public int maxRectangleArea(int[] heights){
        if(null == heights || 0 == heights.length){
            return 0;
        }

        int max = 0;
        int h = 1;
        int w;
        boolean flag = true;
        while(flag){
            w = 0;
            flag = false;

            for(int height : heights){
                if(height >= h){
                    w++;
                    flag = true;
                }else{
                    w = 0;
                }

                max = Math.max(max, w * h);
            }

            h++;
        }

        return max;
    }

    /**
     * define n as heights.length
     * Time complexity O( n*logn + n * n ),
     */
    public int maxRectangleArea_n(int[] heights) {
        if (null == heights || 0 == heights.length) {
            return 0;
        }

        int n = heights.length;

        int[] ordered = new int[n];
        System.arraycopy(heights, 0, ordered, 0, n);
        Arrays.sort(ordered);

        int max = 0;
        int w;
        for(int i = 0; i < n; i++){
            if(i > 0 && ordered[i - 1] == ordered[i]){
                continue;
            }

            w = 0;
            for(int height : heights){
                if(height >= ordered[i] ){
                    w++;
                }else{
                    w = 0;
                }

                max = Math.max(max, ordered[i] * w);
            }
        }

        return max;
    }

    @Test
    public void test(){
        Assert.assertTrue( 6 == maxRectangleArea(new int[]{1, 3, 2, 5}));

        Assert.assertTrue( 6 == maxRectangleArea_n(new int[]{1, 3, 2, 5}));
    }
}
