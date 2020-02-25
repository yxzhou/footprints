package fgafa.leetcode.facebook;

import org.junit.Assert;
import org.junit.Test;

import java.util.*;

/**
 * We are given a list of (axis-aligned) rectangles.  Each rectangle[i] = [x1, y1, x2, y2] , where (x1, y1) are the coordinates of the bottom-left corner, and (x2, y2) are the coordinates of the top-right corner of the ith rectangle.

 Find the total area covered by all rectangles in the plane.  Since the answer may be too large, return it modulo 10^9 + 7.

 Example 1:
 Input: [[0,0,2,2],[1,0,2,3],[1,0,3,1]]
 Output: 6
 Explanation: As illustrated in the picture.

 Example 2:
 Input: [[0,0,1000000000,1000000000]]
 Output: 49
 Explanation: The answer is 10^18 modulo (10^9 + 7), which is (10^9)^2 = (-7)^2 = 49.

 Note:
 1 <= rectangles.length <= 200
 rectanges[i].length = 4
 0 <= rectangles[i][j] <= 10^9
 The total area covered by all rectangles will never exceed 2^63 - 1 and thus will fit in a 64-bit signed integer.
 *
 *
 */

public class RectangleAreaII {

    /**
     *  line sweep,  add coverage line by line
     *
     *  define n = rectangles.length, it's [1, 200];  m = rectangles[i][j], it's [0, 10^9]
     *   O( n * logn + m * n * logn )
     */
    public int rectangleArea(int[][] rectangles) {

        Arrays.sort(rectangles, new Comparator<int[]>(){
            @Override
            public int compare(int[] r1, int[] r2){
                if(r1[0] != r2[0]){
                    return Integer.compare(r1[0], r2[0]);
                }else if(r1[1] != r2[1]){
                    return Integer.compare(r1[1], r2[1]);
                }else if(r1[2] != r2[2]){
                    return Integer.compare(r1[2], r2[2]);
                }else if(r1[3] != r2[3]){
                    return Integer.compare(r1[3], r2[3]);
                }

                return 0;
            }
        });

        int size = rectangles.length;
        int max = 1000000000;
        int base = 1000000000 + 7;

        long result = 0;
        for(int line = 0, rId = 0; line <= max; line++){
            if(rId >= size){
                break;
            }

            if(line < rectangles[rId][0]){
                line = rectangles[rId][0];
            }

            List<int[]> intervals = new ArrayList<>();
            for(int r = rId; r < size; r++){
                if(line >= rectangles[r][2]){
                    rId = r + 1;
                    continue;
                }else if(line < rectangles[r][0]){
                    break;
                }

                intervals.add(new int[]{rectangles[r][1], rectangles[r][3]});
            }

            if(intervals.isEmpty()){
                continue;
            }

            Collections.sort(intervals, new Comparator<int[]>() {
                @Override
                public int compare(int[] r1, int[] r2) {
                    if(r1[0] != r2[0]){
                        return Integer.compare(r1[0], r2[0]);
                    }else if(r1[1] != r2[1]){
                        return Integer.compare(r1[1], r2[1]);
                    }

                    return 0;
                }
            });

            int[] curr = intervals.get(0);
            int[] next;
            for(int i = 1; i < intervals.size(); i++){
                next = intervals.get(i);
                if(next[0] > curr[1]){
                    result += curr[1] - curr[0];
                    curr = next;
                }else{
                    curr[1] = next[1];
                }
            }
            result += curr[1] - curr[0];
            result %= base;
        }

        return (int)result;
    }

    /**
     *  line sweep,  multiple instead of addition
     *
     *  define n = rectangles.length, it's [1, 200];  m = rectangles[i][j], it's [0, 10^9]
     *   O( n * n * logn )
     */
    public int rectangleArea_n(int[][] rectangles) {
        int size = rectangles.length;
        int base = 1000000000 + 7;
        int LEFT = 1;
        int RIGHT = 0;

        int i = 0;
        int[][] events = new int[size << 1][4];
        for(int[] r : rectangles){
            events[i++] = new int[]{r[0], LEFT, r[1], r[3]}; // rectangle's left side line
            events[i++] = new int[]{r[2], RIGHT, r[1], r[3]}; // rectangle's right side line
        }

        Arrays.sort(events, (e1, e2) -> {
            if(e1[0] != e2[0]){
                return Integer.compare(e1[0], e2[0]);
            }else {
                return Integer.compare(e1[1], e2[1]);
            }
        });

        long result = 0;
        List<int[]> intervals = new ArrayList<>();
        int currX = events[0][0];
        long diffY = 0;
        int currY = -1;
        for(int[] event : events){
            currY = -1;
            diffY = 0;
            for(int[] interval : intervals){
                currY = Math.max(currY, interval[0]);
                diffY += Math.max(interval[1] - currY, 0);
                currY = Math.max(currY, interval[1]);
            }

            result += (event[0] - currX) * diffY;
            result %= base;

            if(event[1] == LEFT) {
                intervals.add(new int[]{event[2], event[3]});

                Collections.sort(intervals, Comparator.comparingInt(a -> a[0]));
            }else{
                for(int j = 0; j < intervals.size(); j++){
                    if(intervals.get(j)[0] == event[2] && intervals.get(j)[1] == event[3]){
                        intervals.remove(j);
                        break;
                    }
                }
            }

            currX = event[0];
        }

        return (int)result;
    }

    @Test public void test(){
        int mod = 1_000_000_007;

        System.out.println(mod);

        int x = 2;
        int y = 3;
        int[] next = {x, y};


        //Assert.assertEquals(6, rectangleArea(new int[][]{{0,0,2,2}, {1,0,2,3}, {1,0,3,1}}));
        //Assert.assertEquals(49, rectangleArea(new int[][]{{0,0,1000000000,1000000000}}));

        Assert.assertEquals(6, rectangleArea_n(new int[][]{{0,0,2,2}, {1,0,2,3}, {1,0,3,1}}));
        Assert.assertEquals(49, rectangleArea_n(new int[][]{{0,0,1000000000,1000000000}}));
    }
}
