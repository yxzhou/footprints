package game;

import util.Misc;

import java.util.*;


/**
 *
 *
 * You are playing the following Flip Game with your friend: 
 * Given a string that contains only these two characters: + and -, 
 * you and your friend take turns to flip two consecutive "++" into "--".
 * The game ends when a person can no longer make a move and therefore the other person will be the winner.
 * 
 * Q1, Write a function to compute all possible states of the string after one valid move.  Lintcode #914
 *  For example, given s = "++++", after one move, it may become one of the following states:
 *  [
 *    "--++",
 *    "+--+",
 *    "++--"
 *  ]
 *  If there is no valid move, return an empty list [].
 * 
 * Q2, Write a function to determine if the starting player can guarantee a win. Lintcode #913
 *  For example, given s = "++++", return true. The starting player can guarantee a win by flipping the middle "++" to become "+--+".
 *  
 *  Follow up:
 *  Derive your algorithm's runtime complexity.
 */
public class FlipGame {

    /**
     * 
     * Time Complexity O(n) Space O(n)
     */
    public List<String> generatePossibleNextMoves(String s) {
        List<String> result = new ArrayList<>();
        
        if(null == s || s.length() < 2){
            return result;
        }
        
        for(int i = 1; i < s.length(); i++){
            if(s.charAt(i) == '+' && s.charAt(i - 1) == '+'){
                result.add(s.substring(0, i - 1) + "--" + s.substring(i+1));
            }
        }
        
        return result;
    }
    
    /**
     * @param s: the given string
     * @return if the starting player can guarantee a win
     * 
     * DFS
     * Time Complexity O(n^n) 
     */
    public boolean canWin(String s) {
        if(null == s || s.length() < 2){
            return false;
        }
        
        return canWinHelper(s);
    }
    
    private boolean canWinHelper(String s){
        boolean result = false;
        
        for(int i = 1; i < s.length(); i++){
            if(s.charAt(i) == '+' && s.charAt(i - 1) == '+'){
                result = result || !canWinHelper(s.substring(0, i - 1) + "--" + s.substring(i + 1));
            }
        }
        
        return result;
    }
    
    /**
     * @param s: the given string
     * @return if the starting player can guarantee a win
     * 
     * DFS + backtracking
     * Time Complexity O(n^n) 
     */
    public boolean canWin_n(String s) {
        if(null == s || s.length() < 2){
            return false;
        }
        
        return canWinHelper(s.toCharArray());
    }
    
    private boolean canWinHelper(char[] s){
        boolean result;
        for(int i = 1; i < s.length; i++){
            if(s[i - 1] == '+' && s[i] == '+'){
                s[i - 1] = s[i] = '-';
                
                result = !canWinHelper(s);
                
                s[i - 1] = s[i] = '+';
                
                if(result){
                    return true;
                }
            }
        }
        
        return false;
    }
    
    /**
     * @param s: the given string
     * @return if the starting player can guarantee a win
     * 
     * DFS + memorization
     * Time Complexity O(n * 2^n)   状态数 * 决策数目 * 转移费用 = 2^n * n * 1
     */
    public boolean canWin_x(String s) {
        if(s == null){
            return false;
        }

        return helper(s.toCharArray(), new HashMap<>());
    }

    private boolean helper(char[] s, Map<String, Boolean> cache){
        String state = String.valueOf(s);
        if(cache.containsKey(state)){
            return cache.get(state);
        }

        boolean result = false;
        for(int i = 1; i < s.length; i++){
            if(s[i - 1] == '+' && s[i] == '+'){
                s[i - 1] = s[i] = '-';

                result = result || !helper(s, cache);

                s[i - 1] = s[i] = '+';

                if(result){
                    break;
                }
            }
        }

        cache.put(state, result);
        return result;
    }
    
    public boolean canWin_x2(String s) {
        if(null == s || s.length() < 2){
            return false;
        }
        
        BitSet bits = new BitSet(s.length());
        for(int i = 0; i < s.length(); i++){
            if(s.charAt(i) == '+'){
                bits.set(i);
            }
        }

        return helper(bits, new HashMap<>());
    }

    private boolean helper(BitSet state, Map<BitSet, Boolean> cache){
        if(cache.containsKey(state)){
            return cache.get(state);
        }

        boolean result = false;
        for(int i = 1; i < state.size(); i++){
            if(state.get(i - 1) && state.get(i)){
                state.set(i - 1, false);
                state.set(i, false);

                result = result || !helper(state, cache);

                state.set(i - 1, true);
                state.set(i, true);

                if(result){
                    break;
                }
            }
        }

        cache.put(state, result);
        return result;
    }
    
    /**
     * @param s: the given string
     * @return if the starting player can guarantee a win
     * 
     * 
     */
    public boolean canWin_wrong(String s) {
        if(s == null || s.length() < 2){
            return false;
        }

        List<Integer> counts = new LinkedList<>();
        int count = 0;
        int max = 0;
        s += '-';
        for(char c : s.toCharArray()){
            if(c == '+'){
                count++;
            }else{
                max = Math.max(max, count);
                counts.add(count);
                count = 0;
            }
        }

        boolean[] f = new boolean[Math.max(max + 1, 5)];
        f[2] = f[3] = f[4] = true;
        for(int i = 5; i <= max; i++){
            f[i] = !(f[i - 2] && f[i - 3]) ;
            if( f[i] ){
                continue;
            }

            for(int left = 2, right = i - 4; left < right ; left++, right-- ){
                f[i] = !(f[i - 2] ^ f[i - 3]) ;
                if( f[i] ){
                    break;
                }
            }
        }

        boolean result = false;
        for(int x : counts){
            result ^= f[x];
        }

        return result;
    }

    // ++    True
    // +++   T
    // ++++  T

    // f[5] -> !f[3]  or !f[2]   
    // f(6) -> !f(4)  or !f(3)  or  !( f(2) ^ f(2) ) )   
    // f[7] -> !f[5] or !f[4] or !( f[2] ^ f[3] )
    // f[8] -> !f[6] or !f[5] or !( f[2] ^ f[4] ) or !( f[3] ^ f[3] )
    
    public static void main(String[] args){
        FlipGame sv = new FlipGame();
        
        String[] input = {
                    "",
                    "+",
                    "++",
                    "+++",
                    "++++",
                    "+++++",
                    "++++++",
                    "+++++++",
                    "++++++++",
                    "+++++++++",
                    "++++-+++++",
                    "++++-++++++",
        };
        
        for(String s : input){
            System.out.println(String.format(" \n Input: %s", s));
            
            System.out.println(" generatePossibleNextMoves: " );
            Misc.printArrayList(sv.generatePossibleNextMoves(s));
            
            System.out.println(" canWin: " + sv.canWin(s) + " " + sv.canWin_n(s) + " " + sv.canWin_x(s) + " " + sv.canWin_x2(s));
        }
        
    }
}
