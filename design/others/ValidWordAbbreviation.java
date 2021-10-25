/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package design.others;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

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
    A word's abbreviation is unique if "no other word" from the dictionary has the same abbreviation.
    Example: 
    Given dictionary = [ "deer", "door", "cake", "card" ]
    
    isUnique("dear") -> false
    isUnique("cart") -> true
    isUnique("cane") -> false
    isUnique("make") -> true
 *
 * Think more:
 * 
 * 1) in the above case, 
 *    isUnique("cake")?  true,  because to "c2e", it's "cake" in the dictionary only,  no other word. 
 *    isUnique("deer")?  false, because to "d2r",  it's "deer" and "door" in dictionary, "door" is the other word. 
 *    isUnique("dear")?  false, because to "d2r",  it's "deer" and "door" in dictionary
 *    isUnique("cane")?  false, because to "c2e",  it's "cake" in dictionary, 
 *    isUnique("cart")?  true,  because to "c2t",  no one in the dictionary
 *
 * so: 
 *  -- A word's abbreviation is unique if "no other word" from the dictionary has the same abbreviation.
 *    It means: 
 *      If the abbr is unique, it is not found in the dictionary, such as the above "cart", return true;   OR 
 *      If the abbr is found in the dictionary, 
 *         the word is unique, only the word is the only one who has the abbr in the dictionary, such as the above "cake", return true;
 *         notes: a question? how about two "cake" in the dictionary
 *      Else if the abbr is not unique,  and there is the other word who has the abbr is found in the dictionary, return false 
 *    
 *
 */
public class ValidWordAbbreviation {
    Set<String> words;
    Map<String, Integer> abbrs;

    /*
    * @param dictionary: a list of words
    */
    public ValidWordAbbreviation(String[] dictionary) {

        words = new HashSet<>();
        abbrs = new HashMap<>();

        String hashcode;
        for(String word : dictionary){
            words.add(word);

            hashcode = getAbbr(word);
            abbrs.put(hashcode, abbrs.getOrDefault(hashcode, 0) + 1 );
        }
    }

    /*
     * @param word: a string
     * @return: true if its abbreviation is unique or false
     */
    public boolean isUnique(String word) {
        // if (word == null ) {
        //     return true;
        // }

        String hashcode = getAbbr(word);
        return !abbrs.containsKey(hashcode) || ( abbrs.get(hashcode) == 1 && words.contains(word) ) ;
    }

    private String getAbbr(String word){
        int n = word.length();

        if(n < 3){
            return word;
        }

        return word.charAt(0) + String.valueOf(n - 2) + word.charAt(n - 1);
    }
}
