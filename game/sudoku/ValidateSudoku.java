package fgafa.game.sudoku;

/**
 * The <code>Sudoku</code> class povides a static <code>main</code>
 * method allowing it to be called from the command line to print the
 * solution to a specified Sudoku problem.
 *
 * <p>The following is an example of a Sudoku problem:
 *
 * <pre>
 * -----------------------
 * |   8   | 4   2 |   6   |
 * |   3 4 |       | 9 1   |
 * | 9 6   |       |   8 4 |
 *  -----------------------
 * |       | 2 1 6 |       |
 * |       |       |       |
 * |       | 3 5 7 |       |
 *  -----------------------
 * | 8 4   |       |   7 5 |
 * |   2 6 |       | 1 3   |
 * |   9   | 7   1 |   4   |
 *  -----------------------
 * </pre>
 *
 * The goal is to fill in the missing numbers so that
 * every row, column and box contains each of the numbers
 * <code>1-9</code>.  Here is the solution to the
 * problem above:
 *
 * <pre>
 *  -----------------------
 * | 1 8 7 | 4 9 2 | 5 6 3 |
 * | 5 3 4 | 6 7 8 | 9 1 2 |
 * | 9 6 2 | 1 3 5 | 7 8 4 |
 *  -----------------------
 * | 4 5 8 | 2 1 6 | 3 9 7 |
 * | 2 7 3 | 8 4 9 | 6 5 1 |
 * | 6 1 9 | 3 5 7 | 4 2 8 |
 *  -----------------------
 * | 8 4 1 | 9 6 3 | 2 7 5 |
 * | 7 2 6 | 5 8 4 | 1 3 9 |
 * | 3 9 5 | 7 2 1 | 8 4 6 |
 *  -----------------------
 * </pre>
 *
 * Note that the first row <code>187492563</code> contains
 * each number exactly once, as does the first column
 * <code>159426873</code>, the upper-left box
 * <code>187534962</code>, and every other row, column
 * and box.
 *
 * <p>The {@link #main(String[])} method encodes a problem as an array
 * of strings, with one string encoding each constraint in the problem
 * in row-column-value format.  Here is the problem again with
 * the indices indicated:
 *
 * <pre>
 *     0 1 2   3 4 5   6 7 8
 *    -----------------------
 * 0 |   8   | 4   2 |   6   |
 * 1 |   3 4 |       | 9 1   |
 * 2 | 9 6   |       |   8 4 |
 *    -----------------------
 * 3 |       | 2 1 6 |       |
 * 4 |       |       |       |
 * 5 |       | 3 5 7 |       |
 *   -----------------------
 * 6 | 8 4   |       |   7 5 |
 * 7 |   2 6 |       | 1 3   |
 * 8 |   9   | 7   1 |   4   |
 *    -----------------------
 * </pre>
 *
 * The <code>8</code> in the upper left box of the puzzle is encoded
 * as <code>018</code> (<code>0</code> for the row, <code>1</code> for
 * the column, and <code>8</code> for the value).  The <code>4</code>
 * in the lower right box is encoded as <code>874</code>.
 *
 * <p>The full command-line invocation for the above puzzle is:
 *
 * <pre>
 * % java -cp . Sudoku 018 034 052 076 \
 *                     113 124 169 171 \
 *                     209 216 278 284 \
 *                     332 341 356     \
 *                     533 545 557     \
 *                     608 614 677 685 \
 *                     712 726 761 773 \
 *                     819 837 851 874 \
 * </pre>
 *
 * <p>See <a href="http://en.wikipedia.org/wiki/Sudoku">Wikipedia:
 * Sudoku</a> for more information on Sudoku.
 *
 * <p>The algorithm employed is similar to the standard backtracking
 * <a href="http://en.wikipedia.org/wiki/Eight_queens_puzzle">eight
 * queens algorithm</a>.
 *
 * @version 1.0
 * @author <a href="http://www.colloquial.com/carp">Bob Carpenter</a>
 */
public class ValidateSudoku
{
  
  /*
   * Determine if a Sudoku is valid. 
   * According to: Sudoku Puzzles - The Rules.
   *  1) Each row must have the numbers 1-9 occuring just once.
   *  2) Each column must have the numbers 1-9 occuring just once.
   *  3) The numbers 1-9 must occur just once in each of the 9 sub-boxes of the grid. 
   * 
   * The Sudoku board could be partially filled, where empty cells are filled with the character '.'. 
   * 
   * Time O(),  Space O()
   */
	
   public boolean isValidSudoku_n(char[][] board) {
       //check
       if(null == board || 9 != board.length || 9 != board[0].length){
           return false;
       }
       
       //check row from up to down
       boolean[] isVisited;//default all are false
       for(int row = 0; row < 9; row++){
           isVisited = new boolean[9];//default all are false
           for(int col = 0; col  < 9; col++){
               if(!isValid(board[row][col], isVisited)){
                   return false;
               }
           }
       }
       
       //check col from left to right
       for(int col = 0; col < 9; col++){
           isVisited = new boolean[9];//default all are false
           for(int row = 0; row < 9; row++){
               if(!isValid(board[row][col], isVisited)){
                   return false;
               }
           }
       }
       
       //check sub-matrix (3 * 3) 
       for(int row = 0; row< 9; row += 3){
           for(int col = 0; col < 9; col += 3){
               isVisited = new boolean[9];//default all are false
               for(int subRow = 0; subRow <3; subRow++){
                   for(int subCol = 0; subCol < 3; subCol++){
                       if(!isValid(board[row + subRow][col + subCol], isVisited)){
                           return false;
                       }
                   }
               }
           }
       }
       
       return true;
   }
	
   private boolean isValid(char c, boolean[] isVisited){
       if('.' == c){
           return true;
       }else{
           int i = c - '1';
           if(i >=0 && i <=8 && !isVisited[i]){
        	   isVisited[i] = true;
        	   return true;
           }
       }
       
       return false;
   }
   
	public boolean isValidSudoku(int[][] board) {
		//check
		
		//init
		boolean[][] rows = new boolean[9][9];
		boolean[][] cols = new boolean[9][9];
		boolean[][] blocks = new boolean[9][9];
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				rows[i][j] = false;
				cols[i][j] = false;
				blocks[i][j] = false;
			}
		}
		
		//
		int value;
		for (int row = 0; row < 9; ++row) {
			for (int col = 0; col < 9; ++col) {
				value = board[row][col] - 49; // '1' is 49
				if ('.' != board[row][col]){
					if (rows[row][value] || cols[col][value] || blocks[row - row % 3 + col / 3][value]){
						return false;
					}
					rows[row][value] = cols[col][value] = blocks[row - row % 3 + col / 3][value] = true;
				}
			}
		}
		return true;
	}
	
	
  public boolean isValidSudoku(int[][] board, int n) {
    //check
    if(board == null)
      throw new IllegalArgumentException("board can't be null");
    
    if(board.length != n)
      throw new IllegalArgumentException("board.length != n");
    
    for(int i=0; i<n; i++){
      if(board[i].length != n)
        throw new IllegalArgumentException("board["+i+"].length != n");
    }
    
    int blockLen = (int)Math.sqrt(n);
    if(blockLen * blockLen != n)
      throw new IllegalArgumentException("n must be x*x, x is 2, 3, --");
    
    //
    int[] nVal ;
    
    //check the row one by one        
    int unit;
    for (int row = 0; row < n; ++row){
      nVal = new int[n];   //default it's 0, mark 1 when it happened once. 
      for (int col = 0; col < n; ++col){
        unit = board[row][col]; 

        if(!isValid(nVal, n, unit))
          return false;
      }
    }
    
    //check the col one by one    
    for (int col = 0; col < n; ++col){
      nVal = new int[n];   //default it's 0, mark 1 when it happened once. 
      for (int row = 0; row < n; ++row){
        unit = board[row][col]; 

        if(!isValid(nVal, n, unit))
          return false;
      }
    }
    
    //check the Math.sqrt(n)*Math.sqrt(n) sub grids    
    for (int boxRow = 0; boxRow < n; boxRow += blockLen){
      for (int boxCol = 0; boxCol < n; boxCol += blockLen){
        nVal = new int[n];   //default it's 0, mark 1 when it happened once.         
        for (int k = 0; k < blockLen; ++k)
          for (int m = 0; m < blockLen; ++m){
            unit = board[boxRow + k][boxCol + m]; 
            
            if(!isValid(nVal, n, unit))
              return false;
          }
      }
    }

    // no violations, so it's legal
    return true; 
  }
  
  private boolean isValid(int[] nVal, int n, int unit){
    assert nVal.length == n;
    
    if(unit < 0 || unit >= n || nVal[unit] == 1)
      return false;
    
    nVal[unit] = 1;
    return true;
  }
  

  
}