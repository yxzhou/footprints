package dfsbfs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import util.Misc;

/**
 * 
 * An abbreviation of a word follows the form <first letter><number><last letter>. 
 * Below are some examples of word abbreviations:
    a) it                      --> it    (no abbreviation)
    
         1
    b) d|o|g                   --> d1g
    
                  1    1  1
         1---5----0----5--8
    c) i|nternationalizatio|n  --> i18n
    
                  1
         1---5----0
    d) l|ocalizatio|n          --> l10n
    
    Assume you have a dictionary and given a word, find whether its abbreviation is unique in the dictionary. 
    A word's abbreviation is unique if no other word from the dictionary has the same abbreviation.
    Example: 
    Given dictionary = [ "deer", "door", "cake", "card" ]
    
    isUnique("dear") -> false
    isUnique("cart") -> true
    isUnique("cane") -> false
    isUnique("make") -> true
 *
 */

/*
 * Think more:
 * 
 * 1) in the above case, 
 *    isUnique("cake")?  true, because to "c2e", it's "cake" in the dictionary only,  no other word. 
 *    isUnique("deer")?  false, because to "d2r",  it's "deer" and "door", "door" is the other one. 
 *    isUnique("dear")?  false, because to "d2r",  it's "deer" and "door"
 *    isUnique("cane")?  false, because to "c2e",  it's "cake", 
 *    isUnique("cart")?  true,  because to "c2t",  no one in the dictionary
 *    
 * so: 
 *   if the abbr is not in the dictionary, return true;  (case#1)
 *   or, if the abbr is in, 
 *      it's the word only in, return true
 *      it's the word not in,  return false
 *      it's multiple word in, return false
 *
 */

public class UniqueAbbreviation {

    private Map<String, String> abbrDict; //Map<abbr,  word>, word is null when duplicated.
 
    public UniqueAbbreviation() {
        
    }
    
    public UniqueAbbreviation(String[] dictionary) {
        
        abbrDict = new HashMap<String, String>();
        
        String abbr;
        for(String word : dictionary){
            abbr = getAbbr(word);
            
            if(abbrDict.containsKey(abbr)){
                abbrDict.put(abbr, null);
            }else{
                abbrDict.put(abbr, word);
            }
        }
        
    }
    
    public boolean isUnique(String word) {
        if (word == null || word.length() == 0) {
            return true;
        }
         
        String abbr = getAbbr(word);
        if (!abbrDict.containsKey(abbr) || word.equals(abbrDict.get(abbr))) {
            return true;
        } else {
            return false;
        }
    }
     
    private String getAbbr(String word) {
        if (word == null || word.length() < 3) {
            return word;
        }
         
        StringBuffer sb = new StringBuffer();
        sb.append(word.charAt(0));
        sb.append(word.length() - 2);
        sb.append(word.charAt(word.length() - 1));
         
        return sb.toString();
 
    }
    
    
    /**
     * Write a function to generate the generalized abbreviations of a word.
     * 
        Example:
        Given word = "word", return the following list (order does not matter):
        ["word", "1ord", "w1rd", "wo1d", "wor1", "2rd", "w2d", "wo2", "1o1d", "1or1", "w1r1", "1o2", "2r1", "3d", "w3", "4"]
     */
    /*
     * Think more:
     *   how about "all" ?? ["all", "1ll", "2l", "3", "1l1", "a1l","a2","al1"] ?
     */
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
    
    public List<String> getAllAbbreviations_binary(String word){
        
        List<String> result = new ArrayList<>();
        if(null == word || 0 == word.length()){
            return result;
        }
        
        long n = (1 << word.length());
        String binaryString;
        for(long i = 0; i < n; i++ ){
            binaryString = Long.toBinaryString(i);
            result.add(build(word, binaryString));
        }
        
        return result;
    }
    
    private String build(String word, String binaryString){
        StringBuilder sb = new StringBuilder();
        int count = 0;
        
        int diff = word.length() - binaryString.length();
        for(int i = 0; i < diff; i++  ){
            sb.append(word.charAt(i));
        }
        
        for( int i = 0, j = diff; j < word.length(); i++, j++){
            if(binaryString.charAt(i) == '1'){
                count++;
            }else{
                if(count > 0){
                    sb.append(count);
                    count = 0;
                }
                
                sb.append(word.charAt(j));
            }
        }
        
        if(count > 0){
            sb.append(count);
        }
        
        return sb.toString();
    }
    
    public static void main(String[] args){
        
        UniqueAbbreviation sv = new UniqueAbbreviation();
        
        String[] words = {"a", "as", "all", "word"};
        
        for(String word : words){
            System.out.println(String.format("\nInput: %s, Output:", word));
            
            Misc.printArrayList(sv.getAllAbbreviations(word));
            
            Misc.printArrayList(sv.getAllAbbreviations_binary(word));
        }

    }
}
