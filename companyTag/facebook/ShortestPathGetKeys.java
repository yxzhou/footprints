package companyTag.facebook;

import junit.framework.Assert;
import org.junit.Test;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

/**
 *
 * We are given a 2-dimensional grid. "." is an empty cell, "#" is a wall, "@" is the starting point, ("a", "b", ...) are keys, and ("A", "B", ...) are locks.

 We start at the starting point, and one move consists of walking one space in one of the 4 cardinal directions.  We cannot walk outside the grid, or walk into a wall.  If we walk over a key, we pick it up.  We can't walk over a lock unless we have the corresponding key.

 For some 1 <= K <= 6, there is exactly one lowercase and one uppercase letter of the first K letters of the English alphabet in the grid.  This means that there is exactly one key for each lock, and one lock for each key; and also that the letters used to represent the keys and locks were chosen in the same order as the English alphabet.

 Return the lowest number of moves to acquire all keys.  If it's impossible, return -1.


 Example 1:
 Input: ["@.a.#","###.#","b.A.B"]
 Output: 8

 Example 2:
 Input: ["@..aA","..B#.","....b"]
 Output: 6


 Note:
 1 <= grid.length <= 30
 1 <= grid[0].length <= 30
 grid[i][j] contains only '.', '#', '@', 'a'-'f' and 'A'-'F'
 The number of keys is in [1, 6].  Each key has a different letter and opens exactly one lock.
 *
 * Solutions:
 *   1) to get teh shortest path, it's BFS
 *   2) key <= 6,  it can use a 6 bits for how many keys it got.
 *   3) it need a queue for BFS, the every step's status includes: where (rowId, columnId) and key status
 *
 */

public class ShortestPathGetKeys {


    public int shortestPathAllKeys(String[] grid){
        if(null == grid){
            return -1;
        }

        int rowSize = grid.length;
        int columnSize = grid[0].length();

        //find the start point and allKeyStatus
        int allKeyStatus = 0;
        int rowId = -1;
        int columnId = -1;
        for(int i = 0; i < rowSize; i++){
            char[] currRow = grid[i].toCharArray();
            for(int j = 0; j < columnSize; j++){
                if(currRow[j] >= 'a' && currRow[j] <= 'f'){
                    allKeyStatus |= ( 1 << (currRow[j] - 'a'));
                }else if(currRow[j] == '@'){
                    rowId = i;
                    columnId = j;
                }
            }
        }

        // when not found the start point or any keys, return -1
        if(rowId == -1 || allKeyStatus == 0){
            return -1;
        }

        //BFS,
        int[][] diffs = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};
        Queue<String> queue = new LinkedList<>();
        Set<String> visited = new HashSet<>();
        String startStatus = rowId + " " + columnId + " " + 0;
        queue.add(startStatus);
        visited.add(startStatus);

        String curr;
        int key;
        int stepCounter = 0;
        while(!queue.isEmpty()){
            stepCounter++;

            for(int i = queue.size(); i > 0; i--) {
                curr = queue.poll();

                String[] tokens = curr.split(" ");
                rowId = Integer.valueOf(tokens[0]);
                columnId = Integer.valueOf(tokens[1]);
                key = Integer.valueOf(tokens[2]);

                for (int[] diff : diffs) {
                    int nextRowId = rowId + diff[0];
                    int nextColumnId = columnId + diff[1];
                    int nextKey = key;

                    if (nextRowId < 0 || nextRowId >= rowSize || nextColumnId < 0 || nextColumnId >= columnSize) {
                        continue;
                    }

                    char nextChar = grid[nextRowId].charAt(nextColumnId);
                    if (nextChar == '#') {
                        continue;
                    } else if (nextChar >= 'a' && nextChar <= 'f') {
                        nextKey |= (1 << (nextChar - 'a'));

                        if (nextKey == allKeyStatus) {
                            return stepCounter;
                        }
                    } else if (nextChar >= 'A' && nextChar <= 'F' && ((nextKey >> (nextChar - 'A')) & 1) != 1) {
                        continue;
                    }

                    String nextStatus = nextRowId + " " + nextColumnId + " " + nextKey;
                    if (!visited.contains(nextStatus)) {
                        visited.add(nextStatus);
                        queue.add(nextStatus);
                    }
                }
            }
        }

        return -1;
    }

    @Test public void test(){
        Assert.assertEquals(8, shortestPathAllKeys(new String[]{"@.a.#","###.#","b.A.B"}));
        Assert.assertEquals(6, shortestPathAllKeys(new String[]{"@..aA","..B#.","....b"}));
    }
}
