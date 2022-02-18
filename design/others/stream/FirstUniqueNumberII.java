/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package design.others.stream;

import java.util.HashSet;
import java.util.LinkedHashSet;

/**
 * _https://www.lintcode.com/problem/960
 *
 * We need to implement a data structure named DataStream. There are two methods required to be implemented:
 *   void add(number) // add a new number 
 *   int firstUnique() // return first unique number
 *
 * You can assume that there must be at least one unique number in the stream when calling the firstUnique.
 *
 * Example 1:
 * Input: 
 * add(1) 
 * add(2) 
 * firstUnique() 
 * add(1) 
 * firstUnique() 
 * Output: [1,2] 
 * 
 * Example 2:
 * Input: 
 * add(1) 
 * add(2) 
 * add(3) 
 * add(4) 
 * add(5) 
 * firstUnique() 
 * add(1) 
 * firstUnique() 
 * add(2) 
 * firstUnique() 
 * add(3)
 * firstUnique() 
 * add(4) 
 * firstUnique() 
 * add(5) 
 * add(6) 
 * firstUnique() 
 * Output: [1,2,3,4,5,6]
 * 
 */
public class FirstUniqueNumberII {
    LinkedHashSet<Integer> listSet;
    HashSet<Integer> set;
    
    public FirstUniqueNumberII(){
        listSet = new LinkedHashSet();
        set = new HashSet<>();
    }

    /**
     * @param num: next number in stream
     * @return: nothing
     */
    public void add(int num) {
        if(!set.contains(num)){
            set.add(num);
            listSet.add(num); 
        } else if( listSet.contains(num) ) {
            listSet.remove(num);
        }
    }

    /**
     * @return the first unique number in stream
     */
    public int firstUnique() {
        return listSet.isEmpty()? -1 : listSet.iterator().next();
    }
}
