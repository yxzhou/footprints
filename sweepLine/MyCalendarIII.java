/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sweepLine;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import junit.framework.Assert;
import util.Misc;

/**
 * _https://www.lintcode.com/problem/1063/
 *
 * Implement a MyCalendarThree class to store your events. A new event can always be added.
 *
 * Your class will have one method, book(int start, int end). Formally, this represents a booking on the half open
 * interval [start, end), the range of real numbers x such that start <= x < end.
 *
 * A K-booking happens when K events have some non-empty intersection (ie., there is some time that is common to all K
 * events.)
 *
 * For each call to the method MyCalendar.book, return the largest integer such that there exists a K-booking in the
 * calendar.
 *
 * Your class will be called like this: MyCalendarThree cal = new MyCalendarThree(); MyCalendarThree.book(start, end)
 * The number of calls to MyCalendarThree.book per test case will be at most 400.
 * In calls to MyCalendarThree.book(start, end), start and end are integers in the range [0, 10^9].
 * 
 * Example 1
 * Input:
 * MyCalendarThree()
 * book(10,20)
 * book(50,60)
 * book(10,40)
 * book(5,15)
 * book(5,10)
 * book(25,55)
 * Output: [1,1,2,3,3,3]
 * Explanation: The first two events can be booked and are disjoint, so the maximum K-booking is a 1-booking. The third 
 * event [10, 40) intersects the first event, and the maximum K-booking is a 2-booking. The remaining events cause the 
 * maximum K-booking to be only a 3-booking. Note that the last event locally causes a 2-booking, but the answer is 
 * still 3 because eg. [10, 20), [10, 40), and [5, 15) are still triple booked.
 * 
 * Example 2
 * Input:
 * MyCalendarThree()
 * book(1,2)
 * book(1,2)
 * book(2,3)
 * Output: [1,2,2]
 * 
 * 
 * 
 */
public class MyCalendarIII {
    TreeMap<Integer, Integer> map; //map: key is start or end, value is the change, which is 1 on start, -1 on end

    public MyCalendarIII() {
        map = new TreeMap<>();
    }
    
    public int book(int start, int end) {
        map.put(start, map.getOrDefault(start, 0) + 1);
        map.put(end, map.getOrDefault(end, 0) - 1);

        int max = 0;
        int sum = 0;

        for(Map.Entry<Integer, Integer> entry : map.entrySet()){
            sum += entry.getValue();

            //if(entry.getKey() >= start && entry.getKey() < end){
                max = Math.max(max, sum);
            //}
        }

        return max;
    }
    

    public static void main(String[] args){
        String[] classes = {"MyCalendarIII", "MyCalendarIII2"};
        
        String[][] inputs = {
            {
                "book(10,20) book(50,60) book(10,40) book(5,15) book(5,10) book(25,55)",
                "1,1,2,3,3,3"
            },
            {
                "book(1,2) book(1,2) book(2,3)",
                "1,2,2"
            },
            {
                "book(24,40) book(43,50) book(27,43) book(5,21) book(30,40) book(14,29) book(3,19) book(3,14) book(25,39) book(6,19)",
                "1,1,2,2,3,3,3,3,4,4"
            },
            {
                "book(44,50) book(47,50) book(9,15) book(4,10) book(2,7) book(28,37) book(26,33) book(22,28) book(43,50) book(18,25)",
                "1,2,2,2,2,2,2,2,3,3"
            },
            {
                "book(8,23) book(35,48) book(24,39) book(10,22) book(10,23) book(8,22) book(1,14) book(36,50) book(42,50) book(42,50)",
                "1,1,2,2,3,4,5,5,5,5"
            }
        };
                
        // int parameter
        Class[] paramInt2 = new Class[2];
        paramInt2[0] = Integer.TYPE;
        paramInt2[1] = Integer.TYPE;

        Class cls;
        List<Integer> result;
        for(String sv : classes){
            System.out.println(String.format("\n call: %s" , sv ));
            
            for (String[] usercase : inputs) {
                System.out.println(String.format(" testcase: %s" , usercase[0]));
                result = new ArrayList<>();
                
                try {

                    cls = Class.forName("sweepLine." + sv);

                    Object obj = cls.getConstructor().newInstance();

                    Method method;
                    StringTokenizer st = new StringTokenizer(usercase[0], " ");
                    while (st.hasMoreTokens()) {
                        String action = st.nextToken();
                        int index = action.indexOf('(');
    //System.out.println(" " + action);

                        if ("book".equals(action.substring(0, index))) {
                            method = cls.getDeclaredMethod("book", paramInt2);

                            int seconfComma = action.lastIndexOf(',');
                            Object ret = method.invoke(obj, Integer.valueOf(action.substring(index + 1, seconfComma).trim()),
                                Integer.valueOf(action.substring(seconfComma + 1, action.length() - 1).trim()));

                            if (ret instanceof Integer) {
                                result.add((Integer) ret);
                            }
                        }

                    }

                    //System.out.println(String.format(" output: %s" , Misc.array2String(result, false).toString()));
                    Assert.assertEquals(usercase[1], Misc.array2String(result, false).toString() );
                
                }catch (Exception ex) {
                    Logger.getLogger(MyCalendarII.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
   }

    
}
