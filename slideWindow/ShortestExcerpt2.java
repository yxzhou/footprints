package slideWindow;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;


/*
 * Note:  better solution, please see MinWindowSubstring
 * 
 * Given a set T of characters and a string S, find the minimum window in S which will contain all the characters in T in complexity O(n).
 *
 * eg,
 * S = “ADOBECODEBANC”
 * T = “ABC”
 *
 * Minimum window is “BANC”.
 * 
 * Further:  
 *  1) the "ABC" sequence is important. how to do
 * 
 */

public class ShortestExcerpt2
{
  /*
   * eg, input
   * S = "ADOBECODEBANC"    n
   * T = "ABC"              m
   *  ( n > m)
   * output, Minimum window is “BANC”.
   * when not found, return null 
   * 
   * Time O( n*m )  ,  Space O(m)
   * 
   */
  public String findMinWindow(String s, String t){
    //check
    if(s == null || t== null || s.length()==0 || s.length() < t.length())
      return null;
    
    //init
    char[] sc = s.toCharArray();
    char[] tc = t.toCharArray();
    Hashtable ht =new Hashtable();  // t.char[i], index
    Hashtable tHT =new Hashtable(); //
    
    for(int i=0; i<tc.length; i++){
      tHT.put(tc[i], 0);
    }
    
    //main
    int i=0;
    while(ht.size() < tc.length){
      if(tHT.containsKey(sc[i])){
        ht.put(sc[i], i);
      }       
      i++;
    }
    
    tHT = null;   // ready for GC
    ArrayList minWindowWith = null;   //( the max - min, the max, the min}
    ArrayList currWindowWith = null;  //( the max - min, the max, the min}
    
    if(ht.size() == tc.length){
      minWindowWith= calWindowWidth(ht);
      currWindowWith = minWindowWith;
    
      for(int j=i; j<sc.length; j++){
        if(ht.containsKey(sc[j])){
          //System.out.println(sc[(Integer)currWindowWith.get(2)]+ "  "+ sc[j]);
          
          if( sc[(Integer)currWindowWith.get(2)] == sc[j] ){
            ht.put(sc[j], j);
                        
            currWindowWith = calWindowWidth(ht);
            if((Integer)minWindowWith.get(0) - (Integer)currWindowWith.get(0) > 0){
              minWindowWith = currWindowWith;
            }
          } else {
            ht.put(sc[j], j);
          }
            
        }
      }
      
      //System.out.println(s.substring((Integer)minWindowWith.get(2), (Integer)minWindowWith.get(1)+1 ));
      return s.substring((Integer)minWindowWith.get(2), (Integer)minWindowWith.get(1)+1 );
    }
    
    //return 
      return null;
  }

  /*
   * return list, 
   *   index = 0, the max - min
   *   index = 1, the max
   *   index = 2, the min
   * 
   * Time O(m)
   */
  
  private ArrayList<Integer> calWindowWidth(Hashtable ht){
    //init
    ArrayList<Integer> list = new ArrayList<Integer>();
    int max = Integer.MIN_VALUE;
    int min = Integer.MAX_VALUE;
    int curr = 0;
    
    //find the max and min 
    Iterator iterator = ht.keySet().iterator();  
    if(iterator.hasNext()){
      max = (Integer)ht.get(iterator.next());
      min = max;
    }
    
    while (iterator.hasNext()) {  
       curr =  (Integer)ht.get(iterator.next());
       
       if(curr > max)
         max = curr;
       else if(curr < min)
         min = curr;
    }
    
    //return the windowwidth max-min
    list.add(max - min);
    list.add(max);
    list.add(min);
    return list;
    
  }
  
  /*
   * Time O(n)  space O(m)
   */
  public String minWindow2(String S, String T) {
    if(S==null || T==null) return "";
    Map<Character,Integer> total = new HashMap<Character,Integer>();
    for(int i=0;i<T.length();i++){
        char c = T.charAt(i);
        if(total.containsKey(c))
            total.put(c,total.get(c)+1);
        else
            total.put(c,1);
    } 
    
    Map<Character,Integer> covered = new HashMap<Character,Integer>();
    int minWinStart=0,
        minWinEnd=-1,
        curWinStart=0,
        curWinEnd=0,
        count=0;

    while(curWinStart<S.length() && curWinEnd<S.length()){
        while(curWinStart<S.length() && !total.containsKey(S.charAt(curWinStart))) 
            curWinStart++;
            
        if(curWinStart<S.length()){
            if(curWinEnd<curWinStart) curWinEnd=curWinStart;
            
            if(count<T.length()){
                while(curWinEnd<S.length() && !total.containsKey(S.charAt(curWinEnd))) 
                    curWinEnd++;
                if(curWinEnd<S.length()){
                    char c = S.charAt(curWinEnd);   
                    if(!covered.containsKey(c) ){
                        covered.put(c,1);
                        count++;
                    }else{                        
                        if(covered.get(c)<total.get(c)) 
                            count++;
                        covered.put(c,covered.get(c)+1);
                    } 
                    if(count<T.length()) curWinEnd++;
                }
            }else{
                if(minWinStart>minWinEnd || minWinEnd-minWinStart>curWinEnd-curWinStart){
                    minWinEnd=curWinEnd;
                    minWinStart=curWinStart;
                }
                char c = S.charAt(curWinStart);
                covered.put(c,covered.get(c)-1);
                curWinStart++;
                if(covered.get(c)<total.get(c)){
                     count--;
                     curWinEnd++;
                }
            }
        }
    }
    return  S.substring(minWinStart,minWinEnd+1); 
  }
  
  /*
   * Time O(n)  space O(m)
   */
  public String minWindow(String S, String T) {
      if (S == null || T == null || S.length() == 0 || S.length() < T.length()) {
          return null;
      }

      int[] need = new int[256];
      for (char c : T.toCharArray()) {
          need[c]++;
      }

      int[] found = new int[256];
      int count = 0;
      int minWinLen = S.length();
      int minWinStart = 0;

      char ch;
      for (int start = 0, end = 0, len = 0; end < S.length(); end++) {
          ch = S.charAt(end);
          if (need[ch] == 0) {
              continue;
          }

          if (++found[ch] <= need[ch]) {
              count++;
          }

          if (count == T.length()) {
              ch = S.charAt(start);
              while (need[ch] == 0 || found[ch] > need[ch]) {
                  if (found[ch] > need[ch])
                      found[ch]--;
                  start++;
                  ch = S.charAt(start);
              }

              len = end - start + 1;
              if (len < minWinLen) {
                  minWinLen = len;
                  minWinStart = start;
              }

          }

      }

      return S.substring(minWinStart, minWinStart + minWinLen);
  }
   
  
  /**
   * @param args
   */
  public static void main(String[] args) {
    ShortestExcerpt2 md = new ShortestExcerpt2();
    
    String[] S = {null,"","a", "ADOBECODEBANC", "acdacbdeaab", "a","a","ab","a", "aa", "bba","bbaa", "bdab"};
    String[] T = {null,"","ab", "ABC", "bcd", "a","b","a","ab", "aa", "ab","aba", "ab"};
    
    for(int i=9; i< S.length; i++){
      System.out.println("\nThe S is:"+ S[i] +", T is:"+ T[i] );
      System.out.print("The minimum window is: ");
      //System.out.println(md.findMinWindow(S[i], T[i]));
      System.out.println(md.minWindow(S[i], T[i]));
      System.out.println(md.minWindow2(S[i], T[i]));
      
    }

  }

}
