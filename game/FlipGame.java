package fgafa.game;

import java.util.ArrayList;
import java.util.List;

import fgafa.util.Misc;


/**
 * 
 * You are playing the following Flip Game with your friend: 
 * Given a string that contains only these two characters: + and -, 
 * you and your friend take turns to flip two consecutive "++" into "--".
 * The game ends when a person can no longer make a move and therefore the other person will be the winner.
    
   Q1, Write a function to compute all possible states of the string after one valid move.
    For example, given s = "++++", after one move, it may become one of the following states:
    [
      "--++",
      "+--+",
      "++--"
    ]
    If there is no valid move, return an empty list [].
 * 
 * Q2, Write a function to determine if the starting player can guarantee a win.
    For example, given s = "++++", return true. The starting player can guarantee a win by flipping the middle "++" to become "+--+".
    
    Follow up:
    Derive your algorithm's runtime complexity.
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
     * 
     * Time Complexity O(n^n or 2^n) 
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
                    "+++++++++"
        };
        
        for(String s : input){
            System.out.println(String.format(" \n Input: %s", s));
            
            System.out.println(" generatePossibleNextMoves: " );
            Misc.printArrayList(sv.generatePossibleNextMoves(s));
            
            System.out.println(" canWin: " + sv.canWin(s) + " " + sv.canWin_n(s) );
        }
        
    }
}
