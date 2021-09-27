package uva.permutationAndCombination.AnagramN195;

import java.io.*;
import java.util.*;

class Main
{

  public void printPermutation(String input) {
    // parse the input
    HashMap<Character, Integer> hm = new HashMap<Character, Integer>();
    Character ch;
    int allCount = 0;
    for (int i = 0; i < input.length(); i++) {
      ch = input.charAt(i);

      if(isValid(ch)){
        hm.put(ch, hm.containsKey(ch) ? hm.get(ch) + 1 : 1);
        allCount++;        
      }

    }

    if(allCount == 0)
      return ;
    
    List<Character> list = new ArrayList<Character>();
    for (Character lowercase = 'a', uppercase = 'A'; lowercase <= 'z'; lowercase++, uppercase++) {
      if (hm.containsKey(uppercase))
        list.add(uppercase);
      if (hm.containsKey(lowercase))
        list.add(lowercase);

    }

    // getPermutesByOrder
    int distinctCount = list.size();
    int[] indexs = new int[allCount];
    for (int i = 0, j = 0; i < distinctCount; i++)
      for (int k = hm.get(list.get(i)); k > 0; k--)
        indexs[j++] = i;

    StringBuffer sb;
    do {

      sb = new StringBuffer();
      for (int i = 0; i < allCount; i++) {
        sb.append(list.get(indexs[i]));
      }
      System.out.println(sb.toString());

    }
    while (getNextPermutes(indexs, allCount));

  }



  /* 1123->1132->1213->1231->1312->1321->2113->2131->2311->3112->3121->3211 */
  private boolean getNextPermutes(int[] indexs, int length) {
    int j = length - 2;
    /* fetch from right, break if the left is smaller than the right */
    for (; j >= 0 && indexs[j] >= indexs[j + 1]; j--);

    if (j < 0)
      return false; // no next, ( all finished, such as "321" or "111" )

    int k = length - 1;
    /* from right, fetch the first one that bigger than -- */
    for (; k > j && indexs[k] <= indexs[j]; k--);

    swap(indexs, j, k);
    for (j = j + 1, k = length - 1; j < k; j++, k--)
      swap(indexs, j, k);

    return true;
  }



  private static void swap(int[] arr, int i, int j) {
    int temp = arr[i];
    arr[i] = arr[j];
    arr[j] = temp;
  }

  private boolean isValid(char ch){
    return ( ch >= 'a' && ch <= 'z' ) || ( ch >= 'A' && ch <= 'Z' );
  }
  

  public static void main(String[] args) {
    Main sv = new Main();

    Scanner in = new Scanner(new BufferedInputStream(System.in), "UTF-8");

    long start = System.currentTimeMillis();
    try {
      int n = Integer.valueOf(in.nextLine().trim());

      for (int i = n; i > 0; i--)
        sv.printPermutation(in.nextLine());
    }
    catch (Exception e) {
      e.printStackTrace();
    }
    finally {
      in.close();
      System.out.printf("%n%10d%n", System.currentTimeMillis() - start);
    }
  }
}
