package graph.maze;

import java.applet.Applet;
import java.awt.Graphics;

//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;

public class MazeApplet extends Applet
{
  /**
   * 
   */

  private static final long serialVersionUID = 3583853686079306810L;

  //public static final TVCategory logger = (TVCategory) TVCategory
  //    .getInstance(MazeApplet.class);
  //private static final Logger logger = LoggerFactory.getLogger(MazeApplet.class);


  private int generator = MazeConstant.GENERATOR_KRUSKAL;

  private int speed = 100; // millisecond
  private static boolean paintLog = true;

  private Graphics g = null;

  private static int status = MazeConstant.STATUS_INIT;

  private Maze myMaze = null;
  private MazeCreateToolkit myMazeCreate = null;
  private MazeResolveToolkit myMazeResolve = null;



  /*
   * This method that will be automatically called when the applet is started.
   * (non-Javadoc)
   * @see java.applet.Applet#init()
   */
  public void init() {
    //super.init();

    //buffer = new StringBuffer();
    addItem(" || initializing -" + MazeConstant.STATUSDEF[status]);

    if (getParameter("generator") != null)
      generator = Integer.parseInt(getParameter("generator"));

    if (getParameter("speed") != null)
      speed = Integer.parseInt(getParameter("speed"));
    if (getParameter("showlog") != null)
      paintLog = Boolean.parseBoolean(getParameter("showlog"));

    myMaze = new Maze();

    if (null == myMaze) {
      //System.out.println(" No such Algorithm !!"); 
      return;
    }

    if (getParameter("cellwidth") != null)
      myMaze.setCellWidth(Integer.parseInt(getParameter("cellwidth")));

    if (getParameter("zeroPoint") != null) {
      int zeroPoint = Integer.parseInt(getParameter("zeroPoint"));

      myMaze.setZeroPoint(zeroPoint);
      myMaze.setZeroX(zeroPoint);
      myMaze.setZeroY(zeroPoint);

    }

    if (getParameter("mazerow") != null)
      myMaze.setMazeRowCount(Integer.parseInt(getParameter("mazerow")));

    if (getParameter("mazecol") != null)
      myMaze.setMazeColCount(Integer.parseInt(getParameter("mazecol")));

    myMaze.init();

    myMazeCreate = getFactory(generator, myMaze);
    myMazeResolve = new MazeResolveDFS(myMaze);

    status = MazeConstant.STATUS_INIT;
  }

  /*
   * 
   */
  private static final MazeCreateToolkit getFactory(int generator, Maze myMaze) {

    switch (generator) {
      case MazeConstant.GENERATOR_DFS:
        return new MazeCreateDFS(myMaze);
      case MazeConstant.GENERATOR_KRUSKAL:
        return new MazeCreateKruskal(myMaze);
      case MazeConstant.GENERATOR_PRIM:
        return new MazeCreatePrim(myMaze);
    }

    throw new IllegalArgumentException(MazeConstant.GENERATOR[generator]);
  }



  /*
   * The standard method that you have to use to paint things on screen
   * This overrides the empty Applet method, you can't called it "display" for example
   * 
   * (non-Javadoc)
   * @see java.awt.Container#paint(java.awt.Graphics)
   */

  public void paint(Graphics g) {
    this.setG(g);
    addItem(" || painting -" + status + " speed:" + speed + " Maze:"
        + myMaze.toString());

    myMaze.paintCanvas(g);

    if (MazeConstant.STATUS_INIT == status
        || MazeConstant.STATUS_CREATING == status
        || MazeConstant.STATUS_CREATED_N == status) {
      addItem(" || creating -" + MazeConstant.STATUSDEF[status]);

      if (myMazeCreate.createMaze(g, speed))
        status = MazeConstant.STATUS_CREATED_Y;
      else
        status = MazeConstant.STATUS_CREATED_N;

      addItem(" || created -" + MazeConstant.STATUSDEF[status]);

    }
    else if (MazeConstant.STATUS_CREATED_Y == status) {
      addItem(" || resolving -" + MazeConstant.STATUSDEF[status]);

      myMaze.repaintMaze(g);
      myMazeResolve.resolveMaze(g, speed);

      //status = STATUS_CREATED;
      status = MazeConstant.STATUS_RESOLVED_Y;

      addItem(" || resolved -" + MazeConstant.STATUSDEF[status]);

    }
    else if (MazeConstant.STATUS_RESOLVED_Y == status) {
      addItem(" || resolved -" + MazeConstant.STATUSDEF[status]);

      myMaze.repaintMaze(g);
      myMaze.repaintRoute(g);

      //status = STATUS_CREATED;
      status = MazeConstant.STATUS_RESOLVED_Y;

      addItem(" || resolved -" + MazeConstant.STATUSDEF[status]);

    }

    g.dispose();
  }



  public void start() {
    addItem(" || starting... -" + MazeConstant.STATUSDEF[status]);
  }



  public void stop() {
    addItem(" || stopping... -" + MazeConstant.STATUSDEF[status]);
  }



  public void destroy() {
    addItem(" || unloading... -" + MazeConstant.STATUSDEF[status]);
  }



  private void addItem(String newWord) {
    //System.out.println(newWord);
    //logger.info(newWord);

    paintStatus(this.getG(), newWord);

  }



  /*
   * 
   *  ---------
   * | Maze    |
   * |         |
   *  ---------
   * | Status  |
   * 
   */

  private void paintStatus(Graphics g, String status) {
    if (null == g || !paintLog)
      return;

    int tmpLength = status.length();
    if (tmpLength > 20)
      tmpLength = 20;

    String statusOut = status.substring(0, tmpLength);

    //paint the maze status
    g.setColor(MazeConstant.BACKGROUNDCOLOR);
    g.fillRect(0, myMaze.getStatusY(), myMaze.getStatusWidth(), myMaze
        .getStatusHeight());

    g.setColor(MazeConstant.BOARDCOLOR);
    g.drawRoundRect(0, myMaze.getStatusY(), myMaze.getStatusWidth(), myMaze
        .getStatusHeight(), 0, 0);

    g.setColor(MazeConstant.STATUSCOLOR);
    g.drawString(statusOut, myMaze.getStatusX(), myMaze.getStatusY()
        + myMaze.getCellHeight());

  }



  public Graphics getG() {
    return g;
  }



  public void setG(Graphics g) {
    this.g = g;
  }

}
