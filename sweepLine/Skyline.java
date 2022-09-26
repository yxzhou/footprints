/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sweepLine;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.TreeMap;
import junit.framework.Assert;
import util.Misc;

/**
 * _https://www.lintcode.com/problem/131/
 *
 * Given N buildings in a x-axis，each building is a rectangle and can be represented by a triple (start, end,
 * height)，where start is the start position on x-axis, end is the end position on x-axis and height is the height of
 * the building. Buildings may overlap if you see them from far away，find the outline of them。
 *
 * An outline can be represented by a triple, (start, end, height), where start is the start position on x-axis of the
 * outline, end is the end position on x-axis and height is the height of the outline.
 *
 * Please merge the adjacent outlines if they have the same height and make sure different outlines can't overlap on
 * x-axis.
 *
 *
 * Example 1
 * Input:
 * [
 *     [1, 3, 3],
 *     [2, 4, 4],
 *     [5, 6, 1]
 * ]
 * Output:
 * [
 *     [1, 2, 3],
 *     [2, 4, 4],
 *     [5, 6, 1]
 * ]
 * 
 * 
 * Example 2
 * Input:
 * [
 *     [1, 4, 3],
 *     [6, 9, 5]
 * ]
 * Output:
 * [
 *     [1, 4, 3],
 *     [6, 9, 5]
 * ]
 * 
 */
public class Skyline {
    /**
     * @param buildings: A list of lists of integers
     * @return Find the outline of those buildings
     */
    public List<List<Integer>> buildingOutline(int[][] buildings) {
        
        if(buildings == null){
            return Collections.EMPTY_LIST;
        }

        TreeMap<Integer, List<Integer>> treeMap = new TreeMap<>();// map: x to a list of buildings' indexes
        
        int[] building;
        for(int i = 0; i < buildings.length; i++){
            building = buildings[i];
            treeMap.computeIfAbsent(building[0], o -> new ArrayList<>()).add(i);
            treeMap.computeIfAbsent(building[1], o -> new ArrayList<>()).add(i);
        }

        PriorityQueue<Integer> maxHeap = new PriorityQueue<>( Collections.reverseOrder() ); // max heap of height

        List<List<Integer>> result = new ArrayList<>();

        int preX = 0;
        int currX;
        int preHeight = 0; //
        int currHeight;
        
        for(Map.Entry<Integer, List<Integer>> entry : treeMap.entrySet()){
            currX = entry.getKey();

            for(Integer i : entry.getValue()){
                if(currX == buildings[i][0]){ //it's start 
                    maxHeap.add(buildings[i][2]);
                }else { //it's end
                    maxHeap.remove(buildings[i][2]);
                }
            }

            currHeight = (maxHeap.isEmpty()? 0 : maxHeap.peek());
                    
            if( currHeight != preHeight){
                
                if(preHeight != 0){
                    result.add(Arrays.asList(preX, currX, preHeight));
                }

                
                preX = currX;
                
                preHeight = currHeight;
            }
        }

        return result;
    }
    
    /**
     * @param buildings: A list of lists of integers
     * @return Find the outline of those buildings
     */
    public List<List<Integer>> buildingOutline_2(int[][] buildings) {
        
        if(buildings == null){
            return Collections.EMPTY_LIST;
        }

        int[][] edges = new int[ buildings.length * 2][2]; // the left and right edges of the buildings

        int i = 0;
        for(int[] building : buildings){
            edges[i++] = new int[]{building[0], building[2]}; 
            edges[i++] = new int[]{building[1], -building[2]}; 
        }

        Arrays.sort(edges, (a, b) -> Integer.compare(a[0], b[0]) );

        PriorityQueue<Integer> maxHeap = new PriorityQueue<>((a, b) -> Integer.compare(b, a) ); // max heap of height

        List<List<Integer>> result = new ArrayList<>();

        int preX = 0;
        int currX;
        int preHeight = 0; //
        int currHeight;

        for(int j = 0, n = edges.length; j < n; ){
            
            for( currX = edges[j][0]; j < n && currX == edges[j][0]; j++){
                if(edges[j][1] > 0){ //it's start 
                    maxHeap.add(edges[j][1]);
                }else { //it's end
                    maxHeap.remove(-edges[j][1]);
                }
            }

            currHeight = (maxHeap.isEmpty()? 0 : maxHeap.peek());

            if( preHeight != currHeight ){
                if(preHeight != 0){
                    result.add(Arrays.asList(preX, currX, preHeight)); 
                }

                preX = currX;
                
                preHeight = currHeight;
            }
        }

        return result;
    }
    
    public static void main(String[] args){
        Skyline sv = new Skyline();
        
        int[][][][] inputs = {
            //{ buildings, expect }
            {    
                {
                    {1, 3, 3},
                    {2, 4, 4},
                    {5, 6, 1}
                },
                {
                    {1, 2, 3},
                    {2, 4, 4},
                    {5, 6, 1}
                }
            },
            {
                {
                    {1, 4, 3},
                    {6, 9, 5}
                },
                {
                    {1, 4, 3},
                    {6, 9, 5}
                }
            }, 
            {
                {
                    {2, 9, 10}, 
                    {3, 7, 15}, 
                    {5, 12, 12}, 
                    {15, 20, 10}, 
                    {19, 24, 8}
                },
                {
                    {2, 3, 10}, 
                    {3, 7, 15}, 
                    {7, 12, 12}, 
                    {15, 20, 10}, 
                    {20, 24, 8}
                }
            },
            {
                {{4, 67, 187}, {3, 80, 65}, {49, 77, 117}, {67, 74, 9}, {6, 42, 92}, {48, 67, 69}, {10, 13, 58}, {47, 99, 152}, {66, 99, 53}, {66, 71, 34}, {27, 63, 2}, {35, 81, 116}, {47, 49, 10}, {68, 97, 175}, {20, 33, 53}, {24, 94, 20}, {74, 77, 155}, {39, 98, 144}, {52, 89, 84}, {13, 65, 222}, {24, 41, 75}, {16, 24, 142}, {40, 95, 4}, {6, 56, 188}, {1, 38, 219}, {19, 79, 149}, {50, 61, 174}, {4, 25, 14}, {4, 46, 225}, {12, 32, 215}, {57, 76, 47}, {11, 30, 179}, {88, 99, 99}, {2, 19, 228}, {16, 57, 114}, {31, 69, 58}, {12, 61, 198}, {70, 88, 131}, {7, 37, 42}, {5, 48, 211}, {2, 64, 106}, {49, 73, 204}, {76, 88, 26}, {58, 61, 215}, {39, 51, 125}, {13, 38, 48}, {74, 99, 145}, {4, 12, 8}, {12, 33, 161}, {61, 95, 190}, {16, 19, 196}, {3, 84, 8}, {5, 36, 118}, {82, 87, 40}, {8, 44, 212}, {15, 70, 222}, {16, 25, 176}, {9, 100, 74}, {38, 78, 99}, {23, 77, 43}, {45, 89, 229}, {7, 84, 163}, {48, 72, 1}, {31, 88, 123}, {35, 62, 190}, {21, 29, 41}, {37, 97, 81}, {7, 49, 78}, {83, 84, 132}, {33, 61, 27}, {18, 45, 1}, {52, 64, 4}, {58, 98, 57}, {14, 22, 1}, {9, 85, 200}, {50, 76, 147}, {54, 70, 201}, {5, 55, 97}, {9, 42, 125}, {31, 88, 146}},
                {{1, 2, 219}, {2, 19, 228}, {19, 45, 225}, {45, 89, 229}, {89, 95, 190}, {95, 97, 175}, {97, 99, 152}, {99, 100, 74}}
            }
        };
        
        for(int[][][] input : inputs){
            Assert.assertEquals( Misc.array2String(input[1], true), Misc.array2String(sv.buildingOutline(input[0]), true).toString());
            
            Assert.assertEquals( Misc.array2String(input[1], true), Misc.array2String(sv.buildingOutline_2(input[0]), true).toString());
        }
        
    }
}
