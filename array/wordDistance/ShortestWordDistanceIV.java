package fgafa.array.wordDistance;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

/**
 * 
 * Given a list of words and three words (word1 and word2 and word3), 
 * return the shortest distance between these three words in the list.

    word1 and word2 and word3 are not the same and they represent individual words in the list.
    
    For example,
    
    Assume that words = ["practice", "makes", "perfect", "coding", "makes"].
    
    Given word1 = "makes", word2 = "coding", word3 = "make", return 3.
    Given word1 = "practice", word2 = "makes", word3 = "coding", return 3.
    Note: if word1 or word2 or word3 is not in the list, return 0.
 *
 */

public class ShortestWordDistanceIV {

    // two points
    public int getShortestDistances(String textSearch,
                                    String s0,
                                    String s1,
                                    String s2) {
        if (null == textSearch || null == s0 || null == s1 || null == s2) {
            throw new IllegalArgumentException(" ");
        }

        //
        List<Integer> list0 = new ArrayList<>();

        StringTokenizer st = new StringTokenizer(textSearch);
        int p0;
        int p1 = -1;
        String token;

        int min = Integer.MAX_VALUE;
        for (int i = 0; st.hasMoreElements(); i++) {
            token = st.nextToken();

            if (token.equals(s0)) {
                list0.add(i);
            } else if (token.equals(s1)) {
                if (!list0.isEmpty()) {
                    p1 = i;
                }
            } else if (token.equals(s2)) {
                if (p1 != -1) {
                    for (int j = list0.size() - 1; j >= 0; j--) {
                        p0 = list0.get(j);

                        if (p0 < p1) {
                            min = Math.min(min, i - p0);

                            p0 = list0.get(list0.size() - 1);
                            list0.clear();
                            p1 = -1;
                            
                            break;
                        }
                    }
                }
            }

        }

        return min == Integer.MAX_VALUE ? 0 : min;
    }
    
    // two points
    public int getShortestDistances_n(String textSearch,
                                    String s0,
                                    String s1,
                                    String s2) {
        // check input
        if (null == textSearch || null == s0 || null == s1 || null == s2) {
            throw new IllegalArgumentException(" ");
        }

        //
        StringTokenizer st = new StringTokenizer(textSearch);
        int p00 = -1, p01 = -1;
        int p1 = -1;
        String token;

        int min = Integer.MAX_VALUE;
        for (int i = 0; st.hasMoreElements(); i++) {
            token = st.nextToken();

            if (token.equals(s0)) {
                p01 = i;
            } else if (token.equals(s1)) {
                if (p01 != -1) {
                    p1 = i;
                    p00 = p01;
                }
            } else if (token.equals(s2)) {
                if (p1 != -1) {
                    min = Math.min(min, i - p00);

                    if (p01 < p1) {
                        p01 = -1;
                    }
                    
                    p1 = -1;
                }
            }

        }

        return min == Integer.MAX_VALUE ? 0 : min;
    }
    
    //dp
    public int getShortestDistances(String textSearch, String[] words){
        //check input
        if(null == textSearch || null == words || words.length < 2){
            throw new IllegalArgumentException(" ");
        }
        
        //scan textSearch, get all positions of the words
        Map<String, List<Integer>> positions = new HashMap<>();
        for(String word : words){
            positions.put(word, new ArrayList<>());
        }
        
        String[] tokens = textSearch.split(" ");
        
        List<Integer> tmp;
        for(int i = 0; i < tokens.length; i++){
            tmp = positions.get(tokens[i]);
            
            if(null != tmp){
                tmp.add(i);
            }
        }
        
        //get the max length
        int maxLen = 0;
        for(List<Integer> list : positions.values()){
            if(0 == list.size() ){
                return 0;
            }
            
            maxLen = Math.max(maxLen, list.size());
        }
        
        //dp
        int[][] distances = new int[2][maxLen];
        List<Integer> up;
        List<Integer> down;
        for(int i = 1; i < words.length; i++){
            up = positions.get(words[i - 1]);
            down = positions.get(words[i]);

            int k = 0;
            for(int j = 0; j < up.size() && k < down.size();){
                if(down.get(k) <= up.get(j)){
                    k++;
                }else{
                    if(j + 1 < up.size() && up.get(j + 1) < down.get(k)){
                        j++;
                    }else{
                        distances[i & 1][k] = distances[(i - 1) & 1][j] + down.get(k) - up.get(j);
                        k++; 
                    }
                }
            }
            
            while(k < maxLen){
                distances[i & 1][k++] = 0;
            }
        }
        
        int min = Integer.MAX_VALUE;
        for(int distance : distances[(words.length - 1) & 1]){
            if( 0 == distance){
                break;
            }
            
            min = Math.min(min, distance);
        }
        
        //return
        return min == Integer.MAX_VALUE ? 0 : min;
    }
    
    public static void main(String[] args) {
        String[] textSearchs = {
                    "a a b a b b a c a b b c a b c", 
                    "a a b a b b a c a b b c a b "
                    };
        
        String[] words = {
                    "a",
                    "b",
                    "c"
        };

        ShortestWordDistanceIV sv = new ShortestWordDistanceIV();
        
        for(String textSearch : textSearchs){
            System.out.println();
            
            System.out.println(sv.getShortestDistances(textSearch, words));
            System.out.println(sv.getShortestDistances(textSearch, words[0], words[1], words[2]));
            System.out.println(sv.getShortestDistances_n(textSearch, words[0], words[1], words[2]));
        }
    }

}
