/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sweepLine;

import java.util.TreeMap;
import java.util.TreeSet;

/**
 *_https://www.lintcode.com/problem/1513
 * 
 *   s1: store every two adjacent person with (start, end), to get the first longest "empty" interval, it need a maxHeap.
 * 
 *       
 * 
 */
public class ExamRoom2 {
    
    TreeSet<Integer> persons;
    TreeMap<Integer, TreeSet<Integer>> intervals; //map: interval length/2 to a ordered list of interval.start
    
    int n;
    
    public ExamRoom2(int N) {
        persons = new TreeSet<>();
        intervals = new TreeMap<>();
        
        n = N - 1;
    }
    
    //O(logn), find the first longest interval
    public int seat() {
        if(persons.isEmpty()){
            persons.add(0);
            return 0;
        }
        
        //find the seat that maximizes the distance to the closest person
        int maxLength = persons.first();
        int middle = 0;
        
        if(!intervals.isEmpty()){ //persons.size() >= 2
            int length = intervals.lastKey();
            
            if( maxLength < length ){
                maxLength = length;
                middle = intervals.get(length).first()+ length;
            }
        }
        
        if(maxLength < n - persons.last()){
            middle = n;
        }

        //
        persons.add(middle);
        
        //
        Integer pre = persons.lower(middle);
        Integer next = persons.higher(middle);
        if(pre != null && next != null){
            int length = (next - pre) / 2;
            intervals.computeIfAbsent(length, o -> new TreeSet<>()).remove(pre);
            
            clear(length);
        }  
        if(pre != null){
            intervals.computeIfAbsent((middle - pre) / 2, o -> new TreeSet<>()).add(pre);
        }
        if(next != null){
            intervals.computeIfAbsent((next - middle) / 2, o -> new TreeSet<>()).add(middle);
        }

        return middle;
        
    }

    // O(logn)
    public void leave(int p) {
        persons.remove(p);
                
        int length;
        Integer pre = persons.lower(p);
        Integer next = persons.higher(p);
        if(pre != null){
            length = (p - pre) / 2;
            intervals.get(length).remove(pre);
            clear(length);
        }
        if(next != null){
            length = (next - p) / 2;
            intervals.get(length).remove(p);
            clear(length);
        }
        
        if(pre != null && next != null){
            intervals.computeIfAbsent((next - pre) / 2, o -> new TreeSet<>()).add(pre);
        }
        
    }
    
    
    private void clear(int length){
        if(intervals.get(length).isEmpty()){
            intervals.remove(length);
        }
    }
    
}
