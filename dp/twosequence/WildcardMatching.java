package dp.twosequence;

import java.util.Hashtable;
import java.util.TreeSet;

import util.Misc;

/**
 * <pre>
 *
 * This class provides a method for the programmar to test whether a file matches a wildcard.
 *
 * A "?" can match any single char.
 * A "*" can match any length of string including an empty string,
 *
 * e.g.
 * "abc"    &   "a*"        matches
 * "abc"    &   "*"         matches
 * "abc"    &   "b*"        doesn't match
 * "abcd"   &   "a*d"       matches
 * "abcd"   &   "a??d"      matches
 *
 * Description:
 * @version 1.0
 */

public class WildcardMatching
{

  /**
   * @param args
   */
  public static void main(String[] args) {
    /* fundmental testing */
    System.out.println("ab".substring(2).equals(""));
    //System.out.println("a".substring(2));
    
    //test
    WildcardMatching sv = new WildcardMatching();
    
    String[] t = {null, null, "",  "abc", "abc", "ho", "abc", "abc", "abc", "abc", "abcd", "abcd", "abbbc", "ac",  "abbc",  "abcbcd", "aaabbaabbaab", "screeeewywxd"
        , "bbaaaaaabbbbbabbabbaabbbbababaaabaabbababbbabbababbaba"
        , "abbabaaabbabbaababbabbbbbabbbabbbabaaaaababababbbabababaabbababaabbbbbbaaaabababbbaabbbbaabbbbababababbaabbaababaabbbababababbbbaaabbbbbabaaaabbababbbbaababaabbababbbbbababbbabaaaaaaaabbbbbaabaaababaaaabb"
        , "ho", "ho"
        };
    String[] p = {null, "",   "*", "a",   "a*",  "ho**", "*",   "*?",  "?*",  "b*",  "a*d",  "a??d", "ab*c",  "ab*c","ab*bbc","a?*c?*d","*aabbaa*a*",   "scr*w?d"
        , "b*b*ba**a*aaa*a*b**bbaa"
        , "**aa*****ba*a*bb**aa*ab****a*aaaaaa***a*aaaa**bbabb*b*b**aaaaaaaaa*a********ba*bbb***a*ba*bb*bb**a*b*bb"
        , "ho**", "**ho"
        };
    
    boolean[] ans = {false, false, true, false, true, true, true, true, true, false, true, true, true, false, false, true, true, true, false, false, true, true};
    boolean result;
    
    System.out.println("---shrink---"+ sv.shrinkAsterisk(p[18]));
    
    long start = System.nanoTime();
    for(int i=0; i< t.length; i++){
      result = sv.isMatch(t[i], p[i]); 
      System.out.println(i+"--The Text is: " + t[i] + " \t\t the pattern is: " + p[i] + " \t\t are they matched:" + result + "  " + ((ans[i] == result)? " ": "??"));  
    }
    System.out.println("====="+ (System.nanoTime() - start));
    start = System.nanoTime();
    for(int i=0; i< t.length; i++){
      result = sv.isMatch_n(t[i], p[i]); 
      System.out.println(i+"--The Text is: " + t[i] + " \t\t the pattern is: " + p[i] + " \t\t are they matched:" + result + "  " + ((ans[i] == result)? " ": "??"));  
    }
    System.out.println("====="+ (System.nanoTime() - start));
    start = System.nanoTime();
    for(int i=0; i< t.length; i++){
      result = sv.isMatch_greedy(t[i], p[i]); 
      System.out.println(i+"--The Text is: " + t[i] + " \t\t the pattern is: " + p[i] + " \t\t are they matched:" + result + "  " + ((ans[i] == result)? " ": "??"));  
    }
    System.out.println("====="+ (System.nanoTime() - start));
    start = System.nanoTime();
    for(int i=4; i< t.length; i++){
      result = sv.isMatch_greedy_x(t[i], p[i]); 
      System.out.println(i+"--The Text is: " + t[i] + " \t\t the pattern is: " + p[i] + " \t\t are they matched:" + result + "  " + ((ans[i] == result)? " ": "??"));  
    }
    System.out.println("====="+ (System.nanoTime() - start));
    
  }

  public boolean isMatch_recursive(String txt, int i, String pattern, int j) {
    
    int m = txt.length(); // i
    int n = pattern.length(); // j

    if ((i >= m && j >= n) || (j == n - 1 && pattern.charAt(j) == '*')){
      return true;
    }
    if ((i >= m && j < n) || (i < m && j >= n)){
      return false;
    }

    // main
    char pj = pattern.charAt(j);
    if (pj != '*' ) {
      return (pj == txt.charAt(i) || pj == '?')
          && isMatch_recursive(txt, i + 1, pattern, j + 1);
    }

    int k = i;
    while (k < m) {
      if (isMatch_recursive(txt, k, pattern, j + 1)){
        return true;
      }

      k++;
    }

    return false;
  }
  
/*
 *   
 */
  public boolean isMatch(String txt, String pattern) {
    //check
    if(txt == null || pattern == null)
      return false;
    
    pattern = shrinkAsterisk(pattern);
    
    Hashtable<String, Integer> cache = new Hashtable<String, Integer>(); 
    
    return isMatch_recursive_cache(txt, 0, pattern, 0, cache);
    //return isMatch_recursive(txt, 0, pattern, 0);
  }
  
  private boolean isMatch_recursive_cache(String txt, int i, String pattern, int j,
      Hashtable<String, Integer> cache) {

    int tLen = txt.length(); // i
    int pLen = pattern.length(); // j

    if ((i >= tLen && j >= pLen) || (j == pLen - 1 && pattern.charAt(j) == '*'))
      return true;
    if ((i >= tLen && j < pLen) || (i < tLen && j >= pLen))
      return false;

    // main
    boolean result = false;
    String key = i + "&" + j;
    if (cache.containsKey(key))
      return cache.get(key) == 1;

    char pj = pattern.charAt(j);
    if (pj != '*') {
      result = (pj == txt.charAt(i) || pj == '?')
          && isMatch_recursive_cache(txt, i + 1, pattern, j + 1, cache);

      cache.put(key, result ? 1 : 0);

      return result;
    }

    int k = i;
    char p1 = pattern.charAt(j + 1);
    while (k < tLen) {
      if (p1 == txt.charAt(k) || p1 == '?')
        result = result || isMatch_recursive_cache(txt, k + 1, pattern, j + 2, cache);

      cache.put(key, result ? 1 : 0);

      if (result)
        return true;

      k++;
    }

    return false;
  }
  
  /* "a**b" ==> "a*b",  "a**?b" ==> "a*b" */
  private String shrinkAsterisk(String pattern){
    StringBuffer sb = new StringBuffer();
    
    if(pattern.length() < 2)
      return pattern;
    
    char pre = pattern.charAt(0), curr;
    sb.append(pre);
      
    for(int i=1; i< pattern.length(); i++){
      curr =  pattern.charAt(i);
      
      if(pre == '*' && curr == '*')  // "**" => "*"
      //if((pre == '*' && (curr == '*' || curr == '?')) || (pre == '?' && curr == '*' )) // "?*", "**", "*?" => "*" 
        continue;
      else{
        sb.append(curr);
        pre = curr;

      }
    }
   
    return sb.toString();
  }
  
  /* best one till to now !!
   * 
   * Idea: The pattern string can be something+'*'+sth+'*'+sth+`*'+...+'*'+sth+'*'+sth. 
   * We first deal with the matching of the head and tail (before the first `*` and after the last `*'). 
   * Then the pattern string left is '*'+sth+'*'+sth+`*'+...+'*'+sth+'*', 
   * for which, we use greedy algorithm to find the first appearance of each something. 
   * So the total time required is O(m*n).
   */
  public boolean isMatch_greedy(String s, String p) {

    // check
    if (s == null || p == null)
      return false;

    int sLen = s.length(); // i
    int pLen = p.length(); // j
    if (pLen == 0)
      return sLen == 0;

    // deal with head
    int i = 0;
    while (i < pLen && i < sLen && p.charAt(i) != '*') {
      if (p.charAt(i) != s.charAt(i) && p.charAt(i) != '?')
        return false;
      i++;
    }
    if (i == sLen) {
      while (i < pLen)
        if (p.charAt(i++) != '*')
          return false;
      return true;
    }
    else if (i == pLen)
      return false;
    else {
      s = s.substring(i);
      p = p.substring(i);
    }

    // deal with tail
    i = p.length() - 1;
    int j = s.length() - 1;
    while (i >= 0 && j >= 0 && p.charAt(i) != '*') {
      if (p.charAt(i) != s.charAt(j) && p.charAt(i) != '?')
        return false;
      i--;
      j--;
    }
    if (j < 0) {
      while (i >= 0)
        if (p.charAt(i--) != '*')
          return false;
      return true;
    }else if (i < 0)
      return false;
    else {
      s = s.substring(0, j + 1);
      p = p.substring(0, i + 1);
    }

    //greedy
    String[] pattern = p.split("[*]");
    for (String temp : pattern) {
      if (temp.length() > 0) {
        int index = getFirstIndex(s, temp);
        if (index < 0)
          return false;
        else
          s = s.substring(index + temp.length());
      }
    }
    return true;
  }

  /* s.length()  * p.length) */
  private int getFirstIndex(String s, String p) {
    int sLen = s.length(); // i
    int pLen = p.length(); // j

    if (sLen < pLen)
      return -1;
    int i = 0;
    while (i <= sLen - pLen) {
      while (i < sLen && p.charAt(0) != '?' && p.charAt(0) != s.charAt(i))
        i++;
      if (sLen - i < pLen)
        return -1;

      int j = i;
      while (j - i < pLen
          && (p.charAt(j - i) == '?' || p.charAt(j - i) == s.charAt(j)))
        j++;
      if (j - i == pLen)
        return i;
      i++;
    }
    return -1;
  }
  
  public static void KMPMatcher(char[] T, char[] P) {

    int n = T.length;
    int m = P.length;

    //fail 
    int[] next = new int[m]; // the default value all are 0
    //System.out.println(" The prefix function initial value is: " + misc.array2String(fail));

    //preprocessing
    getNexts(P, next);

    System.out.println("   The prefix function is: " + Misc.array2String(next));

    //if (true) return;
    System.out.println("The text is:" + Misc.array2String(T));

    int q = 0;
    for (int i = 1; i <= n; i++) {
      while (q > 0 && P[q] != T[i - 1]) {
        q = next[q - 1];
      }

      if (P[q] == T[i - 1] || P[q] == '?') {
        q = q + 1;
      }

      if (q == m) {
        q = next[q - 1];
      }
    }
  }

  private static void getNexts(char[] P, int[] next) {
    int m = P.length;
    
    int k = 0;
    for (int i = 2; i <= m; i++) {
        while (k > 0 && P[k] != P[i - 1]) 
            k = next[k - 1];
        
        if (P[k] == P[i - 1] || P[k] == '?') 
            k ++;
        
        next[i - 1] = k;
    }
   }

  public boolean isMatch_greedy_x(String s, String p) {
    // check
    if (s == null || p == null)
      return false;
    if (p.length() == 0)
      return s.length() == 0;

    //deal with the header
    int head = p.indexOf('*');
    if(head == 0)
      return true;
    if(head == -1)
      return s.equals(p);
    if(!isMatch(s, 0, head, p, 0, head))
      return false;
    
    //deal with the tail
    int tail = p.lastIndexOf('*');
    int tailS = s.length() - p.length() + tail + 1;
    if(!isMatch(s, tailS, s.length(), p, tail + 1, p.length()))
      return false;
      
    //trim * from 2 head and tail     
    p = trimAsterisk(p, head, tail);  
    if(p == null)
      return true;
    if(head>tailS)
      return false;
    s = s.substring(head, tailS+1);
    if(s==null)
      return false;
    
    // greedy
    String[] pattern = p.split("[*]");
    for (String temp : pattern) {
      if (temp.length() > 0) {
        int index = getFirstIndex(s, temp);
        if (index < 0)
          return false;
        else
          s = s.substring(index + temp.length());
      }
    }
    return true;
        
  }
  /* [start, end)  */
  private boolean isMatch(String s, int sStart, int sEnd, String p, int pStart, int pEnd){
    assert(sEnd > sStart);assert(pEnd > pStart);assert(sEnd - sStart == pStart - pEnd);
    
    for (;sStart < sEnd && pStart < pEnd; sStart ++, pStart ++ ) {
      if (p.charAt(pStart) != s.charAt(sStart) && p.charAt(pStart) != '?')
        return false;      
    }
    
    return true;
  }

  private String trimAsterisk(String p, int head, int tail){
    assert(head <= tail);
    while(head <= tail){
      if(p.charAt(head) == '*')
        head ++;
    }
    while(head <= tail){
      if(p.charAt(tail) == '*')
        tail --;
    }
    return (head <= tail)? p.substring(head, tail+1) : null;
  }
  
  /*
   * // Time: O(|s||p|*log|s|), Space: O(|s|) 
   * // Time can also optimize to O(|s||p|)
   */
  public boolean isMatch_dp(String s, String p) {
    // check
    if (s == null || p == null)
      return false;
    if (p.length() == 0)
      return s.length() == 0;
    
    int slen = s.length();
    int plen = p.length();
    boolean dp[][] = new boolean[slen + 1][plen+1];
    dp[0][0] = true;
    
    for(int i=0; i<=slen; i++)
      for(int j=0; j<=plen; j++){
        if(i==0 && j==0) 
          continue;
        if(j>1 && dp[i][j-2] && p.charAt(j-1) == '*')
          dp[i][j] = true;
        if(i>0 && j>0 && dp[i-1][j-1] && isMatch(s.charAt(i-1), p.charAt(j-1)))
          dp[i][j] = true;
        if(i>0 && j>1 && dp[i-1][j] && p.charAt(j-1) == '*' && isMatch(s.charAt(i-1), p.charAt(j-2)))
          dp[i][j] = true;
      }
    
    return dp[slen][plen];
  }
  
  
  /**
   * @param s: A string 
   * @param p: A string includes "?" and "*"
   * @return: A boolean
   */
  public boolean isMatch_n(String s, String p) {
      //check
      if(null == s || null == p){
          return false;
      }
      if (p.length() == 0){
          return s.length() == 0;
      }
      
      boolean[][] isMatch = new boolean[s.length() + 1][p.length() + 1]; //default all are false
      isMatch[0][0] = true;
      
      for(int i = 0; i<= s.length(); i++){
          for(int j = 0; j <= p.length(); j++){
              if(isMatch[i][j]){
                  continue;
              }
              
              //It's equal or ?
              if(i > 0 && j > 0 && isMatch[i - 1][j - 1] && ( s.charAt(i - 1) == p.charAt(j - 1) || '?' == p.charAt(j - 1) || '*' == p.charAt(j - 1)) ){
                  isMatch[i][j] = true;
                  continue;
              }
              
              //It's *, and * match the empty 
              if(j > 0 && isMatch[i][j - 1] && '*' == p.charAt(j - 1)){
                  isMatch[i][j] = true;
                  continue;
              }
              
              //It's *, and * match any sequence of characters
              if(i > 0 && j > 0 && isMatch[i - 1][j] && '*' == p.charAt(j - 1)){
                  isMatch[i][j] = true;
                  continue;
              }

          }
      }
      
      return isMatch[s.length()][p.length()];
   }
   
   private boolean isMatch(char s, char p){
       return '?' == p || s == p || '*' == p;
   }
   
    public boolean isMatch_x(String s, String p) {

        int m = s.length();
        int n = p.length();
        boolean[][] dp = new boolean[m + 1][n + 1];
        dp[0][0] = true;

        for (int j = 1; j <= n; j++) {
            if (p.charAt(j - 1) == '*') {
                dp[0][j] = true;
            } else {
                break;
            }
        }

        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                if (p.charAt(j - 1) != '*') {
                    dp[i][j] = dp[i - 1][j - 1] && (s.charAt(i - 1) == p.charAt(j - 1) || p.charAt(j - 1) == '?');
                } else {
                    dp[i][j] = dp[i - 1][j] || dp[i][j - 1];
                }
            }
        }

        return dp[m][n];
    }

   
  public boolean isMatch_dp_x(String s, String p) {
    // without this optimization, it will fail for large data set
    int plenNoStar = 0;
    for (char c : p.toCharArray())
      if (c != '*')
        plenNoStar++;
    if (plenNoStar > s.length())
      return false;

    s = " " + s;
    p = " " + p;
    int slen = s.length();
    int plen = p.length();

    boolean[] dp = new boolean[slen];
    TreeSet<Integer> firstTrueSet = new TreeSet<Integer>();
    firstTrueSet.add(0);
    dp[0] = true;

    boolean allStar = true;
    for (int pi = 1; pi < plen; pi++) {
      if (p.charAt(pi) != '*')
        allStar = false;
      for (int si = slen - 1; si >= 0; si--) {
        if (si == 0) {
          dp[si] = allStar ? true : false;
        }
        else if (p.charAt(pi) != '*') {
          if (s.charAt(si) == p.charAt(pi) || p.charAt(pi) == '?')
            dp[si] = dp[si - 1];
          else
            dp[si] = false;
        }
        else {
          int firstTruePos = firstTrueSet.isEmpty()
              ? Integer.MAX_VALUE
              : firstTrueSet.first();
          if (si >= firstTruePos)
            dp[si] = true;
          else
            dp[si] = false;
        }
        if (dp[si])
          firstTrueSet.add(si);
        else
          firstTrueSet.remove(si);
      }
    }
    return dp[slen - 1];
  }

}
