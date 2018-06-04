package fgafa.graph.maze;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Random;

import com.televigation.log.TVCategory;

/*
 * The <tt>MazeCreateKruskal</tt> class represents a Maze generation with Kruskal algorithm.
 * 
 * 
 * 
 */
public class MazeCreateKruskal implements MazeCreateToolkit
{

  public static final TVCategory logger = (TVCategory) TVCategory
      .getInstance(MazeCreateKruskal.class);

  private Maze myMaze = null;

  private Hashtable<Integer, ArrayList<Cell>> htCells = new Hashtable<Integer, ArrayList<Cell>>(); // key is the
  // Cell.state,
  // value is
  // List<Cell>
  private Random random = new Random();



  public MazeCreateKruskal(Maze myMaze) {
    this.myMaze = myMaze;
  }



  /*
   * (non-Javadoc)
   * 
   * @see algs4.graph.maze.MazeCreateToolkit#createMaze(java.awt.Graphics, int)
   */
  public boolean createMaze(Graphics g, int speed) {
    // init the state
    // init the set for Walls. To a Maze that has w columns and h rows, except
    // the border, it has walls, h(w-1)+ w(h-1)
    init();

    Cell theCell = null;
    Cell nextCell = null;
    while (htCells.size() > 1) {
      // random get a unvisited cell,
      theCell = myMaze.map[random.nextInt(myMaze.getMazeColCount())][random
          .nextInt(myMaze.getMazeRowCount())];

      // random try to get a neighbor and wall, the neighbor belongs to
      // different set with theCell
      nextCell = myMaze.getNeighborInRandom(theCell, theCell.state);

      if (nextCell != null) {
        // remove the wall between them
        myMaze.breakWall(theCell, nextCell);

        //join them
        join(theCell, nextCell);
        
        // paint this cell border
        myMaze.paintCellBoard(g, theCell, speed);
        myMaze.paintCellBoard(g, nextCell, -1);

      }
    }
    return true;

  }

  private void init() {

    int state;
    ArrayList<Cell> cells = null;
    for (int row = 0; row < myMaze.getMazeRowCount(); row++)
      for (int col = 0; col < myMaze.getMazeColCount(); col++) {
        state = myMaze.getMazeColCount() * (row - 1) + col;
        cells = new ArrayList<Cell>();

        myMaze.map[col][row].state = state;
        cells.add(myMaze.map[col][row]);
        htCells.put(state, cells);
      }
  }



  /*
   * 
   * 
   */
  private void join(Cell theCell, Cell nextCell) {
    // init, suppose the list that includes theCell is smaller, the list that
    // includes nextCell is Trunk
    ArrayList<Cell> smallList = htCells.get(theCell.state);
    int trunkState = nextCell.state;
    int smallState = theCell.state;

    // check the list size, reverse if the suppose is wrong
    if (smallList.size() > htCells.get(trunkState).size()) {
      smallList = htCells.get(nextCell.state);
      trunkState = theCell.state;
      smallState = nextCell.state;
    }

    // change the cells' state in the smallList
    for (Cell cell : smallList) {
      cell.state = trunkState;
    }

    // merge the smallList in the trunkList, remove the smallList
    htCells.get(trunkState).addAll(htCells.get(smallState));
    htCells.remove(smallState);

  }



  /*
   * check if 2 Cells are connected. if these 2 Cells are same, return true;
   */
//  private boolean isConnected(Cell theCell, Cell nextCell) {
//    return (theCell.state == nextCell.state);
//  }

}
