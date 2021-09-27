package util.random;

import java.util.Arrays;
import java.util.Random;
import java.util.stream.Stream;

/**
 * Given a stream of elements too large to store in memory, pick a random element from the stream with uniform probability.
 *
 *  Tags:  Facebook,  random, Stream
 *
 * An efficient program to randomly select a number from stream of numbers, with O(1) space
 * You are allowed to use only O(1) space and the input is in the form of stream, so cannot store the previously seen numbers.
 *
 * So how do we generate a random number from the whole stream such that the probability of picking any number is 1/n. with O(1) extra space?
 * This problem is a variation of Reservoir Sampling. Here the value of k is 1.
 *
 * 1) Initialize count as 0, count is used to store count of numbers seen so far in stream.
 * 2) For each number x from stream, do following
 *   a) Increment count by 1.
 *   b) If count is 1, set result as x, and return result.
 *   c) Generate a random number from 0 to count-1. Let the generated random number be i.
 *   d) If i is equal to count � 1, update the result as x.
 *
 * To simplify proof, let us first consider the last element, the last element replaces the previously stored result with 1/n probability.
 * So probability of getting last element as result is 1/n.
 * Let us now talk about second last element. When second last element processed first time, the probability that it replaced the previous result is 1/(n-1).
 * The probability that previous result stays when nth item is considered is (n-1)/n.
 * So probability that the second last element is picked in last iteration is [1/(n-1)] * [(n-1)/n] which is 1/n.
 * Similarly, we can prove for third last element and others.
 *
 */

public class RandomOfStream {

    class Packet{
        int result;
        int count = 1;
    }

    public int random(Stream<Integer> stream){

        final Packet myPacket = new Packet();
        Random random = new Random();

        //stream.forEach();
        stream.forEachOrdered( x -> {
            if(myPacket.count == 1){
                myPacket.result = x;
            }else{
                if(random.nextInt(myPacket.count) == 0){
                    myPacket.result = x;
                }
            }

            myPacket.count++;
        });

        return myPacket.result;
    }

    public static void main(String[] args){

        Integer[] ints = {1, 3, 5, 6, 7, 9};

        //verify forEach
        for(int i = 0; i < 10; i++) {
            System.out.println("\n " + i);
            Arrays.stream(ints).forEach(x -> System.out.printf("\t" + x));
        }


        RandomOfStream sv = new RandomOfStream();

        //try 10 times
        System.out.println();
        for(int i = 0; i < 10; i++){
            //Arrays.stream(ints, 0, ints.length)
            //Stream.of(ints)
            //Arrays.stream(ints)
            System.out.println(sv.random(Arrays.stream(ints)));
        }

    }

}
