/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package design.others.stream;

import java.util.HashMap;
import java.util.Map;

/**
 * Given a continuous stream of data, write a function that returns the first unique number (including the last number)
 * when the terminating number arrives. If the terminating number is not found, return -1.
 *
 * Example1
 * Input: [1, 2, 2, 1, 3, 4, 4, 5, 6] 5 
 * Output: 3 
 * Explanation: the 3 is first unique number by 5  (by means before and include 5)
 * 
 * Example2
 * Input: [1, 2, 2, 1, 3, 4, 4, 5, 6] 7 
 * Output: -1 
 * Explanation: not found the terminating number 7 in the input stream 
 * 
 * Example3
 * Input: [1, 2, 2, 1, 3, 4] 3 
 * Output: 3
 * Explanation: not found any unique number by 3
 * 
 * Solutions:
 *   Because the input is a data stream, so it need get the answer by the terminating number, one pass.
 *   And because it need check if it's unique, it has to cache the inputted numbers, the best way is BloomFilter
 * 
 */
public class FirstUniqueNumber {
    
    class FirstUniqueSteam {

        class Node {

            int value;

            Node pre;
            Node next;
        }

        Map<Integer, Node> datas;
        Node header;

        FirstUniqueSteam() {
            datas = new HashMap<>();
            header = new Node();
            header.next = header;
            header.pre = header;
        }

        void add(int num) {
            if (datas.containsKey(num)) {
                Node curr = datas.get(num);

                if (curr.next != null) {
                    curr.next.pre = curr.pre;
                    curr.pre.next = curr.next;
                    curr.next = null;
                    curr.pre = null;
                }
            } else {
                Node curr = new Node();
                curr.value = num;
                datas.put(num, curr);

                //Node pre = header.pre;
                curr.pre = header.pre;
                curr.next = header;
                header.pre.next = curr;
                header.pre = curr;
            }
        }

        int getFirstUnique() {
            if (header == header.next) {
                return -1;
            }

            return header.next.value;
        }
    }

    /**
     * 
     * @param nums: a continuous stream of numbers
     * @param number: a number
     * @return: returns the first unique number
     */
    public int firstUniqueNumber(int[] nums, int number) {
        FirstUniqueSteam stream = new FirstUniqueSteam();

        for (int x : nums) {
            stream.add(x);

            if (x == number) {
                return stream.getFirstUnique();
            }
        }

        return -1;
    }
}
