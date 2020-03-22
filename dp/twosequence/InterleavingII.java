package fgafa.dp.twosequence;

import java.util.ArrayList;

/**
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
public class InterleavingII
{

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
    
    InterleavingII s = new InterleavingII();

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
