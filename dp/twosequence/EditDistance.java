package fgafa.dp.twosequence;

/**
 * 
 * Q1: Given two strings S and T, determine if they are both one edit distance apart.
 * 
 *    boolean isOneEdit(String s, String t)
 * 
 * time O(n) and space O(1)
 * 
 * Q2: Given two words word1 and word2, find the minimum number of steps required to convert word1 to word2. 
 * (each operation is counted as 1 step.)
 * 
 * You have the following 3 operations permitted on a word:
 * 
 * a) Insert a character
 * b) Delete a character
 * c) Replace a character
 * 
 *     int minDistance(String s, String t)
 * 
 * time O(m*n) and space O(m*n)
 *
 */
public class EditDistance
{

  public int minDistance_recur(String word1, String word2) {    
    //check
    if(word1 == null && word2 == null)
      return 0;
    else if(word1 == null || word1.length() == 0)
      return word2.length();
    else if(word2 == null || word2.length() == 0)
      return word1.length();

    int len1 = word1.length();
    int len2 = word2.length();
    
    /* define a int[][] to cache the result */
    int[][] cache = new int[len1+1][len2+1];
    for(int i=0; i<=len1; i++){
      for(int j=0; j<=len2; j++){
        cache[i][j] = -1;
      }
    }
        
    return minDistance(word1, 0, len1-1, word2, 0, len2-1, cache);
  }
  
  private int minDistance(String s1, int start1, int end1, String s2, int start2, int end2, int[][] cache){
    
    /*if it was cached, just return */
    if(cache[start1][start2] != -1){
      return cache[start1][start2]; 
    }
          
    /* no cache, have to calculate  */
    int result = -1;
    if(start1>end1 && start2>end2)  //
      result = 0;
    else if(start1>end1)  //
      result = end2-start2+1;
    else if(start2>end2)  // 
      result = end1-start1+1;
    else{
      /*  */
      if(s1.charAt(start1) == s2.charAt(start2)){
        result = minDistance(s1, start1+1, end1, s2, start2+1, end2, cache);
      }
      else{
        int r1 = minDistance(s1, start1, end1, s2, start2+1, end2, cache); // remove s2.charAt(start2), or add s2.charAt(start2) before s1.charAt(start1)
        int r2 = minDistance(s1, start1+1, end1, s2, start2, end2, cache); // remove s2.charAt(start2), or add s1.charAt(start1) before s2.charAt(start2)
        int r3 = minDistance(s1, start1+1, end1, s2, start2+1, end2, cache); // update, make s2.charAt(start2) and s1.charAt(start1) same
        
        result = Math.min(r1, Math.min(r2, r3))+1;
      }
    }
    
    cache[start1][start2] = result;
    return result;
    
  }
  
  
  
  /*
   * calculate the min steps change, ( same as uva.dp.StringComputerN164 )
   * input arr1={a1, a2, ---, am},  arr2={b1, b2, ---, bn}  
   * 
   * We use the notation dp[i][j] = the min steps change of x[0..i] and y[0..j]
   * dp[i][j] = j                              if i == 0
   *          = i                              if j == 0
   *          = min(dp[i][j-1] + 1,   (Insert)            
   *                dp[i-1][j] + 1,   (Delete)
   *                dp[i-1][j-1]) + s (Change) if arr1[i] == arr2[j], s=0;  else s=1
   *               )    
   *           
   * time O(m*n) and space O(m*n)
   * 
   */
  public int minDistance_DP(String word1, String word2) {
    //init
    int M = word1.length();
    int N = word2.length();  

    int[][] dp = new int[M + 1][N + 1];

    for(int j = 0; j<=N; j++)
      dp[0][j] = j;
        
    for(int i = 0; i<=M; i++)
      dp[i][0] = i;
    
    //main
    for (int i = 1; i <= M; i++) {
      for (int j = 1; j <= N; j++) {
        if (word1.charAt(i - 1) == word2.charAt(j - 1)){
          dp[i][j] = dp[i-1][j-1];
        }else{ 
          dp[i][j] = dp[i-1][j-1] + 1;
        }
        
        dp[i][j] = Math.min(dp[i][j], Math.min(dp[i][j-1], dp[i-1][j]) + 1 );
                
      }
    }

    return dp[M][N];
  }
  
  /**
   * Given two strings S and T, determine if they are both one edit distance apart.
   */
  
  public boolean isOneEditDistance(String s, String t) {
      if(null == s || null == t){
          return false;
      }
      
      int diff = s.length() - t.length();
      
      if(0 == diff){ // same length, check if it can be one replace 
          return isOneReplace(s, t);
      }else if(1 == diff){ // 
          return isOneAdd(s, t);
      }else if(-1 == diff){
          return isOneAdd(t, s);
      }
      
      return false;
  }
  
  private boolean isOneReplace(String s, String t){
      boolean hasOnlyOne = false; // it's 0 at beginning.
      
      for(int i = 0; i < s.length(); i++){
          if(s.charAt(i) != t.charAt(i)){
              if(hasOnlyOne){ // if it has 1 now, return false
                  return false;
              }
              
              hasOnlyOne = true; //
          }
      }
      
      //return true;
      return hasOnlyOne;
  }
  
  private boolean isOneReplace_2(String s, String t){

      for(int i = 0; i < s.length(); i++){
          if(s.charAt(i) != t.charAt(i)){
              return s.substring(i + 1).equals(t.substring(i + 1));
          }
      }
      
      return true;
  }
  
  private boolean isOneAdd(String longer, String shorter){      
      for(int i = 0; i < shorter.length(); i++){
          if(longer.charAt(i) != shorter.charAt(i)){
              return longer.substring(i + 1).equals(shorter.substring(i));
          }
      }
      
      return true;
  }
  
  public boolean isOneEditDistance_n(String s, String t){
      if(null == s || null == t){
          return false;
      }
      
      int diff = s.length() - t.length();
      
      if(diff > 1 || diff < -1){
          return false;
      }
      
      if(1 == diff){
          return isOneEditDistance(t, s);
      }
      
      int i = 0;
      while(i < s.length() && s.charAt(i) == t.charAt(i)){
          i++;
      }
      
      if(i == s.length()){
          return diff == -1;
      }
      
      if(0 == diff){
          i++;
      }
      
      while(i < s.length() && s.charAt(i) == t.charAt(i - diff)){
          i++;
      }
      
      return i == s.length();
      
  }
  
  /**
   * @param args
   */
  public static void main(String[] args) {
    EditDistance sv = new EditDistance();
    
    String[] s1 = {null, "", "horse"};
    String[] s2 = {null, "", "ros"};
    
    for(int i=0; i< s1.length; i++){
      System.out.println("\nInput s1:"+s1[i]+", s2:"+s2[i]);
      System.out.println("Output  :"+ sv.minDistance_recur(s1[i], s2[i]));
      
    }

  }

}
