/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dfsbfs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import java.util.List;
import java.util.Map;

import org.junit.Assert;
import util.Misc;

/**
 * _https://www.lintcode.com/problem/1049
 *
 * We are stacking blocks to form a pyramid. Each block has a color which is a one letter string, like 'Z'.
 *
 * For every block of color C we place not in the bottom row, we are placing it on top of a left block of color A and
 * right block of color B. We are allowed to place the block there only if (A, B, C) is an allowed triple.
 *
 * We start with a bottom row of bottom, represented as a single string. We also start with a list of allowed triples
 * allowed. Each allowed triple is represented as a string of length 3.
 *
 * Return true if we can build the pyramid all the way to the top, otherwise false.
 *
 * Constraints: 
 * bottom will be a string with length in range [2, 8]. 
 * allowed will have length in range [0, 200]. 
 * Letters in all strings will be chosen from the set {'A', 'B', 'C', 'D', 'E', 'F', 'G'}.
 *
 * Example 1: 
 * Input: bottom = "BCG", allowed = ["BCD", "CGE", "DEA", "FFF"] 
 * Output: true
 *
 * Explanation: We can stack the pyramid like this:
        A
       / \
      D   E
     / \ / \
    B   C   G

 * This works because ('B', 'C', 'D'), ('C', 'G', 'E'), and ('D', 'E', 'A') are allowed triples.
 *
 * Example 2:
 * Input: bottom = "XXYX", allowed = ["XXX", "XXY", "XYX", "XYY", "YXZ"] 
 * Output: false
 *
 * Note that there could be allowed triples (A, B, C) and (A, B, D) with C != D.
 *
 * Thoughts:
 *  S1: simulator, DFS
 *  ("BCG", 0) -> ("DCG", 1) -> ("DE", 0) -> ("A", 0)
 * 
 *  S2: BFS
 * 
                            A
                           / \
              D           D   E
             / \         / \ / \
    B       B   C       B   C   G
 * 
 *   3 bits can represent a letter, and a int (32 bits) can represent a bottom string.
 *   f("B") = "B" 
 *   f("BC") = "DC"
 *   f("BCG") = f("BC", "G") = "AEG"
 *  
 * 
 */
public class PyramidTransitionMatrix {
    
    /**
     * @param bottom: a string
     * @param allowed: a list of strings
     * @return a boolean
     */
    public boolean pyramidTransition(String bottom, List<String> allowed) {
        //Letters in all strings will be chosen from the set {'A', 'B', 'C', 'D', 'E', 'F', 'G'}.
        Map<Integer, List<Integer>> map = new HashMap<>();// Example, "ABC", key is 0 * 8 + 1 for 'AB', value is 'C' is 2

        int key;
        for(String triple : allowed){
            key = getKey(getId(triple.charAt(0)), getId(triple.charAt(1)));
            map.computeIfAbsent(key, a -> new ArrayList<>()).add(getId(triple.charAt(2)));
        }

        int n = bottom.length();
        int[] start = new int[n];
        for(int i = 0; i < n; i++ ){
            start[i] = getId(bottom.charAt(i));
        }

        return transition(start, 0, start.length - 1, map);
    }

    private boolean transition(int[] line, int i, int last, Map<Integer, List<Integer>> map){
        if(i == last){            
            i = 0;
            last--;

            if(last == 0){
                return true;
            }
        }

        int key = getKey(line[i], line[i + 1]);
        if( !map.containsKey(key) ){
            return false;
        }
        
        int tmp;
        for(int next : map.get(key)){
            tmp = line[i];
            line[i] = next;

            if(transition(line, i + 1, last, map)){
                return true;
            }

            line[i] = tmp;
        }

        return false;
    }

    private int getId(char c){
        return c - 'A';
    }

    private int getKey(int i, int j){
        return (i << 3) + j;
    }

    public static void main(String[] args) {
        final String TRUE = "true";
        final String FALSE = "false";

        String[][][] inputs = {
            {
                {"BCG"},
                {"BCD", "CGE", "DEA", "FFF"},
                {TRUE}
            },
            {
                {"AABA"},
                {"AAA", "AAB", "ABA", "ABB", "BAC"},
                {FALSE}
            },
            {
                {"ABC"},
                {"ABD", "BCE", "DEF", "FFF"},
                {TRUE}
            },
            {
                {"ABCD"},
                {"AAA", "AAC", "ABC", "ABD", "ACD", "ADB", "BAB", "BBC", "BCA", "BDC", "BDD", 
                    "CAA", "CBC", "CCD", "CDB", "DAB", "DAC", "DBD", "DCA", "DDC"},
                {TRUE}
            },
            {
                {"AACC"},
                {"AAA", "AAC", "ABC", "ABD", "ACD", "ADB", "BAB", "BBC", "BCA", "BDC", "BDD", 
                    "CAA", "CBC", "CCD", "CDB", "DAB", "DAC", "DBD", "DCA", "DDC"},
                {TRUE}
            },
            {
                {"AAAA"},
                {"AAA", "AAC", "ABC", "ABD", "ACD", "ADB", "BAB", "BBC", "BCA", "BDC", "BDD", 
                    "CAA", "CBC", "CCD", "CDB", "DAB", "DAC", "DBD", "DCA", "DDC"},
                {TRUE}
            },
            {
                {"CCCC"},
                {"AAA", "AAC", "ABC", "ABD", "ACD", "ADB", "BAB", "BBC", "BCA", "BDC", "BDD", 
                    "CAA", "CBC", "CCD", "CDB", "DAB", "DAC", "DBD", "DCA", "DDC"},
                {TRUE}
            },
            {
                {"DDDD"},
                {"AAA", "AAC", "ABC", "ABD", "ACD", "ADB", "BAB", "BBC", "BCA", "BDC", "BDD", 
                    "CAA", "CBC", "CCD", "CDB", "DAB", "DAC", "DBD", "DCA", "DDC"},
                {TRUE}
            }

        };

        PyramidTransitionMatrix sv = new PyramidTransitionMatrix();

        for (String[][] input : inputs) {
            System.out.println(String.format("\nbottom: %s, allowed: %s", input[0][0], Misc.array2String(input[1])));
            //System.out.println(String.format("\n %s - %b", input[2][0], sv.pyramidTransition(input[0][0], Arrays.asList(input[1])) ));
            
            Assert.assertEquals("", input[2][0], sv.pyramidTransition(input[0][0], Arrays.asList(input[1])) ? TRUE : FALSE);

        }

    }

}
