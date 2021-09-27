package uva.graph.ArbitrageN104;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.Scanner;

import util.Misc;

public class Main22
{
  /**
   * @param args
   */
  public static void main(String[] args) {
    int n, i, j, k, step, flag;
    int[][][] path = new int[20][20][20];
    int[] seq = new int[20]; // for output,
    double[][][] best = new double[20][20][20];  //best[i][j][s] mean the best profit of a path that take s transactions to move from i to j

    double temp;

    String inputFile = "src/fgafa.uva/graph/ArbitrageN104/Arbitrage.txt";
    try(//Scanner in = new Scanner(new BufferedInputStream(System.in), "UTF-8");
            Scanner in = new Scanner(new BufferedInputStream(new FileInputStream(new File(inputFile))), "UTF-8")){
      while (in.hasNext()) {

        // init
        flag = 0;
        n = in.nextInt();
        for (i = 0, step = 0; i < n; i++) {
          for (j = 0; j < n; j++) {
            if (i == j)
              best[i][j][step] = 1d;
            else
              best[i][j][step] = in.nextDouble();

            path[i][j][step] = i;
          }
        }
        for (step = 1; step < n; step++)
          for (i = 0; i < n; i++)
            for (j = 0; j < n; j++)
              best[i][j][step] = 0;

        // floyd-Warshall
        outerloop: for (step = 1; step < n; step++) {
          for (k = 0; k < n; k++)
            for (i = 0; i < n; i++)
              for (j = 0; j < n; j++) {

                temp = best[i][k][step - 1] * best[k][j][0];

                if (temp > best[i][j][step]) {
                  best[i][j][step] = temp;
                  path[i][j][step] = k;

                  //output
                  if (i == j && best[i][j][step] > 1.01) {
                    seq[step] = path[i][i][step];
                    for (j = step - 1; j >= 0; j--)
                      seq[j] = path[i][seq[j + 1]][j];

                    for (j = 0; j <= step; j++)
                      System.out.print(seq[j] + 1 + " ");
                    System.out.println(i + 1);

                    flag = 1;
                    break outerloop;
                  }
                }
              }

        }

        if (flag == 0)
          System.out.println(Main.NOTFOUND);

      }
    }
    catch (Exception e) {
      // e.printStackTrace();
    }


    System.out.println("\n===Ans====");

    Misc.printFile("src/fgafa.uva/graph/ArbitrageN104/Arbitrage_ans.txt");

  }




}
