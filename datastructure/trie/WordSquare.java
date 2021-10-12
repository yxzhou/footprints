/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datastructure.trie;

/**
 *
 * Given a sequence of words, check whether it forms a valid word square.
 * A sequence of words forms a valid word square if the k^th row and column read the exact same string, where 0 ≤ k < max(numRows, numColumns).
 * 
 * Notes:
 *   The number of words given is at least 1 and does not exceed 500.
 *   Word length will be at least 1 and does not exceed 500.
 *   Each word contains only lowercase English alphabet a-z.
 *   
 * Example1
 * Input:  
 * [
 *   "abcd",
 *   "bnrt",
 *   "crmy",
 *   "dtye"
 * ]
 * Output: true
 * Explanation:
 *   The first row and first column both read "abcd".
 *   The second row and second column both read "bnrt".
 *   The third row and third column both read "crmy".
 *   The fourth row and fourth column both read "dtye".
 * Therefore, it is a valid word square.
 * 
 * Example2
 * Input:  
 * [
 *   "abcd",
 *   "bnrt",
 *   "crm",
 *   "dt"
 * ]
 * Output: true
 * Explanation:
 *   The first row and first column both read "abcd".
 *   The second row and second column both read "bnrt".
 *   The third row and third column both read "crm".
 *   The fourth row and fourth column both read "dt".
 * Therefore, it is a valid word square.
 * 
 * Example3
 * Input:  
 * [
 *   "ball",
 *   "area",
 *   "read",
 *   "lady"
 * ]
 * Output: false
 * Explanation:
 *   The third row reads "read" while the third column reads "lead".
 * Therefore, it is NOT a valid word square.
 * 
 */
public class WordSquare {
    /**
     * @param words: a list of string
     * @return: a boolean
     */
    public boolean validWordSquare(String[] words) {
        if(words == null){
            return false;
        }

        //check by dianogal 
        int m;
        for(int r = 0, n = words.length; r < n; r++){
            m = words[r].length();
            for(int c = r + 1; c < m; c++){
                //point(r, c) and point(c, r) should be same
                if(c >= n || r >= words[c].length() || words[c].charAt(r) != words[r].charAt(c)  ){
                    return false;
                }
            }

            // because point(r, m) is not existing, so point(m, r) should not existing
            if( m < n && r < words[m].length() ){
                return false;
            }
        }

        return true;
    }
}
