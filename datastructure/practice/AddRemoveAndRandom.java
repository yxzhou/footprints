package fgafa.datastructure;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

/**
 * 
 * Design a data structure, implement the following methods:
    1, add(int value)
    2, remove(int value)
    3, remove_rand() 
    
    follow up, the data maybe duplicated 
 *
 */

public class MyDataStructure {
    Map<Integer, Set<Integer>> value2Index = new HashMap<>();
    //final int ENOUGH = Integer.MAX_VALUE;
    //int[] values = new int[ENOUGH];
    List<Integer> index2Value = new ArrayList<Integer>();
    
    void add(int value){
        index2Value.add(value);
        
        Set<Integer> indexs;
        if(value2Index.containsKey(value)){
            indexs = value2Index.get(value);
        }else{
            indexs = new HashSet<Integer>();
            value2Index.put(value, indexs);
        }
        indexs.add(index2Value.size() - 1);
        
    }
    
    void removeByValue(int value){
        if(value2Index.containsKey(value)){
            int end = index2Value.size();
            
            Set<Integer> indexs = value2Index.get(value);
            for(int index : indexs){
                replace(end, index);
                
                end--;
            }
                
            value2Index.remove(value);
        }
    }
    
    private void replace(int toRemovedIndex, int toReplacedIndex){
        int toKeptValue = index2Value.get(toRemovedIndex);
        
        index2Value.set(toReplacedIndex, toKeptValue);
        index2Value.remove(toRemovedIndex);
        
        Set<Integer> indexs = value2Index.get(toKeptValue);
        indexs.add(toReplacedIndex);
        indexs.remove(toRemovedIndex);

    }
    
    
    void remove_randByIndex(){
        int end = index2Value.size();
        Random random = new Random();
        int toRemovedIndex = random.nextInt(end);
        replace(end, toRemovedIndex);
        
        Set<Integer> indexs = value2Index.get(toRemovedIndex);
        indexs.remove(toRemovedIndex);
    }
    
    void remove_randByValue(){
        Integer[] values = value2Index.keySet().toArray(new Integer[0]);
        
        int end = values.length;
        Random random = new Random();
        int toRemovedIndex = random.nextInt(end);
        
        removeByValue(values[toRemovedIndex]);
        
        
    }
}
