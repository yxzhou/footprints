package dfsbfs;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import util.Misc;

/**
 * Write a function to generate the generalized abbreviations of a word.
 * 
 * Example:
 * Given word = "word", return the following list (order does not matter):
 *  ["word", "1ord", "w1rd", "wo1d", "wor1", "2rd", "w2d", "wo2", "1o1d", "1or1", "w1r1", "1o2", "2r1", "3d", "w3", "4"]
 *
 * Think more:
 *   how about "all" ?? ["all", "1ll", "2l", "3", "1l1", "a1l","a2","al1"] ?
 *   
 *   to "word", it has 16 abbrs
 *   to "all", it has 8 abbrs
 */

public class GenerateAbbreviations {

    public List<String> getAllAbbreviations(String word){
        
        List<String> result = new ArrayList<>();
        if(null == word || 0 == word.length()){
            return result;
        }
        
        getAllAbbreviations(word.toCharArray(), 0, result);
        return result;
    }
    
    private void getAllAbbreviations(char[] word, int start, List<String> result){
        
        result.add(build(word));
        
        char tmp;
        for(int i = start; i < word.length; i++){
            tmp = word[i];
            word[i] = '1';
            
            getAllAbbreviations(word, i + 1, result);
            
            word[i] = tmp;
        }
        
    }
    
    //[1,1,a] -> "2a"
    //[a,1,1] -> "a2"
    private String build(char[] word){
        StringBuilder sb = new StringBuilder();
        int count = 0;
        for(int i = 0; i < word.length; i++){
            if(word[i] >= '0' && word[i] <= '9'){
                count++;
            }else{
                if(count > 0){
                    sb.append(count);
                    count = 0;
                }
                    
                sb.append(word[i]);
            }
        }
        
        if(count > 0){
            sb.append(count);
        }
        
        return sb.toString();
    }
    
    public List<String> getAllAbbreviations_binary(String word) {
        if(word == null ){
            return Collections.EMPTY_LIST;
        }
        
        List<String> result = new ArrayList<>(); 

        int n = word.length();
        StringBuilder sb;
        int count = 0;
        for(int i = (1 << n) - 1; i >= 0; i--){
            sb = new StringBuilder();
            for(int j = 0; j < n; j++){
                if(((i >> j) & 1) == 1 ){
                    if(count > 0){
                        sb.append(count);
                        count = 0;
                    }
                    sb.append(word.charAt(j));
                }else{
                    count++;
                }
            }
            if(count > 0){
                sb.append(count);
                count = 0;
            }

            result.add(sb.toString());
        }

        return result;
    }
    
    public static void main(String[] args){
        
        GenerateAbbreviations sv = new GenerateAbbreviations();
        
        String[] words = {"a", "as", "all", "word"};
        
        for(String word : words){
            System.out.println(String.format("\nInput: %s, Output:", word));
            
            Misc.printArrayList(sv.getAllAbbreviations(word));
            
            Misc.printArrayList(sv.getAllAbbreviations_binary(word));
        }

    }
}
