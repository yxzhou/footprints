package util;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;


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
        if (list == null || list.size() == 0) {
            System.out.println("Null");
            return;
        }

        for (int i = 0; i < list.size(); i++) {
            System.out.print("\t-" + i + "-\t\"" + list.get(i) + "\"");
        }

        System.out.println();
    }

    public static void printArray_Int(int[] list) {
        if (list == null || list.length == 0) {
            System.out.println("[]");
            return;
        }

        StringBuilder result = new StringBuilder();

        result.append("[");

        for (int i = 0; i < list.length; i++) {
            result.append(list[i]).append(",\t");
        }

        result.delete(result.length() - 2, result.length());
        result.append("]");
        System.out.println(result.toString());
    }

    public static void printArrayList(List<String> list) {
        printArrayList(list, true, "");
    }
    
    public static void printArrayList(List<String> list, boolean printId, String delimiter) {
        if (list == null || list.size() == 0) {
            System.out.println("Null");
            return;
        }

        for (int i = 0; i < list.size(); i++) {
            System.out.print(String.format("%s %s %s", printId? "\t-" + i + "-\t" : "", list.get(i), delimiter));
        }

        System.out.println();
    }

    public static void printStringArrayList(List<String[]> resultList) {
        if (resultList == null || resultList.size() == 0) {
            System.out.println("Null");
            return;
        }

        for (int i = 0; i < resultList.size(); i++) {
            System.out.print("\t-" + i + "-\t" + Misc.array2String(resultList.get(i)));
        }

        System.out.println();
    }

    public static <E> void printList(List<E> result) {
        if (result == null || result.size() == 0) {
            System.out.println("Null");
            return;
        }

//        for (int i = 0; i < result.size(); i++) {
//            System.out.print("\t-" + i + "-\t" );
//        }
//        System.out.println();
        for (int i = 0; i < result.size(); i++) {
            System.out.print("\t" + result.get(i) + "\t");
        }

        System.out.println();
        
        //result.stream().map(E::toString).forEach(System.out::print);
    }


    public static <E> void printListList(List<List<E>> result) {
        if (result == null || result.size() == 0) {
            System.out.println("Null");
            return;
        }

        for (int i = 0; i < result.size(); i++) {
            //System.out.print("---"+i+"---");
            printList(result.get(i));
        }

        System.out.println();
    }

    public static void printMetrix(char[][] metrix) {
        if (null == metrix || 0 == metrix.length || 0 == metrix[0].length) {
            System.out.println("Null");
            return;
        }

        for (int i = 0; i < metrix.length; i++) {
            System.out.print("\n-" + i + "-\t"
                    + Misc.array2String(metrix[i]));
        }

        System.out.println();
    }

    public static void printFile(String file) {

        byte[] buffer = new byte[8192];

        try ( InputStream input = new BufferedInputStream(new FileInputStream(file))) {
            for (int length = 0; (length = input.read(buffer)) != -1;) {
                System.out.write(buffer, 0, length);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
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
    
    public static String array2String(int[] array) {
        return array2String(array, true);
    }

    public static String array2String(int[] array, boolean needSpace) {
        if (array == null) {
            return null;
        }

        StringBuffer returnValue = new StringBuffer();

        //returnValue.append("[ ");
        for (int i = 0; i < array.length; i++) {
            returnValue.append(array[i]);
            if(needSpace){
                returnValue.append(", ");
            }else{
                returnValue.append(",");
            }
        }

        int length = returnValue.length();
        if (length > 2) {
            if(needSpace){
                returnValue.delete(length - 2, length);
            }else{
                returnValue.deleteCharAt(length - 1);
            }
        }
        
        //if(returnValue.length() > 0)
        //returnValue.append("]");

        return returnValue.toString();
    }

    public static <E> StringBuffer array2String(E[] array) {
        if (array == null) {
            return null;
        }

        StringBuffer returnValue = new StringBuffer();

        returnValue.append("[ ");
        for (int i = 0; i < array.length; i++) {
            returnValue.append(array[i]);
            returnValue.append(", ");
        }

        if (array.length > 0) {
            returnValue.deleteCharAt(returnValue.length() - 2);
        }

        //if(returnValue.length() > 0)
        returnValue.append("]");

        return returnValue;
    }

    public static StringBuffer array2String_T(int[] array) {
        if (array == null) {
            return null;
        }

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

    public static String array2String(int[][] array) {
        return array2String(array, false);
    }
        
    public static String array2String(int[][] array, boolean oneLine) {

        if (null == array) {
            return "";
        }

        StringBuffer returnValue = new StringBuffer();
        for (int i = 0; i < array.length; i++) {
            if(!oneLine){
                returnValue.append("\n");
            }
            
            returnValue.append("[");
            for (int j = 0, m = array[i].length; j < m; j++) {
                returnValue.append(array[i][j]);
                
                if(j != m - 1){
                    returnValue.append(", ");

                    if(!oneLine){
                        returnValue.append("\t");
                    }
                }
            }
            returnValue.append("]");

            if(oneLine) {
                returnValue.append(", ");
            } else {
                returnValue.append(",");
            }
        }
        
        int length = returnValue.length();
        if (length > 2) {
            if (oneLine) {
                returnValue.delete(length - 2, length);
            } else {
                returnValue.deleteCharAt(length - 1);
            }
        }

        return returnValue.toString();
    }
     

    public static StringBuffer array2String(String[] array) {
        StringBuffer returnValue = new StringBuffer();

        for (int i = 0; i < array.length; i++) {
            returnValue.append(array[i]);
            returnValue.append(", ");
        }

        int length = returnValue.length();
        if(length > 2){
            returnValue.delete(length - 2, length);
        }

        return returnValue;
    }
    
    public static <T> StringBuffer array2String(List<T> list) {
        return array2String(list, true);
    }

    /**
     * 
     * @param <T>
     * @param list
     * @param needSpace or oneLine,  
     * @return 
     */
    public static <T> StringBuffer array2String(List<T> list, boolean needSpace) {
        StringBuffer returnValue = new StringBuffer();

        if (null == list) {
            return returnValue.append("null");
        }else if(list.isEmpty()){
            return returnValue;
        }
        
        if(list.get(0) instanceof List){
            boolean oneLine = needSpace;
            List tmp;
            
            for (int i = 0; i < list.size(); i++) {
                tmp = (List)list.get(i);

                if(!oneLine){
                    returnValue.append("\n");
                }
                
                returnValue.append("[");
                for (int j = 0, m = tmp.size(); j < m; j++) {
                    returnValue.append(tmp.get(j));

                    if(j != m - 1){
                        returnValue.append(", ");
                        
                        if(!oneLine){
                            returnValue.append("\t");
                        }
                    }

                }
                returnValue.append("]");
            }
            
            
        }else{
            for (int i = 0; i < list.size(); i++) {
                returnValue.append(list.get(i));
                if (needSpace) {
                    returnValue.append(", ");
                } else {
                    returnValue.append(",");
                }
            }
            
            int length = returnValue.length();
            if (length > 2) {
                if (needSpace) {
                    returnValue.delete(length - 2, length);
                } else {
                    returnValue.deleteCharAt(length - 1);
                }
            }
        }

        return returnValue;
    }

    public static String array2String(String[][] array) {
        return array2String(array, false);
    }
        
    public static String array2String(String[][] array, boolean oneLine) {
        if (null == array) {
            return "";
        }
                
        StringBuffer returnValue = new StringBuffer();

        for (int i = 0; i < array.length; i++) {
            if(!oneLine){
                returnValue.append("\n");
            }
            
            returnValue.append("[");
            for (int j = 0, m = array[i].length; j < m; j++) {
                returnValue.append(array[i][j]);
                
                if(j != m - 1){
                    returnValue.append(", ");

                    if(!oneLine){
                        returnValue.append("\t");
                    }
                }
            }
            returnValue.append("]");
        }

        return returnValue.toString();
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

    public static boolean compare(int[] array1, int[] array2) {
        if (array1 == null) {
            return array2 == null;
        }

        if (array1.length != array2.length) {
            return false;
        }

        for (int i = 0; i < array1.length; i++) {
            if (array1[i] != array2[i]) {
                return false;
            }
        }

        return true;
    }

    public static boolean compare(Object[] o1, Object[] o2) {
        if (o1 == null) {
            return o2 == null;
        }

        if (o1.length != o2.length) {
            return false;
        }

        for (int i = 0; i < o1.length; i++) {
            if (o1[i].equals(o2[i])) {
                return false;
            }
        }

        return true;
    }
    
    public static void sort(List<List<Integer>> result){
        Collections.sort(result, (a, b) -> {
            for(int i = 0, n = Math.min(a.size(), b.size()); i < n; i++ ){
                if(a.get(i) != b.get(i)){
                    return a.get(i) - b.get(i);
                }
            }
            
            return a.size() < b.size()? 1 : -1;
        });
    }

    public static List<List<String>> convert(String[][] strings) {

        List<List<String>> result = new LinkedList<>();

        for (String[] ss : strings) {
            result.add(Arrays.asList(ss));
        }

        return result;
    }
    
    public static List<List<Integer>> convert(int[][] nums) {

        List<List<Integer>> result = new LinkedList<>();

        for (int[] ss : nums) {
            result.add(Arrays.stream(ss)
                                .boxed()
                                .collect(Collectors.toCollection(ArrayList::new)));
        }

        return result;
    }
    
    public static List<List<Double>> convert(double[][] nums) {

        List<List<Double>> result = new LinkedList<>();

        for (double[] ss : nums) {
            result.add(Arrays.stream(ss)
                                .boxed()
                                .collect(Collectors.toCollection(ArrayList::new)));
        }

        return result;
    }
    
    
    public static int[][] convertInteger(List<List<Integer>> nums) {
        return nums.stream().map(l -> l.stream().mapToInt(Integer::intValue).toArray()).toArray(int[][]::new);
    }
    
    public static double[][] convertDouble(List<List<Double>> nums) {
        return nums.stream().map(l -> l.stream().mapToDouble(Double::doubleValue).toArray()).toArray(double[][]::new);
    }
    
    public static String[][] convertString(List<List<Double>> nums) {
        return nums.stream().map(l -> l.stream().toArray(String[]:: new)).toArray(String[][]::new);
    }
    
    public static char[][] convert(String[] matrix){
        char[][] r = new char[matrix.length][];
        
        for(int i = 0; i < matrix.length; i++){
            r[i] = matrix[i].toCharArray();
        }
        
        return r;
    }
    
}
