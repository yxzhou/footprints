/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package matrix.island;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

/**
 * _https://www.lintcode.com/problem/1062
 * 
 * An image is represented by a 2-D array of integers, each integer representing the pixel value of the image (from 0 to
 * 65535).
 *
 * Given a coordinate (sr, sc) representing the starting pixel (row and column) of the flood fill, and a pixel value
 * newColor, "flood fill" the image.
 *
 * To perform a "flood fill", consider the starting pixel, plus any pixels connected 4-directionally to the starting
 * pixel of the same color as the starting pixel, plus any pixels connected 4-directionally to those pixels (also with
 * the same color as the starting pixel), and so on. Replace the color of all of the aforementioned pixels with the
 * newColor.
 *
 * At the end, return the modified image.
 * 
 * The length of image and image[0] will be in the range [1, 50].
 * The given starting pixel will satisfy 0 <= sr < image.length and 0 <= sc < image[0].length.
 * The value of each color in image[i][j] and newColor will be an integer in [0, 65535].
 * 
 * Example 1:
 * Input: image = [[1,1],[0,0]] sr = 0, sc = 0, newColor = 2
 * Output: [[2,2],[0,0]]
 * 
 * Example 2:
 * Input: image = [[1,1,1],[1,1,0],[1,0,1]] sr = 1, sc = 1, newColor = 2
 * Output: [[2,2,2],[2,2,0],[2,0,1]]
 * Explanation: From the center of the image (with position (sr, sc) = (1, 1)), all pixels connected by a path of the
 * same color as the starting pixel are colored with the new color. Note the bottom corner is not colored 2, because it
 * is not 4-directionally connected to the starting pixel.
 */
public class FloodFill {
    /**
     * do it in-place with the image 
     * 
     * @param image: a 2-D array
     * @param sr: an integer
     * @param sc: an integer
     * @param newColor: an integer
     * @return: the modified image
     */
    int[][] diffs = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};

    public int[][] floodFill(int[][] image, int sr, int sc, int newColor) {
        if(image[sr][sc] == newColor){
            return image; // no need flood fill
        }

        int n = image.length;
        int m = image[0].length;
        int currColor = image[sr][sc];

        Queue<Integer> queue = new LinkedList<>(); 
        queue.add( (sr << 6) | sc ); //n and m is in the range[1, 50]
        image[sr][sc] = newColor;

        int top;
        int r;
        int c;
        int nr;
        int nc;
        while(!queue.isEmpty()){
            top = queue.poll();
            r = (top >> 6); 
            c = top & 0x3f;

            for(int[] diff : diffs){
                nr = r + diff[0];
                nc = c + diff[1];

                if(nr >= 0 && nr < n && nc >= 0 && nc < m && image[nr][nc] == currColor){
                    queue.add( (nr << 6) | nc );
                    image[nr][nc] = newColor;
                }
            }
        }

        return image;
    }
}
