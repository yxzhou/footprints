package design.systemdesign.HitCounter;

import org.junit.Assert;
import org.junit.Test;

import java.util.LinkedList;
import java.util.Queue;

public class HitCounter4 {

    Queue<Integer> queue;
    static int K = 300;

    /** Initialize your data structure here. */
    public HitCounter4() {
        this.queue = new LinkedList<>();
    }

    /** Record a hit.
     @param timestamp - The current timestamp (in seconds granularity). */
    public void hit(int timestamp) {
        queue.offer(timestamp);
    }

    /** Return the number of hits in the past 5 minutes.
     @param timestamp - The current timestamp (in seconds granularity). */
    public int getHits(int timestamp) {
        int start = timestamp - K;
        while(!queue.isEmpty() && queue.peek() <= start ){
            queue.poll();
        }

        return queue.size();
    }


    @Test public void test(){

        String[] cmds = {"HitCounter","hit","hit","hit","getHits","getHits","getHits","getHits","getHits","hit","getHits"};
        int[][] values = {{},{2},{3},{4},{300},{301},{302},{303},{304},{501},{600}};

        Integer[] expects = {null,null,null,null,3,3,2,1,0,null,1};

        HitCounter4 sv = new HitCounter4();

        for(int i = 0, end = cmds.length; i < end; i++){
            if(cmds[i].equals("hit")){
                sv.hit(values[i][0]);
            }else if(cmds[i].equals("getHits")){
                Assert.assertEquals( (int)expects[i], sv.getHits(values[i][0]));
            }
        }

    }

}
