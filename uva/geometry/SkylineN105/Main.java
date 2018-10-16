package fgafa.uva.geometry.SkylineN105;

import java.io.IOException;
import java.util.ArrayList;
import java.util.StringTokenizer;

class Main
{
  public static ArrayList<Integer> RESULT_X = new ArrayList<Integer> (); //represent the vertical line (x-coordinate) 
  public static ArrayList<Integer> RESULT_Y = new ArrayList<Integer> (); //represent the horizontal line (height), the last one is 0 
  
  /*
   * Time O(),  Space O()
   * @return the 
   */
  private int addTriplet(int xIndexStart, int ln, int hn, int rn) {
    
    int returnValue = xIndexStart;  //represent a right vertical line (x-coordinate) of the new Rect
    int xIndexEnd = RESULT_X.size() -1 ;   
    
    if(RESULT_X.get(xIndexEnd) < ln){ //case 1, the new Rect is in the right without interleaving. 
      returnValue = ++xIndexEnd;
      addPoint(xIndexEnd, ln, hn);
      addPoint(++xIndexEnd, rn, 0);
      return returnValue;
      
    }else if(RESULT_X.get(xIndexEnd) == ln){ //case 2, the new Rect is in the right with a point interleaving.
      returnValue = xIndexEnd; 
      if(RESULT_Y.get(xIndexEnd - 1) != hn){
        addPoint(xIndexEnd, ln, hn);
        xIndexEnd ++;
      }
      updatePointX(xIndexEnd, rn);
      
      return returnValue;      
    }      
    
    //the other cases, the right point is in [xIndexStart, xIndexEnd)
    int xIndexLeft = searchX_binary(xIndexStart, xIndexEnd, ln);  // represent a left vertical line (x-coordinate) of the new Rect
    int xIndexRight = searchX_binary(xIndexLeft, xIndexEnd, rn);  // represent a right vertical line (x-coordinate) of the new Rect
    returnValue = xIndexLeft;
    
    //case 3, the new Rect is in the exits Skyline. 
    //the right point is at left of xIndexEnd and the height --- 
    //or the right point and the xIndexEnd are in the same Vertical line. and the height ---
    if( (RESULT_X.get(xIndexRight) < rn && RESULT_Y.get(xIndexRight) >= hn) || ( RESULT_X.get(xIndexRight) == rn &&  RESULT_Y.get(xIndexRight - 1) >= hn ) )
      return returnValue; 

    //case 4, the new Rect will create 2 new points.  
    // add the right point
    addPoint(xIndexRight+1, rn, RESULT_Y.get(xIndexRight));
    
    // add the left point
    int xTmp = ln;
    
    if(RESULT_Y.get(xIndexLeft) >= hn ){ // the new Rect's top Horizon line cross a Vertical line
      for( int i=xIndexLeft; i<=xIndexRight; i++){
        if( RESULT_Y.get(i) <= hn){
          xIndexLeft = i;
          xTmp = RESULT_X.get(i) ;
          break;
        }
      } 
    }else{ // the new Rect's left Vertical line cross the Horizon line
      if(RESULT_X.get(xIndexLeft) < ln)
        xIndexLeft = xIndexLeft + 1;
      xTmp = ln;
      returnValue = xIndexLeft;
    }    
    
    addPoint(xIndexLeft, xTmp, hn);
    xIndexRight ++;
    xIndexLeft ++;
    
    //remove the points between right and left, inclusive,  because they are covered by the new Rect.
    for(int i = xIndexLeft; i<=xIndexRight; i++)
      deletePoint(xIndexLeft);

    return returnValue ;
  }
  
/**
 * add a new point in result_x and result_h.
 * @param index, the index of x and y
 * @param x, x-coordinate
 * @param h, height
 */
  private void addPoint(int index, int x, int h){
    RESULT_X.add(index, x);
    RESULT_Y.add(index, h);
  }
  
  private void deletePoint(int index){
    RESULT_X.remove(index);
    RESULT_Y.remove(index);
  }  

  private void updatePointX(int index, int x){
    RESULT_X.remove(index);
    RESULT_X.add(index, x);     
  }

  
  /*
   *  insert into {1, 3} with 0, it would be insert into index -1 
   *  insert into {1, 3} with 1, it would be insert into index 0 
   *  insert into {1, 3} with 2, it would be insert into index 0 
   *  insert into {1, 3} with 3, it would be insert into index 1 
   *  insert into {1, 3} with 4, it would be insert into index 1 
   */
  private int searchX_binary(int left, int right, int xn){
    
    int mid, xTmp;
    while(left <= right){
      mid = left + ((right - left) >> 1);
      xTmp = RESULT_X.get(mid);
          
      if( xTmp == xn )
        return mid;
      else if( xTmp < xn){
        left = mid + 1;
      }else{
        right = mid - 1;
      }
    
    }
        
    return Math.min(left, right);
  }
 
  /*
   * Time O(the num of triplet)  Space O()
   */
  public void drawSkyline(ArrayList<Integer> input) {
    //check 
    if(input == null || input.size() == 0)
      return;
    
    //init
    int i = 0;
    RESULT_X.add(input.get(i++));   // L1
    RESULT_Y.add(input.get(i++));   // H1
    RESULT_X.add(input.get(i++));   // R1
    RESULT_Y.add(0);              // right border.

    int xIndexStart = 0;
    for( ; i< input.size(); ){
      xIndexStart = addTriplet(xIndexStart, input.get(i++), input.get(i++), input.get(i++) );
    }
    
    //output
    StringBuilder sb = new StringBuilder();
    
    for( i=0; i< RESULT_X.size(); i++){
      sb.append(RESULT_X.get(i));
      sb.append(" ");
      sb.append(RESULT_Y.get(i));
      sb.append(" ");
    }
    
    System.out.println(sb.substring(0, sb.length() - 1).toString());
  }

  private boolean isValid(int x1, int h, int x2){
    return ( x1 >0 && h >0 && x2>0 && x1 < x2 ); 
  }
  
  
  /**
   * @param args
   */
  public static void main(String[] args) {
    Main sv = new Main();
      
    ArrayList<Integer> input = new ArrayList<Integer> (); 

//    int[][] cases = {{0, 1, 0}
//      ,{1,11,5}, {2,6,7}, {3,13,9}, {12,7,16}, {14,3,25}, {19,18,22}, {23,13,29}, {24,4,28}
//      ,{100, 5, 110}, {100, 3, 110}, {100, 8, 109}, {100, 6, 110}
//      ,{111, 6, 120}, {111, 5, 125}, {115, 10, 117},{116, 7, 123}, {120, 7, 126}
//      ,{200, 5, 205}, {205, 7, 206},{206, 5, 207}
//      ,{210, 5, 216}, {215, 7, 218}, {215, 6, 220}, {215, 7, 219}, {219, 8, 220}
//      ,{250, 6, 255}, {255, 4, 258}, {255, 5, 259}
//      ,{260, 6, 265}, {260, 4, 268}, {260, 4, 270}
//      ,{303, 2, 304},{303, 1, 307}, {305, 2, 306}
//      ,{401, 11, 405}, {402, 5, 410}, {406, 9, 408}, {406, 8, 408}
//    };
//    int[] expectation = {1, 11, 3, 13, 9, 0, 12, 7, 16, 3, 19, 18, 22, 3, 23, 13, 29, 0 
//        ,100, 8, 109, 6, 110, 0
//        ,111, 6, 115, 10, 117, 7, 126, 0
//        ,200, 5, 205, 7, 206, 5, 207, 0
//        ,210, 5, 215, 7, 219, 8, 220, 0
//        ,250, 6, 255, 5, 259, 0
//        ,260, 6, 265, 4, 270, 0
//        ,303, 2, 304, 1, 305, 2, 306, 1, 307, 0 
//        ,401, 11, 405, 5, 406, 9, 408, 5, 410, 0
//        };
    
    try {
      int x1, x2, h;
          
//      for (int j=0; j < cases.length; j++) {
//        x1 = cases[j][0];
//        h = cases[j][1];
//        x2 = cases[j][2];        

      String line;
      StringTokenizer idata;
      while ((line = readLn (255)) != null){
        idata = new StringTokenizer (line);
        x1 = Integer.parseInt (idata.nextToken());
        h = Integer.parseInt (idata.nextToken());
        x2 = Integer.parseInt (idata.nextToken());
        
        if(sv.isValid(x1, h, x2)){
          input.add(x1);
          input.add(h);
          input.add(x2);
        }
        
      }        
    }
    catch (Exception e) {
      //e.printStackTrace();
    }
    
    sv.drawSkyline(input);
    
  }


  static String readLn(int maxLg) // utility function to read from stdin
  {
    byte lin[] = new byte[maxLg];
    int lg = 0, car = -1;

    try {
      while (lg < maxLg) {
        car = System.in.read();
        if ((car < 0) || (car == '\n'))
          break;
        lin[lg++] += car;
      }
    }
    catch (IOException e) {
      return (null);
    }

    if ((car < 0) && (lg == 0))
      return (null); // eof
    return (new String(lin, 0, lg));
  }
 
}
