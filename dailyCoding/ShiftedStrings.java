package fgafa.dailyCoding;

import org.junit.Assert;
import org.junit.Test;

/**
 *
 * Given two strings A and B, return whether or not A can be shifted some number of times to get B.
 *
 * For example, if A is abcde and B is cdeab, return true. If A is abc and B is acb, return false.
 *
 * Tags: goo
 *
 * Thoughts:
 *    abcde -> cdeab, true
 *    abc -> acb, false
 *    abc -> abc, ? true
 *
 *    abcde -> cdeab -> bcdea -> deabc, true
 */

public class ShiftedStrings {

    /**
     *   Time O(n*n)
     */
    public boolean isShifted(String from, String to){
        if(from == null){
            return to == null;
        }

        if(to == null || from.length() != to.length()){
            return false;
        }

        int size = from.length();

        for(int i = 0, j = size ; i < size; i++, j--){
            if(from.substring(0, i).equals(to.substring(j)) && from.substring(i).equals(to.substring(0, j))){
                return true;
            }
        }

        return false;
    }

    /**
     *   Time O(n), Space O(1)
     */
    public boolean isShifted_n(String from, String to){
        if(from == null){
            return to == null;
        }

        if(to == null || from.length() != to.length()){
            return false;
        }

        int hashcode1 = hash(from);
        int hashcode2 = hash(to);
        if(hashcode1 == hashcode2 && from.equals(to)){
            return true;
        }

        int hashcode1Left = 0;
        int hashcode1Right = hashcode1;
        int hashcode2Left = hashcode2;
        int hashcode2Right = 0;

        int size = from.length();
        for(int i = 0, j = size - 1; i < size; i++, j--){
            hashcode1Left = hash(hashcode1Left) + from.charAt(i);
            hashcode1Right = hashcode1Right - hash(from.charAt(i), j);

            hashcode2Left = reverseHash(hashcode2Left - to.charAt(j));
            hashcode2Right = hashcode2Right + hash(to.charAt(j), i);

            if(hashcode1Left == hashcode2Right && hashcode1Right == hashcode2Left && from.substring(0, i + 1).equals(to.substring(j)) && from.substring(i + 1).equals(to.substring(0, j))){
                return true;
            }

        }

        return false;
    }

    private int hash(String str){
        int hashcode = 0;
        for(char c : str.toCharArray()){
            hashcode = hash(hashcode) + c;
        }

        return hashcode;
    }

    private int hash(int hashcode, int times){
        while(times > 0){
            hashcode = hash(hashcode);
            times--;
        }

        return hashcode;
    }

    private int hash(int hashcode){
        return hashcode << 5; // hashcode * 32
    }

    private int reverseHash(int hashcode){
        return hashcode >> 5;
    }

    /**
     *   Time O(n), Space O(1)
     *
     *   Note, XOR vs hash
     */
    public boolean isShifted_XOR(String from, String to) {
        if (from == null) {
            return to == null;
        }

        if (to == null || from.length() != to.length()) {
            return false;
        }

        int xor1= xor(from);
        int xor2 = xor(to);
        if (xor1 == xor2 && from.equals(to)) {
            return true;
        }

        int xor1Left = 0;
        int xor1Right = xor1;
        int xor2Left = xor2;
        int xor2Right = 0;

        int size = from.length();
        for(int i = 0, j = size - 1; i < size; i++, j--){
            xor1Left ^= from.charAt(i);
            xor1Right ^= from.charAt(i);
            xor2Left ^= to.charAt(j);
            xor2Right ^= to.charAt(j);

            if(xor1Left == xor2Right && xor1Right == xor2Left && from.substring(0, i + 1).equals(to.substring(j)) && from.substring(i + 1).equals(to.substring(0, j))){
                return true;
            }
        }

        return false;
    }

    private int xor(String str){
        int result = 0;
        for(char c : str.toCharArray()){
            result ^= c;
        }

        return result;
    }

    @Test public void test(){

        Assert.assertTrue(isShifted("abcde", "cdeab"));
        Assert.assertTrue(isShifted_n("abcde", "cdeab"));
        Assert.assertTrue(isShifted_XOR("abcde", "cdeab"));

        Assert.assertTrue(isShifted("abc", "abc"));
        Assert.assertTrue(isShifted_n("abc", "abc"));
        Assert.assertTrue(isShifted_XOR("abc", "abc"));

        Assert.assertFalse(isShifted("abc", "acb"));
        Assert.assertFalse(isShifted_n("abc", "acb"));
        Assert.assertFalse(isShifted_XOR("abc", "acb"));

    }
}
