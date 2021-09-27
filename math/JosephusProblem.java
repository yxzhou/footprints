package math;

import java.util.LinkedList;

/**
 *  _https://en.wikipedia.org/wiki/Josephus_problem
 *
 *   n个人（编号0~(n-1))，从0开始报数，报到(m-1)的退出，剩下的人继续从0开始报数。求最后一个人的编号。
 *   n个人（编号1~n)，从1开始报数，报到m的退出，剩下的人继续从1开始报数。求最后一个人的编号。
 *
 *   n people ( the index is from 0 to n - 1 ) are standing in a circle,
 *     counting begins at index 1, the m_th is executed (remove from the circle),
 *     counting begins at the next, the m_th is executed (remove from the circle),
 *     repeat until there is only 1 person left.
 */

public class JosephusProblem {

    class Node{
        int value;
        Node next;

        Node(int value ){
            this.value = value;
        }
    }

    /* Time O(m * n), Space O(n) */
    public int josephus_ListNode(int n, int m){
        if(n <= 1){
            return n;
        }

        Node dummy = new Node(0);

        //init the circle
        Node curr = dummy;
        for(int i = 1; i <= n; i++){
            curr.next = new Node(i);
            curr = curr.next;
        }
        curr.next = dummy.next;

        //
        int size = n;
        while(curr != curr.next){
            for(int i = 1; i < m % size; i++){
                curr = curr.next;
            }

            curr.next = curr.next.next;
            size--;
        }

        return curr.value;
    }


    /* Time O(n*n), Space O(n) */
    public int josephus_linkedList(int n, int m){
        if(n <= 1){
            return n;
        }

        //init the circle
        LinkedList<Integer> circle = new LinkedList<>();
        for(int i = 1; i <= n; i++){
            circle.add(i);
        }

        //
        int startIndex = 0;
        while(circle.size() > 1){
            startIndex = (startIndex + m - 1) % circle.size();
            circle.remove(startIndex);  //note, it's a linkedlist, so time complexity is O(startIndex)
        }

        return circle.get(0);
    }


    /* Time O(n), Space O(1)
    *
    *
    *  Define f[i] is the last one when i people and m.
    *
    *  1 start/base case: when i = 1, the last person's index is 0.  f(1) = 0
    *  2 final case:  when n people, the last person's index is f(n)
    *                 when n-1 people, the last person's index is f(n-1)
    *  3 Let's find the recurrence with f(n) and f(n - 1)
    *    To n people and m, the list is [1, 2, --, n], the m%n will be executed. set k = m%n, the last person's index is f(n)
    *    After the index k was removed, the list is [1, 2, --, k - 1, k + 1, --, n],
    *    Now it need count from k + 1,  the list is [k + 1, k+ 2, --, n, 1, 2, --, k- 1],  it's n-1 people, what's the last person's index?
    *    If changes/marks the indexes in the above list to [1, 2, --, n - k, n - k + 1, --, n - 1], the last person's index is f(n - 1),
    *    so before changing, the last person's index is (f(n - 1) + k) % n = (f(n - 1) + m) % n
    *
    *
    * */
    public int josephus_dp(int n, int m){
        if(n <= 1){
            return n;
        }


        int dp = 1;  // when n = 1
        for(int i = 2; i <= n; i++){
            dp = (dp + m) % n;
        }

        return dp;
    }


}
