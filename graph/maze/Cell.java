package graph.maze;

import java.awt.Graphics;

public class Cell
{

  static final int LEFT = 0;
  static final int TOP = 1;
  static final int RIGHT = 2;
  static final int BOTTOM = 3;
  
  static final int NOWALL = 0;
  static final int HAVEWALL = 1;
  
  int state = 0;

  int x = 0;  // default it's the left-top of the cell
  int y = 0;   //default it's the left-top of the cell

  int col = 0;
  int row = 0;

  int cellWidth = 10;
  
  boolean visited = false;
  
  boolean painted = false;
  int entrance = 0;
  
  /*
   *  init the walls, the order is left - top - right - bottom, the default value is HAVEWALL
   */ 
  int[] walls = new int[]{HAVEWALL,HAVEWALL,HAVEWALL,HAVEWALL};
  
  public Cell(int zerox, int zeroy, int col, int row, int width){
    this.x = zerox + col*width;
    this.y = zerox + row*width;
    this.col = col;
    this.row = row;
    this.cellWidth = width;
    
  }
  
  // draw the cell
  public void paintBorder(Graphics g, int speed) 
  {

    sleep(speed);
    
    int x1 = 0,y1=0,x2=0,y2=0;
    for ( int i=0;i<4;i++)
    {     
      if(walls[i] == NOWALL)
        continue;
      
      switch(i)
      {
      // left
      case LEFT:
        x1 = this.x;
        y1 = this.y;
        x2 = x1;
        y2 = y1 + cellWidth;
        break;
      // top
      case TOP:
        x1 = this.x;
        y1 = this.y;
        x2 = x1 + cellWidth;
        y2 = y1;
        break;
      // right
      case RIGHT:
        x1 = this.x+cellWidth;
        y1 = this.y;
        x2 = x1;
        y2 = y1 + cellWidth;
        break;
      // bottom
      case BOTTOM:
        x1 = this.x;
        y1 = this.y +cellWidth;
        x2 = x1 +cellWidth;
        y2 = y1;
        break;
      }
      // drawoline
      g.drawLine(x1, y1, x2, y2);
    }
        
  }
  
  public void paintCell(Graphics g, int speed){
   
    sleep(speed);

      g.fillRect( this.x, this.y, cellWidth, cellWidth );
  }
  
  private void sleep(int speed){
    
    if(speed > 0)
      try {
        Thread.sleep(speed);
      }
      catch (InterruptedException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
    
  }
  
  /**
   * Return a string representation of the Cell.
   */
  public String toString() {
    StringBuilder s = new StringBuilder();
    //s.append("(");
    s.append(this.col);
    s.append(",");
    s.append(this.row);
  //s.append(")");
    return s.toString();
  }
  
  
}
