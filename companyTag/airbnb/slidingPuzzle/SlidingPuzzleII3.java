/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package companyTag.airbnb.slidingPuzzle;

import static java.nio.file.Files.move;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.Queue;
import org.junit.Assert;
import util.Misc;

/**
 * _
 * 
 * 
 */
public class SlidingPuzzleII3 {
    
    /* the position index in puzzle is 
     * {{0, 1, 2}, 
     *  {3, 4, 5}, 
     *  {6, 7, 8}}
     * when the 0 is in possition index 1, the next possible possition index are {0, 2, 4}
     */
    final int[][] NEXTS = { {1, 3}, {0, 2, 4}, {1, 5}, {0, 4, 6}, {1, 3, 5, 7}, {2, 4, 8}, {3, 7}, {4, 6, 8}, {5, 7} };
    
    /**
     * A*
     * 
     * @param board
     * @param target
     * @return the least number of moves required so that the state of the board is solved
     */
    public int slidingPuzzle(int[][] board, int[][] target) {        
        String start = getState(board);
        String end = getState(target);
        
        if(start.equals(end)){
            return 0;
        }
        
        //the number of reverse pairs of the board is b, the number of reverse pairs of the target is t
        //only when b and t both are even, or both are odd, the board can be solved.
        if( ((countReversePairs(start) ^ countReversePairs(end)) & 1) == 1){
            return -1;
        }
        
        int[][] positions = getPosition(target);
        
        Set<String> visited = new HashSet<>();//Set<state>
        visited.add(start);
        
        Queue<Node> heap = new PriorityQueue<>((a, b) -> Integer.compare(a.estimate, b.estimate));
        heap.add(new Node(start, start.indexOf("0"), 0, estimate_Manhattan(start, positions)));
        
        Node curr;
        int p;
        char[] arr;
        String next; // next state
        while(!heap.isEmpty()){
            curr = heap.poll();
            arr = curr.state.toCharArray();
            p = curr.p;
            
            for(int newP : NEXTS[p]){
                //swap
                arr[p] = arr[newP];
                arr[newP] = '0';
                
                next = String.valueOf(arr);
                
                if(!visited.contains(next)){
                    if(next.equals(end)){
                        return curr.count + 1;
                    }
                
                    visited.add(next);
                    heap.add(new Node(next, newP, curr.count + 1, estimate_Manhattan(next, positions)));
                }
                
                //swap back
                arr[newP] = arr[p];
                arr[p] = '0';
                
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
    
    /**
     * 
     * @param board
     * @return the position {row, col} of every number
     */
    private int[][] getPosition(int[][] board){
        int[][] result = new int[9][];
        
        for(int r = 0, n = board.length, m = board[0].length; r < n; r++){
            for(int c = 0; c < m; c++){
                result[board[r][c]] = new int[]{r, c};
            }
        }
        
        return result;
    }
    
    private String getState(int[][] board){
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
    
    private int estimate_Manhattan(String state, int[][] positions){
        int result = 0;
        
        int r;
        int c;
        int[] pos;
        for(int p = state.length() - 1; p >= 0; p--){
            r = p / 3; 
            c = p % 3;
            
            pos = positions[state.charAt(p) - '0'];
            result += Math.abs(r - pos[0]) + Math.abs(c - pos[1]);
        }
        
        return result;
    }
    
    class Node{
        String state; 
        int p; // the position index of 0
        int count;// distance from the start state to current.
        int estimate; // distance from the start to the end.
        
        Node(String state, int p, int count, int estimate){
            this.state = state;
            this.p = p;
            this.count = count;
            this.estimate = count + estimate;
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
        
        SlidingPuzzleII3 sv = new SlidingPuzzleII3();
        
        for(int[][][] input : inputs){
            System.out.println(Misc.array2String(input[0]));
            
            Assert.assertEquals("sv", input[2][0][0], sv.slidingPuzzle(input[0], input[1]));
        }
    }

}
