/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package stringmatching;

/**
 * _https://www.lintcode.com/problem/1127
 *
 * 
 * Given a string s and a list of strings dict, you need to add a closed pair of bold tag and to wrap the substrings in
 * s that exist in dict. If two such substrings overlap, you need to wrap them together by only one pair of closed bold
 * tag. Also, if two substrings wrapped by bold tags are consecutive, you need to combine them.
 *
 *
 * The given dict won't contain duplicates, and its length won't exceed 100. 
 * All strings entered have a length in the range [1,1000]. 
 * 
 * Example 
 * Input: s = "abcxyz123" dict = ["abc","123"] 
 * Output: "<b>abc</b>xyz<b>123</b>" 
 * 
 * Input: s = "aaabbcc" dict = ["aaa","aab","bc"] 
 * Output: "<b>aaabbc</b>c"
 * 
 */
public class BoldTagsInStringII {
    
    /**
     * @param s: a string
     * @param dict: a list of strings
     * @return return s with bold tags
     */
    public String addBoldTag(String s, String[] dict) {
        TrieNode root = new TrieNode();

        for(String d : dict){
            add(root, d);
        }

        StringBuilder result = new StringBuilder();

        int n = s.length();
        int k;
        for(int l = 0, r = 0, i ; r < n; ){
            for( i = r ; i <= r && i < n; i++){
                k = getLongest(root, s, i);
                r = Math.max(r, k + 1);
            }
            
            if(l < r){
                result.append("<b>").append(s.substring(l, r)).append("</b>");           
            }else{
                result.append(s.charAt(l));
                r++;
            }

            l = r;
        }

        return result.toString();

    }

    private void add(TrieNode root, String s){
        TrieNode curr = root;

        int c;
        for(int i = 0; i < s.length(); i++){
            c = s.charAt(i);

            if(curr.nexts[c] == null){
                curr.nexts[c] = new TrieNode();
            }

            curr = curr.nexts[c];
        }

        curr.isLeaf = true;
    }

    private int getLongest(TrieNode root, String s, int i){
        TrieNode curr = root;

        int c;
        int j = -1;
        for( ; i < s.length(); i++){
            c = s.charAt(i);

            if(curr.nexts[c] == null){
                break;
            }

            curr = curr.nexts[c];
            
            if( curr.isLeaf ){
                j = i;
            }
            
        }

        return j;
    }
    
    class TrieNode{
        TrieNode[] nexts = new TrieNode[128];
        boolean isLeaf = false;
    }
}
