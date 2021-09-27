package dailyCoding2.pinterest;

import java.util.Random;

/**
 *
 * This problem was asked by Pinterest.
 *
 * At a party, there is a single person who everyone knows, but who does not know anyone in return (the "celebrity").
 * To help figure out who this is, you have access to an O(1) method called knows(a, b), which returns True if person a knows person b, else False.
 *
 * Given a list of N people and the above operation, find a way to identify the celebrity in O(N) time.
 *
 */

public class Celebrity {

    public int getCelebrity(int n){
        int curr = 0;

        for(int next = 1; next < n; next++){
            if(knows(curr, next)){
                curr = next;
            }
        }

        return curr;
    }

    Random random = new Random();
    private boolean knows(int id1, int id2){
        return 1 == random.nextInt(2);
    }


}
