package bitwise;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 *  Leetcode 1178
 *
 * With respect to a given puzzle string, a word is valid if both the following conditions are satisfied:
 *   word contains the first letter of puzzle.
 *   For each letter in word, that letter is in puzzle.
 *
 * For example, if the puzzle is "abcdefg", then valid words are "faced", "cabbage", and "baggage";
 * while invalid words are "beefed" (doesn't include "a") and "based" (includes "s" which isn't in the puzzle).
 *
 * Return an array answer, where answer[i] is the number of words in the given word list words that are valid with respect to the puzzle puzzles[i].
 *
 *
 * Example :
 *
 * Input:
 * words = ["aaaa","asas","able","ability","actt","actor","access"],
 * puzzles = ["aboveyz","abrodyz","abslute","absoryz","actresz","gaswxyz"]
 * Output: [1,1,3,2,4,0]
 * Explanation:
 * 1 valid word for "aboveyz" : "aaaa"
 * 1 valid word for "abrodyz" : "aaaa"
 * 3 valid words for "abslute" : "aaaa", "asas", "able"
 * 2 valid words for "absoryz" : "aaaa", "asas"
 * 4 valid words for "actresz" : "aaaa", "asas", "actt", "access"
 * There're no valid words for "gaswxyz" cause none of the words in the list contains letter 'g'.
 *
 *
 * Constraints:
 *
 * 1 <= words.length <= 10^5
 * 4 <= words[i].length <= 50
 * 1 <= puzzles.length <= 10^4
 * puzzles[i].length == 7
 * words[i][j], puzzles[i][j] are English lowercase letters.
 * Each puzzles[i] doesn't contain repeated characters.
 *
 *
 */

public class ValidWordsForPuzzles {

    @Test public void test(){
        String[] words = {"aaaa","asas","able","ability","actt","actor","access"};
        String[] puzzles = {"aboveyz","abrodyz","abslute","absoryz","actresz","gaswxyz"};

        String expects = "[1, 1, 3, 2, 4, 0]";

        Assert.assertEquals(expects, findNumOfValidWords_bruteforce(words, puzzles).toString());
        Assert.assertEquals(expects, findNumOfValidWords_trie(words, puzzles).toString());
    }

    /**
     *
     *  rule 1:  word contains puzzle's first letter
     *  rule 2:  every letter in word, should be in puzzle
     *
     *  every puzzle and word is from the 26 lowercase letters,
     *  if bit-mask word and puzzle,
     *  rule 1:  word_code & ( 1 << (puzzle.charAt[0] - 'a') ) > 0
     *  rule 2:  ( word_code | puzzle_code ) == puzzle_code
     *
     *
     *  S1, brute force
     *  to every puzzle,
     *    to every words
     *       count if rule1 and rule2 both are matched
     *
     *  the time complexity is O(n * m)  10^4 * 10^5
     *
     *  S2, optimize
     *    pre-handle words, total it's 10^5 words
     *    1) from rule 2, it just need care about the distinct letters, that is from the 26 letter. no need on letter's sequence.
     *    2) the puzzle's length is 7, it means the max distinct letters in puzzle is 7.
     *    so total the "valid" possible is C(26, 7) = (26 * 25 * 24 * 23 * 22 * 21) / (7 * 6 * 5 * 4 * 3 * 2 )  it's below 2000.
     *
     *    example, the words of "acad" and 'addc", the word_code both are 1101. it can store in map<1101, 2>
     *         and if there is a word, "ca", the word_code is 101, it can store in a trie.
     *
     *
     */


    public List<Integer> findNumOfValidWords_bruteforce(String[] words, String[] puzzles) {
        int wn = words.length;
        int pn = puzzles.length;

        int[] wCodes = new int[wn];
        for(int i = 0; i < wn; i++){
            wCodes[i] = hashcode(words[i]);
        }

        List<Integer> result = new ArrayList<>(pn);
        int count;
        int pcode;
        int x;
        for(int i = 0; i < pn; i++){
            count = 0;
            x = ( 1 << (puzzles[i].charAt(0) - 'a'));
            pcode = hashcode(puzzles[i]);

            for(int j = 0; j < wn; j++){
                //rule 1 && rule 2
                if( (wCodes[j] & x) > 0 && ((wCodes[j] | pcode) == pcode)){
                    count++;
                }
            }
            result.add(count);
        }

        return result;
    }

    private int hashcode(String s){
        int r = 0;

        for(char c : s.toCharArray()){
            r |= ( 1 << (c - 'a') );
        }

        return r;
    }


    /********************/


    public List<Integer> findNumOfValidWords_trie(String[] words, String[] puzzles) {
        Node trie = new Node();
        int wCode;
        for(int i = 0, wn = words.length; i < wn; i++){
            wCode = hashcode(words[i], 7);

            if(wCode != -1){
                add(trie, wCode);
            }
        }

        List<Integer> result = new LinkedList<>();
        int pcode;
        int x;
        for(int i = 0, pn = puzzles.length; i < pn; i++){
            x = ( 1 << (puzzles[i].charAt(0) - 'a'));
            pcode = hashcode(puzzles[i], 7);

            result.add(get(trie, pcode, x));
        }

        return result;
    }

    private int get(Node node, int puzzleCode, int include){
        int sum = 0;

        if ((node.state & include) > 0) {
            sum += node.count;
        }

        //dfs
        for(int i = 0; i < 26; i++) {
            if ( node.next[i] != null && (puzzleCode & (1 << i)) > 0) {
                sum += get( node.next[i], puzzleCode, include);
            }
        }

        return sum;
    }

    private void add(Node node, int wordCode) {
        int i = 0;
        int code = wordCode;
        while(code > 0){
            if( (code & 1) == 1){
                if(node.next[i] == null){
                    node.next[i] = new Node();
                }
                node = node.next[i];
            }

            code >>>= 1;
            i++;
        }

        node.state = wordCode;
        node.count++;
    }

    class Node{
        Node[] next = new Node[26];
        int state;
        int count = 0;
    }

    private int hashcode(String s, int maxLength){
        int r = 0;
        int x;
        for(char c : s.toCharArray()){
            x = ( 1 << (c - 'a') );
            if( (r & x) == 0){
                maxLength--;

                if(maxLength == -1){
                    return -1;
                }

                r |= x;
            }
        }

        return r;
    }
}
