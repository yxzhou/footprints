/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sweepLine;

import datastructure.LRU.LRUCache;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;
import org.junit.Assert;

/**
 *_https://www.lintcode.com/problem/1513
 * 
 * In an exam room, there are N seats in a single row, numbered 0, 1, 2, ..., N-1. 
 * When a student enters the room, they must sit in the seat that maximizes the distance to the closest person. If there
 * are multiple such seats, they sit in the seat with the lowest number. (Also, if no one is in the room, then the 
 * student sits at seat number 0.)
 * Return a class ExamRoom(int N) that exposes two functions:ExamRoom.seat() returning an int representing what seat the 
 * student sat in, and ExamRoom.leave(int p) representing that the student in seat number p now leaves the room. It is 
 * guaranteed that any calls toExamRoom.leave(p) have a student sitting in seat p.

 * Example 1:
 * Input: ["ExamRoom","seat","seat","seat","seat","leave","seat"], [[10],[],[],[],[],[4],[]]
 * Output: [null,0,9,4,2,null,5]
 * Explanation:
 * ExamRoom(10) -> null
 * seat() -> 0, no one is in the room, then the student sits at seat number 0.
 * seat() -> 9, the student sits at the last seat number 9.
 * seat() -> 4, the student sits at the last seat number 4.
 * seat() -> 2, the student sits at the last seat number 2.
 * leave(4) -> null
 * seat() -> 5, the student sits at the last seat number 5.
​​​​​​​ * 
 * Note:
 *   1 <= N <= 10^9
 *   ExamRoom.seat()and ExamRoom.leave() will be called at most 10^4 times across all test cases.
 *   Calls to ExamRoom.leave(p) are guaranteed to have a student currently sitting in seat number p.
 * 
 * Thoughts:
 *   It's a single row, numbered 0, 1, 2, ..., N-1. 
 *   The 1st person will seat in 0, 
 *   The 2nd person will seat in (N - 1)
 *   The 2nd person will seat in (0 + N - 1) / 2
 *   In seat(), it need find the seat that maximizes the distance to the closest person. It's in the middle of the first
 *   longest "empty" interval.
 * 
 *   s1: store every two adjacent person with (start, end), to get the first longest "empty" interval, it need a maxHeap.
 * 
 *       
 * 
 */
public class ExamRoom {
    
    TreeSet<Integer> persons;
    
    int n;
    
    public ExamRoom(int N) {
        persons = new TreeSet<>();
        
        n = N - 1;
    }
    
    //O(n), find the first longest interval
    public int seat() {

        if(persons.isEmpty()){
            persons.add(0);
            return 0;
        }
        
        int pre = 0;
        int maxLength = persons.first();
        int middle = 0;
        int length;
        
        for(int p : persons){
            length = (p - pre) / 2;
            
            if(maxLength < length){
                maxLength = length;
                middle = pre + maxLength;
            }
            
            pre = p;
        }
        
        if(maxLength < n - pre){
            middle = n;
        }

        persons.add(middle);

        return middle;
    }

    //O(logn)
    public void leave(int p) {
        persons.remove(p);
    }
    
    
    public static void main(String[] args) throws ClassNotFoundException, NoSuchMethodException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException{
        String[] classes = {"ExamRoom", "ExamRoom2"};
        String[] methods = {"seat", "leave"}; 
                
        int[][][] inputs = {
            //{methodIds, parameters, expects
            {
                {0,  1, 1, 1, 1,  2, 1,  2,  2, 1, 1},
                {10, 0, 0, 0, 0,  4, 0,  0,  9, 0, 0},
                {-2, 0, 9, 4, 2, -1, 5, -1, -1, 9, 0}
            }
            
        };
        
        Class[] paramInt1 = new Class[1];
        paramInt1[0] = Integer.TYPE;
        
        for(String sv : classes){
            for (int[][] input : inputs) {
                List<Integer> result = new ArrayList<>();

                Class cls = Class.forName("sweepLine." + sv);
                Constructor<LRUCache> constructor = cls.getConstructor(paramInt1);

                Object obj = constructor.newInstance(input[1][0]);
                result.add(-2);

                Method method;
                for (int j = 1; j < input[0].length; j++) {
                    if (1 == input[0][j]) {//seat

                        method = cls.getDeclaredMethod("seat");

                        Object ret = method.invoke(obj);

                        if (ret instanceof Integer) {
                            // System.out.println(String.format("  return value= %d", ret));
                            result.add((Integer) ret);
                        }
                    } else { // leave

                        method = cls.getDeclaredMethod("leave", paramInt1);
                        method.invoke(obj, input[1][j]);
                        result.add(-1);
                    }
                }

                Assert.assertArrayEquals(cls.getName(), input[2], result.stream().mapToInt(Integer::intValue).toArray());
            }
        }
        
    }
    
}
