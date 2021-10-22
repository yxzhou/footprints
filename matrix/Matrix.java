package matrix;

public class Matrix{

    private double[][] data;

    public Matrix(double[][] data) {
        this.data = data;
    }

    public Matrix() {
        this.data = new double[4][4];
    }

    public String toString() {
        if (this.data == null) {
            return null;
        }

        StringBuffer sb = new StringBuffer();
        sb.append("{");
        for (int row = 0; row < this.data.length; row++) {

            sb.append("{");
            for (int col = 0; col < this.data[row].length; col++) {
                sb.append(this.data[row][col]);
                sb.append(", ");
            }
            sb.replace(sb.length() - 2, sb.length(), "}, ");

        }
        sb.replace(sb.length() - 2, sb.length(), "} ");

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

        if (bMatrix.data.length != data.length || bMatrix.data[0].length != data[0].length) {
            return null;
        }

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

        if (bMatrix.data.length != data.length || bMatrix.data[0].length != data[0].length) {
            return null;
        }

        double[][] minus = new double[data.length][data[0].length];

        for (int i = 0; i < data.length; i++) {
            for (int j = 0; j < data.length; j++) {
                minus[i][j] = data[i][j] - bMatrix.data[i][j];

            }
        }
        return new Matrix(minus);
    }
  
}
