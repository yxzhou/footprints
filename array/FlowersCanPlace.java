/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package array;

import java.util.Arrays;
import org.junit.Assert;

/**
 * _https://www.lintcode.com/problem/1138
 * 
 *
 * Suppose you have a long flowerbed in which some of the pots are planted and some are not. However, flowers cannot be
 * planted in adjacent plots - they would compete for water and both would die.
 *
 * Given a flowerbed (represented as an array containing 0 and 1, where 0 means empty and 1 means not empty), and a
 * number n, return if n new flowers can be planted in it without violating the no-adjacent-flowers rule.
 * 
 * 1.The input array won't violate no-adjacent-flowers rule.
 * 2.The input array size is in the range of [1, 20000].
 * 3.n is a non-negative integer which won't exceed the input array size.
 * 
 * Example1
 * Input: flowerbed = [1,0,0,0,1], n = 1
 * Output: True
 * 
 * Example2
 * Input: flowerbed = [1,0,0,0,1], n = 2
 * Output: False
 * 
 * Thoughts:
 *   s1: simulation, valid place means: curr and left and right all are not planted, 
 *     
 *   greedy, count the valid place from left to right (FIFO). 
 * 
 */
public class FlowersCanPlace {
    
    /**
     * 
     * @param flowerbed: an array
     * @param n: an Integer
     * @return if n new flowers can be planted in it without violating the no-adjacent-flowers rule
     */
    public boolean canPlaceFlowers(int[] flowerbed, int n) {
        if(flowerbed == null || n > flowerbed.length){
            return false;
        }
        if(n <= 0){
            return true;
        }

        for(int i = 0, m = flowerbed.length; i < m; i++){

            if( flowerbed[i] == 0 && (i == 0 || flowerbed[i - 1] == 0)  && (i == m - 1 || flowerbed[i + 1] == 0)){
                n--;

                if(n <= 0){
                    return true;
                }

                flowerbed[i] = 1;
            }
        }

        return false;

    }
    
    public boolean canPlaceFlowers_n(int[] flowerbed, int n) {
        if(flowerbed == null || n > flowerbed.length){
            return false;
        }
        if(n <= 0){
            return true;
        }

        boolean left = true; //init: the left offlowerbed[0] is true
        boolean curr;
        for(int i = 0, m = flowerbed.length; i < m; i++){
            curr = (flowerbed[i] == 0);

            if( curr && left  && (i == m - 1 || flowerbed[i + 1] == 0)){
                n--;

                left = false;
            }else{
                left = curr;
            }
            
        }

        return n <= 0;
    }
    
    public boolean canPlaceFlowers_n_1(int[] flowerbed, int n) {
        if(flowerbed == null || n > flowerbed.length){
            return false;
        }
        if(n <= 0){
            return true;
        }

        for(int i = 0, m = flowerbed.length - 1; i <= m; ){
            
            if(i < m && flowerbed[i + 1] == 1){// xx1
                i += 3;
            }else if(flowerbed[i] == 1){// x10
                i += 2;
            }else if(i > 0 && flowerbed[i - 1] == 1){ //100
                i++;
            }else{ //000
                i += 2;
                n--;
            }
        }

        return n <= 0;
    }
    
    /**  
                n     s    k    end
        000 -> 010   +2    2    +4
        001          +2    3    +5
        010          +2    2 
        011          +2    3

        100          +2    1
        101          +2    3
        110          +2    2 
        111          +2    3

     * @param flowerbed: an array
     * @param n: an Integer
     * @return if n new flowers can be planted in it without violating the no-adjacent-flowers rule
     */
    public boolean canPlaceFlowers_n_2(int[] flowerbed, int n) {
        if(flowerbed == null || n > flowerbed.length){
            return false;
        }
        if(n <= 0){
            return true;
        }

        int x = 0b110; //init: the left offlowerbed[0] is true
        int k;
        int end;
        for(int i = 0, m = flowerbed.length; i < m; ){
            if(x == 0b000){
                n--;
            }

            if( (x & 1) == 1 ){
                k = 3;
            }else if(x == 0b100){
                k = 1;
            }else{
                k = 2;
            }

            x <<= k;
            for( end = Math.min(i + k, m), k--; i < end; i++, k--){
                x |= ( flowerbed[i] << k);
            }
            x &= 0b111;
        }

        if(x == 0b000){
            n--;
        }
        return n <= 0;
    }
    
    
    /**
     * Given int[] flowerbed, If no flower between [i + 1, j - 1] (j - i >= 2), there are flower on i and j. The max flowers can be 
     * planted is (j - i - 2) / 2
     * 
     * @param flowerbed: an array
     * @param n: an Integer
     * @return if n new flowers can be planted in it without violating the no-adjacent-flowers rule
     */
    public boolean canPlaceFlowers_x_1(int[] flowerbed, int n) {
        int count = 0;
        
        int m = flowerbed.length;
        int left = -2;
        for(int i = 0; i < m; i++){
            if(flowerbed[i] == 1){
                count += (i - left - 2) / 2;
                left = i;
            }
        }
        
        count += (m - left - 1) / 2;
        return count >= n;
    }
    
    
    public static void main(String[] args){
        
        FlowersCanPlace sv = new FlowersCanPlace();
        
        int[][][] inputs = {
            //{flowerbed, {n}, {expect: 1,true; 0, false }}
            {
                {1,0,0,0,1},
                {1},
                {1}
            }, 
            {
                {1,0,0,0,1},
                {2},
                {0}
            }, 
            {
                {0,0,0,0,1},
                {2},
                {1}
            }, 
            {
                {1,0,0,1},
                {1},
                {0}
            },
            {
                {0,0,0,1},
                {2},
                {0}
            }, 
            {
                {0,0,0,1},
                {1},
                {1}
            },
            {
                {1,0,0,0},
                {2},
                {0}
            },
            {
                {1,0,0,0},
                {1},
                {1}
            },
            {
                {0,0,0},
                {2},
                {1}
            }, 
        };
        
        for(int[][] input : inputs){
            System.out.println(String.format("flowerbed: %s, n = %d", Arrays.toString(input[0]), input[1][0]));
            
            //Assert.assertEquals(input[2][0] == 1, sv.canPlaceFlowers( Arrays.copyOf(input[0], input[0].length) , input[1][0]));
            
            
            //Assert.assertEquals(input[2][0] == 1, sv.canPlaceFlowers_n(input[0], input[1][0]));
            
            //Assert.assertEquals(input[2][0] == 1, sv.canPlaceFlowers_n_1(input[0], input[1][0]));
            
            Assert.assertEquals(input[2][0] == 1, sv.canPlaceFlowers_x_1(input[0], input[1][0]));
        }
        
    }
    
}
