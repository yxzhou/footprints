package fgafa.array;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import fgafa.util.Misc;


/*
 * 
 * Given two strings s and t, write a function to determine if t is an anagram of s.

    For example,
    s = "anagram", t = "nagaram", return true.
    s = "rat", t = "car", return false.
    
    Note:
    You may assume the string contains only lowercase alphabets.
    
    Follow up:
    What if the inputs contain unicode characters? How would you adapt your solution to such case?
    
    
    Solutions:
        #1 string to char[] and sort and compare
        #2 count and compare, int[256] if it's ASCII
           Hashtable<Character, Integer> if it's unicode character.
 */

public class Anagram
{

  /*
   * 
   * Given an array of strings, return all groups of strings that are anagrams.
   *
   * Note: All inputs will be in lower-case.
   * 
   */
  public List<String> fetchAllAnagrams(String[] strs){
    ArrayList<String> retList = new ArrayList<String>();
    
    //check
    if(strs == null || strs.length < 2)
      return retList;
    
    //init

    //travel the string list,  get the new "sorted String" , example {"fagd","acb", "cba"} ==> {"adfg","abc","abc"}
    //add the <new String, ArrayList<old String>> into a Hashtable.
    HashMap<String, ArrayList<String>> hm = new HashMap<>();
    String newStr;
    ArrayList<String> arrList;
    for(int i=0; i<strs.length; i++){
      if(strs[i] == null)
        continue;
      
      newStr = sort(strs[i]);
      if(hm.containsKey(newStr)){
       ((ArrayList<String>) hm.get(newStr)).add(strs[i]);
      }else {
        arrList = new ArrayList<String>();
        arrList.add(strs[i]);
        
        hm.put(newStr, arrList);
      }
    }
    
    //travel the Hashtable, if the ArrayList<old String> size is not one, add it in Arraylist<String> from return 
    for (ArrayList<String> subList : hm.values() ) {
  
       if(subList.size() > 1){
         retList.addAll(subList);
       }
    }
    
    return retList;
  }
  
  public ArrayList<ArrayList<String>> fetchAllAnagrams2(String[] strs){
    //check
    if(strs == null )
      return new ArrayList();
    
    //init

    //travel the string list,  get the new "String" , example {"fagd","acb", "cba"} ==> {"adfg","abc","abc"}
    //add the <new String, ArrayList<old String>> into a Hashtable.
    Hashtable<String, HashSet<String>> ht = new Hashtable<String, HashSet<String>>();
    String newStr;
    HashSet<String> hs;
    for(int i=0; i<strs.length; i++){
      if(strs[i] == null)
        continue;
      
      newStr = sort(strs[i]);
      if(ht.containsKey(newStr)){
       ((HashSet<String>) ht.get(newStr)).add(strs[i]);
      }else {
        hs = new HashSet<String>();
        hs.add(strs[i]);
        
        ht.put(newStr, hs);
      }
    }
    
    ArrayList<ArrayList<String>> retList = new ArrayList<ArrayList<String>>();
    
    //travel the Hashtable, if the ArrayList<old String> size is not one, add it in Arraylist<String> from return 
    for (HashSet<String> subList : ht.values() ) {
       retList.add(new ArrayList<String>(subList));
    }
    
    return retList;
  }
  /*
   * reorder the input string, 
   * e.g.
   *   input  "acb"
   *  output  "abc"
   */
  private String sort(String str){
//    ArrayList<String> list = new ArrayList<String>();
//    
//    for(char c : str.toCharArray()){
//      list.add(String.valueOf(c));
//    }
//
//    Collections.sort(list);
//    
//    StringBuffer sb = new StringBuffer();
//    for(String s : list){
//      sb.append(s);
//    }
//    
//    return sb.toString();
    
    char[] newArray = str.toCharArray();
    Arrays.sort(newArray);
    return new String(newArray);
    
  }
  
  
  /**
   * @param strs: A list of strings
   * @return: A list of strings
   */
  public List<String> fetchAllAnagrams_n(String[] strs) {
      List<String> result = new ArrayList<>();
      
      //check
      if(null == strs){
          return result;
      }
      
      Map<String, List<String>> map = new HashMap<>();
      String tmp;
      List<String> list;
      for(String str : strs){
    	  if(null == str){
    		  continue;
    	  }
    	  
    	  char[] chars = str.toCharArray();
    	  Arrays.sort(chars);
    	  tmp = new String(chars);
    	  
    	  if(map.containsKey(tmp)){
    		  list = map.get(tmp);
    	  }else{
    		  list = new ArrayList<>();
    		  map.put(tmp, list);
    	  }
    	  
    	  list.add(str);
      }
      
      for(List<String> anagrams : map.values()){
    	  if(anagrams.size() > 1){
    		  result.addAll(anagrams);
    	  }
      }
      
      return result;
  }
  
  
  /*
   * check if the two string have identical counts for each unique char with Hashtable / Array
   * 
   * @ return boolean,  true means they are anagrams each other.
   */
  public static boolean isAnagrams(String s1, String s2){
    //check input. They are not anagrams if they are null, or size is not same.
    if(s1 == null || s2 == null || s1.length() != s2.length())
      return false;
    
    //init
    int[] letters = new int[256];  // ?? 
    for(int i : letters ){
      letters[i] = 0;
    }
    
    int uniqueCharsNum = 0;
        
    //check if the two string have identical counts for each unique char with Hashtable, (or Array)
    //check s1
    for(char c : s1.toCharArray()){
      if(letters[(int)c] == 0 )
        uniqueCharsNum ++;
      
      letters[(int)c] ++;
    }
    
    //check s2  
    for(char c : s2.toCharArray()){
      //if not found, return false
      if(letters[(int)c] == 0 )
        return false;
      
      //if found, --
      letters[(int)c] -- ;
      
      //if it's 0 after --
      if(letters[(int)c] == 0)
    	  uniqueCharsNum --;
    }
    
    //return
    return 0 == uniqueCharsNum;
  }
  
  /**
   * @param s: The first string
   * @param b: The second string
   * @return true or false
   */
  public static boolean isAnagram_n(String s, String t) {
      //check input. They are not anagrams if they are null, or size is not same.
	  if(null == s || null == t || s.length() != t.length()){
		  return false;
	  }
	  
	  int[] counts = new int[256]; //default all are 0
	  for(int c : s.toCharArray()){
		  counts[c]++;
	  }
	  
	  for(int c : t.toCharArray()){
		  counts[c]--;
	  }
	  
	  for(int c : counts){
		  if( 0 != c){
			  return false;
		  }
	  }
	  
	  return true;
  }
  
  public static boolean isAnagram_n2(String s, String t) {
      //check input. They are not anagrams if they are null, or size is not same.
      if(null == s || null == t || s.length() != t.length()){
          return false;
      }
      
      Map<Character, Integer> counts = new HashMap<>(s.length());
      for(char c : s.toCharArray()){
          if(counts.containsKey(c)){
              counts.put(c, counts.get(c) + 1);
          }else{
              counts.put(c, 1);
          }
      }
      
      for(char c : t.toCharArray()){
          if(counts.containsKey(c)){
              if( 0 == counts.get(c)){
                  return false;
              }else{
                  counts.put(c, counts.get(c) - 1);
              }
          }else{
              return false;
          }
      }
      
      return true;
  }
  
  /*
   * Arrays.sort and String.equals
   * @ return boolean,  true means they are anagrams each other.
   */
  public static boolean isAnagrams3(String s1, String s2){
    //check input. They are not anagrams if they are null, or size is not same.
    if(s1 == null || s2 == null || s1.length() != s2.length())
      return false;
    
    char[] newStr = s1.toCharArray();
    Arrays.sort(newStr);
    s1 = new String(newStr);
    
    newStr = s2.toCharArray();
    Arrays.sort(newStr);
    s2 = new String(newStr);
    
    return s1.equals(s2);
  }
  
  /*
   * WRONG !!!
   * WRONG !!!
   * 
   * XOR does not work,  eg. isAnagrams2("abab", "bcbc") true, it's wrong 
   * 
   * check if the two string have identical counts for each unique char without big additional space, such as Hashtable
   * 
   * @ input
   * @ return boolean,  true means they are anagrams each other.
   */
  private static boolean isAnagrams2(String s1, String s2){
    //check input. They are not anagrams if they are null, or size is not same.
    if(s1 == null || s2 == null || s1.length() == 0 || s1.length() != s2.length())
      return false;
    
    //init
    int ret = 0;
    
    
    //check if the two string have identical counts for each unique char with XOR
    for(char c : s1.toCharArray()){
       ret = ret ^ (int) c;  //  x xor 0 = x, x xor x = 0 
    }
    for(char c : s2.toCharArray()){
      ret = ret ^ (int) c;
    }
    
    //return
    return ret == 0;
  }
  
  
  
  public static void main(String[] args) {
    Anagram sv = new Anagram();
    
//    String s = "aa";
//    int ret = 0;
//    for(char c : s.toCharArray()){
//      ret = ret ^ (int) c;  //  x xor 0 = x, x xor x = 0 
//   }
//    System.out.println("test " + isAnagrams2("abab", "bcbc") );
    
    
    System.out.println("------------Start-------------- " );

    //init
    String[] strs1 = {null, "acb", "abab", "abc", "abcde", "abcdef", "abcbcae"}; 
    String[] strs2 = {null, "bac", "bcbc", "abcd", "bdcea", "abcdge", "abaebcb"};    

    for(int i = 0; i<strs1.length; i++){
      System.out.println("check if string are anagrams: s1=" + strs1[i] + ", s2=" + strs2[i]);
      System.out.println("\t Result -isAnagrams:" + isAnagrams(strs1[i], strs2[i]));
      
      System.out.println("\t Result -isAnagram_n:" + isAnagram_n(strs1[i], strs2[i]));
      
      System.out.println("\t Result -isAnagrams2:" + isAnagrams2(strs1[i], strs2[i]));
      
      System.out.println("\t Result -isAnagrams3:" + isAnagrams3(strs1[i], strs2[i]));
    }
    
    System.out.println("------------method 2-------------- " );

    String[] strs = {"tea","and","ate","eat","dan", "eat", "abc"};
    
    Misc.printArrayList((ArrayList<String>)sv.fetchAllAnagrams(strs));
    ArrayList<ArrayList<String>> resultList = sv.fetchAllAnagrams2(strs);
    
    for (int i = 0; i < resultList.size(); i++) {
      ArrayList<String> subList = (ArrayList<String>)resultList.get(i);
      
      if(subList == null || subList.size() == 0){
        System.out.println("Null");
        continue;
      }
      
      for (int j = 0; j < subList.size(); j++) {
        System.out.print("\t-"+i+"-\t\""+subList.get(j)+"\"");
      }
      
      System.out.println();
      
    }
    
    System.out.println("------------End-------------- " );
    
  }

}
