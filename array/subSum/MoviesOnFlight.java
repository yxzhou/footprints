/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package array.subSum;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import org.junit.Assert;

/**
 * _https://www.lintcode.com/problem/1373
 * 
 * You are on a flight and wanna watch two movies during this flight. You are given an array movieDurations which
 * includes all the movie durations. You are also given the duration of the flight which is k minutes. Now, you need to
 * pick two movies and the total duration of the two movies is less than or equal to (k - 30min).
 *
 * Find the pair of movies with the longest total duration and return their indexes. If multiple found, return the pair
 * with the longest movie.
 *
 *
 * Example: 
 * Input: movieDurations = [90, 85, 75, 60, 120, 150, 125], d = 250 
 * Output: [0, 6] 
 * Explanation:
 * movieDurations[0] + movieDurations[6] = 90 + 125 = 215 is the maximum number within 220 (250min - 30min)
 *
 */
public class MoviesOnFlight {
    
    /**
     * @param arr: An integer array represents durations of movies
     * @param k: An integer represents the duration of the flight
     * @return the pair of movies index with the longest total duration
     */
    public int[] FlightDetails(int[] arr, int k) {
        if(arr == null || arr.length < 2 ){
            return new int[0];
        }

        Map<Integer, Integer> map = new HashMap<>();
        for(int i = 0; i < arr.length; i++){
            map.putIfAbsent(arr[i], i);
        }

        Arrays.sort(arr);

        k -= 30;

        int p = 0;
        int q = 0;
        int max = -1;
        int sum;
        for(int l = 0, r = arr.length - 1;  l < r; ){
            sum = arr[l] + arr[r];
            if(sum == k){
                max = sum;

                p = l;
                q = r; 

                break;

            } else if(sum > k){
                r--;
                while(l < r && arr[r] == arr[r + 1]){
                    r--;
                }
            } else { // sum <= k
                if(max < sum){
                    max = sum;

                    p = l;
                    q = r; 
                }

                l++;
                while(l < r && arr[l] == arr[l - 1]){
                    l++;
                }
            }
        }

        if(max == -1){
            return new int[0];
        }

        p = map.get(arr[p]);
        q = map.get(arr[q]);

        int[] result = new int[2];
        if(p < q){
            result[0] = p;
            result[1] = q;
        }else{
            result[0] = q;
            result[1] = p;
        }
        return  result;
    }
    
    
    public static void main(String[] args){
        
        int[][][] inputs = {
            {
                {90, 85, 75, 60, 120, 150, 125}, 
                {250},
                {0,6}

            },
            {
                {250, 116, 365, 489, 274, 233, 329, 48},
                {859},
                {3,6}
            },
            {
                {222, 182, 274, 341, 417, 48, 350, 9, 110, 34, 292, 142},
                {192},
                {5,8}
            },
            {
                {183, 117, 452, 296, 229, 21, 331, 498, 481, 429, 387, 155, 359, 370, 209, 245, 106, 164, 312, 325, 444, 81, 31, 151, 411, 107, 192, 67, 341, 139, 372, 483, 243, 468},
                {457},
                {12,27}
            }
        };

        MoviesOnFlight sv = new MoviesOnFlight();
        
        for(int[][] input : inputs){
            System.out.println(String.format("\narr: %s,  k = %d", Arrays.toString(input[0]), input[1][0] ));
            
            //System.out.println(Arrays.toString(sv.FlightDetails(input[0], input[1][0])));
            
            Assert.assertArrayEquals(input[2], sv.FlightDetails(input[0], input[1][0]));
        }
        
    }
    
}
