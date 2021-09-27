package design.systemdesign.HitCounter;

public class HitCounter2 {

    //TreeMap<Integer, Integer> stores;
    int[] counts;
    int[] timestamps;

    static int K = 300;

    /** Initialize your data structure here. */
    public HitCounter2() {
        //this.stores = new HashMap<>();
        this.counts = new int[K];
        this.timestamps = new int[K];
    }

    /** Record a hit.
     @param timestamp - The current timestamp (in seconds granularity). */
    public void hit(int timestamp) {
        int i = timestamp % K;

        if(timestamp == timestamps[i]){
            counts[i]++;
        }else{
            //int count = counts[i];

            counts[i] = 1;
            timestamps[i] = timestamp;

            //stores.put(timestamp, count);
        }
    }

    /** Return the number of hits in the past 5 minutes.
     @param timestamp - The current timestamp (in seconds granularity). */
    public int getHits(int timestamp) {
        int sum = 0;

        int start = timestamp - K;
        for(int i = 0; i < K; i++){
            if(timestamps[i] > start){
                sum += counts[i];
            }
        }

        return sum;
    }

}
