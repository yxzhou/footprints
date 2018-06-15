package fgafa.graph.maze;


import java.awt.Graphics;
import java.util.Random;



/*************************************************************************
 *  Dependencies: Graphics.java
 *                
 *
 *  Generation Maze with Prim
 *  
 *  
 *************************************************************************/

public class MazeCreatePrim implements MazeCreateToolkit
{

//  public static final TVCategory logger = (TVCategory) TVCategory
//      .getInstance(MazeCreatePrim.class);
//  private static final Logger logger = LoggerFactory.getLogger(MazeApplet.class);


  private Maze myMaze = null;

  private Random random = new Random();

  public MazeCreatePrim(Maze myMaze) {
    this.myMaze = myMaze;
  }


  public boolean createMaze(Graphics g, int speed) {
    //init 
    Cell theCell = null;
    if(myMaze.visitedCount == 0){
      theCell = myMaze.map[random.nextInt(myMaze.getMazeColCount())][random.nextInt(myMaze.getMazeRowCount())];
      theCell.visited = true;
      myMaze.visitedCells[myMaze.visitedCount++] = theCell; 
      
      //paint this cell border  
      myMaze.paintCellBoard(g, theCell, speed);
    }

    Cell nextCell = null;
    // when all cell have been visited, the maze is done. 
    while( myMaze.visitedCount < myMaze.cellCount){
      //1 random get a Cell from visited Cell
      theCell = myMaze.visitedCells[random.nextInt(myMaze.visitedCount)];
                                
      //2 random get a unvisited neighbor 
      nextCell = myMaze.getNeighborInRandom (theCell);
        
      if(nextCell != null){
        //3 remove the wall between them
        myMaze.breakWall(theCell, nextCell);
        
        //4 paint this cell border  
        myMaze.paintCellBoard(g, theCell, speed);
        myMaze.paintCellBoard(g, nextCell, speed); 
        
        //5 set the neighbor as visited, visitedCount++,  
        nextCell.visited = true;
        myMaze.visitedCells[myMaze.visitedCount++] = nextCell; 
      }
    }
    
    //highlight the entrance and exit
    myMaze.painStartEnd(g, speed);

    return true;
  }
  

  
  
}
