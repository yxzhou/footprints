package fgafa.dailyCoding;

import java.util.ArrayList;
import java.util.List;

/**
 * Implement an autocomplete system. That is, given a query string s and a set of all possible query strings, return all strings in the set that have s as a prefix.

 For example, given the query string de and the set of strings [dog, deer, deal], return [deer, deal].

 Hint: Try preprocessing the dictionary into a more efficient data structure to speed up queries.
 *
 * Tags: Twitter, data structure
 */

public class TrieTree {

    class TrieNode{
        TrieNode[] next = new TrieNode[256]; //assume it's AscII characters, default all are null
        boolean isLeaf;
        String word;
    }

    TrieNode header = new TrieNode();

    public TrieTree(final List<String> words){
        for(String word : words){
            TrieNode curr = header;

            for(char c : word.toCharArray()){
                if( curr.next[c] == null ){
                    curr.next[c] = new TrieNode();
                }

                curr = curr.next[c];
            }

            curr.isLeaf = true;
            curr.word = word;
        }
    }

    public List<String> query(String prefix){
        List<String> result = new ArrayList<>();

        if(prefix == null || prefix.isEmpty()){
            return result;
        }

        TrieNode curr = header;
        for(char c : prefix.toCharArray()){
            if(curr.next[c] == null){
                return result;
            }else{
                curr = curr.next[c];
            }
        }

        traversal_dfs(curr, result);

        return result;
    }


    private void traversal_dfs(TrieNode node, List<String> result){
        if(node.isLeaf){
            result.add(node.word);
        }

        for(TrieNode n : node.next){
            if(n != null){
                traversal_dfs(n, result);
            }
        }
    }



}

