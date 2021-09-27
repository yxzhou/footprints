package util.random;

import math.IntegerHelper;
import util.Misc;
import org.junit.Test;

/**
 * You are given n numbers as well as n probabilities that sum up to 1. Write a function to generate one of the numbers with its corresponding probability.
 *
 * For example, given the numbers [1, 2, 3, 4] and probabilities [0.1, 0.5, 0.2, 0.2], your function should return 1 10% of the time, 2 50% of the time, and 3 and 4 20% of the time.
 *
 * You can generate random numbers between 0 and 1 uniformly.
 *
 * Tags: Triplebyte
 *
 * Solutions:
 *    To case {{1, 2, 3, 4} and {0.1, 0.5, 0.2, 0.2}}, the point is to 10% or 1/10,
 *    Now it gave random() to retur 0 or 1.
 *    With 4 times random(), it can get 2^4 status uniformly.  2^3 * random() + 2^2 * random() + 2^1 * random() + 1 * random()
 *
 *
 */

public class Probability {

    public int getAsTheProbability(int[] values, float[] probabilties){
        if(values == null || probabilties == null || values.length != probabilties.length || values.length == 0){
            throw new IllegalArgumentException(" ");
        }

        float sum = 0;
        for(float p : probabilties){
            sum += p;
        }
        if(Math.abs(sum - 1) > 0.0000001){
            throw new IllegalArgumentException(" ");
        }

        if(values.length == 1){
            return values[0];
        }

        int maxLength = 0;
        for(float p : probabilties){
            maxLength = Math.max(maxLength, String.valueOf(p).length());
        }
        maxLength -= 2;

        int factor = (int)Math.round(Math.pow(10, maxLength));
        int[] scopes = new int[probabilties.length + 1];
        for(int i = 0; i < probabilties.length; i++){
            scopes[i + 1] = Math.round(probabilties[i] * factor) + scopes[i];
        }

        int n = (int)Math.ceil(maxLength * log2(10));

        int currIndex = 0;
        while((currIndex = calculate(n)) >= factor) ;

        Misc.printArray_Int(scopes);
        System.out.println(currIndex);

        return values[binarySearch(scopes, currIndex)];
    }

    private int binarySearch(int[] scopes, int curr){
        int left = 0;
        int right = scopes.length - 1;

        while(left <= right){
            int middle = left + (right - left) / 2;

            if(scopes[middle] == curr){
                return middle;
            } else if(scopes[middle] < curr){
                left = middle + 1;
            }else{
                right = middle - 1;
            }
        }

        return right;
    }

    private int calculate(int n){
        int result = 0;
        int factor = 1;

        for(int i = 0; i < n; i++){
            result += factor * random();

            factor <<= 1;
        }

        return result;
    }

    private static double log2(int n){
       return Math.log(n)/Math.log(2);
    }

    static java.util.Random random = new java.util.Random();
    private static int random(){
        return random.nextInt(2);
    }


    @Test public void test(){

        for(int i = 0; i < 20; i++){
            System.out.println(getAsTheProbability(new int[]{1, 2, 3, 4}, new float[]{0.1f, 0.5f, 0.2f, 0.2f} ));
        }

        for(int i = 0; i < 20; i++){
            System.out.println(getAsTheProbability(new int[]{1, 2}, new float[]{0.5f, 0.5f} ));
        }

        for(int i = 0; i < 20; i++){
            System.out.println(getAsTheProbability(new int[]{1, 2, 3}, new float[]{0.33f, 0.33f, 0.34f} ));
        }
    }
}
