package sorting.median;

import java.util.Hashtable;

import util.Misc;

/**
 * There are 2 sorted array.  (same sorted way), find the median, ith. 
 * Note,  median and ith, it's based on the position instead of value.  
 * e.g.   
 *   median of [ 1, 2, 3, 4, 5, 5, 6, 6, 7, 7, 8, 9 ] is : 6 
 *   6th of [ 1, 2, 3, 4, 5, 5, 6, 6, 7, 7, 8, 9 ] is : 5
 * 
 * Say the two arrays are sorted and increasing, namely A and B. Assume the median of array A is m and the median of array B is n. Then，
 * 1、If m==n，then clearly the median after merging is also m，the algorithm holds.
 * 2、If m<=n，then reserve the half of sequence A in which all numbers are greater than m，also reserve the half of sequence B in which all numbers are smaller than n.
 *   Run the algorithm on the two new arrays。
 * 3、If m>n，then reserve the half of sequence A in which all numbers are smaller than m，also reserve the half of sequence B in which all numbers are larger than n.
 *   Run the algorithm on the two new arrays
 * 
 * *A1: 一个可以保证最坏运行时间为O(n)的算法，叫做 "Median of Medians algorithm" 
   *1.将这n个元素分为5个一组，找出每组里的中间数，形成新的n/5亿个中间值组成的集合 
   *2.对这n/5个值再分为5个一组，找出每组中间的数...重复这些步骤，只至找的到最后的中间值m 
   *3.以m为中值，将n个数分为L,R两组，L集合里的数都小于m,R集合里的数都大于m 
   * 如果m=n/2，则返回m 
   * 否则 如果L集合里的数多余一半，则从L集合中找出第n/2小的数 
   *     如果R集合里的数多余一半，R集合的元素个数为k, 则从R集合中找出第k-n/2小的数. 
   *4.如此重复迭代调用，直至找到中值. 
   *
 */

public class Median
{

  /**
   * @param args
   */
  public static void main(String[] args) {
    
    Median s = new Median();
    
    
    System.out.println("\n =====findMedianEqualLength =====");
    int a[][] = {{}, {5}, {5,8}, {5,8}, {3,5}, {3,5}, {3,8}, {1,2,3,6,8},  {1,3,5,7,9,11}, {1, 2, 2, 2, 2, 2, 3}} ;  
    int b[][] = {{}, {9}, {3,9}, {3,6}, {2,3}, {2,5}, {5,7}, {6,7,8,9,10}, {2,4,6,8,10,12}, {9, 12, 13, 14, 15, 17, 19}};  
    int start = 0;
    int length;
    for(int i=0; i< a.length; i++){
      length = a[i].length;
      System.out.println("median for "+Misc.array2String(a[i])+" and "+Misc.array2String(b[i])+" is : "+ s.findMedianEqualLength_R(a[i], b[i]));  
      System.out.println(length+"th of "+Misc.array2String(a[i])+" and "+Misc.array2String(b[i])+" is : "+ s.findkthRandomLength_R(a[i], b[i], length));
    }
  
    ///=====================
    System.out.println("\n =====findMedianRandomLength =====");
    
    int A[][]={{}, {1,2,3}, {1,3,5,7,8,9,10}, {1, 2, 3, 4, 5, 6, 7}, {1, 2, 2, 2, 2, 2, 3}, {1, 3}};  
    int B[][]={{}, {}, {2,4,6,10,11,12,13,14,17,19,20}, {5, 6, 7, 8, 9}, {9, 12, 13, 14, 15, 17, 19}, {9, 12, 13, 14, 15, 17, 19}};  
    start = 0;
    int alength, blength;   
    for(int i=0; i< A.length; i++){
      alength = A[i].length;
      blength = B[i].length;
      System.out.println("median of "+Misc.array2String(A[i])+" and "+Misc.array2String(B[i])+" is : "+ s.findMedianRandomLength_R(A[i], B[i])); 
      System.out.println("median of "+Misc.array2String(A[i])+" and "+Misc.array2String(B[i])+" is : "+ s.findMedianRandomLength_Iterate(A[i], B[i]));
      
    }
    
    System.out.println("\n =====findkthRandomLength =====");
    int t1[][]={{1,2,3}, {5,8}, {1,2,3,3,4,4,5,6,9}, {1, 3}};
    int t2[][]={{}, {3,9}, {2,2,3,4,5,7,8}, {9, 12, 13, 14, 15, 17, 19}};
    for(int i = 0; i< t1.length; i++){
      for(int j = 1; j<= (t1[i].length + t2[i].length); j++){
        System.out.println(j+"th of "+Misc.array2String(t1[i])+" and "+Misc.array2String(t2[i])+" is : "+ s.findkthRandomLength_R(t1[i], t2[i], j));
        System.out.println(j+"th of "+Misc.array2String(t1[i])+" and "+Misc.array2String(t2[i])+" is : "+ s.findkthRandomLength_Iterate(t1[i], t2[i], j));
        
      }
    
    }
    
  }


/*
 * There are 2 integer array on ascend order with the same length, find the member where is in median 
 * recurrence
 * 
 * Time complexity: O(log(2n))
 */
  public int findMedianEqualLength_R(int[] a, int[] b) {
    if (a == null || b == null || a.length == 0 || b.length ==0) return Integer.MIN_VALUE;
    
    return findMedianEqualLength_R(a, 0, b, 0, a.length);
  }
  private int findMedianEqualLength_R(int[] a, int aStart, int[] b, int bStart,
      int length) {
    if (length == 1)
      return min(a[aStart], b[bStart]);  // pick the bigger one as Median

    int i = (length - 1) / 2;
    int aMedian = aStart + i;
    int bMedian = bStart + i;
    if (a[aMedian] == b[bMedian])
      return a[aMedian];
    else if (a[aMedian] < b[bMedian])
      return findMedianEqualLength_R(a, aMedian+1, b, bStart, length - i -1);
    else
      return findMedianEqualLength_R(a, aStart, b, bMedian+1, length - i -1);
    
  }
  
  
  /*
   * There are 2 integer array on ascend order (maybe not the same length), find the member where is in median 
   * 
   * recurrence
   * 
   * Time complexity: O(log(n + m))
   *  
   * E.g. to {1, 2, 3, 4, 5, 6, 7} and {5, 6, 7, 8, 9}, the median is 6    
   * 
   */
  
  public int findMedianRandomLength_R( int[] a, int[] b){
    int alength = a.length;
    int blength = b.length;
    
    //make the latter is longer.  
//    if(alength == blength){
//      return findMedianEqualLength_R(a, b);
//    }else if(alength > blength){
//      return findMedianRandomLength_R(b, 0, blength, a, 0, alength);
//    }else{
//      return findMedianRandomLength_R(a, 0, alength, b, 0, blength);
//    }
      
    
    int k = (alength + blength)/2 + 1 ;
    return findkthRandomLength_R(a, b, k);
  }
  
  
  private int findMedianRandomLength_R( int[] a, int aStart, int alength, int[] b, int bStart, int blength)  
  {     
      int ma = alength/2;  
      int nb = blength/2;  
      int i  = ma;  
      ma += aStart;
      nb += bStart;
      
      //the previous length maybe 1, while the latter will be longer.
      
      if (alength == 1) {  
          if (blength%2==0){  
              if (a[0] >= b[nb])  
                  return b[nb];  
              else if (a[0]<=b[nb-1])  
                  return b[nb-1];  
              return a[0];  
          }  
          else  
              return b[nb];  
      } 
      
      if ( a[ma]==b[nb] )  
          return a[ma];  
      else if ( a[ma] < b[nb] )  
          return findMedianRandomLength_R(a, ma, alength-i, b, bStart, blength-i);  
      else   
          return findMedianRandomLength_R(a, aStart, alength-i, b, bStart + i, blength-i);  
  } 
  
  /*
   * There are 2 integer array on ascend order (maybe not the same length), find the member where is in median 
   * 
   * not recurrence
   * 
   * Time complexity: O((m+n) / 2)
   *  
   * It return median as 6 to {1, 2, 3, 4, 5, 6, 7} and {5, 6, 7, 8, 9};  
   * 
   */
  protected int findMedianRandomLength_Iterate(int[] a, int[] b) {  
    if (a == null || b == null ) return 0;  
    
    int lengthA = a.length;  
    int lengthB = b.length;  
      
    int k = (lengthA + lengthB)/2 + 1 ;

    return findkthRandomLength_Iterate(a, b, k);
  }
  
  private float max(float a, float b){
    return (a > b)? a : b ; 
  }
  private int max(int a, int b){
    return (a > b)? a : b ; 
  }
  private int min(int a, int b){
    return (a > b)? b : a ; 
  }

  /*
   * 
   *求两有序数组（长度不一定相等）合并后的第k个元素（不一定是中位数）。
   *
   *思路分析：
   *
   *假设两个有序数组是A[1...n]和B[1...n]，由于是寻找第k个元素，那么该元素只可能在A[1...k]与B[1...k]中，现在比较A[(k-1)/2]与B[(k-1)/2]   
   *(1)   A[(k-1)/2]==B[(k-1)/2]，那么A[i/2]（或者B[i/2])即要找的元素   
   *(2)   A[(k-1)/2]>B[(k-1)/2]，那么第i个元素在A[1...(k-1)/2]和B[(k+1)/2...i]中，递归的在上述两个数组中查找  
   *(3)   A[(k-1)/2]<B[(k-1)/2]，那么第i个元素在A[(k+1)/2...i]和B[1...(k-1)/2]中，递归的在上述两个数组中查找  
    
   *很显然，上述算法时间上为O(logm + logn)。
   *
   * 
   */
  public int findkthRandomLength_R(int[] a, int[] b, int k) {
    if (a == null || b == null ) return Integer.MIN_VALUE; 
    
    if(k > (a.length + b.length) || k < 1 )
      //throw new IllegalArgumentException("k can't be smaller than 1 and bigger than a.length + b.length. " + k);
      return Integer.MIN_VALUE;

      if(a.length == 0)
        return b[k -1];
      else if(b.length == 0)
        return a[k-1];
      else
        return findkthRandomLength_R0(a, 0, b, 0, k); 
  }
  
  /**
   * recursive,   O ( log2k )
   * @return
   */
  private int findkthRandomLength_R(int[] a, int aStart, int[] b, int bStart, int k) {

    if (aStart >= a.length)
      return max(a[a.length - 1], b[bStart + k - 1]);
    if (bStart >= b.length)
      return max(a[aStart + k - 1], b[b.length -1]);

    if (k == 1){
      if(a[aStart] < b[bStart]){
        if(bStart > 0)
          return max(a[aStart], b[bStart-1]);
        else
          return a[aStart];
      }
      else{
        if(aStart > 0)
          return max(a[aStart-1], b[bStart]);
        else
          return b[aStart];
      }
        
    }
    else if(k == 2){
      if(a[aStart] < b[bStart]){
        if(aStart + 1 < a.length) 
          return min(a[aStart + 1], b[bStart]);
        else 
          return b[bStart];
      }
      else{
        if(bStart + 1 < b.length) 
          return min(a[aStart], b[bStart+1]);
        else 
          return a[aStart];        
      }
    }

    int m = (k - 1) / 2;
    int ai = min(aStart + m, a.length -1); // next 
    int bi = min(bStart + m, b.length -1); // next

    if(a[ai] == b[bi]){
      if(ai == a.length -1 && (a.length - aStart) < k )
        return b[aStart + bStart + k - a.length];   //( k - (a.length - aStart) + bStart - 1 
      else if( bi == b.length -1 && (b.length - bStart) < k)
        return a[aStart + bStart + k - b.length];
      else
        return a[ai];  
    }
    else if (a[ai] < b[bi])
      return findkthRandomLength_R(a, ai + 1, b, bStart, k + aStart - ai -1);  // k -(ai - aStart), it's (k-1-m) when (ai==aStart+m ) 
    else
      return findkthRandomLength_R(a, aStart, b, bi + 1, k + bStart - bi -1);

  }
  

  //one by one
  private int findkthRandomLength_R2(int[] a, int aStart, int aEnd, int[] b, int bStart, int bEnd, int k) {
    
    int m = (k - 1) / 2; 
    int ai = min(aStart + m, aEnd); // next 
    int bi = min(bStart + m, bEnd); // next
    
    int aM0 = (ai == aStart)? Integer.MIN_VALUE : a[ai - 1];
    int aM1 = (ai == aEnd)? Integer.MAX_VALUE : a[ai];
    int bM0 = (bi == bStart)? Integer.MIN_VALUE : b[bi - 1];
    int bM1 = (bi == bEnd)? Integer.MAX_VALUE : b[bi];
    
    if(bM0 < aM1 && aM1 < bM1)
      return aM1;
    else if(aM0< bM1 && bM1 < aM1 )
      return bM1;
    
    if(aM1 < bM1)
      return findkthRandomLength_R2(a, ai+1, aEnd, b, bStart, bi, k - (ai + 1 - aStart));
    else
      return findkthRandomLength_R2(a, aStart, ai, b, bi+1, bEnd, k - (bi + 1 - bStart));
    
  }
  
  private int findKthSmallest(int A[], int aStart, int aEnd, int B[], int bStart, int bEnd, int k) {
    assert(aStart >=0 && aEnd >= aStart); assert(bStart>=0 && bEnd >= bStart); assert(k > 0); assert(k <= (aEnd - aStart) + (bEnd - bStart));
     
    int i = (int)((double)(aEnd - aStart) / (aEnd - aStart + bEnd - bStart) * (k-1)) + aStart;
    int j = (k-1) - i + bStart;
   
    assert(i >= 0); assert(j >= 0); assert(i <= aEnd - aStart); assert(j <= bEnd - bStart);
    // invariant: i + j = k-1
    // Note: A[-1] = -INF and A[m] = +INF to maintain invariant
    int Ai_1 = ((i == aStart) ? Integer.MIN_VALUE : A[i-1]);
    int Bj_1 = ((j == bStart) ? Integer.MIN_VALUE : B[j-1]);
    int Ai   = ((i == aEnd) ? Integer.MAX_VALUE : A[i]);
    int Bj   = ((j == bEnd) ? Integer.MAX_VALUE : B[j]);
   
    if (Bj_1 < Ai && Ai < Bj)
      return Ai;
    else if (Ai_1 < Bj && Bj < Ai)
      return Bj;
   
    assert((Ai > Bj && Ai_1 > Bj) || 
           (Ai < Bj && Ai < Bj_1));
   
    // if none of the cases above, then it is either:
    if (Ai < Bj)
      // exclude Ai and below portion
      // exclude Bj and above portion
      return findKthSmallest(A, i+1, aEnd, B, bStart, j, k-i-1);
    else /* Bj < Ai */
      // exclude Ai and above portion
      // exclude Bj and below portion
      return findKthSmallest(A, aStart, i, B, j+1, bEnd, k-j-1);
  }
  /**
   * recursive,   O (k)
   * @return
   */
  private int findkthRandomLength_R0(int[] a, int aStart, int[] b, int bStart, int k) {

    if (aStart == a.length)
      return b[bStart + k - 1];
    if (bStart == b.length)
      return a[aStart + k - 1];

    if (k == 1)
      return min(a[aStart], b[bStart]);

    if (a[aStart] < b[bStart])
      return findkthRandomLength_R(a, aStart + 1, b, bStart, k - 1);
    else
      return findkthRandomLength_R(a, aStart, b, bStart + 1, k - 1);

  }
  
  /**
   * Iterate,   O (k)
   * @return
   */
  public int findkthRandomLength_Iterate(int[] a, int[] b, int k) {
    int aLength = a.length; 
    int bLength = b.length;
    
    if(k > aLength + bLength || k < 1 )
      //throw new IllegalArgumentException("k can't be smaller than 1 and bigger than a.length + b.length. " + k);
      return Integer.MIN_VALUE;
      
    int ai = 0, bj = 0;
    
    while (true) {

      while (ai < aLength && (bj == bLength || a[ai] <= b[bj])) {
        ai++;
        if (ai + bj == k)
          return a[ai - 1];
      }

      while (bj < bLength && (ai == aLength || a[ai] >= b[bj])) {
        bj++;
        if (ai + bj == k)
          return b[bj - 1];
      }

    }
    
  }
  
  

  
  

}
