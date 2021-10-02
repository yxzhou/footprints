package todo.foo;

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
 * Notes:
 *   The length of word does not exceed 100.
 *   The number of words does not exceed 10000.
 *   You can assume that the given new alphabet is a 26-character string. Only lowercase letters.
 *   
 *   Example 1:
 *   Input: Alphabet = "zbadefghijklmnopqrstuvwxyc", array = ["bca","czb","za","zba","ade"]
 *   Output: ["zba","za","bca","ade","czb"]
 * 
 *   Example 2:
 *   Input: Alphabet = "cbadefghijklmnopqrstuvwxyz", array = ["cab", "cba", "abc"]
 *   Output: ["cba", "cab", "abc"]
 *
 *
 * S:
 *    1) implement a new comparator
 *    2) sort with default comparator, then replace
 */
public class WordSorting {
    public String[] wordSort_comparator_1(char[] alphabet, String[] words){
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

    public String[] wordSort_comparator(char[] alphabet, String[] words) {
        if(null == alphabet || null == words){
            return words;
        }
                
        Map<Character, Integer> map = new HashMap<>();
        for(int i = alphabet.length - 1; i >= 0; i--){
            map.put(alphabet[i], i);
        }

        Arrays.sort(words, (s1, s2) -> {
            int l1 = s1.length(); 
            int l2 = s2.length();
            int diff;
            for( int i = 0; i < l1 && i < l2; i++ ){
                diff = map.get(s1.charAt(i)) - map.get(s2.charAt(i));
                if( diff != 0  ){
                    return diff;
                }
            }

            return l1 - l2;
        } );

        return words;
    }
        
    public String[] wordSort_hash_1(char[] alphabet, String[] words) {
        if(null == alphabet || null == words){
            return words;
        }

        Map<Character, Character> new2Old = new HashMap<>(26); //You can assume that the given new alphabet is a 26-character string. Only lowercase letters.
        for(int i = alphabet.length - 1; i >= 0; i--){
            new2Old.put(alphabet[i], (char)(i + 'a'));
        }

        int n = words.length;
        String[] hashed = new String[n];
        Map<String, String> map = new HashMap<>();
        char[] sb;
        for(int i = 0; i < n; i++ ){
            sb = new char[words[i].length()];
            for(int j = 0, m = words[i].length(); j < m; j++ ){
                sb[j] = new2Old.get(words[i].charAt(j));
            }
            hashed[i] = String.valueOf(sb);
            map.put(hashed[i], words[i]);
        }

        Arrays.sort(hashed);

        String[] result = new String[words.length];
        for(int i = 0; i < n; i++ ){
            result[i] = map.get(hashed[i]);
        }

        return result;
    }
    
    /** fastest */
    public String[] wordSort_hash(char[] alphabet, String[] words) {
        if(null == alphabet || null == words){
            return words;
        }

        char[] new2Old = new char[26]; //You can assume that the given new alphabet is a 26-character string. Only lowercase letters.
        for(int i = alphabet.length - 1; i >= 0; i--){
            new2Old[alphabet[i] - 'a'] = (char)(i + 'a');
        }

        int n = words.length;
        String[] hashed = new String[n];
        char[] tmp = new char[100]; //The length of word does not exceed 100.
        int m;
        Map<String, String> map = new HashMap<>();
        for(int i = 0; i < n; i++ ){
            m = words[i].length();
            for( int j = 0; j < m; j++ ){
                tmp[j] = new2Old[words[i].charAt(j) - 'a'];
            }
            hashed[i] = String.valueOf(tmp, 0, m);
            map.put(hashed[i], words[i]);
        }

        Arrays.sort(hashed);

        String[] result = new String[words.length];
        for(int i = 0; i < n; i++ ){
            result[i] = map.get(hashed[i]);
        }

        return result;
    }
}
