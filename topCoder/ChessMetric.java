package topCoder;

import java.util.HashSet;
import java.util.Set;

/**
 *
 * Problem Statement
 * Suppose you had an n by n chess board and a super piece called a kingknight. Using only one move the kingknight denoted 'K' below can reach any of the spaces denoted 'X' or 'L' below:
 * .......
 * ..L.L..
 * .LXXXL.
 * ..XKX..
 * .LXXXL.
 * ..L.L..
 * .......
 * In other words, the kingknight can move either one space in any direction (vertical, horizontal or diagonally) or can make an 'L' shaped move.
 * An 'L' shaped move involves moving 2 spaces horizontally then 1 space vertically or 2 spaces vertically then 1 space horizontally.
 * In the drawing above, the 'L' shaped moves are marked with 'L's whereas the one space moves are marked with 'X's. In addition, a kingknight may never jump off the board.
 *
 * Given the size of the board, the start position of the kingknight and the end position of the kingknight,
 * your method will return how many possible ways there are of getting from start to end in exactly numMoves moves. start and finish are int[]s each containing 2 elements.
 * The first element will be the (0-based) row position and the second will be the (0-based) column position.
 * Rows and columns will increment down and to the right respectively. The board itself will have rows and columns ranging from 0 to size-1 inclusive.
 *
 * Note, two ways of getting from start to end are distinct if their respective move sequences differ in any way.
 * In addition, you are allowed to use spaces on the board (including start and finish) repeatedly during a particular path from start to finish.
 * We will ensure that the total number of paths is less than or equal to 2^63-1 (the upper bound for a long).
 *
 * Definition
 *
 * Class:	ChessMetric
 * Method:	howMany
 * Parameters:	int, int[], int[], int
 * Returns:	long
 * Method signature:	long howMany(int size, int[] start, int[] end, int numMoves)
 * (be sure your method is public)
 *
 *
 * Notes
 * -	For C++ users: long long is a 64 bit datatype and is specific to the GCC compiler.
 *
 * Constraints
 * -	size will be between 3 and 100 inclusive
 * -	start will contain exactly 2 elements
 * -	finish will contain exactly 2 elements
 * -	Each element of start and finish will be between 1 and size-1 inclusive
 * -	numMoves will be between 1 and 50 inclusive
 * -	The total number of paths will be at most 2^63-1.
 *
 * Examples
 * 0)
 * 3
 * {0,0}
 * {1,0}
 * 1
 * Returns: 1
 * Only 1 way to get to an adjacent square in 1 move
 *
 * 1)
 * 3
 * {0,0}
 * {1,2}
 * 1
 * Returns: 1
 * A single L-shaped move is the only way
 *
 * 2)
 * 3
 * {0,0}
 * {2,2}
 * 1
 * Returns: 0
 * Too far for a single move
 *
 * 3)
 * 3
 * {0,0}
 * {0,0}
 * 2
 * Returns: 5
 * Must count all the ways of leaving and then returning to the corner
 *
 * 4)
 * 100
 * {0,0}
 * {0,99}
 * 50
 * Returns: 243097320072600
 *
 *
 */

public class ChessMetric {

    public long howMany(int size, int[] start, int[] end, int numMoves){

        int[][] directions = {
                {1, 0}, {-1, 0}, {0, 1}, {0, -1}, //horizontal or vertical
                {1, 1}, {1, -1}, {-1, -1}, {-1, 1}, //diagonally
                {2, 1}, {2, -1}, {-2, 1}, {-2, -1}, {1, 2}, {1, -2}, {-1, 2}, {-1, -2} // L
        };


        long[][][] paths = new long[size][size][numMoves + 1];//define paths[x][y][m] as the path number at (x, y) after m moves
        paths[start[0]][start[1]][0] = 1;

        for(int m = 1; m <= numMoves; m++){
            for(int x = 0; x < size; x++){
                for(int y = 0; y < size; y++){
                    for(int[] direction : directions){
                        int newX = x + direction[0];
                        int newY = y + direction[1];

                        if(newX >= 0 && newX < size && newY >= 0 && newY < size){
                            paths[newX][newY][m] += paths[x][y][m - 1];
                        }
                    }
                }
            }
        }

        return paths[end[0]][end[1]][numMoves];
    }

    public long howMany_bfs(int size, int[] start, int[] end, int numMoves) {

        int[][] directions = {
                {1, 0}, {-1, 0}, {0, 1}, {0, -1}, //horizontal or vertical
                {1, 1}, {1, -1}, {-1, -1}, {-1, 1}, //diagonally
                {2, 1}, {2, -1}, {-2, 1}, {-2, -1}, {1, 2}, {1, -2}, {-1, 2}, {-1, -2} // L
        };

        long[][][] paths = new long[size][size][numMoves + 1];//define paths[x][y][m] as the path number at (x, y) after m moves
        paths[start[0]][start[1]][0] = 1;

        Set<String> positions = new HashSet<>();
        positions.add(start[0] + " " + start[1]);

        for(int m = 1; m <= numMoves; m++){
            Set<String> newPositions = new HashSet<>();
            for(String position : positions){
                String[] curr = position.split(" ");
                int x = Integer.parseInt(curr[0]);
                int y = Integer.parseInt(curr[1]);

                for(int[] direction : directions){
                    int newX = x + direction[0];
                    int newY = y + direction[1];

                    if(newX >= 0 && newX < size && newY >= 0 && newY < size){
                        paths[newX][newY][m] += paths[x][y][m - 1];

                        newPositions.add(newX + " " + newY);
                    }
                }
            }

            positions = newPositions;
        }

        return paths[end[0]][end[1]][numMoves];
    }


    public static void main(String[] args){
        int[][][] cases = {
                {{3}, {0, 0}, {1, 0}, {1}},
                {{3}, {0, 0}, {1, 2}, {1}},
                {{3}, {0, 0}, {2, 2}, {1}},
                {{3}, {0, 0}, {0, 0}, {2}},
                {{100}, {0, 0}, {0, 99}, {50}}
        };

        long[] expects = {
                1,
                1,
                0,
                5,
                243097320072600l
        };

        ChessMetric sv = new ChessMetric();

        for(int i = 0; i < cases.length; i++){
            long result1 = sv.howMany(cases[i][0][0], cases[i][1], cases[i][2], cases[i][3][0]);
            long result2 = sv.howMany_bfs(cases[i][0][0], cases[i][1], cases[i][2], cases[i][3][0]);

            System.out.println(String.format("\n %d: %d - %d \t %b", i, result1, result2, result1 == expects[i]));
        }
    }

}
