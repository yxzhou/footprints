package fgafa.matrix;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class IslandsII {

	/**
	 * 
	 * A 2d grid map of m rows and n columns is initially filled with water. 
	 * We may perform an addLand operation which turns the water at position (row, col) into a land. 
	 * Given a list of positions to operate, count the number of islands after each addLand operation. 
	 * An island is surrounded by water and is formed by connecting adjacent lands horizontally or vertically. 
	 * You may assume all four edges of the grid are all surrounded by water.
	 * 
	 * Example:
    Given m = 3, n = 3, positions = [[0,0], [0,1], [1,2], [2,1]].
    
    Initially, the 2d grid grid is filled with water. (Assume 0 represents water and 1 represents land).
    0 0 0
    0 0 0
    0 0 0
    
    Operation #1: addLand(0, 0) turns the water at grid[0][0] into a land.
    1 0 0
    0 0 0   Number of islands = 1
    0 0 0
    
    Operation #2: addLand(0, 1) turns the water at grid[0][1] into a land.
    1 1 0
    0 0 0   Number of islands = 1
    0 0 0
    
    Operation #3: addLand(1, 2) turns the water at grid[1][2] into a land.
    1 1 0
    0 0 1   Number of islands = 2
    0 0 0
    
    Operation #4: addLand(2, 1) turns the water at grid[2][1] into a land.
    1 1 0
    0 0 1   Number of islands = 3
    0 1 0
    
    We return the result as an array: [1, 1, 2, 3]
    
    Challenge:
    Can you do it in time complexity O(k log mn), where k is the length of the positions?

     tags:  union find

	 */
    public List<Integer> numIslands2(int colNum,
                                     int rowNum,
                                     Point[] operators) {
        List<Integer> result = new ArrayList<Integer>();

        // check
        if (null == operators) {
            return result;
        }

        int[] parentIds = new int[colNum * rowNum]; // default all are 0

        int count = 0;
        int groupId = 0;

        int[][] neighbors = { {1, 0}, {-1, 0}, {0, 1}, {0, -1} };
        for (Point p : operators) {

            if (p.x < 0 && p.x >= colNum && p.y < 0 && p.y >= rowNum) {
                continue;
            }

            // count++ when there is no adjacent islands, count decrease based
            // on adjacent islands group
            if (parentIds[p.x * p.y] == 0) {

                // quick union with path compression
                Set<Integer> set = new HashSet<Integer>(4);

                for (int i = 0; i < 4; i++) {
                    int x = p.x + neighbors[i][0];
                    int y = p.y + neighbors[i][1];
                    if (0 <= x && x < colNum && 0 <= y && y < rowNum
                                && parentIds[x * y] > 0) {
                        set.add(parentIds[x * y]);
                    }
                }

                count += 1 - set.size();
                groupId++;
                parentIds[p.x * p.y] = groupId;

                // union
                if (!set.isEmpty()) {
                    dfs(parentIds, p.x, p.y, parentIds[p.x][p.y]);
                }
            }

            result.add(count);
        }

        return result;
    }

    private void union(){

    }

    private void findRoot(int p, int[] parentIds){
        while(parentIds[p] != p
    }

    private void dfs(int[][] matrix,
                     int x,
                     int y,
                     int value) {
        if (x < 0 || x >= matrix.length || y < 0 || y >= matrix[0].length) {
            return;
        }

        if (matrix[x][y] > 0 && matrix[x][y] != value) {
            matrix[x][y] = value;
            dfs(matrix, x - 1, y, value);
            dfs(matrix, x + 1, y, value);
            dfs(matrix, x, y - 1, value);
            dfs(matrix, x, y + 1, value);
        }
    }
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	/**
	 * Definition for a point.
	 */
	class Point {
		int x;
		int y;

		Point() {
			x = 0;
			y = 0;
		}

		Point(int a, int b) {
			x = a;
			y = b;
		}
	}

}
