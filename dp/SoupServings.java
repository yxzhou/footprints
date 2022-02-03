/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dp;

/**
 * _https://www.lintcode.com/problem/1009
 *
 * There are two types of soup: type A and type B. Initially we have N ml of each type of soup. There are four kinds of
 * operations:
 *
 * Serve 100 ml of soup A and 0 ml of soup B 
 * Serve 75 ml of soup A and 25 ml of soup B 
 * Serve 50 ml of soup A and 50 ml of soup B 
 * Serve 25 ml of soup A and 75 ml of soup B 
 * 
 * When we serve some soup, we give it to someone and we no longer have it. Each turn, we will choose from the four
 * operations with equal probability 0.25. If the remaining volume of soup is not enough to complete the operation, we
 * will serve as much as we can. We stop once we no longer have some quantity of both types of soup.
 *
 * Note that we do not have the operation where all 100 ml's of soup B are used first.
 *
 * Return the probability that soup A will be empty first, plus half the probability that A and B become empty at the
 * same time.
 *
 * Constraints:
 * 1.0 <= N <= 10^9. 
 * 2.Answers within 10^-6 of the true value will be accepted as correct. 
 * 
 * Example 
 * Input: N = 50 
 * Output: 0.625 
 * Explanation: 
 * If we choose the first two operations, A will become empty first. For the third operation, A and B will become empty 
 * at the same time. For the fourth operation, B will become empty first. So the total probability of A becoming empty 
 * first plus half the probability that A and B become empty at the same time, is 0.25 * (1 + 1 + 0.5 + 0) = 0.625.
 *
 * Thoughts:
 *           n <= 25     0.25 * ( 1 + 0.5 + 0.5 + 0.5)
 *      25 < n <= 50     0.25 * ( 1 + 1 + 0.5 + 0)
 *      50 < n <= 75     0.25 * ( 1 + 1 + 0.5 + 0)
 *      75 < n <= 100    0.25 * ( 1 + f(n-75, n-25) + f(n-50, n-50) + f(n-25, n-75)  )
 * 
 */
public class SoupServings {
    
    /**
     * 
     * @param N: an integer
     * @return the probability that soup A will be empty first
     */

    public double soupServings_DFS(int N) {
        int n = N / 25 + (N % 25 == 0? 0 : 1);

        if(n == 100){
            return 1;
        }

        return dfs(n, n);
    }

    private double dfs(int n, int m){
        n = Math.max(n, 0);
        m = Math.max(m, 0);

        if(n == 0){
            if(m == 0){
                return 0.5;
            }

            return 1;
        }
        if(m == 0){
            return 0;
        }

        return (dfs(n - 4, m) + dfs(n - 3, m - 1) + dfs(n - 2, m - 2) + dfs(n - 1, m - 3)) / 4;
    }
        
    public double soupServings(int N) {
        
        if(N >= 1700){
            return 1;
        }

        int n = N / 25 + (N % 25 == 0? 0 : 1);

        n += 3; // special for f[a - 4][b] and f[a - 1][b - 3]. 
        double[][] f = new double[n + 1][n];
        
        for(int a = 0; a <= 3; a++){
            for(int b = 0; b <= 2; b++ ){
                f[a][b] = 0.5;
            }

            for(int b = 3; b < n; b++ ){
                f[a][b] = 1;
            }
        }

        for(int a = 4, m = f.length; a < m; a++){
            for(int b = 3; b < n; b++){
                f[a][b] = (f[a - 4][b] + f[a - 3][b - 1] + f[a - 2][b - 2] + f[a - 1][b - 3] ) / 4;
            }
        }

        return f[n][n - 1];
    }
    
}
