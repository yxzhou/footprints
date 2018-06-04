package fgafa.graph.maze;

import java.awt.Graphics;
import java.util.Stack;

import com.televigation.log.TVCategory;

public class MazeResolveDFS implements MazeResolveToolkit 
{
  public static final TVCategory logger = (TVCategory) TVCategory.getInstance(MazeResolveDFS.class);
  
  private Maze myMaze = null;
  
  public MazeResolveDFS(Maze myMaze){
    this.myMaze = myMaze;   
  }
  
  
  public void resolveMaze(Graphics g, int speed)
  {
    //set the entrance of Maze
    Cell theCell = myMaze.map[0][0];
    theCell.entrance = Cell.LEFT;
        
    //paintedCells.add(theCell);
    solveMazeDFS(g, speed, myMaze.paintedCells, theCell);
    
  }
  
  private boolean solveMazeDFS(Graphics g, int speed, Stack<Cell> paintedCells, Cell theCell) {
    logger.info(" || solve... "+ System.currentTimeMillis()+ "theCell:" + theCell + " paintedRoute:" + paintedCells.toString());
    
    //resolved mean finished,  
//    if(resolved )
//      return true;
    
    //paint and add in the Stack,  
    paintedCells.add(theCell);
    myMaze.paintCellRect(g, theCell, speed);
    
    //check next cells: if no next cells, return false; if has next cells, recurrence on these cells. And if one is the exit, set resolved = true, and return true;
    //if the return is false, erase and remove it in the Stack.   
    Stack<Cell> nextCells = new Stack<Cell>();
    Cell nextCell = null;
    for ( int i=theCell.entrance+1 ;i< theCell.entrance + 4;i++)
    {
      if(theCell.walls[i%4] == Cell.HAVEWALL)
        continue;
      
      switch(i%4)
      {
        // left
        case Cell.LEFT:
          if(theCell.col >= 1 ){
            nextCell = myMaze.map[theCell.col-1][theCell.row];
            nextCell.entrance = Cell.RIGHT;
            nextCells.add(nextCell);
          }
          break;
        // top
        case Cell.TOP:
          if(theCell.row >=1 ){
            nextCell = myMaze.map[theCell.col][theCell.row-1];
            nextCell.entrance = Cell.BOTTOM;
            nextCells.add(nextCell);
          }              
          break;
        // right
        case Cell.RIGHT:
          if(theCell.col+1<myMaze.getMazeColCount()){
            nextCell =myMaze.map[theCell.col+1][theCell.row];
            nextCell.entrance = Cell.LEFT;
            if(nextCell.col+1==myMaze.getMazeColCount() && nextCell.row+1==myMaze.getMazeRowCount() ){
              myMaze.resolved = true;
              paintedCells.add(nextCell);
              myMaze.paintCellRect(g, nextCell, speed);
              return true;
            }
            nextCells.add(nextCell);
          }
          break;
        // bottom
        case Cell.BOTTOM:
          if(theCell.row+1<myMaze.getMazeRowCount()){
            nextCell =myMaze.map[theCell.col][theCell.row+1];
            nextCell.entrance = Cell.TOP;
            
            if(nextCell.col+1==myMaze.getMazeColCount() && nextCell.row+1==myMaze.getMazeRowCount() ){
              myMaze.resolved = true;
              paintedCells.add(nextCell);
              myMaze.paintCellRect(g, nextCell, speed);
              return true;
            }
            nextCells.add(nextCell);        
          }
          break;
      }       
    }
    while( nextCells.size() > 0  ){ 
      if( solveMazeDFS(g, speed, paintedCells, nextCells.pop()))
        return true;
    }
    
    //it come to here, means no nextCells, or all the nextCells return are false  . 
    myMaze.paintCellBoard(g, theCell, speed);
    paintedCells.pop();
    return false;
  }

//  public Maze getMyMaze() {
//    return myMaze;
//  }

//  public void setMyMaze(Maze myMaze) {
//    this.myMaze = myMaze;
//  }
  
}
