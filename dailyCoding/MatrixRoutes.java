package fgafa.dailyCoding;

/**
 *
 * There is an N by M matrix of zeroes. Given N and M, write a function to count the number of ways of starting at the top-left corner and getting to the bottom-right corner. You can only move right or down.

 For example, given a 2 by 2 matrix, you should return 2, since there are two ways to get to the bottom-right:

 Right, then down
 Down, then right

 Given a 5 by 5 matrix, there are 70 ways to get to the bottom-right.
 *
 * tags: facebook
 *
 */

public class MatrixRoutes {

    public int routes(int n, int m){
        if(n <= 0 || m <= 0){
            return 0;
        }

        int[][] routes = new int[n][m];

        for(int row = 0; row < n; row++){
            routes[row][0] = 1;
        }

        for(int col = 0; col < m; col++){
            routes[0][col] = 1;
        }

        for(int row = 1; row < n; row++){
            for(int col = 1; col < m; col++){
                routes[row][col] = routes[row - 1][col] + routes[row][col - 1];
            }
        }

        return routes[n - 1][m - 1];
    }


    public static void main(String[] args){

        MatrixRoutes sv = new MatrixRoutes();

        for(int n = 1; n < 6; n++){
            System.out.println(String.format("routes(%d, %d) is %d", n, n, sv.routes(n, n)));
        }

    }

}
