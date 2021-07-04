package fgafa.array.parenthese;

import fgafa.util.Misc;

import java.util.ArrayList;
import java.util.List;

/**
 * Given n pairs of parentheses, write a function to generate all combinations
 * of well-formed parentheses. For example, given n = 3, a solution set is:
 * "((()))", "(()())", "(())()", "()(())", "()()()"
 *
 * @author yxzhou
 */
public class GenerateParenthese {
    /*
     * insert
     */
    public ArrayList<String> generateParenthesis_insert(int n) {
        ArrayList<String> result = new ArrayList<String>();

        // check
        if (n < 1) {
            return result;
        }

        //init
        String sb = "()";

        //main
        generateParenthesis_insert(sb, 1, n << 1, result);

        return result;
    }

    private void generateParenthesis_insert(String s, int index, int length,
            ArrayList<String> result) {

        if (s.length() == length) {
            result.add(s);
            return;
        }

        for (int k = index; k <= s.length(); k++) {
            generateParenthesis_insert(s.substring(0, k) + "(" + s.substring(k, s.length()) + ")", k + 1, length,
                    result);
        }

    }

    /*
     * recursive
     * idea:
     *    to every position, put the "(" or ")",
     */
    public List<String> generateParenthesis_recursive(int n) {
        List<String> result = new ArrayList<>();

        if (n < 1) {
            return result;
        }

        generateParenthesis_recursive(new char[n * 2], 0, 0, n, result);

        return result;
    }

    /**
     *
     * @param s,  char array
     * @param i
     * @param diff,  open - close
     * @param remain,  n - open
     * @param result
     */
    private void generateParenthesis_recursive(char[] s, int i, int diff, int remain, List<String> result) {
        if (remain == 0) {
            while (i < s.length) {
                s[i++] = ')';
            }

            result.add(new String(s));
            return;
        }

        //remain > 0
        s[i] = '(';
        generateParenthesis_recursive(s, i + 1, diff + 1, remain - 1, result);


        if (diff > 0) {
            s[i] = ')';
            generateParenthesis_recursive(s, i + 1, diff - 1, remain, result);
        }
    }

    /*
     * next
     *
     * ()()()    ()(())    (())()    (()())    ((()))
     * 101010 => 101100 => 110010 => 110100 => 111000
     *
     */
    public ArrayList<String> generateParenthesis_order(int n) {
        ArrayList<String> result = new ArrayList<String>();

        char[] arr = { ')', '(' };  //

        // check
        if (n < 1)
            return result;

        n <<= 1;
        int[] seq = new int[n];
        for (int i = 0; i < n; ) {
            seq[i++] = 1;
            seq[i++] = 0;
        }

        do {
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < n; i++) {
                sb.append(arr[seq[i]]);
            }

            result.add(sb.toString());
        } while (generateNextParenthesis(seq, n));

        return result;
    }

    private boolean generateNextParenthesis(int[] seq, int len) {

        int j = len - 2;
        for (; j >= 0 && seq[j] >= seq[j + 1]; j--)
            ; // fetch from right, break if the left is smaller than the right

        if (j < 0)
            return false; // no next, ( all finished, such as "321" or "111" )

        seq[j] = 1;
        seq[j + 1] = 0;

        int countOf1 = 0;
        for (int i = j + 2; i < len; i++) {
            if (seq[i] == 1) {
                countOf1++;
                seq[i] = 0;
            }
        }
        for (int i = len - 2; countOf1 > 0; countOf1--) {
            seq[i] = 1;
            i -= 2;
        }

        return true;
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        GenerateParenthese sv = new GenerateParenthese();
        int[] n = { 0, 1, 2, 3, 4 };

        for (int i = 0; i < n.length; i++) {
            System.out.println("\nInput n:" + n[i]);
            Misc.printArrayList(sv.generateParenthesis_recursive(n[i]));
            Misc.printArrayList(sv.generateParenthesis_insert(n[i]));
            Misc.printArrayList(sv.generateParenthesis_order(n[i]));
        }

    }

}
