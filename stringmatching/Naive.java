package stringmatching;

import java.util.ArrayList;

import util.Misc;

/**
 * @version
 * @author yxzhou (zhouyuanxi@gmail.com)
 */
public class Naive
{

  /**
   * @param args
   */
  public static void main(String[] args) {
    //  String txt = args[0];
    //  String pat = args[1];
   
    String txt = "abcababcaabcabcdeabc";
    String pat = "bc*a*a";
                 //"abc";

    System.out.println(" The txt is:     : " + txt);
    System.out.println(" The pattern is  : " + pat);
    
    char[] arrayT = txt.toCharArray();
    char[] arrayP = pat.toCharArray();
    
    wildcardStrMatcher(arrayT, arrayP);
    
    wildcardStrMatcher2(arrayT, arrayP);
    
    //naiveStringMatcher2(arrayT, arrayP);
    
    int[] matches = naiveStrMatcher(arrayT, arrayP);
    System.out.println(" The txt is:     : " + txt);
    for(int i=0; i< matches.length; i++){
      //System.out.println(" The txt is:     : " + txt);
      System.out.print("                   ");
      for (int l = 0; l < matches[i]; l++)
        System.out.print(" ");
      System.out.println(Misc.array2String(arrayP));
    }
  }



  /**
   * match a String it contains a gap character * that can match an arbitrary
   * string of characters (even one of zero length)
   * 
   * @param arrayT
   * @param arrayP
   * @return void
   * @throws
   */
  public static void wildcardStrMatcher(char[] arrayT, char[] arrayP) {

    // split arrayP to * and sub-arrays

    ArrayList<char[]> arrayListP = splitArray(arrayP);
    
    System.out.print("The arrayP is splitted to:  ");
    printCharArrayList(arrayListP);
    System.out.println(" --end--");
    
    // match the sub-arrays
    ArrayList<String> resultList = new ArrayList<String>();
    
    wildcardStrMatcher(arrayT, 0, arrayListP, 0, resultList); 
  }

  /*
   * recursively match the ordered sub-strings. 
   * 
   */
  private static void wildcardStrMatcher(char[] arrayT, int indexT, ArrayList<char[]> arrayListP, int indexP, ArrayList<String> resultList) {
       
    if(indexP >= arrayListP.size() || indexT >= arrayT.length ){
      if(resultList.size() == arrayListP.size() ) {
        System.out.print(" #3 Pattern occurs with shift: ");
        printStringArrayList(resultList);
        System.out.println(" --end--");
      }

      return;
    }
    
    char[] tmpArray = arrayListP.get(indexP);
    if( '*' == tmpArray[0]){
      resultList.add("*");
      
      wildcardStrMatcher(arrayT, indexT, arrayListP, indexP + 1, resultList); 
    }else{
      int[] matchers = naiveStrMatcher(arrayT, indexT, tmpArray);    
      int i = 0;
      
      while( i < matchers.length ){
        ArrayList<String> resultListInner = (ArrayList<String>) resultList.clone();
        
        resultListInner.add(String.valueOf(matchers[i]));
        
        wildcardStrMatcher(arrayT, matchers[i] + tmpArray.length, arrayListP, indexP + 1, resultListInner);
        i ++;
      }      
    }   
  }

  /*
   * same purpose as naiveStringMatcher3, while better performance
   */
  public static void wildcardStrMatcher2(char[] arrayT, char[] arrayP) {

    // split arrayP to * and sub-arrays

    ArrayList<char[]> arrayListP = splitArray(arrayP);
    
    System.out.print("The arrayP is splitted to:  ");
    printCharArrayList(arrayListP);
    System.out.println(" --end--");
    
    // match the sub-arrays
    ArrayList<int[]> indexList = new ArrayList<int[]>();
    int indexT = 0;
    for(int i =0; i < arrayListP.size(); i++){
      
      char[] tmpArray = arrayListP.get(i);
      
      if( '*' != tmpArray[0]){
        int[] matchers = naiveStrMatcher(arrayT, indexT, tmpArray);     
        
        if( 0 == matchers.length ) {
          System.out.print(" #31 No Pattern occurs ");
          return;
        }
        
        indexList.add(matchers);
        
        indexT = matchers[0] + tmpArray.length ;
      }
    }
    
    System.out.print(" #31 Found sub Pattern: ");
    Misc.printIntArrayList(indexList);
    System.out.println(" --end--");
  
    //spelling the final result
    ArrayList<String> resultList = new ArrayList<String>();
    wildcardStrMatcher2(indexList, 0, 0, resultList); 
  }
  
  private static void wildcardStrMatcher2(ArrayList<int[]> indexList, int dimension1Index, int dimension2Index, ArrayList<String> resultList){
    
    if(dimension1Index >= indexList.size()){
      if(resultList.size() == indexList.size()){
        
        System.out.print(" #31 Pattern occurs with shift: ");
        printStringArrayList(resultList);
        System.out.println(" --end--");

      }
      return; 
    }
    
    int[] tmpList = indexList.get(dimension1Index);
    
    if(dimension2Index >= tmpList.length){
      wildcardStrMatcher2(indexList, dimension1Index + 1, 0, resultList); 
    }else{
      int preInt = Integer.MIN_VALUE;
      if( 0 != resultList.size()) 
        preInt = Integer.parseInt(resultList.get(resultList.size()-1));
      int tmpInt = 0;
      while(dimension2Index < tmpList.length  ){
        tmpInt = tmpList[dimension2Index];
        if(tmpInt > preInt ) {
          ArrayList<String> resultListInner = (ArrayList<String>) resultList.clone();
          resultListInner.add(String.valueOf(tmpInt));
          wildcardStrMatcher2(indexList, dimension1Index + 1, 0, resultListInner);      
        }
        dimension2Index ++;            
      }
    }
  }
  

  /**
   * split arrayP to * and sub-arrays
   * 
   * @param arrayP
   * @return ArrayList<char[]>, eg: ArrayList({*, *},{a, b},{*}, {b}, {*})
   * @throws
   */
  private static ArrayList<char[]> splitArray(char[] arrayP) {

    ArrayList<char[]> arrayList = new ArrayList<char[]>();

    int m = arrayP.length;

    int j = 0;
    int i = 1;
    char flag = arrayP[0];  // '*' means the gap character, or means the normal character.

    while (i < m) {
      // if found the character type changed, 
      if (('*' == arrayP[i] && '*' != flag)
          || ('*' != arrayP[i] && '*' == flag)) {
        char[] tmpArray = copyArray(arrayP, j, i);
        arrayList.add(tmpArray);

        j = i;
        flag = arrayP[i];
      }

      i++;
    }

    //the end part
    if (m == i && i > j) {
      char[] tmpArray = copyArray(arrayP, j, i);
      arrayList.add(tmpArray);
    }

    return arrayList;

  }



  /**
   * match a String that all characters are different. it's O(n)
   * 
   * @param arrayT
   * @param arrayP
   * @return void
   * @throws
   */
  public static void naiveStringMatcher2(char[] arrayT, char[] arrayP) {

    int operateCount = 0;

    System.out.println(" The txt is:     : " + Misc.array2String(arrayT));

    int n = arrayT.length;
    int m = arrayP.length;

    int dTmp = n - m;

  //-----------------
 
  //----------------
//    for (int s = 0; s <= dTmp;) {
//      int t = 0;
//      while (t < m && arrayP[t] == arrayT[s + t]) {      
//        operateCount++;
//        t++;
//      }
//      
//      if (t == m) {
//        System.out.println(" #2 Pattern occurs with shift:" + s);
//      }
//      
//      if (t == 0)
//        s ++;
//      else 
//        s = s + t ;
//    }
  //----------------------------
    for (int s = 0; s < n; ) {
      int t = 0;
      while (t < m && arrayP[t] == arrayT[s]) {      
        operateCount++;
        t++;
        s++;
      }
      
      if (t == m) {
    //System.out.println(" #2 Pattern occurs with shift:" + (s-t) );
    //System.out.println(" The pattern is  : " + misc.array2String(arrayP));
        System.out.print("                   ");
        for (int l = 0; l < (s-t); l++)
          System.out.print(" ");
        System.out.println(Misc.array2String(arrayP));
        
      }
      
      if (t == 0)
        s ++;
    }  
    
    System.out.println("The comparision is:" + operateCount);
  }



  /**
   * The basic pure NAIVE string matching. It's O( m * ( n - m + 1) )
   * 
   * @param arrayT
   * @param arrayP
   * @return void
   * @throws
   */
  public static int[] naiveStrMatcher(char[] arrayT, char[] arrayP) {
    return naiveStrMatcher(arrayT, 0,  arrayP);
  }


  /**
   * The basic pure NAIVE string matching. It's O( m * ( n - indexT - m + 1) )
   * n = arrayT.length, m = arrayP.length.
   * 
   * @param arrayT
   * @param indexT
   * @param arrayP
   * @return 
   *      return null, if input parameter is not valid
   *      return new int[0], if no any match
   * 
   * @throws
   */
  private static int[] naiveStrMatcher(char[] arrayT, int indexT, char[] arrayP) {
    int[] returnValueTmp = null;
    //int operateCount = 0;
  
    //check input parameter
    if (null == arrayT || 0 == arrayT.length || null == arrayP || 0 == arrayP.length 
        || indexT >= arrayT.length || indexT < 0 
        || arrayP.length > arrayT.length)
      return returnValueTmp;
      
    int n = arrayT.length;
    int m = arrayP.length;
    int dTmp = n - m;

    int i = 0;
    for (int s = indexT; s <= dTmp; s++) {  
      int t = 0;
      while (t < m && arrayP[t] == arrayT[s + t]) {  
        //operateCount++;        
        t++ ;
      }
      
      if (t == m) {
        //System.out.println(" #1 Pattern occurs with shift:" + s);
        if (returnValueTmp == null) {
          returnValueTmp = new int[n-indexT];
        }
        returnValueTmp[i++] = s;
      }
      
    }

    int[] returnValue = new int[i];
    while (i > 0) {
      --i;
      returnValue[i] = returnValueTmp[i];
    }

    //System.out.println("The comparision is:" + operateCount);
    //System.out.println("The returnValue is:" + array2String(returnValue)); // array2String(returnValue)
    return returnValue;
  }

  /*
   * return a new array, that data is from a array [ array[indexStart],
   * array[indexEnd]), includes array[indexStart], not include array[indexEnd] )
   */
  private static char[] copyArray(char[] array, int indexStart, int indexEnd) {

    char[] returnValue = null;

    // check input parameter

    int size = indexEnd - indexStart;
    if (size <= 0)
      return returnValue;
    else
      returnValue = new char[size];

    for (int i = 0; i < size; i++) {
      returnValue[i] = array[indexStart + i];
    }

    return returnValue;
  }
  
  
  private static void printStringArrayList(ArrayList<String> resultList){

    for(int i=0; i< resultList.size(); i++ ){
      System.out.print("," + resultList.get(i));
    }
  }
  
  private static void printCharArrayList(ArrayList<char[]> resultList){

    for(int i=0; i< resultList.size(); i++ ){
      System.out.print("," + Misc.array2String( resultList.get(i) ));
    }
  }
  


}
