package fgafa.dailyCoding;

import java.util.Random;

/**
 *
 * Given a function that generates perfectly random numbers between 1 and k (inclusive), where k is an input,
 * write a function that shuffles a deck of cards represented as an array using only swaps.
 *
 * It should run in O(N) time.
 *
 * Hint: Make sure each one of the 52! permutations of the deck is equally likely.
 *
 * Tags: facebook
 */

public class ShuffleCards {

    public void shuffle(int[] cards){
        for(int i = 0, j = cards.length; i < cards.length; i++, j--) {
            int pick = random(j) + i - 1;

            //swap
            int tmp = cards[pick];
            cards[pick] = cards[i];
            cards[i] = tmp;
        }
    }


    //Given
    final static Random random = new Random();
    public int random(int k){
        return random(k) + 1;
    }
}
