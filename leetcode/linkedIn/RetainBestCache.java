package fgafa.leetcode.linkedIn;


import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

public class RetainBestCache<K, T extends RetainBestCache.Rankable> {


    /**  callable API end   */
    /*
     * For reference, here are the Rankable and DataSource interfaces.
     * You do not need to implement them, and should not make assumptions
     * about their implementations.
     */
    public interface Rankable {
        /**
         * Returns the Rank of this object, using some algorithm and potentially
         * the internal state of the Rankable.
         */
        long getRank();
    }
    public interface DataSource<K, T extends Rankable> {
        T get(K key);
    }

    /**  callable API end   */


    int entriesToRetain;
    Map<K, T> map;

    PriorityQueue<T> minHeap;

    DataSource<K, T> ds;

    /* Constructor with a data source (assumed to be slow) and a cache size */
    public RetainBestCache(DataSource<K, T> ds, int entriesToRetain) {
        this.entriesToRetain = entriesToRetain;

        map = new HashMap<K, T>();
        minHeap = new PriorityQueue<>((t1, t2) -> (Long.compare(t1.getRank(), t2.getRank())));
    }

    /* Gets some data. If possible, retrieves it from cache to be fast. If the data is not cached,
     * retrieves it from the data source. If the cache is full, attempt to cache the returned data,
     * evicting the T with lowest rank among the ones that it has available
     * If there is a tie, the cache may choose any T with lowest rank to evict.
     */
    public T get(K key) {
        if(map.containsKey(key)){
            return map.get(key);
        }

        T result = ds.get(key);

        if(map.size() == entriesToRetain){
            minHeap.poll();
        }

        map.put(key, result);
        minHeap.add(result);

        return result;
    }

}


