/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package companyTag.airbnb.slidingPuzzle;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import junit.framework.Assert;

import util.Misc;

/**
 * _https://www.lintcode.com/problem/941
 * 
 * On a 2x3 board, there are 5 tiles represented by the integers 1 through 5, and an empty square represented by 0.
 *
 * A move consists of choosing 0 and a 4-directionally adjacent number and swapping it.
 *
 * The state of the board is solved if and only if the board is [[1,2,3],[4,5,0]].
 *
 * Given a puzzle board, return the least number of moves required so that the state of the board is solved. If it is
 * impossible for the state of the board to be solved, return -1.
 *
 * Notes:
 *   board will be a 2 x 3 array as described above. 
 *   board[i][j] will be a permutation of [0, 1, 2, 3, 4, 5]. 
 * 
 * 
 * Example 1:
 * Given board = [[1,2,3],[4,0,5]], 
 * return 1.
 * Explanation:  Swap the 0 and the 5 in one move.
 * 
 * Example 2：
 * Given board = [[1,2,3],[5,4,0]], 
 * return -1.
 * Explanation:  No number of moves will make the board solved.
 * 
 * Thoughts:
 *   to Example #1, the start state is [[1,2,3],[4,0,5]], the end state is [[1,2,3],[4,5,0]]
 *   to find the minimum distance. Shortest path between two nodes in graph. 
 *   V is the states, it can be serialized as a String or Integer (2d to 1d).  "123450" or 0x00123450
 *   E is the movement of 0 to 4-directionally adjacent,  
 *  
 *   Refer to Test, because the reverse pairs of the end [[1,2,3],[4,5,0]] is 0. 
 *   Only when the reverse pairs of the start is even, it's possible for the board to be solved.   
 * 
 *   s1: BFS, one-way search,  Queue<state> + Map<state> visited 
 *   s2: improve on s1,  Queue<state> + array[state] visited 
 *   s3: BFS, two-way search, 
 *   s4: A* (A-Star) 
 *   s5: IDA*算法 
 * 
 * 
 * 
 */
public class SlidingPuzzle {
    
    /**
     * BFS, one-way search
     *
     * @param board: the given board
     * @return the least number of moves required so that the state of the board is solved
     */
    public int slidingPuzzle(int[][] board) {
        //the position index in puzzle is {{5, 4, 3}, {2, 1, 0}}
        int[] mods = {0x0000000f, 0x000000f0, 0x00000f00, 0x0000f000, 0x000f0000, 0x00f00000 }; 
        //int[] diffs = {3, -3, 1, -1};//{up, down, left, right} //wrong 
        int[][] diffs = { {1, 3}, {0, 2, 4}, {1, 5}, {0, 4}, {1, 3, 5}, {2, 4} };// when the 0 is in possition index 1, the next possible possition index are {0, 2, 4}
        
        Map<Integer, Integer> visited = new HashMap<>();//map<state, the position index of 0>, 
        
        int n = 2; //board.length;
        int m = 3; //board[0].length;

        int start = 0;
        int p = 0;
        for(int r = 0; r < n; r++){
            for(int c = 0; c < m; c++){
                if(board[r][c] == 0){
                    p = 5 - r * m - c; //(n - r) * m - c - 1;
                }
                start = (start << 4) | board[r][c];
            }
        }
        visited.put(start, p);

        int end = 0x00123450;
        if(start == end){
            return 0;
        }

        //BFS
        int count = 0;
        Queue<Integer> states = new LinkedList<>();
        states.add(start);

        int state;
        int newState;
        while(!states.isEmpty()){
            count++;

            for(int i = states.size(); i > 0; i--){
                state = states.poll();
                p = visited.get(state);
                
                for(int newP : diffs[p]){
                    
                    newState = ((state & mods[newP]) >> (newP << 2)) << (p << 2);
                    newState |= (state & ~mods[newP]);

                    if(newState == end){
                        return count;
                    }
                    
                    if(!visited.containsKey(newState)){
                        visited.put(newState, newP);
                        states.add(newState);
                    }
                }    
            }
        }

        return -1;
    }
    
    public static void main(String[] args){
        int[][][][] inputs = {
            //{board, {{expect}}}
            {   
                {
                    {1,2,3},
                    {4,0,5}
                },
                {{1}}
            },
            {   
                {
                    {1,2,3},
                    {5,4,0}
                },
                {{-1}}
            },
            {   
                {
                    {4,1,2},
                    {5,0,3}
                },
                {{5}}
            },
            {
                {
                    {3,2,4},
                    {1,5,0}
                },
                {{14}}
            },
            {
                {
                    {3,2,1},
                    {5,0,4}
                },
                {{19}}
            }
            
        };
        
        SlidingPuzzle sv = new SlidingPuzzle();
        SlidingPuzzle2 sv2 = new SlidingPuzzle2();
        SlidingPuzzle3 sv3 = new SlidingPuzzle3();
        SlidingPuzzle4 sv4 = new SlidingPuzzle4();
        SlidingPuzzle5 sv5 = new SlidingPuzzle5();
        
        for(int[][][] input : inputs){
            System.out.println(Misc.array2String(input[0]));
            //System.out.println( sv.slidingPuzzle(input[0]) );
            
            Assert.assertEquals("sv", input[1][0][0], sv.slidingPuzzle(input[0]));
            
            Assert.assertEquals("sv2", input[1][0][0], sv2.slidingPuzzle(input[0]));
            
            Assert.assertEquals("sv3", input[1][0][0], sv3.slidingPuzzle(input[0]));
            
            Assert.assertEquals("sv4", input[1][0][0], sv4.slidingPuzzle(input[0]));
            
            Assert.assertEquals("sv5", input[1][0][0], sv5.slidingPuzzle(input[0]));
        }
        
    }
    
    private String getPuzzle(int binaryString){
        int[] result = new int[6];
        
        int mod = 0xf;
                
        for(int p = 5; p >= 0; p--){
            result[p] = binaryString & mod;
            
            binaryString >>= 4;
        }

        
        return Arrays.toString(result);
    }
    
}
