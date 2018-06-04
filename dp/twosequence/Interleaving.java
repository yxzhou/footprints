package fgafa.dp.twosequence;

import java.util.ArrayList;

/* 
 * If all characters of C match either with a character of A or a character of B
 *  and length of C is sum of lengths of A and B, 
 * then C is an interleaving A and B.
 * 
 * Example:
 *   case1: strA = "AB",  strB = "CD"
 *   the interleaving: ABCD, ACBD, ACDB, CABD, CADB, CDAB
 *   case2: strA = "AB",  strB = "C"
 *   the interleaving: ABC,  ACB,  CAB
 * 
 */
public class Interleaving
{

  /**
   * @param args
   */
  public static void main(String[] args) {
    long startnano = System.nanoTime(); 
    long startmill = System.currentTimeMillis();
    
    System.out.println("=====start===="+ startnano);
    System.out.println("=====start===="+ startmill);
    
//    boolean[][] path = new boolean[2][2];
//    
//    for (int i = 0; i < path.length; i++) {
//      for (int j = 0; j < path[i].length; j++) {
//        System.out.print("\t"+ path[i][j]);
//      }
//    }
//    
//    if(true) return;
    
    String strA = "aab";
    String strB = "aac";
    
    //String strA = "bbbbbabbbbabaababaaaabbababbaaabbabbaaabaaaaababbbababbbbbabbbbababbabaabababbbaabababababbbaaababaa";
    //String strB = "babaaaabbababbbabbbbaabaabbaabbbbaabaaabaababaaaabaaabbaaabaaaabaabaabbbbbbbbbbbabaaabbababbabbabaab";

    String[] strDst = {"ABCAABCEF", "AABBCCAEF", "ABACBCAEF","ABCABCEF", "ABCAEF", "ABCEAF", "ABCEF"
        ,"aaabac"
        ,"babbbabbbaaabbababbbbababaabbabaabaaabbbbabbbaaabbbaaaaabbbbaabbaaabababbaaaaaabababbababaababbababbbababbbbaaaabaabbabbaaaaabbabbaaaabbbaabaaabaababaababbaaabbbbbabbbbaabbabaabbbbabaaabbababbabbabbab"};
    
    Interleaving s = new Interleaving();
    
    //check if it's interleaving
    for(int i=0; i<strDst.length; i++){
      System.out.println("\n-"+i+"- "+strA+" and "+strB);
      System.out.println("to "+strDst[i].toLowerCase());
      System.out.println(s.isInterleave_recur(strA, strB, strDst[i].toLowerCase()));  
      System.out.println(s.isInterleave_dp_I(strA, strB, strDst[i].toLowerCase()));
      System.out.println(s.isInterleave_dp_II(strA, strB, strDst[i].toLowerCase()));
      System.out.println(s.isInterleave_n(strA, strB, strDst[i].toLowerCase()));
      System.out.println(s.isInterleave_dp_II2(strA, strB, strDst[i].toLowerCase()));

    }
    
    System.out.println("===== end ===="+ (System.nanoTime() - startnano));
    System.out.println("===== end ===="+ (System.currentTimeMillis() - startmill));
    
    //if(true) return;
    
    //get all Interleavings by insert    
    int iStop = strA.length() + strB.length();
    
    s.setList(new ArrayList<String>());
    s.getInterleavingByInsert(strA.toCharArray(), strB.toCharArray(), iStop, 0, 0, "");
    System.out.println(s.getList().toString());
    
    //get all interleaving by order
    s.setList(new ArrayList());
    s.getInterleavingsByOrder(strA, strB);
    
    System.out.println(s.getList().toString());
    
    
  }

  /*
   * Given s1, s2, s3, find whether s3 is formed by the interleaving of s1 and s2.
   * 
   * For example,
   * Given:
   * s1 = "aabcc",
   * s2 = "dbbca",
   * 
   * When s3 = "aadbbcbcac", return true.
   * When s3 = "aadbbbaccc", return false.
   * 
   */
  public boolean isInterleave_recur(String strA, String strB, String strDst){
    //check
    if(strA == null)
      return strDst.equals(strB);
    if(strB == null)
      return strDst.equals(strA);
    int lenA = strA.length();
    int lenB = strB.length();
    int lenDst = strDst.length();
    if((lenA + lenB) != lenDst)
      return false;
    
    boolean[][] path = new boolean[lenA+1][lenB+1];  //default it's false
    
    //return isInterleave_recur(strA, strB, strDst, 0, 0, 0) ;
    return isInterleave_recur_X(strA, strB, strDst, 0, 0, 0, path) ;

  }
  
  /*  will be time out to big data test case */
  private boolean isInterleave_recur(String strA, String strB, String strDst, int i, int j, int k) {

    //System.out.println("===i:"+i+"\tj:"+j+"\tk:"+k);
    
    int lenA = strA.length();
    int lenB = strB.length();
    int lenDst = strDst.length();

    if (i >= lenA && j >= lenB && k >= lenDst) {
      return true;
    }
    
    boolean ret = false;
    if (!ret && i < lenA && strA.charAt(i) == strDst.charAt(k)) {
      ret |= isInterleave_recur(strA, strB, strDst, i + 1, j, k + 1);
    }
    if (!ret && j < lenB && strB.charAt(j) == strDst.charAt(k)) {
      ret |= isInterleave_recur(strA, strB, strDst, i, j + 1, k + 1);
    }
    
    return ret;
  }

  /*  same as isInterleave_recur, while adding a cache to avoid duplicate checking 
   *  Time O(m*n)  Space O(m*n) */
  private boolean isInterleave_recur_X(String strA, String strB, String strDst, int i, int j, int k, boolean[][] path) {

    // System.out.println("===i:"+i+"\tj:"+j+"\tk:"+k);

    if ((i > strA.length()) || (j > strB.length()) || (k > strDst.length()))
      return false;

    // Iterate through all characters of C.
    char c;
    for (; k < strDst.length(); k++) {
      // System.out.println("==i:"+i+"\tj:"+j+"\tk:"+k);
      if (path[i][j]) // avoid do it again
        return false;
      else
        path[i][j] = true;

      c = strDst.charAt(k);
      if (i < strA.length() && strA.charAt(i) == c) {
        if (j < strB.length() && strB.charAt(j) == c) // both A and B match C
          return isInterleave_recur_X(strA, strB, strDst, i + 1, j, k + 1, path)
              || isInterleave_recur_X(strA, strB, strDst, i, j + 1, k + 1, path);
        else // only A match C
          i++;

      }
      else if (j < strB.length() && strB.charAt(j) == c) // only B match C
        j++;
      else // If doesn't match with either A or B, then return false
        return false;

    }

    return true;
  }
  
  
  /* Time O(n*m)  Space O(n) */
  public boolean isInterleave_dp_II2(String s1, String s2, String strDst) {
    if(strDst == null){
        return false;
    }
    if (s1 == null){
      return strDst.equals(s2);
    }
    if (s2 == null){
      return strDst.equals(s1);
    }

    int m = s1.length();
    int n = s2.length();
    int l = strDst.length();
    if (m + n != l){
      return false;
    }

    boolean[] match = new boolean[n + 1];
    match[n] = true;
    for (int j = n - 1; j >= 0; j--){
      if (!(match[j] = strDst.charAt(m + j) == s2.charAt(j) && match[j + 1])){
        break;
      }
    }

    for (int i = m - 1; i >= 0; i--) {
      for (int j = n - 1; j >= 0; j--) {
        if (strDst.charAt(i + j) == s1.charAt(i) && match[j]){
          match[j] = true;
        }else if (strDst.charAt(i + j) == s2.charAt(j) && match[j + 1]){
          match[j] = true;
        }else{
          match[j] = false;
        }
      }

      match[n] = strDst.charAt(n + i) == s1.charAt(i) && match[n];
    }
    return match[0];
  }
  
  /**
   * Determine whether s3 is formed by interleaving of s1 and s2.
   * @param s1, s2, s3: As description.
   * @return: true or false.
   */
  public boolean isInterleave_n(String s1, String s2, String s3) {
	  if(null == s3){
		  return false;
	  }else if(null == s1){
		  return s2.equals(s3);
	  }else if(null == s2){
		  return s1.equals(s3);
	  }else if(s1.length() + s2.length() != s3.length()){
		  return false;
	  }
	  
	  int m = s1.length();
	  int n = s2.length();
	  if(m > n){
		  return isInterleave_n(s2, s1, s3);
	  }
	  boolean[] isMatch = new boolean[m + 1]; //default all are false
	  isMatch[0] = true;
	  
	  for(int i = 0; i < m; i++){
		  if(s1.charAt(i) == s3.charAt(i)){
			  isMatch[i + 1] = true;
		  }else{
			  break;
		  }
	  }
	  
		for (int j = 0; j < n; j++) { // s2
			isMatch[0] = s3.charAt(j) == s2.charAt(j) && isMatch[0];
			
			for (int i = 0; i < m; i++) { // s1
				isMatch[i + 1] = (isMatch[i] && s1.charAt(i) == s3.charAt(i + j + 1))
						|| (isMatch[i + 1] && s2.charAt(j) == s3.charAt(i + j + 1));
			}
		}
	  
	  
      return isMatch[m];
  }
  
  /*Time O(m*n)  Space O(n)*/
	public boolean isInterleave_dp_II(String s1, String s2, String s3) {
	      if(null == s3){
	          return false;
	      }else if(null == s1){
	          return s2.equals(s3);
	      }else if(null == s2){
	          return s1.equals(s3);
	      }else if(s1.length() + s2.length() != s3.length()){
	          return false;
	      }

		boolean[] match = new boolean[s2.length() + 1]; // default it's false
		match[0] = true;
		for (int j = 0; j < s2.length(); j++){
			if (!(match[j + 1] = s3.charAt(j) == s2.charAt(j) && match[j])){
				break;
			}
		}

//		for (int i = 1; i <= s1.length(); i++) {
//			match[0] = s3.charAt(i - 1) == s1.charAt(i - 1) && match[0];
//
//			for (int j = 1; j <= s2.length(); j++) {
//				match[j] = (s3.charAt(i + j - 1) == s2.charAt(j - 1) && match[j-1])
//						|| (s3.charAt(i + j - 1) == s1.charAt(i - 1) && match[j] );
//			}
//		}
		
		for (int i = 0; i < s1.length(); i++) {
			match[0] = s3.charAt(i) == s1.charAt(i) && match[0];

			for (int j = 0; j < s2.length(); j++) {
				match[j + 1] = (s3.charAt(i + j + 1) == s2.charAt(j) && match[j])
						|| (s3.charAt(i + j + 1) == s1.charAt(i) && match[j+1] );
			}
		}

		return match[s2.length()];
	}
  
  /* Time O(m*n)  Space O(m*n) */
  public boolean isInterleave_dp_I(String s1, String s2, String s3) {
      
    if(null == s3){
        return false;
    }else if(null == s1){
        return s2.equals(s3);
    }else if(null == s2){
        return s1.equals(s3);
    }else if(s1.length() + s2.length() != s3.length()){
        return false;
    }
    
    //create indicator
    boolean[][] match = new boolean[s1.length()+1][s2.length()+1];   //default it's false
    
    //initialization the first row and the first column
    match[0][0] = true;
    for( int i = 1; i <= s1.length(); ++ i ) {
        if (s1.charAt(i - 1) == s3.charAt(i - 1)) {
            match[i][0] = true;
        }else {
            break;
        }
    }
    for( int j = 1; j <= s2.length(); ++ j ) {
        if (s2.charAt(j - 1) == s3.charAt(j - 1)) {
            match[0][j] = true;
        }else {
            break;
        }
    }
    
    //work through the rest of matrix using the formula
    for( int i = 1; i <= s1.length(); ++ i ) {
        for( int j = 1 ; j <= s2.length() ; ++ j ) {
			match[i][j] = (s3.charAt(i + j - 1) == s2.charAt(j - 1) && match[i][j - 1])
					|| (s3.charAt(i + j - 1) == s1.charAt(i - 1) && match[i - 1][j] );
        }
    }
    
    //the last element is the result
    return match[s1.length()][s2.length()];
  }
  
  
  private ArrayList<String> list = new ArrayList<String>() ;
  private ArrayList<String> getList() {
    return list;
  }
  private void setList(ArrayList<String> permutes) {
    this.list = permutes;
  }
  
  /**
   *  
   * e.g.  [a,b,c], [A,B]  output: (3+2)! / (3! * 2!)
   * Step 1:  [a] or [A]
   * Step 2:  [ab], [aA] or [Aa], [AB]
   * Step 3:  [abc], [abA], [aAb], [aAB] or [Aab], [AaB], [ABa]
   * Step 4:  [abcA], [abAc], [abAB], [aAbc], [aAbB], [aABb] or [Aabc], [AabB], [AaBb], [ABab]
   * Step 5:  [abcAB], [abAcB], [abABc], [aAbcB], [aAbBc], [aABbc] or [AabcB], [AabBc], [AaBbc], [ABabc]
   */
  public void getInterleavingByInsert(char[] a1, char[] a2, int iStop, int ia1,
      int ia2, String prefix) {

    if (prefix.length() == iStop) {
      this.getList().add(prefix);
      return;
    }

    String sTmp;
    int iTmp;
    if (ia1 < a1.length ) 
      getInterleavingByInsert(a1, a2, iStop, ia1+1, ia2, prefix + a1[ia1]);

    if (ia2 < a2.length) 
      getInterleavingByInsert(a1, a2, iStop, ia1, ia2+1, prefix + a2[ia2]);

  }

  
  
  
  /*
   * Method #1
   * e.g.  [a,b,c], [A,B]  output: (3+2)! / (3! * 2!)
   * 00011 =>00101 =>00110 =>01001 =>01010 =>01100 =>10001 =>10010=> 10100 =>11000
   * 
   */
  public void getInterleavingsByOrder(String s1, String s2){
    //check
    if(s1 == null)
      return;
    if(s2 == null)
      return;
    
    //init
    int iLen1 = s1.length();
    int iLen2 = s2.length();
    if(iLen2 > iLen1){
      getInterleavingsByOrder(s2, s1);
      return ;
    }
      
    //s1.length() >= s2.length(), init 00011
    int iLen=iLen1+iLen2;   
    int[] indexs = new int[iLen];
    for(int i=0; i<iLen1; i++){
      indexs[i] = 0;
    }
    for(int i=iLen1; i<iLen; i++){
      indexs[i] = 1;
    }    

    do{
      String str = parseInterleaving(s1, s2, indexs);
      
      this.getList().add(str);
      //System.out.println(sb.toString());
      
    }while(getNextInterleaving(indexs));
    
  }
  
  /*
   * 00011 =>00101 =>00110 =>01001 =>01010 =>01100 =>10001 =>10010=> 10100 =>11000
   *  
   * Refer to careercup.Bit37
   * Find the next smallest that have the same number of 1 bits in their binary representation
   * 1 From the right to left, find the first "01", convert the first "01" to "10"
   * 2 To to right of "01", make the number as small as possible 
   * e.g. ****011100 => ****101100 => ****100011 
   * 
   * @ return: false means can't deal with it.
   */
  private boolean getNextInterleaving(int[] indexs){
    int j=indexs.length - 2;
    for( ; j>=0 && indexs[j]>= indexs[j + 1]; j-- ){
      ; //fetch from right, break if the left is smaller than the right 
    }
    
    if(j < 0) return false; // no next
    
    int k = j+1;
    swap(indexs, j, k);
    
    for(j=j+2, k=indexs.length - 1; j < k; j++, k-- )
      swap(indexs, j, k);
    
    return true;
  }
  
  private String parseInterleaving(String s1, String s2, int[] indexs){
    //check
    if(s1 == null || s2 == null || (s1.length() + s2.length()) != indexs.length)
      return null;
    
    StringBuffer sb = new StringBuffer();
    
    int i=0, j=0;
    char[] chars1 = s1.toCharArray();
    char[] chars2 = s2.toCharArray();
    for(int k=0; k < indexs.length; k++ ){
      if(indexs[k] == 0){
        sb.append(chars1[i++]);
      }else{
        sb.append(chars2[j++]);
      }
      
    }
    
    return sb.toString();
  }
  
  private void swap(int[] arr, int i, int j) {
    if (i != j) {
      int temp = arr[i];
      arr[i] = arr[j];
      arr[j] = temp;
    }
  }
  
  /*
   * TODO 
   * 
   * e.g.  [a,b,c], [A,B]  output: (3+2)! / (3! * 2!) ==> it's similar with selecting 2 from 5. Combination. 
   * The final result is 12345, AB can take: 
   * [12], [13], [14], [15], [23], [24], [25], [34], [35], [45]
   * 
   */
  
  
  
  
}
