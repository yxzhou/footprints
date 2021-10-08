/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package matrix;

import java.util.Arrays;

/**
 * _https://www.lintcode.com/problem/1044/
 * 
 * 
 * 
 * 
 */
public class LargestPlusSign {
    /**
     * @param N: size of 2D grid
     * @param mines: in the given list
     * @return: the order of the plus sign
     */
    public int orderOfLargestPlusSign(int N, int[][] mines) {
        if(N < 1 || mines == null){
            return 0;
        }

        boolean[][] zeros = new boolean[N][N]; //default all are false. true means 0 which list mines
        for(int[] mine : mines){
            zeros[mine[0]][mine[1]] = true;
        }

        int[][] signs = new int[N][N];
        for(int[] sign : signs){
            Arrays.fill(sign, N);
        }

        for(int i = 0; i < N; i++){ 
            int count = 0;
            // to row i from left to right 
            for(int j = 0; j < N; j++){
                count = zeros[i][j] ? 0 : count + 1;
                signs[i][j] = Math.min(signs[i][j], count);
            }

            //to row i from right to left
            count = 0;
            for(int j = N - 1; j >= 0; j--){
                count = zeros[i][j] ? 0 : count + 1;
                signs[i][j] = Math.min(signs[i][j], count);
            }

            //to column i from up to down.
            count = 0;
            for(int j = 0; j < N; j++){
                count = zeros[j][i] ? 0 : count + 1;
                signs[j][i] = Math.min(signs[j][i], count);
            }

            //to column i from down to up.
            count = 0;
            for(int j = N - 1; j >= 0; j--){
                count = zeros[j][i] ? 0 : count + 1;
                signs[j][i] = Math.min(signs[j][i], count);
            }
        }

        int result = 0; //the order of the plus sign
        for(int i = 0; i < N; i++){ 
            for(int j = 0; j < N; j++){
                result = Math.max(result, signs[i][j]);
            }
        }
        return result;
    }
    
    public int orderOfLargestPlusSign_n(int N, int[][] mines) {
        if(N < 1 || mines == null){
            return 0;
        }

        boolean[][] zeros = new boolean[N][N]; //default all are false. true means 0 which list mines
        for(int[] mine : mines){
            zeros[mine[0]][mine[1]] = true;
        }

        int[][] signs = new int[N][N];
        for(int[] sign : signs){
            Arrays.fill(sign, N);
        }

        int l, r, u, d;
        for(int i = 0; i < N; i++){ 
            l = r = u = d = 0;
            for(int j = 0, k = N - 1; j < N; j++, k--){
                l = zeros[i][j] ? 0 : l + 1;
                signs[i][j] = Math.min(signs[i][j], l);

                r = zeros[i][k] ? 0 : r + 1;
                signs[i][k] = Math.min(signs[i][k], r);      

                u = zeros[j][i] ? 0 : u + 1;
                signs[j][i] = Math.min(signs[j][i], u);                          

                d = zeros[k][i] ? 0 : d + 1;
                signs[k][i] = Math.min(signs[k][i], d);
            }
        }

        int result = 0; //the order of the plus sign
        for(int i = 0; i < N; i++){ 
            for(int j = 0; j < N; j++){
                result = Math.max(result, signs[i][j]);
            }
        }
        return result;
    }
}
