/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package array;

/**
 *
 * Given a non-negative integer. You could choose to swap two digits of it. Return the maximum valued number you could get.

 * The given number is in the range of [0, 10^8]
 *
 * Example 1:
 * Input: 2736
 * Output: 7236
 * Explanation: Swap the number 2 and the number 7.
 * 
 * Example 2:
 * Input: 9973
 * Output: 9973
 * Explanation: No swap.
 * 
 */
public class MaximumSwap {
    public int maximumSwap(int num) {
        final int N = 11;
        int[] digits = new int[N]; //digits[N - 1] is the single digit of num, digits[N - 2] is the tenth digit of num,
        int[] maxDigits = new int[N]; //max[i] is the first max digit from right to left in ( N - 1 to i in digits) 
        int maxDigit = 0;
        int i = N - 1;

        while(num > 0){
            digits[i] = num % 10;
            if(digits[maxDigit] < digits[i]){
                maxDigit = i;
            }
            maxDigits[i] = maxDigit;

            i--;
            num /= 10;
        }

        i++;
        for( int j = i; j < N ; j++){
            if(j != maxDigits[j] && digits[j] < digits[maxDigits[j]] ){
                //swap
                int tmp = digits[j];
                digits[j] = digits[maxDigits[j]];
                digits[maxDigits[j]] = tmp;

                break;
            }
        }

        int result = 0;
        for( int j = i; j < N ; j++){
            result = result * 10 + digits[j];
        }
        return result;
    }
    
    public int maximumSwap_n(int num) {
        char[] digits = String.valueOf(num).toCharArray();
        int n = digits.length;
        int[] maxDigits = new int[n]; //max[i] is the first max digit from right to left ( N - 1 to i in digits) 
        int maxDigit = n - 1;
        
        for(int i = n - 1; i >= 0; i--){
            if(digits[maxDigit] < digits[i]){
                maxDigit = i;
            }
            maxDigits[i] = maxDigit;
        }

        for( int j = 0; j < n ; j++){
            if(j != maxDigits[j] && digits[j] < digits[maxDigits[j]] ){
                //swap
                char tmp = digits[j];
                digits[j] = digits[maxDigits[j]];
                digits[maxDigits[j]] = tmp;

                break;
            }
        }

        String result = new String(digits);
        return Integer.valueOf(result);
    }
}
