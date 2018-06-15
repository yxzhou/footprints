package fgafa.todo.m;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * _https://www.jiuzhang.com/article/sQOWpf/
 *
 * Problem: Give two positive numbers, maxChoosableInteger and desiredTotal.
 * Two persons play a game, pick a number between 1 to maxChoosableInteger, turn by turn, it can't pick the number that already picked before.
 * The person win when sum of all picked number is not smaller than the desiredTotal.
 *
 * Return if the person who pick at first would win.
 *
 * Note,  you can assume that maxChoosableInteger<= 20 and desiredTotal <= 300.
 *
 * Example:
 *   Given: maxChoosableInteger = 10 and desiredTotal = 11
 *   return false.  Because the person who do the second pick will win.
 *
 */

public class CanIWin {


    public boolean canIWin(int maxChoosableInteger, int desiredTotal){
        if(maxChoosableInteger <= 0 || maxChoosableInteger > 20 || desiredTotal <= 0 || desiredTotal > 300){
            throw new IllegalArgumentException("maxChoosableInteger is in (0, 20] and desiredTotal is in (0, 300]");
        }

        if(maxChoosableInteger >= desiredTotal){
            return true;
        }

        int totalSum = (maxChoosableInteger + 1) * maxChoosableInteger / 2;
        if(totalSum < desiredTotal){
            return false;
        }

        int forSelected = (1 << maxChoosableInteger) - 1; // example, maxChoosableInteger is 2, forSelected is 0b11.
        Map<Integer, Boolean> cache = new HashMap<>();

        canIWin(maxChoosableInteger, desiredTotal, forSelected, cache);

        return cache.get(forSelected);

    }

    boolean canIWin(int maxChoosableInteger, int desiredTotal, int forSelected, Map<Integer, Boolean> cache){

        if(cache.containsKey(forSelected)){
            return cache.get(forSelected);
        }

        for(int i = 1, j = 1; i <= maxChoosableInteger; i++, j<<=1 ){
            if((forSelected & j) > 0){ // i is ok for be selected
                if(desiredTotal <= i || !canIWin(maxChoosableInteger, desiredTotal - i, forSelected ^ j, cache)){
                    cache.put(forSelected, true);
                    return true;
                }
            }
        }

        cache.put(forSelected, false);
        return false;
    }


    public static void main(String[] args){
        int[][] input = {
                {4, 3},
                {4, 5},
                {4, 6},
                {4, 7},
                {4, 8},
                {4, 9},
                {4, 10},
                {4, 11},
                {5, 5},
                {5, 6},
                {5, 8},
                {5, 13},
                {5, 14},
                {5, 15},
                {5, 16}

        };

        CanIWin service = new CanIWin();

        for(int[] pair : input){

            System.out.println(String.format("Input: %d, %d, --Output: %b ", pair[0], pair[1], service.canIWin(pair[0], pair[1])));
        }
    }

}
