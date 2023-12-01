/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package companyTag.airbnb.slidingPuzzle;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;
import java.util.Queue;
import org.junit.Assert;
import util.Misc;

/**
 * _
 * 
 * 
 */
public class SlidingPuzzleII2 {
    
    /* the position index in puzzle is 
     * {{0, 1, 2}, 
     *  {3, 4, 5}, 
     *  {6, 7, 8}}
     * when the 0 is in possition index 1, the next possible possition index are {0, 2, 4}
     */
    final int[][] NEXTS = { {1, 3}, {0, 2, 4}, {1, 5}, {0, 4, 6}, {1, 3, 5, 7}, {2, 4, 8}, {3, 7}, {4, 6, 8}, {5, 7} };
    
    /**
     * BFS, two-way search, two Queue and two Visited
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
        
        Queue<String> startQueue = new LinkedList<>();
        Queue<String> endQueue = new LinkedList<>();
        startQueue.add(start);
        endQueue.add(end);
        
        Set<String> startVisited = new HashSet<>(); // store the states from start side
        Set<String> endVisited = new HashSet<>(); // store the states from end side
        startVisited.add(start);
        endVisited.add(end);
        
        int count = 0;
        while(!startQueue.isEmpty() && !endQueue.isEmpty()){
            count++;
            if(move(startQueue, startVisited, endVisited)){
                return count;
            }
            
            count++;
            if(move(endQueue, endVisited, startVisited)){
                return count;
            }
        }
        
        return -1;
    }
    
    private boolean move(Queue<String> queue, Set<String> visited, Set<String> otherVisited){
        
        String curr; //curr state
        char[] arr;
        String next; // next state
        int p = 0;
        for(int i = queue.size(); i > 0; i--){
            curr = queue.poll();
            arr = curr.toCharArray();
            p = curr.indexOf("0");
            
            for(int newP : NEXTS[p]){
                //swap
                arr[p] = arr[newP];
                arr[newP] = '0';
                
                next = String.valueOf(arr);
                if(otherVisited.contains(next)){
                    return true;
                }else if(!visited.contains(next)) {
                    visited.add(next);
                    queue.add(next);
                }
                
                //swap back
                arr[newP] = arr[p];
                arr[p] = '0';
                
            }
        }
        
        return false;
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
        
        SlidingPuzzleII2 sv = new SlidingPuzzleII2();
        
        for(int[][][] input : inputs){
            System.out.println(Misc.array2String(input[0]));
            
            Assert.assertEquals("sv", input[2][0][0], sv.slidingPuzzle(input[0], input[1]));
        }
    }

}
