/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package companyTag.airbnb;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import org.junit.Assert;

/**
 * Write a file system class, which has two functions: create, and get
 * 
 * Example:
 * create("/a",1)
 * get("/a") //get 1
 * create("/a/b",2)
 * get("/a/b") //get 2
 * create("/c/d",1) //Error, because "/c" is not existed
 * get("/c") //Error, because "/c" is not existed
 * 
 */
public class FileSystem {
    class Node{
        Map<String, Node> nodes ;
        
//        String name;
        Integer value = null;
        
//        String creator;
//        long createTime;
//        long modifyTime;
        
        Node(){            
            nodes = new HashMap<>();
        }
    }
    
    Node root;
    
    FileSystem(){
        root = new Node();
    }
    
    public void create(String url, int value){
        
        Node curr = root;
        String[] nodes = url.split("/");
        int n = nodes.length - 1;
        //System.out.println(String.format("url: %s, nodes: %s", url, Arrays.toString(nodes))); 
        
        for(int i = 0; i < n; i++ ){
            if(nodes[i] == null || nodes[i].isEmpty()){
                continue;
            }
            
            curr = curr.nodes.get(nodes[i]); 
            if(curr == null){
                throw new IllegalArgumentException(nodes[i] + " is not existed. ");
            }
        }
        
        if(curr.nodes.containsKey(nodes[n])){
            throw new IllegalArgumentException(nodes[n] + " is already existed. ");
        }
        curr.nodes.put(nodes[n], new Node());
        curr.nodes.get(nodes[n]).value = value;
    }
    
    public int get(String url){
        
        Node curr = root;
        String[] nodes = url.split("/");
        for(int i = 0, n = nodes.length; i < n; i++ ){
            if(nodes[i] == null || nodes[i].isEmpty()){
                continue;
            }
            
            curr = curr.nodes.get(nodes[i]); 
            //System.out.println(String.format("nodes[i]: %s, curr: %s", nodes[i], curr == null ? "null" : "not null")); 
            
            if(curr == null){
                throw new IllegalArgumentException(url + "is not existed. ");
            }
        }
        
        return curr.value;
    }
    
    
    public static void main(String[] args){
        
        FileSystem sv = new FileSystem();
        
        String[][][] inputs = {
            {{"/a", "1"}, {"Y"}},
            {{"/a"}, {"1"}},
            {{"/a/b", "2"}, {"Y"}},
            {{"/a/b"}, {"2"}},
            {{"/c/d", "1"}, {"N"}},
            {{"/c"}, {"N"}},
        };
        
        String[] testCase;
        String expect;
        for(String[][] input : inputs){
            testCase = input[0];            
            expect = input[1][0];
            
            System.out.println(String.format("event: %s, expect: %s", Arrays.toString(testCase), expect));
            
            if(testCase.length == 2){ // create()
                try{
                    sv.create(testCase[0], Integer.parseInt(testCase[1]) );
                    
                    Assert.assertTrue(expect.equals("Y"));
                }catch(IllegalArgumentException ie){
                    Assert.assertTrue(expect.equals("N"));
                }
                
            }else{ //testCase.length == 1, get()
                try{                    
                    Assert.assertEquals(Integer.parseInt(expect), sv.get(testCase[0] ));
                }catch(IllegalArgumentException ie){
                    Assert.assertTrue(expect.equals("N"));
                }
                
            }
            
        }
        
    }
}
