package datastructure.trie;

/**
 * Leetcode #745
 *
 * Given many words, words[i] has weight i.
 *
 * Design a class WordFilter that supports one function, WordFilter.f(String prefix, String suffix). It will return the word with given prefix and suffix with maximum weight. If no word exists, return -1.
 *
 * Examples:
 *
 * Input:
 * WordFilter(["apple"])
 * WordFilter.f("a", "e") // returns 0
 * WordFilter.f("b", "") // returns -1
 *
 *
 * Note:
 *
 * words has length in range [1, 15000].
 * For each test case, up to words.length queries WordFilter.f may be made.
 * words[i] has length in range [1, 10].
 * prefix, suffix have lengths in range [0, 10].
 * words[i] and prefix, suffix queries consist of lowercase letters only.
 *
 */

/**
 * S1: prefix trie and suffix trie, then get intersection with Set
 *
 * Time complexity O(2NK + Q(2K + N)),  Space complexity O(2NK), 2 times of the size of the tries
 *
 * S2: suffix and prefix wrapped word, prefix trie
 * Consider the word 'apple'. For each suffix of the word, we could insert that suffix, followed by '#', followed by the word, all into the trie.
 *
 * For example, we will insert '#apple', 'e#apple', 'le#apple', 'ple#apple', 'pple#apple', 'apple#apple' into the trie.
 * Then for a query like prefix = "ap", suffix = "le", we can find it by querying our trie for le#ap.
 *
 * Time complexity O(N*2K*K + Q*2K),  Space complexity O(N*2K*K),
 *
 *  * N is the number of word
 *  * K is the maximum length of a word
 *  * Q is the number of f() call
 *  * Q >> N >> K
 */
public class PrefixAndSuffixSearch {

    TrieNode head;

    public PrefixAndSuffixSearch(String[] words) {
        head = new TrieNode();

        //build suffix and prefix wrapped words
        String word;
        TrieNode curr;
        int k;
        for(int w = 0; w < words.length; w++){
            word = words[w] + "{";

            for(int i = 0, len = word.length(); i < len; i++){
                curr = head;
                curr.weight = w;

                for(int j = i, end = word.length() * 2 - 1; j < end; j++){
                    k = word.charAt(j % len) - 'a';

                    if(curr.children[k] == null){
                        curr.children[k] = new TrieNode();
                    }

                    curr = curr.children[k];
                    curr.weight = w;
                }

            }
        }
    }

    public int f(String prefix, String suffix) {
        TrieNode curr = head;

        String target = suffix + "{" + prefix;
        int k;
        for(char c : target.toCharArray()){
            k = c - 'a';

            if(curr.children[k] == null){
                return -1;
            }

            curr = curr.children[k];
        }

        return curr.weight;
    }

    //TrieNode
    class TrieNode{
        TrieNode[] children = new TrieNode[27]; //a - z{
        int weight = 0;
    }
}


