package fgafa.graph.topological;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;


/**
 * 
 * 给一些排过序的未知语言的词，求该语言的字母序
 *
 */

public class Topological2 {

    public List<Character> getTopological(char[][] directions){
        //check
        
        //init
        Map<Character, Node> pool = new HashMap<>(); // store all graph nodes
        Map<Character, Integer> inDegree = new HashMap<>();
        Queue<Character> topNode = new LinkedList<>();

        //parse the input
        Node n1, n2;
        for(char[] pair : directions){
            n1 = addAndGet(pool, pair[0]);
            n2 = addAndGet(pool, pair[1]);

            inDegree.put(pair[1], inDegree.containsKey(n2) ? inDegree.get(n2) + 1 : 1);
        }
        
        for(Character node : pool.keySet()){
            if(!inDegree.containsKey(node)){
                topNode.offer(node);
            }
        }
        
        //
        List<Character> result = new ArrayList<>();
        Character c;
        while(!topNode.isEmpty()){
            c = topNode.poll();
            
            result.add(c);
            
            for(Node node : pool.get(c).children){
                if(1 == inDegree.get(node.name)){
                    topNode.offer(node.name);
                }
                
                inDegree.put(node.name, inDegree.get(node.name) - 1);
            }
            
        }
        
        for(Character node : pool.keySet()){
            if(inDegree.containsKey(node) && inDegree.get(node) > 0){
                throw new IllegalArgumentException("Found loop.");
            }
        }
        
        //return
        return result;
    }
    
    private Node addAndGet(Map<Character, Node> pool, char c){
        if( !pool.containsKey(c) ){
            pool.put( c, new Node(c) );
        }
        
        return pool.get(c);
    }
    
    
    class Node{
        public char name;
        public List<Node> children = new ArrayList<Node>();
        
        public Node(char name){
            this.name = name;
        }
        
        public void addChild(Node child){
            this.children.add(child);
        }
    }
    
}
