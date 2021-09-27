package design.others;

import org.junit.Test;

import java.util.*;

/**
 *
 *
 * Design a Leaderboard class, which has 3 functions:
 *
 * addScore(playerId, score): Update the leaderboard by adding score to the given player's score. If there is no player with such id in the leaderboard, add him to the leaderboard with the given score.
 * top(K): Return the score sum of the top K players.
 * reset(playerId): Reset the score of the player with the given id to 0. It is guaranteed that the player was added to the leaderboard before calling this function.
 * Initially, the leaderboard is empty.
 *
 *
 *
 * Example 1:
 *
 * Input:
 * ["Leaderboard","addScore","addScore","addScore","addScore","addScore","top","reset","reset","addScore","top"]
 * [[],[1,73],[2,56],[3,39],[4,51],[5,4],[1],[1],[2],[2,51],[3]]
 * Output:
 * [null,null,null,null,null,null,73,null,null,null,141]
 *
 * Explanation:
 * Leaderboard leaderboard = new Leaderboard ();
 * leaderboard.addScore(1,73);   // leaderboard = [[1,73]];
 * leaderboard.addScore(2,56);   // leaderboard = [[1,73],[2,56]];
 * leaderboard.addScore(3,39);   // leaderboard = [[1,73],[2,56],[3,39]];
 * leaderboard.addScore(4,51);   // leaderboard = [[1,73],[2,56],[3,39],[4,51]];
 * leaderboard.addScore(5,4);    // leaderboard = [[1,73],[2,56],[3,39],[4,51],[5,4]];
 * leaderboard.top(1);           // returns 73;
 * leaderboard.reset(1);         // leaderboard = [[2,56],[3,39],[4,51],[5,4]];
 * leaderboard.reset(2);         // leaderboard = [[3,39],[4,51],[5,4]];
 * leaderboard.addScore(2,51);   // leaderboard = [[2,51],[3,39],[4,51],[5,4]];
 * leaderboard.top(3);           // returns 141 = 51 + 51 + 39;
 *
 *
 * Constraints:
 *
 * 1 <= playerId, K <= 10000
 * It's guaranteed that K is less than or equal to the current number of players.
 * 1 <= score <= 100
 * There will be at most 1000 function calls.
 *
 *
 */

public class Leaderboard {

    /**
     *
     *                      addScore                   reset                 top(k)
     * PriorityQueue all: heap.offer() O(logn)   heap.remove(),O(n)    heap.peek(),O(k*logn)
     *                    or update ??
     *
     * ( 1 <= score <= 100), define m as the number of distinct score
     * HashMap + HashMap:     O(1)                 O(1)                  O(m*logm )
     * HashMap + TreeMap      O(logm )             O(logm )              O(K)
     * HashMap + int array    O(logn )             O(logn)               O(K)             --best
     *
     */

    /**  HashMap + int array */

    Map<Integer, Integer> map = new HashMap<>(); //Map<playerid, score>
    int[] scores = new int[10000]; //1 <= playerId, K <= 10000

    int end = 0;

    public Leaderboard() {

    }

    public void addScore(int playerId, int score) {
        int p = 0;
        if(map.containsKey(playerId)){
            p = Arrays.binarySearch(scores, 0, end, map.get(playerId));
            System.arraycopy(scores, p + 1, scores, p, end - p - 1);
            end--;

            score += map.get(playerId);
        }

        map.put(playerId, score);

        p = Arrays.binarySearch(scores, 0, end, score);
        if(p < 0){
            p = -(p + 1);
        }

        System.arraycopy(scores, p, scores, p + 1, end - p);
        scores[p] = score;
        end++;
    }

    public int top(int K) {
        int sum = 0;

        for(int i = end - K; i < end; i++){
            sum += scores[i];
        }

        return sum;
    }

    public void reset(int playerId) {
        int score = map.remove(playerId);
        int p = Arrays.binarySearch(scores, 0, end, score);
        System.arraycopy(scores, p + 1, scores, p, end - p - 1);
        end--;
    }


    @Test public void test(){

        for(int i = 3; i < 21; i++){
            System.out.println((int)Math.log10(i));
        }


//        ["Leaderboard","addScore","addScore","addScore","addScore","addScore","top","reset","reset","addScore","top"]
//[[],[1,73],[2,56],[3,39],[4,51],[5,4],[1],[1],[2],[2,51],[3]]

        Leaderboard sv = new Leaderboard();

        sv.addScore(1, 73);
        sv.addScore(2, 56);
        sv.addScore(3, 39);
        sv.addScore(4, 51);
        sv.addScore(5, 4);

        System.out.println(sv.top(1));

        sv.reset(1);
        sv.reset(2);

        sv.addScore(2, 51);

        System.out.println(sv.top(3));

    }

}
