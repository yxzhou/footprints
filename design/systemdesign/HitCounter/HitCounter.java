package fgafa.design.systemdesign.HitCounter;

/**
 * Design a hit counter which counts the number of hits received in the past 5 minutes.
 *
 * Each function accepts a timestamp parameter (in seconds granularity) and you may assume that calls are being made to the system in chronological order (ie, the timestamp is monotonically increasing). You may assume that the earliest timestamp starts at 1.
 *
 * It is possible that several hits arrive roughly at the same time.
 *
 * Example:
 *
 * HitCounter counter = new HitCounter();
 *
 * // hit at timestamp 1.
 * counter.hit(1);
 *
 * // hit at timestamp 2.
 * counter.hit(2);
 *
 * // hit at timestamp 3.
 * counter.hit(3);
 *
 * // get hits at timestamp 4, should return 3.
 * counter.getHits(4);
 *
 * // hit at timestamp 300.
 * counter.hit(300);
 *
 * // get hits at timestamp 300, should return 4.
 * counter.getHits(300);
 *
 * // get hits at timestamp 301, should return 3.
 * counter.getHits(301);
 *
 *
 * Follow up:
 * What if the number of hits per second could be very large? Does your design scale?
 *
 *
 */

public class HitCounter {
    class Entity{
        int timestamp;
        int count;

        Entity(int timestamp, int count){
            this.timestamp = timestamp;
            this.count = count;
        }
    }

    Entity[] circle = new Entity[300];


    /** Initialize your data structure here. */
    public HitCounter() {
        circle = new Entity[300];
    }

    /** Record a hit.
     @param timestamp - The current timestamp (in seconds granularity). */
    public void hit(int timestamp) {
        int index = timestamp % 300;

        if(circle[index] == null){
            circle[index] = new Entity(timestamp, 1);
        }else if(circle[index].timestamp < timestamp){
            circle[index].timestamp = timestamp;
            circle[index].count = 1;
        }else{ // ==
            circle[index].count++;
        }
    }

    /** Return the number of hits in the past 5 minutes.
     @param timestamp - The current timestamp (in seconds granularity). */
    public int getHits(int timestamp) {
        // (start, end]
        int start = timestamp - 300;

        int sum = 0;
        for(int i = 0; i < 300; i++ ){
            if(circle[i] != null && circle[i].timestamp > start ){
                sum += circle[i].count;
            }
        }

        return sum;
    }
}

/**
 * Your HitCounter object will be instantiated and called as such:
 * HitCounter obj = new HitCounter();
 * obj.hit(timestamp);
 * int param_2 = obj.getHits(timestamp);
 */
