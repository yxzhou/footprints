package fgafa.greedy;

import org.junit.Assert;
import org.junit.Test;

import java.util.*;

/**
 * Leetcode #854
 *
 * Strings A and B are K-similar (for some non-negative integer K) if we can swap the positions of two letters in A exactly K times so that the resulting string equals B.
 *
 * Given two anagrams A and B, return the smallest K for which A and B are K-similar.
 *
 * Example 1:
 * Input: A = "ab", B = "ba"
 * Output: 1
 *
 * Example 2:
 * Input: A = "abc", B = "bca"
 * Output: 2
 *
 * Example 3:
 * Input: A = "abac", B = "baca"
 * Output: 2
 *
 * Example 4:
 * Input: A = "aabc", B = "abca"
 * Output: 2
 *
 * Note:
 * 1 <= A.length == B.length <= 20
 * A and B contain only lowercase letters from the set {'a', 'b', 'c', 'd', 'e', 'f'}
 *
 */

public class KSimilarString {

    @Test public void test(){

        Assert.assertEquals(1, kSimilarity("ab", "ba"));
        Assert.assertEquals(2, kSimilarity("abc", "bca"));
        Assert.assertEquals(2, kSimilarity("abac", "baca"));
        Assert.assertEquals(2, kSimilarity("aabc", "abca"));

        Assert.assertEquals(3, kSimilarity(  "baccadee",   "cebacdae"));
        Assert.assertEquals(4, kSimilarity( "abaccddee",  "dcebacdae"));

        Assert.assertEquals(5, kSimilarity("aabbccddee", "bdcebacdae"));

        Assert.assertEquals(6, kSimilarity("aabbccddee", "cbeddebaac"));

    }

    int kSimilarity(String A, String B){
        //return kSimilarity_greedy(A, B);
        return kSimilarity_dfs(A, B);
    }

    // not work !!
    public int kSimilarity_greedy(String A, String B) {
        int n = A.length();

        Map<Character, Set<Integer>> c2p = new HashMap<>();
        for(int i = 0; i < n; i++){
            c2p.computeIfAbsent(A.charAt(i), x -> new HashSet<>()).add(i);
        }

        int count = 0;

        char[] aa = A.toCharArray();

        char ca;
        char cb;
        Iterator<Integer> it;
        int p;
        int candidate = 0;
        for(int i = 0; i < n; i++){
            ca = aa[i];
            cb = B.charAt(i);

            if(ca == cb){
                continue;
            }

            count++;

            it = c2p.get(cb).iterator();
            while(it.hasNext()){
                p = it.next();
                cb = B.charAt(p);

                if(p < i){
                    it.remove();
                }else if( cb != aa[p] ){
                    candidate = p;

                    if( cb == ca){
                        break;
                    }
                }
            }

            aa[candidate] = aa[i];
            c2p.get(cb).remove(candidate);
            c2p.get(ca).add(candidate);
        }

        return count;
    }


    public int kSimilarity_dfs(String A, String B) {
        Map<Character, Set<Integer>> c2p = new HashMap<>();
        for(int i = 0; i < A.length(); i++){
            c2p.computeIfAbsent(A.charAt(i), x -> new HashSet<>()).add(i);
        }

        return dfs(A.toCharArray(), B, 0, c2p);
    }

    private int dfs(char[] aa, String B, int k, Map<Character, Set<Integer>> c2p){
        int min = Integer.MAX_VALUE;

        char ca;
        char cb;
        char cp;
        Iterator<Integer> it;
        int p;
        List<Integer> candidate = new LinkedList<>();
        for( ; k < aa.length; k++ ){
            ca = aa[k];
            cb = B.charAt(k);

            if(ca == cb){
                continue;
            }


            it = c2p.get(cb).iterator();
            while(it.hasNext()){
                p = it.next();
                cp = B.charAt(p);

                if(p < k){
                    //it.remove();
                    continue;
                }else if( cp != aa[p] ){
                    candidate.add(p);

                    if( cp == ca){
                        candidate.clear();
                        candidate.add(p);

                        break;
                    }
                }
            }

            for(int cand : candidate){
                aa[cand] = ca;
                c2p.get(cb).remove(cand);
                c2p.get(ca).add(cand);

                min = Math.min(min, dfs(aa, B, k + 1, c2p) + 1);

                aa[cand] = cb;
                c2p.get(cb).add(cand);
                c2p.get(ca).remove(cand);
            }

            if(!candidate.isEmpty()){
                break;
            }
        }

        return min == Integer.MAX_VALUE ? 0 : min;
    }

}
