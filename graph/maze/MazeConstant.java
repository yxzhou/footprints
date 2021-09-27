package graph.maze;

import java.awt.Color;

public class MazeConstant
{
  public static final Color BACKGROUNDCOLOR = Color.white; // the color of the canvas background
  public static final Color BOARDCOLOR = Color.gray;   // the color of the canvas board
  public static final Color WALLCOLOR = Color.blue;   // the color of the cell wall
  public static final Color HIGHLIGHTCOLOR = Color.pink;   // the color of the cell board
  public static final Color ROUTECOLOR = Color.green;   // the color of the cell board
  public static final Color STATUSCOLOR = Color.green;   // the color of the cell board
  
  public static final int STATUS_INIT = 0;
  public static final int STATUS_CREATING = 1;
  public static final int STATUS_CREATED_Y = 2;
  public static final int STATUS_CREATED_N = 3;
  public static final int STATUS_RESOLVING = 4;
  public static final int STATUS_RESOLVED_Y = 5;
  public static final int STATUS_RESOLVED_N = 6;
  public static final int STATUS_START = 7;
  public static final int STATUS_STOP = 8;
  public static final int STATUS_DESTROY = 9;
  
  public static final String[] STATUSDEF = 
  {"Init",
   "Creating",
   "Created Successful",
   "Created Failed",
   "Resolving",
   "Resolved Successful",
   "Resolved Failed",
   "Starting",
   "Stoping",
   "Destroying"
  };
  
  public static final int GENERATOR_DFS = 0;
  public static final int GENERATOR_KRUSKAL = 1;
  public static final int GENERATOR_PRIM = 2;
  
  public static final String[] GENERATOR = {
    "DFSGenerator",
    "KruskalGenerator",
    "PrimGenerator"
  };
      
}
