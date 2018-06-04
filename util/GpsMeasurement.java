/*jode*/
/* GpsMeasurement - Decompiled by JODE
 * Visit http://jode.sourceforge.net/
 */
package fgafa.util;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class GpsMeasurement implements Serializable
{
    static final long serialVersionUID = 3403946559493939607L;
    private long gpsTime = 0L;
    private double lat = 0.0;
    private double lon = 0.0;
    private double velocity = 0.0;
    private double heading = 0.0;
    private double altitude = 0.0;
    private double hdop = 0.0;
    private double pdop = 0.0;
    private long createTime = 0L;
    private byte reserve0 = 0;
    private byte reserve1 = 0;
    private byte reserve2 = 0;
    private byte reserve3 = 0;
    public transient int index = -1;
    private transient boolean stopFlag = false;
    private transient long lIdleEndTime = 0L;
    private transient long recordingDate = 0L;
    private static final int INIT_VALUE = 0;
    private static final int TRACK_VALUE = 1;
    private static final int EVENT_VALUE = 2;
    private final byte RESERVER1_MASK_GSP_TYPE = 3;
    
    public static final class GpsType
    {
    private int value = 0;
    public static final GpsType INIT = new GpsType(0);
    public static final GpsType TRACK = new GpsType(1);
    public static final GpsType EVENT = new GpsType(2);
    
    private GpsType(int value) {
        this.value = value;
    }
    
    public int getValue() {
        return value;
    }
    }
    
    public GpsType getGpsType() {
    switch (reserve1 & 0x3) {
    case 1:
        return GpsType.TRACK;
    case 2:
        return GpsType.EVENT;
    default:
        return GpsType.INIT;
    }
    }
    
    public void setGpsType(GpsType gpsType) {
    reserve1 = (byte) (reserve1 & ~0x3 | gpsType.getValue() & 0x3);
    }
    
    public int getRoundVelocityInMile() {
    int r = (int) (velocity / 1.609344);
    return r > 3 ? r : 0;
    }
    
    public GpsMeasurement(String aRecordingDate, long gpsTime, double lat,
              double lon, double velocity, double heading,
              double altitude, double hdop, double pdop,
              boolean stopFlag) {
    setRecordDate(aRecordingDate);
    this.gpsTime = gpsTime;
    this.lat = lat;
    this.lon = lon;
    this.velocity = velocity;
    this.heading = heading;
    this.altitude = altitude;
    this.hdop = hdop;
    this.pdop = pdop;
    this.stopFlag = stopFlag;
    }
    
    public GpsMeasurement copy() {
    GpsMeasurement gps = new GpsMeasurement();
    gps.gpsTime = gpsTime;
    gps.lat = lat;
    gps.lon = lon;
    gps.velocity = velocity;
    gps.heading = heading;
    gps.altitude = altitude;
    gps.hdop = hdop;
    gps.pdop = pdop;
    gps.createTime = createTime;
    gps.reserve0 = reserve0;
    gps.reserve1 = reserve1;
    gps.reserve2 = reserve2;
    gps.reserve3 = reserve3;
    gps.recordingDate = recordingDate;
    gps.stopFlag = stopFlag;
    gps.index = index;
    gps.lIdleEndTime = lIdleEndTime;
    return gps;
    }
    
    public GpsMeasurement(String aRecordingDate, long gpsTime, double lat,
              double lon, double velocity, double heading,
              double altitude, double hdop, double pdop,
              boolean stopFlag, int index) {
    setRecordDate(aRecordingDate);
    this.gpsTime = gpsTime;
    this.lat = lat;
    this.lon = lon;
    this.velocity = velocity;
    this.heading = heading;
    this.altitude = altitude;
    this.hdop = hdop;
    this.pdop = pdop;
    this.stopFlag = stopFlag;
    this.index = index;
    }
    
    public GpsMeasurement() {
    /* empty */
    }
    
    public void setRecordDate(String date) {
    SimpleDateFormat simpleDate = new SimpleDateFormat("yyyy/MM/dd");
    Date aDate = null;
    try {
        aDate = simpleDate.parse(date);
    } catch (Exception ex) {
        ex.printStackTrace();
        aDate = new Date();
    }
    recordingDate = aDate.getTime();
    }
    
    public void setRecordDate(long aRecordingDate) {
    recordingDate = aRecordingDate;
    }
    
    public void setGpsTime(long time) {
    gpsTime = time;
    }
    
    public void setIdleEndTime(long time) {
    lIdleEndTime = time;
    }
    
    public long getIdleEndTime() {
    return lIdleEndTime;
    }
    
    public void setLat(double d) {
    lat = (double) (int) (d * 100000.0) / 100000.0;
    }
    
    public void setLon(double d) {
    lon = (double) (int) (d * 100000.0) / 100000.0;
    }
    
    public void setVelocity(double d) {
    velocity = d;
    }
    
    public void setHeading(double d) {
    heading = d;
    }
    
    public void setAltitude(double d) {
    altitude = d;
    }
    
    public void setHdop(double d) {
    hdop = d;
    }
    
    public void setPdop(double d) {
    pdop = d;
    }
    
    public void setStopFlag(boolean flag) {
    stopFlag = flag;
    }
    
    public String getRecordDate() {
    Calendar cal_date = Calendar.getInstance();
    cal_date.setTime(new Date(recordingDate));
    SimpleDateFormat simpleDate = new SimpleDateFormat("yyyy/MM/dd");
    simpleDate.setCalendar(cal_date);
    String strDate = simpleDate.format(cal_date.getTime());
    return strDate;
    }
    
    public long getGpsTime() {
    return gpsTime;
    }
    
    public double getLat() {
    return lat;
    }
    
    public double getLon() {
    return lon;
    }
    
    public double getVelocity() {
    return velocity;
    }
    
    public double getHeading() {
    return heading;
    }
    
    public double getAltitude() {
    return altitude;
    }
    
    public double getHdop() {
    return hdop;
    }
    
    public double getPdop() {
    return pdop;
    }
    
    public boolean isStop() {
    return stopFlag;
    }
    
    public String toString() {
    StringBuffer sb = new StringBuffer();
    sb.append(">>[");
    sb.append(index);
    sb.append("]");
    sb.append(recordingDate);
    sb.append(",");
    sb.append(gpsTime);
    sb.append(",");
    sb.append(createTime);
    sb.append(",(");
    sb.append(lIdleEndTime);
    sb.append("),");
    sb.append(lat);
    sb.append(",");
    sb.append(lon);
    sb.append(",");
    sb.append(altitude);
    sb.append(",");
    sb.append(velocity);
    sb.append(",");
    sb.append(hdop);
    sb.append(",");
    sb.append(heading);
    sb.append(",");
    sb.append(reserve0);
    sb.append(",");
    sb.append(reserve1);
    sb.append(",");
    sb.append(reserve2);
    sb.append(",");
    sb.append(reserve3);
    sb.append(",");
    sb.append(stopFlag);
    sb.append("<<\n");
    return sb.toString();
    }
    
    public long getCreateTime() {
    return createTime;
    }
    
    public void setCreateTime(long createTime) {
    this.createTime = createTime;
    }
    
    public byte getReserve0() {
    return reserve0;
    }
    
    public void setReserve0(byte reserve0) {
    this.reserve0 = reserve0;
    }
    
    public byte getReserve1() {
    return reserve1;
    }
    
    public void setReserve1(byte reserve1) {
    this.reserve1 = reserve1;
    }
    
    public byte getReserve2() {
    return reserve2;
    }
    
    public void setAccuracy(int accuracy) {
    if (accuracy < 0)
        accuracy = 0;
    if (accuracy > 65535)
        accuracy = 65535;
    reserve2 = (byte) (accuracy >> 8 & 0xff);
    reserve3 = (byte) (accuracy & 0xff);
    }
    
    public int getAccuracy() {
    int accuracy = 0;
    accuracy
        = accuracy | (reserve2 >= 0 ? (int) reserve2 : reserve2 + 256);
    accuracy = accuracy << 8 | (reserve3 >= 0 ? (int) reserve3
                    : reserve3 + 256);
    return accuracy;
    }
    
    public void setReserve2(byte reserve2) {
    this.reserve2 = reserve2;
    }
    
    public byte getReserve3() {
    return reserve3;
    }
    
    public void setReserve3(byte reserve3) {
    this.reserve3 = reserve3;
    }
}


/***** DECOMPILATION REPORT *****
    LOCATION: E:\src\telenav\tnt\Unify\Unify1.0\lib\common\measserver_client-1.0.3715.jar!com.telenav.tntplatform.client.measserver.measurement.facade.GpsMeasurement
    TOTAL TIME: 0 ms
 ********************************/