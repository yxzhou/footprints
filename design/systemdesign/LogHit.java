package design.systemdesign;
/**
 * 
 * Design a log hitter. 
 * Void hit()
 * Long getHits()  //return how many hit in the past 5 minutes 
 * 
 *
 */
public class LogHit {
    private Counter[] counters; // ring buffer
    
    public LogHit(){
        counters = new Counter[300]; // 5 minutes = 300 seconds
        for(int i = 0; i < counters.length; i++){
            counters[i] = new Counter(System.currentTimeMillis() / 1000, 0);
        }
    }
    
    public void hit(){
        long now = System.currentTimeMillis() / 1000;
        int bucket = (int)(now % 300);
        
        Counter counter = counters[bucket];
        if(now == counter.time){
            counter.count++;
        }else{
            counter.time = now;
            counter.count = 1;
        }
        
    }
    
    public long getHits(){
        long now = System.currentTimeMillis() / 1000;
        long end = now - 300;
        
        long total = 0;
        for(Counter counter : counters){
            if(counter.time > end){
                total += counter.count;
            }
        }
        
        return total;
    }
    
    
    class Counter{
        long time; // the unit is second
        long count;
        
        public Counter(long time, long count){
            this.time = time;
            this.count = count;
        }
    }
}

/**
 * More:
 * 1)  if too many request on hit(), 
 * it need think about concurrency, 
 * one way is add "synchronized" on hit() method or counter.count++ operation. 
 * the other way is design a producer-consumer, producer receive the hit requests and save it (the timestamp) in a queue (ConcurrentLinkedQueue), consumer get the timestamp and run hit()
 * 
 * if one machine can't handle so many request, it need think about shards. Deploy multiple producer behind a load balance to receive the requests. getHit() will collect all result from every shards
 * It can use solr to store the counter. 
 * 
 */
