package fgafa.uva.geometry.SkylineN105;

import java.io.*;
import java.util.*;

class Main2
{

  private static String readLn(int maxLg) // utility function to read from stdin
  {
    byte lin[] = new byte[maxLg];
    int lg = 0, ch = -1;

    try {
      while (lg < maxLg) {
        ch = System.in.read();
        if ((ch < 0) || (ch == '\n'))
          break;
        lin[lg++] += ch;
      }
    }
    catch (IOException e) {
      return (null);
    }

    if ((ch < 0) && (lg == 0))
      return (null); // eof
    return (new String(lin, 0, lg));
  }

  private static boolean isValid(int x1, int h, int x2){
    return ( x1 >0 && h >0 && x2>0 && x1 < x2 ); 
  }
  
  public static void main(String[] args) throws Exception {
    int[] koordinate = new int[10000];
    int l_i, h_i, r_i;
    
    String line;
    StringTokenizer idata;
    while ((line = readLn (100)) != null){
      idata = new StringTokenizer(line);
      if (idata.countTokens() == 0)
        break;
      l_i = Integer.parseInt(idata.nextToken());
      h_i = Integer.parseInt(idata.nextToken());
      r_i = Integer.parseInt(idata.nextToken());
      
//    int[][] cases = {{0, 1, 0}
//    ,{1,11,5}, {2,6,7}, {3,13,9}, {12,7,16}, {14,3,25}, {19,18,22}, {23,13,29}, {24,4,28}
//    ,{100, 5, 110}, {100, 3, 110}, {100, 8, 109}, {100, 6, 110}
//    ,{111, 6, 120}, {111, 5, 125}, {115, 10, 117},{116, 7, 123}, {120, 7, 126}
//    ,{200, 5, 205}, {205, 7, 206},{206, 5, 207}
//    ,{210, 5, 216}, {215, 7, 218}, {215, 6, 220}, {215, 7, 219}, {219, 8, 220}
//    ,{250, 6, 255}, {255, 4, 258}, {255, 5, 259}
//    ,{260, 6, 265}, {260, 4, 268}, {260, 4, 270}
//    ,{303, 2, 304},{303, 1, 307}, {305, 2, 306}
////  };     
//    for (int j=0; j < cases.length; j++) {
//      l_i = cases[j][0];
//      h_i = cases[j][1];
//      r_i = cases[j][2];  
      
      if(!isValid(l_i, h_i, r_i))
        continue;
      
      for (int i = l_i; i < r_i; i++)
        if (koordinate[i] < h_i)
          koordinate[i] = h_i;
    }
    
    StringBuilder output = new StringBuilder();
    for (int j = 1; j < 10000; j++) {
      if (koordinate[j-1] != koordinate[j]){
        output.append(j);
        output.append(" ");
        output.append(koordinate[j]);
        output.append(" ");
      }
    }
    if(output.length() > 0)
      System.out.println(output.substring(0, output.length() - 1));
    else
      System.out.println();
  }

}
