package fgafa.dailyCoding;

import java.util.Arrays;
import java.util.Random;
import java.util.stream.Stream;

/**
 * Given a stream of elements too large to store in memory, pick a random element from the stream with uniform probability.
 *
 *
 *  Tags:  Facebook,  Random, Stream
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
