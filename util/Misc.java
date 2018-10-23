package fgafa.util;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;


public class Misc
{

  public static int max(int x, int y){
    return (x > y)? x : y;  
    
  }
  public static int min(int x, int y){
    return (x < y)? x : y;  
    
  }
  
  public static void swap(char[] arr, int i, int j) {
    if (i != j) {
      char temp = arr[i];
      arr[i] = arr[j];
      arr[j] = temp;
    }
  }


  public static void swap(int[] arr, int i, int j) {
    if (i != j) {
      int temp = arr[i];
      arr[i] = arr[j];
      arr[j] = temp;
    }
    
    /* Here are some wayz to swap two variables without using temp variable */
//    //solution #1
//    arr[i] = arr[i] + arr[j];
//    arr[j] = arr[i] - arr[j];
//    arr[i] = arr[i] - arr[j];
//    //solution #2
//    arr[i] = arr[i] * arr[j];
//    arr[j] = arr[i] / arr[j];
//    arr[i] = arr[i] / arr[j];
//    //solution #3  
//    arr[i] = arr[i] ^ arr[j];
//    arr[j] = arr[i] ^ arr[j];
//    arr[i] = arr[i] ^ arr[j];
    /**
     * Don't use these tricks others are suggesting in real code. They don't work for all cases. 
     * If you don't know why look into values like "non a number", "infinity" and look at floating point arithmetic & storage when exponents are significantly different, 
     * it simply will not work and is REALLY BAD code.
     * 
     * If for some reason you don't have another variable at your disposal, then you could probably use the above method. (Of course only for ints, and maybe for chars with a ascii value of less than 128).
     * Even if you use this, you will still in memory be using extra space.
     * For the operation a = a + b, not only the memory spaces of a and b is used. A tmp is used in anyway to store the value of a + b, before is stores that value in a. 
     * So even though you're not using a tmp variable in your source code, it gets compiled to be using a temporary space in memory.
     * And, besides the fact that you're using the same number of memory addresses, you have extra instructions now also. Three extra arithmetic operations. 
     * And even though they take only one clock cycle, it is still one clock cycle too much.
     * 
     */
    
  }

  
  
//  public static void printIntArrayListArrayList(List<List<Integer>> resultList) {
//
//    for (int i = 0; i < resultList.size(); i++) {
//      printArrayList((List<Integer>)resultList.get(i));
//      
//    }
//  }

  
  public static void printIntArrayList(List<int[]> resultList) {

    for (int i = 0; i < resultList.size(); i++) {
      System.out.print("," + array2String(resultList.get(i)));
    }
  }

  public static void printInt2ArrayList(List<int[][]> resultList) {

    for (int i = 0; i < resultList.size(); i++) {
      System.out.println(array2String(resultList.get(i)));
      
    }
  }
  
  public static void printArrayList_Integer(List<Integer> list) {
      if(list == null || list.size() == 0){
        System.out.println("Null");
        return;
      }
      
      for (int i = 0; i < list.size(); i++) {
        System.out.print("\t-"+i+"-\t\""+list.get(i)+"\"");
      }
      
      System.out.println();
  }

  
  public static void printArrayList(List<String> list) {
    if(list == null || list.size() == 0){
      System.out.println("Null");
      return;
    }
    
    for (int i = 0; i < list.size(); i++) {
      System.out.print("\t-"+i+"-\t\""+list.get(i)+"\"");
    }
    
    System.out.println();
  }
  
  public static void printStringArrayList(List<String[]> resultList) {
    if(resultList == null || resultList.size() == 0){
      System.out.println("Null");
      return;
    }
    
    for (int i = 0; i < resultList.size(); i++) {
      System.out.print("\t-"+i+"-\t"+Misc.array2String(resultList.get(i)));
    }
    
    System.out.println();
  }

  public static <E> void printList(List<E> result){
	    if(result == null || result.size() == 0){
	        System.out.println("Null");
	        return;
	      }
	      
        for (int i = 0; i < result.size(); i++) {
            System.out.print("\t-" + i + "-\t" );
        }
        System.out.println();
        for (int i = 0; i < result.size(); i++) {
            System.out.print("\t" + result.get(i) + "\t");
        }
	      
	      System.out.println();
  }
  
  public static <E> void printListList(List<List<E>> result){
	    if(result == null || result.size() == 0){
	        System.out.println("Null");
	        return;
	      }
	      
	      for (int i = 0; i < result.size(); i++) {
	    	  System.out.print("---"+i+"---");
	    	  printList(result.get(i));
	      }
	      
	      System.out.println();
  }
  
  public static <E> void printListList(ArrayList<ArrayList<E>> result){
	    if(result == null || result.size() == 0){
	        System.out.println("Null");
	        return;
	      }
	      
	      for (int i = 0; i < result.size(); i++) {
	    	  System.out.print("---"+i+"---");
	    	  printList(result.get(i));
	      }
	      
	      System.out.println();
}
  
	public static void printMetrix(char[][] metrix) {
		if (null == metrix || 0 == metrix.length || 0 == metrix[0].length) {
			System.out.println("Null");
			return;
		}

		for (int i=0; i< metrix.length; i++) {
			System.out.print("\t-" + i + "-\t"
					+ Misc.array2String(metrix[i]));
		}

		System.out.println();
	}

	public static void printFile(String file){

        byte[] buffer = new byte[8192];

        try(InputStream input = new BufferedInputStream(new FileInputStream(file))) {
            for (int length = 0; (length = input.read(buffer)) != -1;) {
                System.out.write(buffer, 0, length);
            }
        } catch (Exception e){
            e.printStackTrace();
        } finally {
        }
    }

  public static StringBuffer array2String(boolean[] array) {
    StringBuffer returnValue = new StringBuffer();

    returnValue.append("[");
    for (int i = 0; i < array.length; i++) {
      returnValue.append(array[i]);
      returnValue.append(", ");
    }
    returnValue.deleteCharAt(returnValue.length() - 1);
    returnValue.deleteCharAt(returnValue.length() - 1);
    returnValue.append("]");
    
    return returnValue;
  }
  
  public static StringBuffer array2String(int[] array) {
    if(array == null)
      return null;

    StringBuffer returnValue = new StringBuffer();

    returnValue.append("[ ");
    for (int i = 0; i < array.length; i++) {
      returnValue.append(array[i]);
      returnValue.append(", ");
    }
    
    if(array.length > 0)
      returnValue.deleteCharAt(returnValue.length() - 2);
    
    //if(returnValue.length() > 0)
      returnValue.append("]");
    
    return returnValue;
  }
  
  public static <E> StringBuffer array2String(E[] array) {
	    if(array == null)
	      return null; 
	    
	    StringBuffer returnValue = new StringBuffer();

	    returnValue.append("[ ");
	    for (int i = 0; i < array.length; i++) {
	      returnValue.append(array[i]);
	      returnValue.append(", ");
	    }
	    
	    if(array.length > 0)
	      returnValue.deleteCharAt(returnValue.length() - 2);
	    
	    //if(returnValue.length() > 0)
	      returnValue.append("]");
	    
	    return returnValue;
	  }
  
  public static StringBuffer array2String_T(int[] array) {
    if(array == null)
      return null; 
    
    StringBuffer returnValue1 = new StringBuffer();
    StringBuffer returnValue2 = new StringBuffer();
    StringBuffer returnValue3 = new StringBuffer();
    
    
    returnValue1.append("[ ");
    returnValue2.append("--");
    returnValue3.append("  ");
    
    for (int i = 0; i < array.length; i++) {
      returnValue1.append(array[i]);
      returnValue1.append("\t");
      
      returnValue2.append("-------");
      
      returnValue3.append(i + "\t");
    }
    
    //if(returnValue.length() > 0)
      returnValue1.append("]");
    
    returnValue1.append("\n");
    returnValue1.append(returnValue2);
    returnValue1.append("\n");
    returnValue1.append(returnValue3);
    
    return returnValue1;
  }

  public static StringBuffer array2String(double[] array) {
    StringBuffer returnValue = new StringBuffer();

    returnValue.append("[");
    for (int i = 0; i < array.length; i++) {
      returnValue.append(array[i]);
      returnValue.append(", ");
    }
    returnValue.deleteCharAt(returnValue.length() - 1);
    returnValue.deleteCharAt(returnValue.length() - 1);
    returnValue.append("]");
    
    return returnValue;
  }


  public static StringBuffer array2String(int[][] array) {
    StringBuffer returnValue = new StringBuffer();
    if(null == array){
    	return returnValue;
    }
    
    for (int i = 0; i < array.length; i++) {
      returnValue.append("\n");
      
      for(int j = 0; j< array[i].length; j++){
        returnValue.append(array[i][j]);
        returnValue.append(",\t");        
      }
    }

    return returnValue;
  }

  
  public static StringBuffer array2String(String[] array) {
    StringBuffer returnValue = new StringBuffer();

    for (int i = 0; i < array.length; i++) {
      returnValue.append(array[i]);
      returnValue.append(", ");
    }

    return returnValue;
  }

    public static StringBuffer array2String(List<String> list) {
        StringBuffer returnValue = new StringBuffer();

      if(null == list){
        return returnValue.append("null");
      }

        for (int i = 0; i < list.size(); i++) {
            returnValue.append(list.get(i));
            returnValue.append(", ");
        }

        int length = returnValue.length();
        if(length > 2){
            returnValue.delete(length -2, length);
        }

        return returnValue;
    }

  public static StringBuffer array2String(String[][] array) {
    StringBuffer returnValue = new StringBuffer();
    
    for (int i = 0; i < array.length; i++) {
      returnValue.append("\n");      
      for(int j = 0; j< array[i].length; j++){
        returnValue.append(array[i][j]);
        returnValue.append(", ");        
      }
    }

    return returnValue;
  }

  
  public static StringBuffer array2String(char[] array, int len) {
    StringBuffer returnValue = new StringBuffer();

    for (int i = 0; i < len; i++) {
      returnValue.append(array[i]);

    }

    return returnValue;
  }
  
  public static StringBuffer array2String(char[] array) {
    StringBuffer returnValue = new StringBuffer();

    for (int i = 0; i < array.length; i++) {
      returnValue.append(array[i]);

    }

    return returnValue;
  }


  public static boolean compare(int[] array1, int[] array2){
	if(array1 == null){
	    return array2 == null;
    }

    if(array1.length != array2.length){
	    return false;
    }

    for(int i = 0; i < array1.length; i++){
        if(array1[i] != array2[i]){
            return false;
        }
    }

    return true;
  }

  public static boolean compare(Object[] o1, Object[] o2){
      if(o1 == null){
          return o2 == null;
      }

      if(o1.length != o2.length){
          return false;
      }

      for(int i = 0; i < o1.length; i++){
          if(o1[i].equals(o2[i])){
              return false;
          }
      }

      return true;
  }


}
