package fgafa.mianjing.fb;


/**
 * lintcode #1393
 *
 * Some people will make friend requests. The list of their ages is given and ages[i] is the age of the ith person.
 *
 * If any of the following conditions is met, A will not make a friend request to B (b! =A):
 *
 * age[B] <= 0.5 * age[A] + 7
 * age[B] > age[A]
 * age[B] > 100 && age[A] < 100
 * Otherwise, A will send a friend request to B.
 * Note that if A requests B, B does not necessarily request A. Also, people will not friend request themselves.
 *
 * How many friend requests have been sent?
 *
 * Example
 * Example 1:
 *
 * Input: [16,16]
 * Output: 2
 * Explanation: 2 people friend request each other.
 * Example 2:
 *
 * Input: [16,17,18]
 * Output: 2
 * Explanation: Friend requests are made 17 -> 16, 18 -> 17.
 * Example 3:
 *
 * Input: [20,30,100,110,120]
 * Output: 3
 * Explanation: Friend requests are made 110 -> 100, 120 -> 110, 120 -> 100.
 * Notice
 * 1 <= ages.length <= 20000.
 * 1 <= ages[i] <= 120.
 *
 *
 *
 */

public class FriendRequests {

    public int numFriendRequests(int[] ages) {

        if(ages == null || ages.length < 2){
            return 0;
        }

        int[] counts = new int[121]; //count for age from 1 to 120, default it's 0
        for(int age : ages){
            counts[age]++;
        }

        int[] sums = new int[121];
        // for(int age = 1; age < 121; age++){
        //     sums[age] = sums[age - 1] + counts[age];
        // }

        int result = 0;

        int floor;
        for(int age = 1; age < 121; age++){
            sums[age] = sums[age - 1] + counts[age];

            if(counts[age] == 0){
                continue;
            }

            floor = age / 2 + 7;
            if(age > floor){
                result += counts[age] * ( sums[age] - sums[floor] - 1);
            }
        }

        return result;
    }

}
