package graph;

import util.Misc;

import java.util.*;

/**
 *
 * Given two words (beginWord and endWord), and a dictionary's word list, find the length of shortest transformation sequence from beginWord to endWord, such that:
 *
 * Only one letter can be changed at a time.
 * Each transformed word must exist in the word list. Note that beginWord is not a transformed word.
 *
 * Note:
 *   Return 0 if there is no such transformation sequence.
 *   All words have the same length.
 *   All words contain only lowercase alphabetic characters.
 *   You may assume no duplicates in the word list.
 *   You may assume beginWord and endWord are non-empty and are not the same.
 *
 * Example 1:
 * beginWord = "hit",  endWord = "cog",  wordList = ["hot","dot","dog","lot","log","cog"]
 * Output: 5
 * Explanation: As one shortest transformation is "hit" -> "hot" -> "dot" -> "dog" -> "cog",
 * return its length 5.
 *
 * Example 2:
 * beginWord = "hit" endWord = "cog" wordList = ["hot","dot","dog","lot","log"]
 * Output: 0
 * Explanation: The endWord "cog" is not in wordList, therefore no possible transformation.
 *
 * 
 * Solutions:
 *  1. find a shortest path in Graph BFS, from start to end, every time it need try a new word for start.length * 26 times. 
 *    define m as the word.length, t as the shortest path, n as the dictionary size, 
 *    assume average it find out p possible word, p < dict.size()
 *    the time complexity is O( (m * 26) + p * m * 26 + p * p * m * 26 + ... ), it's about O( m * 26 * n  ) 
 *  2. 
 * 
 */


public class WordLadderI {


    /**
     * Solution: Graph BFS
     *
     * 这道题想明白后非常简单，其实就是求最短路径问题，自然是BFS方法，其实问题可以用Graph来很好的解释。
     * 顶点是每个字符串，如果相差一个字符，我们就可以连一条边， 一个字符串的边的数量最大值可能是 25 * L. 然后连线，形成Graph,
     * 这样就是start - end的最短路径问题. 每次我们可以从start 出发，找adjacent string, 然后 enqueue,
     * 下次再遍历下一层，这样第一次到end的时候，shortest = length + 1.
     *
     * 这题目的特点: 1. dict来代替BFS 中 visited 标记，直接remove from dict 就代表遍历过了 或者 不存在 2.
     * 都小写字母, 字符串长度固定. 问题简单化(如果不固定，就不是只换一个char这种简单情形了，会复杂的多，跟这题也会大不相同)
     *
     * Time Complexity. 有点不太确定 最差情况: 对于每一个词, 查询应该是26*wordLength.
     * 然后一直遍历完所有dict才找到答案. O(dict.size * 26*wordLength) Space 只需要一个Queue
     * 存储邻接点，最大是dict的size, 因为dict不会是规模的，所以算是O(1)
     *
     */
    public int ladderLength_BFS_oneWay(String start, String end, Set<String> dict) {
        if (start == null || end == null || dict == null) {
            return 0;
        }
        if (start.equals(end)) {
            return 0;
        }

        Set<String> visited = new HashSet<>();
        visited.add(start);

        Queue<String> queue = new LinkedList<>();
        queue.add(start);

        String curr;
        String next;
        char[] chars;
        int distance = 1;
        while (!queue.isEmpty()) {
            distance++;
            for (int k = queue.size(); k > 0; k--) {
                curr = queue.poll();
                chars = curr.toCharArray();

                for (int i = 0; i < curr.length(); i++) { //to every letter 
                    for (char c = 'a'; c <= 'z'; c++) { // to 26 possible
                        chars[i] = c;
                        next = String.valueOf(chars);

                        if (next.equals(end)) {
                            return distance;
                        }

                        if (!visited.contains(next) && dict.contains(next)) {
                            visited.add(next);
                            queue.add(next);
                        }
                    }

                    chars[i] = curr.charAt(i); //backtracking
                }
            }
        }

        return 0;
    }


    public int ladderLength_twoWay(String start, String end, Set<String> dict) {
        if (start == null || end == null || dict == null) {
            return 0;
        }
        if (start.equals(end)) {
            return 0;
        }

        dict.add(end); //for safe, words should includes the end word

        int distance = 0;

        Set<String> visited = new HashSet<>();

        Set<String>[] sets = new HashSet[2];
        sets[0] = new HashSet<>();
        sets[0].add(start);
        sets[1] = new HashSet<>();
        sets[1].add(end);

        final int size = start.length();

        Set<String> tmp;
        char[] curr;
        char old;
        String next;
        while(!sets[0].isEmpty() && !sets[0].isEmpty()){

            if(sets[0].size() < sets[1].size()){
                tmp = sets[0];
                sets[0] = sets[1];
                sets[1] = tmp;
            }

            distance++;

            tmp = new HashSet<>();
            for(Iterator<String> iterator = sets[0].iterator(); iterator.hasNext(); iterator.remove()){
                curr = iterator.next().toCharArray();

                for(int i = 0; i < size; i++){
                    old = curr[i];
                    for(char c = 'a'; c <='z'; c++ ){
                        curr[i] = c;
                        next = new String(curr);

                        if(!visited.contains(next) && dict.contains(next)){
                            if(sets[1].contains(next)){
                                return distance + 1;
                            }

                            tmp.add(next);
                            visited.add(next);
                        }
                    }
                    curr[i] = old;
                }
            }

            sets[0] = tmp;
        }

        return 0;
    }

    
    /** 
     *
     */
    public int ladderLength_BFS_Trie_oneWay(String start, String end, Set<String> dict) {
        if (start == null || end == null || dict == null) {
            return 0;
        }
        if (start.equals(end)) {
            return 0;
        }
        
        TrieNode root = new TrieNode();
        
        dict.remove(start);
        dict.add(end); 
        dict.forEach(s -> add(root, s) );

        Queue<String> queue = new LinkedList<>();
        queue.add(start);

        int m = start.length(); //all words have the same length
        String curr;
        char[] chars;
        int distance = 1;
        while (!queue.isEmpty()) {
            distance++;
            for (int k = queue.size(); k > 0; k--) {
                curr = queue.poll();
                chars = curr.toCharArray();
                
                for (int i = 0; i < m; i++) {
                    chars[i] = '*';
                    if (searchAndAdd(root, chars, 0, end, queue)) {
                        return distance;
                    }
                    chars[i] = curr.charAt(i);
                }
            }
        }
        
        return 0;
    }
    
    class TrieNode{
        TrieNode[] children = new TrieNode[26];
        String word;
        
        boolean visited;
    }
    
    private void add(TrieNode root, String word){
        TrieNode curr = root;
        
        int i;
        for(char c : word.toCharArray()){
            i = c - 'a';
            if( curr.children[i] == null ){
                curr.children[i] = new TrieNode();
            }
            
            curr = curr.children[i];
        }
        
        curr.word = word;
    }
    
    private boolean searchAndAdd(TrieNode node, char[] word, int i, String end, Queue<String> queue){
        while(node != null && i < word.length){
            if(word[i] == '*'){
                for(TrieNode child : node.children){
                    if(child != null && searchAndAdd(child, word, i + 1, end, queue)){
                        return true;
                    }
                }
                return false;
            }else{
                node = node.children[word[i] - 'a'];
                i++;
            }
        }
        
        if(node == null || node.visited){
            return false;
        }
        
        if(end.equals(node.word)){
            return true;
        }
        
        node.visited = true;
        queue.add(node.word);
        
        return false;
    }
    

    public static void main(String[] args) {
        //init
        String[][][] input = {
                {{"a", "c"}, {"a", "b", "c"}},
                {{"hot", "dog"}, {"hot", "dog", "dot"}},
                {{"hit", "cog"}, {"hot", "dot", "dog", "lot", "log", "cog"}},
                {{"hit", "cog"}, {"hot", "dot", "dog", "lot", "log"}}

        };


        int[] expects = {
                2,
                3,
                5,
                0
        };

        WordLadderI sv = new WordLadderI();

        for (int i = 0; i < input.length; i++) {
            System.out.println(String.format("\nconvert %s to %s,  List: [%s] \n expect: %d", input[i][0][0], input[i][0][1], Misc.array2String(input[i][1]), expects[i]));

            System.out.println(sv.ladderLength_BFS_oneWay(input[i][0][0], input[i][0][1], sv.convert(input[i][1])));
            System.out.println(sv.ladderLength_twoWay(input[i][0][0], input[i][0][1], sv.convert(input[i][1])));
            System.out.println(sv.ladderLength_BFS_Trie_oneWay(input[i][0][0], input[i][0][1], sv.convert(input[i][1])));
        }

    }

    private Set<String> convert(String[] dict){
        Set<String> ret = new HashSet<>();
        for(String word : dict){
            ret.add(word);
        }

        return ret;
    }
}
