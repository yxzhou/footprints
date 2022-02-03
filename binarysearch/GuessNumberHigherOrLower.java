/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package binarysearch;

/**
 * _https://www.lintcode.com/problem/662
 *
 * We are playing the Guess Game. The game is as follows:
 *
 * I pick a number from 1 to n. You have to guess which number I picked.
 *
 * Every time you guess wrong, I will tell you if this number is greater or less than the number you guessed.
 *
 * You call a pre-defined API guess(int num) which returns 3 possible results (-1, 1, or 0): -1 means this number is
 * less than the number you guessed
 *
 * 1 means this number is greater than the number you guessed 0 means this number is equal to the number you guessed
 *
 * 
 * Example 1:
 * Input : n = 10, I pick 4 (but you don't know) 
 * Output : 4
 *
 */
public class GuessNumberHigherOrLower {
    
    /**
     * @param n an integer
     * @return the number you guess
     */
    public int guessNumber(int n) {

        int low = 1; 
        int high = n;
        int mid;
        int r;
        while(low < high){
            mid = low + (high - low) / 2;

            r = guess(mid);
            if(r == 0){
                return mid;
            }

            if(r < 0){
                high = mid - 1;
            }else{
                low = mid + 1;
            }
        }

        return low;
    }
    
    /**
     * The guess API is defined in the parent class GuessGame.
     *
     * @param num, your guess
     * @return -1 if my number is lower, 1 if my number is higher, otherwise return 0
     */
     static java.util.Random random = new java.util.Random();
     private int guess(int num){
         return random.nextInt(2);
     }

}
