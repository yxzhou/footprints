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

/**
 * 
 * 拼图的数学定义
 * 在m*n*p(m,n>2,p>=1)的方块区域里，所有的方格两两不同，其中有一个特殊的方格，称为空穴，任何与之有邻面(二维时只须有邻边)的方块均可与之互换位
 * 置(一次这样的位置互换称为一次操作，也称为空穴的一次移动)。刚开始时随机产生杂乱的排列顺序，要求经过一系列操作后形成要求的排列顺序(目标排列)。
 *
 * 其实，拼图问题可以转化为这么一个问题：“任意给一个数字矩阵，能否证明：经过无限次的交换，一定能到达目标矩阵或者经过无限的交换也不能实现目标矩阵？”。
 *
 *
 * 逆序的定义：
 * 逆序是一个与排列相关的概念。
 * 由自然数1，2…，n组成的不重复的每一种有确定次序的排列，称为一个n级排列（简称为排列）；或者一般的，n个互不同元素排成一列称为“一个n级排列”。
 * 例如，1234和4312都是4级排列，而24315是一个5级排列。
 * 在一个n级排列中，如果一对数的前后位置与大小顺序相反，即前面的数大于后面的数，那么它们就称为一个“逆序”。
 *
 * 一个排列中逆序的总数就称为这个排列的逆序数。
 *
 * 逆序数为偶数的排列称为偶排列；逆序数为奇数的排列称为奇排列。如2431中，21，43，41，31是逆序，逆序数是4，为偶排列。
 *
 * 
 * 定理：
 *   1 交换一个排列中的两个数，则排列的奇偶性发生改变。
 *   2 对于任意 m * n 的情况，任意两个空穴在同一个位置且奇偶性相同的排列可以通过空穴移动相互转化。
 *     对于任意 m * n 的情况, 忽略两个空穴，奇偶性相同的排列可以通过空穴移动相互转化。
 */
public class Test {
    
    /**
     * copy from SlidingPuzzle, check rule #2
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
    System.out.println(String.format("start: [%s], %d, %d", getPuzzle(start), computeInversePairs(start, true), computeInversePairs(start, false) ));      
    System.out.println(String.format("  end: [%s], %d, %d", getPuzzle(end), computeInversePairs(end, true), computeInversePairs(end, false) ));  

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
                
    System.out.println(String.format("[%s], %d, %d", getPuzzle(state), computeInversePairs(state, true), computeInversePairs(state, false) ));            
                
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
        
        Test sv = new Test();
        
        for(int[][][] input : inputs){
            System.out.println("\nsteps: " + input[1][0][0] );
            //System.out.println(Misc.array2String(input[0]));
            //System.out.println( sv.slidingPuzzle(input[0]) );
            
            Assert.assertEquals("sv", input[1][0][0], sv.slidingPuzzle(input[0]));
            
        }
        
    }
    
    private int computeInversePairs(int state, boolean includeZero){        
        int mod = 0xf;
        int[] nums = new int[6];
        for(int i = 5; i >= 0; i--){
            nums[i] = state & mod;
            
            state >>= 4;
        }
        
        int count = 0;
        for(int i = 5; i >= 0; i--){
            if(!includeZero && nums[i] == 0 ){
                continue;
            }
                    
            for(int j = i - 1; j >= 0; j--){
                count += nums[j] > nums[i] ? 1 : 0;
            }
        }
        
        return count;
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
