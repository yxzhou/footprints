package topCoder.graph;

import java.util.*;

/**
 *
 * In one mode of the grafix software package, the user blocks off portions of a masking layer using opaque rectangles. The bitmap used as
 * the masking layer is 400 pixels tall and 600 pixels wide. Once the rectangles have been blocked off, the user can perform painting actions
 * through the remaining areas of the masking layer, known as holes. To be precise, each hole is a maximal collection of contiguous pixels that
 * are not covered by any of the opaque rectangles. Two pixels are contiguous if they share an edge, and contiguity is transitive.
 *
 * You are given a String[] named rectangles, the elements of which specify the rectangles that have been blocked off in the masking layer.
 * Each String in rectangles consists of four integers separated by single spaces, with no additional spaces in the string. The first two
 * integers are the window coordinates of the top left pixel in the given rectangle, and the last two integers are the window coordinates of
 * its bottom right pixel. The window coordinates of a pixel are a pair of integers specifying the row number and column number of the pixel,
 * in that order. Rows are numbered from top to bottom, starting with 0 and ending with 399. Columns are numbered from left to right, starting
 * with 0 and ending with 599. Every pixel within and along the border of the rectangle defined by these opposing corners is blocked off.
 *
 * Return a int[] containing the area, in pixels, of every hole in the resulting masking area, sorted from smallest area to greatest.
 *
 * Notes
 * ==========================
 * Window coordinates are not the same as Cartesian coordinates. Follow the definition given in the second paragraph of the problem statement.
 *
 * Constraints
 * ==========================
 * - rectangles contains between 1 and 50 elements, inclusive
 * - each element of rectangles has the form "ROW COL ROW COL",
 *   where: "ROW" is a placeholder for a non-zero-padded integer between 0 and 399, inclusive; "COL" is a placeholder for a non-zero-padded
 *   integer between 0 and 599, inclusive; the first row number is no greater than the second row number; the first column number is no
 *   greater than the second column number
 *
 *
 */

public class GrafixMask {

    static final int width = 600;
    static final int height = 400;

    public List<Integer> sortedAreas(String[] rectangles){

        if(null == rectangles || 0 == rectangles.length){
            return Arrays.asList(new Integer[]{width * height});
        }

        boolean[][] masks = new boolean[height][width]; //default all are false

        for(String rectangle : rectangles){
            String[] position = rectangle.split(" ");
            int topLeftRow = Integer.parseInt(position[0]);
            int topLeftColumn = Integer.parseInt(position[1]);
            int bottomRightRow = Integer.parseInt(position[2]);
            int bottomRightColumn = Integer.parseInt(position[3]);

            for(int row = topLeftRow; row <= bottomRightRow; row++){
                for(int column = topLeftColumn; column <= bottomRightColumn; column++){
                    masks[row][column] = true;
                }
            }
        }

        List<Integer> result = new ArrayList<>();
        for(int row = 0; row < height; row++){
            for(int column = 0; column < width; column++){
                if(!masks[row][column]){
                    result.add(paint2(masks, row, column));
                }
            }
        }

        Collections.sort(result);
        return result;
    }

    //bfs recursive
    private int paint(boolean[][] masks, int row, int column){
        if(row < 0 || row >= height || column < 0 || column >= width || masks[row][column]){
            return 0;
        }

        masks[row][column] = true;
        int count = 1;

        count += paint(masks, row + 1, column);
        count += paint(masks, row - 1, column);
        count += paint(masks, row, column + 1);
        count += paint(masks, row, column - 1);

        return count;
    }

    //bfs Queue or Stack
    private int paint2(boolean[][] masks, int row, int column){
        int count = 0;

        Stack<String> stack = new Stack<>();
        stack.add(row + " " + column);

        while(!stack.isEmpty()) {
            String[] position = stack.pop().split(" ");
            row = Integer.parseInt(position[0]);
            column = Integer.parseInt(position[1]);

            if(row < 0 || row >= height || column < 0 || column >= width || masks[row][column]){
                continue;
            }

            masks[row][column] = true;
            count++;

            stack.add((row + 1) + " " + column);
            stack.add((row - 1) + " " + column);
            stack.add(row + " " + (column + 1));
            stack.add(row + " " + (column - 1));
        }

        return count;
    }


}
