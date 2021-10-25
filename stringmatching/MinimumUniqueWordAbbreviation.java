/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package stringmatching;

import java.util.BitSet;
import junit.framework.Assert;

/**
 *
 * A string such as "word" contains the following abbreviations:
 * ["word","1ord","w1rd","wo1d","wor1","2rd","w2d","wo2","1o1d","1or1","w1r1","1o2","2r1","3d","w3","4"]
 * 
 * Given a target string and a set of strings in a dictionary, find an abbreviation of this target string with the 
 * smallest possible length such that it does not conflict with abbreviations of the strings in the dictionary.
 * 
 * Each number or letter in the abbreviation is considered length = 1. For example, the abbreviation "a32bc" has length = 5.
 * 
 * Notes:
 *   In the case of multiple answers as shown in the second example below, you may return any one of them.
 *   Assume length of target string = m, and dictionary size = n. You may assume that m ≤ 21, n ≤ 200, and log2(n) + m ≤ 20.
 * 
 * Example 1:
 * Input："apple",["blade"]
 * Output："a4"
 * Explanation：Because "5" or "4e" conflicts with "blade".
 * 
 * Example 2:
 * Input："apple",["plain","amber","blade"]
 * Output："1p3"
 * Explanation：Other valid answers include "ap3", "a3e", "2p2", "3le", "3l1"
 * 
 * Thought:
 *  1) the abbreviations of "word": 
 *     "4"
 *     "w3","3d",
 *     "w2d","wo2","1o2","2r1","2rd",
 *     "word","1ord","w1rd","wo1d","wor1","1o1d","1or1","w1r1",
 * 
 *  2) how to get the minimum unique word abbreviation?     
 *    Assume the words only contain lower case letter, define 1 as the unique character, 0 as the non-unique character.          
 *      "apple" vs "blade", the common pattern is "11110", any one of unique character can make a unique abbreviation, such as "a4" and "2p2"
 *      "apple" vs "alive", the common pattern is "01110", any one of unique character can make a unique abbreviation, such as "1p3"
 *      "apple" vs ["plain","amber","bible","opper"],  
 *                   11111   01111   11100   10011      
 *      to make a unique abbreviation, 'a' is not enough, it need 'a' and 'p', such as "ap3", or 'p'and 'l' such as "2pl1" 
 *  3) total it has (1 << m) abbreviation,  find out all unique, then get one with the minimum length 
 *     
 */
public class MinimumUniqueWordAbbreviation {
    /**
     * @param target: a target string 
     * @param dictionary: a set of strings in a dictionary
     * @return: an abbreviation of the target string with the smallest possible length
     */
    public String minAbbreviation(String target, String[] dictionary) {
        int m = target.length();
        int n = dictionary.length;
        
        int count = 0;
        BitSet[] patterns = new BitSet[m];
        for(int i = 0; i < m; i++){
            patterns[i] = new BitSet(n);
        }
        
        String word;
        for(int k = 0; k < n; k++){
            word = dictionary[k];
            if(word.length() != m){
                continue;
            }
            
            count++;
            for(int i = 0; i < m; i++){
                if(target.charAt(i) != word.charAt(i)){
                    patterns[i].set(k);
                }
            }
        }
        
        if(count == 0){
            return String.valueOf(m);
        }
        
        StringBuilder result = new StringBuilder(target);
        StringBuilder curr;
        BitSet bits = new BitSet(n);
        int c = 0;
        for(int i = (1 << m) - 1; i > 0; i--){
            bits.clear();
            curr = new StringBuilder(); 
            for(int j = 0; j < m; j++){
                if(((i >> j) & 1) == 1 ){
                    if(c > 0){
                        curr.append(c);
                        c = 0;
                    }
                    curr.append(target.charAt(j));
                    
                    bits.or(patterns[j]);
                }else{
                    c++;
                }
                
            }
            
            if(c > 0){
                curr.append(c);
                c = 0;
            }
            
            //count the true in bits
            if(bits.cardinality() == count && curr.length() < result.length()){
                result = curr;
            }
                
        }
        
        return result.toString();
    }
    
//    private int bitCount(BitSet bits){
//        
//    }
    
    public static void main(String[] args) {
    
        /**
         * Test BitSet
         */
        BitSet bits = new BitSet(10);//it will use a long inside, because 64 > 10
        
        try{
            bits.set(11); // t's a long inside, 
        }catch(IndexOutOfBoundsException e){
            System.out.println( "  IndexOutOfBoundsException when set(11)");
        }
        
        try{
            bits.set(65); // because it's a long inside, and it will extends capacity inside.  IndexOutOfBoundsException only when the specified index is negative
        }catch(IndexOutOfBoundsException e){
            System.out.println( "  IndexOutOfBoundsException when set(65)");
        }
        
        bits.set(2);
        bits.set(5);
        bits.set(7);
        System.out.println("size: " + bits.size() + " \tcardinality: " + bits.cardinality()); // bits.size() is 64 instead of 10
        
        MinimumUniqueWordAbbreviation sv = new MinimumUniqueWordAbbreviation();
        
        //Assert.assertTrue(new HashSet<>(Arrays.asList("a4", "")).contains(sv.minAbbreviation("apple", new String[]{"blade"})));
        Assert.assertTrue("a4".length() == sv.minAbbreviation("apple", new String[]{"blade"}).length() );
        Assert.assertTrue("1p3".length() == sv.minAbbreviation("apple", new String[]{"plain","amber","blade"}).length() );
        
        System.out.println( " " + sv.minAbbreviation( "apple", new String[]{"plain","amber","blade", "123", "124", "125", "alive", "appee", "bbple"} ) );
        
        Assert.assertTrue("a2l1".length() == sv.minAbbreviation("apple", new String[]{"plain","amber","blade","alive", "appee", "bbple"}).length() );
        Assert.assertTrue("a2l1".length() == sv.minAbbreviation("apple", new String[]{"plain","amber","blade","123", "124", "125", "alive", "appee", "bbple"}).length() );
    }
}
