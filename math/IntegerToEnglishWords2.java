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

public class IntegerToEnglishWords2 {
    String[] belowTwenty = {"","One", "Two", "Three", "Four", "Five", "Six", "Seven", "Eight", "Nine", "Ten", "Eleven", "Twelve", "Thirteen", "Fourteen", "Fifteen", "Sixteen", "Seventeen", "Eighteen", "Nineteen"};

    String[] Tens = {"", "", "Twenty", "Thirty", "Forty", "Fifty", "Sixty", "Seventy", "Eighty", "Ninety"};

    public String numberToWords(int num) {
        //assume it's non-negative

        if(num == 0){
            return "Zero";
        }

        return helper(num).trim();

    }

    private String helper(int n){
        //[0, 20)
        if(n < 20){
            return belowTwenty[n];
        }

        //[20, 100)
        if(n < 100){
            if(n % 10 == 0){
                return Tens[n / 10];
            }else{
                return Tens[n / 10] + " " + belowTwenty[n % 10];
            }
        }

        //[100, 1000)
        if(n < 1000){
            if(n % 100 == 0){
                return belowTwenty[n / 100] + " Hundred";
            }else {
                return belowTwenty[n / 100] + " Hundred " + helper(n % 100);
            }

        }

        //[1000, 1000_000)
        if(n < 1000_000){
            return helper(n / 1000) + " Thousand " + helper(n % 1000);
        }

        if(n < 1000_000_000){
            return helper(n / 1000_000) + " Million " + helper(n % 1000_000);
        }

        return helper(n / 1000_000_000) + " Billion " + helper(n % 1000_000_000);
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
