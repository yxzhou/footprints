package companyTag.facebook;

import junit.framework.Assert;
import org.junit.Test;

import java.util.*;

/**
 *
 * Given a non-empty array of unique positive integers A, consider the following graph:

 There are A.length nodes, labelled A[0] to A[A.length - 1];
 There is an edge between A[i] and A[j] if and only if A[i] and A[j] share a common factor greater than 1.
 Return the size of the largest connected component in the graph.



 Example 1:

 Input: [4,6,15,35]
 Output: 4

 Example 2:

 Input: [20,50,9,63]
 Output: 2

 Example 3:

 Input: [2,3,6,7,4,12,21,39]
 Output: 8

 Note:

 1 <= A.length <= 20000
 1 <= A[i] <= 100000

 */

public class LargestComponentSizeByCommonFactor {
    public int largestComponentSize(int[] A) {
        if(null == A){
            return 0;
        }

        //get all primes that will be used in A
        int max = A[0];
        for(int a : A){
            max = Math.max(max, a);
        }

        int half = max / 2;
        boolean[] notPrime = new boolean[half + 1]; //default all are false
        List<Integer> primes = new ArrayList<>();
        for(int i = 2; i <= half; i++){
            if(!notPrime[i]){
                primes.add(i);

                for(long k = (long)i * i; k <= half; k += i){
                    notPrime[(int)k] = true;
                }
            }
        }

        //group vertex by common factor
        Map<Integer, List<Integer>> prime2vertex = new HashMap<>();
        for(int prime : primes){
            prime2vertex.put(prime, new ArrayList<>());
        }
        Map<Integer, List<Integer>> vertex2Prime = new HashMap<>();
        for(int i = 0; i < A.length; i++){
            vertex2Prime.put(i, new ArrayList<>());
        }
        for(int i = 0; i < A.length; i++){
            for(int prime : primes){
                if(A[i] % prime == 0){
                    prime2vertex.get(prime).add(i);
                    vertex2Prime.get(i).add(prime);
                }
            }
        }

        //find the largest connected component
        Queue<Integer> group = new LinkedList<>();
        boolean[] isVisited = new boolean[A.length]; //default all are false

        int result = 0;
        for(int i = 0; i < A.length; i++){
            if(!isVisited[i]){
                int counter = 0;

                group.add(i);
                isVisited[i] = true;
                while(!group.isEmpty()){
                    int curr = group.poll();
                    counter++;
                    for(int prime : vertex2Prime.get(curr)){
                        for(int next : prime2vertex.get(prime)){
                            if(!isVisited[next]){
                                group.add(next);
                                isVisited[next] = true;
                            }
                        }
                    }
                }

                result = Math.max(result, counter);
            }
        }

        return result;
    }

    public int largestComponentSize_n(int[] A) {
        if(null == A){
            return 0;
        }

        //get primes that be used in A[i] one by one,  and group vertex by common factor
        List<Integer>[] vertex2Prime = new ArrayList[A.length];
        Map<Integer, List<Integer>> prime2vertex = new HashMap<>();
        for (int i = 0; i < A.length; ++i) {
            vertex2Prime[i] = new ArrayList<>();

            if(A[i] == 1){
                continue;
            }

            int d = 2, x = A[i];
            while (d * d <= x) {
                if (x % d == 0) {
                    while (x % d == 0) {
                        x /= d;
                    }
                    vertex2Prime[i].add(d);
                }

                d++;
            }

            if (x > 1 || vertex2Prime[i].isEmpty()) {
                vertex2Prime[i].add(x);
            }

            for(int prime : vertex2Prime[i]){
                prime2vertex.putIfAbsent(prime, new ArrayList<>());
                prime2vertex.get(prime).add(i);
            }
        }

        //find the largest connected component
        Queue<Integer> group = new LinkedList<>();
        boolean[] isVisited = new boolean[A.length]; //default all are false

        int result = 0;
        for(int i = 0; i < A.length; i++){
            if(!isVisited[i]){
                int counter = 0;

                group.add(i);
                isVisited[i] = true;
                while(!group.isEmpty()){
                    int curr = group.poll();
                    counter++;
                    for(int prime : vertex2Prime[curr]){
                        for(int next : prime2vertex.get(prime)){
                            if(!isVisited[next]){
                                group.add(next);
                                isVisited[next] = true;
                            }
                        }
                    }
                }

                result = Math.max(result, counter);
            }
        }

        return result;
    }

    @Test public void test(){
        int i = 100000 / 2;
        long k = (long)i * i;
        System.out.println(k);

        Assert.assertEquals(4, largestComponentSize(new int[]{4, 6, 15, 35}));
        Assert.assertEquals(2, largestComponentSize(new int[]{20, 50, 9, 63}));
        Assert.assertEquals(8, largestComponentSize(new int[]{2,3,6,7,4,12,21,39}));

        Assert.assertEquals(11, largestComponentSize(new int[]{1,2,3,4,5,6,7,8,9,10,11,12,13,14}));


        Assert.assertEquals(4, largestComponentSize_n(new int[]{4, 6, 15, 35}));
        Assert.assertEquals(2, largestComponentSize_n(new int[]{20, 50, 9, 63}));
        Assert.assertEquals(8, largestComponentSize_n(new int[]{2,3,6,7,4,12,21,39}));

        Assert.assertEquals(11, largestComponentSize_n(new int[]{1,2,3,4,5,6,7,8,9,10,11,12,13,14}));
    }
}
