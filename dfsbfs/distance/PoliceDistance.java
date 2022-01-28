/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dfsbfs.distance;

import java.util.LinkedList;
import java.util.Queue;

/**
 * _https://www.lintcode.com/problem/1367
 * 
 * Given a matrix size of n x m, element 1 represents policeman, -1 represents wall and 0 represents empty. Now please
 * output a matrix size of n x m, output the minimum distance between each empty space and the nearest policeman
 *
 * Constraints:
 * Given a matrix size of n x m， n <= 200，m <= 200. We guarantee that each empty space can be reached by one policeman
 * at least.
 * 
 * Example1
 * Input: 
 * mat =
 * [
    [0, -1, 0],
    [0, 1, 1],
    [0, 0, 0]
 * ]
 * Output: [[2,-1,1],[1,0,0],[2,1,1]]
 * Explanation: The distance between the policeman and himself is 0, the shortest distance between the two policemen to 
 * other empty space is as shown above
 * 
 * Example2
 * Input: 
 * mat =
 * [
    [0, -1, -1],
    [0, -1, 1],
    [0, 0, 0]
 * ]
 * Output: [[5,-1,-1],[4,-1,0],[3,2,1]]
 * Explanation: The shortest distance between the policemen to other 5 empty space is as shown above.
 * 
 */
public class PoliceDistance {
    
    /**
     * @param matrix : the martix
     * @return the distance of grid to the police
     */

    public int[][] policeDistance(int[][] matrix ) {
        if(matrix == null || matrix.length == 0 || matrix[0].length == 0){
            return new int[0][0];
        }

        int n = matrix.length;
        int m = matrix[0].length;

        int[][] result = new int[n][m];
        Queue<Integer> queue = new LinkedList<>();

        for(int r = 0; r < n; r++){
            for(int c = 0; c < m; c++){
                if(matrix[r][c] == -1){
                    result[r][c] = -1;
                } else if(matrix[r][c] == 1){
                    queue.add(r * m + c);
                }
            }
        }

        int[][] diffs = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};
            
        //BFS
        int top;
        int i, j;
        int ni, nj;
        int count = 0;
        while(!queue.isEmpty()){
            count++;

            for(int k = queue.size(); k > 0; k--){
                top = queue.poll();
                i = top / m;
                j = top % m;

                for(int[] diff : diffs){
                    ni = i + diff[0];
                    nj = j + diff[1];

                    if(ni >= 0 && ni < n && nj >= 0 && nj < m &&  matrix[ni][nj] == 0 &&( result[ni][nj] == 0 || count < result[ni][nj])  ){
                        result[ni][nj] = count;

                        queue.add(ni * m + nj);
                    }

                }
            }
        }

        return result;
    }
    
}
