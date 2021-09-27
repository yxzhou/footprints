/**
 * _http://acm.hdu.edu.cn/showproblem.php?pid=1394
 * Problem Description
 * The inversion number of a given number sequence a1, a2, ..., an is the number of pairs (ai, aj) that satisfy i < j and ai > aj.
 * 
 * For a given sequence of numbers a1, a2, ..., an, if we move the first m >= 0 numbers to the end of the sequence, we will obtain another sequence. There are totally n such sequences as the following:
 * 
 * a1, a2, ..., an-1, an (where m = 0 - the initial seqence)
 * a2, a3, ..., an, a1 (where m = 1)
 * a3, a4, ..., an, a1, a2 (where m = 2)
 * ...
 * an, a1, a2, ..., an-1 (where m = n-1)
 * 
 * You are asked to write a program to find the minimum inversion number out of the above sequences.
 * 
 * Input
 * The input consists of a number of test cases. Each case consists of two lines: the first line contains a positive integer n (n <= 5000); the next line contains a permutation of the n integers from 0 to n-1.
 * 
 * Output
 * For each case, output the minimum inversion number on a single line.
 * 
 * Sample Input
   10
   1 3 6 9 0 8 5 7 4 2
   1
   0
   2
   1 2
   2
   2 1
   3
   0 1 2
   3
   1 0 2
    
 * 
 * Sample Output
   16
   0
   0
   0
   0
   1
 * 
 * Solution: define B[i] as the inversion number of input[i], how many input[i] > input[j], ( i < j < n ) 
 * step1: get all B[i] for input[1..n], sum them as the inversion number on a single line 
 * (Note, brute force, Time O(n^2); interval Tree, Time O(nlogn))
 * step2: sum them as the inversion number on a single line,  s[0] = sum(B[1..n]), minS = s[0]
 * step3: get the inversion number on the next single line,   s[1] = s[0] - B[0] + (n -1 - B[0]), minS = min(minS, s[1])
 *        adjust the inversion number 
 * step4: loop step3
 */
package datastructure.segmentTree.inversions;

import java.io.BufferedInputStream;
import java.util.Scanner;

public class Main
{
  final static int maxN = 5001;
  final static int[] input = new int[maxN];
  final static int[] inversionNum = new int[maxN];
  
  public int calMinInversionNum(int length){
    //check
    if(length < 3)
      return 0;
    
    //init
    calInversionNum2(length);  //get inversionNum[] from input[]
    
    int minIN = 0, currIN = 0;
    for(int i=0; i<length; i++)
      currIN += inversionNum[i];
    
    minIN = currIN;
    for(int i=0; i<length - 1; i++){
      currIN = currIN - (inversionNum[i] << 1) + length - 1 ; //s[0] - B[0] + (n-1 - B[0])
      minIN = Math.min(minIN, currIN);
      
      for(int j = i + 1; j < length; j++)
        if(input[j] > input[i])
          inversionNum[j] ++; 
    }
      
    return minIN;
  }
  
  /* brute force, get all the inversion number on a single line  
   * Time O(n^2) */
  private void calInversionNum(int length){
    assert(length > 0);
    
    for(int i= length - 1; i>=0; i--){
      inversionNum[i] = 0; //all default it's 0  
      for(int j = i+1; j < length; j++)
        if(input[i] > input[j])
          inversionNum[i] ++;     
    }  
  }
  
  /* interval tree, get all the inversion number on a single line
   * Note: the single line is a permutation of the n integers from 0 to n-1  
   * Time O(nlogn) */
  //final static int maxN = 5555;  // maxN = 2^((int)log(2,n) + 1) * 2 - 1,  maxN < n * 4  
  final static int[] count = new int[maxN << 2];
  
  private void calInversionNum2(int length){
    assert(length > 0);
    
    length --;
    build(0, length, 1);
    inversionNum[length] = 0;
    for(int i = length; i>0; ){
      update(input[i], 0, length, 1);
      i--;
      inversionNum[i] = query(0, input[i], 0, length, 1);                
    }
    
  }
  
//  private void pushUp(int rt) {
//    count[rt] = Math.max(count[rt << 1], count[rt << 1 | 1]);
//  }

  private void build(int l, int r, int rt) {
    count[rt] = 0;
    if (l == r) 
      return;

    int mid = (l + r) >> 1;
    int leftSon = rt << 1;
    build(l, mid, leftSon);// build(lson);
    build(mid + 1, r, leftSon + 1);// build(rson);
  }
  
  private int query(int L, int R, int l, int r, int rt) {
    if (L <= l && r <= R) 
      return count[rt];
        
    int mid = (l + r) >> 1;
    int leftSon = rt << 1;
    if (R <= mid)
      return query(L, R, l, mid, leftSon); // checkIn lson
    else if (L > mid)
      return query(L, R, mid + 1, r, leftSon + 1); //checkIn rson
    else    
      return query(L, R, l, mid, leftSon) + query(L, R, mid+1, r, leftSon + 1);
  }
  
  private void update(int p, int l, int r, int rt) {
    count[rt] ++;
    
    if (l == r) 
      return;

    int mid = (l + r) >> 1;
    int leftSon = rt << 1;
    if (p <= mid)
      update(p, l, mid, leftSon); // update lson
    else
      update(p, mid + 1, r, leftSon + 1); // update rson
    
    //pushUp(rt);
  }
  
  /**
   * @param args
   */
  public static void main(String[] args) {
    Main sv = new Main();
    Scanner in = new Scanner(new BufferedInputStream(System.in), "ISO-8859-1");
    
    try {
      //get test case one by one
      while(in.hasNext()){
        
        int n = in.nextInt();
  
        for (int i = 0; i < n; i++)
          input[i] = in.nextInt();
  
        System.out.println(sv.calMinInversionNum(n));
      }
    }
    catch (Exception e) {

    }
    finally {
      in.close();
    }


  }

}
