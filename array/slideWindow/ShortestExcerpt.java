package fgafa.array.slideWindow;

import java.util.ArrayList;
import java.util.List;

/**
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
 * 
 */


public class ShortestExcerpt
{
  static String content[] = {"a", "c", "d", "a", "c", "b", "d", "e", "a", "a",
      "b"};
  static String keyword[] = {"b", "c", "d"};



  public static void main(String args[]) {
    // init

    // print input
    System.out.print("content:");
    for (int i = 0; i < content.length; i++)
      System.out.print(content[i] + " ");
    System.out.println();
    System.out.print("keyword:");
    for (int i = 0; i < keyword.length; i++)
      System.out.print(keyword[i] + " ");
    System.out.println();

    ShortestExcerpt s = new ShortestExcerpt();

    s.findAbstract_X();

  }

/*
 *  brute force. 
 *  
 *  1) Define 2 points (pEnd and pBegin), at beginning, they point to the content first record.
 *  2) pEnd move to right until it includes all Keys.
 *  3) pBegin move to right until it's not includes all Keys,  so we got one Window width.  
 *  4) loop #2 and #3 until pEnd == nLen.
 *  
 *  Time O( 2n*O(isAllHave) )  => O( n^2 * m )
 */
  public void findAbstract_X(){
    int resultBegin = 0, resultEnd = 0;
    int resultLen = content.length + 1;
    
    int pBegin = 0, pEnd = 0;
    
    int nLen = content.length;
    
    while (pEnd < nLen) {
      
      while( !isAllHave(content, pBegin, pEnd, keyword) && pEnd < nLen ){
        pEnd ++;
      }
      
      while(isAllHave(content, pBegin, pEnd, keyword)){
        if((pEnd - pBegin) < resultLen){
          resultLen = pEnd - pBegin;
          resultBegin = pBegin;
          resultEnd = pEnd;  
        }
        pBegin ++;
      }     
      
    }
    
    //print the result
    System.out.println("[ShortestMatch]: " + iCount);
    for (int i = resultBegin; i < resultEnd; i++)
      System.out.print(content[i] + " ");
    
    
  }
  
/*
 * brute force.  
 * 
 * 1) define 2 points (pEnd and pBegin), at beginning, they point to the content first and last record.
 * 2) pBegin move from 0 to right, pEnd move from content.length to left. 
 *  and (end - start)>keys.length.     
 * 
 * 
 * Time  O( (n-m)*(n-m)*O(isAllHave) ) ==>  O(n^3 * m )  Space O(n)  
 * 
 */

  public void findAbstract01() {

    List<String> result = new ArrayList<String>();
    List<String> contentList = new ArrayList<String>();

    int pBegin = 0;
    int pEnd = content.length;

    for (int i = 0; i < pEnd; i++)
      contentList.add(i, content[i]);

    // print the result
    result = contentList;
    System.out.println("AllMatch:" );
    for (pEnd = content.length; pEnd - pBegin >= keyword.length; pEnd--) {
      for (pBegin = 0; pEnd - pBegin >= keyword.length; pBegin++) {
        if (isAllHave(content, pBegin, pEnd, keyword)
            && result.size() > pEnd - pBegin) {
          result = contentList.subList(pBegin, pEnd);
          System.out.println("   " + result);
        }
      }
      pBegin = 0;
    }
    System.out.println("[ShortestMatch]: " + iCount + " "+ result);

  }


 /*
  *   
  * Time O(n*m) 
  */
  private boolean isAllHave(String[] arr, int pBegin, int pEnd, String[] keys) {
    
    for (int i = 0; i < keys.length; i++)
      if (!isContentIn(arr, pBegin, pEnd, keys[i]))
        return false;

    return true;
  }
  
  private boolean isContentIn(String[] arr, int pBegin, int pEnd, String content) {
    count();
    
    for (int i = pBegin; i < pEnd; i++)
      if (arr[i] == content)
        return true;
    return false;

  }

  static int iCount = 0;
  private static void count(){
    iCount ++;
  }
  
}
