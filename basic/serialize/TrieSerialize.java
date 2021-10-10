/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package basic.serialize;

import java.util.Map;
import java.util.NavigableMap;
import java.util.TreeMap;

/**
 *
 * @author yuanxi
 */
public class TrieSerialize {
    /** predefined class */
    public class TrieNode{
        public NavigableMap<Character, TrieNode> children;
        public TrieNode(){
            children = new TreeMap<>();
        }
    }
    
    /**
     * This method will be invoked first, you should design your own algorithm 
     * to serialize a trie which denote by a root node to a string which
     * can be easily deserialized by your own "deserialize" method later.
     */
    public String serialize(TrieNode root) {
    
        StringBuilder result = new StringBuilder();
        dfs(root, result);

        //System.out.println(" -after serialize- " + result.toString());    
        return result.toString();
    }

    private void dfs(TrieNode node, StringBuilder result){

        result.append('<');
        for( Map.Entry<Character, TrieNode> entry : node.children.entrySet() ){
            result.append(entry.getKey() );
            dfs(entry.getValue(), result);
        }
        result.append('>');

    }

    /**
     * This method will be invoked second, the argument data is what exactly
     * you serialized at method "serialize", that means the data is not given by
     * system, it's given by your own serialize method. So the format of data is
     * designed by yourself, and deserialize it here as you serialize it in 
     * "serialize" method.
     */
    public TrieNode deserialize(String data) {
        //System.out.println(" -before deserialize- " + data);      
        Data input = new Data(data.toCharArray());

        return dfs(input);
    }

    private TrieNode dfs(Data input){
        TrieNode curr = new TrieNode(); 

        input.p++; //pass '<', because data[p] is '<'
        char key;
        TrieNode child;
        while ( input.p < input.datas.length && (key = input.datas[input.p]) != '>'){
            input.p++; // pass the character
            child = dfs(input);

            curr.children.put(key, child);
            //System.out.println(" - - " + key + " " + serialize(child));
        }

        input.p++; //pass '>'
        return curr;
    }

    class Data{
        char[] datas;
        int p;

        Data(char[] datas){
            this.datas = datas;
            p = 0;
        }
    }
}
