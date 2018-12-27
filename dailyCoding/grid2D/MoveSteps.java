package fgafa.dailyCoding.grid2D;

/**
 *
 * You are in an infinite 2D grid where you can move in any of the 8 directions:

 (x,y) to
 (x+1, y),
 (x - 1, y),
 (x, y+1),
 (x, y-1),
 (x-1, y-1),
 (x+1,y+1),
 (x-1,y+1),
 (x+1,y-1)
 You are given a sequence of points and the order in which you need to cover the points. Give the minimum number of steps in which you can achieve it. You start from the first point.

 Example:

 Input: [(0, 0), (1, 1), (1, 2)]
 Output: 2
 It takes 1 step to move from (0, 0) to (1, 1). It takes one more step to move from (1, 1) to (1, 2).
 *
 *  Tips: google
 *
 */

public class MoveSteps {

    public int minimumSteps(int[][] destinations){
        if(null == destinations || destinations.length < 2){
            return 0;
        }

        int result = 0;
        for(int i = 1; i < destinations.length; i++ ){
            int diffx = Math.abs(destinations[i][0] - destinations[i - 1][0]);
            int diffy = Math.abs(destinations[i][1] - destinations[i - 1][1]);

            if(diffx < diffy){
                result += diffy;
            }else{
                result += diffx;
            }
        }

        return result;
    }

}
