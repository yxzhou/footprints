package fgafa.util;


import java.awt.Point;
import java.awt.Polygon;


/**
 * <b>GraphicsHelper</b> class. <br />
 * <p>
 * </p>
 * 
 * @author yxzhou
 */
public class GraphicsHelper
{
		
	
  public static int IN_SIDE = 1;
  public static int OUT_SIDE = 0;

  public static final double MAP_SIZE = (1<<17)*256;


  /**
   * crossing number test for a point in a polygon.
   * 
   * @param xpoint,ypoint :
   *          a point to test
   * @param double[]
   *          xpoints, double[] ypoints, int npoints : vertex points of a
   *          polygon, clockwise, no requirement of points[n-1]=points[0]
   * @return: 0 = outside, 1 = inside ,  false = outside, true = inside 
   *  if the the point is on a edge of the polygon,  it return false; 
   */
  public static boolean inPolygonCN(long xpoint, long ypoint, long[] xpoints,
      long[] ypoints, int npoints) {

    int crossNumber = 0;

    long[] xpointsTmp = new long[npoints + 1];
    long[] ypointsTmp = new long[npoints + 1];
    for (int i = 0; i < npoints; i++) {
      xpointsTmp[i] = xpoints[i];
      ypointsTmp[i] = ypoints[i];
    }
    xpointsTmp[npoints] = xpoints[0];
    ypointsTmp[npoints] = ypoints[0];

    double vt;
    for (int i = 0; i < npoints; i++) {
      if (((ypointsTmp[i] <= ypoint) && (ypoint < ypointsTmp[i + 1]))
          || ((ypointsTmp[i] > ypoint) && (ypoint >= ypointsTmp[i + 1]))) {
        vt = (double) (ypoint - ypointsTmp[i])
            / (ypointsTmp[i + 1] - ypointsTmp[i]);
        if (xpoint < xpointsTmp[i] + vt * (xpointsTmp[i + 1] - xpointsTmp[i])) {
          // pt.x < intersect, a valid crossing of y = pt.y right of pt.x
          ++crossNumber;
        }
      }
    }


    return (crossNumber & 1) == 1;
  }

  

  /**
   * calculate area of a triangle.
   * 
   * @param Node pos1, Point pos2, Point pos3 :
   *          the three points of the triangle to test
   * @return: area of the triangle
   */
  
private static double triangleArea(Point pos1, Point pos2, Point pos3) {
   double result = Math.abs((pos1.x * pos2.y + pos2.x * pos3.y + pos3.x * pos1.y
           - pos2.x * pos1.y - pos3.x * pos2.y - pos1.x * pos3.y) / 2.0D);
   return result;
}

/**
 * area test for a point in a triangle.
 * 
 * @param Node pos1, Point pos2, Point pos3 :
 *          the three points of the triangle to test
 * @return: false = outside, true = inside 
 *  if the the point is on a edge of the polygon,  it return true; 
 */
public static boolean inTriangle1(Point pos, Point posA, Point posB,
       Point posC) {
   double triangleArea = triangleArea(posA, posB, posC);
   double area = triangleArea(pos, posA, posB);
   area += triangleArea(pos, posA, posC);
   area += triangleArea(pos, posB, posC);
   double epsilon = 0.0001;  
   if (Math.abs(triangleArea - area) < epsilon) {
       return true;
   }
   return false;
}


/**
 * vector product for a point in a triangle.
 * 
 * Define: a=(a1,b1,c1)，b=(a2,b2,c2)，　
 *  a*b=　　| i j k|　|a1 b1 c1|　|a2 b2 c2|　= (b1c2-b2c1,c1a2-a1c2,a1b2-a2b1)
 * 
 * @param Node pos1, Point pos2, Point pos3 :
 *          the three points of the triangle to test
 * @return: false = outside, true = inside 
 *  if the the point is on a edge of the polygon,  it return true; 
 */

  private static double Product(Point A, Point B, Point C) {

    return (B.x - A.x) * (C.y - A.y) - (C.x - A.x) * (B.y - A.y);

  }



  public static boolean intiangle2(Point A, Point B, Point C, Point D) {

    if (Product(A, B, D) >= 0 && Product(A, C, D) >= 0 && Product(C, B, D) >= 0)
      return true;

    return false;

  }

  /**
   * check if a point in a polygon with java.awt.Polygon.
   * 
   * @param xpoint,ypoint :
   *          a point to test
   * @param int[]
   *          xpoints, int[] ypoints, int npoints : vertex points of a polygon,
   *          clockwise, no requirement of points[n-1]=points[0]
   * @return: false = outside, true = inside
   */
  private static boolean inPolygon(double xPoint, double yPoint, int[] xpoints,
      int[] ypoints, int npoints) {
    boolean returnValue = false;
    Polygon polygon = new Polygon(xpoints, ypoints, npoints);

    returnValue = polygon.contains(xPoint, yPoint);
    return returnValue;
  }

  /**
   * get the rate according the lat, lon
   * 
   * 
   * @param lat
   * @param lon
   * @return
   */
   public static double[] getRateFromLatLon(double lat, double lon) {
        double rateX = 1.0 * (lon + 180) / 360;
        double rateY = Math.log(Math.tan((lat + 90) * Math.PI / 360));
        rateY = 0.5-rateY / 2 / Math.PI;
        return new double[] {rateX, rateY};
   }
   
   

  /**
   * 
   * The ellipse points should be:
   * 
                   1
   *      2                  4
   *               3
   * 
   * 
   * @param latDM5
   * @param lonDM5
   * @param latDM5s
   * @param lonDM5s
   * @return
   */
  public static int inEllipse(long latDM5, long lonDM5, long[] latDM5s, long[] lonDM5s){
    
    double basicValue = 100000d;
    
    if(latDM5s.length != 4 || lonDM5s.length != 4){
        return -1;
    }
    
    double testRate[]   = getRateFromLatLon(latDM5/basicValue, lonDM5/basicValue);
    double testX        = ((int)10*testRate[0]*MAP_SIZE)/10;
    double testY        = ((int)10*testRate[1]*MAP_SIZE)/10;
          
    double point1Rate[] = getRateFromLatLon(latDM5s[0]/basicValue, lonDM5s[0]/basicValue);
    //double point1X      = ((int)10*point1Rate[0]*MapUtils.MAP_SIZE)/10;
    double point1Y      = ((int)10*point1Rate[1]*MAP_SIZE)/10;
    
    double point2Rate[] = getRateFromLatLon(latDM5s[1]/basicValue, lonDM5s[1]/basicValue);
    double point2X      = ((int)10*point2Rate[0]*MAP_SIZE)/10;
    //double point2Y      = ((int)10*point2Rate[1]*MapUtils.MAP_SIZE)/10;
          
    double point3Rate[] = getRateFromLatLon(latDM5s[2]/basicValue, lonDM5s[2]/basicValue);
    //double point3X      = ((int)10*point3Rate[0]*MapUtils.MAP_SIZE)/10;
    double point3Y      = ((int)10*point3Rate[1]*MAP_SIZE)/10;
    
    double point4Rate[] = getRateFromLatLon(latDM5s[3]/basicValue, lonDM5s[3]/basicValue);
    double point4X      = ((int)10*point4Rate[0]*MAP_SIZE)/10;
    //double point4Y      = ((int)10*point4Rate[1]*MapUtils.MAP_SIZE)/10;
    
    double centerX      = (point2X + point4X)/2;
    double centerY      = (point1Y + point3Y)/2; 
    testX              -= centerX;
    testY              -= centerY;
    
    int returnValue = OUT_SIDE;
    if (Math.pow(testX,2)/Math.pow((point2X - point4X)/2, 2) + Math.pow(testY, 2)/Math.pow((point1Y - point3Y)/2, 2)<=1){
      returnValue = IN_SIDE;
    }
    return returnValue;
  }


  /**
   * check if a point is in a round.
   * 
   * @param xpoint,ypoint :
   *          a point to test
   * @param double
   *          xcenterPoint, double ycenterPoint : origin of a round
   * @param double
   *          radius : radiu of a round
   * @return: 0 = outside, 1 = inside
   */
  public static int inRound(long latDM5, long lonDM5, long centerLatDM5,
      long centerLonDM5, long radiusDM5) {
    int returnValue = OUT_SIDE;
    double dist=GpsHelper.getDm5Distance(latDM5, lonDM5, centerLatDM5, centerLonDM5);
    if (dist < radiusDM5)
      returnValue = IN_SIDE; // found the one inside
    
    return returnValue;
  }

  public static void main(String[] args) {
  /*long xpoint = 25;// 1;
    long ypoint = 13;// 2;

    int[] xpoints = {10, 20, 20, 30, 20};
    int[] ypoints = {10, 20, 10, 10, 0};
    long[] xpointsd = {10, 20, 20, 30, 20};
    long[] ypointsd = {10, 20, 10, 10, 0};
    int npoints = 5;

    System.out
        .println(inPolygonCN(xpoint, ypoint, xpointsd, ypointsd, npoints));

    System.out.println(inPolygon(xpoint, ypoint, xpoints, ypoints, npoints));

    long time0, time1, time2;
    time0 = System.currentTimeMillis();
    System.out.println(time0);

    for (int i = 0; i < 10000; i++) {
      xpoint = (long)Math.random() * 100;
      ypoint = (long)Math.random() * 100;

      inPolygon(xpoint, ypoint, xpoints, ypoints, npoints);

    }

    time1 = System.currentTimeMillis();
    System.out.println(time1);
    System.out.println(time1 - time0);

    for (int i = 0; i < 10000; i++) {
      xpoint = (long)Math.random() * 100;
      ypoint = (long)Math.random() * 100;

      inPolygonCN(xpoint, ypoint, xpointsd, ypointsd, npoints);

    }
    time2 = System.currentTimeMillis();
    System.out.println(time2);
    System.out.println(time2 - time1);*/

    /*
     * double[] xpoints = {20,20,10,10}; double[] ypoints = {30,10,10,30};
     * System.out.println(InEllipse(xpoint,ypoint,xpoints,ypoints));
     */ 
      
      long[] lat = {3778274, 3777706, 3777137, 3777706};
      long[] lon = {-12251099, -12252096,-12251099, -12250102};
      
      System.out.println(inEllipse(3777706, -12251099, lat, lon));
  }



}

