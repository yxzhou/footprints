/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package easy;

import java.util.HashSet;
import java.util.Set;

/**
 * _https://www.lintcode.com/problem/1402
 * 
 * Give n personal friends list, Tell you who the user is. Find the person that user is most likely to know. (He and the
 * user have the most common friends and he is not a friend of user)
 *
 * Constraints:
 * 1 n <= 500. 
 * 2 The relationship between friends is mutual. (if B appears on a's buddy list, a will appear on B's friends list). 
 * 3 Each person's friend relationship does not exceed m, m <= 3000. 
 * 4 If there are two people who share the same number of friends as user, the friends alphabetical order is considered 
 *   the mostlikely person to know. 
 * 5 If user and all strangers have no common friends, return -1. 
 * 
 * Example 1:
 * Input:list = [[1,2,3],[0,4],[0,4],[0,4],[1,2,3]], user = 0 
 * Output:4 
 * Explanation:0 and 4 are not friends, and they have 3 common friends. So 4 is the 0 most likely to know. 
 * 
 * Example 2:
 * Iuput: list = [[1,2,3,5],[0,4,5],[0,4,5],[0,5],[1,2],[0,1,2,3]], user = 0 
 * Output :4. 
 * Explanation: Although 5 and 0 have 3 common friends, 4 and 0 only have 2 common friends, but 5 is a 0's friend, so 4 
 * is the 0 most likely to know.
 *
 * Thoughts:
 *   The only difference to RecommendFriends is Constraints #4. 
 *
 */
public class RecommendFriendsII {
    /**
     * @param friends: people's friends
     * @param user: the user's id
     * @return the person who most likely to know
     */
    public int recommendFriends(int[][] friends, int user) {
        if(friends == null || user < 0 || friends.length <= user ){
            return -1;
        }

        Set<Integer> set = new HashSet<>();
        for(int f : friends[user]){
            set.add(f);
        }
        set.add(user);

        int n = friends[user].length;

        int recommend = -1;
        int[][] commonFriends = new int[2][n + 1]; // includs two commonFriends, both array's the first element is size. 
        int max = 0;  // 0 or 1
        for(int p = 0; p < friends.length; p++ ){
            if(set.contains(p) ){
                continue;
            }

            commonFriends(set, friends[p], commonFriends[max ^ 1]);
            if(compare(commonFriends[max], commonFriends[max ^ 1]) < 0 ){
     
                max = max ^ 1;
                recommend = p;
            }
        }

        return commonFriends[max][0] > 1 ? recommend : -1;
    }

    private void commonFriends(Set<Integer> user, int[] person, int[] commonFriends){
        //Arrays.sort(person);

        int i = 1;
        for(int p : person){
            if(user.contains(p)){
                commonFriends[i++] = p;
            }
        }

        commonFriends[0] = i;
    }

    private int compare(int[] a, int[] b){
        int diff = a[0] - b[0];
        if(diff != 0){
            return diff;
        }

        for(int i = 1; i < a[0]; i++ ){
            diff = b[i] - a[i];
            if(diff != 0 ){
                return diff;
            }
        }

        return 0;
    }
}
