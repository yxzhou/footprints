/**
 * _http://acm.hdu.edu.cn/showproblem.php?pid=1754
 * Problem Description
 * 很多学校流行一种比较的习惯。老师们很喜欢询问，从某某到某某当中，分数最高的是多少。
 * 这让很多学生很反感。
 * 
 * 不管你喜不喜欢，现在需要你做的是，就是按照老师的要求，写一个程序，模拟老师的询问。当然，老师有时候需要更新某位同学的成绩。
 * 
 * 
 * Input
 * 本题目包含多组测试，请处理到文件结束。
 * 在每个测试的第一行，有两个正整数 N 和 M ( 0<N<=200000,0<M<5000 )，分别代表学生的数目和操作的数目。
 * 学生ID编号分别从1编到N。
 * 第二行包含N个整数，代表这N个学生的初始成绩，其中第i个数代表ID为i的学生的成绩。
 * 接下来有M行。每一行有一个字符 C (只取'Q'或'U') ，和两个正整数A，B。
 * 当C为'Q'的时候，表示这是一条询问操作，它询问ID从A到B(包括A,B)的学生当中，成绩最高的是多少。
 * 当C为'U'的时候，表示这是一条更新操作，要求把ID为A的学生的成绩更改为B。
 * 
 * Output
 * 对于每一次询问操作，在一行里面输出最高成绩。
 *  
 * Sample Input
   5 6
   1 2 3 4 5
   Q 1 5
   U 3 6
   Q 3 4
   Q 4 5
   U 2 9
   Q 1 5
    
 * Sample Output
 * 5
 * 6
 * 5
 * 9
 */
    
package fgafa.datastructure.segmentTree.rankScore;

import java.io.BufferedInputStream;
import java.util.Scanner;
import java.util.StringTokenizer;

public class Main
{
  final static int maxN = 200001; //222222;
  final static int[] max = new int[maxN << 2];

  final static int[] input = new int[maxN];
  
  void pushUp(int rt) {
    int leftSon = rt << 1;
    max[rt] = Math.max(max[leftSon], max[leftSon + 1]);
  }

  public void build(int l, int r, int rt) {
    if (l == r) {
      max[rt] = input[l];
      return;
    }
    
    int mid = (l + r) >> 1;
    int leftSon = rt << 1;
    build(l, mid, leftSon);// build(lson);
    build(mid + 1, r, leftSon + 1);// build(rson);
    
    pushUp(rt);
  }

  public void update(int p, int sc, int l, int r, int rt) {
    if (l == r) {
      max[rt] = sc;
      return;
    }

    int mid = (l + r) >> 1;
    if (p <= mid)
      update(p, sc, l, mid, rt << 1); // update lson
    else
      update(p, sc, mid + 1, r, rt << 1 | 1); // update rson

    pushUp(rt);
  }

  public int query(int L, int R, int l, int r, int rt) {
    if (L <= l && r <= R) {
      return max[rt];
    }

    int mid = (l + r) >> 1;
    int ret = 0;

    if (L <= mid)
      ret = Math.max(ret, query(L, R, l, mid, rt << 1)); // checkIn lson
    if (R > mid)
      ret = Math.max(ret, query(L, R, mid + 1, r, rt << 1 | 1)); //checkIn rson

    return ret;
  }

  public static void main(String[] args) {

    Main sv = new Main();
    Scanner in = new Scanner(new BufferedInputStream(System.in), "ISO-8859-1");

    try {
      //get test case one by one
      int n, m;
      while(in.hasNext()){
        n = in.nextInt();
        m = in.nextInt();
  
        for (int i = 1; i <= n; i++)
          input[i] = in.nextInt();
  
        sv.build(1, n, 1);
  
        StringTokenizer st;
        String command;
        int a, b;
        while (m-- > 0) {
          while(( command = in.nextLine().trim()).equals("") && in.hasNextLine());

          if(command.equals(""))
            break;
          
          st = new StringTokenizer(command, " ");
          command = st.nextToken();
          a = Integer.valueOf(st.nextToken());
          b = Integer.valueOf(st.nextToken());
          
          if ("Q".equals(command))
            System.out.println(sv.query(a, b , 1, n, 1));
          else
            sv.update(a, b, 1, n, 1);
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