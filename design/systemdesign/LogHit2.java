package design.systemdesign;


/**
 * Design a web counter to give how many hits per second, per minute and per hour. 
 * What kind of data structure and algorithm would you use to do this?
 */

public class LogHit2 {
    private Counter[] counters_second = new Counter[60]; // 1 minute = 60 seconds
    private Counter[] counters_minute = new Counter[60]; // 1 hour = 60 minutes
    
    public void hit(){
        long now = System.currentTimeMillis() / 1000; // unit is second
        int bucket = (int) (now % 60);

        Counter tail = counters_second[bucket];
        if(tail.time == now){
            tail.count++;
        }else{
            long end = now - 60;
            for(Counter counter : counters_second){
                if(counter.time <= end){
                    hit(counter);
                    
                    counter.time = now;
                    counter.count = 1;
                }
            }

        }
    }

    public void hit(Counter second) {
        long now = second.time / 60; // unit is minute
        int bucket = (int) (now % 60);

        Counter tail = counters_minute[bucket];
        if (tail.time == now) {
            tail.count++;
        } else {
            long end = now - 60;
            for (Counter counter : counters_second) {
                if (counter.time <= end) {

                    // save the head

                    counter.time = now;
                    counter.count = 1;
                }
            }
        }
    }
    
    public long getHitsOfLastSecond(){
        long now = System.currentTimeMillis() / 1000; // unit is second
        int bucket = (int) (now % 60);

        Counter tail = counters_second[bucket];
        if(tail.time == now){
            return tail.count;
        }
            
        return 0;
    }
  
    public long getHitsOfLastMinute(){
        long now = System.currentTimeMillis() / 1000;
        long end = now - 60;
        
        long total = 0;
        for(Counter counter : counters_second){
            if(counter.time > end){
                total += counter.count;
            }
        }
        
        return total;
    }
    
    public long getHitsOfLastHour(){
        long now = System.currentTimeMillis() / 60000;
        long end = now - 60;
        
        long total = 0;
        for(Counter counter : counters_minute){
            if(counter.time > end){
                total += counter.count;
            }
        }
        
        return total;
    }
    
    class Counter{
        long time; // the unit is second or minute
        long count;
        
        Counter(){
            this(0L, 0L);
        }
        
        Counter(long time, long count){
            this.time = time;
            this.count = count;
        }
    }
}
