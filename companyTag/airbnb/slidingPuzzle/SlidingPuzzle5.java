/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package companyTag.airbnb.slidingPuzzle;

import java.util.HashSet;
import java.util.Set;

/**
 * IDA* 算法， Iterative deepening A* algorithm， 是A*算法和 迭代加深算法的结合。
 * 迭代加深算法是在 DFS 上逐步加深搜索的深度。它避免了BFS占用搜索空间太大的确定，也减少了DFS的盲目性。
 * 
 * IDA* 的基本思路：
 * 首先将初始状态结点的H值设为阈值maxH，然后进行深度优先搜索，搜索过程中忽略所有H值大于maxH的结点（即剪枝）；如果没有找到解，则加大阈值maxH，
 * 再重复上述搜索，直到找到一个解。在保证H值的计算满足A*算法的要求下，可以证明找到的这个解一定是最优解。
 * 在程序实现上，IDA* 要比 A* 方便，因为不需要保存结点，不需要判重复，也不需要根据 H值对结点排序，占用空间小。而这里在IDA*算法中也使用合适的估价函数，来评估与目标状态的距离。
 *
 * 在一般的问题中是这样使用IDA*算法的，当前局面的估价函数值+当前的搜索深度 > 预定义的最大搜索深度时，就进行剪枝。
 *
 * 因为同样需要使用估计函数，我同样用了A*中的两种估计函数分别实现（设定的最大搜索深度为20，该值可以根据问题不同和需求不同进行修改，但如果设定太小小于了实际需要的最小步数，则无法找到最终解）。 ————————————————
 *
 * refer：https://blog.csdn.net/zhuiyisinian/article/details/108585591
 * 
 */
public class SlidingPuzzle5 {
    //board[i][j] will be a permutation of [0, 1, 2, 3, 4, 5].
    //the position index in puzzle is {{5, 4, 3}, {2, 1, 0}}
    final int[] MODS = {0x0000000f, 0x000000f0, 0x00000f00, 0x0000f000, 0x000f0000, 0x00f00000 }; 
    final int[][] NEXTS = { {1, 3}, {0, 2, 4}, {1, 5}, {0, 4}, {1, 3, 5}, {2, 4} };// when the 0 is in possition index 1, the next possible possition index are {0, 2, 4}

    int n = 2; //board.length;
    int m = 3; //board[0].length;

    final int END = 0x00123450;
    
    int bound;
    int steps;
        
    public int slidingPuzzle(int[][] board){
        //n = board.length;
        //m = board[0].length;
        
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

        if(start == END){
            return 0;
        }
        Node node = new Node(start, p);
        
        bound = node.estimate;
        steps = 0;
        
        while(!dfs(node, 0, p) && bound < 20){ // limit the max distance is 20  
            bound++;
        }
        
        return bound < 20 ? steps : -1;
    }    
    
    private boolean dfs(Node node, int steps, int lastP){
        if(node.estimate + steps > this.bound){
            return false;
        }
        
        if(node.state == END){
            this.steps = steps;
            return true;
        }
        
        int p = node.p;
        int newState;
        for(int newP : NEXTS[p]){
            if(newP == lastP){ //avoid to repeat
                continue;
            }
            
            newState = ((node.state & MODS[newP]) >> (newP << 2)) << (p << 2);
            newState |= (node.state & ~MODS[newP]);

            if(dfs(new Node(newState, newP), steps + 1, p)){
                return true;
            }
        }
        
        return false;
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
     * Manhattan distance
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
        int estimate; // distance from the current to the end.
        
        Node(int state, int p){
            this.state = state;
            this.p = p;

            this.estimate = estimate_Similarity(state);
        }
    }
}
