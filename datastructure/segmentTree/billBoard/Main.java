/*
 * _http://acm.hdu.edu.cn/showproblem.php?pid=2795
 *Problem Description
 *At the entrance to the university, there is a huge rectangular billboard of size h*w (h is its height and w is its width).
 * The board is the place where all possible announcements are posted: nearest programming competitions, changes in the dining room menu, and other important information.
 *On September 1, the billboard was empty. One by one, the announcements started being put on the billboard.
 *Each announcement is a stripe of paper of unit height. More specifically, the i-th announcement is a rectangle of size 1 * wi.
 *When someone puts a new announcement on the billboard, she would always choose the topmost possible position for the announcement.
 * Among all possible topmost positions she would always choose the leftmost one.
 *If there is no valid location for a new announcement, it is not put on the billboard (that's why some programming contests have no participants from this university).
 *Given the sizes of the billboard and the announcements, your task is to find the numbers of rows in which the announcements are placed.
 *
 *Input
 *There are multiple cases (no more than 40 cases).
 *The first line of the input file contains three integer numbers, h, w, and n (1 <= h,w <= 10^9; 1 <= n <= 200,000) - the dimensions of the billboard and the number of announcements.
 *Each of the next n lines contains an integer number wi (1 <= wi <= 10^9) - the width of i-th announcement.
 *
 *Output
 *For each announcement (in the order they are given in the input file) output one number - the number of the row in which this announcement is placed.
 * Rows are numbered from 1 to h, starting with the top row. If an announcement can't be put on the billboard, output "-1" for this announcement.
 *
 *Sample Input
3 5 5
2
4
3
3
3
 
 *Sample Output
1
2
1
3
-1
 * 
 * Solution #1: define a width[h] to every row. To every input w, if w < width[i], update width-=w and return i. Time O(n*h), ( 2*10^5 * 10^9  ) 
 * Solution #2: define a interval tree to store the width[h], so Time ( n * log h).    ( 2*10^5 * log(10^9) = 2 * 10^5 * 30 )    
 * 
 */
package datastructure.segmentTree.billBoard;

import java.io.BufferedInputStream;
import java.util.Scanner;

public class Main
{
  public static void main(String[] args) {
    Main sv = new Main();
    Scanner in = new Scanner(new BufferedInputStream(System.in), "ISO-8859-1");
    
    //read input
    try {
      //get test case one by one
      int h, w, n, width;
      while(in.hasNext()){
        h = in.nextInt();
        w = in.nextInt();
        n = in.nextInt();
        
        if(h > n)
          h = n;
        sv.build(w, 1, h, 1);
        
        while ( n-- > 0){
          width = in.nextInt();
          System.out.println(sv.query(width, 1, h, 1));
        }  
      }
    }
    catch (Exception e) {

    }
    finally {
      in.close();
    }
  }

  //final static long maxRow = (long)1E9 << 2;  // 10^9 
  final static int maxN = 200001;
  final static int[] max = new int[maxN << 2];//the max width

  private void pushUp(int rt) {
    int newMax = max[rt];
    if((rt & 1) == 1)
      newMax = Math.max(newMax, max[rt - 1]); 
    else 
      newMax = Math.max(newMax, max[rt + 1]);

    rt >>= 1;
    if(rt > 0 && max[rt] > newMax){
       max[rt] = newMax;
       pushUp(rt);
    }  
  }
  
  private void build(int w, int l, int r, int rt) {
    max[rt] = w;
    if (l == r) 
      return;

    int mid = (l + r) >> 1;
    rt <<= 1;
    build(w, l, mid, rt);// build(lson);
    build(w, mid + 1, r, rt + 1);// build(rson);
  }
  
  private int query(int width, int l, int r, int rt) {
    if(width > max[rt])
      return -1;
    
    if (l == r) {
        max[rt] -= width; 
        pushUp(rt);
        return l;
    }

    int mid = (l + r) >> 1;
    int leftSon = rt << 1;
    if (width <= max[leftSon])
      return query(width, l, mid, leftSon); // checkIn lson
    else //if(width <= max[leftSon + 1])
      return query(width, mid + 1, r, leftSon + 1); //checkIn rson
  }
}
