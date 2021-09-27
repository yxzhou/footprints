package uva.geometry.OrchardTreesN143;

import java.io.*;
import java.util.*;

class Main
{
  static final double EPS = 1e-9d;





  /**
   * calculate area of a triangle.
   * 
   * @param Point pos1, pos2, pos3: the three points of the triangle to test
   * @return: area of the triangle
   */

  private static double triangleArea(double x1, double y1, double x2, double y2,
          double x3, double y3) {
    return Math
        .abs((x1 * y2 + x2 * y3 + x3 * y1 - x1 * y3 - x2 * y1 - x3 * y2) / 2.0D);

  }



  /**
   * area test for a point in a triangle.
   * 
   * @param Point pos1, pos2, pos3: the three points of the triangle to test
   * @return: area of the triangle
   */
  // public static boolean inTriangle(Point pos, Point posA, Point posB, Point
  // posC) {
  public static boolean inTriangle1(int xpoint, int ypoint, double[] xpoints,
      double[] ypoints) {

    double triangleArea = triangleArea(xpoints[0], ypoints[0], xpoints[1],
        ypoints[1], xpoints[2], ypoints[2]);
    double area = triangleArea(xpoint, ypoint, xpoints[0], ypoints[0],
        xpoints[1], ypoints[1]);
    area += triangleArea(xpoint, ypoint, xpoints[1], ypoints[1], xpoints[2],
        ypoints[2]);
    area += triangleArea(xpoint, ypoint, xpoints[2], ypoints[2], xpoints[0],
        ypoints[0]);

    if (Math.abs(triangleArea - area) < EPS) {
      return true;
    }
    return false;
  }



  /**
   * crossing number test for a point in a polygon. it's similar with "vector cross product"
   * 
   * @param xpoint
   *          ,ypoint : a point to test
   * @param double[] xpoints, double[] ypoints, int npoints : vertex points of a
   *        polygon, clockwise, a special requirement of points[n-1]=points[0]
   * @return: 0 = outside, 1 = inside, false = outside, true = inside
   */
  public static boolean inPolygonCN(int xpoint, int ypoint, float[] xpoints,
      float[] ypoints, int npoints) {
       
    int crossNumber = 0;
    double vt;

    for (int i = 0; i < npoints; i++) {
      if (((ypoints[i] <= ypoint) && (ypoint < ypoints[i + 1]))
          || ((ypoints[i] > ypoint) && (ypoint >= ypoints[i + 1]))) {
        vt = (double) (ypoint - ypoints[i]) / (ypoints[i + 1] - ypoints[i]);
        if (xpoint < xpoints[i] + vt * (xpoints[i + 1] - xpoints[i])) {
          // pt.x < intersect, a valid crossing of y = pt.y right of pt.x
          ++crossNumber;
        }
      }
    }

    return (crossNumber & 1) == 1;
  }

  
  
  /**
   * vector cross product for a point in a triangle.<br> 
   * 
   * In fact, you can use just the cross products to check whether a point is inside a polygon.
   * If ABC is oriented counter-clockwise, then point P is inside ABC if PA x PB > 0, PB x PC > 0 and PC x PA > 0. 
   * (the cross products here are defined by the expression above.) 
   * Define: 
   * Vector PA = (Ax-Px)i + (Ay-Py)j = [Ax-Px, Ay-Py] 
   * Vector PB = (Bx-Px)i + (By-Py)j = [Bx-Px, By-Py] 
   * 
   * PA x PB=　((Ax-Px)*(By-Py)-(Bx-Px)*(Ay-Py))
   * 
   * @param Point pos1, pos2, pos3: the three points of the triangle to test
   * @return: false = outside, true = inside if the the point is on a edge of
   *          the polygon, it return true;
   */

  // private static double Product(Point A, Point B, Point P) {
  private static double Product(double ax, double ay,
          double bx, double by, double px, double py) {

    //return (A.x - P.x) * (B.y - P.y) - (B.x - P.x) * (A.y - P.y);
    return (ax - px) * (by - py) - (bx - px) * (ay - py);

  }


  // public static boolean intiangle2(Point A, Point B, Point C, Point D) {
  public static boolean inTriangle2(int xpoint, int ypoint, double[] xpoints,
          double[] ypoints, int n) {

//    if(ypoint == 5){  
//        System.out.println("\n"+xpoint + " == " + ypoint);
//        System.out.println(Product(xpoints[0], ypoints[0], xpoints[1], ypoints[1], xpoint, ypoint));  
//        System.out.println(Product(xpoints[1], ypoints[1], xpoints[2], ypoints[2], xpoint, ypoint));
//        System.out.println(Product(xpoints[2], ypoints[2], xpoints[0], ypoints[0], xpoint, ypoint));
//    }
    
    //if (Product(A, B, D) >= 0 && Product(A, C, D) >= 0 && Product(C, B, D) >= 0)
  //if (Product(C, B, D) >= 0 && Product(B, A, D) >= 0 && Product(A, C, D) >= 0)
    if (Product(xpoints[0], ypoints[0], xpoints[1], ypoints[1], xpoint, ypoint) > 0-EPS
        && Product(xpoints[1], ypoints[1], xpoints[2], ypoints[2], xpoint, ypoint) > 0-EPS
        && Product(xpoints[2], ypoints[2], xpoints[0], ypoints[0], xpoint, ypoint) > 0-EPS)
      return true;

    return false;

  }

  
  //
  private static void makeClockwise(double[] xpoints,  double[] ypoints){
    if(Product(xpoints[0], ypoints[0], xpoints[1], ypoints[1], xpoints[2], ypoints[2]) <= 0-EPS)
      swap(xpoints, ypoints, 0, 2);
    
  }

  private static void swap(double[] xpoints, int i, int j) {
    double tmp = xpoints[i];
    xpoints[i] = xpoints[j];
    xpoints[j] = tmp;
  }



  private static void swap(double[] xpoints, double[] ypoints, int i, int j) {

    swap(xpoints, i, j);
    swap(ypoints, i, j);

  }

  
  
  public static void main(String[] args) throws Exception {

    Scanner in = new Scanner(new BufferedInputStream(System.in), "UTF-8");
    int n = 3;
    double[] xpoints = new double[n+1];
    double[] ypoints = new double[n+1];

    try {
      while (in.hasNext()) {
        boolean isExit = true;
        int xMax = 0, xMin = 100, yMax = 0, yMin = 100;

        // read
        for (int i = 0; i < n; i++) {
          xpoints[i] = in.nextFloat();
          ypoints[i] = in.nextFloat();

          xMax = Math.max(xMax, (int) Math.floor(xpoints[i]));
          xMin = Math.min(xMin, (int) Math.ceil(xpoints[i]));
          yMax = Math.max(yMax, (int) Math.floor(ypoints[i]));
          yMin = Math.min(yMin, (int) Math.ceil(ypoints[i]));
                    

          isExit = isExit && (xpoints[i] < EPS) && (ypoints[i] < EPS );

        }

        xMax = Math.min(xMax, 99);
        xMin = Math.max(xMin, 1);
        yMax = Math.min(yMax, 99);
        yMin = Math.max(yMin, 1);
        
        if (isExit)
          return;

        //make it clockwise  
        makeClockwise(xpoints, ypoints);  
        xpoints[n] = xpoints[0];
        ypoints[n] = ypoints[0];
        
        //System.out.println("\n"+xpoints[0]+" "+ ypoints[0]+" "+xpoints[1]+" "+ypoints[1]+" "+xpoints[2]+" "+ypoints[2]);
        
        // main and output        
        int count = 0;
        for (int x = xMin; x <= xMax; x++) {
          for (int y = yMin; y <= yMax; y++) {
            // if(inPolygonCN(x, y, xpoints, ypoints, n))
            if (inTriangle1(x, y, xpoints, ypoints))    // accurate issue EPS = 1e-5f;
            //if (inTriangle2(x, y, xpoints, ypoints, n))
              count++;

          }
        }

        System.out.printf("%4d%n", count);
      }
    }
    catch (Exception e) {
      // e.printStackTrace();
    }
    finally {
      in.close();
    }

  }

}
