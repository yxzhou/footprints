package geometry.distance;

import java.util.Stack;

/**
 * 
 * Given a grid with 'o' and 'x'. Find minimum steps from top-left to bottom-right without touching 'x'.
 * 
 * Problem #1,  move right or down
 *      a) You can only move right or move down. 
 * 
 * Problem #2,  move 4 directions, up or down or right or left
 *         b) You can move in all 4 directions. (BFS)
 * 
 */

public class DistanceInMetrix {

    /**
     *  BFS or DP
     * @param metrix,  only includes 0 and 1, 1 means 'x', 
     * @return return -1 if there is no way to bypass "x"
     */
    public int minSteps_rightAnddown(int[][] metrix){
        //check
        if(null == metrix || 0 == metrix.length || 0 == metrix[0].length || 0 != metrix[0][0]){
            return -1;
        }
        
        int m = metrix.length;
        int n = metrix[0].length;
        int[] steps = new int[n + 1];//default all are 0
        
        //steps[1] = 1;
        for(int row = 0, col = 0; col < n; col++){
            if(0 == metrix[row][col]){
                steps[col + 1] = steps[col] + 1; // note, steps[0] will be 0 always, steps[1] will be 1 when row is 0 and metrix[0][0] is not 'x'
            }else{
                break;
            }
        }

        for(int row = 1; row < m; row++){
            for(int col = 1; col <= n; col++){
                if( 0 == metrix[row][col - 1]){
                    if(0 != steps[col] & 0 != steps[col - 1]){
                        steps[col] = Math.min(steps[col], steps[col - 1]) + 1;
                    }else if(0 != steps[col] || 0 != steps[col - 1]){
                        steps[col] = Math.max(steps[col], steps[col - 1]) + 1;
                    }else{
                        steps[col] = 0;
                    }
                }else{
                    steps[col] = 0;
                }
            }
        }
        
        return steps[n] - 1;
    }
    
    
    /**
     *  BFS
     * @param metrix
     * @return
     */
    public int minSteps_4Directions(int[][] metrix){
        //check
        if(null == metrix || 0 == metrix.length || 0 == metrix[0].length || 0 != metrix[0][0]){
            return -1;
        }
        
        int m = metrix.length;
        int n = metrix[0].length;

        int[][] diffs = {{1, 0},{-1, 0},{0, 1},{0, -1}};
        
        Stack<int[]> stack = new Stack<int[]>();
        stack.push(new int[]{0, 0});
        
        int[] curr;
        int i, j, newI, newJ;
        int step = 0;
        int[][] steps = new int[m][n];//default all are 0
        while(!stack.isEmpty()){
            for(int t = stack.size(); t > 0; t--){
                curr = stack.pop();
                i = curr[0];
                j = curr[1];

                if(0 != steps[i][j]){
                    continue;
                }
                
                if(0 != metrix[i][j]){
                    steps[i][j] = Integer.MAX_VALUE;
                }else{
                    steps[i][j] = step;
                    
                    for(int k = 0; k < diffs.length; k++){
                        newI = i + diffs[k][0];
                        newJ = j + diffs[k][1];
                        
                        if(newI >= 0 && newI < m && newJ >= 0 && newJ < n){
                            stack.push(new int[]{newI, newJ});
                        }
                        
                    }
                }
            }
            
            step++;
        }
        
        return steps[m - 1][n - 1] > 0 ? steps[m - 1][n - 1] - 1 : -1;
    }
    
    class Pair{
        int x;
        int y;
        
        public Pair(int x, int y){
            this.x = x;
            this.y = y;
        }
    }

    public static void main(String[] args) {
        // TODO Auto-generated method stub

    }

}
