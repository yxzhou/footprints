package fgafa.dailyCoding;

import java.util.LinkedList;
import java.util.Random;


/**
 *
 * Q1:
 * Given a function that generates perfectly random numbers between 1 and k (inclusive), where k is an input,
 * write a function that shuffles a deck of cards represented as an array using only swaps.
 *
 * It should run in O(N) time.
 *
 * Hint: Make sure each one of the 52! permutations of the deck is equally likely.
 *
 * Tags: facebook
 *
 * Q2:
 * Given a linked list, uniformly shuffle the nodes. What if we want to prioritize space over time?
 *
 * Tags: Apple
 *
 */

public class ShuffleCards {

    public void shuffle(int[] cards){
        for(int j = cards.length; j > 1; j--) {
            int pick = random(j);

            //swap pick and j
            int tmp = cards[pick];
            cards[pick] = cards[j];
            cards[j] = tmp;
        }
    }

    public void shuffle(LinkedList<Integer> cards){


    }


    //Given
    final static Random random = new Random();
    public int random(int k){
        return random(k) + 1;
    }
}
