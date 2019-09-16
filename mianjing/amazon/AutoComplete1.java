package fgafa.mianjing.amazon;


import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;

/**
 *   Implement a AutoComplete class, which supports:
 *     1) init with a list of keyWords
 *     2) when get a prefix, it can query out the top 10 keyWords who was queried more frequently.
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
public class AutoComplete1 {

    class Node{
        int weight;
        String keyWord;

        Node(int weight, String keyWord){
            this.weight = weight;
            this.keyWord = keyWord;
        }
    }

    class TrieNode{
        TrieNode[] nexts = new TrieNode[26]; // assume it's from a to z
        TrieNode previous;
        boolean isLeaf = false;

        PriorityQueue<Node> minHeap = new PriorityQueue<>(new Comparator<Node>() {
            @Override
            public int compare(Node o1, Node o2) {
                return Integer.compare(o1.weight, o2.weight);
            }
        });

        HashMap<String, Node> map = new HashMap<>();

        TrieNode(TrieNode previous){
            this.previous = previous;
        }
    }


    TrieNode header;
    HashMap<String, Node> weights = new HashMap<>();
    static int LIMIT;

    public AutoComplete1(List<String> keyWords, int limit){
        header = new TrieNode(null);
        this.LIMIT = limit;

        init(keyWords);
    }


    private void init(List<String> keyWords){
        for(String word : keyWords){
            add(word, header);
        }
    }

    private void add(String keyWord, TrieNode header){
        if(weights.containsKey(keyWord)){
            weights.get(keyWord).weight++;
        }else{
            weights.put(keyWord, new Node(1, keyWord));
        }

        TrieNode curr = header;
        for(char c : keyWord.toCharArray()){
            int i = c - 'a';

            if( curr.nexts[i] == null ){
                curr.nexts[i] = new TrieNode(curr);
            }
            curr = curr.nexts[i];

            add(curr, weights.get(keyWord));
        }

        curr.isLeaf = true;
    }

    private void add(TrieNode trieNode, Node newNode){
        PriorityQueue<Node> minHeap = trieNode.minHeap;

        if(minHeap.isEmpty() || minHeap.peek().weight < newNode.weight){
            if(trieNode.map.containsKey(newNode.keyWord)){
                minHeap.remove(trieNode.map.get(newNode.keyWord));
            }

            while(minHeap.size() >= LIMIT){
                trieNode.map.remove(minHeap.peek().keyWord);
                minHeap.poll();
            }

            minHeap.add(newNode);
            trieNode.map.put(newNode.keyWord, newNode);
        }
    }

    public List<String> query(String prefix, int limit){

        TrieNode curr = header;

        //locate the TrieNode
        for(char c: prefix.toCharArray()){
            int i = c - 'a';

            if(curr.nexts[i] == null){
                return Collections.emptyList();
            }

            curr = curr.nexts[i];
        }

        //get the result
        LinkedList<String> result = new LinkedList<>();
        Iterator<Node> iterator = curr.minHeap.iterator();

        while(limit > 0 && iterator.hasNext()){
            result.addFirst(iterator.next().keyWord);
        }

        //update weight
        //todo

        return result;
    }

    //update: increaseWeight
    private void increaseWeight(String prefix){

    }

    private void toUp(){

    }
}
