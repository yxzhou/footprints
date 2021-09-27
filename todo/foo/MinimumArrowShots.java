package todo.foo;

import java.util.Arrays;
import java.util.Comparator;

/**
 * _https://www.jiuzhang.com/solution/minimum-number-of-arrows-to-burst-balloons/
 *
 * There are a number of spherical balloons spread in two-dimensional space.
 * For each balloon, provided input is the start and end coordinates of the horizontal diameter.
 * Since it's horizontal, y-coordinates don't matter and hence the x-coordinates of start and end of the diameter suffice.
 * Start is always smaller than end. There will be at most 10^4 balloons.
 *
 * An arrow can be shot up exactly vertically from different points along the x-axis.
 * A balloon with xstart and xend bursts by an arrow shot at x if xstart <<  x << xend.
 * There is no limit to the number of arrows that can be shot. An arrow once shot keeps travelling up infinitely.
 * The problem is to find the minimum number of arrows that must be shot to burst all balloons.
 *
 *
 *
 */
public class MinimumArrowShots {
    public int minArrowShots(int[][] balloons){
        if(null == balloons || 0 == balloons.length || 0 == balloons[0].length){
            return 0;
        }

        Pair[] pairs = new Pair[balloons.length];
        for(int i = 0; i < balloons.length; i++){
            pairs[i] = new Pair(balloons[i][0], balloons[i][1]);
        }

        Arrays.sort(pairs);

        int count = 0;
        int end = Integer.MIN_VALUE;
        for(Pair balloon : pairs){
            if(balloon.x <= end){
                end = Math.min(end, balloon.y);
            }else{
                count++;

                end = balloon.y;
            }
        }

        return count + 1;
    }

    class Pair implements Comparable<Pair> {
        int x;
        int y;

        Pair(int x, int y){
            x = x;
            y = y;
        }

        @Override public int compareTo(Pair other){
            if(null == other){
                throw new IllegalArgumentException("It need a not null Pair object.");
            }

            if(this.x == other.x){
                return this.y - other.y;
            }else{
                return this.x - other.x;
            }
        }
    }

    public int minArrowShots_jiuzhang(int[][] balloons){
        if(null == balloons || 0 == balloons.length){
            return 0;
        }

        Arrays.sort(balloons, new Comparator<int[]>() {
            @Override public int compare(int[] o1, int[] o2) {
                return o1[1] - o2[1];
            }
        });

        int count = 1;
        int end = balloons[0][1];
        for(int i = 1; i < balloons.length; i++){
            if(balloons[i][0] > end){
                count++;
                end = balloons[i][1];
            }
        }

        return count;
    }

    public static void main(String[] args){
        int[][][] input = {
                {
                        {10,16},
                        {2,8},
                        {1,6},
                        {7,12}
                },
        };

        MinimumArrowShots sv = new MinimumArrowShots();

        for(int i=0; i < input.length; i++){
            System.out.print(String.format("%d, %d\n", sv.minArrowShots(input[i]), sv.minArrowShots_jiuzhang(input[i])));
        }
    }
}

