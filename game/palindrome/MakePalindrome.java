package fgafa.game.palindrome;

import org.junit.Assert;
import org.junit.Test;

/**
 * Given a string which we can delete at most k, return whether you can make a palindrome.
 * For example, given 'waterrfetawx' and a k of 2, you could delete f and x to get 'waterretaw'.
 *
 * Tags: Google
 *
 */

public class MakePalindrome {

    public boolean makePalindrome(String str, int deleteLimit){
        if(null == str ){
            return false;
        }

        return makePalindrome(str, 0, str.length() - 1, deleteLimit);
    }


    private boolean makePalindrome(String str, int left, int right, int deleteLimit){
        if(deleteLimit < 0){
            return false;
        }

        for( ; left < right; left++, right-- ){
            if(str.charAt(left) != str.charAt(right)){
                deleteLimit--;
                return makePalindrome(str, left, right - 1, deleteLimit) || makePalindrome(str, left + 1, right, deleteLimit);
            }
        }

        return true;
    }

    @Test
    public void test(){

        Assert.assertTrue(makePalindrome("abc", 2));
        Assert.assertTrue(makePalindrome("waterrfetawx", 2));

        Assert.assertFalse(makePalindrome("abcd", 2));
    }

}
