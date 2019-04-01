package fgafa.util;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import java.util.Locale;
import java.util.SimpleTimeZone;
import java.util.TimeZone;

public class DateTimeHelper
{
  private static final DecimalFormat dfMin = (DecimalFormat) DecimalFormat
      .getNumberInstance();

  /**
   * get the timestamp for today at 23:59:59.999
   */
  public static final long ONE_SECOND = 1000L;
  public static final long ONE_MINUTE = 60000L;
  public static final long ONE_HOUR = 3600000L;
  public static final long ONE_DAY = 86400000L;

  // i18n format
  // Do NOT change following format !!!
  public static final String MILITARY_TIME_FORMAT = "HH:mm zzz";
  public static final String DECIMAL_TIME_FORMAT = "hh:mma zzz";
  public static final String LONG_DATE_FORMAT_S24 = "MM/dd/yyyy HH:mm";
  public static final String LONG_DATE_FORMAT_SZ = "MM/dd/yyyy hh:mm aaa zzz";
  public static final String LONG_DATE_FORMAT_24 = "MM/dd/yyyy HH:mm zzz";
  public static final String LONG_DATE_FORMAT_12 = "MM/dd/yyyy hh:mma zzz";
  public static final String YYYY_MON_DAY = "yyyy-MM-dd";
  public static final String YYYY_MON_DAY_HOU_MIN = "yyyy-MM-dd HH:mm";
  public static final String YYYY_MON_DAY_HOU_MIN_SS_SSS = "yyyy-MM-dd HH:mm:ss SSS";
  public static final String MON_DAY_YYYY_HH_MM_BRACE = "MMM dd yyyy-hh:mm a";
  public static final String YYYY_MON_DAY_HOU_MIN_BRACE = "yyyy-MM-dd hh:mm a";
  public static final String YYYY_MON_DAY_HOU_MIN_SS_SSS_BRACE = "yyyy-MM-dd hh:mm:ss SSS a";
  public static final String YYYY_MON_DAY_HOU_MIN_Z = "yyyy-MM-dd HH:mm z";

  // GMT time zone id
  public static final String GMT_ID = "GMT";

  private static SimpleDateFormat sdf;
  
  static {
    dfMin.applyPattern("##0.00");
    sdf = new SimpleDateFormat("MM/dd/yyyy hh:mm a", Locale.US);
  }



  /**
   * Get time string
   */
  public static String formatTime(long timeStamp, TimeZone timeZone,
      boolean isDecimalFormat) {
    if (isDecimalFormat)
      return dateTime(timeStamp, DECIMAL_TIME_FORMAT, timeZone);
    else
      return dateTime(timeStamp, MILITARY_TIME_FORMAT, timeZone);
  }





  public static String formatLongTime(long timeStamp, TimeZone timeZone,
      boolean isDecimalFormat) {
    if (isDecimalFormat)
      return dateTime(timeStamp, LONG_DATE_FORMAT_12, timeZone);
    else
      return dateTime(timeStamp, LONG_DATE_FORMAT_24, timeZone);
  }



  public static String formatMinutes(int minutes, boolean isDecimalFormat) {
    String str = "";
    if (!isDecimalFormat) {
      if (minutes < 60)
        str = "00:";
      else {
        int hour = minutes / 60;
        if (hour < 10)
          str = "0";
        str += hour + ":";
      }
      int mimutes = minutes % 60;
      if (mimutes < 10)
        str += "0";
      str += mimutes;
    }
    else {
      str = formatMinutes(minutes) + " hours";
    }
    return str;
  }



  public static String dateTime(long timestamp, String dateFormat,
      TimeZone timeZone) {

    Date new_dt = new Date(timestamp);

    String strDate;

    Calendar cal_date = Calendar.getInstance();

    cal_date.setTime(new_dt);

    SimpleDateFormat dt_format1 = new SimpleDateFormat(dateFormat, Locale.US);

    dt_format1.setCalendar(cal_date);

    dt_format1.setTimeZone(timeZone);

    strDate = dt_format1.format(cal_date.getTime());

    return strDate;
  }




  public static long getTimeStamp(String date, String format) {
    SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.US);
    Date d = null;
    try {
      d = sdf.parse(date);
    }
    catch (ParseException e) {
    }
    if (d == null)
      return 0;
    return d.getTime();
  }



  /*
   * Returns true if the SplitDurationByMonth date is one of the following US holidays:
   * New Years Day, Martin Luther King Jr Day, Presidents Day, Memorial Day,
   * Independence Day, Labor Day, Columbus Day, Veterans Day, Thanksgiving
   * Day,
   */
  public static boolean isHoliday(Calendar currDate) {
    boolean isHoly = false;
    int currMonth = currDate.get(Calendar.MONTH);
    int currDay = currDate.get(Calendar.DAY_OF_MONTH);
    int currDayWeek = currDate.get(Calendar.DAY_OF_WEEK);
    int currWeekMonth = currDate.get(Calendar.WEEK_OF_MONTH);

    switch (currMonth) {
      case (Calendar.JANUARY):
        if ((currDay == 1) // New Years Day
            || ((currDayWeek == Calendar.MONDAY) && (currWeekMonth == 3))// MLK
        )
          isHoly = true;
        break;
      case (Calendar.FEBRUARY):
        if ((currDayWeek == Calendar.MONDAY) && (currWeekMonth == 3))// Presidents
          isHoly = true;
        break;
      case (Calendar.MAY):
        if ((currDayWeek == Calendar.MONDAY)
            && ((currWeekMonth == currDate.getMaximum(Calendar.WEEK_OF_MONTH)) || (currDay == 25)))
          isHoly = true;
        break;
      case (Calendar.JULY):
        if (currDay == 4) // Independence Day
          isHoly = true;
        break;
      case (Calendar.SEPTEMBER):
        if ((currDayWeek == Calendar.MONDAY) && (currWeekMonth == 1))// Labor
          // Day
          isHoly = true;
        break;
      case (Calendar.OCTOBER):
        if ((currDayWeek == Calendar.MONDAY) && (currWeekMonth == 2))// Columbus
          isHoly = true;
        break;
      case (Calendar.NOVEMBER):
        if ((currDay == 11) // Veterans Day
            || ((currDayWeek == Calendar.THURSDAY) && (currWeekMonth == 4))// Thanksgiving
            || ((currDayWeek == Calendar.FRIDAY) && (currWeekMonth == 4)))
          isHoly = true;
        break;

      case (Calendar.DECEMBER):
        if (currDay == 25) // Christmas
          isHoly = true;
        break;
      default :
        break;
    }

    return isHoly;
  }



  /**
   * deparse string to long Example: deparse '03:05 am' to '185'
   * 
   */
  public static long deparseHourMin(String sHour, String sMin, String sType) {
    return deparseHourMin(Long.parseLong(sHour), Long.parseLong(sMin), sType);
  }



  public static long deparseHourMin(long lHour, long lMin, String sType) {
    long lReturn = -1L;
    if (sType.equalsIgnoreCase("AM")) {
      if (lHour == 12)
        lHour = 0;
    }
    else {
      if (lHour != 12)
        lHour = lHour + 12;
    }
    lReturn = lHour * 60 + lMin;
    return lReturn;
  }



  public static long getHourMinStamp(String sHour, String sMin, String sType) {
    return deparseHourMin(Long.parseLong(sHour), Long.parseLong(sMin), sType)
        * ONE_MINUTE;
  }



  /*
   * parse long to string, example: parse '185' to '03:05 am'
   * 
   */
  public static String parseHourMin(String strTime) {
    String strReturn = "";
    if (isDigit(strTime)) {
      String[] times = paresHourMin(strTime);
      strReturn = times[0] + ":" + times[1] + " " + times[2];
    }
    return strReturn;
  }



  public static String[] paresHourMin(String strTime) {

    String[] times = new String[3];
    if (isDigit(strTime)) {
      long lHourMin = Long.parseLong(strTime);
      long lHour = lHourMin / 60;
      long lMin = lHourMin % 60;

      String sPostfix = "";
      String sHour = "";
      String sMin = "";
      if (lHour < 12) {
        sPostfix = "AM";

        if (lHour < 10)
          sHour = "0" + String.valueOf(lHour);
        else
          sHour = String.valueOf(lHour);

        if (lHour == 0)
          sHour = "12";

      }
      else {
        sPostfix = "PM";
        lHour = lHour - 12;

        if (lHour < 10)
          sHour = "0" + String.valueOf(lHour);
        else
          sHour = String.valueOf(lHour);

        if (lHour == 0)
          sHour = "12";
      }
      if (lMin < 10)
        sMin = "0" + String.valueOf(lMin);
      else
        sMin = String.valueOf(lMin);

      times[0] = sHour;
      times[1] = sMin;
      times[2] = sPostfix;

    }
    return times;
  }



  private static boolean isDigit(String str) {
    boolean digits = true;
    if (str == null || str.equals(""))
      digits = false;

    for (int i = 0; i < str.length(); i++)
      if (!Character.isDigit(str.charAt(i))) {
        digits = false;
        break;
      }
    return digits;
  }



  public static long convertMillis2Minutess(long millis) {
    long minutes = millis / 60000; // 60000 = 60*1000
    return minutes;
  }



  public static long getInterval2CurrentTime(long locationTime)
      throws Exception {
    return convertMillis2Minutess(System.currentTimeMillis() - locationTime);
  }



  /**
   * minute desc --d--h--min
   */
  public static String getMinuteDesc(long minutes) {
    if (minutes <= 0)
      return "now";
    long days = minutes / (24 * 60);
    long leftMinutes1 = minutes % (24 * 60);
    long hours = leftMinutes1 / 60;
    long leftMinutes2 = leftMinutes1 % 60;
    String ret = "";
    if (days > 0)
      ret += days + " d ";
    if (hours > 0)
      ret += hours + " hr ";
    if (leftMinutes2 > 0)
      ret += leftMinutes2 + " min ";
    ret += "ago";
    return ret;
  }



  public static String formatMinutes(long millSeconds, boolean isDecimalFormat) {
    if (millSeconds < 0)
      millSeconds = 0;
    int minutes = (int) (millSeconds / GpsHelper.ONE_MINUTE);
    return isDecimalFormat ? formatMinutes(minutes) : formatMinutes(minutes,
        isDecimalFormat);
  }



  public static String formatMinutesSS(long millSeconds, boolean isDecimalFormat) {
    if (millSeconds < 0)
      millSeconds = 0;
    int minutes = (int) (millSeconds / GpsHelper.ONE_MINUTE);
    if (isDecimalFormat) {
      return formatMinutes(minutes);
    }
    else {
      String timeStr = formatMinutes(minutes, isDecimalFormat);
      timeStr += ":";
      int seconds = (int) (millSeconds / 1000);
      seconds = seconds % 60;
      if (seconds < 10)
        timeStr += "0";
      timeStr += seconds;
      return timeStr;
    }
  }



  public static String formatMinutes(int minutes) {
    double _minutes = minutes;
    return dfMin.format(_minutes / 60);
  }



  public static int roundMinutes(long timeStamp, int roundMode) {
    BigDecimal tempDec = new BigDecimal(timeStamp);
    BigDecimal min = new BigDecimal(GpsHelper.ONE_MINUTE);
    BigDecimal temp = new BigDecimal(tempDec.doubleValue() / min.doubleValue());
    return temp.setScale(0, roundMode).intValue();
  }



  // calculate day milliseconds left
  public static long isToday(long startTime, long endTime, TimeZone zone) {
    Calendar cNow = Calendar.getInstance(zone);
    cNow.setTime(new Date());
    Calendar cTime = Calendar.getInstance(zone);
    cTime.setTime(new Date(startTime));
    if (cTime.get(Calendar.YEAR) == cNow.get(Calendar.YEAR)
        && cTime.get(Calendar.MONTH) == cNow.get(Calendar.MONTH)
        && cTime.get(Calendar.DATE) == cNow.get(Calendar.DATE)) {
      long now = cNow.getTime().getTime();

      if (now > startTime) {
        if (now < endTime) {
          return now - startTime;
        }
        else {
          return endTime - startTime;
        }
      }
      else {
        return 0;
      }
    }
    return endTime - startTime;
  }



  public static String getDuration(long duration) {
    StringBuffer buf = new StringBuffer();
    int hours = (int) (duration / ONE_HOUR);
    int minutes = (int) ((duration % ONE_HOUR) / ONE_MINUTE);
    buf.append(hours);
    buf.append(" hours ");
    buf.append(minutes);
    buf.append(" minutes ");
    return buf.toString();
  }



  public static byte getDayType(TimeZone timeZone, long time) {
    byte ret = -1;
    Calendar cal = Calendar.getInstance(timeZone);
    cal.setTime(new Date(time));
    int day = cal.get(Calendar.DAY_OF_WEEK);
    switch (day) {
      case Calendar.SATURDAY:
        ret = 0;
        break;
      case Calendar.SUNDAY:
        ret = 1;
        break;
    }
    return ret;
  }



  public static String formatDate(Date date, String pattern) {
    try {
      SimpleDateFormat format = new SimpleDateFormat(pattern);
      return format.format(date);
    }
    catch (Exception e) {
      return "";
    }
  }



  /**
   * Get GMT/UTC time zone.
   * 
   * @return GMT/UTC time zone
   */
  public static TimeZone getTimeZoneUtc() {
    return TimeZone.getTimeZone(GMT_ID);
  }



  /**
   * Get local time zone.
   * 
   * @return local time zone
   */
  public static TimeZone getTimeZoneLocal() {
    return TimeZone.getDefault();
  }



  /**
   * Get local time zone offset to UTC time which is the raw offset.
   * 
   * @return local time zone offset to UTC time
   * @see #getTimeZoneOffset(java.util.TimeZone, java.util.TimeZone)
   */
  public static int getLocalTimeZoneOffset() {
    return getTimeZoneOffset(getTimeZoneLocal(), getTimeZoneUtc());
  }



  /**
   * Get the assigned time zone offset to UTC time which is the raw offset.
   * 
   * @param aTimeZone
   *          the assigned time zone
   * @return the assigned time zone offset to UTC time
   * @see #getTimeZoneOffset(java.util.TimeZone, java.util.TimeZone)
   */
  public static int getTimeZoneOffsetToUtc(TimeZone aTimeZone) {
    return getTimeZoneOffset(aTimeZone, getTimeZoneUtc());
  }



  /**
   * Get the assigned time zone offset to local time, which is the sum of raw
   * offset and daylight saving value of the time zone.
   * 
   * @param aTimeZone
   *          the assigned time zone
   * @return the assigned time zone offset to local time
   * @see #getTimeZoneOffset(java.util.TimeZone, java.util.TimeZone)
   */
  public static int getTimeZoneOffsetToLocal(TimeZone aTimeZone) {
    return getTimeZoneOffset(aTimeZone, TimeZone.getDefault());
  }



  /**
   * Get offset between 2 time zones.
   * 
   * @param fromTimeZone
   * @param toTimeZone
   * @return offset between 2 time zone
   */
  public static int getTimeZoneOffset(TimeZone fromTimeZone, TimeZone toTimeZone) {
    if (fromTimeZone == null || toTimeZone == null)
      return 0;
    if (fromTimeZone.equals(toTimeZone))
      return 0;
    return fromTimeZone.getRawOffset() - toTimeZone.getRawOffset();
  }




  /**
   * Get daylight saving time.
   * 
   * @param date
   * @param timeZone
   * @return
   */
  public static int getDstSavings(Date date, TimeZone timeZone) {
    if (date == null || timeZone == null || !timeZone.useDaylightTime()
        || !(timeZone instanceof SimpleTimeZone))
      return 0;
    Calendar calender = Calendar.getInstance(timeZone);
    calender.setTime(date);
    return calender.get(Calendar.DST_OFFSET);
  }



  public static long toDayStart(long time, TimeZone timeZone) {
    Calendar cld = Calendar.getInstance(timeZone);
    cld.setTimeInMillis(time);
    cld.set(Calendar.HOUR_OF_DAY, cld.getActualMinimum(Calendar.HOUR_OF_DAY));
    cld.set(Calendar.MINUTE, cld.getActualMinimum(Calendar.MINUTE));
    cld.set(Calendar.SECOND, cld.getActualMinimum(Calendar.SECOND));
    cld.set(Calendar.MILLISECOND, cld.getActualMinimum(Calendar.MILLISECOND));
    return cld.getTimeInMillis();
  }



  public static long toDayEnd(long time, TimeZone timeZone) {
    Calendar cld = Calendar.getInstance(timeZone);
    cld.setTimeInMillis(time);
    cld.set(Calendar.HOUR_OF_DAY, cld.getActualMaximum(Calendar.HOUR_OF_DAY));
    cld.set(Calendar.MINUTE, cld.getActualMaximum(Calendar.MINUTE));
    cld.set(Calendar.SECOND, cld.getActualMaximum(Calendar.SECOND));
    cld.set(Calendar.MILLISECOND, cld.getActualMaximum(Calendar.MILLISECOND));
    return cld.getTimeInMillis();
  }



  public static boolean isSameDay(long milliseconds1, long milliseconds2,
      TimeZone timeZone) {
    Calendar cld1 = Calendar.getInstance(timeZone);
    cld1.setTimeInMillis(milliseconds1);
    Calendar cld2 = Calendar.getInstance(timeZone);
    cld2.setTimeInMillis(milliseconds2);

    return cld1.get(Calendar.YEAR) == cld2.get(Calendar.YEAR)
        && cld1.get(Calendar.DAY_OF_YEAR) == cld2.get(Calendar.DAY_OF_YEAR);
  }



  public static void main(String[] args){
    
    long timestamp = System.currentTimeMillis();
    TimeZone timeZone = TimeZone.getTimeZone("Navajo");   //Arizona Time/Phoenix
    String dateFormat = LONG_DATE_FORMAT_12;
    
    String dateTime = dateTime(timestamp, dateFormat, timeZone);
    
    System.out.println("----dateTime: "+ dateTime);
    
  }


}
