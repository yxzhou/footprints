package fgafa.datastructure.segmentTree.mayorPoster;

import java.util.*;

/**
 *
 * Problem
 *   Given a list of poster, as interval (start, end), the latter poster maybe "cover" the previous poster,
 *   How many poster is visible?
 *
 * It's n poster, average poster width is m,
 *
 * Solution 1, naive
 * define int[] posterId = new int[n], posterId[i] is the last poster id that cover position i.
 * The time complexity is O(n * m), Space is O(n)
 *
 * Solution 2, interval tree   ---WRONG !
 * in solution 1, if m is big, m equals to n, it will take O(n^2) and the visible poster is 1.
 * A improvement is to use interval to show a poster.
 * The time complexity is O(n * logn)
 *
 * Solution 3, scan line filling
 *
 *
 * n is 10^4, m is 10^7,  n*m is 10^11,  n*logn = 10^4 * 7
 * The solution 1 is better when m is smaller.
 *
 */

public class MayorPoster {

    public int mayorPoster(int[][] posters){
        if(null == posters || 0 == posters.length){
            return 0;
        }

        int length = posters.length;

        int max = Integer.MIN_VALUE;
        //int min = Integer.MAX_VALUE;
        for(int[] poster : posters){
            if(poster[0] < poster[1]) {
        //        min = Math.min(min, poster[0]);
                max = Math.max(max, poster[1]);
            }
        }

        int n = 1;
        while(n <= max){
            n <<= 1;
        }

        int[] tree = new int[n * 2]; //define tree[i] as the posterId in this interval, default it's 0. it means blank wall

        for(int i = length - 1; i >= 0; i--){
            if(posters[i][0] < posters[i][1]) {
                update(tree, 1, 1, n, i + 1, posters[i][0], posters[i][1]);
            }
        }

        return countPoster(tree, 1, 1, n);
    }


    //addPoster
    private void update(int[] tree, int nodeIndex, int nodeStart, int nodeEnd, int posterId, int start, int end){
        if(posterId <= tree[nodeIndex] ){ //ignore the leaf
            return;
        }

        if(start <= nodeStart && nodeEnd <= end){ //include
            tree[nodeIndex] = posterId;
        }else if(nodeEnd < start || end < nodeStart){ // no intersection
            //do nothing
        }else {   //intersection
            int nodeMiddle = nodeStart + (nodeEnd - nodeStart) / 2;
            int leftSon = nodeIndex * 2;

            update(tree, leftSon, nodeStart, nodeMiddle, posterId, start, end);
            update(tree, leftSon + 1, nodeMiddle + 1, nodeEnd, posterId, start, end);
        }
    }


    private int countPoster(int[] tree, int nodeIndex, int nodeStart, int nodeEnd){
        Set<Integer> posterIds = pushDown(tree, nodeIndex, nodeStart, nodeEnd);
        posterIds.remove(0);
        return posterIds.size();
    }

    private Set<Integer> pushDown(int[] tree, int nodeIndex, int nodeStart, int nodeEnd){
        if(nodeStart == nodeEnd ){
            return new HashSet(Arrays.asList(tree[nodeIndex]));
        }

        //nodeStart < nodeEnd
        int nodeMiddle = nodeStart + (nodeEnd - nodeStart) / 2;
        int leftSon = nodeIndex * 2;

        Set<Integer> leftPosterIds = pushDown(tree, leftSon, nodeStart, nodeMiddle);
        Set<Integer> rightPosterIds = pushDown(tree, leftSon + 1, nodeMiddle + 1, nodeEnd);

        Set<Integer> result = new HashSet<>();
        for(Set<Integer> posterIds : new Set[] {leftPosterIds, rightPosterIds}){
            for(Integer posterId : posterIds){
                if(posterId >= tree[nodeIndex]){
                    result.add(posterId);
                }else{
                    result.add(tree[nodeIndex]);
                }
            }
        }

        return result;
    }


    public static void main(String[] args){
        int[][][] input = {
//                {
//                        {1, 6},
//                        {1, 5},
//                        {6, 6},
//                        {4, 4}
//                },
                {
                        {1, 4},
                        {2, 6},
                        {8, 10},
                        {3, 4},
                        {7, 10}
                },
                {
                        {1, 10},
                        {1, 4},
                        {5, 10}
                },
                {
                        {1, 10},
                        {1, 4},
                        {6, 10}
                },
                {
                        {1, 10},
                        {1, 5},
                        {5, 10}
                }
        };

        int[] expect = {4,3,3,2};

        MayorPoster sv = new MayorPoster();
        MayorPoster2 sv2 = new MayorPoster2();

        for(int i = 0; i < input.length; i++){
            int result1 = sv.mayorPoster(input[i]);
            int result2 = sv2.mayorPoster(input[i]);
            System.out.printf("%d - %d - %d, %b , %b \n",  expect[i], result1, result2, result1 == expect[i], result2 == expect[i]);

        }
    }
}
