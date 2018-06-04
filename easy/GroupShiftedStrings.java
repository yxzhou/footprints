package fgafa.easy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fgafa.util.Misc;

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
 */

public class GroupShiftedStrings {

    public List<List<String>> groupStrings(String[] strings) {
        List<List<String>> result = new ArrayList<List<String>>();
        
        //check
        if(null == strings || 0 == strings.length){
            return result;
        }
        
        //group by the code
        Map<String, List<String>> groups = new HashMap<>();
        String code;
        List<String> group;
        for(String s : strings){
            code = groupCode(s);
            
            group = groups.get(code);
            if(null == group){
                group = new ArrayList<>();
                groups.put(code, group);
            }
            group.add(s);
        }
        
        //re-organize and return
        result.addAll(groups.values());
        
        return result;
    }
    
    private String groupCode(String s){
        StringBuilder sb = new StringBuilder();
        
        for(int i = 1; i < s.length(); i++){
            sb.append( (s.charAt(i) - s.charAt(i - 1) + 26) % 26 );
            sb.append( '#' );
        }
        
        return sb.toString();
    }
    
    public static void main(String[] args){
        
        String[][] input = {
                    {"abc", "bcd", "acef", "xyz", "az", "ba", "a", "z"}
        };
        
        GroupShiftedStrings sv = new GroupShiftedStrings();
        
        for(int i = 0; i < input.length; i++){
            System.out.println(String.format(" Input: %s", Misc.array2String(input[i])));
            System.out.println("Output:");
            Misc.printListList(sv.groupStrings(input[i]));
        }
    }
    
}
