/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package stringmatching;

/**
 * _https://www.lintcode.com/problem/637
 * 
 * 
 * Given a non-empty string word and an abbreviation abbr, return whether the string matches with the given
 * abbreviation.
 *
 * A string such as "word" contains only the following valid abbreviations:
 * ["word", "1ord", "w1rd", "wo1d", "wor1", "2rd", "w2d", "wo2", "1o1d", "1or1", "w1r1", "1o2", "2r1", "3d", "w3", "4"]
 * Where 1 means one character is omitted, 2 means two characters are omitted, and so on. Notice that only the above
 * abbreviations are valid abbreviations of the string word. Any other string is not a valid abbreviation of word.
 * 
 * Example 1:
 * Input : s = "internationalization", abbr = "i12iz4n"
 * Output : true
 * 
 * Example 2:
 * Input : s = "apple", abbr = "a2e"
 * Output : false
 * 
 */
public class WordAbbreviationII {
    /**
     * @param word: a non-empty string
     * @param abbr: an abbreviation
     * @return true if string matches with the given abbr or false
     */
    public boolean validWordAbbreviation(String word, String abbr) {
        if(word == null || abbr == null){
            return false;
        }

        int i = 0;
        int n = word.length();
        int x = 0;
        char c;
        for( int j = 0, m = abbr.length(); j < m; j++){
            c = abbr.charAt(j);
            if( c >= 'a' && c <= 'z'  ){
                i += x;
                x = 0;

                if( i < n && word.charAt(i) == c ){
                    i++;
                }else{
                    return false;
                }
            }else{ // 
                x = x * 10 + ( c - '0' );

                if(x == 0){  //for case:  ( "a", "01" )
                    return false;
                }
            }
        }

        i += x;
        //x = 0;

        return i == n;
    }
}
