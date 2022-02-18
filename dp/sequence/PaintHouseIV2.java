/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dp.sequence;

/**
 *
 * Continue on PaintHouseIV, bottom-up, 
 * 
 */
public class PaintHouseIV2 {
    
    public int minCost(int[] houses, int[][] costs, int m, int n, int target){
        
       /**
        * f[i][c][g] is the min cost when group number is g, the last color is c
        *
        */
        int[][][] f = new int[m + 1][n + 1][target + 2];
        
        for(int i = m; i >= 0; i--){
            for(int c = n; c >= 0; c--){
                for(int g = target + 1; g >= 0; g--){
                    if(i == m){            
                        f[i][c][g] = (g == target? 0 : -1);
                        continue;
                    }

                    if(g > target){
                        f[i][c][g] = -1;
                        continue;
                    }

                    int min = Integer.MAX_VALUE;
                    int price;
                    if(houses[i] > 0){
                        min = f[i + 1][houses[i]][g + (houses[i] == c? 0 : 1)];
                    }else{
                        for(int nc = 1; nc <= n; nc++){
                            price = f[i + 1][nc][g + (nc == c? 0 : 1)];

                            if(price >= 0){
                                min = Math.min( min,  costs[i][nc - 1] + price );    
                            }

                        }
                    }

                    f[i][c][g] = (min == Integer.MAX_VALUE? -1 : min);
                }
            }
        }
        
        return f[0][0][0];
    }
    
}
