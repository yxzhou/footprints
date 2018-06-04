package fgafa.easy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 
 * From Careercup 9.2
 * 
 * The Game of Master Mind is played as follows:
 * - The computer has four slots containing balls that are red (R), yellow (Y), green (G) or blue (B).
 *   For example, the computer might have RGGB (eg, Slot #1 is red, Slots #2 and #3 are green, #4 is blue).
 * - You, the user, are trying to guess the solution. You might, for example, guess YRGB.
 * - When you guess right color for the right slot, you get a “hit”. 
 *   If you guess a color that exists but is in the wrong slot, you get a “psuedo-hit”. 
 *   For example, the guess YRGB has 2 hits and one pseudo-hit.
 * 
 * For each guess, you are told the number of hits and pseudo hits. Write a method that, given
 * a guess and a solution, returns the number of hits and pseudo hits.
 * 
 *
 */
public class BullsAndCows00
{

  public int hits = 0;
  public int pseudoHits = 0;

  public String toString(){
    StringBuffer sb = new StringBuffer();
    
    sb.append("[hits-");
    sb.append(hits);
    sb.append(";pseudoHits-");
    sb.append(pseudoHits);
    sb.append("]");
    
    return sb.toString();
    
  }
  
  

  /**
   * @param args
   */
    public static void main(String[] args) {
        String[] solution = { "RGGB", "RGGB", "RRGB", "RYGB" };
        String[] guess = { "YRGB", "GRGB", "YGGB", "RRRR" };

        for (int i = 0; i < solution.length; i++) {
            System.out.println(String.format("\n--input: solution- %s, guess- %s ", solution[i], guess[i]));
            System.out.println(String.format("--: %s", estimate_wrong(guess[i].toCharArray(), solution[i].toCharArray())));
            System.out.println(String.format("--: %s", estimate_n(guess[i].toCharArray(), solution[i].toCharArray())));
        }

    }

  /*wrong*/
  public static BullsAndCows00 estimate_wrong(char[] guess, char[] solution) {
    BullsAndCows00 res = new BullsAndCows00();
    
    int solution_mask = 0;
    for (int i = 0; i < 4; ++i) {
      solution_mask |= 1 << (1 + solution[i] - 'A');
    }
    for (int i = 0; i < 4; ++i) {
      if (guess[i] == solution[i]) {
        ++res.hits;
      }else if ((solution_mask & (1 << (1 + guess[i] - 'A'))) > 0) {
        ++res.pseudoHits;
      }
    }
    return res;
  }

  
  public static BullsAndCows00 estimate_n(char[] guess, char[] solution) {
      BullsAndCows00 res = new BullsAndCows00();
      
      HashMap<Character, Integer/*count*/> counts = new HashMap<>();
      List<Character> positions = new ArrayList<>();
      
      for (int i = 0; i < 4; ++i) {
        if (guess[i] == solution[i]) {
          ++res.hits;
        }else{
            if(counts.containsKey(solution[i])){
                counts.put(solution[i], counts.get(solution[i]) + 1);
            }else{
                counts.put(solution[i], 1);
            }
            
            positions.add(guess[i]);
        }
      }
      
      for(Character c : positions ){
          if(counts.containsKey(c) && counts.get(c) > 1){
              ++res.pseudoHits;
              
              counts.put(c, counts.get(c) - 1);
          }
      }
      
      return res;
    }
  
}
