package fgafa.easy;

import java.util.ArrayList;
import java.util.Hashtable;

public class CompareStrings
{

  /*
   * You are given a string, S, and a list of words, L, that are all of the same
   * length. Find all starting indices of substring(s) in S that is a
   * concatenation of each word in L exactly once and without any intervening
   * characters.<br> 
   * 
   * i.e. given: S: "barfoothefoobarman"   L: ["foo", "bar"]
   * You should return the indices: [0,9]. 
   * (order does not matter).
   * 
   * Time O(A.length * L.Length * L[0].length)   Space O(L.Length * L[0].length)
   */


  public ArrayList<Integer> findSubstring(String S, String[] L) {

    int lenL = L[0].length();
    Hashtable<String, Integer> in = new Hashtable<String, Integer>();

    for (String s : L) {
      if (in.containsKey(s)) {
        in.put(s, in.get(s) + 1);
      }
      else {
        in.put(s, 1);
      }
    }

    Hashtable<String, Integer> table = new Hashtable<String, Integer>();

    ArrayList<Integer> result = new ArrayList<Integer>();

    next: for (int i = 0; i <= S.length() - L.length * lenL; i++) {

      table.clear();

      for (String s : in.keySet()) {
        table.put(s, 0);
      }

      int current = i;

      for (int j = 0; j < L.length; j++) {
        String s = S.substring(current, current + lenL);
        if (!table.containsKey(s) || table.get(s) >= in.get(s)) {
          continue next;
        }

        table.put(s, table.get(s) + 1);
        current += lenL;
      }

      result.add(i);
    }

    return result;

  }



  /**
   * @param args
   */
  public static void main(String[] args) {
    // TODO Auto-generated method stub

  }

}
