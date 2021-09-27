package graph.maze;

import java.awt.*;
import java.util.Random;

public class MazeCreateDFS implements MazeCreateToolkit 
{ 
//  public static final TVCategory logger = (TVCategory) TVCategory.getInstance(MazeCreateDFS.class);
//    private static final Logger logger = LoggerFactory.getLogger(MazeCreateDFS.class);

  private Maze myMaze = null;
  
  private Random random = new Random();
  
  public MazeCreateDFS(Maze myMaze){
    this.myMaze = myMaze;   
  }
  
  
  public boolean createMaze(Graphics g, int speed)
  {     
    createMaze( g, myMaze.map[random.nextInt(myMaze.getMazeColCount())][random.nextInt(myMaze.getMazeRowCount())], speed);

    //highlight the entrance and exit
    myMaze.painStartEnd(g, speed);
    return true;
  }
  
  /*
   * 
   */
  private void createMaze(Graphics g, Cell theCell, int speed)
  {
    
    // when all cell have been visited, the maze is done. 
    if( myMaze.visitedCount == myMaze.cellCount)
      return;
    
    if(!myMaze.isVisited(theCell))
    {
      theCell.visited = true;
      myMaze.visitedCells[myMaze.visitedCount++] = theCell; 
    }
    
    Cell nextCell;   
    // Randomized select a cell from neighbors
    nextCell = myMaze.getNeighborInRandom (theCell);
    
    //if all the 4 neighbors have been visited , create a new maze.
    if(nextCell == null)
    {       
      //paint this cell
      theCell.paintBorder(g, speed);
      
      createMaze(g, myMaze.visitedCells[random.nextInt(myMaze.visitedCount)], speed);
      return;
    }
    
    // remove the wall between the current cell and the chosen cell
    myMaze.breakWall(theCell, nextCell);
    
    //init the cell and paint this cell border     
    myMaze.paintCellBoard(g, theCell, speed);
    
    //recurse with the neighbor as the current cell
    createMaze(g, nextCell, speed);    
    
  }

}
