package fgafa.game.sudoku;

import fgafa.Constants;
import algs4.stdlib.In;

/*
 * For leetcode
 * 
 * Write a program to solve a Sudoku puzzle by filling the empty cells.
 * 
 * Empty cells are indicated by the character '.'.
 * 
 * You may assume that there will be only one unique solution.
 */


public class SolveSudoku {

    /*
     * check cell by cell. ([0][0] -> [0][1]-> .. -> [1][0]-> ..   )
     * 
     */
      public void solveSudoku(int[][] cells) {
        solve(0, 0, cells);
      }
      
      private boolean solve(int rowNum, int colNum, int[][] cells) {
        /* check row from up to down, from left to right in a row. stop when i== 8 and j==8*/
        if (colNum == 9) {
          if (rowNum == 8){
            return true;  // the output is in the cells 
          }
          
          colNum = 0;
          rowNum++;
        }
     
        /*if cells[i][j] filled, skip, go to next, cells[i+1][j]*/
        if (cells[rowNum][colNum] != 0) 
          return solve(rowNum, colNum + 1, cells);

        /*to a cell, recursive try 1-9 if it's legal.*/ 
        for (int val = 1; val <= 9; ++val) {
          if (isLegal(rowNum, colNum, val, cells)) { //if it's ok till to now, try next cell.
            cells[rowNum][colNum] = val;
            if (solve(rowNum, colNum + 1, cells)){
              return true;
            }
          }
        }
        
        //reset to 0 when it's not legal
        cells[rowNum][colNum] = 0; // reset on backtrack
        return false;
      }

      private boolean isLegal(int currRowNum, int currColNum, int expect, int[][] cells) {
        //check the row
        for (int row = 0; row < 9; ++row)
          if (expect == cells[row][currColNum]){
            return false;
          }
        
        //check the col
        for (int col = 0; col < 9; ++col)
          if (expect == cells[currRowNum][col]){
            return false;
          }
        
        //check the 3*3 sub grids
        int blockBaseRowNum = ((int)(currRowNum / 3)) * 3;
        int blockBaseColNum = ((int)(currColNum / 3)) * 3;
        for (int k = 0; k < 3; ++k)
          for (int m = 0; m < 3; ++m)
            if (expect == cells[blockBaseRowNum + k][blockBaseColNum + m]){
              return false;
            }

        // no violations, so it's legal
        return true; 
      }

      private static int[][] parseProblem(In in) {
        int[][] problem = new int[9][9]; // default 0 vals
        int V = in.readInt();
        String tmp;
        for (int n = 0; n < V; ++n) {
          tmp = in.readString();
          int i = Integer.parseInt(tmp.substring(0, 1));
          int j = Integer.parseInt(tmp.substring(1, 2));
          int val = Integer.parseInt(tmp.substring(2, 3));
          problem[i][j] = val;
        }
        return problem;
      }



      private static void writeMatrix(int[][] solution) {
        for (int i = 0; i < 9; ++i) {
          if (i % 3 == 0){
            System.out.println(" -----------------------");
          }
          
          for (int j = 0; j < 9; ++j) {
            if (j % 3 == 0){
              System.out.print("| ");
            }
            System.out.print(solution[i][j] == 0 ? " " : Integer
                .toString(solution[i][j]));

            System.out.print(' ');
          }
          System.out.println("|");
        }
        System.out.println(" -----------------------");
      }


      /**
       * Print the specified Sudoku problem and its solution.  The
       * problem is encoded as specified in the class documentation
       * above.
       *
       * @param args The command-line arguments encoding the problem.
       */
      public static void main(String[] args) {
        SolveSudoku sv = new SolveSudoku();
        //int[][] matrix = parseProblem(args);

        String args0 = Constants.TEST_DATA_URL + "sudoku2.txt";
        
        In in = new In(args0);

        int[][] matrix = parseProblem(in);

        writeMatrix(matrix);
        
        if (sv.solve(0, 0, matrix)) // solves in place
          writeMatrix(matrix);
        else
          System.out.println("NONE");
      }

}
