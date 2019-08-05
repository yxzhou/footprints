package fgafa.dailyCoding.game;

import org.junit.Test;

/**
 * This problem was asked by Spotify.
 *
 * Write a function, throw_dice(N, faces, total), that determines how many ways it is possible to throw N dice with some number of faces each to get a specific total.
 *
 * For example, throw_dice(3, 6, 7) should equal 15.
 *
 */

public class DicePermutation {

    public int throwDice(int dice, int face, int sum){
        int result = 0;

        if(dice == 1){
            if(sum > 0 && sum <= face){
                result = 1;
            }
        } else{
            for(int i = 1; i <= face; i++){
                result += throwDice(dice - 1, face, sum - i);
            }
        }

        return result;
    }

    public int throwDice_dp(int dice, int face, int sum){
        int[] dp = new int[sum + 1]; //default all are 0
        dp[0] = 1;

        for(int j = 1; j <= dice; j++){
            for(int k = Math.min(sum, dice * j); k >= j; k--){
                for(int i = Math.min(face, k); i > 0; i--){
                    dp[k] += dp[k - i];
                }
            }
        }

        return dp[sum];
    }

    public static void main(String[] args){
        DicePermutation sv = new DicePermutation();

        System.out.println("(3, 6, 7): " + sv.throwDice(3, 6, 7) + " - " + sv.throwDice_dp(3, 6, 7));
    }
}
