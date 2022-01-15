package array;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Stack;
import org.junit.Assert;

/**
 * _https://www.lintcode.com/problem/834
 * 
 * Given a string s which contains only lowercase letters, remove duplicate letters so that every letter appear once and
 * only once. You must make sure your result is the smallest in lexicographical order among all possible results.
 * 
 * Example1
 * Input: s = "bcabc" Output: "abc"
 * 
 * Example2
 * Input: s = "cbacdcbc" Output: "acdb"
 * 
 */

public class RemoveDuplicateLetters {

    /**
     * traverse once
     * 
     * @param s
     * @return 
     */
    public String removeDuplicateLetters(String s) {
        if (null == s ) {
            return "";
        }

        Map<Integer, List<Integer>> positions = new HashMap<>(26); //chars[0] is the positions of 'a'
        for (int i = 0, c; i < s.length(); i++) {
            c = s.charAt(i) - 'a';

            if (!positions.containsKey(c)) {
                positions.put(c, new LinkedList<>());
            }

            positions.get(c).add(i + 1);
        }

        StringBuilder result = new StringBuilder();
        int curr = 0;
        int pre = 0;
        for (int k = positions.size(); k > 0; k--) {
            for (int i = 0; i < 26; i++) {
                if (positions.containsKey(i)) {
                    for (int p : positions.get(i)) {
                        if (p > pre) {
                            curr = p;
                            break;
                        }
                    }

                    if (validToStartWith(i, positions, curr)) {
                        pre = curr;

                        result.append((char) (i + 'a'));
                        positions.remove(i);

                        break;
                    }
                }
            }
        }

        return result.toString();
    }

    private boolean validToStartWith(int currKey, Map<Integer, List<Integer>> positions, int base) {
        List<Integer> list;
        for (int key : positions.keySet()) {
            if (key != currKey) {
                list = positions.get(key);
                if (base > list.get(list.size() - 1)) {
                    return false;
                }
            }
        }

        return true;
    }
    
    public String removeDuplicateLetters_n(String s) {
        if(s == null){
            return "";
        }
        
        ArrayList<Integer>[] indexes = new ArrayList[26];
        for(int i = 0, c, n = s.length(); i < n; i++){
            c = s.charAt(i) - 'a';
            
            if(indexes[c] == null){
                indexes[c] = new ArrayList<>();
            }

            indexes[c].add(i);
        }

        int[] letters = new int[26];
        int end = 0;
        PriorityQueue<Pair> lastIndexes = new PriorityQueue<>((p1, p2) -> Integer.compare(p1.position, p2.position)); //the minimum heap of last index
        
        for(int c = 0; c < indexes.length; c++ ){
            if(indexes[c] != null){
                letters[end++] = c;
                
                lastIndexes.add(new Pair(c, indexes[c].get(indexes[c].size() - 1)));
            }
        }
        
        int low = 0;
        for(int start = 0; start < end; start++){
            while(!lastIndexes.isEmpty() && indexes[lastIndexes.peek().value] == null){
                lastIndexes.poll();
            }
            
            for(int i = start; i < end; i++){
                int p = Collections.binarySearch(indexes[letters[i]], low);
                if(p < 0){
                   p = 0 - p - 1; 
                }
                
                if(indexes[letters[i]].get(p) <= lastIndexes.peek().position){
                    low = indexes[letters[i]].get(p);
                    
                    int c = letters[i];
                    System.arraycopy(letters, start, letters, start + 1, i - start);
                    letters[start] = c; 
                    //start++;
                    
                    indexes[c] = null;
                    
                    break;
                }
            }
        }
        
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < end; i++){
            sb.append((char)(letters[i] + 'a'));
        }
        return sb.toString();
    }
    
    class Pair{
        int value; //value
        int position; //position
        
        Pair(int value, int position){
            this.value = value;
            this.position = position;
        }
    }
    
    /**
     * traverse twice
     * 
     * Solution:
     *   step 1, traverse the input string, count the letters
     *   step 2, traverse the input string again, with a stack to store the result
     *      if the letter is smaller than the top element of the stack in lexicographical and the top letter will appear
     *      again, 
     * 
     * @param s
     * @return 
     */
    public String removeDuplicateLetters_t2(String s) {
        if (s == null) {
            return "";
        }
        
        int n = s.length();
        
        int[] counts = new int[26];
        for(int i = 0; i < n; i++){
            counts[s.charAt(i) - 'a']++;
        }
        
        Stack<Integer> stack = new Stack<>();
        
        int c;
        for(int i = 0; i < n; i++){
            c = s.charAt(i) - 'a';
            if(!stack.contains(c)){
                while(!stack.isEmpty() && c < stack.peek() && counts[stack.peek()] > 0 ){
                    stack.pop();
                }

                stack.add(c);
            }
            
            counts[c]--;
        }
        
        StringBuilder sb = new StringBuilder();
        while(!stack.isEmpty()){
            sb.append((char)(stack.pop() + 'a'));
        }
        return sb.reverse().toString();
    }
        

    public static void main(String[] args) {
        String[][] inputs = {
            {"bcabc", "abc"},
            {"cbacdcbc", "acdb"},
            {"cbabc", "abc"},
            {"fceadb", "fceadb"},
            {"cbaddabaa", "cadb"},
            {"lozeuxznadhnwwntjluowjurpmfvzubcfxylkdmeksatrlkybj", "adhnjlowpfvzubcxkmestry"}
        };

        RemoveDuplicateLetters sv = new RemoveDuplicateLetters();

        for (int i = 0; i < inputs.length; i++) {
            System.out.println(String.format("Input: %s", inputs[i][0]));

            Assert.assertEquals(inputs[i][1], sv.removeDuplicateLetters(inputs[i][0]));
            Assert.assertEquals(inputs[i][1], sv.removeDuplicateLetters_n(inputs[i][0]));
            
            Assert.assertEquals(inputs[i][1], sv.removeDuplicateLetters_t2(inputs[i][0]));
        }
    }

}
