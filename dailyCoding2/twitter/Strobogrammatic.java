package fgafa.dailyCoding2.twitter;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.stream.Collectors;

import junit.framework.Assert;
import org.junit.Test;

/**
 *
 * This problem was asked by Twitter.
 *
 * A strobogrammatic number is a positive number that appears the same after being rotated 180 degrees. For example, 16891 is strobogrammatic.
 *
 * Create a program that finds all strobogrammatic numbers with N digits.
 *
 * Thoughts:
 *  1) strobogrammatic digits:
 *    0 -> 0
 *    1 -> 1
 *    8 -> 8
 *    6 -> 9
 *    9 -> 6
 *
 *    and 0 can not be in the first place, and the last place.
 *
 */

public class Strobogrammatic {

    final Map<Character, Character> chars = new HashMap<>();
    {
        chars.put('0','0');
        chars.put('1','1');
        chars.put('8','8');
        chars.put('6','9');
        chars.put('9','6');
    }

    public List<Integer> getAllStrobogrammatic(int n){
        if ( n < 1 ) {
            return Collections.emptyList();
        }

        Queue<char[]> result = new LinkedList<>();
        result.offer(new char[n]);

        for(int i = 0, j = n - 1; i <= j; i++, j--){
            for(int k = result.size(); k > 0; k--){
                char[] top = ((LinkedList<char[]>) result).pop();

                for(Map.Entry<Character, Character> entry : chars.entrySet()){
                    if(i < j && i == 0 && entry.getKey() == '0'){
                        continue;
                    }

                    if(i == j && entry.getKey() != entry.getValue()){
                        continue;
                    }

                    char[] curr = new char[n];
                    System.arraycopy(top, 0, curr, 0, n);
                    curr[i] = entry.getValue();
                    curr[j] = entry.getKey();

                    result.add(curr);
                }
            }
        }

        return result.stream().map(chars -> Integer.valueOf(String.valueOf(chars))).sorted().collect(Collectors.toList());
    }

    @Test
    public void test(){
        //Assert.assertEquals(Arrays.asList(0, 1, 8), getAllStrobogrammatic(1));
        Assert.assertEquals(Arrays.asList(11, 69, 88, 96), getAllStrobogrammatic(2));
        Assert.assertEquals(Arrays.asList(
                101, 111, 181,
                609, 619, 689,
                808, 818, 888,
                906, 916, 986), getAllStrobogrammatic(3));
        Assert.assertEquals(Arrays.asList(
                1001, 1111, 1691, 1881, 1961,
                6009, 6119, 6699, 6889, 6969,
                8008, 8118, 8698, 8888, 8968,
                9006, 9116, 9696, 9886, 9966), getAllStrobogrammatic(4));
    }
}
