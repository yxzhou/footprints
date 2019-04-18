package fgafa.design.systemdesign.HitCounter;

public class HitCounter2 {

    int[] times = new int[300];
    int[] counts = new int[300];

    /** Initialize your data structure here. */
    public HitCounter2() {

    }

    /** Record a hit.
     @param timestamp - The current timestamp (in seconds granularity). */
    public void hit(int timestamp) {
        int index = timestamp % 300;

        if(times[index] < timestamp){
            times[index]= timestamp;
            counts[index] = 1;
        }else{ // ==
            counts[index]++;
        }
    }

    /** Return the number of hits in the past 5 minutes.
     @param timestamp - The current timestamp (in seconds granularity). */
    public int getHits(int timestamp) {
        // (start, end]
        int start = timestamp - 300;

        int sum = 0;
        for(int i = 0; i < 300; i++ ){
            if(times[i] > start ){
                sum += counts[i];
            }
        }

        return sum;
    }

}
