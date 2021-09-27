package dp;


/**
 * Leetcode #87
 * 
 * Given a string s1, we may represent it as a binary tree by partitioning it to two non-empty substrings recursively.
 *
 *Below is one possible representation of s1 = "great":
 *
 *    great
 *   /    \
 *  gr    eat
 * / \    /  \
 *g   r  e   at
 *           / \
 *          a   t
 *To scramble the string, we may choose any non-leaf node and swap its two children.
 *
 *For example, if we choose the node "gr" and swap its two children, it produces a scrambled string "rgeat".
 *
 *    rgeat
 *   /    \
 *  rg    eat
 * / \    /  \
 *r   g  e   at
 *           / \
 *          a   t
 *We say that "rgeat" is a scrambled string of "great".
 *
 *Similarly, if we continue to swap the children of nodes "eat" and "at", it produces a scrambled string "rgtae".
 *
 *    rgtae
 *   /    \
 *  rg    tae
 * / \    /  \
 *r   g  ta  e
 *      / \
 *      t   a
 * We say that "rgtae" is a scrambled string of "great".
 *
 * Given two strings s1 and s2 of the same length, determine if s2 is a scrambled string of s1.
 *
 * Thoughts:
 *
 * If string s1 and s2 are scramble strings, there must be a point that breaks s1 to two parts s11, s12,
 * and a point that breaks s2 to two parts, s21, s22, and isScramble(s11, s21) && isScramble(s12, s22) is true,
 * or isScramble(s11,  s22) && isScramble(s12, s21) is true.
 * 
 * So we can make it recursively. We just break s1 at different position to check if there exists one position satisfies the requirement.
 * 
 * Some checks are needed otherwise it will time out. For example, if the lengths of two strings are different, they can’t be scramble.
 * And if the  characters in two strings are different, they can’t be scramble either.
 * 
 * Another way is to use DP. I use a three dimension array scramble[][][] to  save the states.
 * What scramble[k][i][j] means is that two substrings of  length k,
 * one starts from i of string s1, another one starts from j of string s2, are scramble. We are trying to find scramble[L][0][0].
 * For every length k, we try to divide the string to two parts differently, checking if there is a way that can make it true.
 *
 */
public class ScrambleString {

  /**
   * ("abab", "bbaa") ==> true
   * ("bccab","cbcbb") ==> true
   * 
   * @param s1
   * @param s2
   * @return
   */

    public boolean isScramble_recursive(String s1, String s2) {
        //check null, return true if both are null; return false if only one is null.
        if(null == s1 || null == s2) {
            return s1 == s2;
        }
        //if both are not null, check lengths. return false if the length is not same
        if (s1.length() != s2.length()) {
            return false;
        }

        int n = s1.length();
        return isScramble(s1.toCharArray(), 0, s2.toCharArray(), 0, n, new int[n + 1][n][n]) == 1;
    }

    private int isScramble(char[] s1, int i, char[] s2, int p, int len, int[][][] cache) {
        if(cache[len][i][p] > 0){
            return cache[len][i][p];
        }

        if(len == 1){
            cache[len][i][p] = ( s1[i] == s2[p] ? 1 : 2);
        }

        if(isSame(s1, i, s2, p, len)){
            cache[len][i][p] = 1;
        }

        if(!includeSameCharacters(s1, i, s2, p, len)){
            cache[len][i][p] = 2;
        }

        if(cache[len][i][p] > 0){
            return cache[len][i][p];
        }

        cache[len][i][p] = 2;

        for(int k = 1 ; k < len; k++){
            if( (isScramble(s1, i, s2, p, k, cache) == 1 && isScramble(s1, i + k, s2, p + k, len - k, cache) == 1)
                    || (isScramble(s1, i, s2, p + len - k, k, cache) == 1 && isScramble(s1, i + k, s2, p, len - k, cache) == 1) ){
                cache[len][i][p] = 1;
                break;
            }
        }

        return cache[len][i][p];
    }
  
  private boolean isSame(char[] str1, int s1, char[] str2, int s2, int length){
	  for(int i = s1, j = s2, end = s1 + length; i < end; i++, j++){
		  if(str1[i] != str2[j]){
			  return false;
		  }
	  }
	  
	  return true;
  }
  
  private boolean includeSameCharacters(char[] str1, int s1, char[] str2, int s2, int length){
        int[] chars = new int[26]; // ?? all are lower-case characters ??
        for (int i = s1, j = s2, end = s1 + length; i < end; i++, j++) {
            chars[str1[i] - 'a']++;
            chars[str2[j] - 'a']--;
        }

        for (int i = 0; i < 26; i++) {
            if (chars[i] != 0){
                return false;
            }
        }
        
        return true;
  }
  
	/**
	 * 
	 * Define a three dimension array scramble[][][] to save the states. 
	 * What scramble[k][i][j] means is that two substrings of length k, 
	 * one starts from i of string s1, another one starts from j of string s2, are scramble. 
	 * We are trying to find scramble[L][0][0]. 
	 * 
	 * For every length k, we try to divide the string to two parts differently, 
	 * checking if there is a way that can make it true.
	 */
  public boolean isScramble_dp(String s1, String s2){
	  //check null
	  if(null == s1 || null == s2){
		  return s1 == s2;
	  }
      //Check lengths.
      if (s1.length() != s2.length()){
          return false;
      }
      
      int length = s1.length();
      
      //check characters.
      if(isSame(s1.toCharArray(), 0, s2.toCharArray(), 0, length)){
          return true;
      }
      if(!includeSameCharacters(s1.toCharArray(), 0, s2.toCharArray(), 0, length)){
          return false;
      }

      boolean[][][] scramble = new boolean[length][length][length];
      for (int i = 0; i < length; i++) {
          for (int j = 0; j < length; j++)
              if (s1.charAt(i) == s2.charAt(j)){
                  scramble[0][i][j] = true;
              }
      }
      
      boolean isScramble = false;
      for (int k = 2; k <= length; k++) {
          for (int i = length - k; i >= 0; i--) {
              for (int j = length - k; j >= 0; j--) {
                  for (int m = 1; m < k; m++) {
                      if ( (scramble[m - 1][i][j] && scramble[k - m - 1][i + m][j + m]) 
                        || (scramble[m - 1][i][j + k -m] && scramble[k - m - 1][i + m][j]) ){
                          scramble[k - 1][i][j] = isScramble;
                          break;
                      }
                  }
              }
          }
      }
      
      return scramble[length - 1][0][0];
}

	/**
	 * Define a three dimension array scramble[][][] to save the states. 
	 * What scramble[k][i][j] means is that two substrings of length k, 
	 * one starts from i of string s1, another one starts from j of string s2, are scramble. 
	 * We are trying to find scramble[L][0][0]. 
	 * 
	 * 1) isScramble("a1", "b2") = true, when a1 == b1
	 * =>scramble[1][i][j] = true when s1[i] == s2[j]
	 * 2) isScramble("a1a2", "b1b2") = true, when (a1 == b1 && a2 == b2 ) || ( a1 == b2 && a2 == b1)
	 * =>scramble[2][i][j] = true when (scramble[1][i][j] && scramble[1][i+1][j+1]) || (scramble[1][i][j+1] && scramble[1][i+1][j])
	 * 
	 * 3) isScramble("a[1, k)", "b[1, k)") = true, 
	 * when (a[1, m) == b[1, m) && a[m, k) == b[m, k) ) || ( a[1, m) == b[k-m+1, k) && a[m, k) == b[1, k-m+1)) = true, m is [1, k), k is [2, length+1)
	 * =>scramble[k][i][j] = true when (scramble[m][i][j] && scramble[k-m][i+m][j+m]) || (scramble[m][i][j+k-m] && scramble[k-m][i+m][j])
	 *   
	 */
  public boolean isScramble_dp2(String s1, String s2){ 
	  //check null
	  if(null == s1 || null == s2)
		  return s1 == s2;
      //Check lengths.
      if (s1.length() != s2.length())
          return false;
      if (s1.equals(s2))
          return true;
          
      int length = s1.length();
      boolean[][][] scramble = new boolean[length+1][length][length];
      // scramble[1][i][j] = true when s1[i] == s2[j]
      for (int i = 0; i < length; i++) {
          for (int j = 0; j < length; j++){
              if (s1.charAt(i) == s2.charAt(j)){
                  scramble[1][i][j] = true;
              }
          }
      }
      
      for (int k = 2; k <= length; k++) {
          for (int i = length - k; i >= 0; i--) {
          //for (int i = 0; i <= length - k; i--) {
              for (int j = length - k; j >= 0; j--) {
              //for (int j = 0; j <= length - k; j--) {
            	  //if(!scramble[k][i][j]){
                      for (int m = 1; m < k; m++) {
                          if( (scramble[m][i][j] && scramble[k - m][i + m][j + m]) || (scramble[m][i][j + k -m] && scramble[k - m][i + m][j])){
                           	  scramble[k][i][j] = true;
                        	  break;
                          }
                      }
            	  //}
              }
          }
      }
      
      return scramble[length][0][0];
}

  /**
   * @param args
   */
  public static void main(String[] args) {


  }

}
