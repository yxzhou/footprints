package fgafa.topCoder.graph;

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
 */

public class GrafixMask {
}
