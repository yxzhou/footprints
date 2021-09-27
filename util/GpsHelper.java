package util;


import java.util.Comparator;

import util.GpsMeasurement;



/**
 * @version 1.0
 * @author
 */
public class GpsHelper
{
  public static final double SCALING_CONSTANT = 1.113194908; // DM5 to meters
  public static final double DEGREE_MULTIPLIER = 1.0e5; // degree to DM5
  private static final double DEGREE_TO_DM6 = 1.0e6;
  public static final double MILE_TO_METER = 1609.344;

  public static final double MILES_TO_KM = 1.609344;
  public static final long ONE_MINUTE = 1000 * 60;
  public static final double mileageRatio = 1.02;
  public static final long ONE_HOUR = 1000 * 60 * 60;
  public static final long ONE_DAY = 24 * ONE_HOUR;

  public static final double DM5_TO_METER = 1.113194908;
  
  public static final double DM5_TO_FEET = 3.65221426509185977719296;
  
  public static final int FEET_TO_MILE = 5280;
  
  
  public static final int DEFAULT_STOP_DISTANCE=200;

  
  public static double dm5ToDegree(long dm5) {
    return dm5 / DEGREE_MULTIPLIER;
  }



  public static long degreeToDM5(double degree) {
    return (long) (degree * DEGREE_MULTIPLIER);
  }
  public static long degreeToDM6(double degree) {
        return (long) (degree * DEGREE_TO_DM6);
      }


  public static double dm5ToMeters(long dm5) {
    return dm5 * SCALING_CONSTANT;
  }



  public static long metersToDM5(double m) {
    return (long) (m / SCALING_CONSTANT);
  }



  public static long milesToDM5(double miles) {
    return (long) metersToDM5(miles * MILE_TO_METER);
  }



  public static double dm5ToMiles(long dm5) {
    return dm5ToMeters(dm5) / MILE_TO_METER;
  }



  public static String toHeadingString(String heading) {
    return toHeadingString((int) Double.parseDouble(heading));
  }



  public static String toHeadingString(double heading) {
    return toHeadingString((int) heading);
  }



  public static String toHeadingString(int heading) {
    String s = "N";
    if(heading < 0){
      s = "N/A";
    }else{
      int index = ((2 * heading + 45) % 720) / 90;
      switch (index) {
        case 0: {
          s = "N";
          break;
        }
        case 1: {
          s = "NE";
          break;
        }
        case 2: {
          s = "E";
          break;
        }
        case 3: {
          s = "SE";
          break;
        }
        case 4: {
          s = "S";
          break;
        }
        case 5: {
          s = "SW";
          break;
        }
        case 6: {
          s = "W";
          break;
        }
        case 7: {
          s = "NW";
          break;
        }
      }
    }
    return s;
  }

  public static long[] degreeToDM5(double[] ds) {
    if (ds == null)
      return null;
    long[] dm5 = new long[ds.length];
    for (int i = 0; i < ds.length; i++) {
      dm5[i] = GpsHelper.degreeToDM5(ds[i]);
    }
    return dm5;
  }

 
  public static double convertKmToMph(double kmSpeed, int length) {
    return NumberHelper.formatDouble(kmSpeed /GpsHelper.MILES_TO_KM, length);
  }



  public static double convertMphToKm(double mphSpeed, int length) {
    return NumberHelper.formatDouble(mphSpeed * MILES_TO_KM, length);

  }
  
 public static class GpsComparator implements Comparator{
    public int compare(Object a, Object b) {
      int result=0;
      GpsMeasurement gps1=(GpsMeasurement)a;
      GpsMeasurement gps2=(GpsMeasurement)b;
      long differ = gps1.getGpsTime() - gps2.getGpsTime();
      if (differ > 0)
        result = 1;
      else if (differ == 0)
        result = 0;
      else
        result = -1;
      return result;
    }
  }
  

  
  /**
   * Get distance bewteen two gps points. return the meters.
   * @param fromLat
   * @param fromLon
   * @param toLat
   * @param toLon
   * @return
   */
  
  public static double getMeterDistance(double fromLat,double fromLon,double toLat,double toLon) 
  {
    long dLat = (long) Math.abs((fromLat - toLat) * 100000);
    long dLon = (long) Math.abs((fromLon - toLon) * 100000);

    int alpha = (int) fromLat;
    long dLonC = DataUtil.xCosY(dLon, alpha);
    long delta = DataUtil.rss(dLat, dLonC);
    
    return delta* DM5_TO_METER;           
  }
  
  /**
   * Get distance bewteen two gps points. return the feets.
   * @param fromLat
   * @param fromLon
   * @param toLat
   * @param toLon
   * @return double
   * @author trsun
   */
  
  public static double getFeetDistance(double fromLat,double fromLon,double toLat,double toLon) 
  {
    long dLat = (long) Math.abs((fromLat - toLat) * 100000);
    long dLon = (long) Math.abs((fromLon - toLon) * 100000);

    int alpha = (int) fromLat;
    long dLonC = DataUtil.xCosY(dLon, alpha);
    long delta = DataUtil.rss(dLat, dLonC);
    
    return delta* DM5_TO_FEET;        
  }

  /**
   * Get distance bewteen two gps points. return the DM5 unit length.
   * @param fromLat
   * @param fromLon
   * @param toLat
   * @param toLon
   * @return
   */
//  public static double getDm5Distance(double fromLat,double fromLon,double toLat,double toLon) {
//
//
//    long dLat = (long) Math.abs((fromLat - toLat) * 100000);
//    long dLon = (long) Math.abs((fromLon - toLon) * 100000);
//
//    int alpha = (int) fromLat;
//    long dLonC = DataUtil.xCosY(dLon, alpha);
//    double delta = DataUtil.rss(dLat, dLonC);
//    
//    return delta;  //return DM5 unit length.
//}
  
  public static long getDm5Distance(long latDM5, long lonDM5, long prevLatDM5, long prevLonDM5) {
        long dLat = Math.abs(latDM5 - prevLatDM5);
        long dLon = Math.abs(lonDM5 - prevLonDM5);

        int alpha = (int) (latDM5 / 100000L);
        long dLonC = DataUtil.xCosY(dLon, alpha);
        long delta = DataUtil.rss(dLat, dLonC);

        return delta;
      }



/**
 * @param width
 * @return
 */
public static double meterToMile(double width) {
    return width/MILE_TO_METER;
}
  

    /**
     * convert feet to mile
     * @param distance
     * @return double
     * @author trsun
     */
    public static double feetToMile(double distance) {
        return distance/FEET_TO_MILE;
    }

  
}