package datastructure.trie;


import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NavigableSet;
import java.util.TreeSet;

/**
 *   Implement a AutoComplete class, which supports:
 *     1) init with a list of keyWords
 *     2) when get a prefix, it can query out the top 10 keyWords who was queried more frequently.
 *
 *   similar with TypeAhead
 * 
 *   Solution #1.
 *     with Trie, cache the top 10 keywords who is with this prefix and queried more frequently.
 *   Solution #2,
 *     with Trie, not cache the top 10 keywords, cache the query weight in TrieNode
 *
 *
 *   Comparation:
 *
 *
 *
 */
public class Autocomplete {

    /** ---- predefined class start -----   */
    class TrieNode{
        TrieNode[] nexts;
        boolean isLeaf = false;
        NavigableSet<String> hotspots;

        TrieNode(){
            nexts = new TrieNode[26]; // assume it's from a to z
            hotspots = new TreeSet<>((s1, s2) -> { 
                int diff = weights.get(s2) - weights.get(s1);
                return diff == 0? s1.compareTo(s2) : diff; } );
        }
    }
    /** ---- predefined class end -----   */

    TrieNode root;
    Map<String, Integer> weights; //<keyWord, frequency>
    int limit;

    public Autocomplete(List<String> keyWords, int limit){
        root = new TrieNode();
        weights = new HashMap<>();
        
        this.limit = limit;

        for(String word : keyWords){
            add(word);
        }
    }

    private void add(String keyWord){
        weights.put(keyWord, 0);
        
        TrieNode curr = root;
        
        for(char c : keyWord.toCharArray()){
            int i = c - 'a';
            if( curr.nexts[i] == null ){
                curr.nexts[i] = new TrieNode();
            }
            curr = curr.nexts[i];

            curr.hotspots.add(keyWord);
            if(curr.hotspots.size() > limit){
                curr.hotspots.pollLast();
            }
        }

        curr.isLeaf = true;
    }


    public List<String> query(String prefix){
        TrieNode curr = root;
        for(char c: prefix.toCharArray()){
            int i = c - 'a';

            if(curr.nexts[i] == null){
                return Collections.emptyList();
            }

            curr = curr.nexts[i];
        }

        //List<String> result = curr.top10.stream().map(s -> weights.get(n)).collect(Collectors.toList());
        return new ArrayList<>(curr.hotspots);
    }

    private void increaseWeight(String keyWord){
        weights.put(keyWord, weights.getOrDefault(keyWord, 0) + 1 );
        
        TrieNode curr = root;
        for(char c: keyWord.toCharArray()){
            int i = c - 'a';
            if(curr.nexts[i] == null){
                return;
            }
            curr = curr.nexts[i];
            
            curr.hotspots.remove(keyWord);
            curr.hotspots.add(keyWord);
        }
    }

}
