/*
 * _http://acm.hdu.edu.cn/showproblem.php?pid=1542
 * Problem Description
There are several ancient Greek texts that contain descriptions of the fabled island Atlantis. Some of these texts even include maps of parts of the island. But unfortunately, these maps describe different regions of Atlantis. Your friend Bill has to know the total area for which maps exist. You (unwisely) volunteered to write a program that calculates this quantity.

*Input:
The input file consists of several test cases. Each test case starts with a line containing a single integer n (1<=n<=100) of available maps. The n following lines describe one map each. Each of these lines contains four numbers x1;y1;x2;y2 (0<=x1<x2<=100000;0<=y1<y2<=100000), not necessarily integers. The values (x1; y1) and (x2;y2) are the coordinates of the top-left resp. bottom-right corner of the mapped area.
The input file is terminated by a line containing a single 0. Don’t process it.

*Output
For each test case, your program should output one section. The first line of each section must be “Test case #k”, where k is the number of the test case (starting with 1). The second one must be “Total explored area: a”, where a is the total explored area (i.e. the area of the union of all rectangles in this test case), printed exact to two digits to the right of the decimal point.
Output a blank line after each test case.

*Sample Input
2
10 10 20 20
15 15 25 25.5
0
 
*Sample Output
Test case #1
Total explored area: 180.00 
 * 
 * Solution #1: convert 2D to 1D,  thinking about a horizontal line from y0 to yn, AREA = x0*(y1-y0) + ---
 *  
 * 
 */
package fgafa.datastructure.segmentTree.atlantis;

import java.io.BufferedInputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;

public class Main
{
  class Rect implements Comparable<Rect>{
    double leftTopX, leftTopY, rightBottomX, rightBottomY;
    
    Rect(double leftTopX, double leftTopY, double rightBottomX, double rightBottomY){
      this.leftTopX = leftTopX; 
      this.leftTopY = leftTopY;  
      this.rightBottomX = rightBottomX; 
      this.rightBottomY = rightBottomY;
    }

    @Override
    public int compareTo(Rect rect2) {
      if(this.leftTopX > rect2.leftTopX)
        return 1;
      else if(this.leftTopX < rect2.leftTopX)
        return -1;

      if(this.rightBottomX > rect2.rightBottomX)
        return 1;
      else if(this.rightBottomX < rect2.rightBottomX)
        return -1; 
      
      return 0;
    }
  }
  
  final static int maxN = 100;
  final static double[] width = new double[maxN << 3];  
  final static double[] x = new double[maxN << 3];
  
  final static Rect[] rect = new Rect[maxN];
  final static double[] inputY = new double[maxN << 1];
  
  final static int DEFALT = 0;
  final static int SPECIAL = -1;
  
  private double calArea(int l, int r, int rt) {    
    if(width[rt] > DEFALT)
      return width[rt] * (inputY[r] - inputY[l]);

    if(l == r-1)
      return 0;
      
    double ret = 0;
    int mid = (l + r) >> 1;
    int leftSon = rt << 1;
    ret += calArea(l, mid, leftSon);// lson
    ret += calArea(mid + 1, r, leftSon + 1);// rson; rt << 1 | 1
    
    return ret;
  }  
   
  private void pushUp(int rt) {
    int leftSon = rt << 1;
    if(width[leftSon] == width[leftSon + 1] && x[leftSon] == x[leftSon + 1]){
      width[rt] = width[leftSon];
      x[rt] = x[leftSon];
    }else{
      width[rt] = SPECIAL;
      x[rt] = DEFALT;
    }
        
  }
  
  private void pushDown(int rt) {
    if(width[rt] > DEFALT ){
      int leftSon = rt << 1;
      width[leftSon] = width[rt];
      x[leftSon] = x[rt]; 
      width[++leftSon] = width[rt];
      x[leftSon] = x[rt];
    }
  }
  
  
  private void build(int l, int r, int rt) {
    width[rt] = DEFALT;  //re-initial  
    x[rt] = DEFALT;  //re-initial
    
    if (l == r - 1) 
      return;

    int m = (l + r) >> 1;
    int leftSon = rt << 1;
    build(l, m, leftSon);// build(lson);
    build(m + 1, r, leftSon + 1 );// build(rson); rt << 1 | 1
  }
  
  
  /*
   * 
   */
  private void update(int L, int R, double leftX, double rightX, int l, int r, int rt) {   
    if (L <= l && r <= R && width[rt] != SPECIAL) {
      if(x[rt] >= rightX)
        return;      

      width[rt] += rightX - Math.max(leftX, x[rt]);
      x[rt] = rightX; 
        
      return;
    }
    
    if(l == r - 1)
      return;
    
    pushDown(rt);
    
    int mid = (l + r) >> 1;
    int leftSon = rt << 1;
//    if (mid >= R )
//      update(L, R, leftX, rightX, l, mid, leftSon); // update lson
//    else if(mid < L)
//      update(L, R, leftX, rightX, mid + 1, r, leftSon + 1); // update rson
//    else{  // mid is between L and R 
//      update(L, R, leftX, rightX, l, mid, leftSon); // update lson
//      update(L, R, leftX, rightX, mid + 1, r, leftSon + 1); // update lson
//    }       
    if (mid >= L)
      update(L, R, leftX, rightX, l, mid, leftSon); // update lson
    if (mid < R)
      update(L, R, leftX, rightX, mid + 1, r, leftSon + 1); // update rson
      
    pushUp(rt);
  }
  
  public static void main(String[] args) {
    Main sv = new Main();
    Scanner in = new Scanner(new BufferedInputStream(System.in), "ISO-8859-1");
    
    try {
      int caseCount = 1;
      while(in.hasNext()){        
        int h = in.nextInt();
        //exit when it's 0
        if(0 == h)
          break;
                
        for (int i = 0, j=0; i < h; i++){
          rect[i] = sv.new Rect(in.nextDouble(), in.nextDouble(), in.nextDouble(), in.nextDouble());
          
          inputY[j++] = rect[i].leftTopY;
          inputY[j++] = rect[i].rightBottomY;
        }  
        Arrays.sort(inputY, 0, h << 1);
        HashMap<Double, Integer> set1 = new HashMap<Double, Integer>();
        int index = 0;
        set1.put(inputY[0], index++);
        for(int i=1; i< (h<<1); i++)
          if(inputY[i-1] != inputY[i])
            set1.put(inputY[i], index++);
        
        index--;
        sv.build(0, index, 1);
        
        Arrays.sort(rect, 0, h);  
        for(int i=0; i<h; i++)  
          sv.update(set1.get(rect[i].leftTopY), set1.get(rect[i].rightBottomY), rect[i].leftTopX,  rect[i].rightBottomX, 0, index, 1);
                  
        //output       
        System.out.format("Test case #%d%n", caseCount++);
        System.out.format("Total explored area:%.2f%n", sv.calArea(0, index, 1));
      }
    }
    catch (Exception e) {
      e.printStackTrace();
    }
    finally {
      in.close();
    }

  }

}
