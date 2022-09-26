package sweepLine.interval;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import org.junit.Assert;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import util.Misc;

/**
 * _https://www.lintcode.com/problem/1074
 *
 *
 * A Range Module is a module that tracks ranges of numbers. Your task is to design and implement the following
 * interfaces in an efficient manner.
 *
 * addRange(int left, int right) Adds the half-open interval [left, right), tracking every real number in that interval.
 * Adding an interval that partially overlaps with currently tracked numbers should add any numbers in the interval
 * [left, right) that are not already tracked. queryRange(int left, int right) Returns true if and only if every real
 * number in the interval [left, right) is currently being tracked. removeRange(int left, int right) Stops tracking
 * every real number currently being tracked in the interval [left, right).
 * 
 * Notes:
 *   A half open interval [left, right) denotes all real numbers left <= x < right.
 *   0 < left < right < 10^9 in all calls to addRange, queryRange, removeRange.
 *   The total number of calls to addRange in a single test case is at most 1000.
 *   The total number of calls to queryRange in a single test case is at most 5000.
 *   The total number of calls to removeRange in a single test case is at most 1000.
 *
 * Example 1
 * Input:
 * addRange(10,20)
 * removeRange(14,16)
 * queryRange(10,14)
 * queryRange(13,15)
 * queryRange(16,17)
 *
 * Output: [true,false,true]
 * Explanation:
 * Every number in [10, 14) is being tracked
 * Numbers like 14, 14.03, 14.17 in [13, 15) are not being tracked
 * The number 16 in [16, 17) is still being tracked, despite the remove operation
 *
 * 
 * Example 2
 * Input:
 * addRange(1,2)
 * queryRange(2,3)
 * addRange(11,20)
 * queryRange(15,20)
 *
 * Output: [false,true]
 *
 */

public class RangeModule {

    TreeMap<Integer, Integer> map; //<left, right>

    public RangeModule() {
        this.map = new TreeMap<>();
    }

    /**
     * case 1: addRange(5, 8) in map {  }
     * case 2: addRange(5, 8) in map { {1, 2} }
     * case 3: addRange(5, 8) in map { {1, 3} }
     * case 4: addRange(5, 8) in map { {1, 9} }
     * case 5: addRange(5, 8) in map { {3, 8} }
     * case 6: addRange(5, 8) in map { {8, 9} }
     * case 7: addRange(5, 8) in map { {9, 10} }
     * 
     * @param left
     * @param right 
     */
    public void addRange(int left, int right) {
        if(right <= left){
            return;
        }

        Map.Entry<Integer, Integer> first = map.floorEntry(left);
        Map.Entry<Integer, Integer> last = map.floorEntry(right);
        if(first != null && first.getValue() >= left){
            left = first.getKey();
            //right = Math.max(right, first.getValue());
        }
        if(last != null){
            right = Math.max(right, last.getValue());
        }

        map.put(left, right);

        Map<Integer, Integer> subMap = map.subMap(left, false, right, true);
        Set<Integer> subSet = new HashSet<>(subMap.keySet()); //deep copy, to avoid java.util.ConcurrentModificationException
        map.keySet().removeAll(subSet);

    }

    public boolean queryRange(int left, int right) {
        Map.Entry<Integer, Integer> entry = map.floorEntry(left);
        return entry != null && entry.getValue() >= right;
    }

    public void removeRange(int left, int right) {
        if(right <= left){
            return;
        }

        Map.Entry<Integer, Integer> first = map.floorEntry(left);
        Map.Entry<Integer, Integer> last = map.floorEntry(right);
        if(first != null && first.getValue() > left){
            map.put(first.getKey(), left);
        }
        if(last != null && last.getValue() > right){
            map.put(right, last.getValue());
        }

        Map<Integer, Integer> subMap = map.subMap(left, true, right, false);
        Set<Integer> subSet = new HashSet<>(subMap.keySet()); //deep copy, to avoid java.util.ConcurrentModificationException
        map.keySet().removeAll(subSet);
    }


    public static void main(String[] args) throws NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException{
        
        String[][] inputs = {
            {
                "addRange(10,20),removeRange(14,16),queryRange(10,14),queryRange(13,15),queryRange(16,17)",
                "true,false,true"
            },
            {
                "addRange(1,2),queryRange(2,3),addRange(11,20),queryRange(15,20)",
                "false,true"
            }
        };
        
        RangeModule sv = new RangeModule();
        
        Class[] paramInt2 = new Class[2];
        paramInt2[0] = Integer.TYPE;
        paramInt2[1] = Integer.TYPE;
        
        Method method;
        for (String[] input : inputs) {
            List<String> result = new ArrayList<>();
            
            for(String cmd : input[0].split("\\)")){
                int openPar = cmd.indexOf('(');
                
                method = sv.getClass().getDeclaredMethod(cmd.substring(cmd.startsWith(",")? 1 : 0, openPar), paramInt2);
                
                int comma = cmd.lastIndexOf(',');
                Object ret = method.invoke(sv,
                        Integer.valueOf(cmd.substring(openPar + 1, comma).trim()),
                        Integer.valueOf(cmd.substring(comma + 1).trim())  );

                if (ret != null && ret instanceof Boolean) {
                    // System.out.println(String.format("  return value= %d", ret));
                    result.add(String.valueOf(ret));
                }
            }
            
            Assert.assertEquals(input[0], input[1], Misc.array2String(result,false).toString());
        }

    }

}
