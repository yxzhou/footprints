package dailyCoding;

import java.util.*;

import util.Misc;

/**
 *
 * Given a dictionary of words and a string made up of those words (no spaces), return the original sentence in a list.
 * If there is more than one possible reconstruction, return any of them. If there is no possible reconstruction, then return null.
 For example,
   given the set of words 'quick', 'brown', 'the', 'fox', and the string "thequickbrownfox", you should return ['the', 'quick', 'brown', 'fox'].
   Given the set of words 'bed', 'bath', 'bedbath', 'and', 'beyond', and the string "bedbathandbeyond", return either ['bed', 'bath', 'and', 'beyond] or ['bedbath', 'and', 'beyond'].
 *
 * Tags: Microsoft, Trie, DFS
 *
 * Points:
 *   1 memorization search
 *   2 trie tree for dictionary
 */

public class SplitSentence {
    public List<String> getSentence(String text, Set<String> dictionary){
        List<String> result = new ArrayList<>();

        if(null == text || text.isEmpty() || null == dictionary || dictionary.isEmpty()){
            return result;
        }

        getSentence(text, 0, dictionary, result, new boolean[text.length() + 1]);

        return result.isEmpty()? null : result;
    }

    private boolean getSentence(String text, int index, Set<String> dictionary, List<String> result, boolean[] notSplitable){
        if(index >= text.length()){
            return true;
        }

        if(notSplitable[index]){
            return false;
        }

        for(int i = index + 1; i <= text.length(); i++){
            String curr = text.substring(index, i);

            if(dictionary.contains(curr) && !notSplitable[i]){
                result.add(curr);

                if(getSentence(text, i, dictionary, result, notSplitable)){
                    return true;
                }

                result.remove(result.size() - 1);
                notSplitable[i] = true;
            }
        }

        return false;
    }

    public static void main(String[] args){
        String[][][] inputs = {
                {{"quick", "brown", "the", "fox"}, {"thequickbrownfox"}},
                {{"bed", "bath", "bedbath", "and", "beyond"}, {"bedbathandbeyond"}},
                {{"from", "waterloo", "hi", "am", "yes", "i", "a", "student"}, {"iamastudentfromwaterloo"}}
        };

        SplitSentence sv = new SplitSentence();

        for(String[][] input : inputs){
            //System.out.println();

            Misc.printList(sv.getSentence(input[1][0],  new HashSet<String>(Arrays.asList(input[0]))));
        }

    }
}
