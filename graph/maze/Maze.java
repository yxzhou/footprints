package fgafa.graph.maze;

import java.awt.Graphics;
import java.util.Random;
import java.util.Stack;

import com.televigation.log.TVCategory;

/** 
 *  ---------
 * | Maze    |
 * |         |
 *  ---------
 * | Status  |
 * 
 * @author yxzhou
 *
 */

public class Maze
{
  
  public static final TVCategory logger = (TVCategory) TVCategory.getInstance(Maze.class);
  
  /**
   * 
   */
  private static final long serialVersionUID = 1L;
  
  private Random random = new Random();
  
  private int mazeRowCount = 12;   // the rows of maze
  private int mazeColCount = 12;   // the columns of maze

  private int cellWidth = 10;   // the width of the cell
  

  
  private int zeroPoint = this.getCellWidth();
  private int zeroX = this.getCellWidth();  // the start point 
  private int zeroY = this.getCellWidth();  // the start point  
  
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

  public Cell[] visitedCells = null;
  public int visitedCount = 0;
  public int cellCount = 0;
  
  public Stack<Cell> paintedCells = new Stack<Cell>();
  public boolean resolved = false;
  
  
  public int getMazeRowCount() {
    return mazeRowCount;
  }


  public void setMazeRowCount(int mazeRowCount) {
    this.mazeRowCount = mazeRowCount;
  }


  public int getMazeColCount() {
    return mazeColCount;
  }


  public void setMazeColCount(int mazeColCount) {
    this.mazeColCount = mazeColCount;
  }


  public int getCellWidth() {
    return cellWidth;
  }

  public int getCellHeight() {
    return getCellWidth();
  }

  public void setCellWidth(int cellWidth) {
    this.cellWidth = cellWidth;
  }


  public int getZeroPoint() {
    return zeroPoint;
  }


  public void setZeroPoint(int zeroPoint) {
    this.zeroPoint = zeroPoint;
  }


  public int getZeroX() {
    return zeroX;
  }


  public void setZeroX(int zeroX) {
    this.zeroX = zeroX;
  }


  public int getZeroY() {
    return zeroY;
  }


  public void setZeroY(int zeroY) {
    this.zeroY = zeroY;
  }


  public int getCanvasWidth() {
    return this.getCellWidth() * this.getMazeColCount()+ this.getZeroX()*2 ;
  }


  public int getCanvasHeight() {
    return this.getCellWidth() * this.getMazeRowCount()+this.getZeroY()*2 ;  
  }
  
  public int getStatusX() {
    return getZeroX();
  }

  public int getStatusY() {
    return getCanvasHeight() + this.getCellHeight() ;
  }
  
  public int getStatusWidth() {
    return getCanvasWidth() ;
  }


  public int getStatusHeight() {
    return getCellHeight() * 2 ;
  }
  
  
  public void paintCanvas(Graphics g)
  {
    //paint the maze background
    g.setColor(MazeConstant.BACKGROUNDCOLOR);
    g.fillRoundRect(0, 0, this.getCanvasWidth(), this.getCanvasHeight(), this.getZeroPoint(), this.getZeroPoint());
    
    //paint the maze boarder 
    g.setColor(MazeConstant.BOARDCOLOR);
    g.drawRoundRect(0, 0, this.getCanvasWidth(), this.getCanvasHeight(), this.getZeroPoint(), this.getZeroPoint());
  
  }

  /*
   * check if the cell is visited
   */
  public boolean isVisited(Cell cell)
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

  
  
  /**
   * Return a string representation of the maze.
   */
  public String toString() {
    StringBuilder s = new StringBuilder();

    s.append(" colsnum:"+this.getMazeColCount());
    s.append(" rowsnum:"+this.getMazeRowCount());
    
    s.append("{");
    for( int row=0; row < this.getMazeRowCount(); row++){
      s.append("[");
      for( int col=0; col < this.getMazeColCount(); col++){
        s.append("(");
        for ( int i=0;i<4;i++)
        { 
          s.append(map[col][row].walls[i]); 
          s.append(",");
        }
        s.append("),");
      }
      s.append("]");
    }
    
    s.append("}");
    
    return s.toString();
  }

  public void paintCellBoard(Graphics g, Cell theCell, int speed){
    g.setColor(MazeConstant.BACKGROUNDCOLOR);
    theCell.paintCell(g, speed);
    
    g.setColor(MazeConstant.WALLCOLOR);
    theCell.paintBorder(g, -1);
   
  }
  
  public void paintCellRect(Graphics g, Cell theCell, int speed){
    g.setColor(MazeConstant.ROUTECOLOR);
    theCell.paintCell(g, speed);
    
    g.setColor(MazeConstant.WALLCOLOR);
    theCell.paintBorder(g, -1);
  } 

  public void repaintMaze(Graphics g)
  {    
    logger.info(" || repaintMaze... "+ System.currentTimeMillis()+ " Maze:" + this.toString());
    
    //paint the maze
    g.setColor(MazeConstant.WALLCOLOR);
    for( int row=0; row < this.getMazeRowCount(); row++)
      for( int col=0; col < this.getMazeColCount(); col++)
        this.map[col][row].paintBorder(g, -1);
    
    //highlight the entrance and exit
//    g.setColor(HIGHLIGHTCOLOR);
//    map[0][this.getMazeRowCount()-1].paintBorder(g);
//    map[this.getMazeColCount()-1][0].paintBorder(g);
    
  }
  
  public void repaintRoute(Graphics g)
  {    
    logger.info(" || repaintRoute... "+ System.currentTimeMillis()+ " resolved:"+ this.resolved + " repaintedRoute:" + this.paintedCells.toString());
    
    if(!this.resolved) return; 
    
    //paint the maze
    g.setColor(MazeConstant.ROUTECOLOR);

    for (Cell cell : this.paintedCells){
      this.paintCellRect(g, cell, -1);
    }     
  }
  
  public void init()
  {
    cellCount = this.getMazeColCount() * this.getMazeRowCount();
    visitedCells = new Cell[cellCount];

    // init
    Cell cell;
    map = new Cell[this.getMazeColCount()][this.getMazeRowCount()];
    for( int row=0; row < this.getMazeRowCount(); row++)
      for( int col=0; col < this.getMazeColCount(); col++)
      {
        cell = new Cell(this.getZeroX(), this.getZeroY(), col, row, this.getCellWidth() );
        cell.visited = false;
        
        map[col][row] = cell;
      }
    
    // set top-left cell and bottom-right cell as the entrance and exit
    this.intStartEnd();
  }
  
  /*
   * paint the entrance and exit
   */
  public void intStartEnd(){
    
    // set top-left cell and bottom-right cell as the entrance and exit
    Cell start = this.map[0][0];
    Cell end = this.map[this.getMazeColCount() - 1][this.getMazeRowCount() - 1];
    
    start.entrance = Cell.LEFT;
    start.walls[Cell.LEFT] = Cell.NOWALL;

    end.walls[Cell.RIGHT] = Cell.NOWALL;
  }
  

  /*
   * paint the entrance and exit
   */
  public void painStartEnd(Graphics g, int speed){
    
    // set top-left cell and bottom-right cell as the entrance and exit
    Cell start = this.map[0][0];
    Cell end = this.map[this.getMazeColCount() - 1][this.getMazeRowCount() - 1];
    
    paintCellRect(g, start, speed);
    paintCellRect(g, end, speed);
    
  }
  
  
  /*
   * random get a unvisited neighbor
   * when no unvisited neighbor,  return null;
   * 
   */
  public Cell getNeighborInRandom(Cell theCell){
    Cell nextCell = null;
    
    // find the unvisited neighbor, the order is left, top, right and bottom, 
    int neibCount = 0;
    Cell[] neighbours = new Cell[4];

    // left
    if(theCell.col-1>=0   
        && !(nextCell =this.map[theCell.col-1][theCell.row]).visited)
    {
      neighbours[neibCount++] = nextCell;
    }
    // top
    if(theCell.row-1>=0 
        && !(nextCell=this.map[theCell.col][theCell.row-1]).visited)
    {
      neighbours[neibCount++] = nextCell;
    }
    // right
    if(theCell.col+1<this.getMazeColCount()
        && !(nextCell = this.map[theCell.col+1][theCell.row]).visited)
    {
      neighbours[neibCount++] = nextCell;
    }
    // bottom
    if(theCell.row+1<this.getMazeRowCount() 
        && !(nextCell = this.map[theCell.col][theCell.row+1]).visited)
    {
      neighbours[neibCount++] = nextCell;
    }
    
    // random select a unvisited neighbor, or return null
    if(neibCount > 0)
    {       
      // Randomized select a cell from neighbors
      nextCell = neighbours[random.nextInt(neibCount)];
    }else
      nextCell = null;
    
    return nextCell;
  }
  
  /*
   * random get a neighbor whose state is not equal to theState and is divided by a Wall
   * when no unvisited neighbor,  return null;
   * 
   */
  public Cell getNeighborInRandom(Cell theCell, int theState){
    Cell nextCell = null;
    
    // find the unvisited neighbor, the order is left, top, right and bottom, 
    int neibCount = 0;
    Cell[] neighbours = new Cell[4];

    // left
    if(theCell.col-1>=0   
        && (nextCell =this.map[theCell.col-1][theCell.row]).state != theState)
    {
      neighbours[neibCount++] = nextCell;
    }
    // top
    if(theCell.row-1>=0 
        && (nextCell=this.map[theCell.col][theCell.row-1]).state != theState)
    {
      neighbours[neibCount++] = nextCell;
    }
    // right
    if(theCell.col+1<this.getMazeColCount()
        && (nextCell = this.map[theCell.col+1][theCell.row]).state != theState)
    {
      neighbours[neibCount++] = nextCell;
    }
    // bottom
    if(theCell.row+1<this.getMazeRowCount() 
        && (nextCell = this.map[theCell.col][theCell.row+1]).state != theState)
    {
      neighbours[neibCount++] = nextCell;
    }
    
    // random select a unvisited neighbor, or return null
    if(neibCount > 0)
    {       
      // Randomized select a cell from neighbors
      nextCell = neighbours[random.nextInt(neibCount)];
    }else
      nextCell = null;
    
    return nextCell;
  }
  
  /*
   * break the wall between theCell and nextCell
   * if they are not connected and duplicated, no wall will be changed.  
   * 
   */
  public void breakWall(Cell theCell, Cell nextCell ){
    
    // remove the wall between the current cell and the chosen cell

    if(nextCell.col == theCell.col)
    {
      // when up and down relationship, 
      if( nextCell.row == theCell.row - 1 ){
        // when the neighbor is top to the current 
        nextCell.walls[Cell.BOTTOM] = Cell.NOWALL;
        theCell.walls[Cell.TOP] = Cell.NOWALL;
      }else if( nextCell.row == theCell.row + 1 ){
        // when the neighbor is bottom to the current 
        nextCell.walls[Cell.TOP] = Cell.NOWALL;
        theCell.walls[Cell.BOTTOM] = Cell.NOWALL;
      }
    }else if (nextCell.row == theCell.row) {
      // when right and left relationship
      if( nextCell.col == theCell.col - 1 ){
        //when the neighbor is left to the current
        nextCell.walls[Cell.RIGHT] = Cell.NOWALL;
        theCell.walls[Cell.LEFT] = Cell.NOWALL;
      }else if(nextCell.col == theCell.col + 1){
        //when the neighbor is right to the current
        nextCell.walls[Cell.LEFT] = Cell.NOWALL;
        theCell.walls[Cell.RIGHT] = Cell.NOWALL;      
      }
      
    }
    
  }
  
}
