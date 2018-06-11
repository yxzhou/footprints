package fgafa.todo.foo;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

/**
 * _http://www.jiuzhang.com/solution/word-sorting
 *
 * Give a new alphabet, such as {c,b,a,d,e,f,g,h,i,j,k,l,m,n,o,p,q,r,s,t,u,v,w,x,y,z}.
 * Sort the string array according to the new alphabet
 *
 *
 * S:
 *    1) implement a new comparator
 *    2) sort with default comparator, then replace
 */
public class WordSorting {
    public String[] wordSorting(char[] alphabet, String[] words){
        if(null == alphabet || null == words){
            return words;
        }

        Map<Character, Integer> positions = new HashMap<>(alphabet.length);
        for(int i = 0; i < alphabet.length; i++ ){
            positions.put(alphabet[i], i);
        }

        Arrays.sort(words, new Comparator<String>() {
            @Override public int compare(String o1, String o2) {
                for(int i = 0; i < Math.min(o1.length(), o2.length()); i++ ){
                    int diff = positions.get(o2.charAt(i)) - positions.get(o1.charAt(i));
                    if( diff != 0 ){
                        return diff;
                    }
                }

                return o2.length() - o1.length();
            }
        });
        return words;
    }

}
