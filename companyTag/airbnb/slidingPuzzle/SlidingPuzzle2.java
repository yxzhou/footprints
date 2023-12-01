/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package companyTag.airbnb.slidingPuzzle;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

/**
 *  Cantor expansion, aka Cantor ternary function.
 *  Permutation to Index: Given n, and a sequence nums[0, ..., n - 1], and 1 <= nums[i] <= n, output the index of nums.
 *  康托展开的实质是计算当前排列在所有由小到大全排列中的顺序，因此是可逆的。
 * 
 *  Here it used to improve the space complexity, as a hash function
 * 
 */
public class SlidingPuzzle2 {
    
   /**
     * @param board: the given board
     * @return the least number of moves required so that the state of the board is solved
     */
    public int slidingPuzzle(int[][] board) {
        //board[i][j] will be a permutation of [0, 1, 2, 3, 4, 5].
        //the position index in puzzle is {{5, 4, 3}, {2, 1, 0}}
        final int[] mods = {0x0000000f, 0x000000f0, 0x00000f00, 0x0000f000, 0x000f0000, 0x00f00000 }; 
        final int[][] nexts = { {1, 3}, {0, 2, 4}, {1, 5}, {0, 4}, {1, 3, 5}, {2, 4} };// when the 0 is in possition index 1, the next possible possition index are {0, 2, 4}
        
        final int n = 2; //board.length;
        final int m = 3; //board[0].length;
        
        int[] visited = new int[720]; //to visited[k]: k is the state index, visited[k] is the position index of 0. total it's 6! 
        Arrays.fill(visited, -1);
        
        int start = 0;
        int p = 0;
        for(int r = 0; r < n; r++){
            for(int c = 0; c < m; c++){
                if(board[r][c] == 0){
                    p = 5 - r*m - c;
                }
                
                start = (start << 4) | board[r][c];
            }
        }
        visited[cantorHash(start)] = p;
        
        final int end = 0x00123450;
        if(start == end){
            return 0;
        }
        
        Queue<Integer> states = new LinkedList<>();
        states.add(start);
        
        //BFS
        int count = 0;
        
        int state;
        int newState;
        int hashcode;
        while(!states.isEmpty()){
            count++;
            
            for(int i = states.size(); i > 0; i--  ){
                state = states.poll();
                p = visited[cantorHash(state)];
                
                for(int newP : nexts[p]){
                    newState = ((state & mods[newP]) >> (newP << 2)) << (p << 2);
                    newState |= state & (~mods[newP]);
                    
                    if(newState == end){
                        return count;
                    }
                    
                    hashcode = cantorHash(newState);
                    if(visited[hashcode] == -1 ){
                        visited[hashcode] = newP;
                        states.add(newState);
                    }
                }
            }
        }
        
        return -1;
    }
    
    
    private int cantorHash(int binaryString){
        int hashcode = 0;
        
        boolean[] found = new boolean[6]; //default all are false
        
        int mod = 0xf;
        int curr;
        int count;
        int factor = 1;
        for(int k = 0; k < 6; ){
            curr = binaryString & mod;
            binaryString >>= 4;
            found[curr] = true;
            
            count = 0;
            for(int i = 0; i < curr; i++){
                count += found[i] ? 1 : 0;
            }
            
            hashcode += count * factor;
            factor *= ++k;
        }
        
        return hashcode;
    }
}
