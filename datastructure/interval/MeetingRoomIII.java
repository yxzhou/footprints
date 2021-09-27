package datastructure.interval;

import java.util.Arrays;

/**
 *
 * Q3,  determine if a interval can be placed in the current meeting list
 *
 * you have a list intervals of current meetings, and some meeting rooms with start and end timestamp.When a stream of new meeting ask coming in,
 * judge one by one whether they can be placed in the current meeting list without overlapping..A meeting room can only hold one meeting at a time. Each inquiry is independent.
 *
 * Ensure that Intervals can be arranged in rooms meeting rooms
 * Ensure that the end time of any meeting is not greater than 50000
 * |Intervals|<=50000
 * |ask|<=50000
 * rooms<=20
 *
 * Example:
 * Input:  Intervals:[[1,2],[4,5],[8,10]], rooms = 1, ask: [[2,3],[3,4],[4,5],[5,6]]
 * Output: [true, true, false, true]
 *
 * Explanation:
 * For the ask of [2,3], we can arrange a meeting room room0.
 * The following is the meeting list of room0:
 * [[1,2], [2,3], [4,5], [8,10]]
 * For the ask of [3,4], we can arrange a meeting room room0.
 * The following is the meeting list of room0:
 * [[1,2], [3,4], [4,5], [8,10]]
 *
 */

public class MeetingRoomIII {

    final int N = 50001; //the start and end time of meeting is [1, 50000]
    int[] counts = new int[N];
    int[] sums = new int[N];
    public boolean[] meetingRoomIII(int[][] intervals, int rooms, int[][] ask) {
        //init
        Arrays.fill(counts, 0);
        Arrays.fill(sums, 0);

        //at timestamp i, counts[i] is 0 default, +1 when need hold a new room, -1 when free a room.
        for (int[] interval : intervals) {
            counts[interval[0]]++;
            counts[interval[1]]--;
        }
        //counts[i] marks the number of rooms hold at time i.
        for (int i = 1; i < N; i++) {
            counts[i] += counts[i - 1];
        }

        /**  It takes O(ask[j][1] - ask[j][0])  to check if counts[i] equals to rooms in ask[j] ,
         *.   Optimize:  introduce sums[i] as the times of rooms full hold.
         */
        for (int i = 1; i < N; i++) {
            if (counts[i] == rooms) {
                sums[i] = sums[i - 1] + 1;
            } else {
                sums[i] = sums[i - 1];
            }
        }

        int m = ask.length;
        boolean[] results = new boolean[m];
        for (int i = 0; i < m; i++) {
            results[i] = (counts[ask[i][0]] < rooms && sums[ask[i][0]] == sums[ask[i][1] - 1]);
        }

        return results;
    }

}
