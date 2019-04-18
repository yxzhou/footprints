package fgafa.design.systemdesign.LogStorage;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * You are given several logs that each log contains a unique ids and timestamp. Timestamp is a string that has the following format: Year:Month:Day:Hour:Minute:Second, for example, 2017:01:01:23:59:59. All domains are zero-padded decimal numbers.
 *
 * Design a log storage system to implement the following functions:
 *
 * void Put(int ids, string timestamp): Given a log's unique ids and timestamp, store the log in your storage system.
 *
 *
 * int[] Retrieve(String start, String end, String granularity): Return the ids of logs whose timestamps are within the range from start to end. Start and end all have the same format as timestamp. However, granularity means the time level for consideration. For example, start = "2017:01:01:23:59:59", end = "2017:01:02:23:59:59", granularity = "Day", it means that we need to find the logs within the range from Jan. 1st 2017 to Jan. 2nd 2017.
 *
 * Example 1:
 * put(1, "2017:01:01:23:59:59");
 * put(2, "2017:01:01:22:59:59");
 * put(3, "2016:01:01:00:00:00");
 *
 * retrieve("2016:01:01:01:01:01","2017:01:01:23:00:00","Year"); // return [1,2,3], because you need to return all logs within 2016 and 2017.
 * retrieve("2016:01:01:01:01:01","2017:01:01:23:00:00","Hour"); // return [1,2], because you need to return all logs start from 2016:01:01:01 to 2017:01:01:23, where log 3 is left outside the range.
 *
 * Note:
 * There will be at most 300 operations of Put or Retrieve.
 * Year ranges from [2000,2017]. Hour ranges from [00,23].
 * Output for Retrieve has no order required.
 *
 */

public class LogSystem {
    class Entity{
        int id;

        //String timestamp; // Year:Month:Day:Hour:Minute:Second
        long time; //yyyymmddhhminss

        Entity(int id, String timestamp){
            this.id = id;
            this.time = convert(timestamp);
        }
    }

    static Map<String, Integer> levels = new HashMap<>();
    final static long[] ends = new long[]{130000000000L, 3200000000L, 25000000L, 600000L, 6000L, 60L, 0L};
    {
        levels.put("Year",  0);
        levels.put("Month", 1);
        levels.put("Day",   2);
        levels.put("Hour",  3);
        levels.put("Minute",4);
        levels.put("Second",5);
    }

    Entity[] list;
    int size = 0;
    //Map<String, List<Entity>> pool;

    public LogSystem() {
        list = new Entity[300];
    }

    public void put(int id, String timestamp) {
        if(size == list.length){
            resize();
        }

        long time = convert(timestamp);

        int index = binarySearch(list,0, size, time);

        System.arraycopy(list, index, list, index + 1, size - index );
        list[index] = new Entity(id, timestamp);
        size++;
    }

    public List<Integer> retrieve(String s, String e, String gra) {

        int start = binarySearch( list, 0, size, convert(s, gra, false));
        int end = binarySearch( list, start, size, convert(e, gra, true));

        List<Integer> result = new ArrayList<>();

        for(int i = start; i < end; i++){
            result.add(list[i].id);
        }



        return result;
    }

    private void resize(){
        Entity[] newList = new Entity[size * 2];
        System.arraycopy(list, 0, newList, 0, size);

        list = newList;
    }

    private int binarySearch(Entity[] list, int low, int size, long time){

        int high = size;
        int mid;
        while(low < high){
            mid = low + (high - low) / 2;

            if(list[mid].time == time){
                return mid;
            }else if(list[mid].time > time){
                high = mid - 1;
            }else{
                low = mid + 1;
            }
        }

        return (low < size && list[low].time < time)? low + 1 : low;
    }

    // String Year:Month:Day:Hour:Minute:Second -> long yyyymmddhhminss
    // year ranges from [2000,2017]
    private long convert(String timestamp){
        return convert(timestamp, "Second", false);
    }

    private long convert(String timestamp, String gra, boolean isEnd){
        String[] tokens = timestamp.split(":");
        long time = Integer.valueOf(tokens[0]); //yyyymmddhhminss

        int level = levels.get(gra);
        int diff;
        for(int i = 1; i < 7; i++){
            diff = (i > level ? 0 : Integer.valueOf(tokens[i]));
            time = time * 100 + diff;
        }

        if(isEnd){
            time += ends[level];
        }

        return time;
    }

    @Test public void testConvert(){
        String timestamp = "2016:01:01:01:01:01";

        System.out.println(timestamp);
        System.out.println(convert(timestamp));

        for(String level : levels.keySet()){
            System.out.println(level);
            System.out.println(convert(timestamp, level, false));
            System.out.println(convert(timestamp, level, true));
        }
    }

    @Test public void testBinarySearch(){
        //LogSystem sv = new LogSystem();

        Assert.assertEquals(0, binarySearch(list, 0, 0, 20150101010101L));

        list[0] = new Entity(2001, "2016:01:01:01:01:01");
        Assert.assertEquals(0, binarySearch(list, 0, 1, 20150101010101L));
        Assert.assertEquals(0, binarySearch(list, 0, 1, 20160101010101L));
        Assert.assertEquals(1, binarySearch(list, 0, 1, 20170101010101L));

        list[1] = new Entity(2001, "2017:01:01:01:01:01");
        Assert.assertEquals(0, binarySearch(list, 0, 2, 20150101010101L));
        Assert.assertEquals(0, binarySearch(list, 0, 2, 20160101010101L));
        Assert.assertEquals(1, binarySearch(list, 0, 2, 20170101010101L));
        Assert.assertEquals(2, binarySearch(list, 0, 2, 20180101010101L));
    }

    public static void main(String[] args){
        LogSystem sv = new LogSystem();

        sv.put(1, "2017:01:01:23:59:59");
        sv.put(2, "2017:01:01:22:59:59");
        sv.put(3, "2016:01:01:00:00:00");

        System.out.println(sv.retrieve("2016:01:01:01:01:01","2017:01:01:23:00:00","Year")); // return [1,2,3], because you need to return all logs within 2016 and 2017.
        System.out.println(sv.retrieve("2016:01:01:01:01:01","2017:01:01:23:00:00","Hour")); // return [1,2], because you need to return all logs start from 2016:01:01:01 to 2017:01:01:23, where log 3 is left outside the range.


        sv = new LogSystem();
        sv.put(1, "2017:01:01:23:59:59");
        sv.put(2, "2017:01:02:23:59:59");

        System.out.println(sv.retrieve("2017:01:01:23:59:59","2017:01:02:23:59:59","Second")); // return [1,2], because you need to return all logs within 2016 and 2017.

   }
}

/**
 * Your LogSystem object will be instantiated and called as such:
 * LogSystem obj = new LogSystem();
 * obj.put(ids,timestamp);
 * List<Integer> param_2 = obj.retrieve(s,e,gra);
 */
