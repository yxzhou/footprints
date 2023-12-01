/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package companyTag.airbnb;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import org.junit.Assert;
import util.Misc;

/**
 *
 * Given a list of everyone's preferred city list, output the city list following the order of everyone's preference order.
 * 
 * For example, 
 * input is [[3, 5, 7, 9], [2, 3, 8], [5, 8]]. 
 * One of possible output is [2, 3, 5, 8, 7, 9].
 * 
 * Thoughts:
 *   S1 topology
 * 
 */
public class PreferenceList {
    
    /**
     * 
     * @param preferences
     * @return 
     */
    public List<Integer> getPreference(List<List<Integer>> preferences) {
        
        Map<Integer, Set<Integer>> edges = new HashMap<>();
        Map<Integer, Integer> indegrees = new HashMap<>();
        int from;
        int to;
        for(List<Integer> list : preferences){
            
            indegrees.putIfAbsent(list.get(0), 0);
            for(int i = 1; i < list.size(); i++){
                from = list.get(i - 1);
                to = list.get(i);
                
                indegrees.putIfAbsent(to, 0);
                
                if(!edges.containsKey(from)){ 
                    edges.put(from, new HashSet<>());
                }    
                
                if(!edges.get(from).contains(to)){
                    edges.get(from).add(to);
                    indegrees.put(to, indegrees.get(to) + 1);
                }
            }
        }
        
        Queue<Integer> queue = new LinkedList<>();
        for(Map.Entry<Integer, Integer> pair : indegrees.entrySet()){
            if(pair.getValue() == 0){
                queue.add(pair.getKey());
            }
        }
        
        List<Integer> result = new ArrayList<>();
        int v;
        while(!queue.isEmpty()){
            v = queue.poll();
            
            result.add(v);
            
            if(!edges.containsKey(v)){
                continue;
            }
            
            for(Integer next : edges.get(v)){
                
                indegrees.put(next, indegrees.get(next) - 1);
                
                if(indegrees.get(next) == 0){
                    queue.add(next);
                }
            }
        }
        
        return result;
    }
    
    
    public static void main(String[] args){
        
        int[][][][] inputs = {
            {
                {{3,5,7,9}, {2,3,8}, {5,8}}, 
                {{2,3,5,7,8,9}}
            },
            {
                {{2,3,5}, {4,2,1}, {4,1,5,6}, {4,7}}, 
                {{4,2,7,1,3,5,6}}
            }
        };
        
        PreferenceList sv = new PreferenceList();
        
        List<Integer> result;
        for(int[][][] input : inputs){
            
            result = sv.getPreference(Misc.convert(input[0]));
            
            System.out.println(String.format("\n  Input: %s, \n Output: %s",  Misc.array2String(input[0]), result ));
            
            Assert.assertEquals("", input[1].length, result.size());
            Assert.assertEquals("", input[1].length, result.size());
        }
    }
    
}
