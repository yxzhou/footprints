/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package matrix;

import java.util.BitSet;
import java.util.HashMap;
import java.util.Map;

/**
 *  _https://www.lintcode.com/problem/881
 * 
 * 
 * Given a picture consisting of black and white pixels, and a positive integer N, find the number of black pixels
 * located at some specific row R and column C that align with all the following rules:
 *
 * Row R and column C both contain exactly N black pixels. For all rows that have a black pixel at column C, they should
 * be exactly the same as row R The picture is represented by a 2D char array consisting of 'B' and 'W', which means
 * black and white pixels respectively.
 *
 * Notes:
 * The range of width and height of the input 2D array is [1,200].
 *
 * Example 1 
 * Input: 
 * ["WBWBBW", 
 *  "WBWBBW", 
 *  "WBWBBW",
 *  "WWBWBW"]  and 3
 * 
 * Output: 6 
 * Explanation: All the bold 'B' in red are we need (all 'B's at column 1 and 3). 
 * [['W', 'B', 'W', 'B', 'B', 'W'], 
 *  ['W', 'B', 'W', 'B', 'B', 'W'], 
 *  ['W', 'B', 'W', 'B', 'B', 'W'], 
 *  ['W', 'W', 'B', 'W', 'B', 'W']]
 *
 * Take 'B' at row R = 0 and column C = 1 as an example: 
 *   Rule 1, row R = 0 and column C = 1 both have exactly N = 3 black pixels. 
 *   Rule 2, the rows have black pixel at column C = 1 are row 0, row 1 and row 2. They are exactly the same as row R = 0.
 *
 * Example 2
 * Input: ["WWW","WWW","WWB"] 1 
 * Output: 1
 *
 */
public class LonelyPixel {

    /**
     * @param picture: a 2D char array
     * @param N: an integer
     * @return
     */
    public int findBlackPixel(char[][] picture, int N) {
        int n = picture.length;
        int m = picture[0].length;

        int[] rows = new int[n]; //the number of black pixels on row
        int[] columns = new int[m]; // the number of black pixels on column

        for(int r = 0; r < n; r++){
            for(int c = 0; c < m; c++){
                if(picture[r][c] == 'B'){
                    rows[r]++;
                    columns[c]++;
                }
            }
        }

        int result = 0;

        int mr;
        BitSet[] bits = new BitSet[n]; 
        f1: for(int c = 0; c < m; c++){
            if(columns[c] != N ){
                continue;
            }
            
            mr = -1;
            f2: for(int r = 0; r < n; r++){
                if(rows[r] != N){
                    continue;
                }

                if(bits[r] == null){
                    bits[r] = new BitSet(m);

                    for(int i = 0; i < m; i++){
                        if(picture[r][c] == 'B'){
                            bits[r].set(c);
                        }        
                    }        
                }

                if(mr == -1){
                    mr = r;
                }else if(!bits[mr].equals(bits[r]) ){
                    continue f1;
                }
            }

            result += N;
        }

        return result;
    }
    
    
    public int findBlackPixel_n(char[][] picture, int N) {
        int n = picture.length;
        int m = picture[0].length;

        int[] columns = new int[m]; // the number of black pixels on column

        Map<String, Integer> map = new HashMap<>();
        StringBuilder sb;
        int count; //the number of black pixels on row
        for(int r = 0; r < n; r++){

            count = 0;
            sb = new StringBuilder();
            for(int c = 0; c < m; c++){
                if(picture[r][c] == 'B'){
                    columns[c]++;
                    count++;
                }

                sb.append(picture[r][c]);
            }

            if(count == N){
                String key = sb.toString();
                map.put(key, map.getOrDefault(key, 0) + 1);
            }
        }

        int result = 0;

        for(String key : map.keySet()){
            for(int c = 0; c < m; c++){
                if(key.charAt(c) == 'B' && columns[c] == N ){
                    result += N;
                }
            }
        }

        return result;
    }
}
