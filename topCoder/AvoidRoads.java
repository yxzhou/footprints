package fgafa.topCoder;

import java.util.HashSet;
import java.util.Set;


/**
 *
 * Problem Statement ( http://www.topcoder.com/contest/problem/AvoidRoads/ )
 *
 *  In the city, roads are arranged in a grid pattern. Each point on the grid represents a corner where two blocks meet.
 *  The points are connected by line segments which represent the various street blocks. Using the cartesian coordinate
 *  system, we can assign a pair of integers to each corner as shown below.
 *
 *  http://www.topcoder.com/contest/problem/AvoidRoads/AvoidPic1.GIF
 *
 *  You are standing at the corner with coordinates 0,0. Your destination is at corner width,height. You will return the
 *  number of distinct paths that lead to your destination. Each path must use exactly width+height blocks. In addition,
 *  the city has declared certain street blocks untraversable. These blocks may not be a part of any path. You will be given
 *  a String[] bad describing which blocks are bad. If (quotes for clarity) "a b c d" is an element of bad, it means the
 *  block from corner a,b to corner c,d is untraversable. For example, let's say
 *
 *  width  = 6
 *  length = 6
 *  bad = {"0 0 0 1","6 6 5 6"}
 *
 *  The picture below shows the grid, with untraversable blocks darkened in black. A sample path has been highlighted in red.
 *  http://www.topcoder.com/contest/problem/AvoidRoads/AvoidPic2.GIF
 *
 *
 * Constraints
 *   -	width will be between 1 and 100 inclusive.
 *   -	height will be between 1 and 100 inclusive.
 *   -	bad will contain between 0 and 50 elements inclusive.
 *   -	Each element of bad will contain between 7 and 14 characters inclusive.
 *   -	Each element of the bad will be in the format "a b c d",
 *        where a,b,c,d are integers with no extra leading zeros,
 *              a and c are between 0 and width inclusive,
 *              b and d are between 0 and height inclusive,
 *              and a,b is one block away from c,d.
 *   -	The return value will be between 0 and 2^63-1 inclusive.
 *
 */

public class AvoidRoads {

    public long numWays(int width, int height, String[] bad){
        if(null == bad || width < 1 || height < 1){
            return 0;
        }

        Set<String> blocks = new HashSet<>(bad.length);
        //format block always by direction from left to right or from down to up.
        for(String block : bad){

           int[] blockCodes = new int[4];
           int i = 0;
           for(String blockCode: block.split(" ")){
               blockCodes[i++] = Integer.parseInt(blockCode);
           }

           if(blockCodes[0] < blockCodes[2] || blockCodes[1] < blockCodes[3]){
               blocks.add(block);
           }else{
               blocks.add(block(blockCodes[2], blockCodes[3], blockCodes[0], blockCodes[1]));
           }
        }

        long[] numWays = new long[width + 1];
        numWays[0] = 1;
        for(int w = 1; w <= width; w++) {
            numWays[w] = (blocks.contains(block(w -1, 0, w, 0))? 0 : numWays[w - 1]);
        }

        for(int h = 1; h <= height; h++){
            numWays[0] = (blocks.contains(block(0, h - 1, 0, h))? 0 : numWays[0]);

            for(int w = 1; w <= width; w++) {
                numWays[w] = (blocks.contains(block(w -1, h, w, h)) ? 0 : numWays[w - 1]) + (blocks.contains(block(w, h - 1, w, h)) ? 0 : numWays[w]);
            }
        }

        return numWays[width];
    }

    private String block(int a, int b, int c, int d){
        return new StringBuilder().append(a).append(" ").append(b).append(" ").append(c).append(" ").append(d).toString();
    }

    public static void main(String[] args){
        int[][] cases = {
                {6,  6},
                {1,  1},
                {35, 31},
                {2,  2}
        };

        String[][] blocks = {
                {"0 0 0 1","6 6 5 6"},
                {},
                {},
                {"0 0 1 0", "1 2 2 2", "1 1 2 1"}
        };

        long[] expects = {
                252,
                2,
                6406484391866534976l,
                0
        };

        AvoidRoads sv = new AvoidRoads();

        for(int i = 0; i < expects.length; i++){
            long result = sv.numWays(cases[i][0], cases[i][1], blocks[i]);

            System.out.println(String.format("%d: %d %b", i, result, result == expects[i]));
        }
    }
}
