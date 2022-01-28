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
 * 4 If there are two people who share the same number of friends as user, the smaller number is considered the most 
 *   likely person to know. 
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
 *   straight forward, simulate the process
 * 
 */
public class RecommendFriends {
    
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

        int recommend = -1;
        int maxCount = 0;
        int count;
        for(int p = 0; p < friends.length; p++ ){
            if(set.contains(p) ){
                continue;
            }

            count = 0;
            for(int f : friends[p]){
                if(set.contains(f)){
                    count++;
                }
            }

            if(maxCount < count){             
                maxCount = count;
                recommend = p;
            }
        }

        return recommend;
    }
}
