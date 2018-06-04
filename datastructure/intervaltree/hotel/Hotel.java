/*
 * _http://poj.org/problem?id=3667
 * 
 * Description
The cows are journeying north to Thunder Bay in Canada to gain cultural enrichment and enjoy a vacation on the sunny shores of Lake Superior. Bessie, ever the competent travel agent, has named the Bullmoose Hotel on famed Cumberland Street as their vacation residence. This immense hotel has N (1 ≤ N ≤ 50,000) rooms all located on the same side of an extremely long hallway (all the better to see the lake, of course).
The cows and other visitors arrive in groups of size Di (1 ≤ Di ≤ N) and approach the front desk to check in. Each group i requests a set of Di contiguous rooms from Canmuu, the moose staffing the counter. He assigns them some set of consecutive room numbers r..r+Di-1 if they are available or, if no contiguous set of rooms is available, politely suggests alternate lodging. Canmuu always chooses the value of r to be the smallest possible.
Visitors also depart the hotel from groups of contiguous rooms. Checkout i has the parameters Xi and Di which specify the vacating of rooms Xi ..Xi +Di-1 (1 ≤ Xi ≤ N-Di+1). Some (or all) of those rooms might be empty before the checkout.
Your job is to assist Canmuu by processing M (1 ≤ M < 50,000) checkin/checkout requests. The hotel is initially unoccupied.

Input
* Line 1: Two space-separated integers: N and M
* Lines 2..M+1: Line i+1 contains request expressed as one of two possible formats: 
*   (a) Two space separated integers representing a check-in request: 1 and Di 
*   (b) Three space-separated integers representing a check-out: 2, Xi, and Di

Output
* Lines 1.....: For each check-in request, output a single line with a single integer r, the first room in the contiguous sequence of rooms to be occupied. 
* If the request cannot be satisfied, output 0.

Sample Input
10 6
1 3
1 3
1 3
1 3
2 5 5
1 6

Sample Output
1
4
7
0
5
 * Solution #1: define a interval tree to store 
 *   1) the max number of "empty contiguous room" in the interval
 *   2) the number of "empty contiguous room" in the end of interval
 *   3) the number of "empty contiguous room" in the start of interval
 *   4) the total number of "room" in the interval
 *   
 */
package fgafa.datastructure.intervaltree.hotel;

import java.io.BufferedInputStream;
import java.util.Scanner;

public class Hotel
{

  final static int maxN = 50000;  
  final static int[] max = new int[maxN << 2]; //the max number of "empty contiguous room" in the interval.
  final static int[] last = new int[maxN << 2]; //the number of "empty contiguous room" in the end of interval
  final static int[] first = new int[maxN << 2]; //the number of "empty contiguous room" in start of the interval
  final static int[] total = new int[maxN << 2]; //the total number of "room" in the interval
  
  final static boolean OCCUPY = true;
  final static boolean RELEASE = false;
  
  private void pushUp(int rt) {
    int leftSon = rt << 1, rightSon = leftSon + 1;
    max[rt] = Math.max(max[leftSon], max[rightSon]);
    max[rt] = Math.max(max[rt], last[leftSon] + first[rightSon]);
        
    first[rt] = (first[leftSon] == total[leftSon])? first[leftSon] + first[rightSon] : first[leftSon];
    last[rt] = (last[rightSon] == total[rightSon])? last[rightSon] + last[leftSon] : last[rightSon];
  }

  private void pushDown(boolean flag, int l, int r, int rt) {
    if(l == r)
      return;
    
    int leftSon = rt << 1, rightSon = leftSon + 1;    
    max[leftSon] = last[leftSon] = first[leftSon] = flag? 0 : total[leftSon];
    max[rightSon] = last[rightSon] = first[rightSon] = flag? 0 : total[rightSon];
    
    int mid = (l+r) >> 1;
    pushDown(flag, l, mid, leftSon);  //
    pushDown(flag, mid+1, r, rightSon);  //
  }
  
  private void build(int l, int r, int rt) {
    max[rt] = last[rt] = first[rt] = total[rt] = r - l + 1;
        
    if (l == r) 
      return;

    int m = (l + r) >> 1;
    int leftSon = rt << 1;
    build(l, m, leftSon);// build(lson);
    build(m + 1, r, leftSon + 1 );// build(rson); rt << 1 | 1
  }
  
  private int query(int width, int l, int r, int rt) {
    if (width > max[rt]) 
      return 0;
    
    int mid = (l + r) >> 1;
    int leftSon = rt << 1, rightSon = leftSon + 1;

    if (width < max[leftSon]) // query leftSon
      return query(width, l, mid, leftSon); 
    else if (width <= last[leftSon] + first[rightSon]){
      return mid - last[leftSon] + 1;
    }else  // query rightSon
      return query(width, mid + 1, r, rightSon); 
  }
  
  private void update(int L, int R, boolean flag, int l, int r, int rt) {
    //assert(L < l || R > r);

    if (L <= l && r <= R) {
      max[rt] = last[rt] = first[rt] = flag ? 0 : total[rt];

      pushDown(flag, l, r, rt);
      return;
    }
    
    int mid = (l + r) >> 1;
    int leftSon = rt << 1, rightSon = leftSon + 1;
    if (mid >= R )
      update(L, R, flag, l, mid, leftSon); // update lson
    else if(mid < L)
      update(L, R, flag, mid + 1, r, rightSon); // update rson
    else{  // mid is between L and R 
      update(L, R, flag, l, mid, leftSon); // update lson
      update(L, R, flag, mid + 1, r, rightSon); // update lson
    }       
      
    pushUp(rt);
  }
  
  /**
   * @param args
   */
  public static void main(String[] args) {
    Hotel sv = new Hotel();
    Scanner in = new Scanner(new BufferedInputStream(System.in), "ISO-8859-1");
    
    try {
      int n, m;
      int command;
      int queryNum, ret;
      int updateIndexStart, updateIndexEnd;
      while(in.hasNext()){
        
        n = in.nextInt();
        m = in.nextInt();
  
        sv.build(1, n, 1);
  
        while(m-- > 0){
          command = in.nextInt();
          
          if(command == 1){  // checkin
            queryNum = in.nextInt();
            ret = sv.query(queryNum,1, n, 1);
            System.out.println(ret);
            
            sv.update(ret, ret + queryNum - 1, OCCUPY, 1, n, 1);
          }else if(command == 2){ //check out
            updateIndexStart = in.nextInt();
            updateIndexEnd = updateIndexStart + in.nextInt() - 1;
            
            //check
            if(updateIndexEnd > n || updateIndexStart > updateIndexEnd || updateIndexStart < 1)
              continue;
            sv.update(updateIndexStart, updateIndexEnd, RELEASE, 1, n, 1);
          }
        }
      }
    }
    catch (Exception e) {

    }
    finally {
      in.close();
    }
  }
}