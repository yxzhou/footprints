/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package companyTag.airbnb.slidingPuzzle;

import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;

/**
 * A* (A-Star) Algorithm 是一种静态路网中求解最短路径最有效的方法。
 * 
 * 普通的BFS是遍历所有可能，在搜索方向上是盲目的，随着搜寻的深度加大，遍历的节点2^depth增加。 
 * A* 算法里 估算总距离f(n) = g(n) + h(n) , g(n) is the distance from the start state to curr state. h(n) estimates the 
 * distance from curr state to the end state. 搜索方向上优先f(n)小的。
 * 
 * A*算法的关键是 h(n)，这个启发式函数（heuristic function）。不好的估计方法，即影响搜寻效率，也影响最终的准确度。
 * 选择一个适当的估计方法，是个难点。
 * 
 * A*算法 不能保证找到一条最短路径，因为不确定 heuristic funciton 足够好，换来的优点是 它比Dijkstra算法快的多。
 * 
 */
public class SlidingPuzzle4 {
    
    /**
     * A* algorithm 
     *
     * @param board: the given board
     * @return the least number of moves required so that the state of the board is solved
     */
    public int slidingPuzzle(int[][] board) {
        
        final int N = 2; //board.length;
        final int M = 3; //board[0].length;
        
        final int END = 0x00123450;

        int start = 0;
        int[] digits = new int[N * M]; 
        int p = 0;
        for(int r = 0; r < N; r++){
            for(int c = 0; c < M; c++){
                if(board[r][c] == 0){
                    p = 5 - r * M - c; //(n - r) * m - c - 1;
                }
                
                start = (start << 4) | board[r][c];
                digits[r * M + c] = board[r][c];
            }
        }

        if(start == END){
            return 0;
        }
        
        //the number of reverse pairs of the END is 0, only when this board's reverse pairs is even, it's solved
        if( (countReversePairs(digits) & 1) == 1 ){
            return -1;
        }
        
        //board[i][j] will be a permutation of [0, 1, 2, 3, 4, 5].
        //the position index in puzzle is {{5, 4, 3}, {2, 1, 0}}
        final int[] MODS = {0x0000000f, 0x000000f0, 0x00000f00, 0x0000f000, 0x000f0000, 0x00f00000 }; 
        final int[][] NEXTS = { {1, 3}, {0, 2, 4}, {1, 5}, {0, 4}, {1, 3, 5}, {2, 4} };// when the 0 is in possition index 1, the next possible possition index are {0, 2, 4}
        
        Set<Integer> visited = new HashSet<>();//Set<state>, 
        visited.add(start);
        
        //A* 
        Queue<Node> heap = new PriorityQueue<>((a, b) -> Integer.compare(a.count + a.estimate, b.count + b.estimate) );
        heap.add(new Node(start, p, 0));

        Node node;
        int newState;
        while(!heap.isEmpty()){
            node = heap.poll();
            
            if(node.state == END){
                return node.count;
            }
            
            p = node.p;
            for(int newP : NEXTS[p]){
                newState = ((node.state & MODS[newP]) >> (newP << 2)) << (p << 2);
                newState |= (node.state & ~MODS[newP]);
                
                if(!visited.contains(newState)){
                    visited.add(newState);
                    heap.add(new Node(newState, newP, node.count + 1));
                }
            }
            
        }
        
        return -1;
    }
    
    /**
     * 
     * @param board
     * @return the reverse pairs, exclude the 0
     */
    private int countReversePairs(int[] board){                
        int count = 0;
                
        for(int i = board.length - 1; i >= 0; i--){
            if( board[i] == 0 ){
                continue;
            }

            for(int j = i - 1; j >= 0; j--){
                count += board[j] > board[i] ? 1 : 0;
            }
        }
        
        return count;
    }
    
    /**
     * similarity of curr and end, 
     * 
     * @param curr
     * @param end
     * @return 
     */
    private int estimate_Similarity(int curr){
        int diff = 0;
        
        int mod = 0xf;
        int last;
        for(int k = 0; k < 6; k++){
            //diff += ((curr & mod) == (end & mod) ? 0 : 1); //Note: this h(n) does not work for all cases 
            diff += ((last = curr & mod) == 0 || last == (6 - k)%6 ? 0 : 1);
            
            curr >>= 4;
        }
        
        return diff;
    }
    
    /**
     * Manhattan distance in 1D
     * The end is 0x00123450;
     * 
     * @param curr
     * @return 
     */
    private int estimate_Manhattan(int curr){
        int diff = 0;
        
        int mod = 0xf;
        for(int k = 0; k < 6; k++){
            diff += (6 - (curr & mod)) % 6 - k ;
            
            curr >>= 4;
        }
        
        return diff;
    }
    
    class Node{
        int state; 
        int p; // the position index of 0
        int count;// distance from the start state to current.
        int estimate; // distance from the current to the end.
        
        Node(int state, int p, int count){
            this.state = state;
            this.p = p;
            this.count = count;
            this.estimate = estimate_Manhattan(state);
        }
    }
    
}
