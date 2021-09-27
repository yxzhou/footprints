package dp.sequence;

import java.util.Arrays;

/**
 * 
 * There is a straight highway with villages alongside the highway. The highway is represented as an integer axis, 
 * and the position of each village is identified with a single integer coordinate. There are no two villages in the same position. 
 * The distance between two positions is the absolute value of the difference of their integer coordinates.

   Post offices will be built in some, but not necessarily all of the villages. A village and the post office in it have the same position. 
   For building the post offices, their positions should be chosen so that the average distance from each village to its nearest post office is minimized.

   You are to write a program which, given the positions of the villages and the number of post offices, 
   computes the least possible sum of all distances between each village and its nearest post office, 
   and the respective desired positions of the post offices.
 *
 */

public class PostOfficer {

    
    public int postOffice(int[] A, int m) {
        int n = A.length;
        Arrays.sort(A);
        
        int[][] dp = new int[n+1][n+1];
        int[][] distance = init(A);
        if(n == 0 || m >= A.length) return 0;
        for(int i = 0; i <= n; i++){
            dp[i][1] = distance[1][i];
        }
        
        for(int k = 2; k <= m; k++){
            for(int i=k; i <= n; i++){
                dp[i][k] = Integer.MAX_VALUE;
                for(int j = 0; j < i; j++){
                    if(dp[i][k] == Integer.MAX_VALUE || dp[i][k] > (dp[j][k-1] + distance[j+1][i])){
                        dp[i][k] = dp[j][k-1] + distance[j+1][i];
                    }
                }
            }
        }
        return dp[n][m];
    }
    
    private int[][] init(int[] A){
        int n = A.length;
        int[][] distance = new int[n+1][n+1];
        for(int i = 1; i <= n; i++){
            for(int j = i+1; j <= n; j++){
                int mid = (i+j)/2;
                for(int k = i; k <= j; k++){
                    distance[i][j] += Math.abs(A[k-1] - A[mid-1]);
                }
            }
        }
        return distance;
    }
    
    public static void main(String[] args) {
        // TODO Auto-generated method stub

    }

}
