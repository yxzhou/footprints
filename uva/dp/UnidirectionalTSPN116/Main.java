package uva.dp.UnidirectionalTSPN116;

import java.io.*;
import java.util.*;

class Main
{

  private void findTSP(int[][] matrix, int rowN, int colN) {

    if (rowN == 1) {
      String path = String.valueOf(1);
      long weight = matrix[0][0];

      for (int col = 1; col < colN; col++) {
        path += " " + String.valueOf(1);
        weight += matrix[0][col];
      }

      System.out.println(path);
      System.out.println(weight);

    }
    else if (rowN == 2) {
      int min = 0;
      long weight = 0;
      String path = "";

      for (int col = 0; col < colN; col++) {
        min = 0;
        if (matrix[0][col] > matrix[1][col])
          min = 1;

        path += String.valueOf(min + 1) + " ";
        weight += matrix[min][col];
      }

      System.out.println(path.substring(0, path.length() - 1));
      System.out.println(weight);
    }
    else { // rowN >=3
      String[][] path = new String[colN][rowN];
      long[][] weight = new long[colN][rowN];
      int[] min = new int[rowN];

      for (int row = 0; row < rowN; row++) {
        weight[0][row] = matrix[row][0];
        path[0][row] = String.valueOf(row);
      }

      for (int col = 1; col < colN; col++) {

        min[0] = min(weight[col - 1], 0, 1, rowN - 1, path[col - 1]);
        for (int row = 1; row < rowN - 1; row++)
          min[row] = min(weight[col - 1], row - 1, row, row + 1, path[col - 1]);
        min[rowN - 1] = min(weight[col - 1], 0, rowN - 2, rowN - 1,
            path[col - 1]);

        for (int row = 0; row < rowN; row++) {
          path[col][row] = path[col - 1][min[row]] + String.valueOf(row);
          weight[col][row] = weight[col - 1][min[row]] + matrix[row][col];
        }
      }

      //
      int minIndex = 0;
      for (int row = 1; row < rowN; row++)
        if (weight[colN - 1][minIndex] > weight[colN - 1][row]
            || (weight[colN - 1][minIndex] == weight[colN - 1][row] 
                && isBigger(path[colN - 1][minIndex], path[colN - 1][row])))
          minIndex = row;

      StringBuilder sb = new StringBuilder();
      for (int i = 0; i < path[colN - 1][minIndex].length(); i++) {
        sb.append(path[colN - 1][minIndex].charAt(i) - 48 + 1); // '0' is 48
        sb.append(" ");
      }
      System.out.println(sb.substring(0, sb.length() - 1));
      System.out.println(weight[colN - 1][minIndex]);
    }

  }



  private boolean isBigger(String s1, String s2) {

    for (int i = 0; i < s1.length(); i++) {
      if(s1.charAt(i) < s2.charAt(i) )
        return false;
      else if (s1.charAt(i) > s2.charAt(i))
        return true;
    }

    return false;
  }



  private int min(long[] weight, int row1, int row2, int row3, String[] path) {
    int rowX = row1;
    if (weight[rowX] > weight[row2]
        || (weight[rowX] == weight[row2] && isBigger(path[rowX], path[row2])))
      rowX = row2;
    if (weight[rowX] > weight[row3]
        || (weight[rowX] == weight[row3] && isBigger(path[rowX], path[row3])))
      rowX = row3;

    return rowX;
  }



  public static void main(String[] args) throws Exception {
    Main sv = new Main();
    Scanner in = new Scanner(new BufferedInputStream(System.in), "UTF-8");

    int rowN = 0, colN = 0;
    int[][] matrix = new int[10][100];

    try {
      while (in.hasNext()) {
        // read
        rowN = in.nextInt();
        colN = in.nextInt();

        for (int i = 0; i < rowN; i++)
          for (int j = 0; j < colN; j++)
            matrix[i][j] = in.nextInt();

        // main
        sv.findTSP(matrix, rowN, colN);
      }
    }
    catch (Exception e) {
      // e.printStackTrace();
    }
    finally {
      in.close();
    }

  }
}
