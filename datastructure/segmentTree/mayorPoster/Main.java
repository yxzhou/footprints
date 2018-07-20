/**
 * _http://poj.org/problem?id=2528
 * 
 * Description

The citizens of Bytetown, AB, could not stand that the candidates in the mayoral election campaign have been placing their electoral posters at all places at their whim.
 The city council has finally decided to build an electoral wall for placing the posters and introduce the following rules:
Every candidate can place exactly one poster on the wall. 
All posters are of the same height equal to the height of the wall; the width of a poster can be any integer number of bytes (byte is the unit of length in Bytetown). 
The wall is divided into segments and the width of each segment is one byte. 
Each poster must completely cover a contiguous number of wall segments.

They have built a wall 10000000 bytes long (such that there is enough place for all candidates). When the electoral campaign was restarted,
 the candidates were placing their posters on the wall and their posters differed widely in width.
 Moreover, the candidates started placing their posters on wall segments already occupied by other posters.
 Everyone in Bytetown was curious whose posters will be visible (entirely or in part) on the last day before elections.
Your task is to find the number of visible posters when all the posters are placed given the information about posters' size, their place and order of placement on the electoral wall. 
 Input
The first line of input contains a number c giving the number of cases that follow. The first line of data for a single case contains number 1 <= n <= 10000.
 The subsequent n lines describe the posters in the order in which they were placed. The i-th line among the n lines contains two integer numbers li and ri which are the number of the wall segment occupied by the left end and the right end of the i-th poster, respectively. We know that for each 1 <= i <= n, 1 <= li <= ri <= 10000000. After the i-th poster is placed, it entirely covers all wall segments numbered li, li+1 ,... , ri.

 Output
For each input data set print the number of visible posters after all the posters are placed.

 * Sample Input
4
5
1 4
2 6
8 10
3 4
7 10
3
1 10
1 4
5 10
3
1 10
1 4
6 10
3
1 10
1 5
5 10

Sample Output
4
3
3
2

 * Solution #1: define a interval tree to store 
 *   1) the id that occupy the whole interval, 0 if not, default it's 0.
 *   2) is it occupied by a id partially
 * note 1: the latter will "replace" the previous, so to start from the latter will be better.
 * note 2: to save space, we can "zip" the poster's index (li, ri), example to input (3, 10) and (1000, 9000), we can zip it to (1, 2) and (3, 4)
 * if there are n intervals,   
 *   sort and zip,  Time O(n*logn)
 *   interval tree initial, Time O(n)
 *   interval tree update, Time O(n * logn)
 *   count the distinct poster,  Time O(n)
 * so it's O(nlogn)
 */
package fgafa.datastructure.segmentTree.mayorPoster;

import java.io.BufferedInputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;

public class Main
{

  final static int maxN = 10001;   //(int) 1E7  
  final static int[] idInAll = new int[maxN << 3]; //the poster id in the whole of interval, default it's 0, means no poster
  final static boolean[] idInPartial = new boolean[maxN << 3]; //default it's false. 
  
  final static int[] inputStart = new int[maxN];
  final static int[] inputEnd = new int[maxN];
  final static int[] inputAll = new int[maxN << 1];  
  
  final static int DEFALT_ID = 0;
  
  private void count(int l, int r, int rt, HashSet<Integer> set) {
      
    if(idInAll[rt] != DEFALT_ID){  //if(!idInPartial[rt] && idInAll[rt] != DEFALT_ID)
      set.add(idInAll[rt]);
      return;
    }
      
    if (l == r) 
      return;

    int m = (l + r) >> 1;
    int leftSon = rt << 1;
    count(l, m, leftSon, set);// build(lson);
    count(m + 1, r, leftSon + 1, set );// build(rson); rt << 1 | 1
  }  
   
  private void pushUp(int rt) {
    int leftSon = rt << 1;
    idInPartial[rt] = idInPartial[leftSon] || idInPartial[leftSon+1];
        
  }
  
  private void build(int l, int r, int rt) {
    idInAll[rt] = DEFALT_ID;  //re-initial  
    idInPartial[rt] = false;
    
    if (l == r) 
      return;

    int m = (l + r) >> 1;
    int leftSon = rt << 1;
    build(l, m, leftSon);// build(lson);
    build(m + 1, r, leftSon + 1 );// build(rson); rt << 1 | 1
  }
  
  
  /*
   * 
   */
  private void update(int L, int R, int postId, int l, int r, int rt) {
    if(idInAll[rt] != DEFALT_ID)
      return;
      
    if (!idInPartial[rt] && (L <= l && r <= R)) {
      idInAll[rt] = postId;
      idInPartial[rt] = true;
      return;
    }
    
    int mid = (l + r) >> 1;
    int leftSon = rt << 1;
    int rightSon = leftSon + 1;
    if (mid >= R )
      update(L, R, postId, l, mid, leftSon); // update lson
    else if(mid < L)
      update(L, R, postId, mid + 1, r, rightSon); // update rson
    else{  // mid is between L and R 
      update(L, R, postId, l, mid, leftSon); // update lson
      update(L, R, postId, mid + 1, r, rightSon); // update lson
    }       
      
    pushUp(rt);
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
      int n, m;
      while(in.hasNext()){
        
        n = in.nextInt();
        
        while( n-- > 0 ){
          m = in.nextInt();
            
          for(int i=1, j=1; i<=m; i++){
            inputStart[i] = in.nextInt();
            inputEnd[i] = in.nextInt();

            inputAll[j++] = inputStart[i];
            inputAll[j++] = inputEnd[i];    
          }
          
          Arrays.sort(inputAll, 1, m << 1 | 1);
          HashMap<Integer, Integer> hash = new HashMap<Integer, Integer>();
          int index = 1;
          for(int k=1; k<=(m << 1); k++)
            if(inputAll[k] != inputAll[k-1])
              hash.put(inputAll[k], index++);
          
          sv.build(1, index-1, 1);
          
          for(int i=m; i>0; i--)  //this is important !!
            sv.update(hash.get(inputStart[i]), hash.get(inputEnd[i]) - 1, i, 1, index-1, 1);
          
          //
          HashSet<Integer> set = new HashSet<Integer>();
          sv.count(1, index-1, 1, set);
          System.out.printf("%d\n", set.size());
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
