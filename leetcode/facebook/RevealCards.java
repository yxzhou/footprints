package leetcode.facebook;


import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

/**
 * In a deck of cards, every card has a unique integer.  You can order the deck in any order you want.

 Initially, all the cards start face down (unrevealed) in one deck.

 Now, you do the following steps repeatedly, until all cards are revealed:

 Take the top card of the deck, reveal it, and take it out of the deck.
 If there are still cards in the deck, put the next top card of the deck at the bottom of the deck.
 If there are still unrevealed cards, go back to step 1.  Otherwise, stop.
 Return an ordering of the deck that would reveal the cards in increasing order.

 The first entry in the answer is considered to be the top of the deck.



 Example 1:

 Input: [17,13,11,2,3,5,7]
 Output: [2,13,3,11,5,17,7]
 Explanation:
 We get the deck in the order [17,13,11,2,3,5,7] (this order doesn't matter), and reorder it.
 After reordering, the deck starts as [2,13,3,11,5,17,7], where 2 is the top of the deck.
 We reveal 2, and move 13 to the bottom.  The deck is now [3,11,5,17,7,13].
 We reveal 3, and move 11 to the bottom.  The deck is now [5,17,7,13,11].
 We reveal 5, and move 17 to the bottom.  The deck is now [7,13,11,17].
 We reveal 7, and move 13 to the bottom.  The deck is now [11,17,13].
 We reveal 11, and move 17 to the bottom.  The deck is now [13,17].
 We reveal 13, and move 17 to the bottom.  The deck is now [17].
 We reveal 17.
 Since all the cards revealed are in increasing order, the answer is correct.


 Note:

 1 <= A.length <= 1000
 1 <= A[i] <= 10^6
 A[i] != A[j] for all i != j
 *
 * tags:  facebook
 */

public class RevealCards {

    /**
     *   simulation the output with queue
     *
     *   Time O(nlogn),  space O(n)
     */
    public int[] deckRevealedIncreasing(int[] deck) {
        if(null == deck || deck.length < 2){
            return deck;
        }

        Arrays.sort(deck);

        int len = deck.length;
        Queue<Integer> queue = new LinkedList<>();
        for(int i = 0; i < len; i++){
            queue.add(i);
        }

        int[] result = new int[len];
        for(int i = 0; i < len; i++){
            result[queue.poll()] = deck[i];

            queue.add(queue.poll());
        }

        return result;
    }

    /**
     *   simulation the deck backward
     *
     *   Time O(n) or O(n*n)  ?? depends System.arraycopy() ,  space O(n)
     *
     */
    public int[] deckRevealedIncreasing2(int[] deck) {
        if(null == deck || deck.length < 2){
            return deck;
        }

        Arrays.sort(deck);

        int len = deck.length;
        int[][] result  = new int[2][len];
        int i = 0;
        int last = len - 1;
        System.arraycopy(deck, len - 2, result[i], len - 2, 2);

        for(int k = len - 3; k >= 0; k--){
            int j = (i + 1) % 2;
            result[j][k] = deck[k];
            result[j][k + 1] = result[i][last];

            System.arraycopy(result[i], k + 1, result[j], k + 2, len - k - 2);

            i = j;
        }

        return result[i];
    }

    @Test public void test(){
        Assert.assertArrayEquals(new int[]{2,13,3,11,5,17,7}, deckRevealedIncreasing(new int[]{17,13,11,2,3,5,7}));
        Assert.assertArrayEquals(new int[]{2,13,3,11,5,17,7}, deckRevealedIncreasing2(new int[]{17,13,11,2,3,5,7}));
    }

}
