package fgafa.design.systemdesign;

/**
 * 
 * Implement a function, control the call that cannot exceed 100 times in the past 10 seconds
 *
 */

/*
 * Q: Implement a function, control the call that cannot exceed 100 times in the past 10 seconds ?
 * A: Do it as a Ring buffer 
 * 
 */

public class AccessManager {
    
    final int MAX_TIMES = 100;
    final int CAPACITY = 10;
    Pair[] counts;
    
    public AccessManager(){
        counts = new Pair[CAPACITY]; //default all are null
        for(int i = 0; i < counts.length; i++){
            counts[i] = new Pair(System.currentTimeMillis() / 1000, 0);
        }
    }
    
    
    public boolean call(){
        long currInSecond = System.currentTimeMillis() / 1000; 
        
        if(countAll(currInSecond) <= MAX_TIMES){
            addCall(currInSecond);
            return true;
        }
            
        return false;
    }
    
    private void addCall(long currInSecond){
        int buket =  (int)(currInSecond % 10);
        Pair p = counts[buket];
        if(p.time == currInSecond){
            p.count++;
        }else{
            p.time = currInSecond;
            p.count = 1;
        }
    }
    
    private int countAll(long currInSecond){
        int count = 0;
        long limit = currInSecond - 10;
        
        for(Pair p : counts){
            if(p.time >= limit){
                count += p.count;
            }
        }
        
        return count;
    }
    
    class Pair{
        long time; // how many seconds
        int count;
        
        public Pair(long time, int count){
            this.time = time;
            this.count = count;
        }
    }
}
