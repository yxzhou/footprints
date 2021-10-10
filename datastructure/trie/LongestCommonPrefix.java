package datastructure.trie;

/**
 * 
 * Write a function to find the longest common prefix string among an array of strings.
 * 
 * @author yxzhou
 *
 */

public class LongestCommonPrefix {

    public String longestCommonPrefix(String[] strs) {
        String result = "";

        //check
        if (strs == null || strs.length == 0) {
            return result;
        }
        
        char[] base = strs[0].toCharArray();
        char c;
        for(int i = 0; i < base.length; i++ ){
            c = base[i];
            for(int j = 1; j < strs.length; j++){
                if(j >= strs[j].length() || c != strs[j].charAt(i)){
                    return String.valueOf(base, 0, i);
                }
            }
        }
        
        return String.valueOf(base);
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        System.out.println("--a test 122-");
        System.out.println("abcd".substring(0, 0));

        LongestCommonPrefix sv = new LongestCommonPrefix();

        String[] strs = new String[51];
        for(int i = 0; i < 50; i++){
            strs[i] = "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa";
        }
        strs[50] = "aaaa";

        System.out.println(sv.longestCommonPrefix(strs));
        
    }

}
