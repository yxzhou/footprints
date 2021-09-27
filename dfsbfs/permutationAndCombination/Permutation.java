package dfsbfs.permutationAndCombination;

/*
 * 
 * Definition 1: n!,  permutations, P(n)
 * Definition 2: n!/m!,  permutations, P(n, m)
 * Definition 3: n!/[m!(n-m)!] combination, C(n, m)
 * 
 * case 1:  write a method to "shuffle" a deck of cards.
 * 
 */
import java.lang.Math;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Hashtable;
import java.util.List;

import util.Misc;

public class Permutation
{

  private ArrayList<String> list = new ArrayList<String>();



  public ArrayList<String> getList() {
    return list;
  }



  public void setList(ArrayList<String> permutes) {
    this.list = permutes;
  }



  /**
   * @param args
   */
  public static void main(String[] args) {
    char[] arr = {'a', 'b', 'c', 'd'};
    int iStop = 3;
    int iStart = 0;
    int iLen = arr.length;
    int iEnd = iLen;

    Permutation sv = new Permutation();

    // get permutation by swap
    sv.setList(new ArrayList<String>());
    iEnd = iLen;
    sv.getPermutesBySwap(arr, iStop, iStart, iEnd);
    System.out.println("--getPermutesBySwap--  "+sv.getList().toString());

    // get combination 
    sv.setList(new ArrayList<String>());
    
    iEnd = iLen - iStop + 1;

    // get permutation by insert
     sv.setList(new ArrayList<String>());
     sv.getList().add(String.valueOf(arr[0]));
     iStop = iLen;
     sv.getPermutesByInsert(arr, iStart + 1, iStop);
     System.out.println("--getPermutesByInsert--" +sv.getList().toString());

    // get permutation by order
    sv.setList(new ArrayList<String>());
    sv.getPermutesByOrder(arr);
    System.out.println("--getPermutesByOrder-- "+sv.getList().toString());


    for (int i = 0; i < iLen + 5; i++) {
      System.out.println("--" + i + "--: " + sv.getPermuteByOrder(arr, i));
    }

    // get permutation by mod
    sv.setList(new ArrayList<String>());
    sv.getPermutesByMod(arr);
    System.out.println("--getPermutesByMod--   "+sv.getList().toString());

    int[] n = {1,2,3,4,5};
    int[] k ={1,2,6,24,120};
    for(int i=0; i< n.length; i++){
      for (int j = 1; j <= k[i]; j++) {
        System.out.println("--getPermutation--k: "+j + " "+ sv.getPermutation(n[i], j));
      }      
    }
   
    int[][] ns = {{1},{1,2},{1,2,3},{1,2,3,4}};
    for(int i=0; i< ns.length; i++){
      System.out.println("\ngetPermutes " + Misc.array2String(ns[i]) + ":");
      Misc.printListList(sv.getPermutes(ns[i]));
    }
    
    int[][] ns2 = {{1},{1,2},{1,2,3},{1,1,2}, {1,1,2,2}};
    for(int i=0; i< ns2.length; i++){
      System.out.println("\ngetPermuteUnique_X " + Misc.array2String(ns2[i]) + ":");    
    
      Misc.printListList(sv.getPermuteUnique_X(ns2[i]));
    }
    
  }



  /*
   * 全排列（递归交换）算法 1、将第一个位置分别放置各个不同的元素； 2、对剩余的位置进行全排列（递归）； 3、递归出口为只对一个元素进行全排列。
   * e.g. input [1,2,3],  output 3!/2! 
   * Step 1) select 1, swap 1-1, get [1], remain (2, 3) for next select
   *  1.1) select 2, swap 2-2, get [1, 2]
   *  1.2) select 3, swap 2-3, get [1, 3]  
   * Step 2) select 2, swap 1-2, get [2], remain (1, 3) for next select
   *  2.1) select 1, swap 2-1, get [2, 1]
   *  2.1) select 3, swap 2-3, get [2, 3]
   * Step 3) select 3, swap 1-3, get [3], remain (1, 2) for next select
   *  3.1) select 1, swap 3-1, get [3, 1]
   *  3.1) select 2, swap 3-2, get [3, 2]
   * 
   *  refer to CareerCup 2.3
   *  
   */

  public void getPermutesBySwap(char[] arr, int iStop, int iStart, int iEnd) {

    if (iStart >= iStop) {
      StringBuffer sb = new StringBuffer();
      for (int i = 0; i < iStop; i++)
        sb.append(arr[i]);

      this.getList().add(sb.toString());
      return;
    }

    for (int i = iStart; i < iEnd; i++) {
      Misc.swap(arr, iStart, i);
      getPermutesBySwap(arr, iStop, iStart + 1, iEnd);
      Misc.swap(arr, iStart, i);
    }

  }



  
  /**
   * -- 插入递归全排列算法, 先取数组的第一个元素，作成数组，结果是 [0] 接下来取数组的第二个元素，按顺序插入以上数组的"空位"，结果是
   * e.g. input [1,2,3],  output 3! 
   * Step 1, insert 1:  [1]
   * Step 2, insert 2:  [12], [21] 
   * Step 3, insert 3:  [123], [132], [312], [213] [231], [321]
   */
  public void getPermutesByInsert(char[] arr, int iStart, int iStop) {

    if (iStart >= iStop)
      return;

    String str;
    int len = this.getList().size();
    for (int j = 0; j < len; j++) {
      str = this.getList().get(0);

      getPermutesByInsert(str, arr[iStart]);

      this.getList().remove(str);
    }

    getPermutesByInsert(arr, iStart + 1, iStop);
  }

 

  private void getPermutesByInsert(String str, char ch) {
    for (int i = str.length(); i >= 0; i--) {
      this.getList().add(str.substring(0, i) + ch + str.substring(i));
    }
  }
  

  /*
   * 全排列（非递归求顺序）算法 1、建立位置数组，即对位置进行排列，排列成功后转换为元素的排列； 2、按如下算法求全排列：
   * 设P是1～n(位置编号)的一个全排列：p = p1,p2...pn =
   * p1,p2...pj-1,pj,pj+1...pk-1,pk,pk+1...pn
   * (1)从排列的尾部开始，找出第一个比右边位置编号小的索引j（j从首部开始计算），即j = max{i | pi < pi+1}
   * (2)在pj的右边的位置编号中，找出所有比pj大的位置编号中最小的位置编号的索引k，即 k = max{i | pi > pj}
   *    pj右边的位置编号是从右至左递增的，因此k是所有大于pj的位置编号中索引最大的 
   * (3)交换pj与pk
   * (4)再将pj+1...pk-1,pk,pk+1...pn翻转得到排列p'= p1,p2...pj-1,pj,pn...pk+1,pk,pk-1...pj+1 
   * (5)p'便是排列p的下一个排列 
   * 
   * 例如：  to 24310:
   * (1)从右至左找出排列中第一个比右边数字小的数字2；
   * (2)在该数字后的数字中找出比2大的数中最小的一个3； 
   * (3)将2与3交换得到34210；
   * (4)将原来2（当前3）后面的所有数字翻转，即翻转4210，得30124； 
   * 求得24310的下一个排列为30124。
   */
  public void getPermutesByOrder(char[] arr) {
    int iLen = arr.length;
    int[] indexs = new int[iLen];
    for (int i = 0; i < iLen; i++) {
      indexs[i] = i;
    }

    do {
      StringBuffer sb = new StringBuffer();
      for (int i = 0; i < indexs.length; i++) {
        sb.append(arr[indexs[i]]);
      }

      this.getList().add(sb.toString());
      // System.out.println(sb.toString());

    } while (getNextPermutes(indexs));

  }

/**
 * 例如：  to 24310:
 * (1)从右至左找出排列中第一个比右边数字小的数字2；
 * (2)在该数字后的数字中找出比2大的数中最小的一个3； 
 * (3)将2与3交换得到34210；
 * (4)将原来2（当前3）后面的所有数字翻转，即翻转4210，得30124； 
 * 求得24310的下一个排列为30124。
 * 
 * @param indexs
 * @return
 */
  private boolean getNextPermutes(int[] indexs) {
    int j = indexs.length - 2;
    for (; j >= 0 && indexs[j] > indexs[j + 1]; j--) ; // fetch from right, break if the left is smaller than the right    

    if (j < 0)
      return false; // no next, ( all finished, such as "321" or "111" )  

    int k = indexs.length - 1;
    for (; k > j && indexs[k] < indexs[j]; k--); // fetch from right, break if 
    
    Misc.swap(indexs, j, k);

    for (j = j + 1, k = indexs.length - 1; j < k; j++, k--)
      Misc.swap(indexs, j, k);

    return true;
  }

  /*
   * @
   * @return the kth permute, 
   *   null means no such permute
   * 
   */
  private String getPermuteByOrder(char[] arr, int k) {
    String ret = null;

    int iLen = arr.length;
    int[] indexs = new int[iLen];
    for (int i = 0; i < iLen; i++) {
      indexs[i] = i;
    }

    while (k > 1 && getNextPermutes(indexs)) {
      k--;
    };

    if (k == 1) {
      StringBuffer sb = new StringBuffer();
      for (int i = 0; i < indexs.length; i++) {
        sb.append(arr[indexs[i]]);
      }
      ret = sb.toString();
    }

    return ret;
  }



  /*
   * 全排列（非递归求模）算法 1、初始化存放全排列结果的数组result，与原数组的元素个数相等； 2、计算n个元素全排列的总数，即n!；
   * 3、从>=0的任意整数开始循环n!次，每次累加1，记为index；
   * 4、取第1个元素arr[0]，求1进制的表达最低位，即求index模1的值w，将第1个元素（arr[0]）插入result的w位置，并将index迭代为index\1；
   * 5、取第2个元素arr[1]，求2进制的表达最低位，即求index模2的值w，将第2个元素（arr[1]）插入result的w位置，并将index迭代为index\2；
   * 6、取第3个元素arr[2]，求3进制的表达最低位，即求index模3的值w，将第3个元素（arr[2]）插入result的w位置，并将index迭代为index\3；
   * 7、…… 8、直到取最后一个元素arr[arr.length-1]，此时求得一个排列； 9、当index循环完成，便求得所有排列。 例：
   * 求4个元素["a", "b", "c", "d"]的全排列,
   * 共循环4!=24次，可从任意>=0的整数index开始循环，每次累加1，直到循环完index+23后结束；
   * 假设index=13（或13+24，13+2*24，13+3*24…），因为共4个元素，故迭代4次，则得到的这一个排列的过程为：
   * 第1次迭代，13/1，商=13，余数=0，故第1个元素插入第0个位置（即下标为0），得["a"]； 第2次迭代，13/2,
   * 商=6，余数=1，故第2个元素插入第1个位置（即下标为1），得["a", "b"]； 第3次迭代，6/3,
   * 商=2，余数=0，故第3个元素插入第0个位置（即下标为0），得["c", "a", "b"]； 第4次迭代，2/4，商=0，余数=2,
   * 故第4个元素插入第2个位置（即下标为2），得["c", "a", "d", "b"]；
   */

  public void getPermutesByMod(char[] arr) {
    char[] result = new char[arr.length];

    int fac = 1;
    for (int i = 2; i <= arr.length; i++)
      fac *= i;

    for (int index = 0; index < fac; index++) {
      int t = index;
      for (int i = 1; i <= arr.length; i++) {
        int w = t % i;
        for (int j = i - 1; j > w; j--)
          result[j] = result[j - 1];
        result[w] = arr[i - 1];
        t = (int) Math.floor(t / i);
      }
      this.getList().add(String.valueOf(result));
    }

  }

  /*
   * The set [1,2,3,…,n] contains a total of n! unique permutations.
   * 
   * By listing and labeling all of the permutations in order,
   * We get the following sequence (ie, for n = 3):
   * 
   * "123"
   * "132"
   * "213"
   * "231"
   * "312"
   * "321"
   * Given n and k, return the kth permutation sequence.
   * 
   * Note: Given n will be between 1 and 9 inclusive.
   * 
   */
  
  public String getPermutation(int n, long k) {
    StringBuffer result = new StringBuffer();
    
    long[] fac = new long[n];
    fac[0] = 1; 
    for (int i = 1; i < n; i++){
      fac[i] = fac[i-1] * i;
    }
    
    List<Integer> list = new ArrayList<Integer> ();
    for(int i=1; i<=n; i++){
      list.add(i);
    }
    
    k--;
    int remainder;
    while( n > 0){
      n --;

      remainder = (int)(k/fac[n]);
      k = k%fac[n];
        
      result.append(list.get(remainder));
      list.remove(remainder);
    }
   
    return result.toString();
  }
  
  /**
   * Given a collection of numbers, return all possible permutations.
   * 
   * For example,
   * [1,2,3] have the following permutations:
   * [1,2,3], [1,3,2], [2,1,3], [2,3,1], [3,1,2], and [3,2,1].
   * 
   * @param num
   * @return
   */
  public List<List<Integer>> getPermutes(int[] num) {
    List<List<Integer>> result = new ArrayList<>();
    
    //check
    if(num == null || num.length == 0)
      return result;
    
    int n = num.length;
    long[] fac = new long[n+1];
    fac[0] = 1; 
    for (int index = 1; index <= n; index++){
      fac[index] = fac[index-1] * index;
    }
    
    int digit;
    for(int k=1; k<=fac[n]; k++){
      String ret = getPermutation(n, k);
      
      ArrayList<Integer> list = new ArrayList<Integer>();
      for(int i=0; i< ret.length(); i++){
        digit = ret.charAt(i) - 49;  // ret.charAt(i) - 48 - 1;
        list.add(num[digit]);  
      }
      
      result.add(list);
    }
    
    return result;
  }
  
  /**
   * Given a collection of numbers that might contain duplicates, return all possible unique permutations.
   * 
   * For example,
   * [1,1,2] have the following unique permutations:
   * [1,1,2], [1,2,1], and [2,1,1].
   * 
   */
  public ArrayList<ArrayList<Integer>> getPermuteUnique(int[] num) {
    ArrayList<ArrayList<Integer>> result = new ArrayList<ArrayList<Integer>>();
    
    //check
    if(num == null || num.length == 0)
      return result;
    
    int n = num.length;
    long[] fac = new long[n+1];
    fac[0] = 1; 
    for (int index = 1; index <= n; index++){
      fac[index] = fac[index-1] * index;
    }
    
    Hashtable<String, String> ht = new Hashtable<String, String>();
    StringBuffer key ;
    int digit;
    for(int k=1; k<=fac[n]; k++){
      String ret = getPermutation(n, k);
      
      key = new StringBuffer();
      ArrayList<Integer> list = new ArrayList<Integer>();
      for(int i=0; i< ret.length(); i++){
        digit = ret.charAt(i) - 49;  // ret.charAt(i) - 48 - 1;
        list.add(num[digit]);  
        key.append(num[digit]);
      }
      
      if(!ht.containsKey(key.toString())){
        ht.put(key.toString(), "");
        
        result.add(list);
      }
      
    }
    
    return result;
  }
  
  class Node{
    ArrayList<Integer> list;
    int index;
    Node(ArrayList<Integer> list, int i){
      this.list = list;
      this.index = i;
    }
  }
  
 /* refer to getPermutesByInsert */
  public List<List<Integer>> getPermuteUnique_X(int[] num) {
    List<List<Integer>> result = new ArrayList<>();
    
    //check
    if(num == null || num.length == 0)
      return result;
    
    int len = num.length;
    List<Integer> list = new ArrayList<Integer> ();
    for(int i=0; i< len; i++){
      list.add(num[i]);
    }
    Collections.sort(list);
    list.add(0, Integer.MAX_VALUE); // the border 
    
    boolean duplicate = false;
    ArrayList<Node> ret = new ArrayList<Node>();
    
    for(int i=1; i<list.size(); i++){
      if(list.get(i) == list.get(i-1))
        duplicate = true;
      else{
        duplicate = false;
      }
      
      getPermutesByInsert(list.get(i), duplicate, len,  ret);

    }
    
    for (int i = 0; i < ret.size(); i++) {
      result.add(ret.get(i).list);
    }
    
    return result;
  }
  
  
  private void getPermutesByInsert(int ch, boolean duplicate, int length, ArrayList<Node> ret) {
    
    if(ret.size() == 0){
      ArrayList<Integer> first = new ArrayList<Integer>();
      first.add(ch);
      ret.add(new Node(first, 0));
    }else{
      Node parent;
      for (int i = ret.size(); i >0; i--) {
        parent = ret.get(0);
        ret.remove(0);
        
        int j = 0;
        if(duplicate){
          j = parent.index + 1;
        }
        ArrayList<Integer> curr = parent.list;
        for( ; j<=curr.size(); j++){
          ArrayList<Integer> childList = (ArrayList<Integer>)curr.clone();
  
          childList.add(j, ch);
          ret.add( new Node(childList, j));
        }
      }
    }
  }
  
  
}
