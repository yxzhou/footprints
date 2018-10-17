package fgafa.game.palindrome;

import java.util.HashMap;
import java.util.Map;

/**
 * A palindrome is a String that is spelled the same forward and backwards. Given a String base that may or may not be a palindrome,
 * we can always force base to be a palindrome by adding letters to it. For example, given the word "RACE", we could add the letters
 * "CAR" to its back to get "RACECAR" (quotes for clarity only). However, we are not restricted to adding letters at the back.
 * For example, we could also add the letters "ECA" to the front to get "ECARACE". In fact, we can add letters anywhere in the word,
 * so we could also get "ERCACRE" by adding an 'E' at the beginning, a 'C' after the 'R', and another 'R' before the final 'E'.
 *
 * Your task is to make base into a palindrome by adding as few letters as possible and return the resulting String. When there is more
 * than one palindrome of minimal length that can be made, return the lexicographically earliest (that is, the one that occurs first in
 * alphabetical order).
 *
 *
 * Constraints
 * ============================================================================
 * -	base contains between 1 and 25 characters, inclusive.
 * -	Every character in base is an uppercase letter ('A'-'Z').
 *
 * Examples
 * ============================================================================
 *
 * 0)	"RACE"
 * Returns: "ECARACE"
 *  To make "RACE" into a palindrome, we must add at least three letters. However, there are eight ways to do this by adding exactly
 *  three letters:  "ECARACE"  "ECRARCE"  "ERACARE"  "ERCACRE"  "RACECAR"  "RAECEAR"  "REACAER"  "RECACER"
 *  Of these alternatives, "ECARACE" is the lexicographically earliest.
 *
 *
 * MY NOTES
 * ============================================================================
 *  This is a hard problem, but once we know how to solve it, it is pretty easy. The difficulty is in how to reduce this problem to
 *  a smaller one. This problem can be solved using DP however part of the statement is misleading, like "In fact, we can add letters
 *  anywhere in the word". Till now I discover those problems solvable by DP all share a pattern, which is computing the minimum/maximum
 *  number of operations using a limited set of operations.
 *
 *  Once we are aware of this pattern, we need to observe carefully to figure out the recurrence relation. There are also patterns
 *  for the recurrence relation:
 *     (1)  reduce a problem of size N to that of size N-1  (Chop-one-end reduction )
 *     (2)  reduce a problem of size N to subproblems whose total size is N
 *          For example,  [i,j]->[i,k]+[k+1,j]              (Dividing reduction )
 *     (3)  reduce a problem of size N to that of size N-k  (Chop-two-end reduction )
 *
 * The codes are not perfect, as it can introduce a Hashmap to implement memorisation.
 *
 */

public class ShortestPalindromeII {

    public String shortest(String base){
        if(null == base){
            return base;
        }

        return shortest(base, new HashMap<>());
    }

    private String shortest(String base, Map<String, String> cache){
        if(cache.containsKey(base)){
            return cache.get(base);
        }

        if(base.length() < 2){
            return base;
        }

        int length = base.length();
        char head = base.charAt(0);
        char tail = base.charAt(length - 1);

        String result;
        if(head == tail){
            result = head + shortest(base.substring(1, length - 1), cache) + tail;
        }else{
            String keepHead = head + shortest(base.substring(1, length), cache) + head;
            String keepTail = tail + shortest(base.substring(0, length - 1), cache) + tail;

            if(keepHead.length() == keepTail.length()){
                result = keepHead.compareTo(keepTail) < 0 ? keepHead : keepTail;
            }else{
                result = keepHead.length() < keepTail.length()? keepHead : keepTail;
            }
        }
        cache.put(base, result);
        return result;
    }


    public static void main(String[] args){
        String[][] inputs = {
                {"RACE", "ECARACE"},
                {"ZAZZ", "ZAZAZ"},
                {"TOPCODER", "REDTOCPCOTDER"},
                {"Q", "Q"},
                {"MADAMIMADAM", "MADAMIMADAM"},
                {"ALRCAGOEUAOEURGCOEUOOIGFA", "AFLRCAGIOEOUAEOCEGRURGECOEAUOEOIGACRLFA"}
        };

        ShortestPalindromeII sv = new ShortestPalindromeII();

        for(int i = 0; i < inputs.length; i++){
            System.out.println(inputs[i][0]);
            System.out.println(sv.shortest(inputs[i][0]));
            System.out.println(inputs[i][1]);
            System.out.println();
        }

    }

}
