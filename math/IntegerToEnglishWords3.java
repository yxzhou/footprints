package fgafa.math;

import org.junit.Assert;
import org.junit.Test;

/**
 *
 * Convert a non-negative integer to its english belowTwenty representation. Given input is guaranteed to be less than 231 - 1.
 *
 * Example 1:
 *
 * Input: 123
 * Output: "One Hundred Twenty Three"
 * Example 2:
 *
 * Input: 12345
 * Output: "Twelve Thousand Three Hundred Forty Five"
 * Example 3:
 *
 * Input: 1234567
 * Output: "One Million Two Hundred Thirty Four Thousand Five Hundred Sixty Seven"
 * Example 4:
 *
 * Input: 1234567891
 * Output: "One Billion Two Hundred Thirty Four Million Five Hundred Sixty Seven Thousand Eight Hundred Ninety One"
 *
 */

public class IntegerToEnglishWords3 {
    private static String[] belowTwenty = {"", "One", "Two", "Three", "Four", "Five", "Six", "Seven", "Eight", "Nine", "Ten",
            "Eleven", "Twelve", "Thirteen", "Fourteen", "Fifteen", "Sixteen", "Seventeen", "Eighteen", "Nineteen"};
    private static String[] tens = {"", "", "Twenty", "Thirty", "Forty", "Fifty","Sixty", "Seventy", "Eighty", "Ninety"};

    private static String[] thousands = {"", "Thousand ", "Million ", "Billion " };
    private static int[]    nums   = {1, 1000, 1000_000, 1000_000_000 };

    public String numberToWords(int num) {
        //non-negative integer, guaranteed to be less than 2^31 - 1

        if(0 == num){
            return "Zero";
        }

        StringBuilder result = new StringBuilder();

        for(int i = nums.length - 1; i >= 0; i--){
            if(num >= nums[i]){
                result.append(helper(num / nums[i] ));
                result.append(thousands[i]);

                num %= nums[i];
            }
        }

        return result.toString().trim();

    }

    /* 1 - 999 */
    private String helper(int num){
        StringBuilder result = new StringBuilder();

        //hundren digit
        if(num >= 100){
            result.append(belowTwenty[num / 100]);
            result.append(" Hundred ");
            num %= 100;
        }

        //[20, 99]
        if(num >= 20 ){
            int tenDigit = num / 10;
            result.append(tens[tenDigit]);
            result.append(" ");

            num -= tenDigit * 10;
        }

        //[1, 19]
        if( num > 0 ){
            result.append(belowTwenty[num]);
            result.append(" ");
        }

        return result.toString();
    }



    @Test
    public void test(){

        Assert.assertEquals("Zero", numberToWords(0));

        Assert.assertEquals("One", numberToWords(1));

        Assert.assertEquals("Ten", numberToWords(10));

        Assert.assertEquals("Twenty", numberToWords(20));

        Assert.assertEquals("One Hundred", numberToWords(100));

        Assert.assertEquals("One Thousand", numberToWords(1000));

        Assert.assertEquals("Ten Thousand", numberToWords(10000));

        Assert.assertEquals("One Hundred Thousand", numberToWords(100000));

        //Input: 123
        //Output: "One Hundred Twenty Three"
        Assert.assertEquals("One Hundred Twenty Three", numberToWords(123));

        //Input: 12345
        //Output: "Twelve Thousand Three Hundred Forty Five"
        Assert.assertEquals("Twelve Thousand Three Hundred Forty Five", numberToWords(12345));

        //Input: 1234567
        //Output: "One Million Two Hundred Thirty Four Thousand Five Hundred Sixty Seven"
        Assert.assertEquals("One Million Two Hundred Thirty Four Thousand Five Hundred Sixty Seven", numberToWords(1234567));

        //Input: 1234567891
        //Output: "One Billion Two Hundred Thirty Four Million Five Hundred Sixty Seven Thousand Eight Hundred Ninety One"
        Assert.assertEquals("One Billion Two Hundred Thirty Four Million Five Hundred Sixty Seven Thousand Eight Hundred Ninety One", numberToWords(1234567891));
    }

}
