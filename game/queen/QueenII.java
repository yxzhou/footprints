package game.queen;

import java.util.ArrayList;
import java.util.List;

import util.Misc;

/*
 * the basic method
 */
public class QueenII
{

  private static int SUM = 0;
  //private static int N = 8;
  //private static int[] QLIST = new int[N];
  
  public int totalNQueens(int n) {
    SUM = 0 ;
    
    PlaceQueue(0, 0, n, new int[n]);
    
    return SUM;
  }
  

  private void PlaceQueue(int currRow, int first, int last, int[] board) {
    if (currRow == last) {
      SUM ++;
      return;
    }
    
    for (board[currRow] = first; board[currRow] < last; board[currRow]++) {
      if (IsSafe(currRow, board)) // continue the next row
        PlaceQueue(currRow + 1, first, last, board);
    }
    
  }
  

  
  public static void main(String[] args) {
    QueenII sv = new QueenII();
    int n = 12;
    
    for(int i=1; i<=n; i++){
      long startTime = System.currentTimeMillis();
/**      
      sv.totalNQueens(i);
      
      System.out.print("Totally it has " + SUM + " combinations. ");
      System.out.print("It takes " + (System.currentTimeMillis() - startTime)
          + " ms \n");
*/
      
      //ArrayList<String[]> result = sv.solveNQueens(4);
      //Misc.printStringArrayList(result);
      
//      ArrayList<ArrayList<String>> result = sv.solveNQueens_2(n);
//      Misc.printListList(result);
    }
    
    
    List<List<String>> result = sv.solveNQueens_n2(4);
    Misc.printListList(result);
  }

  /**
   * 
   * For example,
   * There exist two distinct solutions to the 4-queens puzzle:
   *
   * [
   * [".Q..",  // Solution 1
   * "...Q",
   * "Q...",
   * "..Q."],
   * 
   * ["..Q.",  // Solution 2
   * "Q...",
   * "...Q",
   * ".Q.."]
   * ]
   * 
   * @param n
   * @return
   */
  
  public ArrayList<String[]> solveNQueens(int n) {
    ArrayList<String[]> result = new ArrayList<String[]>();
    
    PlaceQueue(0, 0, n, new int[n], result);
    
    return result;
  }
  
  /* board[x], it means in xth row, the queen position is in column of board[x] */
  private void PlaceQueue(int currRow, int firstRow, int lastRow, int[] board, ArrayList<String[]> result) {
    
    if (currRow == lastRow) {
      result.add( convert(board) );
      return;
    }
    
    /*to current row, recursive try position 1-n if it's legal. */ 
    for (board[currRow] = firstRow; board[currRow] < lastRow; board[currRow]++) {
      if (IsSafe(currRow, board)) //if it's ok till to now, continue to try next row
        PlaceQueue( currRow + 1, firstRow, lastRow, board, result); 
    }
  }
  
  private boolean IsSafe(int curRow, int[] board) {
    //Check the existed column
    for (int aboveRow = 0; aboveRow < curRow; aboveRow++) {
      int diff = Math.abs(board[aboveRow] - board[curRow]) ;  

      /*false when they are on the same column, or the same diagonal */
      if (diff == 0 || diff == (curRow - aboveRow )) 
        return false;
    }
    return true;
  }
  
  private String[] convert(int[] list){
    String[] result = new String[list.length]; 
    StringBuilder tmp;
    
    for(int i = 0; i<list.length; i++){
      tmp = new StringBuilder();
      for(int j = 0; j<list.length; j++){
        if(list[i] == j)
          tmp.append("Q");
        else
          tmp.append(".");  
      }
      
      result[i] = tmp.toString();
    }
    
    return result;    
  }
  
  
  public List<String[]> solveNQueens_n(int n) {
      List<String[]> result = new ArrayList<>();
      if( n < 1){
    	  return result;
      }
      
      int[] board = new int[n]; //default all are 0
      dfs(0, board, result);
      
      return result;
  }
  
  private void dfs(int rowIndex, int[] board, List<String[]> result){
      if(rowIndex == board.length){
          result.add(convert(board));
          return;
      }
      
      for(board[rowIndex] = 0; board[rowIndex] < board.length; board[rowIndex] ++ ){
          if(isValid(board, rowIndex)){
              dfs(rowIndex + 1, board, result);
          }
      }
  }
  
  
  
  private boolean isValid(int[] board, int rowIndex){
      int diff;
      for(int i=0; i<rowIndex; i++){
    	  diff = board[rowIndex] - board[i];
          
    	  // if it's on the same column,  col1 - col2 == 0.
    	  // it would not be on the same row, and we know rowIndex > i
    	  // if it's on the orthodiagonal,  row1 - col1 == row2 - col2
    	  // if it's on the back-diagonal,  row1 + col1 == row2 + col2
    	  if( 0 == diff || Math.abs(diff) == (rowIndex - i)){
    		  return false;
    	  }
      }
      
      return true;
  }
  
  /**
   * Get all distinct N-Queen solutions
   * @param n: The number of queens
   * @return: All distinct solutions
   * For example, A string '...Q' shows a queen on forth position
   */
  public List<List<String>> solveNQueens_n2(int n) {
      List<List<String>> result = new ArrayList<>();
      
      // check
      if(n < 1){
          return result;
      }
      
      int[] board = new int[n]; // default all are 0
      solveNQueens(board, 0, result);
      
      return result;
  }
  
  //dfs
  private void solveNQueens(int[] board, int rowIndex, List<List<String>> result){
      if(rowIndex == board.length){
          result.add(buildResult(board));
          return;
      }
      
      for(int colIndex = 0; colIndex < board.length; colIndex++){
          if(isValid(board, rowIndex, colIndex)){
        	  board[rowIndex] = colIndex;
        	  solveNQueens(board, rowIndex + 1, result);
        	  //board[rowIndex] = 0;
          }
      }
  }
  
  private ArrayList<String> buildResult(int[] board){
      ArrayList<String> result = new ArrayList<String>();
      
      char[] row = new char[board.length];
      for(int i = 0; i < board.length; i++){
          row[i] = '.';
      }
      
      for(int i : board){
          row[i] = 'Q';
          result.add(String.valueOf(row));
          row[i] = '.';
      }
      
      return result;
  }
  
  private boolean isValid(int[] board, int rowIndex, int colIndex){
	  for(int i = 0; i < rowIndex; i++){
		  if(board[i] == colIndex || rowIndex - i == Math.abs(colIndex - board[i])){
			  return false;
		  }
	  }
	  
	  return true;
  }
  
  public int totalNQueens_n(int n) {
	  if(n<1){
		  return 0;
	  }
	  
	  int[] result = new int[1];
	  dfs(0, new int[n], result);
	  return result[0];
  }
  
  private void dfs(int rowIndex, int[] board, int[] result){
      if(rowIndex == board.length){
          result[0]++;
          return;
      }
      
      for(board[rowIndex] = 0; board[rowIndex] < board.length; board[rowIndex] ++ ){
          if(isValid(board, rowIndex)){
              dfs(rowIndex + 1, board, result);
          }
      }
  }
}
