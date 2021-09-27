package dailyCoding2.google;


import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;

/**
 *
 * This problem was asked by Google.
 *
 * You are given an N by N matrix of random letters and a dictionary of words. Find the maximum number of words that can be packed on the board from the given dictionary.
 *
 * A word is considered to be able to be packed on the board if:
 *
 * It can be found in the dictionary
 * It can be constructed from untaken letters by other words found so far on the board
 * The letters are adjacent to each other (vertically and horizontally, not diagonally).
 * Each tile can be visited only once by any word.
 *
 * For example, given the following dictionary:
 *
 * { 'eat', 'rain', 'in', 'rat' }
 * and matrix:
 *
 * [['e', 'a', 'n'],
 *  ['t', 't', 'i'],
 *  ['a', 'r', 'a']]
 * Your function should return 3, since we can make the words 'eat', 'in', and 'rat' without them touching each other. We could have alternatively made 'eat' and 'rain', but that would be incorrect since that's only 2 words.
 *
 */


public class MaxWord {

    class TrieNode{
        TrieNode[] next = new TrieNode[26];

        String word = null; // null

        boolean isWord(){
            return word != null;
        }
    }


    public Set<String> maxWords(TrieNode root, char[][] matrix) {
        //todo: check input

        Set<String> result = new HashSet<>();

        helper(root, matrix, new HashSet<>(), new boolean[matrix.length][matrix[0].length], 0, result);

        return result;
    }


    int[][] diffs = { {1, 0}, {-1, 0}, {0, 1}, {0, -1} };

    private void helper(TrieNode root, char[][] matrix, Set<String> selected, boolean[][] visited, int position, Set<String > result){
        //check the position
        if(position == matrix.length * matrix[0].length){
            if(selected.size() > result.size()){
                result.clear();
                result.addAll(selected);
            }

            return;
        }

        //not select the char at the position in matrix
        helper(root, matrix, selected, visited, position + 1, result);

        int rowNum = position / matrix.length;
        int colNum = position % matrix.length;

        if( visited[rowNum][colNum] ){
            return;
        }

        //select the char at the position in matrix
        dfs(root, root, matrix, selected, visited, rowNum, colNum, position + 1, result);

    }

    private void dfs(TrieNode root, TrieNode trieNode, char[][] matrix, Set<String> selected, boolean[][] visited, int r, int c, int position, Set<String > result){
        //check
        if(r < 0 || r >= matrix.length || c < 0 || c >= matrix[0].length || visited[r][c]){
            return;
        }

        int i = matrix[r][c] - 'a';
        if(trieNode.next[i] == null){
            return;
        }
        trieNode = trieNode.next[i];

        visited[r][c] = true;

        if(trieNode.isWord()){
            boolean newAdd = selected.add(trieNode.word);

            if(newAdd) {
                helper(root, matrix, selected, visited, position, result);
                selected.remove(trieNode.word);
            }
        }else{
            for(int j = 0; j < diffs.length; j++){
                dfs(root, trieNode, matrix, selected, visited, r + diffs[j][0],c + diffs[j][1], position, result);
            }
        }

        visited[r][c] = false;
    }

    @Test
    public void test(){

        TrieNode root = new TrieNode();
        String[] dictionary = {"eat", "rain", "in", "rat"};

        for(String word : dictionary){
            buildTrie(root, word);
        }

        Assert.assertEquals(new HashSet<>(Arrays.asList("eat","in","rat")), maxWords(root,
                new char[][]{ {'e', 'a', 'n'},
                        {'t', 't', 'i'},
                        {'a', 'r', 'a'} }));

    }

    private void buildTrie(TrieNode root, String word){
        TrieNode curr = root;

        for(char c : word.toCharArray()){
            int i = c - 'a';
            if(curr.next[i] == null){
                curr.next[i] = new TrieNode();
            }

            curr = curr.next[i];
        }

        curr.word = word;
    }
}
