package fgafa.uva.adhoc.The3nP1N100;


import java.io.BufferedInputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Scanner;

class Main
{
  
  private static HashMap<Integer, Integer> cycleLength = new HashMap<Integer, Integer>();  // store the num and the related cycle length

  /*
   * recursive with cache
   * Time O(?),  Space O(1)
   */
  private int cycleLength_curr(int n){
    if(cycleLength.containsKey(n)) return cycleLength.get(n);

    int count = 0;
    
    if(n < 2){
      return 1;
    }

    int m ;
    //while ( n != 1){
    if((n&1) == 1 )
      m = (n << 1) + n + 1;
    else 
      m = n >> 1;
    
    count = cycleLength_curr(m) + 1;      
    //}

    cycleLength.put(n, count);
    return count;
  }
  
  /*
   * iterative without cache
   */
  private int cycleLength_iter(int n){

    int count = 1;
    
    if(n < 2){
      return 1;
    }

    while ( n != 1){
      if((n&1) == 1 )
        n = (n << 1) + n + 1;
      else 
        n >>= 1;
      
      count++;      
    }

    return count;
  }
  
  
  private static HashMap<String, Integer> maxInPeriod = new HashMap<String, Integer>();  // store the period and the related max cycle length
  
  /*
   * Time O(end - start),  Space O(1)
   */
  
  private int maxCycleLength(int start, int end) {
    String key = getKey(start,end);
    
    if(maxInPeriod.containsKey(key))
      return maxInPeriod.get(key);
    
    int length = 0, maxLength = Integer.MIN_VALUE;
    for (int i = start; i <= end; i++) {
      length = cycleLength_iter(i);
      maxLength = Math.max(length, maxLength);
      
    }
    
    maxInPeriod.put(key, maxLength);
    return maxLength;
  }
 
  private static String getKey(int start, int end){
    return start + "&" + end;
  }
  

  /*
   * Time O()  Space O()
   */
//  public int maxCycleLength(ArrayList<Integer> points, int iStart, int end) {
//    String key = getKey(points.get(iStart),end);;
//
//    if(maxInPeriod.containsKey(key) )
//      return maxInPeriod.get(key);
//    
//    int max = Integer.MIN_VALUE;
//    
//    if(points.get(iStart) == end){
//      max = cycleLength_iter(end);
//    }else{
//      max = maxCycleLength(points.get(iStart), points.get(iStart+1));
//      //maxInPeriod.put(getKey(points.get(iStart), points.get(iStart+1)), max);
//      
//      max = Math.max(max, maxCycleLength(points, iStart + 1, end));
//    }
//    
//    maxInPeriod.put(key, max);
//    return max;
//  }

  public int maxCycleLength(ArrayList<Integer> points, int iStart, int end) {
    String key = getKey(points.get(iStart),end);

    if(maxInPeriod.containsKey(key) )
      return maxInPeriod.get(key);
    
    int max = Integer.MIN_VALUE;
    
    while( points.get(iStart) < end){
      key = getKey(points.get(iStart), points.get(iStart+1));
      
      max = Math.max(max, maxInPeriod.get(key));
      
      iStart ++;
    }
    
    return max;
  }
  
  /**
   * @param args
   */
  public static void main(String[] args) {
    Main sv = new Main();
    
    /* unit test 
    //System.out.println(sv.cycleLength(22));
    //for(int i=0; i<=22; i++)
    //  System.out.println(sv.cycleLength(i));
    
    //System.out.println(sv.maxCycleLength(1, 22));
    
    if(true)  return;
    */
    
    Scanner in = new Scanner(new BufferedInputStream(System.in), "UTF-8");
      
    ArrayList<Integer> points = new ArrayList<Integer>() ;
    HashMap<Integer, Pair> startHM = new HashMap<Integer, Pair>();
    
    ArrayList<Pair> pairs = new ArrayList<Pair>();
    Pair pair;

    try{
      while(in.hasNext()) {
        
        pair = sv.new Pair(in.nextInt(), in.nextInt());
//      Random random = new Random();
//      for(int k=0; k< 100; k++){
//        pair = sv.new Pair(random.nextInt(10000), random.nextInt(10000));
      
        pairs.add(pair);
        
        points.add(pair.start);
        points.add(pair.end);
        
        startHM.put(pair.start, pair);
      }
    }catch (Exception e){
      
    }finally{
      in.close();
    }
    /*  */
    //System.out.println("Input "+ pairs.size() );
//    long time1= System.currentTimeMillis();
    
    Collections.sort(points);
    
    for(int i=1; i<points.size(); i++){
      sv.maxCycleLength(points.get(i-1), points.get(i) );     
    }
   
    int start, end;
    for(int i=0; i< points.size() - 1; i++){ 
      start = points.get(i);
      if(startHM.containsKey(start)){
        pair = startHM.get(start);
        end = pair.end;
        
        if(pair.maxCycleLength == 0)
          pair.maxCycleLength = sv.maxCycleLength(points, i, end);
      }
    }
    
    //output
    for(int i=0; i< pairs.size(); i++){
      pair = pairs.get(i);
      System.out.println(pair.toString());
    }    
    
//    System.out.println("------------------------------------------ "   );
//    long time2= System.currentTimeMillis();
    
    /*   
    int min, max, num, n, cycle, cyclemax;
    for(int i=0; i< pairs.size(); i++){
      pair = pairs.get(i);
      min = pair.start;
      max = pair.end;
      for (cyclemax=-1, num=min; num<=max; num++) {
        for (n=num, cycle=1; n != 1; cycle++) if ((n & 1) == 1) n=(n << 1) + n + 1; else n >>= 1;
         
        if (cycle > cyclemax) cyclemax=cycle;
      }
      
      pair.maxCycleLength = cyclemax;
      System.out.println (pair.toString());      
    }
    
    long time3= System.currentTimeMillis();
    System.out.println("------------------------------------------ "   );
    System.out.println("\n " + (time2 - time1) + " "+ (time3 - time2)  );
    */
    
  }

  class Pair{
    int start;
    int end;
    int maxCycleLength;
    boolean rotated = false;
    
    public Pair(int s, int e){
      if(s>e){
        this.rotated = true;
        this.start = e;
        this.end = s;        
      }else{
        this.start = s;
        this.end = e;        
      }
    }
    
    public String toString(){
      StringBuffer sb = new StringBuffer();
      if(rotated){
        sb.append(this.end);
        sb.append(" ");
        sb.append(this.start);
      }else{
        sb.append(this.start);
        sb.append(" ");
        sb.append(this.end);        
      }
      
      sb.append(" ");
      sb.append(this.maxCycleLength);
      
      return sb.toString();
    }
  }
  
}
