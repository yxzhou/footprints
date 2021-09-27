package math;

import java.util.HashMap;
import java.util.Map;

/**
 * Roman number
 *
 * 1 - I 11 - XI 2 - II	12 - XII 3 - III	13 - XIII 4 - IV	14 - XIV 5 - V	15 - XV
 * 6 - VI 16 - XVI 7 - VII	17 - XVII 8 - VIII	18 - XVIII 9 - IX	19 - XIX 10 - X
 * 20 - XX
 *
 * 30 - XXX 40 - XL 90 - XC	100 - C 41 - XLI	51 - LI	91 - XCI	500 - D 42 - XLII
 * 52 - LII	92 - XCII	1000 - M 43 - XLIII 44 - XLIV 45 - XLV 46 - XLVI 47 -
 * XLVII 48 - XLVIII 49 - XLIX 50 - L
 *
 */
public class RomanNumber {

    /**
     * Given an integer, convert it to a roman numeral.
     *
     * Input is guaranteed to be within the range from 1 to 3999.
     */
    public String intToRoman_n(int n) {
        if (n < 1 || n > 3999) {
            return "";
        }

        int[] nums = {1000, 900, 500, 400, 100, 90, 50, 40, 10, 9, 5, 4, 1};
        String[] romains = {"M", "CM", "D", "CD", "C", "XC", "L", "XL", "X", "IX", "V", "IV", "I"};
        StringBuilder result = new StringBuilder();

        for (int i = 0; i < nums.length && n > 0; i++) {
            while (n >= nums[i]) {
                n -= nums[i];
                result.append(romains[i]);
            }
        }

        return result.toString();
    }

    public String intToRoman(int n) {
        if(n > 3999 || n < 1){
            return "";
        }
        
        String[][] map = new String[][]{ 
            {"", "I", "II", "III", "IV", "V", "VI", "VII", "VIII", "IX"} //units
          , {"", "X", "XX", "XXX", "XL", "L", "LX", "LXX", "LXXX", "XC"} //tens
          , {"", "C", "CC", "CCC", "CD", "D", "DC", "DCC", "DCCC", "CM"} //hundreds
          , {"", "M", "MM", "MMM"}  //thousands
        };

        StringBuilder result = new StringBuilder();
        for( int i = 0; n > 0; i++){
            result.insert(0, map[i][n % 10]);
            n /= 10;
        }

        return result.toString();
    }
    
    /**
     * Given a roman numeral, convert it to an integer.
     *
     * Input is guaranteed to be within the range from 1 to 3999.
     */
    public int romanToInt_n(String s) {
        if (null == s || 0 == s.length()) {
            return 0;
        }
        
        Map<Character, Integer> map = new HashMap<>();
        map.put('I', 1);
        map.put('V', 5);
        map.put('X', 10);
        map.put('L', 50);
        map.put('C', 100);
        map.put('D', 500);
        map.put('M', 1000);

        int ret = map.get(s.charAt(0));
        for (int i = 1; i < s.length(); i++) {
            ret += map.get(s.charAt(i));
            if (map.get(s.charAt(i)) > map.get(s.charAt(i - 1))) {
                ret -= (map.get(s.charAt(i - 1)) << 1);
            }
        }

        return ret;
    }

    /**
     * @param s Roman representation
     * @return an integer
     */
    public int romanToInt_1(String s) {
        if (null == s || 0 == s.length()) {
            return 0;
        }

        Map<Character, Integer> map = new HashMap<>();
        map.put('I', 1);
        map.put('V', 5);
        map.put('X', 10);
        map.put('L', 50);
        map.put('C', 100);
        map.put('D', 500);
        map.put('M', 1000);
        
        int result = 0;
        int pre = 0;
        int curr;
        for (char c : s.toCharArray()) {
            curr = map.get(c);
            result += curr;

            if (pre < curr) {
                result -= (pre << 1);
            }

            pre = curr;
        }

        return result;
    }

    public int romanToInt_2(String s) {
        //if(s == null){ }
        
        Map<Character, Integer> map = new HashMap<>();
        map.put('I', 1);
        map.put('V', 5);
        map.put('X', 10);
        map.put('L', 50);
        map.put('C', 100);
        map.put('D', 500);
        map.put('M', 1000);
        
        int r = 0;
        int pre = map.get(s.charAt(0));
        int curr;
        for(int i = 1, len = s.length(); i < len; i++){
            curr = map.get(s.charAt(i));
            if(pre < curr){
                r -= pre;
            }else{
                r += pre;
            }

            pre = curr;
        }

        return r + pre;
    }    

    public static void main(String[] args) {
        RomanNumber sv = new RomanNumber();

        String[] romans = {
            "XCIX", //99
            "XCVIII", //98
            "XCVII", //97 
            "XCVI", //96
            "LXXXIX", //89
            "LXXXIV", //84 
            "LIX", //59
            "XXXIX", //39
            "I" //1   
        };

        for (String roman : romans) {
            System.out.println(sv.romanToInt_2(roman) + " - " + sv.romanToInt_n(roman) + " - " + roman);
        }

        System.out.println();

        int[] ints = {99, 98, 97, 96, 89, 84, 59, 39, 1};
        for (int num : ints) {
            System.out.println(num + " - " + sv.intToRoman(num));
        }
    }

}
