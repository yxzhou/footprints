/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package graph.topological;

import java.util.PriorityQueue;
import java.util.Arrays;
import org.junit.Assert;
import util.Misc;

/**
 * _https://www.lintcode.com/problem/1252
 *
 * Suppose you have a random list of people standing in a queue. Each person is described by a pair of integers (h, k),
 * where h is the height of the person and k is the number of people in front of this person who have a height greater
 * than or equal to h. Write an algorithm to reconstruct the queue.
 *
 * The number of people is less than 1,100.
 * 
 * Example1
 * Input:  [[7,0], [4,4], [7,1], [5,0], [6,1], [5,2]]
 * Output: [[5,0], [7,0], [5,2], [6,1], [4,4], [7,1]]
 * 
 * Example2
 * Input: [2,0],[1,1]]
 * Output: [[2,0],[1,1]]
 * 
 * Thoughts:
 *  m1) 
 *   
 * 
 */
public class QueryReconstructionByHeight {
    /**
     * define n as the number of people, h as the max height of the people, 
     * The worst case is like {{1,0},{2,0},{3,0},{4,0},{5,0},{6,0},{7,0}}
     * Time O(n*logn + n * n * logh )
     * 
     * @param people: a random list of people
     * @return the queue that be reconstructed
     */
    public int[][] reconstructQueue_IntervalTree(int[][] people) {
        
        Arrays.sort(people, (a, b) -> a[1] == b[1] ? Integer.compare(a[0], b[0]) : Integer.compare(a[1], b[1]) );

        int maxH = Integer.MIN_VALUE;
        for(int[] p : people){
            maxH = Math.max(maxH, p[0]);
        }

        int m = (1 << ((int)(Math.log(maxH) / Math.log(2)) + 1)) - 1;
        int[] tree = new int[ m * 2 + 2]; // assume the h is in [ 1, 256]

        int n = people.length;
        int minH;
        int k;
        for(int i = 0; i < n; i++ ){

            minH = Integer.MAX_VALUE;
            k = i;
            for(int j = i; j < n && people[j][1] <= i; j++ ){
                
                if(get(tree, people[j][0], 0, m) == people[j][1]){
                    if(people[j][0] < minH ){
                        minH = people[j][0];
                        k = j;
                    }
                }
            }

            if(minH == Integer.MAX_VALUE){
                return new int[0][0];
            }

            if(i != k){
                swap(people, i, k);
            }

            change(tree, people[i][0], 0, m, 1);
        }

        return people;
    }

    private void swap(int[][] people, int i, int j){
        int[] tmp = people[i];
        people[i] = people[j];
        people[j] = tmp;
    }

    private int get(int[] tree, int h, int l, int r){
        int sum = 0;

        int mid;
        int i = 1;
        while(l < r){
            mid = l + (r - l) / 2;

            i <<= 1;
            if(mid < h){
                i += 1;
                l = mid + 1;
            }else{
                sum += tree[i + 1];
                r = mid;
            }
        }

        sum += tree[i];

        return sum;
    }

    private void change(int[] tree, int h, int l, int r, int diff){
        int mid;
        int i = 1;
        while(l < r){
            mid = l + (r - l) / 2;

            i <<= 1;
            if(mid < h){
                i += 1;
                l = mid + 1;
            }else{
                r = mid;
            }
            tree[i] += diff;
        }

    }
    
    /**
     * @param people: a random list of people
     * @return the queue that be reconstructed
     */
    public int[][] reconstructQueue_Heap(int[][] people) {
        int n = people.length;
        int[][] result = new int[n][2];
                 
        int[] degrees = new int[n];
        PriorityQueue<Integer> minHeap = new PriorityQueue<>((a, b) -> Integer.compare(people[a][0], people[b][0]));
        
        int[] p;
        for(int j = 0; j < n ; j++){
            p = people[j];
            
            if(p[1] == 0){
                minHeap.add(j);
            }
            
            degrees[j] = p[1];
        }
        
        int i = 0;
        int top;
        while(!minHeap.isEmpty()){
            top = minHeap.poll();
            p = people[top];

            result[i++] = p;
            
            for(int j = 0 ; j < n; j++){
                if(degrees[j] == 0){
                    continue;
                }
                
                if(people[j][0] <= p[0]){
                    degrees[j]--;
                    if(degrees[j] == 0){
                        minHeap.add(j);
                    }
                }
            }
        }
        
        return result; 
    }
    
   /**
     * @param people: a random list of people
     * @return the queue that be reconstructed
     */
    public int[][] reconstructQueue_InsertSort(int[][] people) {
        Arrays.sort(people, (a, b) -> a[1] == b[1] ? Integer.compare(a[0], b[0]) : Integer.compare(a[1], b[1]) );

        int n = people.length;
        int i = 0;
        while( i < n && people[i][1] == 0 ){
            i++;
        } 

        int k;
        for( int j = i; j < n; j++){
            k = 0;
            for( int count = 0; k < j ; k++){
                if(people[k][0] >= people[j][0]){
                    count++;
                    
                    if(count > people[j][1]){
                        break;
                    }
                }
            }
            
            if(k < j){
                int[] tmp = people[j];
                
                System.arraycopy(people, k, people, k + 1, j - k);
                people[k] = tmp;
            }
            
        }

        return people;
    }
    
    
    public static void main(String[] args){
        
        /** test Arrays.binarySearch */
        int[] arr = { 1, 3, 3, 4};
        for(int i = 0; i < 6; i++){
            System.out.println("\n - Input: " + Arrays.toString(arr) );
            
            System.out.println(String.format(" binarySearch(%d) = %d", i, Arrays.binarySearch(arr, i)) );
        }


        
        
        int[][][][] inputs = { 
            //{
            //  test_case,
            //  expect result
            //}
            {
                {{7,0}, {4,4}, {7,1}, {5,0}, {6,1}, {5,2}},
                {{5,0}, {7,0}, {5,2}, {6,1}, {4,4}, {7,1}}
            },
            {
                {{7,0}, {4,4}, {7,1}, {6,0}, {6,1}, {5,2}},
                {{6,0}, {6,1}, {5,2}, {7,0}, {4,4}, {7,1}}
            },
            {
                {{1,0},{2,0},{3,0},{4,0},{5,0},{6,0},{7,0}},
                {{1,0},{2,0},{3,0},{4,0},{5,0},{6,0},{7,0}}
            },
            {
                {{2,0},{1,1}}, 
                {{2,0},{1,1}},   
            },
            {
                {{87,6},{13,58},{72,0},{28,14},{9,36},{35,4},{54,26},{34,51},{15,66},{79,10},{9,39},{23,2},{6,21},{18,15},{96,3},{3,71},{20,58},{24,27},{62,34},{91,2},{83,17},{8,84},{57,1},{50,31},{22,5},{92,5},{97,5},{84,1},{7,18},{81,15},{57,36},{57,25},{41,0},{52,15},{8,23},{67,15},{22,62},{50,27},{0,44},{22,43},{10,33},{39,22},{38,26},{23,38},{71,1},{94,3},{1,16},{65,1},{16,32},{76,0},{25,55},{14,39},{90,4},{47,5},{99,0},{98,1},{97,0},{81,12},{32,54},{4,78},{20,20},{89,4},{33,12},{21,8},{26,32},{71,9},{32,34},{97,3},{81,20},{6,36},{6,85},{24,2},{81,6},{48,0},{78,6},{47,26},{81,9},{69,3},{15,76},{81,13},{59,14},{54,1},{31,43},{82,10},{53,7},{3,16},{53,19},{51,39},{89,5},{33,45},{59,5},{87,9},{95,5},{34,15},{31,34},{15,28},{76,5},{76,29},{75,13},{98,0}},
                {{41,0},{48,0},{23,2},{24,2},{72,0},{22,5},{54,1},{35,4},{21,8},{57,1},{65,1},{47,5},{71,1},{76,0},{69,3},{18,15},{1,16},{3,16},{53,7},{33,12},{7,18},{59,5},{28,14},{6,21},{97,0},{20,20},{84,1},{8,23},{34,15},{98,0},{91,2},{76,5},{98,1},{15,28},{52,15},{71,9},{89,4},{59,14},{6,36},{10,33},{16,32},{24,27},{9,36},{39,22},{0,44},{78,6},{9,39},{53,19},{81,6},{14,39},{38,26},{89,5},{26,32},{67,15},{47,26},{23,38},{87,6},{31,34},{32,34},{22,43},{81,9},{75,13},{50,27},{79,10},{90,4},{54,26},{50,31},{31,43},{13,58},{57,25},{94,3},{81,12},{81,13},{3,71},{20,58},{33,45},{82,10},{81,15},{87,9},{15,66},{96,3},{25,55},{4,78},{22,62},{92,5},{51,39},{97,3},{32,54},{34,51},{81,20},{6,85},{15,76},{57,36},{8,84},{95,5},{99,0},{62,34},{97,5},{83,17},{76,29}}
            }
        };
        
        QueryReconstructionByHeight sv = new QueryReconstructionByHeight();
        
        for(int[][][] input : inputs){
            System.out.println(String.format("\nInput: %s", Misc.array2String(input[0], true)));
            //System.out.println(String.format("expect: %s, \nOutput: %s", Misc.array2String(input[1], true), Misc.array2String(sv.reconstructQueue_InsertSort(input[0]), true) ));
            
            
            //Assert.assertArrayEquals(input[1], sv.reconstructQueue(input[0]));
            
            //Assert.assertArrayEquals(input[1], sv.reconstructQueue_InsertSort(input[0]));
            
            Assert.assertArrayEquals(input[1], sv.reconstructQueue_Heap(input[0]));
            
            //Assert.assertEquals(Misc.array2String(input[1]), Misc.array2String(sv.reconstructQueue(input[0])));
        }
        
    }
    
}
