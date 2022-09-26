package sweepLine;

import util.Misc;

import java.util.*;
import junit.framework.Assert;

/**
 * 
 * _https://leetcode.com/problems/the-skyline-problem/
 * 
 * A city's skyline is the outer contour of the silhouette formed by all the buildings in that city when viewed from a
 * distance. Given the locations and heights of all the buildings, return the skyline formed by these buildings
 * collectively.
 *
 * The geometric information of each building is given in the array buildings where buildings[i] = [lefti, righti,
 * heighti]:
 * 
 *   lefti is the x coordinate of the left edge of the ith building.
 *   righti is the x coordinate of the right edge of the ith building.
 *   heighti is the height of the ith building.
 *   
 * You may assume all buildings are perfect rectangles grounded on an absolutely flat surface at height 0.
 *
 * The skyline should be represented as a list of "key points" sorted by their x-coordinate in the form
 * [[x1,y1],[x2,y2],...]. Each key point is the left endpoint of some horizontal segment in the skyline except the last
 * point in the list, which always has a y-coordinate 0 and is used to mark the skyline's termination where the
 * rightmost building ends. Any ground between the leftmost and rightmost buildings should be part of the skyline's
 * contour.
 *
 * Note: There must be no consecutive horizontal lines of equal height in the output skyline. For instance, [...,[2
 * 3],[4 5],[7 5],[11 5],[12 7],...] is not acceptable; the three lines of height 5 should be merged into one in the
 * final output as such: [...,[2 3],[4 5],[12 7],...]
 *
 * 
 * Example 1:
 * Input: buildings = [[2,9,10],[3,7,15],[5,12,12],[15,20,10],[19,24,8]]
 * Output: [[2,10],[3,15],[7,12],[12,0],[15,10],[20,8],[24,0]]
 * 
 * Example 2:
 * Input: buildings = [[0,2,3],[2,5,3]]
 * Output: [[0,3],[5,0]]
 * 
 */
public class Skyline2 {

    /**
     * it's only work when all the coordinates of the buildings are integer less than 10000.
     */
    public List<int[]> getSkyline(int[][] buildings) {
        List<int[]> ret = new ArrayList<>();

        //check
        if (null == buildings || 0 == buildings.length) {
            return ret;
        }

        //main
        final int MAX_X = 10000;
        int[] heights = new int[MAX_X + 1];
        for (int[] building : buildings) {
            for (int x = building[0]; x < building[1]; x++) {
                if (heights[x] < building[2]) {
                    heights[x] = building[2];
                }
            }
        }

        int[] leftTop = new int[2]; // default both are 0 ??
        leftTop[0] = 0;
        leftTop[1] = 0;

        for (int x = 0; x < heights.length; x++) {
            if (leftTop[1] != heights[x]) {
                leftTop[0] = x;
                leftTop[1] = heights[x];
                ret.add(Arrays.copyOf(leftTop, 2));
            }
        }

        //return
        return ret;
    }

    /**
     * it's only work when the number of buildings are not too much.
     */
    public List<int[]> getSkyline_2(int[][] buildings) {
        List<int[]> res = new ArrayList<>();

        //check
        if (null == buildings || 0 == buildings.length) {
            return res;
        }

        //order by x (x_left and x_right)
        List<int[]> bl = new ArrayList<int[]>();
        for (int[] b : buildings) {
            bl.add(new int[]{b[0], b[2]});
            bl.add(new int[]{b[1], -b[2]});
        }

        Collections.sort(bl, new Comparator<int[]>() {
            @Override
            public int compare(int[] a, int[] b) {
                if (a[0] != b[0]) {
                    return a[0] - b[0]; // x in order to ascend
                } else {
                    return b[1] - a[1]; // y in order to descend
                }
            }
        });

        //build a height max heap
        Queue<Integer> heightHeap = new PriorityQueue<>(11,
                new Comparator<Integer>() {
            @Override
            public int compare(Integer a, Integer b) {
                return b - a;
            }
        });
        int pre = 0, cur = 0; //for merging [4 5], [7 5] into one in the final output
        for (int[] b : bl) {
            if (b[1] > 0) { // it's the x_left
                heightHeap.add(b[1]);
            } else { // it's the x_right
                heightHeap.remove(-b[1]);
            }

            cur = heightHeap.isEmpty() ? 0 : heightHeap.peek();
            if (cur != pre) {
                res.add(new int[]{b[0], cur});
                pre = cur;
            }
        }
        return res;
    }


    public List<List<Integer>> getSkyline_20200206(int[][] buildings) {

        int n = buildings.length;

        int[][] edges = new int[n * 2][2]; // edges[i]={x, height}

        int i = 0;
        for (int[] b : buildings) {
            edges[i++] = new int[]{b[0], b[2]};
            edges[i++] = new int[]{b[1], -b[2]};
        }

        Arrays.sort(edges, (e1, e2) -> {
            return e1[0] == e2[0] ? e2[1] - e1[1] : e1[0] - e2[0];
        });

        PriorityQueue<Integer> maxHeap = new PriorityQueue<>(Collections.reverseOrder());

        List<List<Integer>> result = new LinkedList<>();

        int pre = 0;
        int curr;
        for (int[] e : edges) {

            if (e[1] > 0) {
                maxHeap.add(e[1]);
            } else if (e[1] < 0) {
                maxHeap.remove(-e[1]);
            } else {
                continue;
            }

            curr = maxHeap.isEmpty() ? 0 : maxHeap.peek();

            if (curr != pre) {
                pre = curr;

                result.add(Arrays.asList(e[0], curr));
            }

        }

        return result;
    }

    public static void main(String[] args) {
        int[][][][] inputs = {
            //building, expect
            { 
                {
                    {2, 9, 10}, 
                    {3, 7, 15}, 
                    {5, 12, 12}, 
                    {15, 20, 10}, 
                    {19, 24, 8}
                },
                {
                    {2, 10}, 
                    {3, 15}, 
                    {7, 12}, 
                    {12, 0}, 
                    {15, 10}, 
                    {20, 8}, 
                    {24, 0}
                }
            }

        };

        Skyline2 sv = new Skyline2();


        for(int[][][] input : inputs){
            System.out.println(" Input: " + Misc.array2String(input[0]));
            System.out.println("Output: ");
            Misc.printIntArrayList(sv.getSkyline(input[0]));
            System.out.println();
            Misc.printIntArrayList(sv.getSkyline_2(input[0]));
            
        }

    }

}
