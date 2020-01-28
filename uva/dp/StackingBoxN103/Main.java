package fgafa.uva.dp.StackingBoxN103;

import java.io.BufferedInputStream;
import java.util.*;

//import java.util.Collections;
//import java.util.Comparator;

//import java.util.Stack;

class Main
{
    
  /*
   * Time O( rows* rows*cols )  Space O(  )
   */ 
  public void stackBoxes(int[][] boxes, int rows) {
    //init
    HashMap<int[], Integer> hm = new HashMap<int[], Integer>();  
    ArrayList<int[]> list = new ArrayList<int[]>();
    for(int row=0; row < rows; row ++){
      Arrays.sort(boxes[row]);
      
      //list.add(boxes[row]);
      addInSorted(list, boxes[row]);
      hm.put(boxes[row], row);
    }
    
    //Collections.sort(list, new boxComparator());
    
    //DP, get the LIS
    int[] dp = new int[rows]; //dp[i] is the LIS of boxes[0--i]
    String[] path = new String[rows];
    for(int i=0; i<rows; i++)
      path[i] = String.valueOf(hm.get(list.get(i)) + 1);
    
      
    int max = 0;
    for(int i = 1; i<rows; i++ ){    
      for (int j = i-1; j >=0; j--) {
        if (dp[i] < dp[j] + 1 && isNestIn(list.get(j),  list.get(i))) {
          dp[i] = dp[j] + 1;
          path[i] = path[j] + " " + (hm.get(list.get(i)) + 1);
        }  
      }
      max = Math.max(max, dp[i]);
    }
    
    //output
    System.out.println(max + 1);
    
    int i=rows - 1;
    for( ;i>=0; i--){
      if(dp[i] == max){
        System.out.println(path[i]);
        break;
      }  
    }

  }
  
  /**
   * @return true if box1 nests in box2, or false
   */
    private boolean isNestIn(int[] box1, int[] box2)  
    {  
      //if(box1.length != box2.length)
      //  return false;
      
      for(int i=0; i<box1.length; i++){
        if(box1[i] >= box2[i] )
          return false;
      }
      
      return true;
    } 

    private void addInSorted(ArrayList<int[]> list, int[] box){
      if(list.size() == 0){
        list.add(box);
        return;
      }
      
      int col=0, row = 0;
      for( ; col< box.length && row < list.size(); ){
        if(box[col] > list.get(row)[col] ){
            row ++;
        }else if (box[col] == list.get(row)[col]) {
            col ++;
        }else
          break;
        
      }
      
      list.add(row, box);
    }
    
    class boxComparator implements Comparator<int[]> {
      public int compare(int[] box1, int[] box2)
      {
        for(int i=0; i<box1.length; i++){
          if(box1[i] < box2[i] ){
            return -1;
          }
        }

        return 1;
      }
    }
  
  /**
   * @param args
   */
  public static void main(String[] args) {
    Main sv = new Main();
    Scanner in = new Scanner(new BufferedInputStream(System.in), "UTF-8");
    
    try{
      while(in.hasNext()){
        int rows = in.nextInt();
        int cols = in.nextInt();
        int[][] boxes = new int[rows][cols]; 
        
        for (int i = 0; i < rows; i++) {
          for (int j = 0; j < cols; j++) {
              boxes[i][j] = in.nextInt();

          }
        }      
  
        sv.stackBoxes(boxes, rows);  
      }
    }catch(Exception e){
      //e.printStackTrace();
    }finally{
      in.close();      
    }

  }
  
}
