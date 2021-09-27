package dailyCoding;

/**
 *
 * Sudoku is a puzzle where you're given a partially-filled 9 by 9 grid with digits. The objective is to fill the grid with the constraint that every row, column, and box (3 by 3 subgrid) must contain all of the digits from 1 to 9.
 * Implement an efficient sudoku solver.
 *
 *  Tags: dropbox
 */

public class SudokuSolver {
    final static int size = 9;

    public boolean solver(int[][] sudoku){
        boolean[][] rows = new boolean[size][size];
        boolean[][] columns = new boolean[size][size];
        boolean[][] squares = new boolean[size][size];

        for(int row = 0; row < size; row++){
            for(int column = 0; column < size; column++){
                int value = sudoku[row][column];
                if( value != 0 ){
                    int squareId = (row % 3) * 3 + (column % 3);
                    if(rows[row][value] || columns[column][value] || squares[squareId][value] ){
                        return false;
                    }

                    rows[row][value] = true;
                    columns[column][value] = true;
                    squares[squareId][value] = true;
                }
            }
        }

        return helper(sudoku, rows, columns, squares, 0, 0);
    }

    private boolean helper(int[][] sudoku, boolean[][] rows, boolean[][] columns, boolean[][] squares, int i, int j){
        if(j == 9){
            i++;
            j = 0;
        }

        for(int row = i; row < size; row++) {
            for (int column = j; column < size; column++) {
                if (sudoku[row][column] == 0) {
                    int squareId = (row % 3) * 3 + (column % 3);

                    for (int value = 0; value < size; value++) {
                        if (!rows[row][value] && !columns[column][value] && !squares[squareId][value]) {

                            sudoku[row][column] = value;
                            rows[row][value] = true;
                            columns[column][value] = true;
                            squares[squareId][value] = true;

                            if (helper(sudoku, rows, columns, squares, row, column + 1)) {
                                return true;
                            }

                            sudoku[row][column] = 0;
                            rows[row][value] = false;
                            columns[column][value] = false;
                            squares[squareId][value] = false;
                        }
                    }

                    return false;
                }
            }
        }

        return true;
    }
}
