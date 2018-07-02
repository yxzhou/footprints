package fgafa.datastructure.segmentTree.billBoard;

/**
 *
 * When input a post (width is x), check from top to bottom, find the first line that has enough space/width for the post.
 *
 * There are total m post, and h lines. so time complexity is O(m * h).
 *
 * To optimized, binary search in the h lines instead of line by line.
 *
 */

import java.util.Arrays;

public class BillBoard {

    public int[] billBoard(int h, int w, int[] posts){
        int[] maxSpace = new int[h * 2 - 1];  //full interval tree
        for(int i = 0; i < maxSpace.length; i++){
            maxSpace[i] = w; // the max valid space is w for every interval
        }

        int[] result = new int[posts.length];
        for(int i = 0; i < posts.length; i++){
            result[i] = addPostAndGetTheLine(maxSpace, 0, 0, h - 1, posts[i]);
            result[i] = result[i] == -1 ? -1 : result[i] + 1;
        }
        return result;
    }

    private int addPostAndGetTheLine(int[] maxSpace, int intervalIndex, int lineStart, int lineEnd, int spaceNeed){
        int result = -1;

        if(lineStart == lineEnd){
            if(maxSpace[intervalIndex] >= spaceNeed){
                maxSpace[intervalIndex] -= spaceNeed;

                result = lineStart;
            }//else return -1

        }else { //lineStart < lineEnd
            int lineMiddle = lineStart + (lineEnd - lineStart) / 2;
            int leftSon = intervalIndex * 2 + 1;
            if(maxSpace[leftSon] >= spaceNeed){
                result  = addPostAndGetTheLine(maxSpace, leftSon, lineStart, lineMiddle, spaceNeed);
            }else{
                result = addPostAndGetTheLine(maxSpace, leftSon + 1, lineMiddle + 1, lineEnd, spaceNeed);
            }

            maxSpace[intervalIndex] = Math.max(maxSpace[leftSon], maxSpace[leftSon + 1]);
        }

        return result;
    }

    public static void main(String[] args){
        final int caseNumber = 1;
        int[][] boards = {
                {3, 5}
        };

        int[][] posts = {
                {2, 4, 3, 3, 3}
        };

        BillBoard sv = new BillBoard();

        for(int i = 0; i < caseNumber; i++){
            Arrays.stream(sv.billBoard(boards[i][0], boards[i][1], posts[i])).mapToObj(x -> x + "\t").forEach(System.out::print);
        }

    }
}
