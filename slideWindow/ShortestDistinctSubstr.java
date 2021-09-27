package slideWindow;

import java.util.HashMap;

/*
 * 
 * Given a string s, find the shortest substring that occurs exactly once in s.
 * 
 * For example,
 * 
 * s = "aabbabbaab", return either "bab" or "baa"
 * s = "aaaa", return "aaaa"
*/   

public class ShortestDistinctSubstr
{

  /*
   * Solution:
   *  1) There are n substring whose length is 1, count these substring with HashMap<Substr, count>. 
   *  Travel the map, return if it found one substr's count is 1.
   *  2) There are n-1 substring whose length is 2, count these substring with HashMap<Substr, count>. 
   *  Travel the map, return if it found one substr's count is 1.    
   *  ---
   * 
   * Time O(n^2), Space(n?), n is the length of input.
   * 
  */    
  public String shortestDistinctSubStr(String input){
    //check
    if(input == null)
      return input;
    
    String str;
    for(int step = 1; step < input.length(); step ++){
      HashMap<String, Integer> map = new HashMap<String, Integer>();
      
      for(int i=0; i<=input.length()-step; i++){
        str = input.substring(i, i+step);
        
        if(map.containsKey(str))
          map.put(str, map.get(str)+1);
        else
          map.put(str, 1);
          
      }
      
      for(String key : map.keySet()){
        if(map.get(key) == 1)
          return key;
      }
      
    }
    
    return input; 
  }
  
  
  /**
   * @param args
   */
  public static void main(String[] args) {
    String[] input = {"a", "ab", "aa", "aaa", "aabbabbaab"}; 

    ShortestDistinctSubstr sv = new ShortestDistinctSubstr();
    
    for(String str: input){
      System.out.println(sv.shortestDistinctSubStr(str));
    }
    
  }

}
