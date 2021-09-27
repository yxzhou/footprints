package datastructure.intervaltree;

import java.io.BufferedInputStream;
import java.util.Scanner;

public class Template
{

  final static int N = 5000;
  final static int[] input = new int[N];
  final static int[] inversionNum = new int[N];
  
  final static int maxN = 5555;  // maxN = 2^((int)log(2,n) + 1) * 2 - 1,  maxN < n * 4  
  final static int[] sum = new int[maxN << 2];
  
  public int calMinInversionNum(int length){
    //check
    if(length < 2)
      return -1;
    
    calInversionNum(length);  //get inversionNum[] from input[]
    
    int minIN = 0, currIN = 0;
    for(int i =0; i<length; i++)
      currIN += inversionNum[i];
    
    minIN = currIN;
    for(int i=0; i<length - 1; i++){
      currIN = currIN - (inversionNum[i] << 1) + length - 1 ; //s[0] - B[0] + (n-1 - B[0])
      minIN = Math.min(minIN, currIN);
    }
      
    return minIN;
  }
  private void calInversionNum(int length){
    assert(length > 0);
    
    build(0, length - 1, 1);
    for(int i = 0; i< length; i++){
      inversionNum[i] = query(input[i], length - 1, 0, length - 1, 1);
      update(input[i], 0, length - 1, 1);          
    }
    
    
  }
  
  private void pushUp(int rt) {
    sum[rt] = Math.max(sum[rt << 1], sum[rt << 1 | 1]);
  }

  private void build(int l, int r, int rt) {
    sum[rt] = 0;
    if (l == r) 
      return;

    int m = (l + r) >> 1;
    build(l, m, rt << 1);// build(lson);
    build(m + 1, r, rt << 1 | 1);// build(rson);
  }
  
  private int query(int L, int R, int l, int r, int rt) {
    if (L <= l && r <= R) {
      return sum[rt];
    }

    int m = (l + r) >> 1;
    int ret = 0;
    if (L <= m)
      ret += query(L, R, l, m, rt << 1); // checkIn lson
    if (R > m)
      ret += query(L, R, m + 1, r, rt << 1 | 1); //checkIn rson

    return ret;
  }
  
  private void update(int p, int l, int r, int rt) {
    if (l == r) {
      sum[rt] ++;
      return;
    }

    int m = (l + r) >> 1;
    if (p <= m)
      update(p, l, m, rt << 1); // update lson
    else
      update(p, m + 1, r, rt << 1 | 1); // update rson

    pushUp(rt);
  }
  
  /**
   * @param args
   */
  public static void main(String[] args) {
    Template sv = new Template();
    Scanner in = new Scanner(new BufferedInputStream(System.in), "UTF-8");
    
    //read input
    try {
      //get test case one by one
      while(in.hasNext()){
        
        int n = in.nextInt();
  
        for (int i = 1; i <= n; i++)
          input[i] = in.nextInt();
  
        //output
        System.out.printf("%d\n", sv.calMinInversionNum(n));
      }
    }
    catch (Exception e) {

    }
    finally {
      in.close();
    }


  }

}
