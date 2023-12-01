/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package companyTag.airbnb.slidingPuzzle;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

/**
 *
 * 
 */
public class SlidingPuzzle3 {
   /**
    *  BFS, two-way search
    * 
     * @param board: the given board
     * @return the least number of moves required so that the state of the board is solved
     */
    public int slidingPuzzle(int[][] board) {
        //board[i][j] will be a permutation of [0, 1, 2, 3, 4, 5].
        //the position index in puzzle is {{5, 4, 3}, {2, 1, 0}}
        final int[] MODS = {0x0000000f, 0x000000f0, 0x00000f00, 0x0000f000, 0x000f0000, 0x00f00000 }; 
        final int[][] NEXTS = { {1, 3}, {0, 2, 4}, {1, 5}, {0, 4}, {1, 3, 5}, {2, 4} };// when the 0 is in possition index 1, the next possible possition index are {0, 2, 4}
        
        Map<Integer, Node> visited = new HashMap<>();//map: key is the state, 
        
        final int N = 2; //board.length;
        final int M = 3; //board[0].length;

        int start = 0;
        int p = 0;
        for(int r = 0; r < N; r++){
            for(int c = 0; c < M; c++){
                if(board[r][c] == 0){
                    p = 5 - r * M - c; //(n - r) * m - c - 1;
                }
                start = (start << 4) | board[r][c];
            }
        }

        int end = 0x00123450;
        if(start == end){
            return 0;
        }
        
        visited.put(start, new Node(p, 0, true));
        visited.put(end, new Node(0, 0, false));

        //BFS
        Queue<Integer> states = new LinkedList<>();
        states.add(start);
        
        int state;
        int newState;
        Node node;
        Node newNode;        
        while(!states.isEmpty()){
            state = states.poll();
            node = visited.get(state);
            p = node.p;

            for(int newP : NEXTS[p]){

                newState = ((state & MODS[newP]) >> (newP << 2)) << (p << 2);
                newState |= (state & ~MODS[newP]);

                if( (newNode = visited.get(newState)) != null ){
                    if( node.start2End != newNode.start2End ){
                        return node.count + newNode.count + 1;
                    }
                }else{
                    visited.put(newState, new Node(newP, node.count + 1, node.start2End));
                    states.add(newState);
                }
  
            }
        }

        return -1;
    }
    
    class Node{
        int p; // the position index of 0
        int count; // distance from the start state or the end state to current
        boolean start2End; // true, from start to end; false, from end to start
        
        Node(int p, int count, boolean fromStartToEnd){
            this.p = p;
            this.count = count;
            this.start2End = fromStartToEnd;
        }
    }
}
