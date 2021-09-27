package uva.geometry.MouseClicksN142;

import java.io.*;
import java.util.*;

class Main
{
   
  class Point{
      int x;
      int y;
      
      Point(int x, int y){
          this.x = x;
          this.y = y;
      }
  }
 
  class Icon extends Point{
    int index;

    Icon(int x, int y, int index) {
      super(x, y);
      this.index = index;
    }
    
  }
  
  class Rect{
    int index;
    
    int x1, y1;  // top left
    int x2, y2;  // bottom right
    
    Rect(int x1, int y1, int x2, int y2, int index){
      this.x1 = x1;
      this.y1 = y1;
      this.x2 = x2;
      this.y2 = y2;
      this.index = index;
    }
    
    boolean isInclude(int x, int y){
      return ( x >= x1 && x <= x2) && (y >= y1 && y <= y2);
      
    }
    
  }
  
  private static final int ASCII_VALUE_OF_A_PREFIX = 64; // 'A' is 65
  public static char int2Char(int digit){
    return   (char) (ASCII_VALUE_OF_A_PREFIX + digit);
  } 
  
  public static void main(String[] args) throws Exception {

    //init
    Main sv = new Main();
    Scanner in = new Scanner(new BufferedInputStream(System.in), "UTF-8");
    
    int iIndex = 1, rIndex = 1;
    List<Icon> icons = new ArrayList<Icon> (); 
    List<Point> clicks = new ArrayList<Point> ();
    List<Rect> rects = new ArrayList<Rect> ();
    
    String line;
    StringTokenizer st; 
    int x1, y1, x2, y2;
    try {
      while (in.hasNext()) {        
        //read
        line = in.nextLine();

        //exit when it's #
        if("#".equals(line.trim()))
            break;
                  
        st = new StringTokenizer(line, " "); 
        
        switch (st.nextToken().charAt(0)){
            case 'I' :
                x1 = Integer.valueOf(st.nextToken());
                y1 = Integer.valueOf(st.nextToken());
                
                icons.add(sv.new Icon(x1, y1, iIndex ++));
                break;
            case 'R' :
                x1 = Integer.valueOf(st.nextToken());
                y1 = Integer.valueOf(st.nextToken());
                x2 = Integer.valueOf(st.nextToken());
                y2 = Integer.valueOf(st.nextToken());                
                  
                rects.add(sv.new Rect(x1, y1, x2, y2, rIndex ++));                    
                break;
            case 'M' :
                x1 = Integer.valueOf(st.nextToken());
                y1 = Integer.valueOf(st.nextToken());
                
                clicks.add(sv.new Point(x1, y1));
                break;                
        } 
       }
      
    }
    catch (Exception e) {
      //e.printStackTrace();
    }
    finally {
      in.close();
    }
    
    
    //main
    //filter the icon that is covered by rectangle
    Icon iconTmp = null;
     
    Outer: for( int k = icons.size()-1; k > -1; k--){
      iconTmp = icons.get(k);
      
      for(Rect rect : rects)
        if(rect.isInclude(iconTmp.x, iconTmp.y)){
          icons.remove(k);        
          continue Outer; 
        }  

    }
    
    //straightforward
    int distance = 0, minDistance = Integer.MAX_VALUE;
    Rect rect = null;
    Outer: for(Point click : clicks){
      
      for(int i = rects.size()-1; i> -1; i--){
        rect = rects.get(i);
        if(rect.isInclude(click.x, click.y)) {  // show rectangle
          System.out.println( int2Char( rect.index ));
          continue Outer; 
        }
      }
      
      //else show icon
      minDistance = Integer.MAX_VALUE;
      String minIndex = "";
      for(Icon icon : icons){
        distance = (click.x - icon.x) * (click.x - icon.x) + (click.y - icon.y) * (click.y - icon.y);
        
        if(distance < minDistance ){
          minIndex = String.format("%3d", icon.index);
          minDistance = distance ;
        }else if(distance == minDistance)
          minIndex += String.format("%3d", icon.index);
        
      }
      System.out.println( minIndex);
      
    }
  }
    
}