/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package matrix;

import util.Misc;

/**
 *
 * @author yuanxi
 */
public class MatrixMultiplication {
    /*
     *  Define a matrix with m rows and every row has n cols as matrix(m, n); 
     *  
     *  matrix1(m,n) * matrix2(n,p) = matrixM(m,p)
     *   
    *  e.g:
    *  matrix1(2, 3) =
    *  | 1, 2, 3 |
    *  | 2, 3, 4 |
    *   
    *  matrix2(3, 4) =
    *  | 10, 20, 30, 40|
    *  | 20, 30, 40, 50|
    *  | 30, 40, 50, 60|
    *   
    *  matrixM(2, 4) = matrix1(2, 3) * matrix2(3, 4)
    *  | 140, 200, 260, 320|     // ( 1*10 + 2*20 + 3*30, 1*20+ 2*30 + 3*40, 1*30+ 2*40 + 3*50, 1*40+ 2*50 + 3*60)
    *  | 200, 290, 380, 470|     // ( 2*10 + 3*20 + 4*30, 2*20+ 3*30 + 4*40, 2*30+ 3*40 + 4*50, 2*40+ 3*50 + 4*60)         
    *   
    *  Time O(m * p * (n + (n-1))) =>  O( m * p * 2n)  // n times multiple, n-1 times addition
    */
 
    public int[][] multiply_normal(int[][] A, int[][] B) {
        if(A == null || A.length == 0 || A[0].length == 0 ){
            return new int[0][0];
        }
        
        int m = A.length; // row number of A
        int n = A[0].length; //column number of A, row number of B
        int nB = B[0].length; //column number of B
        
        int[][] r = new int[m][nB];

        for (int i = 0; i < m; i++) {
            for (int k = 0; k < n; k++) {
                for (int j = 0; j < nB; j++) {
                    r[i][j] += A[i][k] * B[k][j];
                }
            }
        }
        return r;
    }

    /**
     * 
     * Given two sparse matrices A and B, return the result of AB. 
     * You may assume that A's column number is equal to B's row number.
     * 
     * A sparse matrix can be represented as a sequence of rows, 
     * each of which is a sequence of (column-number, value) pairs of the nonzero values in the row.
     * 
     */
  
    public int[][] multiply_sparse(int[][] A, int[][] B) {
        if(A == null || A.length == 0 || A[0].length == 0 ){
            return new int[0][0];
        }
        
        int m = A.length; // row number of A
        int n = A[0].length; //column number of A, row number of B
        int nB = B[0].length; //column number of B
        
        int[][] r = new int[m][nB];

        for (int i = 0; i < m; i++) {
            for (int k = 0; k < n; k++) {
                if (A[i][k] == 0) {
                    continue;
                }
                
                for (int j = 0; j < nB; j++) {
                    r[i][j] += A[i][k] * B[k][j];
                }
            }
        }
        return r;
    }
  
    /*
     * 
      Consider multiplying two 2x2 matrices, as follows:

      A B * E F = AE+BG AF+BH
      C D   G H   CE+DG CF+DH

      The obvious way to compute the right side is just to do the 8 multiplies and 4 additions. 
      But imagine multiplies are a lot more expensive than additions, so we want to reduce 
      the number of multiplications if at all possible. 
      Strassen uses a trick to compute the right hand side with one less multiply and a lot more additions (and some subtractions).

      Here are the 7 multiplies:
      M1 = (A + D) * (E + H) = AE + AH + DE + DH
      M2 = (A + B) * H = AH + BH
      M3 = (C + D) * E = CE + DE
      M4 = A * (F - H) = AF - AH
      M5 = D * (G - E) = DG - DE
      M6 = (C - A) * (E + F) = CE + CF - AE - AF
      M7 = (B - D) * (G + H) = BG + BH - DG - DH

      AE+BG=M1+M7-M2+M5
      AF+BH=M2+M4 , 
      CE+DG=M3+M5 , 
      CF+DH=M1+M6-M3+M4

      Note Strassen works not just for 2x2 matrices, but for any (even) sized matrices where the A..H are submatrices.

     * 
     */
    protected int[][] multiply_Strassen(int[][] A, int[][] B) {


        //TODO
        return new int[0][0];
    }
    
    public static void main(String[] args){
        MatrixMultiplication sv = new MatrixMultiplication();
        
        int[][] a2 = new int[][]{
            {1, 0, 0},
            {-1, 0, 3}};

        int[][] b2 = new int[][]{
            {7, 0, 0, 0},
            {0, 0, 0, 0},
            {0, 0, 0, 1}};
        
        System.out.println("\nMatrix A2 is: " + Misc.array2String(a2));
        System.out.println("\nMatrix B2 is: " + Misc.array2String(b2));

        int[][] d = sv.multiply_normal(a2, b2);
        System.out.println("\nMultiple_normal(a, b) is: " + Misc.array2String(d));
        
        d = sv.multiply_sparse(a2, b2);
        System.out.println("\nMultiple_sparse(a, b) is: " + Misc.array2String(d));
        
    }
}
