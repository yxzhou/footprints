package fgafa.design.others;

import org.junit.Assert;
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

public class Leaderboard2 {

    /**
     *
     *                      addScore                   reset                 top(k)
     * PriorityQueue all: heap.offer() O(logn)   heap.remove(),O(n)    heap.peek(),O(k*logn)
     *                    or update ??
     *
     * ( 1 <= score <= 100), define m as the number of distinct score
     * HashMap + HashMap:     O(1)                 O(1)                  O(m log m)
     * TreeMap                O(log m)             O(log m)              O(K)
     *
     */

    Map<Integer, Integer> map; // <playerId, score>
    TreeMap<Integer, Integer> buckets;  // <score, count>

    public Leaderboard2() {
        map = new HashMap<>();
        buckets = new TreeMap<>(Collections.reverseOrder()); // 1 <= score <= 100 ??
    }

    /**  O(logm) */
    public void addScore(int playerId, int score) {
        Integer preScore = map.get(playerId);
        Integer newScore = (preScore == null ? score : score + preScore);

        map.put(playerId, newScore);

        if(preScore != null){
            buckets.put(preScore, buckets.get(preScore) - 1);
            if(buckets.get(preScore) == 0){
                buckets.remove(preScore);
            }
        }



        buckets.put(newScore, buckets.getOrDefault(newScore, 0) + 1);
    }

    /**  O(K) */
    public int top(int K) {
        int sum = 0;

        Iterator<Integer> it = buckets.keySet().iterator();

        int score;
        int count;
        while( K > 0 && it.hasNext() ){
            score = it.next();
            count = buckets.get(score);

            if(K < count){
                sum += K * score;
                K = 0;
            }else{
                sum += count * score;
                K -= count;
            }
        }

        return sum;
    }

    /**  O(logm) */
    public void reset(int playerId) {
        if(!map.containsKey(playerId)) {
            return;
        }

        Integer score = map.get(playerId);

        buckets.put(score, buckets.get(score) - 1);
        if(buckets.get(score) == 0){
            buckets.remove(score);
        }

        map.remove(playerId);
    }


    @Test
    public void test(){

//        ["Leaderboard","addScore","addScore","addScore","addScore","addScore","top","reset","reset","addScore","top"]
//[[],[1,73],[2,56],[3,39],[4,51],[5,4],[1],[1],[2],[2,51],[3]]

        Leaderboard2 sv = new Leaderboard2();

        sv.addScore(1, 73);
        sv.addScore(2, 56);
        sv.addScore(3, 39);
        sv.addScore(4, 51);
        sv.addScore(5, 4);

        Assert.assertEquals(73, sv.top(1));

        sv.reset(1);
        sv.reset(2);

        sv.addScore(2, 51);

        Assert.assertEquals(141, sv.top(3));

    }

}
