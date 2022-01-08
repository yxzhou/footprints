/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package matrix.island;

import java.util.LinkedList;
import java.util.Queue;

/**
 * _https://www.lintcode.com/problem/1179
 * 
 * There are N students in a class. Some of them are friends, while some are not. Their friendship is transitive in
 * nature. For example, if A is a direct friend of B, and B is a direct friend of C, then A is an indirect friend of C.
 * And we defined a friend circle is a group of students who are direct or indirect friends.
 *
 * Given a N*N matrix M representing the friend relationship between students in the class. If M[i][j] = 1, then the ith
 * and jth students are direct friends with each other, otherwise not. And you have to output the total number of friend
 * circles among all the students.
 * 
 * Notes:
 *   1.1≤N≤200.
 *   2.M[i][i] = 1 for all students.
 *   3.If M[i][j] = 1, then M[j][i] = 1.
 * 
 * Example 1:
 * Input: [[1,1,0],[1,1,0],[0,0,1]]
 * Output: 2
 * Explanation:
 * The 0th and 1st students are direct friends, so they are in a friend circle. 
 * The 2nd student himself is in a friend circle. So return 2.
 * 
 * Example 2:
 * Input: [[1,1,0],[1,1,1],[0,1,1]]
 * Output: 1
 * Explanation:
 * The 0th and 1st students are direct friends, the 1st and 2nd students are direct friends, 
 * so the 0th and 2nd students are indirect friends. All of them are in the same friend circle, so return 1.
 * 
 * Solutions:
 *   1 DFS
 *   2 BFS
 *   3 UnionFind
 * 
 */
public class FriendCircles {
    /**
     *  DFS
     * 
     * @param M: a matrix
     * @return the total number of friend circles among all the students
     */
    public int findCircleNum_DFS(int[][] M) {
        int n = M.length;

        int count = 0;
        boolean[] visited = new boolean[n];
        for(int i = 0; i < n; i++){
            if(!visited[i]){
                count++;
                dfs(M, i, visited);
            }
        }

        return count;
    }

    private void dfs(int[][] M, int i, boolean[] visited){
        visited[i] = true;

        for(int j = 0; j < M.length; j++){
            if(M[i][j] == 1 && !visited[j]){
                dfs(M, j, visited);
            }
        }
    }
    
    /**
     *  BFS
     * 
     * @param M: a matrix
     * @return the total number of friend circles among all the students
     */
    public int findCircleNum(int[][] M) {
        int n = M.length;
        int count = 0;

        boolean[] visited = new boolean[n];
        Queue<Integer> queue = new LinkedList<>();
        int top;
        for(int i = 0; i < n; i++){
            if(!visited[i]){
                count++;
                queue.add(i);

                while(!queue.isEmpty()){
                    top = queue.poll();
                    visited[top] = true;

                    for(int j = 0; j < n; j++){
                        if(M[top][j] == 1 && !visited[j]){
                            queue.add(j);
                        }
                    }                    
                }
            }
        }

        return count;
    }
}
