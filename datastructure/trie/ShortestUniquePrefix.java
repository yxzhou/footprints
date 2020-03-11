package fgafa.datastructure.trie;

import fgafa.util.Misc;
import org.junit.Test;

/**
 *
 * Given a list of words, return the shortest unique prefix of each word. For example, given the list:

 dog
 cat
 apple
 apricot
 fish

 Return the list:
 d
 c
 app
 apr
 f
 *
 * Tags: Square
 *
 */

public class ShortestUniquePrefix {

    class Node{
        //char value;
        Node[] next = new Node[26];

        int counter = 0;
    }

    class Trie{
        Node root = new Node();

        public void add(String word){
            Node curr = root;

            for(char c : word.toCharArray()){
                int index = c - 'a';
                if(curr.next[index] == null){
                    curr.next[index] = new Node();

                    //curr.next[index].value = c;
                }

                curr.next[index].counter++;

                curr = curr.next[index];
            }
        }

        public String shortestUniquePrefix(String word){
            Node curr = root;

            for(int i = 0; i < word.length(); i++){
                int index = word.charAt(i) - 'a';

                if(curr.next[index].counter == 1){
                    return word.substring(0, i + 1);
                }

                curr = curr.next[index];
            }

            return null; //??
        }
    }

    public String[] shortestUniquePrefix(String[] words){
        if(null == words || 0 == words.length){
            return words;
        }

        Trie trie = new Trie();

        for(String word : words){
            trie.add(word);
        }

        String[] result = new String[words.length];
        for(int i = 0; i < words.length; i++){
            result[i] = trie.shortestUniquePrefix(words[i]);
        }

        return result;
    }


    @Test public void test(){
//        Assert.assertEquals(new String[]{
//                "d",
//                "c",
//                "app",
//                "apr",
//                "f"
//        }, shortestUniquePrefix(new String[]{
//                "dog",
//                "cat",
//                "apple",
//                "apricot",
//                "fish"
//        }));


        System.out.println(Misc.array2String(shortestUniquePrefix(new String[]{
                "dog",
                "cat",
                "apple",
                "apricot",
                "fish"
        })));
    }

}
