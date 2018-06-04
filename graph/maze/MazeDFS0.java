package fgafa.graph.maze;

import java.applet.Applet;
import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

/*************************************************************************
 * Compilation:  javac Maze.java
 *  Execution:    java Maze
 *  Dependencies: Applet.java 
 *                Color.java Graphics.java
 *                Random.java
 *
 *  Generation Maze with depth-first
 *  The Maze is a connected component,  and it's a Acyclic
 *  
 *************************************************************************/

public class MazeDFS0 extends Applet
{

    private int mazeRowCount = 10;   // the rows of maze
    private int mazeColCount = 12;   // the columns of maze

    private int cellWidth = 10;   // the width of the cell
    private static final Color WALL_COLOR = Color.blue;   // the color of the cell board
    private static final Color BOARDER_COLOR = Color.gray;   // the color of the cell board
    
    private int zeroPoint = cellWidth;
    private int zeroX = zeroPoint;  // the start point 
    private int zeroY = zeroPoint;  // the start point 
    private Random random = null;

    private Cell[] visitedCells = null;
    private int visitedCount = 0;
    private int cellCount = 0;
    
    /*
     * map[column][row]
     * 0----> x++ 
     * |  
     * |
     * v
     * y++
     * 
     */ 
    Cell map[][] = null;
    
    /*
     * This method that will be automatically called when the applet is started.
     * (non-Javadoc)
     * @see java.applet.Applet#init()
     */
    public void init()
    {
      super.init();
      if(getParameter("mazerow")!=null)
        mazeRowCount = Integer.parseInt(
            getParameter("mazerow"));
      
      if(getParameter("mazecol")!=null)
        mazeColCount = Integer.parseInt(
            getParameter("mazecol"));
      
      if(getParameter("cellwidth")!=null)
        cellWidth = Integer.parseInt(
            getParameter("cellwidth"));

      if(getParameter("zeroPoint")!=null){
        zeroPoint = Integer.parseInt(
            getParameter("zeroPoint"));
        zeroX = zeroPoint;
        zeroY = zeroPoint;
      }

      
      cellCount = mazeColCount * mazeRowCount;
      visitedCells = new Cell[cellCount];
      random = new Random();
      // init
      Cell cell;
      map = new Cell[mazeColCount][mazeRowCount];
      for( int row=0; row < mazeRowCount; row++)
        //for( int y : mazeRowCount)
        for( int col=0; col < mazeColCount; col++)
        //for( int x : mazeColCount)
        {
          cell = new Cell();
          cell.x = zeroX + col*cellWidth;
          cell.y = zeroY + row*cellWidth;
          cell.col = col;
          cell.row = row;
          map[col][row] = cell;
        }
    }
    /*
     * The standard method that you have to use to paint things on screen
     * This overrides the empty Applet method, you can't called it "display" for example
     * 
     * (non-Javadoc)
     * @see java.awt.Container#paint(java.awt.Graphics)
     */
    
    public void paint(Graphics g)
    {
      createMaze();
      paintMaze(g);
    }
    
    private void createMaze()
    {
      // Randomized  
      initMaze(
          map[random.nextInt(mazeColCount)][random.nextInt(mazeRowCount)]);
      
      // set left-bottom cell and right-top cell as the start point and end point
      map[0][mazeRowCount-1].walls[Cell.BOTTOM] = Cell.NOWALL;
      map[mazeColCount-1][0].walls[Cell.TOP] = Cell.NOWALL;
    }
    
    //depth-first init 
    private void initMaze(Cell theCell)
    {
      // when all cell have been visited, the maze is done. 
      if( visitedCount == cellCount)
        return;
      
      if(!isVisited(theCell))
      {
        theCell.visited = true;
        visitedCells[visitedCount++] = theCell; 
      }
      // find the unvisited neighbor, the order is left, top, right and bottom, 
      int neibCount = 0;
      Cell[] neighbours = new Cell[4];
      Cell nextCell;
      // left
      if(theCell.col-1>=0 
          && !(nextCell =map[theCell.col-1][theCell.row]).visited)
      {
        neighbours[neibCount++] = nextCell;
      }
      // top
      if(theCell.row-1>=0 
          && !(nextCell=map[theCell.col][theCell.row-1]).visited)
      {
        neighbours[neibCount++] = nextCell;
      }
      // right
      if(theCell.col+1<this.mazeColCount
          && !(nextCell = map[theCell.col+1][theCell.row]).visited)
      {
        neighbours[neibCount++] = nextCell;
      }
      // bottom
      if(theCell.row+1<this.mazeRowCount 
          && !(nextCell = map[theCell.col][theCell.row+1]).visited)
      {
        neighbours[neibCount++] = nextCell;
      }
      // if all the 4 neighbors have been visited , create a new maze.
      if(neibCount == 0)
      {
        initMaze(visitedCells[random.nextInt(visitedCount)]);
        return;
      }
      // Randomized select a cell from neighbors
      nextCell = neighbours[random.nextInt(neibCount)];
      // remove the wall between the current cell and the chosen cell
      // when the neighbor is left to the current, 
      if(nextCell.col < theCell.col)
      {
        nextCell.walls[Cell.RIGHT] = Cell.NOWALL;
        theCell.walls[Cell.LEFT] = Cell.NOWALL;
      }
      // when the neighbor is top to the current 
      else if(nextCell.row < theCell.row)
      {
        nextCell.walls[Cell.BOTTOM] = Cell.NOWALL;
        theCell.walls[Cell.TOP] = Cell.NOWALL;
      }
      // when the neighbor is right to the current
      else if(nextCell.col > theCell.col)
      {
        nextCell.walls[Cell.LEFT] = Cell.NOWALL;
        theCell.walls[Cell.RIGHT] = Cell.NOWALL;
      }
      // when the neighbor is bottom to the current
      else if(nextCell.row > theCell.row)
      {
        nextCell.walls[Cell.TOP] = Cell.NOWALL;
        theCell.walls[Cell.BOTTOM] = Cell.NOWALL;
      }
      //recurse with the neighbor as the current cell
      initMaze(nextCell);
    }
    
    /*
     * check if the cell is visited
     */
    private boolean isVisited(Cell cell)
    {
      if(visitedCount == 0 )
        return false;
      
      for( int i =0; i < visitedCount ; i++)
      {
        if( visitedCells[i] == cell )
        //if( cell.equals(visitedCells[i]))
          return true;
      }
      return false;
    }
    
    private void paintMaze(Graphics g)
    {
      //paint the maze boarder 
      g.setColor(BOARDER_COLOR);
      g.drawRoundRect(0, 0, cellWidth * mazeColCount+zeroPoint*2, cellWidth * mazeRowCount+zeroPoint*2, zeroPoint, zeroPoint);
      
      //paint the maze
      for( int row=0; row < mazeRowCount; row++)
        //for( int y : mazeRowCount)
        for( int col=0; col < mazeColCount; col++)
          map[col][row].paint(g);
    }
    
    class Cell
    {
      static final int LEFT = 0;
      static final int TOP = 1;
      static final int RIGHT = 2;
      static final int BOTTOM = 3;
      
      static final int NOWALL = 0;
      static final int HAVEWALL = 1;
      
      int state = 0;

      int x = 0;  // the left-top of the cell
      int y = 0;   // the left-top of the cell

      int col = 0;
      int row = 0;

      boolean visited = false;
      /*
       *  init the walls, the order is left - top - right - bottom, the default value is HAVEWALL
       */ 
      int[] walls = new int[]{HAVEWALL,HAVEWALL,HAVEWALL,HAVEWALL};
      
      // draw the cell
      void paint(Graphics g) 
      {
        g.setColor(WALL_COLOR);
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
        
        if(false)
          try {
            Thread.sleep(1000);
          }
          catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
          }
        
      }
    }
    
    
    /**
     * @param args
     */
    public static void main(String[] args) {
      // TODO Auto-generated method stub
      
      

    }
    
    
  }

