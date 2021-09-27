package design.systemdesign.LogStorage;

import org.junit.Test;

import java.util.*;
import java.util.stream.Collectors;

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

public class LogSystem2 {
    TreeMap<Long, List<Integer>> treeMap; //

    public LogSystem2() {
        this.treeMap = new TreeMap<>();
    }

    public void put(int id, String timestamp) {
        long time = convert(timestamp);

        treeMap.computeIfAbsent(time, x->new LinkedList<>()).add(id);
    }

    public List<Integer> retrieve(String s, String e, String gra) {

        long start = convert(s, levels.get(gra), false);
        long end = convert(e, levels.get(gra), true);

        List<Integer> result = treeMap.subMap(start, true, end, true).values().stream().flatMap(List::stream).collect(Collectors.toList());


        return result;
    }


    // String Year:Month:Day:Hour:Minute:Second -> long yymmddhhminss
    // year ranges from [2000,2017]
    static Map<String, Integer> levels = new HashMap<>();
    {
        levels.put("Year",  0);
        levels.put("Month", 1);
        levels.put("Day",   2);
        levels.put("Hour",  3);
        levels.put("Minute",4);
        levels.put("Second",5);
    }
    int[] ends = new int[]{0, 13/*MM*/, 32/*DD*/, 24/*HH*/, 60/*mm*/, 60/*ss*/};

    private long convert(String timestamp){
        return convert(timestamp, levels.get("Second"), false);
    }

    private long convert(String timestamp, int grad, boolean isEnd){
        String[] tokens = timestamp.split(":");
        long time = Integer.valueOf(tokens[0]) - 1999; //yyyymmddhhminss

        int diff;
        for(int i = 1; i < 6; i++){

            if(i <= grad) {
                diff = Integer.valueOf(tokens[i]);
            }else if(isEnd){
                    diff = ends[i];
            }else{
                diff = 0;
            }


            time = time * 100 + diff;
        }

        return time;
    }

    @Test public void testConvert(){
        String timestamp = "2016:01:01:01:01:01";

        System.out.println(timestamp);

        System.out.println("yymmddHHMMSS");
        System.out.println(convert(timestamp));

        for(String level : levels.keySet()){

            StringBuilder sb = new StringBuilder();
            for(int i = 0; i < levels.get(level); i++) {
                sb.append("  ");
            }
            sb.append(level.charAt(0));
            sb.append(level.charAt(0));

            System.out.println(sb.toString());

            System.out.println(convert(timestamp, levels.get(level), false));
            System.out.println(convert(timestamp, levels.get(level), true));
        }
    }


    public static void main(String[] args){
        LogSystem2 sv = new LogSystem2();

        sv.put(1, "2017:01:01:23:59:59");
        sv.put(2, "2017:01:01:22:59:59");
        sv.put(3, "2016:01:01:00:00:00");

        System.out.println(sv.retrieve("2016:01:01:01:01:01","2017:01:01:23:00:00","Year")); // return [1,2,3], because you need to return all logs within 2016 and 2017.
        System.out.println(sv.retrieve("2016:01:01:01:01:01","2017:01:01:23:00:00","Hour")); // return [1,2], because you need to return all logs start from 2016:01:01:01 to 2017:01:01:23, where log 3 is left outside the range.


        sv = new LogSystem2();
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
