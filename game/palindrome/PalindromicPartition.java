package fgafa.game.palindrome;

import java.util.ArrayList;
import java.util.List;

public class PalindromicPartition
{

  /**
   * @param args
   */
  public static void main(String[] args) {
    String[] arr = {"aab", "fff", "abc", "abbab"};

    List<List<String>> result;
    PalindromicPartition sv = new PalindromicPartition();
    for(String s : arr){
      System.out.println("\n start to test:" + s);
      
      //test
      System.out.print(" getAllPalindrome: \t" );
      for( String palindrome: sv.getAllPalindrome(s))
        System.out.printf(palindrome +",");
      
      // test partition
      System.out.print("\n partition:" );
      result = sv.partition(s);      
      for(List<String> list : result){
        System.out.printf("\n <");
        for(String word : list)
          System.out.printf(word +",");
        System.out.printf(" > ");  
      }    
          
      //test minCut
      System.out.println("\n minCut: \t"+sv.minCut(s));
      System.out.println("\n minCut: \t"+sv.minCut_n(s));
    }
      
  }
  
  /*
   * Given a string s, return all possible palindrome substring of s.
   * 
   * For example, 
   * given s = "aab", return  ["a","a","b", “aa”]
   * given s = "aba", return  ["a","b","a", “aba”]
   * 
   */  
  public List<String> getAllPalindrome(String s) {
    List<String> result = new ArrayList<>();
    //pre check
    if(s == null || s.length() == 0)
      return result;

    int len = s.length();
    for(int i=0; i<len; i++){
      for(int j=i, k=i; j>=0 && k<len; j--, k++ ){
        if(s.charAt(j) == s.charAt(k))
          result.add(s.substring(j, k+1));
        else
          break;
      }
      
      for(int j=i, k=i+1; j>=0 && k<len; j--, k++ ){
        if(s.charAt(j) == s.charAt(k))
          result.add(s.substring(j, k+1));
        else
          break;
      }
    }
           
    return result;
  }
  
  
  /*
   * Given a string s, partition s such that every substring of the partition is a palindrome.
   * 
   * Return all possible palindrome partitioning of s.
   * 
   * For example, given s = "aab", Return
   * [
   *  ["aa","b"],
   *  ["a","a","b"]
   * ]
   */
  public List<List<String>> partition(String s) {
    List<List<String>> result = new ArrayList<List<String>>();
    
    //pre check
    if(null == s || 0 == s.length()){
      return result;
    }
    
    //init
    boolean[][] isPalindromic = new boolean[s.length()][s.length()]; //default all are false
    
    partition(result, isPalindromic, new ArrayList<String>(), s, 0);
    
    return result;
  }
  
  private void partition(List<List<String>> result, boolean[][] isPalindromic, List<String> prefix, String s, int start){
    if(start == s.length()){
      result.add(new ArrayList<>(prefix)); 
    }
      
    for(int i = start; i < s.length(); i++){
      if(!isPalindromic[start][i]){
        if(start == i || (s.charAt(start) == s.charAt(i) && (start + 1 == i || isPalindromic[start + 1][i -1] ) ) ){
            isPalindromic[start][i] = true;            
        }
      }
      
      if(isPalindromic[start][i]){
        prefix.add(s.substring(start, i+1));
        partition(result, isPalindromic, prefix, s, i+1);
        
        //back-tracking
        prefix.remove(prefix.size() - 1);
      }         
    }      
  }
  
  
  /*
   * Given a string s, partition s such that every substring of the partition is a palindrome.
   * 
   * Return the minimum cuts needed for a palindrome partitioning of s.
   * 
   * For example, given s = "aab",
   * Return 1, since the palindrome partitioning ["aa","b"] could be produced using 1 cut.
   * 
   * Solution
   * DP: 
   *   minCut(s, 0, i) = Math.min( minCut(s, 0, j), when isPalindrom(s, j+1, i) == true ) 
   * 
   * Time O(n^2)  Space (n^2),  n is s.length()
   * 
   */
  public int minCut(String s) {
	if(null == s || 0 == s.length()){
      return 0;
	}
    
    int length = s.length();
    int[] minCut = new int[length+1];// minCut[i] is the minCut in s.subString(0, i)
    minCut[length] = 0;
    for(int i=0; i<length; i++){
      minCut[i] = Integer.MAX_VALUE;
    }
    
    boolean[][] isPalindromic = new boolean[length][length];//default all are false
    
    for(int i = length - 1; i >= 0; i--){
      for(int j=i; j<length; j++){
        if(!isPalindromic[i][j]){
          if(j == i || (s.charAt(j) == s.charAt(i) && (i + 1 == j || isPalindromic[i + 1][j -1] ) ) ){
            isPalindromic[i][j] = true;
          }
        }
        
        if(isPalindromic[i][j]){
          minCut[i] = Math.min(minCut[i], 1 + minCut[j+1]);
        }
      }      
    }
    
    return minCut[0] - 1;
  }
  
    public int minCut_n(String s) {
        if (null == s || 0 == s.length()) {
            return 0;
        }

        int length = s.length();
        int[] minCut = new int[length + 1];// minCut[i] is the minCut of s.subString(0, i)
        minCut[0] = 0;

        boolean[][] isPalindromic = isPalindromic(s);
        
        for (int i = 1; i <= length; i++) {
            minCut[i] = Integer.MAX_VALUE; // i-1

            for (int j = 0; j < i; j++) {
                if (isPalindromic[j][i - 1] && minCut[j] != Integer.MAX_VALUE) {
                    minCut[i] = Math.min(minCut[i], 1 + minCut[j]);
                }
            }
        }

        return minCut[length] - 1;
    }
    
    private boolean[][] isPalindromic(String s){
        int length = s.length();
        
        boolean[][] isPalindromic = new boolean[length][length];// default all are false
        
        for (int i = 0; i < length; i++) {
            for (int j = 0; j <= i; j++) {
                if (j == i || (s.charAt(j) == s.charAt(i) && (j + 1 == i || isPalindromic[j + 1][i - 1]))) {
                    isPalindromic[j][i] = true;
                }
            }
        }
        
        return isPalindromic;
    }
}
