package matrix;

public class Matrix
{

  private double[][] data;
  
  public Matrix(double[][] data){
    this.data = data;
  }
  
  public Matrix(){
    this.data = new double[4][4];
  }
  
  public String toString(){
    if(this.data == null) return null;
      
    StringBuffer sb = new StringBuffer();
    sb.append("{");
    for(int row=0; row< this.data.length; row++){
      
      sb.append("{");
      for(int col = 0; col < this.data[row].length; col++){
        sb.append(this.data[row][col]);
        sb.append(", ");
      }
      sb.replace(sb.length()-2, sb.length(), "}, ");
      
    }
    sb.replace(sb.length()-2, sb.length(), "} ");

    return sb.toString();
  }
  
  /**
   * @param args
   */
  public static void main(String[] args) {

    Matrix a = new Matrix(new double[][]{
        {1, 2, 3, 4}, 
        {2, 3, 4, 5},
        {3, 4, 5, 6}, 
        {4, 5, 6, 7}});

    Matrix b = new Matrix(new double[][]{
        {2, 3, 4, 5}, 
        {3, 4, 5, 6},
        {4, 5, 6, 7}, 
        {5, 6, 7, 8}});

    System.out.println("Matrix A is: " + a.toString());
    System.out.println("Matrix B is: " + b.toString());
    
    Matrix c = a.add(b);
    System.out.println("Add(a, b) is: " + c.toString());


    Matrix a2 = new Matrix(new double[][]{
        {1, 2, 3}, 
        {2, 3, 4}});

    Matrix b2 = new Matrix(new double[][]{
        {10, 20, 30, 40}, 
        {20, 30, 40, 50},
        {30, 40, 50, 60}});    
    System.out.println("\nMatrix A2 is: " + a2.toString());
    System.out.println("Matrix B2 is: " + b2.toString());
    
    Matrix d = a2.multiply_Normal(b2);
    System.out.println("Multiple(a, b) is: " + d.toString());
    

  }

  /*
   * Matrix Multiplication as the normal algorithm
   *  Define a matrix with m rows and every row has n cols as matrix(m, n); 
   *   
   *  matrix1(m,n) * matrix2(m,n) = matrixS(m,n)
   *  
   *  e.g:
   *  matrix1(2, 3) =
   *  | 1, 2, 3 |
   *  | 2, 3, 4 |
   *   
   *  matrix2(2, 3) =
   *  | 10, 20, 30 |
   *  | 20, 30, 40 |
   *   
   *  matrixS(2, 3) = matrix1(2, 3) + matrix2(2, 3) =
   *  | 11, 22, 33 |  //( 1+10, 2+20, 3+30 )
   *  | 22, 33, 44 |  //( 2+20, 3+30, 4+40 ) 
   *   
   *  Time O(m * n) 
   */

  public Matrix add(Matrix bMatrix) {

    if (bMatrix.data.length != data.length
        || bMatrix.data[0].length != data[0].length)
      return null;
    
    double[][] sum = new double[data.length][data[0].length];  

    for (int i = 0; i < data.length; i++) {
      for (int j = 0; j < data.length; j++) {
        sum[i][j] = data[i][j] + bMatrix.data[i][j];
        //sum[i][j] += data[i][j] + bMatrix.data[i][j];
      }
    }
    return new Matrix(sum);
  }

  public Matrix minus(Matrix bMatrix) {

    if (bMatrix.data.length != data.length
        || bMatrix.data[0].length != data[0].length)
      return null;
    
    double[][] minus = new double[data.length][data[0].length];  

    for (int i = 0; i < data.length; i++) {
      for (int j = 0; j < data.length; j++) {
        minus[i][j] = data[i][j] - bMatrix.data[i][j];

      }
    }
    return new Matrix(minus);
  }
  
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
 
  public Matrix multiply_Normal(Matrix bMatrix) {

    if (data[0].length != bMatrix.data.length)
      return null;

    int m = data.length;
    int n = data[0].length;
    int p = bMatrix.data[0].length;
    
    double[][] result = new double[m][p];
    
    for (int i = 0; i < m; i++) {
      for (int k = 0; k < n; k++) {

        for (int j = 0; j < p; j++) {
          result[i][j] += (data[i][k] * bMatrix.data[k][j]);
        }

      }
    }
    return new Matrix(result);
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
      int m = A.length, n = A[0].length, nB = B[0].length;
      int[][] C = new int[m][nB];

      for(int i = 0; i < m; i++) {
          for(int k = 0; k < n; k++) {
              if (A[i][k] != 0) {
                  for (int j = 0; j < nB; j++) {
                      if (B[k][j] != 0){
                          C[i][j] += A[i][k] * B[k][j];
                      }
                  }
              }
          }
      }
      return C;   
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
  protected Matrix multiply_Strassen(Matrix bMatrix) {
    
    if (data[0].length != bMatrix.data.length)
      return null;

    int m = data.length;
    int n = data[0].length;
    int p = bMatrix.data[0].length;
    
    double[][] result = new double[m][p];
    

    
    return new Matrix(result);
    
  }
  
}
