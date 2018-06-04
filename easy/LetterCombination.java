package fgafa.easy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import fgafa.util.Misc;

/**
 * Given a digit string, return all possible letter combinations that the number
 * could represent. A mapping of digit to letters (just like on the telephone
 * buttons) is given below. 
 * 
 * Input:Digit string "23" Output: ["ad", "ae", "af", "bd", "be", "bf", "cd", "ce", "cf"].
 */

public class LetterCombination {

    private static final HashMap<Integer, String> htPhoneNumber = new HashMap<Integer, String>();
    static {
        htPhoneNumber.put(2, "abc");
        htPhoneNumber.put(3, "def");
        htPhoneNumber.put(4, "ghi");
        htPhoneNumber.put(5, "jkl");
        htPhoneNumber.put(6, "mno");
        htPhoneNumber.put(7, "pqrs");
        htPhoneNumber.put(8, "tuv");
        htPhoneNumber.put(9, "wxyz");
        htPhoneNumber.put(0, " ");
    }

    public ArrayList<String> letterCombinations(String digits) {
        ArrayList<String> result = new ArrayList<String>();
        result.add(""); //

        // check
        if (digits == null || digits.length() == 0) {
            return result;
        }

        Integer digit;
        String letters = "";
        int len;
        for (int i = 0; i < digits.length(); i++) {
            digit = digits.charAt(i) - 48; // '0' is 48

            letters = htPhoneNumber.get(digit);
            len = result.size();

            String tmp;
            for (int j = 0; j < len; j++) {
                tmp = result.get(0);
                for (int k = 0; k < letters.length(); k++) {
                    result.add(tmp + letters.charAt(k));
                }

                result.remove(0);
            }

        }

        return result;
    }

    public List<String> letterCombinations_2(String digits) {
        List<String> ret = new LinkedList<String>();
        if (null == digits || 0 == digits.length()) {
            return ret;
        }

        String currStr;
        String letters = "";
        ret.add("");
        for (char digit : digits.toCharArray()) {
            letters = htPhoneNumber.get(digit - 48);

            for (int currLength = ret.size(); currLength > 0; currLength--) {
                currStr = ret.remove(0);

                for (char c : letters.toCharArray()) {
                    ret.add(currStr + c);
                }
            }
        }

        return ret;
    }

    public List<String> letterCombinations_3(String digits) {
        List<String> res = new ArrayList<String>();
        if (digits == null || digits.length() == 0) {
            return res;
        }

        String currStr;
        String letters = "";
        res.add("");
        for (char digit : digits.toCharArray()) {
            letters = getLetters(digit);
            List<String> newRes = new ArrayList<String>();
            for (int j = 0; j < res.size(); j++) {
                currStr = res.get(j);
                for (char c : letters.toCharArray()) {
                    newRes.add(currStr + c);
                }
            }
            res = newRes;
        }
        return res;
    }

    public List<String> letterCombinations_n(String digits) {
        List<String> result = new ArrayList<String>();
        // check
        if (null == digits || 0 == digits.length()) {
            return result;
        }

        char[] chars;
        List<String> tmp;
        result.add("");
        for (char digit : digits.toCharArray()) {
            tmp = new ArrayList<String>();

            chars = getLetters(digit).toCharArray();
            for (String str : result) {
                for (char c : chars) {
                    tmp.add(str + c);
                }
            }

            result = tmp;
        }

        return result;
    }

    private String getLetters(char digit) {
        switch (digit) {
            case '2':
                return "abc";
            case '3':
                return "def";
            case '4':
                return "ghi";
            case '5':
                return "jkl";
            case '6':
                return "mno";
            case '7':
                return "pqrs";
            case '8':
                return "tuv";
            case '9':
                return "wxyz";
            case '0':
                return " ";
            default:
                return "";
        }
    }

    public ArrayList<String> letterCombinations_recursive(String digits) {
        ArrayList<String> result = new ArrayList<String>();

        // check
        if (digits == null || digits.length() == 0) {
            result.add("");
            return result;
        }

        letterCombinations_recursive("", digits, 0, result);

        return result;
    }

    private void letterCombinations_recursive(String prefix,
                                              String digits,
                                              int index,
                                              ArrayList<String> result) {

        // finish recurrence
        if (index == digits.length()) {
            result.add(prefix);
            return;
        }

        // combination
        String letters = htPhoneNumber.get(digits.charAt(index) - 48);
        for (char ch : letters.toCharArray()) {
            letterCombinations_recursive(prefix + ch, digits, index + 1, result);
        }

    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        LetterCombination sv = new LetterCombination();
        String[] input = { null, "", "2", "23" };

        for (String digits : input) {
            System.out.println("\n Input: " + digits);

            ArrayList<String> result = sv.letterCombinations(digits);
            Misc.printArrayList(result);

            List<String> result2 = sv.letterCombinations_2(digits);
            Misc.printArrayList(result2);

            List<String> result3 = sv.letterCombinations_3(digits);
            Misc.printArrayList(result3);
        }

    }

}
