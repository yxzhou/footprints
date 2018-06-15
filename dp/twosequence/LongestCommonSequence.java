package fgafa.dp.twosequence;



import fgafa.util.Misc;

/*
 * 
 * 
 * LCSeq(str1, str2), Longest Common Subsequence (LCS), 
 * 查找 LCS 是计算两个序列相似程度的一种方法： LCS 越长，两个序列越相似。
 * e.g:
 *  input:  str1 = {ggcaccacg}, str2 = {acggcggatacg}
 * output:  ggcaacg
 * DP:  
 *  We use the notation opt[i][j] = length of LCS of x[i..M] and y[j..N]
 *  opt[i][j] = 0                               if i == M or j == N
 *            = opt[i+1][j+1] + 1               if str1[i] == str2[j]
 *            = max(opt[i][j+1], opt[i+1][j])   otherwise
 *  
 *  We use the notation opt[i][j] = length of LCS of x[0..i-1] and y[0..j-1]
 * opt[i][j] = 0                              if i == 0 or j == 0
 *           = opt[i-1][j-1] + 1              if arr1[i] == arr2[j]
 *           = max(opt[i][j-1], opt[i-1][j])  otherwise
 * 
 * 
 * LCStr(str1, str2), longest Common consecutive Substring, ( substring => consecutive  )
 * e.g:
 *  input:  str1 = {ggcaccacg}, str2 = {acggcggatacg}
 * output:  ggc or acg
 * LCStr is a special case of LCSeq 
 * 
 * DP：
 *  We use the notation opt[i][j] = length of LCS of x[i..M] and y[j..N]
 *  opt[i][j] = 0                           if str1[i] != str2[j]
 *            = opt[i-1][j-1]+1             if str1[i] == str2[j] 
 *      
 *      
 * LCStr(str1), longest substring without repeating characters
 * e.g:
 *  input:  str1 = "abcabcbb"
 * output:  abc 
 * 
 */

public class LongestCommonSequence
{

  /**
   * @param args
   */
  public static void main(String[] args) {
    // 

    LongestCommonSequence s = new LongestCommonSequence();
    
    String s2 = "ggcaccacg", s1 = "acggcggatacgc"; 
    //String s2 = "abcdefghijklxyztpr", s1 = "pacdfeoomnrdffrr";
    System.out.println("\n callLCSSeq and callLCStr " + s1 + " and " + s2 );    
    System.out.println("\nThe result from calLCSeq_DP is: " + s.calLCSeq_DP(s1.toCharArray(), s2.toCharArray()));
    System.out.println("\nThe result from calLCSeq_DP2 is: " + s.calLCSeq_DP2(s1.toCharArray(), s2.toCharArray()));
    
    System.out.println("\nThe result from calLCStr_DP is: " + s.calLCStr_DP(s1.toCharArray(), s2.toCharArray()));
    System.out.println("\nThe result from calLCStr_DP2 is: " + s.calLCStr_DP2(s1.toCharArray(), s2.toCharArray()));
   
    String[] s3 = {"abcabcbb", "bbb", "abccbabb", "abccbabcd"};
    for(int i=0; i< s3.length; i++){
      System.out.println("\nThe result from calLCStr(str1) for "+ s3[i] +" is: " + s.calLCStr(s3[i].toCharArray()));
    }
    
  }

  
  
  /*
   * fetch Longest Common Subsequence with DP on two arrays.
   * input arr1={a1, a2, ---, am},  arr2={b1, b2, ---, bn}  where m>=n 
   * 
   * We use the notation opt[i][j] = length of LCS of x[i..M] and y[j..N]
   * opt[i][j] = 0                              if i == M or j == N
   *           = opt[i+1][j+1] + 1              if arr1[i] == arr2[j]
   *           = max(opt[i][j+1], opt[i+1][j])  otherwise
   * 
   *        0  1  2  3  4  5  6  7  8  9 10 11 12
   * x\y    a  c  g  g  c  g  g  a  t  a  c  g  
   * --------------------------------------------
   * 0 g    7  7  7  6  6  6  5  4  3  3  2  1  0  
   * 1 g    6  6  6  6  5  5  5  4  3  3  2  1  0
   * 2 c    6  5  5  5  5  4  4  4  3  3  2  1  0  
   * 3 a    6  5  4  4  4  4  4  4  3  3  2  1  0  
   * 4 c    5  5  4  4  4  3  3  3  3  3  2  1  0  
   * 5 c    4  4  4  4  4  3  3  3  3  3  2  1  0  
   * 6 a    3  3  3  3  3  3  3  3  3  3  2  1  0  
   * 7 c    2  2  2  2  2  2  2  2  2  2  2  1  0  
   * 8 g    1  1  1  1  1  1  1  1  1  1  1  1  0  
   * 9      0  0  0  0  0  0  0  0  0  0  0  0  0
   *           
   * time O(m*n) and space O(m*n)
   * 
   * Detail refer to http://introcs.cs.princeton.edu/java/96optimization/
   * 
   * e.g.
   * input:  arr1 = {ggcaccacg}, arr2 = {acggcggatacg}
   * expect output:  ggcaacg
   * --ggc--a-ccacg
   * acggcggat--acg
   * 
   */
  //bottom-up translation of this recurrence
  public String calLCSeq_DP(char[] arr1, char[] arr2){
    if(arr1 == null || arr2 == null)
      return null;
    
    //init

    
    //main program
    int M = arr1.length;
    int N = arr2.length;

    // opt[i][j] = length of LCS of x[i..M] and y[j..N]
    int[][] opt = new int[N+1][M+1];

    // compute length of LCS and all subproblems via dynamic programming
    for (int i = N-1; i >= 0; i--) {
        for (int j = M-1; j >= 0; j--) {
            if (arr1[j] == arr2[i])
                opt[i][j] = opt[i+1][j+1] + 1;
            else 
                opt[i][j] = Math.max(opt[i+1][j], opt[i][j+1]);
        }
    }

    
    // recover LCS itself and print it to standard output
    System.out.println(Misc.array2String(opt));
    
    StringBuffer sb = new StringBuffer();
        
    int i = 0, j = 0;
    while(i < N && j < M) {
        if (arr1[j] == arr2[i]) {
            sb.append(arr2[i]);
            i++;
            j++;
        }
        else if (opt[i+1][j] >= opt[i][j+1]) i++;
        else                                 j++;
    }

    
    return sb.toString();
  }
  
  /*
   * fetch Longest Common Subsequence with DP on two arrays.
   * input arr1={a1, a2, ---, am},  arr2={b1, b2, ---, bn}  where m>=n 
   * 
   * We use the notation opt[i][j] = length of LCS of x[0..i-1] and y[0..j-1]
   * opt[i][j] = 0                              if i == 0 or j == 0
   *           = opt[i-1][j-1] + 1              if arr1[i] == arr2[j]
   *           = max(opt[i][j-1], opt[i-1][j])  otherwise
   * 
   *        0  1  2  3  4  5  6  7  8  9 10 11 12
   * X\Y       a  c  g  g  c  g  g  a  t  a  c  g  
   * --------------------------------------------
   * 0      0  0  0  0  0  0  0  0  0  0  0  0  0 
   * 1 g    0  0  0  1  1  1  1  1  1  1  1  1  1
   * 2 g    0  0  0  1  2  2  2  2  2  2  2  2  2
   * 3 c    0  0  1  1  2  3  3  3  3  3  3  3  3
   * 4 a    0  1  1  1  2  3  3  3  4  4  4  4  4
   * 5 c    0  1  2  2  2  3  3  3  4  4  4  5  5
   * 6 c    0  1  2  2  2  3  3  3  4  4  4  5  5
   * 7 a    0  1  2  2  2  3  3  3  4  4  5  5  5
   * 8 c    0  1  2  2  2  3  3  3  4  4  5  6  6
   * 9 g    0  1  2  3  3  3  4  4  4  4  5  6  7
   *           
   * time O(m*n) and space O(m*n)
   * 
   */
  // 
  public String calLCSeq_DP2(char[] arr1, char[] arr2){
    if(arr1 == null || arr2 == null)
      return null;
    
    //init

    
    //main program
    int M = arr1.length;
    int N = arr2.length;

    // opt[i][j] = length of LCS of x[0..i] and y[0..j]
    int[][] opt = new int[N+1][M+1];

    // compute length of LCS and all subproblems via dynamic programming
    for (int i = 0; i < N; i++) {
        for (int j = 0; j < M; j++) {
            if (arr1[j] == arr2[i])
                opt[i+1][j+1] = opt[i][j] + 1;
            else 
                opt[i+1][j+1] = Math.max(opt[i+1][j], opt[i][j+1]);
        }
    }

    // recover LCS itself and print it to standard output
    StringBuffer sb = new StringBuffer();
    
    int i = N, j = M;
    while(i > 0 && j > 0) {
        if (arr1[j-1] == arr2[i-1]) {
            sb.append(arr1[j-1]);
            i--;
            j--;
        }
        else if (opt[i-1][j] >= opt[i][j-1]) i--;
        else                                 j--;
    }
    
    System.out.println(Misc.array2String(opt));
    return sb.reverse().toString();
  }

  /**
   * @param A, B: Two strings.
   * @return: The length of longest common subsequence of A and B.
   */
  public int longestCommonSubsequence(String A, String B) {
      //check
      if(null == A || 0 == A.length() || null == B || 0 == B.length()){
          return 0;
      }
      
      int m = A.length();
      int n = B.length();
      
      int[][] dp = new int[m + 1][n + 1]; //default all are 0
      for(int i = 0; i < m; i++){
          for(int j = 0; j < n; j++){
              if(A.charAt(i) == B.charAt(j)){
                  dp[i + 1][j + 1] = dp[i][j] + 1;
              }else{
                  dp[i + 1][j + 1] = Math.max(dp[i][j + 1], dp[i + 1][j]);
              }
          }
      }
      
      return dp[m][n];
  }
  
  /*
   * fetch Longest Common consecutive Substring with DP on two arrays.
   * input arr1={a1, a2, ---, am},  arr2={b1, b2, ---, bn}  where m>=n 
   * 
   * We use the notation opt[i][j] = length of LCS of x[0..i-1] and y[0..j-1]
   * opt[i][j] = 0                              if i == 0 or j == 0
   *           = opt[i-1][j-1] + 1              if arr1[i] == arr2[j]
   *           = 0                              otherwise
   * 
   *        0  1  2  3  4  5  6  7  8  9 10 11 12
   * X\Y       a  c  g  g  c  g  g  a  t  a  c  g  
   * --------------------------------------------
   * 0      0  0  0  0  0  0  0  0  0  0  0  0  0 
   * 1 g    0  0  0  1  1  0  1  1  0  0  0  0  1
   * 2 g    0  0  0  1  2  0  1  2  0  0  0  0  1
   * 3 c    0  0  1  0  0  3  0  0  0  0  0  1  0
   * 4 a    0  1  0  0  0  0  0  0  1  0  1  0  0
   * 5 c    0  0  2  0  0  1  0  0  0  0  0  2  0
   * 6 c    0  0  1  0  0  1  0  0  0  0  0  1  0
   * 7 a    0  1  0  0  0  0  0  0  1  0  1  0  0
   * 8 c    0  0  2  0  0  1  0  0  0  0  0  2  0
   * 9 g    0  0  0  3  1  0  2  1  0  0  0  0  3
   *           
   * time O(m*n) and space O(m*n)
   * 
   * e.g.
   * input:  arr1 = {ggcaccacg}, arr2 = {acggcggatacg}
   * expect output:  ggc or acg
   *  
   * calLCStr_DP,  store the above opt[i][j] with int[], the matrix is created line by line, so we can just store the temporary result with int[]
   * calLCStr_DP2, store the above opt[i][j] with int[][] 
   *  
   */
  public String calLCStr_DP(char[] arr1, char[] arr2) {
    if (arr1 == null || arr2 == null)
      return null;

    // main program
    int M = arr1.length;
    int N = arr2.length;

    // opt[i][j] = length of LCS of x[0..i] and y[0..j]
    int[] opt = new int[M + 1];
    
    // compute length of LCS and all subproblems via dynamic programming
    int[] max = new int[M];  // there are LCStr with same length, 
    int[] maxIndex = new int[M];
    //System.out.println(Misc.array2String(opt));
    for (int i = 0; i < N; i++) {
      for (int j = M-1; j >= 0; j--) {
        if (arr1[j] == arr2[i]) {
          opt[j+1 ] = opt[j] + 1;
          
          recordTheMax(max, maxIndex, opt, j);
        }
        else
          opt[j + 1] = 0;
      }
      //System.out.println(Misc.array2String(opt));
    }

    // recover LCS itself and print it to standard output
    System.out.println("max=" + Misc.array2String(max) );
    System.out.println("maxIndex=" + Misc.array2String(maxIndex) );
    
    StringBuffer sb = new StringBuffer();
    for(int k=0; k < M; k++){
      if(max[k] > 0){
        sb.append(getSubString(arr1, maxIndex[k]-max[0], maxIndex[k] ));
        sb.append(" ");
      }
    }

    return sb.toString();
  }
  
  private void recordTheMax(int[] max, int[] maxIndex, int[] opt, int j){
    int M = max.length;
    
    if (opt[j + 1] > max[0]) {           
      //add the new max, replace the first old one
      max[0] = opt[j+1];
      maxIndex[0] = j+1;
      
      //remove the other old 
      for(int k=1; k<M; k++){
        max[k] = 0;
        maxIndex[k] = 0;
      }
    }else if(opt[j + 1] == max[0]){
      //add the new max at the end, no replacement, because the length is as same as existed
      for(int k=0; k< M; k++){
        if(max[k] == 0){
          max[k] = max[0];
          maxIndex[k] = j+1;
          break;   //exit the loop when add it in
        }
      }
    }
  }
  
  private String getSubString(char[] arr, int start, int end){
    StringBuffer sb = new StringBuffer();
    sb.append("[");
    for(int i= start; i< end; i++){
      sb.append(arr[i]);
      sb.append(", ");
    }
    sb.append("]");
    
    return sb.toString();
  }
  

  public String calLCStr_DP2(char[] arr1, char[] arr2) {
    if (arr1 == null || arr2 == null)
      return null;

    // init

    // main program
    int M = arr1.length;
    int N = arr2.length;

    // opt[i][j] = length of LCS of x[0..i] and y[0..j]
    int[][] opt = new int[N + 1][M + 1];

    // compute length of LCS and all subproblems via dynamic programming
    int max = 0, iMax = 0, jMax = 0;
    for (int i = 0; i < N; i++) {
      for (int j = 0; j < M; j++) {
        if (arr1[j] == arr2[i]) {
          opt[i + 1][j + 1] = opt[i][j] + 1;
          if (opt[i + 1][j + 1] > max) {
            iMax = i + 1;
            jMax = j + 1;
            max = opt[iMax][jMax];
          }
        }
        else
          opt[i + 1][j + 1] = 0;
      }
    }

    // recover LCS itself and print it to standard output
    System.out.println(Misc.array2String(opt));
    System.out.println("max=" + max + " iMax=" + iMax + " jMax=" + jMax);

    return getSubString(arr1, iMax-1, jMax );
  }

  /**
   * get the longest substring without repeating characters
   * 
   * @param str
   * @return
   */
  public String calLCStr(char[] str){  
    int len = str.length;
    int i = 0, j = 0;
    int maxLen = 0, maxIndex = i;
    boolean[] exist = new boolean[256]; 
    
    while (j < len) {     
      if (exist[str[j]]) {
        if(maxLen < j-i){
          maxLen = j - i;
          maxIndex = j;
        }
        
        //renew i
        while (str[i] != str[j]) {
          exist[str[i]] = false;
          i++;
        }
        i++;
      } else {
        exist[str[j]] = true;
      }
      
      j++;
    }
    //corner case
    if(maxLen < len-i){
      maxLen = len - i;
      maxIndex = len;
    }
    
    //output
    StringBuffer sb = new StringBuffer();
    for(int k=maxLen; k > 0; k--){
      sb.append(str[maxIndex - k]);
    }
    return sb.toString();
  }
  
}