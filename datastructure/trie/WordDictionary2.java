package datastructure.trie;

import org.junit.Assert;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

/**
 * 
 * Design a data structure that supports the following two operations: addWord(word) and search(word)

    search(word) can search a literal word or a regular expression string containing only letters a-z or ..

    A . means it can represent any one letter.

    Example
    addWord("bad")
    addWord("dad")
    addWord("mad")
    search("pad")  // return false
    search("bad")  // return true
    search(".ad")  // return true
    search("b..")  // return true
    Note
    You may assume that all words are consist of lowercase letters a-z.
 *
 *
 * Solutions:  HashMap the abbr format
 *
 * Only works when to search the word, that format must be "ab...cde" or "...ab" or "ab.."
 *
 */

public class WordDictionary2 {
    Set<String> words;

    /**
     * Initialize your data structure here.
     */
    public WordDictionary2() {
        this.words = new HashSet<>();
    }

    /**
     * Adds a word into the data structure.
     */
    public void addWord(String word) {
        if (words.contains(word)) {
            return;
        }

        words.add(word);

        String part;
        int len = word.length();

        for (int w = 1; w <= len; w++) {
            for (int i = 0; i <= len - w; i++) {
                part = word.substring(0, i) + String.valueOf(w) + word.substring(i + w);
                words.add(part);
            }
        }

    }

    /**
     * Returns if the word is in the data structure. A word could contain the dot character '.' to represent any one
     * letter. the format must be "ab...cde" or "...ab" or "ab.."
     */
    public boolean search(String word) {
        StringBuilder result = new StringBuilder();
        int count = 0;
        for (char c : word.toCharArray()) {
            if (count == 0 && c != '.') {
                result.append(c);
            } else if (c == '.') {
                count++;
            } else { //count != 0 && c != '.'
                if (count > 0) {
                    result.append(count);
                    count = -1;
                }

                result.append(c);
            }
        }

        if (count > 0) {
            result.append(count);
        }

        return words.contains(result.toString());
    }

    /**
     * *   **
     */
    @Test
    public void test() {

        WordDictionary2 sv = new WordDictionary2();

        sv.addWord("bad");
        sv.addWord("dad");
        sv.addWord("mad");

        Assert.assertFalse(sv.search("pad"));
        Assert.assertTrue(sv.search("bad"));
        Assert.assertTrue(sv.search(".ad"));

        Assert.assertTrue(sv.search("b.."));

        Assert.assertTrue(sv.search("..."));
        Assert.assertFalse(sv.search(".."));

        //["WordDictionary","addWord","addWord","addWord","addWord","addWord","addWord","addWord","addWord","search","search","search","search","search","search","search","search","search","search"]
        //[[],["ran"],["rune"],["runner"],["runs"],["add"],["adds"],["adder"],["addee"],["r.n"],["ru.n.e"],["add"],["add."],["adde."],[".an."],["...s"],["....e."],["......."],["..n.r"]]
    }
}


