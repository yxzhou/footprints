package fgafa.array.swapReverseRotate;

import java.util.*;

import org.junit.Assert;
import org.junit.Test;

/**
 * Given a string and a set of delimiters, reverse the words in the string while maintaining the relative order of the delimiters. For example, given "hello/world:here", return "here/world:hello"
 * Follow-up: Does your solution work for the following cases: "hello/world:here/", "hello//world:here"
 *
 * Tags: facebook
 */

public class ReverseWordsII {

    public String reverseWords(String words, Set<Character> delimiters){
        if(null == words || 0 == words.length() || null == delimiters || 0 == delimiters.size()){
            return words;
        }

        Stack<String> wordsStack = new Stack<>();
        Queue<Character> delimitersQuery = new LinkedList<>();

        int left = 0;
        for(int right = 0; right < words.length(); right++){
            if(delimiters.contains(words.charAt(right)) ){
                wordsStack.push(words.substring(left, right));
                left = right + 1;

                delimitersQuery.add(words.charAt(right));
            }
        }

        wordsStack.push(words.substring(left, words.length()));

        StringBuilder result = new StringBuilder();
        while(!delimitersQuery.isEmpty()){
            result.append(wordsStack.pop()).append(delimitersQuery.poll());
        }

        result.append(wordsStack.pop());

        return result.toString();

    }

    @Test public void test(){
        Assert.assertEquals("here/world:hello", reverseWords("hello/world:here", new HashSet<Character>(Arrays.asList(new Character[]{'/', ':'}))));

        Assert.assertEquals("/here:world/hello", reverseWords("hello/world:here/", new HashSet<Character>(Arrays.asList(new Character[]{'/', ':'}))));

        Assert.assertEquals("here/world/:hello", reverseWords("hello//world:here", new HashSet<Character>(Arrays.asList(new Character[]{'/', ':'}))));
    }

}
