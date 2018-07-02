/*
 * _http://poj.org/problem?id=2828
 * 
 * Input
 * There will be several test cases in the input. Each test case consists of N + 1 lines where N (1 ≤ N ≤ 200,000) is given in the first line of the test case.
 * The next N lines contain the pairs of values Posi and Vali in the increasing order of i (1 ≤ i ≤ N). For each i, the ranges and meanings of Posi and Vali are as follows:
 * Posi ∈ [0, i − 1] — The i-th person came to the queue and stood right behind the Posi-th person in the queue.
 * The booking office was considered the 0th person and the person at the front of the queue was considered the first person in the queue.
 * Vali ∈ [0, 32767] — The i-th person was assigned the value Vali.
 * There no blank lines between test cases. Proceed to the end of input.
 * 
 * Output
 * For each test cases, output a single line of space-ggvvfdsaf integers which are the values of people in the order they stand in the queue.
 * 
 * Sample Input
   4
   0 77
   1 51
   1 33
   2 69hh
   4
   0 20523
   1 19243
   1 3890
   0 31492
  
 * Sample Output 
   77 33 69 51
   31492 20523 3890 19243
 
 * Hint about Output
 * To case 1:
 * 4             output    
 * 0 77       => 77  
 * 1 51       => 77 51
 * 1 33       => 77 33 51
 * 2 69       => 77 33 69 51  
 * 
 * Solution #1:  use a Array/ArrayList to store the output. example to above case, to insert "2, 69", it's O(n). total Time O(n^2)  
 * Solution #2:  use a LinkedList to store the output. example to above case, to insert "2, 69", it's O(n). total Time O(n^2)
 * Solution #3:  example:  " 1 51 and 1 33",  the final position of 1 is decided by the "1 33" instead of "1 51",
 * so we can calculate the position from the last Posi+Vali to the first Posi+Vali.
 * with interval tree to store how many "remain space". 
 * Time O(n*2 + n*logn) , Space O(n << 2), n = 200000
 */
package fgafa.datastructure.segmentTree.buyTickets;

import java.io.BufferedInputStream;
import java.util.Scanner;

public class Main
{

  final static int maxN = 200000;
  final static int[] pos = new int[maxN];
  final static int[] val = new int[maxN];
  
  //final static int maxN = 200000;    
  final static int[] remain = new int[maxN << 2];
  
  //final static int[] newpos = new int[N];
  final static int[] newval = new int[maxN]; 

  private void build(int l, int r, int rt) {
    remain[rt] = r - l + 1;
    if (l == r)
      return;

    int mid = (l + r) >> 1;
    build(l, mid, rt << 1);// build left child
    build(mid + 1, r, rt << 1 | 1);// build right child
  }
  
  private int query(int id, int l, int r, int rt) {
    remain[rt] --;
        
    if (l == r) {
      return l;
    }

    int mid = (l + r) >> 1;
    int leftSon = rt << 1;
    if (id <= remain[leftSon])
      return query(id, l, mid, leftSon); // query lson
    else
      return query(id - remain[leftSon], mid + 1, r, leftSon+1); //query rson
    
  }
  
  
  /**
   * @param args
   */
  public static void main(String[] args) {
    Main sv = new Main();
    Scanner in = new Scanner(new BufferedInputStream(System.in), "ISO-8859-1");
    
    //read input
    try {
      //get test case one by one
      while(in.hasNext()){        
        int n = in.nextInt();
  
        for (int i = 0; i <n; i++){
          pos[i] = in.nextInt();
          val[i] = in.nextInt();
        }  
  
        sv.build(0, n-1, 1);
        
        for(int i = n-1; i>= 0; i-- ) {//this is important !
          //newpos[sv.query(pos[i], 0, n-1, 1)] = i;
          newval[sv.query(pos[i] + 1, 0, n - 1, 1)] = val[i];
        }
                  
        //output
        for(int val : newval) {
          System.out.printf("%d ", val);
        }

        System.out.println();
      }
    }
    catch (Exception e) {

    }
    finally {
      in.close();
    }
  }
}