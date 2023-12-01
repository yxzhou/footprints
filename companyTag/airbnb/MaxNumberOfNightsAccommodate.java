/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package companyTag.airbnb;

import java.util.Arrays;
import junit.framework.Assert;

/**
 *
 * Given a set of numbers in an array which represent number of consecutive nights of AirBnB reservation requested, as a
 * host, pick the sequence which maximizes the number of days of occupancy, at the same time, leaving at least 1 day gap
 * in between bookings for cleaning. Problem reduces to finding max-sum of non-consecutive array elements.
 *
 */
public class MaxNumberOfNightsAccommodate {
    public int rob(int[] nums) {
        if(nums == null){
            return 0;
        }
        
        int include = 0; //the max, include the last
        int exclude = 0; //the max, exclude the last
        int tmp;
        for(int x : nums){
            tmp = include;
            include = exclude + x;
            exclude = Math.max(tmp, exclude);
        }

        return Math.max(include, exclude);
    }
    
    public static void main(String[] args){
        int[][][] inputs = {
            //{nums, {expect}}
            {
                {5, 6, 3, 1},
                {8}
            },
            {
                {6, 5, 0, 1, 0, 9},
                {16}
            },
            {
                {5, 1, 1, 5},
                {10}
            },
            {
                {3, 6, 4},
                {7}
            },
            {
                {4, 10, 3, 1, 5},
                {15}
            }
        };
        
        MaxNumberOfNightsAccommodate sv = new MaxNumberOfNightsAccommodate();
        
        for(int[][] input : inputs){
            System.out.println(Arrays.toString(input[0]));
            
            Assert.assertEquals(input[1][0], sv.rob(input[0]));
        }
    }
}
