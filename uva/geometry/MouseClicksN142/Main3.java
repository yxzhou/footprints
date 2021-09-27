package uva.geometry.MouseClicksN142;

import java.io.*;
import java.util.*;

class Main3
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
  
  
  //int[] fab = {2, 3, 5, 8, 13, 21, 34, 55, 89, 144, 233, 377, 500};
  final static int N = 500;
  private static final int ASCII_VALUE_OF_A_PREFIX = 64; // 'A' is 65
  
  public static char int2Char(int digit){
    return   (char) (ASCII_VALUE_OF_A_PREFIX - digit);
  } 
  
  public static void main(String[] args) throws Exception {

    //init
    Main3 sv = new Main3();
    Scanner in = new Scanner(new BufferedInputStream(System.in), "UTF-8");
    
    int[][] matrix = new int[N][N];  // default it's 0, it will be filled-in by (0-the rectangle index) or (the radius when filled by icon)
    
    int iIndex = 1, rIndex = -1;
    List<Icon> icons = new ArrayList<Icon> (); 
    List<Point> clicks = new ArrayList<Point> (); 
    
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
        
//        if("".equals(line.trim()))
//          continue;
                  
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
                  
                //fill in the rectangle index
                for(int y = y1; y <= y2; y ++){
                    for(int x=x1; x<=x2; x ++)
                        matrix[y][x] = rIndex;
                }
                    
                rIndex --;
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
    for(int i = icons.size()-1; i> -1; i--){
      iconTmp = icons.get(i);
      if(matrix[iconTmp.y][iconTmp.x] < 0)
        icons.remove(i);
    }
    
    //flood-fill with the icon index
    //sv.floodfillin(matrix, icons, clicks);
    
    //raster the board where icon in
    
    //straightforward
    sv.straightforward(matrix, icons, clicks);
    
  }
  
  
  private void straightforward(int[][] matrix, List<Icon> icons, List<Point> clicks){
    
    int distance = 0, minDistance = Integer.MAX_VALUE;
    for(Point click : clicks){
      if(matrix[click.y][click.x] < 0){  // show rectangle
        System.out.println( int2Char( matrix[click.y][click.x] ));
        continue; 
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
  
  private void rasterBoard(int[][] matrix, List<Icon> icons, List<Point> clicks){
    
    //split the board in 10*10 grid.
    for(Icon icon : icons){
      
    }
    
  }
  
  
  private boolean isAllFilledIn(int[][] matrix){
    for(int y = 0; y < N; y ++)
      for(int x=0; x< N; x ++)
          if(matrix[y][x] == 0)
            return false;
      
    return true;
}
  
  private void floodfillin(int[][] matrix, List<Icon> icons, List<Point> clicks){
    String[][] matrix4Icon = new String[N][N];  // addition to matrix, it will be filled in icon index, "  1 3".
    
    int countFilled = 0, totalFilled = N * N;    
    
    for(int y = 0; y < N; y ++)
      for(int x=0; x< N; x ++)
        if(matrix[y][x] < 0)
          countFilled ++;
    
    //for( int radius = 1, x=0, y=0; !isAllFilledIn(matrix) ; radius ++){
    for( int radius = 1, x=0, y=0; countFilled < totalFilled  ; radius ++){
      for(Icon icon : icons){
        //left line
        x = icon.x - radius; 
        if(x >=0 ){
          for(y = Math.max(icon.y - radius, 0); y< Math.min(icon.y + radius + 1, N) ; y++  ){
            if(matrix[y][x] == 0){
              matrix4Icon[y][x] = String.format("%3d", icon.index);
              matrix[y][x] = radius;
              countFilled ++;
            }else if(matrix[y][x] == radius)
              matrix4Icon[y][x] += String.format("%3d", icon.index);
              
          }
        }
        
        //right line
        x = icon.x + radius;
        if(x < N){
          for(y = Math.max(icon.y - radius, 0); y< Math.min(icon.y + radius + 1, N) ; y++  ){
            if(matrix[y][x] == 0){
              matrix4Icon[y][x] = String.format("%3d", icon.index);
              matrix[y][x] = radius;
              countFilled ++;
            }else if(matrix[y][x] == radius)
              matrix4Icon[y][x] += String.format("%3d", icon.index);
              
          }
        }
        
        //top line
        y = icon.y - radius; 
        if(y >=0 ){
          for(x = Math.max(icon.x - radius, 0) + 1; x< Math.min(icon.x + radius, N) ; x++  ){
            if(matrix[y][x] == 0){
              matrix4Icon[y][x] =  String.format("%3d", icon.index);
              matrix[y][x] = radius;
              countFilled ++;
            }else if(matrix[y][x] == radius)
              matrix4Icon[y][x] +=  String.format("%3d", icon.index);
               
          }
        }    
          
        //botton line  
        y = icon.y + radius; 
        if(y < N ){
          for(x = Math.max(icon.x - radius, 0) + 1; x< Math.min(icon.x + radius, N); x++  ){
            if(matrix[y][x] == 0){
              matrix4Icon[y][x] = String.format("%3d", icon.index);
              matrix[y][x] = radius;
              countFilled ++;
            }else if(matrix[y][x] == radius)
              matrix4Icon[y][x] += String.format("%3d", icon.index);
               
          }
        }         
      }
      
    }
    
    
    //output
    for(Point click : clicks){
      
      if(matrix[click.y][click.x] < 0)  // show rectangle
        System.out.println( int2Char( matrix[click.y][click.x] ));
      else // show icon
        System.out.println( matrix4Icon[click.y][click.x]);
      
    }
  }
  
  
}
