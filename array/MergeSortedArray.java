package array;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;

public class MergeSortedArray {

    /**
     *
     * Given two sorted integer arrays A and B, merge B into A as one sorted
     * array.
     *
     * Note: You may assume that A has enough space to hold additional elements
     * from B. The number of elements initialized in A and B are m and n
     * respectively.
     *
     *
     */
    public void mergeSortedArray_n(int A[], int m, int B[], int n) {
        if (A == null || B == null || n <= 0) {
            return;
        }

        //iterator check, from right to left
        int a = m - 1;
        int b = n - 1;
        for (int c = m + n - 1; a >= 0 && b >= 0; c--) {
            if (A[a] <= B[b]) {
                A[c] = B[b];
                b--;
            } else {
                A[c] = A[a];
                a--;
            }
        }

        if (b >= 0) {
            System.arraycopy(B, 0, A, 0, b + 1);
        }
    }

    /**
     * Merge two given sorted integer array A and B into a new sorted integer
     * array.
     *
     * Example A=[1,2,3,4], B=[2,4,5,6] return [1,2,2,3,4,4,5,6]
     *
     * Challenge How can you optimize your algorithm if one array is very large
     * and the other is very small?
     */
    public ArrayList<Integer> mergeSortedArray(ArrayList<Integer> A, ArrayList<Integer> B) {
        //check
        if (null == A) {
            if (null == B) {
                return new ArrayList<Integer>();
            } else {
                return new ArrayList<Integer>(B);
            }
        }

        if (null == B) {
            return new ArrayList<Integer>(A);
        }

        if (A.size() < B.size()) {
            return mergeSortedArray(B, A);
        }

        ArrayList<Integer> result = new ArrayList<Integer>(A.size() + B.size());
        int i = 0;
        int j = 0;
        while (i < A.size() && j < B.size()) {
            if (A.get(i) < B.get(j)) {
                result.add(A.get(i));
                i++;
            } else {
                result.add(B.get(j));
                j++;
            }
        }

        if (j < B.size()) {
            result.addAll(B.subList(j, B.size()));
        } else { // i < A.size()
            result.addAll(A.subList(i, A.size()));
        }

        return result;
    }
    
    /**
     * _https://www.lintcode.com/problem/486
     * 
     * Given k sorted integer arrays, merge them into one sorted array.


     * Example 1:
     * Input: 
        [
            [1, 3, 5, 7],
            [2, 4, 6],
            [0, 8, 9, 10, 11]
        ]
      Output: [0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11]

     * Example 2:
     * Input:
     *   [
     *       [1,2,3],
     *       [1,2]
     *   ]
     * Output: [1,1,2,2,3]

     * Challenge
     *   Do it in O(N log k). N is the total number of integers. k is the number of arrays.
     * 
     */
    public int[] mergekSortedArrays(int[][] arrays) {
        if(arrays == null){
            return new int[0];
        }
        
        //return mergekSortedArrays_minHeap(arrays);
        return mergekSortedArrays_Divide_Conquer(arrays, 0, arrays.length - 1 );
    }
    
    public int[] mergekSortedArrays_minHeap(int[][] arrays) {
        if(arrays == null){
            return new int[0];
        }

        int k = arrays.length; 

        PriorityQueue<int[]> minHeap = new PriorityQueue<>(k, (l1, l2) -> Integer.compare(arrays[l1[0]][l1[1]], arrays[l2[0]][l2[1]]) );
        for(int i = 0; i < k; i++){
            if(arrays[i] != null && arrays[i].length > 0){
                minHeap.add(new int[]{i, 0});
            }
        }

        List<Integer> result = new LinkedList<>();
        int[] min;
        while(!minHeap.isEmpty()){
            min = minHeap.poll();
            result.add(arrays[min[0]][min[1]]);

            min[1]++;
            if(min[1] < arrays[min[0]].length ){
                minHeap.add(min);
            }
        }

        return result.stream().mapToInt(i -> i).toArray() ;
    }
        
    public int[] mergekSortedArrays_minHeap_1(int[][] arrays) {
        if(arrays == null){
            return new int[0];
        }

        int k = arrays.length; 

        PriorityQueue<MyArray> minHeap = new PriorityQueue<>(k, (l1, l2) -> Integer.compare(l1.top(), l2.top()) );
        for(int i = 0; i < k; i++){
            if(arrays[i] != null && arrays[i].length > 0){
                minHeap.add(new MyArray(arrays[i]));
            }
        }

        List<Integer> result = new LinkedList<>();
        MyArray min;
        while(!minHeap.isEmpty()){
            min = minHeap.poll();
            result.add(min.top());

            if(min.hasNext()){
                min.point++;
                minHeap.add(min);
            }
        }

        return result.stream().mapToInt(i -> i).toArray() ;
    }

    class MyArray{
        int[] array;
        int point = 0;

        MyArray(int[] array){
            this.array = array;
        }

        int top(){
            return this.array[point];
        }
        boolean hasNext(){
            return array != null && point < array.length - 1;
        }
    }
    
    public int[] mergekSortedArrays_Divide_Conquer(int[][] arrays, int l, int r) {
        if(l == r){
            return arrays[l];
        }

        int mid = l + (r - l) / 2;
        
        int[] first = mergekSortedArrays_Divide_Conquer(arrays, l, mid);
        int[] second = mergekSortedArrays_Divide_Conquer(arrays, mid + 1, r);
        
        return merge2SortedArrays(first, second);
    }
    
    private int[] merge2SortedArrays(int[] sortedList1, int[] sortedList2){
        if(sortedList1 == null){
            return sortedList2;  
        }else if(sortedList2 == null){
            return sortedList1;
        }
        
        int[] result = new int[sortedList1.length + sortedList2.length];
        
        int p = 0;
        int i = 0;
        int j = 0;
        while(i < sortedList1.length && j < sortedList2.length){
            if(sortedList1[i] < sortedList2[j]){
                result[p++] = sortedList1[i++];
            }else{
                result[p++] = sortedList2[j++];
            }
        }
        
        if(i < sortedList1.length){
            System.arraycopy(sortedList1, i, result, p, sortedList1.length - i);
        }
        
        if(j < sortedList2.length){
            System.arraycopy(sortedList2, j, result, p, sortedList2.length - j);
        }
        
        return result;
    }
    

    /**
     * @param args
     */
    public static void main(String[] args) {
        int[][] A = {{1, 0}};
        int[][] B = {{2}};

        MergeSortedArray sv = new MergeSortedArray();
        sv.mergeSortedArray_n(A[0], 1, B[0], 1);
    }

}
