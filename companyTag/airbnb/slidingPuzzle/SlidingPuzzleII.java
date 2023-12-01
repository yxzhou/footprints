/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package companyTag.airbnb.slidingPuzzle;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import org.junit.Assert;
import util.Misc;

/**
 * _https://www.lintcode.com/problem/794
 *
 * On a 3x3 board, there are 8 tiles represented by the integers 1 through 8, and an empty square represented by 0.
 *
 * A move consists of choosing 0 and a 4-directionally adjacent number and swapping it.
 *
 * Given a puzzle board, return the least number of moves required so that the state of the board is solved. If it is
 * impossible for the state of the board to be solved, return -1.
 *
 * Notes:
 *   board will be a 3 x 3 array as described above. 
 *   board[i][j] will be a permutation of [0, 1, 2, 3, 4, 5, 6, 7, 8]. 
 * 
 * Thoughts:
 *   Example, the start state is {{2, 8, 3},{1, 0, 4},{7, 6, 5}}, the end state is {{1, 2, 3},{8, 0, 4},{7, 6, 5}
 *   to find the minimum distance. Shortest path between two nodes in graph. 
 *   V is the states, it can be serialized as a String or Integer (2d to 1d).  "283104765" 
 *   E is the movement of 0 to 4-directionally adjacent,  
 * 
 * 
 * Follow-up:
 *   how to optimize the memory? 
 *   can do it with A* ?
 * 
 */
public class SlidingPuzzleII {
    
    /* the position index in puzzle is 
     * {{0, 1, 2}, 
     *  {3, 4, 5}, 
     *  {6, 7, 8}}
     * when the 0 is in possition index 1, the next possible possition index are {0, 2, 4}
     */
    final int[][] NEXTS = { {1, 3}, {0, 2, 4}, {1, 5}, {0, 4, 6}, {1, 3, 5, 7}, {2, 4, 8}, {3, 7}, {4, 6, 8}, {5, 7} };
    
    /**
     * BFS, two-way search, one Queue and one Visited
     * 
     * @param board
     * @param target
     * @return the least number of moves required so that the state of the board is solved
     */
    public int slidingPuzzle(int[][] board, int[][] target) {        
        String start = parse(board);
        String end = parse(target);
        
        if(start.equals(end)){
            return 0;
        }
        
        //the number of reverse pairs of the board is b, the number of reverse pairs of the target is t
        //only when b and t both are even, or both are odd, the board can be solved.
        if( ((countReversePairs(start) ^ countReversePairs(end)) & 1) == 1){
            return -1;
        }
        
        Queue<String> open = new LinkedList<>();
        open.add(start);
        open.add(end);
        
        Map<String, Node> visited = new HashMap<>(); // store the states
        visited.put(start, new Node(start.indexOf("0"), 0, true));
        visited.put(end, new Node(end.indexOf("0"), 0, false));
        
        String currState; //current state
        String nextState; //next state
        Node currNode;
        Node nextNode;
        char[] curr; // current state
        int p;
        while(!open.isEmpty()){
            currState = open.poll();
            curr = currState.toCharArray();
            currNode = visited.get(currState);
            p = currNode.p;
            
            for(int newP : NEXTS[p]){
                //swap
                curr[p] = curr[newP];
                curr[newP] = '0';
                
                nextState = String.valueOf(curr);
                if( (nextNode = visited.get(nextState)) != null ){
                    if(nextNode.start2End != currNode.start2End){
                        return currNode.count + nextNode.count + 1;
                    }
                }else{
                    visited.put(nextState, new Node(newP, currNode.count + 1, currNode.start2End));
                    open.add(nextState);
                }
                
                //swap back
                curr[newP] = curr[p];
                curr[p] = '0';
            }
        }
        
        return -1;
    }
    
    /**
     * 
     * @param board
     * @return the reverse pairs, exclude the 0
     */
    private int countReversePairs(String state){                
        int count = 0;
                
        for(int i = state.length() - 1; i >= 0; i--){
            if( state.charAt(i) == '0' ){
                continue;
            }

            for(int j = i - 1; j >= 0; j--){
                count += state.charAt(j) > state.charAt(i) ? 1 : 0;
            }
        }
        
        return count;
    }
    
    private String parse(int[][] board){
        int n = board.length;
        int m = board[0].length;
        
        char[] data = new char[9];//n * m
        
        int k = 0;
        for(int r = 0; r < n; r++){
            for(int c = 0; c < m; c++){
                data[k++] = (char)('0' + board[r][c]);
            }
        }
        
        return String.valueOf(data);
    }
    
    class Node{
        int p; // the position index of 0 in current state
        int count; // distance from the start state or the end state to current state
        boolean start2End; // direction: true, from start to end; false, from end to start
        
        Node(int p, int count, boolean fromStartToEnd){
            this.p = p;
            this.count = count;
            this.start2End = fromStartToEnd;
        }
    }
    

    public static void main(String[] args){
        int[][][][] inputs = {
            //{ board, target, {{expect}} }
            {
                {
                    {2, 8, 3},
                    {1, 0, 4},
                    {7, 6, 5}
                },
                {
                    {1, 2, 3},
                    {8, 0, 4},
                    {7, 6, 5}
                },
                {{4}}
            },
            {
                {
                    {2, 3, 8},
                    {7, 0, 5},
                    {1, 6, 4}
                },
                {
                    {1, 2, 3},
                    {8, 0, 4},
                    {7, 6, 5}
                },
                {{-1}}
            }
        };
        
        SlidingPuzzleII sv = new SlidingPuzzleII();
        SlidingPuzzleII2 sv2 = new SlidingPuzzleII2();
        SlidingPuzzleII3 sv3 = new SlidingPuzzleII3();
        
        for(int[][][] input : inputs){
            System.out.println(Misc.array2String(input[0]));
            
            Assert.assertEquals("sv", input[2][0][0], sv.slidingPuzzle(input[0], input[1]));
            
            Assert.assertEquals("sv2", input[2][0][0], sv2.slidingPuzzle(input[0], input[1]));
            
            Assert.assertEquals("sv3", input[2][0][0], sv3.slidingPuzzle(input[0], input[1]));
        }
    }

}
