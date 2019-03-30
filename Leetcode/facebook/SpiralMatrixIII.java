package fgafa.Leetcode.facebook;

import org.junit.Test;

public class SpiralMatrixIII {
    /**
     *
     * 类似于蠡口54的题，也是逆时针打印，但是可以任意起点，同时可以越界，越界的话继续打印就可以（当作没越界）。
     * 例如[[1,2,3,4], [5,6,7,8]]，假如从(0,1)开始，输出会是[2,1,5,6,7,3,8,4]。
     *
     * 四个方向一循环，然后四个中，头两个是走一样的步数（向上，向左），后两个一样，但是＋1（向下，向右），
     * 再下一个循环的时候，头两个是一样的，比上一个的后两个＋1，依次循环。
     *
     */

    int[] print(int[][]matrix, int col, int row){

        int m = matrix.length;
        int n = matrix[0].length;
        int[] result = new int[m*n];

        //->up->left->down->right
        int[][] diffs = { {-1, 0}, {0, -1}, {1, 0}, {0, 1}};

        int count = 0;
        if (col >= 0 && col < m && row >= 0 && row < n) {
            result[count++] = matrix[col][row];
        }

        int r = 2;
        int turn = 0;
        while (count < m * n) {
            int[] diff = diffs[turn % 4];

            for (int i = 1; i < r; i++) {
                col += diff[0];
                row += diff[1];
                if (col >= 0 && col < m && row >= 0 && row < n) {
                    result[count++] = matrix[col][row];
                    if (count == m * n) {
                        return result;
                    }
                }
            }

            turn++;
            r += (turn % 2 == 0 ? 1 : 0);
        }

        return result;
    }

    @Test public void test(){
        System.out.println(print(new int[][]{{1, 2, 3, 4}, {5, 6, 7, 8}}, 0, 1));
    }
}
