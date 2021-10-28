/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dfsbfs.permutationAndCombination;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import util.Misc;

/**
 *
 * Given a list of numbers, return all possible permutations of it.
 * You can assume that there is no duplicate numbers in the list.
 *  
 * Example 1:
 * Input: list = [1]
 * Output:
 * [
 *   [1]
 * ]
 * 
 * Example 2:
 * Input: [1,2,3]
 * Output:
 * [
 *   [1,2,3],
 *   [1,3,2],
 *   [2,1,3],
 *   [2,3,1],
 *   [3,1,2],
 *   [3,2,1]
 * ]
 * 
 * Challenge: Can you do it in both recursively and non-recursively?
 * 
 * Solutions:
 *   1) recursive,  DFS   for example [1, 2, 3]
 *      dfs( nums = [1, 2, 3], i = 0 )  swap every element [0, nums.length) with nums[0], 
 *          dfs( nums = [1, 2, 3], i = 1 )  dfs( nums = [2, 1, 3], i = 1 )  dfs( nums = [3, 2, 1], i = 1 )
 * 
 *          dfs( nums = [1, 2, 3], i = 1 )  swap every element [1, nums.length) with nums[1],    
 *              dfs( nums = [1, 2, 3], i = 2 )  dfs( nums = [1, 3, 2], i = 2)
 *     
 *              dfs( nums = [1, 2, 3], i = 2 ) swap every element [2, nums.length) with nums[2], 
 *              dfs( nums = [1, 2, 3], i = 3 )
 * 
 *              dfs( nums = [1, 2, 3], i = 3 )  because i == nums.length, add [1,2,3] into the result list
 *      
 *          dfs( nums = [2, 1, 3], i = 1 )  swap every element [1, nums.length) with nums[1], 
 *              dfs( nums = [2, 1, 3], i = 2 ) dfs( nums = [2, 3, 1], i = 2 )
 *              ...
 * 
 *   2) non-recursively, 
 *   2.1) insert, BFS  for example [1, 2, 3]
 *       Step 0, init a queue, with adding a empty list. 
 *       Step 1, insert 1:  [1]
 *       Step 2, insert 2:  [12], [21]   ( insert 2 after 1, got [12]; insert 2 before 1, got [21];  )
 *       Step 3, insert 3:  [123], [132], [312], [213] [231], [321]
 *   
 *   2.2) mod, 
 * 
 */
public class Permutations {
   
   /* 
    * 全排列（递归交换）算法 1、将第一个位置分别放置各个不同的元素； 2、对剩余的位置进行全排列（递归）； 3、递归出口为只对一个元素进行全排列。
    * e.g. input [1,2,3],  output 3!/2! 
    * Step 1) select 1, swap 1-1, get [1], remain (2, 3) for next select
    *  1.1) select 2, swap 2-2, get [1, 2]
    *  1.2) select 3, swap 2-3, get [1, 3]  
    * Step 2) select 2, swap 1-2, get [2], remain (1, 3) for next select
    *  2.1) select 1, swap 2-1, get [2, 1]
    *  2.1) select 3, swap 2-3, get [2, 3]
    * Step 3) select 3, swap 1-3, get [3], remain (1, 2) for next select
    *  3.1) select 1, swap 3-1, get [3, 1]
    *  3.1) select 2, swap 3-2, get [3, 2]
    * 
    *  refer to CareerCup 2.3
    */
    public List<List<Integer>> permute_recursive(int[] num) {
        if (null == num) {
            return Collections.EMPTY_LIST;
        }

        List<List<Integer>> result = new ArrayList<>();
        dfs(num, 0, result);

        return result;
    }

    private void dfs(int[] num, int i, List<List<Integer>> result) {
        if (i == num.length) {
            List<Integer> tmp = new ArrayList<>(num.length);
            for (int j : num) {
                tmp.add(j);
            }
            result.add(tmp);
            return;
        }

        for (int j = i; j < num.length; j++) {
            swap(num, j, i);
            dfs(num, i + 1, result);
            swap(num, i, j);
        }
    }

    private void swap(int[] num, int i, int j) {
        if(i == j){
            return;
        }
        
        int tmp = num[i];
        num[i] = num[j];
        num[j] = tmp;
    }


    public List<List<Integer>> permute_insert(int[] nums) {
        if(nums == null){
            return Collections.EMPTY_LIST;
        }

        Queue<List<Integer>> queue = new LinkedList<>();
        queue.add(new ArrayList<>());

        List<Integer> top;
        for(int i = 0; i < nums.length; i++){
            for(int j = queue.size(); j > 0; j--){
                top = queue.poll();

                for(int k = top.size(); k >= 0; k-- ){
                    List<Integer> next = new ArrayList<>(top);
                    next.add(k, nums[i]);

                    queue.add(next);
                }
            }
        }

        return new ArrayList<>(queue);
    }
    
  /**
   * 全排列（非递归求模）算法 
   * 1、初始化存放全排列结果的数组result，与原数组的元素个数相等； 
   * 2、计算n个元素全排列的总数，即n!；
   * 3、从>=0的任意整数开始循环n!次，每次累加1，记为index；
   * 4、取第1个元素arr[0]，求1进制的表达最低位，即求index模1的值w，将第1个元素（arr[0]）插入result的w位置，并将index迭代为index/1；
   * 5、取第2个元素arr[1]，求2进制的表达最低位，即求index模2的值w，将第2个元素（arr[1]）插入result的w位置，并将index迭代为index/2；
   * 6、取第3个元素arr[2]，求3进制的表达最低位，即求index模3的值w，将第3个元素（arr[2]）插入result的w位置，并将index迭代为index/3；
   * 7、…… 8、直到取最后一个元素arr[arr.length-1]，此时求得一个排列； 
    9、当index循环完成，便求得所有排列。 例：
   * 求4个元素["a", "b", "c", "d"]的全排列,
   * 共循环4!=24次，可从任意>=0的整数index开始循环，每次累加1，直到循环完index+23后结束；
   * 假设index=13（或13+24，13+2*24，13+3*24…），因为共4个元素，故迭代4次，则得到的这一个排列的过程为：
   * 第1次迭代，13/1，商=13，余数=0，故第1个元素插入第0个位置（即下标为0），得["a"]； 第2次迭代，13/2,
   * 商=6，余数=1，故第2个元素插入第1个位置（即下标为1），得["a", "b"]； 第3次迭代，6/3,
   * 商=2，余数=0，故第3个元素插入第0个位置（即下标为0），得["c", "a", "b"]； 第4次迭代，2/4，商=0，余数=2,
   * 故第4个元素插入第2个位置（即下标为2），得["c", "a", "d", "b"]；
   */

    public List<List<Integer>> permute_Mod(int[] nums) {
        List<List<Integer>> result = new ArrayList<>();
        
        int n = nums.length;
        int[] curr = new int[n];

        int f = 1; //factorial
        for (int i = 2; i <= n; i++) {
            f *= i;
        }

        int t;
        int w;
        for (int index = 0; index < f; index++) {
            t = index;
            for (int i = 1; i <= n; i++) {
                w = t % i;
                
                System.arraycopy(curr, w, curr, w + 1, i - w - 1);
                
                curr[w] = nums[i - 1];
                t = (int) Math.floor(t / i);
            }
            
            List<Integer> tmp = new ArrayList<>(curr.length);
            for (int j : curr) {
                tmp.add(j);
            }
           result.add(tmp);
        }
        
        return result;

    }
    
    public static void main(String[] args) {
        Permutations sv = new Permutations();

        int[][] input = {{1}, {1, 2}, {1, 2, 3}};

        for (int i = 0; i < input.length; i++) {
        //for (int i = 2; i < 3; i++) {    
            System.out.println("\nInput:" + Misc.array2String(input[i]));

            Misc.printListList(sv.permute_recursive(input[i]));
            Misc.printListList(sv.permute_insert(input[i]));
            
            Misc.printListList(sv.permute_Mod(input[i]));
        }

    }
    
}
