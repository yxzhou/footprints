/*_http://acm.hdu.edu.cn/showproblem.php?pid=1698
 * 
 * Now Pudge wants to do some operations on the hook.

Let us number the consecutive metallic sticks of the hook from 1 to N. For each operation, Pudge can change the consecutive metallic sticks, numbered from X to Y, into cupreous sticks, silver sticks or golden sticks.
The total value of the hook is calculated as the sum of values of N metallic sticks. More precisely, the value for each kind of stick is calculated as follows:

For each cupreous stick, the value is 1.
For each silver stick, the value is 2.
For each golden stick, the value is 3.

Pudge wants to know the total value of the hook after performing the operations.
You may consider the original hook is made up of cupreous sticks.
 

Input
The input consists of several test cases. The first line of the input is the number of the cases. There are no more than 10 cases.
For each case, the first line contains an integer N, 1<=N<=100,000, which is the number of the sticks of Pudge’s meat hook and the second line contains an integer Q, 0<=Q<=100,000, which is the number of the operations.
Next Q lines, each line contains three integers X, Y, 1<=X<=Y<=N, Z, 1<=Z<=3, which defines an operation: change the sticks numbered from X to Y into the metal kind Z, where Z=1 represents the cupreous kind, Z=2 represents the silver kind and Z=3 represents the golden kind.
 

Output
For each case, print a number in a line representing the total value of the hook after the operations. Use the format in the example.
 

Sample Input
4
10
2
1 5 2
5 9 3
100 
2
10 15 2
15 19 3
100 
2
10 15 2
16 19 3
100 
2
10 14 2
16 19 3

Sample Output
Case 1: The total value of the hook is 24.
Case 2: The total value of the hook is 115.
Case 3: The total value of the hook is 114.
Case 4: The total value of the hook is 113.

 * Solution #1: define a interval tree to store 
 *   1) the id that occupy the whole interval. 0:default, 1:1 cupreous, 2:silver, 3:golden.
 *   2) is it occupied by a id partially. false:default
 * AND: the latter will "replace" the previous, so to start from the latter will be better.
 * 
 * if there are n intervals,   
 *   interval tree initial, Time O(n)
 *   interval tree update, Time O(n * logn)
 *   count the total value of the hook,  Time O(n)
 * so it's O(nlogn)  
 *  
 * Solution #2, define a interval tree to store 
 *   
 * AND: check as the order of request.   
 */
package datastructure.segmentTree.justAHook;

import java.io.BufferedInputStream;
import java.util.Scanner;

public class Main
{
  final static int maxN = 100001;   //
  final static int[] color = new int[maxN << 2]; //the poster id in the whole of interval, default it's 0, means no poster
  
  final static int DEFALT = 0;
  final static int CUPREOUS = 1;
  //final static int SILVER = 2;
  //final static int GOLDEN = 3;
  
  private int cal(int l, int r, int rt) {
    if(color[rt] > DEFALT )
      return (color[rt] - DEFALT) * (r-l+1); 
      
    if (l == r) 
      return 0;

    int ret = 0;  
    int m = (l + r) >> 1;
    int leftSon = rt << 1;
    ret += cal(l, m, leftSon);// build(lson);
    ret += cal(m + 1, r, leftSon + 1);// build(rson); rt << 1 | 1
    
    return ret;
  }  
   
  private void pushUp(int rt) {
    int leftSon = rt << 1;
    color[rt] = (color[leftSon] != color[leftSon+1])? DEFALT : color[leftSon];   
  }
  
  private void pushDown(int rt) {
    if(color[rt] != DEFALT){
      int leftSon = rt << 1;
      color[leftSon] = color[rt];     //left Son
      color[leftSon + 1] = color[rt];  //right Son
    }
  }
  
  private void build(int l, int r, int rt) {
    color[rt] = DEFALT;  //re-initial  
    
    if (l == r) 
      return;

    int m = (l + r) >> 1;
    int leftSon = rt << 1;
    build(l, m, leftSon);// build(lson);
    build(m + 1, r, leftSon + 1 );// build(rson); rt << 1 | 1
  }
  
  private void update(int L, int R, int typeId, int l, int r, int rt) {
    if (L <= l && r <= R) {
      color[rt] = typeId;      
      return;
    }

    pushDown(rt);
    
    int mid = (l + r) >> 1;
    int leftSon = rt << 1;
//    if (mid >= R )
//      update(L, R, typeId, l, mid, leftSon); // update lson
//    else if(mid < L)
//      update(L, R, typeId, mid + 1, r, leftSon + 1); // update rson
//    else{  // mid is between L and R 
//      update(L, R, typeId, l, mid, leftSon); // update lson
//      update(L, R, typeId, mid + 1, r, leftSon + 1); // update rson
//    }       
    if (L <= mid)
      update(L, R, typeId, l, mid, leftSon); // update lson
    if (R > mid)
      update(L, R, typeId, mid + 1, r, leftSon + 1); // update rson
    
    pushUp(rt);
  }

  
  public static void main(String[] args) {
    Scanner in = new Scanner(new BufferedInputStream(System.in), "ISO-8859-1");
    Main sv = new Main();
    
    try {
      int n, m, q;
      int inputStart, inputEnd, inputType;
      while(in.hasNext()){
        
        n = in.nextInt(); // how many test case
        
        for(int caseCount = 1; caseCount <= n;  caseCount++ ){ 
          m = in.nextInt();
          q = in.nextInt();
          
          sv.build(1, m, 1);
          
          for(int i=1; i<=q; i++){
            inputStart = in.nextInt();
            inputEnd = in.nextInt();  
            inputType = in.nextInt();
            
            sv.update(inputStart, inputEnd, inputType, 1, m, 1);
          }
          
          System.out.printf("Case %d: The total value of the hook is %d.\n", caseCount, m + sv.cal(1, m, 1));
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
