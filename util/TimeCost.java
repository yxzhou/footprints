package util;

public class TimeCost
{
  private static TimeCost myInstance = new TimeCost();
  
  private TimeCost(){};
  
  private static long time = System.currentTimeMillis();

  public static TimeCost getInstance(){
    return myInstance;
  }
  
  public long init(){
    this.time = System.currentTimeMillis();
    return this.time;
  }
  
  public long getTimeCost(){
    
    long currTime = System.currentTimeMillis();
    long timeCost = currTime - time;
    this.time = currTime;
    
    return timeCost;
    
  }
  
}
