/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package design.others.stream;

import java.util.HashMap;
import java.util.Map;

/**
 *  one pass 
 * 
 */
public class FirstUniqueNumber2 {
    
    class UniqueDataSteam {

        class Node {
            int value;

            Node pre;
            Node next;
        }

        Map<Integer, Node> datas;
        Node header;

        UniqueDataSteam() {
            datas = new HashMap<>();
            header = new Node();
            header.next = header;
            header.pre = header;
        }

        void add(int num) {
            if (datas.containsKey(num)) { //remove the node from the LinkedList
                Node curr = datas.get(num);

                if (curr.next != null) {
                    curr.next.pre = curr.pre;
                    curr.pre.next = curr.next;
                    
                    curr.next = null;
                    curr.pre = null;
                    datas.put(num, null);
                }
            } else { // add the node after the tail of the LinkedList
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
     * onepass
     * 
     * @param nums: a continuous stream of numbers
     * @param number: a number
     * @return the first unique number
     */
    public int firstUniqueNumber(int[] nums, int number) {
        UniqueDataSteam stream = new UniqueDataSteam();

        for (int x : nums) {
            stream.add(x);

            if (x == number) {
                return stream.getFirstUnique();
            }
        }

        return -1;
    }
    
}
