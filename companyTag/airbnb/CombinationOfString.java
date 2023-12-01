/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package companyTag.airbnb;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.apache.commons.collections.CollectionUtils;
import org.junit.Assert;
import util.Misc;

/**
 * Find all the combinations of a string in lowercase and uppercase. For example, string "ab" >>> "ab", "Ab", "aB",
 * "AB". So, you will have 2^n (n = number of chars in the string) output strings.
 *
 */
public class CombinationOfString {

    public List<String> strComb(String text) {
        if (text == null) {
            return Collections.EMPTY_LIST;
        }

        List<String> result = new ArrayList<>();
        
        int n = text.length();
        char[][] cases = new char[n][2];
        char c;
        for(int i = 0; i < n; i++){
            c = text.charAt(i);
            cases[i][0] = Character.toLowerCase(c);
            cases[i][1] = Character.toUpperCase(c);
        }
        
        char[] arr = new char[n];
        for(int i = 0, m = (1 << n); i < m;  i++){
            
            for(int j = n - 1, k = i; j >= 0; j--, k >>= 1){                
                arr[j] = cases[j][k & 1];
            }
            
            result.add(String.valueOf(arr));
        }
        
        return result;
    }

    
    public static void main(String[] args){
        
        String[][][] inputs = {
            {
                {"ab"},
                {"ab", "Ab", "aB", "AB"}
            },
            {
                {"aB"},
                {"ab", "Ab", "aB", "AB"}
            }

        };
        
        CombinationOfString sv = new CombinationOfString();
        
        for(String[][] input : inputs){
            System.out.println(String.format("\ntext: %s, \n%s", input[0][0], Misc.array2String(sv.strComb(input[0][0])) ));
            
            Assert.assertTrue(" ", CollectionUtils.isEqualCollection(Arrays.asList(input[1]), sv.strComb(input[0][0]) ));
        }
        
    }
}
