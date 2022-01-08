/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package stringmatching;

/**
 *
 * Given two strings A and B, find the minimum number of times A has to be repeated such that B is a substring of it. If
 * no such solution, return -1.
 *
 * Notes: 
 *   The length of A and B will be between 1 and 10000.
 * 
 * Example 1:
 * Input : A = "a"     B = "b".
 * Output : -1
 * 
 * Example 2:
 * Input : A = "abcd"     B = "cdabcdab".
 * Output :3
 * Explanation : because by repeating A three times (“abcdabcdabcd”), B is a substring of it; and B is not a substring 
 * of A repeated two times ("abcdabcd").
 * 
 */
public class RepeatedStringMatch {
    /**
     * @param A: a string
     * @param B: a string
     * @return an integer
     */
    public int repeatedStringMatch(String A, String B) {
        int n = A.length();
        int m = B.length();

        StringBuilder sb = new StringBuilder(A);

        while( sb.length() < m ){
            sb.append(A);
        }

        for(int k = 0; k < 2; k++){
            if(sb.indexOf(B) != -1 ){
                return sb.length() / n;
            }

            sb.append(A);
        }

        return -1;
    }
}
