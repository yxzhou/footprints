package matrix.island;

import org.junit.Assert;
import org.junit.Test;

import java.util.*;

/**
 *
 * NumberOfIslandsII
 *
 * union find
 *
 */

public class NumberOfIslandsII2 {

    /**
     * Definition for a point.
     */
    class Point {
        int x;
        int y;

        Point() {
            x = 0;
            y = 0;
        }

        Point(int a, int b) {
            x = a;
            y = b;
        }
    }


    /**
     * @param n: An integer
     * @param m: An integer
     * @param operators: an array of point
     * @return: an integer array
     */
    int N;
    int M;
    int[][] diffs;

    public List<Integer> numIslands2(int n, int m, Point[] operators) {
        if(operators == null){
            return Collections.EMPTY_LIST;
        }

        this.N = n;
        this.M = m;
        this.diffs = new int[][]{{1, 0}, {-1, 0}, {0, 1}, {0, -1}};

        int[] parentIds = new int[n*m];
        Arrays.fill(parentIds, -1);
        int[] unionSizes = new int[parentIds.length];

        List<Integer> result = new ArrayList<>(operators.length);

        int count = 0;
        int id;
        for(Point p : operators){
            id = p.x * m + p.y;
            if(parentIds[id] == -1){
                count += 1 - union(parentIds, unionSizes, p.x, p.y);
            }

            result.add(count);
        }

        return result;
    }

    private int union(int[] parentIds, int[] unionSizes, int r, int c) {
        Set<Integer> uniques = new HashSet<>();
        int id = r * M + c;
        int maxUnionSize = 0;
        int maxGroupId = id;

        for(int[] diff : diffs){
            int nr = r + diff[0];
            int nc = c + diff[1];
            int np = nr*M + nc;

            if(nr >= 0 && nr < N && nc >= 0 && nc < M && parentIds[np] != -1 ){
                int groupId = find(parentIds, np);
                uniques.add(groupId);

                if(unionSizes[groupId] > maxUnionSize){
                    maxUnionSize = unionSizes[groupId];
                    maxGroupId = groupId;
                }
            }
        }

        for(int i : uniques){
            if(i != maxGroupId){
                parentIds[i] = maxGroupId;
                unionSizes[maxGroupId] += unionSizes[i];
            }
        }

        parentIds[id] = maxGroupId;
        unionSizes[maxGroupId] += 1;

        return uniques.size();
    }

    private int find(int[] parentIds, int id){
        while(parentIds[id] != id){
            id = parentIds[id];
        }

        return id;
    }



    @Test
    public void test(){
        Assert.assertArrayEquals( new int[]{1,2,3,4,3,2,1}, numIslands2(3, 3, build(new int[][]{{0,1}, {1,2},{2,1},{1,0},{0,2},{0,0},{1,1}})).stream().mapToInt(Integer::intValue).toArray()  );
    }

    private Point[] build(int[][] points){
//        Point[] result = new Point[points.length];
//        int i = 0;
//        for(int[] p : points){
//            result[i++] = new Point(p[0], p[1]);
//        }
//
//        return result;

        List<Point> result = new ArrayList<>(points.length);
        Arrays.stream(points).forEach(p -> result.add(new Point(p[0], p[1])));
        Point[] tmp = new Point[points.length];
        result.toArray(tmp);
        return tmp;
    }

}
