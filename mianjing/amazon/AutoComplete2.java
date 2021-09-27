package mianjing.amazon;

import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;

import org.apache.commons.lang3.tuple.Pair;

/**
 *   Implement a AutoComplete class, which supports:
 *     1) init with a list of keyWords
 *     2) when get a prefix, it can query out the top 10 keyWords who was queried more frequently.
 *
 *
 *
 */

public class AutoComplete2 {
    class TrieNode{
        TrieNode[] nexts = new TrieNode[26]; // assume it's from a to z
        boolean isLeaf = false;
        String keyWord;

        int weight = 0;
    }

    TrieNode header;

    public AutoComplete2(List<String> keyWords, int limit){
        header = new TrieNode();

        init(keyWords);
    }

    public List<String> query(String prefix, final int limt){

        TrieNode curr = header;
        for(char c : prefix.toCharArray()){
            int i = c - 'a';

            if( curr.nexts[i] == null ){
                return Collections.emptyList();
            }
            curr = curr.nexts[i];
        }

        curr.weight++;

        PriorityQueue<Pair<Integer, String>> minHeap = new PriorityQueue<>( Comparator.comparing(Pair::getValue));
        dfs(curr, 0, minHeap, limt);

        LinkedList<String> result = new LinkedList<>();

        minHeap.stream().forEachOrdered(p -> result.addFirst(p.getValue()));

        return result;
    }

    private void dfs(TrieNode trieNode, int weight, PriorityQueue<Pair<Integer, String>> minHeap, final int limit){
        weight += trieNode.weight;

        if (trieNode.isLeaf && (minHeap.isEmpty() || minHeap.peek().getKey() < weight)) {

            while (minHeap.size() >= limit) {
                minHeap.poll();
            }

            minHeap.add(Pair.of(weight, trieNode.keyWord));
        }

        for(int i = 0; i < 26; i++){
            if(trieNode.nexts[i] != null){
                dfs(trieNode.nexts[i], weight, minHeap, limit);
            }
        }
    }

    private void init(List<String> keyWords){
        for(String word : keyWords){
            add(word, header);
        }
    }

    private void add(String keyWord, TrieNode header){

        TrieNode curr = header;
        for(char c : keyWord.toCharArray()){
            int i = c - 'a';

            if( curr.nexts[i] == null ){
                curr.nexts[i] = new TrieNode();
            }
        }

        curr.weight++;
        curr.isLeaf = true;
        curr.keyWord = keyWord;
    }

}
