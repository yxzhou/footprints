package fgafa.math;

import java.util.HashMap;

public class XOR
{

  /**
   * @param args
   */
  public static void main(String[] args) {
    // 
    
    XOR s = new XOR();
    s.findTheMostof();

    s.getTheNum1();
    
    s.getTheNum2();
    
    //s.getTheNum3();
  }



  /*
   * 有长度为1到n共n根木棒，现从中拿走某一根，再放入一根任意长度的木棒。顺次输入这n根木棒的长度，求拿走与放入木棒的长度分别是多少？
   * 
   */

  
  
  /*
   * 有n根木棒，其中有多于一半的木棒其长度相等，顺次输入所有木棒的长度，求出这些长度相等的木棒的长度是多少？
   */
  public void findTheMostof(){
    int[] arr = {2, 3, 3, 4, 3, 5, 3};
    
    int ret = findTheMostof(arr);
    
    assert ( ret != 3 ) : "bad: " ; 
    System.out.println(" The most of int is: "+ret);
    
  }
  
  private int findTheMostof(int[] arr){
    int theOne = 0;
    int cnt = 0;
    
    for(int curr : arr){
      if(cnt == 0){
        theOne = curr;
        cnt ++;
      }else{
        if(theOne == curr)
          cnt ++;
        else
          cnt --;
      }
    }
    
    return theOne;
  }
  /* 有n根木棒，其中有多于1/3的木棒其长度相等，求出这些长度相等的木棒的长度是多少？ */
  private int findTheMostof(int[] arr, int xth){
    HashMap<Integer, Integer> tetris= new HashMap<Integer, Integer>();
    final int n = xth - 1;
    
    int tmp = arr[0];
    for(Integer curr : arr){
      if(tetris.containsKey(curr))
        tetris.put(curr, tetris.get(curr) + 1);
      else{
        if(tetris.size() != n)
          tetris.put(curr, 1);
        else{
          for(Integer key : tetris.keySet()){
            tmp = tetris.get(key);
            if(tmp == 1)
              tetris.remove(key);
            else
              tetris.put(key, tmp - 1);
          }
        }          
      }      
    }
    
    //for output
    for(Integer key : tetris.keySet())
      tmp = tetris.get(key);
    return tmp;
  }
  
  /*
   * 对于给定的n（n为奇数）根木棒，其中有n - 1根是可以按长度配对的，找出按长度配对后剩余的一根木棒
   * 
   */
  public void getTheNum1(){
    int[] arr = {2, 3, 3, 2, 5, 5, 4};
    
    int ret = getTheNum1(arr);
    
    assert ( ret != 4 ) : "bad: " ; 
    System.out.println(" The only number that is single: "+ret);
  }
  
  private int getTheNum1(int[] arr){
    int theOne = 0;
    
    for(int i=0; i< arr.length; i++){
      theOne ^= arr[i];
    }
    
    return theOne;
  }
  
  
  /*
   * 有n种长度的棍子, 其中n-1种长度的有3根,剩下1种长度的只有2根.求那个长度
   * (or 一个全是32位整数的大数组，除了其中一个数字出现2次外，其余的数字都出现了3次。如何找出那个只出现了2次的数字？)
   * (or 一个全是32位整数的大数组，除了其中一个数字出现1次外，其余的数字都出现了3次。如何找出那个只出现了1次的数字？)
   * 
   * solution, 异或的本质是每一bit分别模2加.. 这里换成模3加即可
   */
  public void getTheNum2(){
    int[] arr = {6, 3, 3, 6, 5, 5, 3, 5};
    
    int ret = getTheNum2(arr, 2);
    
    //assert ( ret != 6 ) : "bad: " ; 
    System.out.println(" The only number that show 2 times is: "+ret);
    
    
    int[] arr2 = {3, 3, 6, 5, 5, 3, 5};
    int ret2 = getTheNum2(arr2, 1);
    System.out.println(" The only number that show 1 time is: "+ret2);
    
  }
  
  private int getTheNum2(int[] arr, int flag){
    int ones = 0;   // 出现1次的
    int twos = 0;   // 出现2次的
    int notThrees = 0;
    
    for(int i=0; i< arr.length; i++){
      twos |= ones & arr[i];  // tmp, (出现2次的) and (出现3次的)   
      ones ^= arr[i];    //tmp, (模2 余1,  即(出现1次的) and (出现3次的)-
      
      //模3 余1 and 模3 余2
      notThrees = ~(ones & twos) ;
      ones &= notThrees;
      twos &= notThrees; 
      
    }
    
    if(flag == 1)
      return ones;
    else
      return twos;
  }
  
  /*
   *Given an array where every element occurs three times, except one element which occurs only once. Find the element that occurs once. Expected time complexity is O(n) and O(1) extra space.
   *Examples:
   *
   *Input: arr[] = {12, 1, 12, 3, 12, 1, 1, 2, 3, 3}
   *Output: 2 
   */
  private int getTheNum21(int arr[], int n) {
    int ones = 0, twos = 0;

    int common_bit_mask;

    // Let us take the example of {3, 3, 2, 3} to understand this
    for (int i = 0; i < n; i++) {
      /*
       * The expression "one & arr[i]" gives the bits that are there in both
       * 'ones' and new element from arr[]. We add these bits to 'twos' using
       * bitwise OR Value of 'twos' will be set as 0, 3, 3 and 1 after 1st, 2nd,
       * 3rd and 4th iterations respectively
       */
      twos = twos | (ones & arr[i]);

      /*
       * XOR the new bits with previous 'ones' to get all bits appearing odd
       * number of times Value of 'ones' will be set as 3, 0, 2 and 3 after 1st,
       * 2nd, 3rd and 4th iterations respectively
       */
      ones = ones ^ arr[i];

      /*
       * The common bits are those bits which appear third time So these bits
       * should not be there in both 'ones' and 'twos'. common_bit_mask contains
       * all these bits as 0, so that the bits can be removed from 'ones' and
       * 'twos' Value of 'common_bit_mask' will be set as 00, 00, 01 and 10
       * after 1st, 2nd, 3rd and 4th iterations respectively
       */
      common_bit_mask = ~(ones & twos);

      /*
       * Remove common bits (the bits that appear third time) from 'ones' Value
       * of 'ones' will be set as 3, 0, 0 and 2 after 1st, 2nd, 3rd and 4th
       * iterations respectively
       */
      ones &= common_bit_mask;

      /*
       * Remove common bits (the bits that appear third time) from 'twos' Value
       * of 'twos' will be set as 0, 3, 1 and 0 after 1st, 2nd, 3rd and 4th
       * itearations respectively
       */
      twos &= common_bit_mask;

      // uncomment this code to see intermediate values
      // printf (" %d %d \n", ones, twos);
    }

    return ones;
  }


/*
 * We can sum the bits in same positions for all the numbers and take modulo with 3. The bits for which sum is not multiple of 3, are the bits of number with single occurrence.
 * Let us consider the example array {5, 5, 5, 8}. The 101, 101, 101, 1000
 * Sum of first bits%3 = (1 + 1 + 1 + 0)%3 = 0;
 * Sum of second bits%3 = (0 + 0 + 0 + 0)%0 = 0;
 * Sum of third bits%3 = (1 + 1 + 1 + 0)%3 = 0;
 * Sum of fourth bits%3 = (1)%3 = 1;
 * Hence number which appears once is 1000
 */
  private int getTheNum20(int arr[], int n) {
    // Initialize result
    int result = 0;

    int x, sum;

    // Iterate through every bit
    for (int i = 0; i < Integer.SIZE; i++) {
      // Find sum of set bits at ith position in all array elements
      sum = 0;
      x = (1 << i);
      for (int j = 0; j < n; j++) {
        if ((arr[j] & x) > 0)
          sum++;
      }

      // The bits with sum not multiple of 3, are the bits of element with single/double occurrence
      if ((sum % 3) == 1 || (sum % 3) == 2)  //single or double occurrence  
        result |= (1 << i);
    }

    return result;
  }
  
  
  /*
   *  一堆数，其中一些数出现了一次，一些数出现了两次，只有一个数出现了三次
   *  (or 一个全是32位整数的大数组，除了其中一个数字出现3次外，其余的数字都出现了2次 或 1次。如何找出那个只出现了3次的数字？)
   *  (or 一个全是32位整数的大数组，除了其中一个数字出现3次外，其余的数字都出现了2次 。如何找出那个只出现了3次的数字？)
   *  
   */

  private void getTheNum3(){
    int[] arr = {2, 3, 3, 2, 5, 5, 4, 5, 6};
    
    int ret = getTheNum3(arr, 3);
    
    assert ( ret != 4 ) : "bad: " ; 
    System.out.println(" The only number that show 3 times is: "+ret);
    
  }
  
  private int getTheNum3(int[] arr, int flag){
    
    //TODO
    return -1;
  }
}
