package array.anagram;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import util.Misc;
import org.junit.Test;

/**
 * 
 * Given a string, we can “shift” each of its letter to its successive letter, 
 * for example: “abc” -> “bcd”. We can keep “shifting” which forms the sequence:

    "abc" -> "bcd" -> ... -> "xyz"
    
    Given a list of strings which contains only lowercase alphabets, group all strings that belong to the same shifting sequence.
    
    For example,
    
    given: ["abc", "bcd", "acef", "xyz", "az", "ba", "a", "z"], Return:
    
    [
    ["abc","bcd","xyz"],
    ["az","ba"],
    ["acef"],
    ["a","z"]
    ]
 *
 *  Thoughts:
 *     1) to ["abc","bcd","xyz"], b - a = 1,  c - b = 1, y - x = 1, z - y = 1,
 *     1) to ["az", "ba"], z - a = 25, a - b = -1,  25 = -1 + 26.
 *     2) to ["a","z"],
 *
 */

public class ShiftedStringGroup {

    public List<List<String>> groupStrings(String[] strings) {
        List<List<String>> result = new ArrayList<>();

        if(null == strings || 0 == strings.length){
            return result;
        }
        
        //group by the code
        Map<String, List<String>> groups = new HashMap<>();
        String code;
        for(String s : strings){
            code = groupCode(s);
            
            groups.putIfAbsent(code, new ArrayList<>());
            groups.get(code).add(s);
        }
        
        result.addAll(groups.values());        
        return result;
    }
    
    private String groupCode(String s) {
        StringBuilder sb = new StringBuilder();

        for (int i = 1; i < s.length(); i++) {
            sb.append((s.charAt(i) - s.charAt(i - 1) + 26) % 26);
            sb.append('#'); // here it need '#',  for case sb.append(1).append(2) vs sb.append(12) 
        }

        return sb.toString();
    }

    private String groupCode_2(String str) {
        int n = str.length();
        char[] diffs = new char[n - 1];
        for(int i = 0, j = 1; j < n; i++, j++ ){
            diffs[i] = (char)((str.charAt(j) - str.charAt(i) + 26) % 26);
        }

        return String.valueOf(diffs);
    }
    
    @Test public void test(){
        
        String[][] input = {
                    {"abc", "bcd", "acef", "xyz", "az", "ba", "a", "z"}
        };

        
        for(int i = 0; i < input.length; i++){
            System.out.println(String.format(" Input: %s", Misc.array2String(input[i])));
            System.out.println("Output:");
            Misc.printListList(groupStrings(input[i]));
        }
    }
    
}
