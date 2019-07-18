package fgafa;

import fgafa.util.Misc;
import org.junit.Test;

import java.util.*;
import java.util.stream.Collectors;

public class MyTest {


/**
 * Intersection of two sorted positive arrays.
 *
 * Input:
 *   arr1 = [1, 2, 3]
 *   arr2 = [1, 3, 5]
 * Output:
 *   [1, 3]
 *
 *   Input:
 *     arr1 = [1, 2, 3]                  (~5 elements)             n
 *     arr2 = [1, 2, 3, ..., 100000000]  (~10,000,000 elements)    m
 *   Output:
 *     [1, 2, 3]

 *    n * logm
 */



    public int[] findIntersection(int[] arr1, int[] arr2){
        if(null == arr1 || null == arr2){
            return new int[0];
        }

        int[] result = new int[Math.min(arr1.length, arr2.length)];
        int k = 0;
        for(int i = 0, j = 0; i < arr1.length && j < arr2.length; ){
            if(arr1[i] == arr2[j]){
                if(k == 0 || (k > 0 && result[k - 1] != arr1[i])){
                    result[k] = arr1[i];
                    k++;
                }

                i++;
                j++;
            }else if (arr1[i] < arr2[j]){
                i++;
            }else{
                j++;
            }
        }

        int[] ret = new int[k];
        System.arraycopy(result, 0, ret, 0, k);

        return ret;

    }

    @Test
    public void testFindIntersection(){
        Misc.printArray_Int(findIntersection(new int[]{1, 2, 3}, new int[]{1, 3, 5}));
    }


    //assume arr1 is smaller,  and arr2 is pretty long
    public int[] findIntersection_binarySearch(int[] arr1, int[] arr2){
        if(null == arr1 || null == arr2){
            return new int[0];
        }

        int[] result = new int[arr1.length];
        int k = 0;

        int left = 0;
        for(int i = 0; i < arr1.length ; i++){

            int right = arr2.length;
            int mid;
            while(left <= right){
                mid = left + (right - left) / 2;

                if(arr2[mid] == arr1[i]){
                    if(k == 0 || (k > 0 && result[k - 1] != arr1[i])){
                        result[k] = arr1[i];
                        k++;
                    }
                    break;
                }else if(arr2[mid] < arr1[i]){
                    left = mid + 1;
                }else{
                    right = mid - 1;
                }
            }

            //left--;
            left = Math.max(0, left - 1);
        }

        int[] ret = new int[k];
        System.arraycopy(result, 0, ret, 0, k);

        return ret;

    }

    @Test
    public void testFindIntersection_binarySearch(){
        Misc.printArray_Int(findIntersection_binarySearch(new int[]{1, 2, 3}, new int[]{1, 3, 5}));
    }

/**
 *
 * Input:
 *   arr: [1, 4, 20, 3, 10, 5]
 *   sum: 33
 * Output:
 *   (2, 4)   sum[4] - sum[1]
 *
 * Thought:
 *
 *    sum[0] 1
 *    sum[1] 1 + 4
 *    sum[2] sum[1] + arr[2]
 *
 *    HashMap  <value, index>
 */

    /* Time O(n)   */
    public int[] find(int[] arr, int target){
        if(null == arr || 0 == arr.length){
            return new int[0];
        }

        int sum = 0;
        Map<Integer, Integer> map = new HashMap<>();
        map.put(0, -1);

        for(int i = 0; i < arr.length; i++){
            sum += arr[i];

            int diff = sum - target;
            if(map.containsKey(diff)){
                return new int[]{map.get(diff) + 1, i};
            }

            map.putIfAbsent(sum, i);
        }

        return new int[0];
    }

    @Test
    public void testFind(){
        Misc.printArray_Int(find(new int[]{1, 4, 20, 3, 10, 5}, 5));
        Misc.printArray_Int(find(new int[]{1, 4, 20, 3, 10, 5}, 33));
    }

    @Test
    public void testDouble(){
        int x = 2;
        int y = 3;
        double z = (double)x / y;

        System.out.println(x/y);
        System.out.println(z);


        int[][] xx = { {0, 3}, {1, 2}, {2, 3}, {-2, 4}, {-4, 2} };

        for(int[] p : xx){
            System.out.println( " == " + (-(double)p[0]/p[1]));
        }

        List<Integer>[] buses = new ArrayList[3];
        Arrays.fill(buses, new ArrayList());

        Map<Integer, List<Integer>> stations = new HashMap<>();

        Set<Integer> target = new HashSet<>();  //<bus>
        target.addAll(stations.get(2));

    }

    @Test
    public void testIf() {
        int x = 2;

        if(x > 0){
            System.out.println(x + " > 0");
        }else if(x > 1){
            System.out.println(x + " > 1");
        }else if(x > 2){
            System.out.println(x + " > 2");
        }

    }


    @Test
    public void testHeap(){

        class Wraper{
            int key;
            int value;

            Wraper(int key, int value){
                this.key = key;
                this.value = value;
            }

            @Override
            public String toString() {
                return key + "-" + value + "\t";
            }
        }

        PriorityQueue<Wraper> minHeap = new PriorityQueue<>((p1, p2) -> (Integer.compare(p1.value, p2.value)));

        Wraper p0 = new Wraper(0, 20);
        Wraper p1 = new Wraper(1, 21);
        Wraper p2 = new Wraper(2, 22);
        minHeap.add(p0);
        minHeap.add(p1);
        minHeap.add(p2);
        System.out.println();
        minHeap.stream().forEach(System.out::print);

        p1.value = 31;
        System.out.println();
        minHeap.stream().forEach(System.out::print);

        minHeap.remove(p1);
        minHeap.add(p1);
        System.out.println();
        minHeap.stream().forEach(System.out::print);

    }

    class Tweet{
        int tweetId;
        long timestamp;

        Tweet(int tweetId, long timestamp){
            this.tweetId = tweetId;
            this.timestamp = timestamp;
        }
    }

    @Test
    public void testQueue2List(){

        Queue<Integer> queue = new LinkedList<>();
        queue.add(1);
        queue.add(2);

        List<Integer> list = new ArrayList<>(queue);
        System.out.println(list);


        Deque<Tweet> deque = new LinkedList<>();
        deque.add(new Tweet(1, 1001));
        deque.add(new Tweet(2, 1002));
        deque.add(new Tweet(3, 1003));

        List<Integer> list2 = deque.stream().map(t -> t.tweetId).collect(Collectors.toList());
        Collections.reverse(list2);

        System.out.println(list2);
        System.out.println(deque.size());


        LinkedList<Integer> list3 = new LinkedList<>();
        for(Tweet t : deque){
            list3.addFirst(t.tweetId);
        }

        System.out.println(list3);
        System.out.println(deque.size());

    }

    private List testList(){
        LinkedList<Integer> result = new LinkedList<>();
        result.addFirst(11);

        return result;
    }

    private void valid(){
        valid(3);
    }

    private void valid(int i, int j){
        if(i < 0 || i >= 5 || j < 0 || j >= 5 || i > j){
            throw new IllegalArgumentException("  ");
        }
    }

    private void valid(int i){
        if(i < 0 || i >= 5){
            throw new IllegalArgumentException("  ");
        }
    }
}
