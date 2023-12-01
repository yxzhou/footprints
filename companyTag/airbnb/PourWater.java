/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package companyTag.airbnb;

import java.util.Arrays;
import java.util.Stack;
import org.junit.Assert;

/**
 * Let's say you have been given a set of heights representing the heights of buildings. Additionally, you have been
 * given an index (let's call it target) and a quantity of water (let's call it q).
 *
 * In this question, you will be asked to print on the screen the state of the world if q quantities of water is poured
 * at index target.
 * 
 * For example.
 * If building array is [5, 2, 0 , 3, 4], we represent it as:
 * O
 * O       O
 * O     O O
 * O O   O O
 * O O   O O
 * Now if q = 1, and target = 2, then you are supposed to print:
 * 
 * O 
 * O       O
 * O     O O
 * O O   O O
 * O O W O O
 * W represents water, O represents the building.
 * 
 * This was my second question in a 45 min interview. Question has a lots of edge cases. I could think of these:
 *   What happens if you pour water at a peak? Does it go left or right? Or split equally into two.
 *   Interviewer asked me to pick any option. Selected that it goes all on the right side
 *   What happens if you pour water at the edge? 
 *   Discussed that it would flow out
 *   What happens when a plain or a plateau is formed?
 * 
 * 
 * _https://www.lintcode.com/problem/851/
 * 
 * Given an elevation map, heights[i] representing the height of the terrain at that index. The width at each index is
 * 1. After V units of water fall at index K, how much water is at each index?
 *
 * Water first drops at index K, and then it flows according to the following rules:
 *    First, the droplet can not move to higher level. 
 *    If the droplet would eventually fall by moving left, then move left.
 *    Otherwise, if the droplet would eventually fall by moving right, then move right. 
 *    Otherwise, rise at it's current position. 
 * 
 * Here, "eventually fall" means that the droplet will eventually be at a lower level if it moves in that direction. 
 * Also, "level" means the height of the terrain plus any water in that column.
 *
 * You can assume there's infinitely high terrain on the two sides out of bounds of the array. Also, there could not be
 * partial water being spread out evenly on more than 1 grid block - each unit of water has to be in exactly one block.
 * 
 * Notes: 
 * heights will have length in [1,100] and contain integers in [0,99].
 * V will be in range [0,2000].
 * K will be in range [0,heights.length−1]. 
 * 
 */
public class PourWater {
    
    /**
     * Simulate
     * 
     * @param heights: the height of the terrain
     * @param v: the units of water
     * @param k: the index
     * @return how much water is at each index
     */
    public int[] pourWater(int[] heights, int v, int k) {
        
        //assume there's infinitely high terrain on the two sides out of bounds of the array
        int n = heights.length + 2;
        int[] result = new int[n];
        result[0] = Integer.MAX_VALUE;
        System.arraycopy(heights, 0, result, 1, n - 2);
        result[n - 1] = Integer.MAX_VALUE;

        k++;
        for(int i = 0, l, r; i < v; i++){

            //from k to left, check if the droplet would eventually fall by moving left
            for( l = k; l > 0 && result[l - 1] <= result[l]; l--){}
            if(result[l] < result[k]){ //found
                for( ; l < k && result[l] == result[l + 1]; l++ ){} //locate the first lowest position 

                result[l]++;
                continue;
            }

            //from k to right, check if the droplet would eventually fall by moving right
            for( r = k + 1; r < n && result[r - 1] >= result[r]; r++){}          
            if(result[--r] < result[k]){ //found
                for( ; r > k && result[r - 1] == result[r]; r-- ){} //locate the first lowest position 

                result[r]++;
                continue;
            }

            //rise at it's current position
            result[k]++;
        }

        return Arrays.copyOfRange(result, 1, n - 1);
    }
    
    /**
     * monotonic stack, sweep line
     * 
     * @param heights
     * @param v
     * @param k
     * @return 
     */
    public int[] pourWater_x(int[] heights, int v, int k) {
        //assume there's infinitely high terrain on the two sides out of bounds of the array
        int n = heights.length;
        int[] origin = new int[n + 2]; //origin[1, n + 1] copy from the heigths
        int[] change = new int[n + 2]; //the height after pouring water 
        
        System.arraycopy(heights, 0, origin, 1, n);
        origin[0] = origin[n + 1] = Integer.MAX_VALUE;
        k++;
        
        Arrays.fill(change, -1);
            
        Stack<Integer> lefts = new Stack<>();
        int l = k - 1;
        
        Stack<Integer> rights = new Stack<>();
        int r = k + 1;

        int diff;
        Integer top;
        int min;
        int w;
        int size;
        outer: while(v > 0){
            //left
            lefts.add(k);
            while(!lefts.isEmpty() ){
                top = lefts.peek();
                min = Math.min(origin[l],  origin[top]) ;

                w = top - l - 1;
                size = ( change[l] == -1 ? 0 : min - change[l] ) * w ;

                if(size < v){
                    v -= size;
                    change[l] = min;
                }else{// size >= v
                    if(size == v){
                        change[l] = min;
                    }else { // size > v
                        change[l] += v / w;
                        change[top - 1 - v % w] = change[l] + 1; 
                    }

                    //v = 0;
                    break outer;
                }

                if ((diff = origin[l] - origin[top]) == 0) {
                    lefts.pop();
                    lefts.add(l);
                    l--;
                } else if (diff < 0) {
                    lefts.add(l);
                    l--;
                } else { // diff > 0
                    lefts.pop();
                }
            }      
            
            //right
            rights.add(k);
            while(!rights.isEmpty() ){
                top = rights.peek();
                min = Math.min(origin[r],  origin[top]);
                
                w = r - top - 1;
                size = ( change[r] == -1? 0 : min - change[r] ) * w;
                
                if (size < v) {
                    v -= size;
                    change[r] = min;
                } else {
                    if(size == v){
                        change[r] = min;
                    }else{ // size > v
                        change[r] += v / w;
                        change[top + 1 + v % w] = change[r] + 1; 
                    }

                    break outer;
                }
                
                if( (diff = origin[r] - origin[top]) == 0){
                    rights.pop();
                    rights.add(r);
                    r++;
                }else if( diff < 0 ){
                    rights.add(r);
                    r++;
                }else{ //diff > 0
                    rights.pop();
                }
            }

            //
            origin[k]++;
            v--;   

        }

        int[] result = new int[n];
        int h = change[0];    
        for(int i = 1; i < k; i++){
            result[i - 1] = Math.max(origin[i], h);
            h = Math.max(h, change[i]);
        }
        
        h = change[n + 1];  
        for(int i = n; i > k; i--){
            result[i - 1] = Math.max(origin[i], h);
            h = Math.max(h, change[i]);
        }
        
        result[k - 1] = origin[k];
        
        return result;
    }
    
    public static void main(String[] args){
        int[][][] inputs = {
            //{heights, {v}, {k}, expect}
            {
                {2, 1, 1, 2, 1, 2, 2},
                {4},
                {3},
                {2, 2, 2, 3, 2, 2, 2}
            },
            {
                {9, 5, 4, 14, 10, 15, 15, 2, 1, 8},
                {17},
                {8},
                {9, 5, 4, 14, 10, 15, 15, 9, 10, 9}
            },
            {
                {13, 7, 9, 6, 4, 4, 4, 10, 15, 9},
                {7},
                {1},
                {13, 9, 9, 6, 6, 6, 5, 10, 15, 9}
            },
            {
                {0, 1, 2, 3, 4, 5},
                {22},
                {0},
                {7, 6, 6, 6, 6, 6}
            },
            {
                {0, 1, 2, 3, 4, 5},
                {22},
                {5},
                {6, 6, 6, 6, 6, 7}
            },
            {
                {5, 4, 3, 2, 1, 0},
                {22},
                {5},
                {6, 6, 6, 6, 6, 7}
            },
            {
                {5, 4, 3, 2, 1, 0},
                {22},
                {0},
                {7, 6, 6, 6, 6, 6}
            },
            {
                {6,11,7,14,15,1,3,11,1,11},
                {4},
                {7},
                {6,11,7,14,15,4,4,11,1,11}
            },
            {
                {11,9,8,3,3,13,2,11,9,15},
                {16},
                {8},
                {11,9,8,3,3,13,12,13,13,15}
            }
        };
        
        PourWater sv = new PourWater();
        
        for(int[][] input : inputs){
//            System.out.println(String.format("\nheights: %s, v = %d, k = %d\n result:%s", 
//                    Arrays.toString(input[0]), input[1][0], input[2][0], 
//                    Arrays.toString(sv.pourWater_n(input[0], input[1][0], input[2][0]))) );
            
            //Assert.assertArrayEquals(Arrays.toString(input[0]), input[3], sv.pourWater(input[0], input[1][0], input[2][0]));

            Assert.assertArrayEquals(Arrays.toString(input[0]), input[3], sv.pourWater_x(input[0], input[1][0], input[2][0]));
        }
        
    }
    
}
