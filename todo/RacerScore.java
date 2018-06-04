package fgafa.todo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

/**
 * 一堆racer，每个racer有出发时间和到达时间，计算每个racer的score，规则如下 
 * score ＝ 所有出发比自己晚但是到达比自己早的racer数量之和，（所有的出发时间
 * 和到达时间没有重复的）要求时间复杂度o(nlgn).
 * 
 *
 */
public class RacerScore
{
  class Racer{
    private String name;
    private int id;
    private int startTime;
    private int endTime;
    
    private int score = 0;
    
    Racer(String name, int id, int startTime, int endTime){
      this.name = name;
      this.id = id;
      this.startTime = startTime;
      this.endTime = endTime;
    }
    
    public int getStartTime(){
      return this.startTime;      
    }
    public int getEndTime(){
      return this.endTime;
    }
    
    public void setScore(int score){
      this.score = score;
    }
    public int getScore(){
      return this.score;
    }
  }
  
  public boolean calScore(ArrayList<Racer> racers){
    //pre-check
    if(racers == null || racers.size() == 0)
      return false;
    
    HashMap<Integer, Racer> map = new HashMap<Integer, Racer>();
    List<Integer> startTimeList = new ArrayList<Integer>(); 
    for(Racer racer : racers){
      map.put(racer.getStartTime(), racer);
      startTimeList.add(racer.getStartTime());
    }

    Collections.sort(startTimeList);
    List<Integer> endTimeList = new ArrayList<Integer>();
    Racer racer;
    for(int i=startTimeList.size() - 1; i>= 0; i--){
      racer = map.get(startTimeList.get(i));
      racer.setScore(getPosition(endTimeList, racer.getEndTime()));
    }      
    
    return true;
  }
  
  /* O(n^2) */
  private int getPosition(List<Integer> endTimes, int x){
    if(endTimes == null || endTimes.size() == 0 ){
      endTimes.add(0, x);
      return 0;
    }  
    
    int lower = 0, higher = endTimes.size() - 1, middle;
    
    while(lower <= higher){
      middle = lower + ((higher - lower) >> 1);
        
      if(x <= endTimes.get(middle) )   // it's O(n)
        higher = middle -1;
      else
        lower = middle + 1;
        
    }
    
    endTimes.add(lower, x);
    return endTimes.size() - lower;
  }
  
  private int getPosition(List<Integer> endTimes, int x){
    if(endTimes == null || endTimes.size() == 0 ){
      endTimes.add(0, x);
      return 0;
    }  
    
    int lower = 0, higher = endTimes.size() - 1, middle;
    
    while(lower <= higher){
      middle = lower + ((higher - lower) >> 1);
        
      if(x <= endTimes.get(middle) )   // it's O(n)
        higher = middle -1;
      else
        lower = middle + 1;
        
    }
    
    endTimes.add(lower, x);
    return endTimes.size() - lower;
  }  
  
  /**
   * @param args
   */
  public static void main(String[] args) {
    // TODO Auto-generated method stub

  }
  
  class Node{
    
  }

}
