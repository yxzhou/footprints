/**
 * @file BaseJob.java
 * @author Zhou Yuanxi
 * @date Feb. 16, 2004 Copyright (c) 2004 TeleVigation, Inc
 */
package basic.socket.ecax.job;

import java.util.StringTokenizer;
import java.util.Vector;

import org.apache.log4j.spi.ThrowableInformation;

import com.televigation.db.telenavpro.AlertManager;
import com.televigation.db.telenavpro.TnpBaseManager;
import com.televigation.db.telenavpro.TrackableManager;
import com.televigation.db.telenavpro.datatypes.TnpTrackable;
import com.televigation.log.TVCategory;
import basic.socket.ecax.EcaMsg;
import basic.socket.ecax.EcaString;
import com.televigation.telenavpro.servlet.TeleNavProStrings;
import com.televigation.util.Configurable;

public class BaseJob implements EcaJob
{

  public static TVCategory log = (TVCategory) TVCategory
      .getInstance("com.televigation.telenavpro.ecax.BaseJob");

  private static Configurable app_config = new Configurable("mypna.cfg");

  public static String MAILSERVER = app_config.getProperty("mail_server");
  public static String MAILSENDER = app_config
      .getProperty("mail_tnt_tech_support");
  public static String MAILSUPORT = app_config
      .getProperty("mail_tnt_tech_support");
  public static String SERVERNAME = app_config.getProperty("sc_servername");
  public static String ENABLESTATUSCHECKING = app_config
      .getProperty("sc_enable");

  public EcaMsg ecaMsg = null;



  public BaseJob() {
  }



  /**
   * @return Returns the ecaMsg.
   */
  public EcaMsg getEcaMsg() {
    return ecaMsg;
  }



  public void process() throws Exception {
  }



  /**
   * if strParam is null, then return ""�? else return itself.
   * 
   * @param strParam
   * @return
   */
  private static String toValue(String strParam) {
    if (strParam == null) {
      return "";
    }
    else {
      return strParam;
    }
  }



  public static String getSubject(int iEcaTypeId, String subjectDefinedByUser) {
    StringBuffer strReturn = new StringBuffer("");

    switch (iEcaTypeId) {
      case TeleNavProStrings.ECA_CLODKIN_KEY:
        strReturn.append(EcaString.CLOCKIN_SUBJECT);
        break;
      case TeleNavProStrings.ECA_APPSTART_KEY:
        strReturn.append(EcaString.APPSTART_SUBJECT);
        break;
      case TeleNavProStrings.ECA_NOCOMM_KEY:
        strReturn.append(EcaString.NOCOMM_SUBJECT);
        break;
      case TeleNavProStrings.ECA_NOCOMMFORDAYS_KEY:
        strReturn.append(EcaString.NOCOMMFORDAYS_SUBJECT);
        break;
      case TeleNavProStrings.ECA_CLOCKOUT_KEY:
        strReturn.append(EcaString.CLOCKOUT_SUBJECT);
        break;
      case TeleNavProStrings.ECA_TURNOFF_KEY:
        strReturn.append(EcaString.APPTURNOFF_SUBJECT);
        break;
      //        case TeleNavProStrings.ECA_WORKINGHOUR_KEY:
      //            strReturn.append(EcaString.whSubject);
      //            break;
      case TeleNavProStrings.ECA_GEOFENCE_KEY:
        strReturn.append(EcaString.GEOFENCE_SUBJECT);
        break;
        //added by gaoyun 2007-7-8
        //add recall speeding alert
      case TeleNavProStrings.ECA_RECALL_SPEEDING_KEY:
    	//end
      case TeleNavProStrings.ECA_SPEEDING_KEY:
        strReturn.append(EcaString.SPEED_SUBJECT);
        break;
      case TeleNavProStrings.ECA_STOP_KEY:
        strReturn.append(EcaString.STOP_SUBJECT);
        break;
      case TeleNavProStrings.ECA_LOWBATTERY_KEY:
        strReturn.append(EcaString.LOWBATTERY_SUBJECT);
        break;
      case TeleNavProStrings.ECA_LOWMEMORY_KEY:
        strReturn.append(EcaString.LOWMEMORY_Subject);
        break;
      case TeleNavProStrings.ECA_JOBDELAY_KEY:
        strReturn.append(EcaString.JOBSTARTDELAY_SUBJECT);
        break;
      case TeleNavProStrings.ECA_JOBOVERTIME_KEY:
        strReturn.append(EcaString.JOBOVERDELAY_SUBJECT);
        break;
      case TeleNavProStrings.ECA_JOBLONGERTRANSIT_KEY:
        strReturn.append(EcaString.JOBTRANSITOVER_SUBJECT);
        break;
      case TeleNavProStrings.ECA_JOBDELAYRECEIVE_KEY:
        strReturn.append(EcaString.JOBRECEIVEDDELAY_SUBJECT);
        break;
      //        case TeleNavProStrings.ECA_JOBEXPIRED_KEY:
      //            strReturn.append(EcaString.jeSubject);
      //            break;
      case TeleNavProStrings.ECA_STATECROSS_KEY:
        strReturn.append(EcaString.STATECROSS_SUBJECT);
        break;
      case TeleNavProStrings.ECA_DATAOVERLIMIT_KEY:
        strReturn.append(EcaString.DATAOVERLIMIT_SUBJECT);
        break;
      default :
        strReturn.append(" ");
    }

    strReturn.append(toValue(subjectDefinedByUser));
    return strReturn.toString();
  }

  //added by gaoyun 2006-3-13
  public static String getAlertName(int iEcaTypeId){
	  switch (iEcaTypeId) {
	      case TeleNavProStrings.ECA_CLODKIN_KEY:
	        return "Not Clocked In";
	      case TeleNavProStrings.ECA_APPSTART_KEY:
	    	return "No Communication(shift start)";
	      case TeleNavProStrings.ECA_NOCOMM_KEY:
	    	return "No Communication(during shift)";
	      case TeleNavProStrings.ECA_NOCOMMFORDAYS_KEY:
	    	  return "No Communication For Days";
	      case TeleNavProStrings.ECA_CLOCKOUT_KEY:
	    	  return "Not Clocked Out";
	      case TeleNavProStrings.ECA_TURNOFF_KEY:
	    	  return "No Communication(after shift)";
	      case TeleNavProStrings.ECA_GEOFENCE_KEY:
	    	  return "Geofence Alert";
	      case TeleNavProStrings.ECA_SPEEDING_KEY:
	    	  return "Speeding Alert";
	      case TeleNavProStrings.ECA_STOP_KEY:
	    	  return "Stop Alert";
	      case TeleNavProStrings.ECA_LOWBATTERY_KEY:
	    	  return "Low Battery";
	      case TeleNavProStrings.ECA_LOWMEMORY_KEY:
	    	  return "Low Memory";
	      case TeleNavProStrings.ECA_JOBDELAY_KEY:
	    	  return "Job Start Delay";
	      case TeleNavProStrings.ECA_JOBOVERTIME_KEY:
	    	  return "Job Completion Delay";
	      case TeleNavProStrings.ECA_JOBLONGERTRANSIT_KEY:
	    	  return "Job Transit Delay ";
	      case TeleNavProStrings.ECA_JOBDELAYRECEIVE_KEY:
	    	  return "Job Receiving Delay";
	      case TeleNavProStrings.ECA_STATECROSS_KEY:
	    	  return "State Cross Alert";
	      case TeleNavProStrings.ECA_CLOCKIN_EVENT_KEY:
	    	  return "Clock In";
	      case TeleNavProStrings.ECA_CLOCKOUT_EVENT_KEY:
	    	  return "Clock Out";
	    	  //add 2006-4-7 by gaoyun 
	      case TeleNavProStrings.ECA_LOCATION_NOTE_KEY:
	    	  return "Location Notes Alert";
	      case TeleNavProStrings.ECA_RECALL_SPEEDING_KEY:
	    	  return "Recall Speeding Alert";
	      default :
	        return "";
	  }
	  
  }
  public static String getEmailBody(int iEcaTypeId, String strMessage,
      String strTrablePhoneList, String strLabelList, String strStartTime,
      String strEndTime) {
    StringBuffer strReturn = new StringBuffer("");
    StringBuffer strTrakableLabel = new StringBuffer("");
    if (strTrablePhoneList != null && strLabelList != null) {
      StringTokenizer stPhone = new StringTokenizer(strTrablePhoneList, ",",
          false);
      StringTokenizer stLabel = new StringTokenizer(strLabelList, ",", false);

      while (stPhone.hasMoreTokens() && stLabel.hasMoreTokens()) {
        strTrakableLabel.append(toValue(stPhone.nextToken()));
        strTrakableLabel.append(" ");
        strTrakableLabel.append(toValue(stLabel.nextToken()));
        strTrakableLabel.append(", ");
      }
    }
    try {
      strTrakableLabel.deleteCharAt(strTrakableLabel.length() - 1);
      strTrakableLabel.deleteCharAt(strTrakableLabel.length() - 1);
    }
    catch (Exception e) {

    }

    strReturn.append(strTrakableLabel.toString());

    switch (iEcaTypeId) {
      case TeleNavProStrings.ECA_CLODKIN_KEY:
        strReturn.append(EcaString.CLOCKIN_EMAIL_BODY);
        strReturn.append(parseHourMin(strStartTime));
        //strReturn.append(" \n ");
        strReturn.append(". ");
        strReturn.append(toValue(strMessage));
        break;
      case TeleNavProStrings.ECA_APPSTART_KEY:
        strReturn.append(EcaString.APPSTART_EMAIL_BODY);
        strReturn.append(parseHourMin(strStartTime));
        //strReturn.append(": \n ");
        strReturn.append(". ");
        strReturn.append(toValue(strMessage));
        break;
      case TeleNavProStrings.ECA_NOCOMM_KEY:
        strReturn.append(EcaString.NOCOMM_EMAIL_BODY);
        //strReturn.append(" \n ");
        strReturn.append(" ");
        strReturn.append(toValue(strMessage));
        break;
      case TeleNavProStrings.ECA_NOCOMMFORDAYS_KEY:
        strReturn.append(EcaString.NOCOMMFORDAYS_EMAIL_BODY);
        strReturn.append(" ");
        strReturn.append(toValue(strMessage));
        break;
      case TeleNavProStrings.ECA_CLOCKOUT_KEY:
        strReturn.append(EcaString.CLOCKOUT_EMAIL_BODY);
        strReturn.append(parseHourMin(strEndTime));
        //strReturn.append(": \n ");
        strReturn.append(". ");
        strReturn.append(toValue(strMessage));
        break;
      case TeleNavProStrings.ECA_TURNOFF_KEY:
        strReturn.append(EcaString.APPTURNOFF_EMAIL_BODY);
        strReturn.append(parseHourMin(strEndTime));
        //strReturn.append(": \n ");
        strReturn.append(". ");
        strReturn.append(toValue(strMessage));
        break;
      //        case TeleNavProStrings.ECA_WORKINGHOUR_KEY:
      //            strReturn.append(EcaString.whEMailBody);
      //            break;
      case TeleNavProStrings.ECA_GEOFENCE_KEY:
        strReturn.append(EcaString.GEOFENCE_EMAIL_BODY);
        strReturn.append(" ");
        strReturn.append(toValue(strMessage));
        break;
        //added by gaoyun 2007-7-8
        //add recall speeding alert
      case TeleNavProStrings.ECA_RECALL_SPEEDING_KEY:
    	//end
      case TeleNavProStrings.ECA_SPEEDING_KEY:
        strReturn.append(EcaString.SPEED_EMAIL_BODY);
        strReturn.append(" ");
        strReturn.append(toValue(strMessage));
        break;
      case TeleNavProStrings.ECA_STOP_KEY:
        strReturn.append(EcaString.STOP_EMAIL_BODY);
        strReturn.append(" ");
        strReturn.append(toValue(strMessage));
        break;
      case TeleNavProStrings.ECA_LOWBATTERY_KEY:
        strReturn.append(EcaString.LOWBATTERY_EMAIL_BODY);
        strReturn.append(" ");
        strReturn.append(toValue(strMessage));
        break;
      case TeleNavProStrings.ECA_LOWMEMORY_KEY:
        strReturn.append(EcaString.LOWMEMORY_EMAIL_Body);
        strReturn.append(" ");
        strReturn.append(toValue(strMessage));
        break;

      case TeleNavProStrings.ECA_JOBDELAY_KEY:
        strReturn.append(EcaString.JOBSTARTDELAY_EMAIL_BODY);
        strReturn.append(" ");
        strReturn.append(toValue(strMessage));
        strReturn.append(" on schedule.");
        break;
      case TeleNavProStrings.ECA_JOBOVERTIME_KEY:
        strReturn.append(EcaString.JOBOVERDELAY_EMAIL_BODY);
        strReturn.append(" ");
        strReturn.append(toValue(strMessage));
        strReturn.append(" on schedule.");
        break;
      case TeleNavProStrings.ECA_JOBLONGERTRANSIT_KEY:
        strReturn.append(EcaString.JOBTRANSITOVER_EMAIL_BODY);
        //strReturn.append(" ");
        strReturn.append(toValue(strMessage));
        break;
      case TeleNavProStrings.ECA_JOBDELAYRECEIVE_KEY:
        strReturn.append(EcaString.JOBRECEIVEDDELAY_EMAIL_BODY);
        strReturn.append(" ");
        strReturn.append(toValue(strMessage));
        strReturn.append(" before job was scheduled to begin.");
        break;
      //        case TeleNavProStrings.ECA_JOBEXPIRED_KEY:
      //
      //            break;
      case TeleNavProStrings.ECA_STATECROSS_KEY:
        strReturn.append(EcaString.STATECROSS_EMAIL_BODY);
        strReturn.append(" ");
        strReturn.append(toValue(strMessage));
        break;
      case TeleNavProStrings.ECA_DATAOVERLIMIT_KEY:
        strReturn.append(EcaString.DATAOVERTIMIT_EMAIL_BODY);
        strReturn.append(" ");
        strReturn.append(toValue(strMessage));
        break;
        //added by gaoyun 20060302 fix bug 14518 
        //add clock in/out event
      case TeleNavProStrings.ECA_CLOCKIN_EVENT_KEY:
    	  strReturn.append(EcaString.CLOKCK_IN_EVENT_EMAIL_BODY);
          strReturn.append(" ");
          strReturn.append(toValue(strMessage));
    	  break;    	  
      case TeleNavProStrings.ECA_CLOCKOUT_EVENT_KEY:
    	  strReturn.append(EcaString.CLOKCK_OUT_EVENT_EMAIL_BODY);
          strReturn.append(" ");
          strReturn.append(toValue(strMessage));
    	  break; 
    	 //end
      case TeleNavProStrings.ECA_LOCATION_NOTE_KEY:
    	  strReturn.append(EcaString.LOCATION_NOTE_EMAIL_BODY);
          strReturn.append(" ");
          strReturn.append(toValue(strMessage));
    	  break; 
    	 //end
      default :
        strReturn.append(" ");
    }

    //if (!toValue(strMessage).equals(""))
    //strReturn.append(" ");
    //strReturn.append(toValue(strMessage));

    return strReturn.toString();
  }



  public static String getMessage(int iEcaTypeId, String strMessage,
      String strStartTime) {
    StringBuffer strReturn = new StringBuffer("");
    switch (iEcaTypeId) {
      case TeleNavProStrings.ECA_CLODKIN_KEY:
        strReturn.append(EcaString.CLOCKIN_SMS_BODY);
        //strReturn.append(parseHourMin(strStartTime));
        strReturn.append(toValue(strMessage));
        break;
      case TeleNavProStrings.ECA_APPSTART_KEY:
        //strReturn.append(EcaString.appsSubject);
        //strReturn.append(" by ");
        //strReturn.append(parseHourMin(strStartTime));
        //strReturn.append(". ");
        strReturn.append(EcaString.APPSTART_SMS_BODY);
        strReturn.append(toValue(strMessage));
        break;
      case TeleNavProStrings.ECA_NOCOMM_KEY:
        strReturn.append(EcaString.NOCOMM_SMS_BODY);
        strReturn.append(toValue(strMessage));
        break;
      case TeleNavProStrings.ECA_NOCOMMFORDAYS_KEY:
        strReturn.append(EcaString.NOCOMMFORDAYS_SMS_BODY);
        strReturn.append(toValue(strMessage));
        break;
      case TeleNavProStrings.ECA_CLOCKOUT_KEY:
        strReturn.append(EcaString.CLOCKOUT_SMS_BODY);
        strReturn.append(toValue(strMessage));
        break;
      case TeleNavProStrings.ECA_TURNOFF_KEY:
        strReturn.append(EcaString.APPTURNOFF_SMS_BODY);
        strReturn.append(toValue(strMessage));
        break;
      //        case TeleNavProStrings.ECA_WORKINGHOUR_KEY:
      //            strReturn.append(EcaString.whSMSBody);
      //            break;
      case TeleNavProStrings.ECA_GEOFENCE_KEY:
        strReturn.append(EcaString.GEOFENCE_SMS_BODY);
        strReturn.append(toValue(strMessage));
        break;
        //added by gaoyun 2007-7-8
        //add recall speeding alert
      case TeleNavProStrings.ECA_RECALL_SPEEDING_KEY:
    	//end
      case TeleNavProStrings.ECA_SPEEDING_KEY:
        strReturn.append(EcaString.SPEED_SMS_BODY);
        strReturn.append(toValue(strMessage));
        break;
      case TeleNavProStrings.ECA_STOP_KEY:
        strReturn.append(EcaString.STOP_SMS_BODY);
        strReturn.append(toValue(strMessage));
        break;
      case TeleNavProStrings.ECA_LOWBATTERY_KEY:
        strReturn.append(EcaString.LOWBATTERY_SMS_BODY);
        strReturn.append(toValue(strMessage));
        break;
      case TeleNavProStrings.ECA_LOWMEMORY_KEY:
        strReturn.append(EcaString.LOWMEMORY_SMS_BODY);
        strReturn.append(toValue(strMessage));
        break;
      case TeleNavProStrings.ECA_JOBDELAY_KEY:
        strReturn.append(EcaString.JOBSTARTDELAY_SMS_BODY);
        strReturn.append(toValue(strMessage));
        strReturn.append(" on schedule.");
        break;
      case TeleNavProStrings.ECA_JOBOVERTIME_KEY:
        strReturn.append(EcaString.JOBOVERDELAY_SMS_BODY);
        strReturn.append(toValue(strMessage));
        strReturn.append(" on schedule.");
        break;
      case TeleNavProStrings.ECA_JOBLONGERTRANSIT_KEY:
        strReturn.append(EcaString.JOBTRANSITOVER_SMS_BODY);
        strReturn.append(toValue(strMessage));
        //strReturn.append(" before job was scheduled to begin.");
        break;
      case TeleNavProStrings.ECA_JOBDELAYRECEIVE_KEY:
        strReturn.append(EcaString.JOBRECEIVEDDELAY_SMS_BODY);
        strReturn.append(toValue(strMessage));
        break;
      //        case TeleNavProStrings.ECA_JOBEXPIRED_KEY:
      //            
      //            break;
      case TeleNavProStrings.ECA_STATECROSS_KEY:
        strReturn.append(EcaString.STATECROSS_SMS_BODY);
        strReturn.append(toValue(strMessage));
        break;
      case TeleNavProStrings.ECA_DATAOVERLIMIT_KEY:
        strReturn.append(EcaString.DATAOVERTIMIT_SMS_BODY);
        strReturn.append(toValue(strMessage));
        break;
      default :
        strReturn.append(toValue(strMessage));
    }

    return strReturn.toString();
  }



  public static String getSubject(EcaMsg ecaMsg) {
    return getSubject(ecaMsg.getJobType(), ecaMsg.getSubject());
  }



  public static String getEmailBody(EcaMsg ecaMsg) {
    return getEmailBody(ecaMsg.getJobType(), ecaMsg.getMessage(), ecaMsg
        .getTrackablePhone(), ecaMsg.getLabelList(), String.valueOf(ecaMsg
        .getStartTime()), String.valueOf(ecaMsg.getEndTime()));
  }



  public static String getSmsBody(EcaMsg ecaMsg) {
    return getMessage(ecaMsg.getJobType(), ecaMsg.getMessage(), String
        .valueOf(ecaMsg.getStartTime()));
  }



  public static long deparseHourMin(String sHour, String sMin, String sType) {
    long lReturn = -1L;
    long lHour = Long.parseLong(sHour);
    long lMin = Long.parseLong(sMin);
    if (sType.equals("AM")) {
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



  public static String parseHourMin(String strTime) {
    String strReturn = toValue(strTime);
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

      strReturn = sHour + ":" + sMin + " " + sPostfix;

    }
    return strReturn;
  }



  public static boolean isDigit(String str) {
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



  public static void sendMail(EcaMsg ecaMsg, String subject, String body) {
    if (ecaMsg.getSendEmail().equals(EcaString.SEND)
        && ecaMsg.getEmailAddress() != null) {
      StringTokenizer st = new StringTokenizer(ecaMsg.getEmailAddress(), ",",
          false);
      while (st.hasMoreTokens()) {
        String sEmailAddress = st.nextToken();
        BaseJob.sendMail(sEmailAddress, subject, body);
      }
    }
  }



  public static boolean sendMail(String toEmail, String strSubject,
      String strBody) {
    log.debug("sendEmail to " + toEmail + " strSubject=" + strSubject
        + " strBody=" + strBody);

    boolean b = false;
    /*
     * StringBuffer body = new StringBuffer(""); body.append(strBody);
     * body.append("\n"); body.append("It's ");
     * body.append(MonitorJob.getCurrentTime(-1)); body.append(" now.");
     */
    try {

      //b = TnpBaseManager.sendMail(toEmail, strSubject, body.toString());
      b = TnpBaseManager.sendMail(toEmail, strSubject, strBody);

    }
    catch (Exception e) {
      log.error("Cannot send email to " + toEmail);
      e.printStackTrace();
    }

    log.debug("sendEmail done");
    return b;
  }



  public static boolean sendMonitorMail(String toEmail, String strSubject,
      String strBody) {
    boolean b = false;
    if (toEmail == null)
      toEmail = BaseJob.MAILSUPORT;
    try {

      b = sendMail(toEmail, strSubject, strBody);

    }
    catch (Exception e) {
      log.error("Cannot send email to " + toEmail);
      e.printStackTrace();
    }
    return b;
  }



  public static boolean sendMonitorMail(String toEmail, String subject,
      String body, Throwable throwable) {
    boolean b = false;
    try {
      String bodyTmp = body + convertThrowable2String(throwable).toString();

      b = sendMonitorMail(toEmail, subject, bodyTmp);

    }
    catch (Exception e) {
      e.printStackTrace();
    }
    return b;
  }



  public static StringBuffer convertThrowable2String(Throwable throwable) {
    StringBuffer sbReturn = new StringBuffer();
    if (throwable != null) {
      try {
        ThrowableInformation tif = new ThrowableInformation(throwable);
        String[] arrStr = tif.getThrowableStrRep();
        sbReturn.append("\n");
        for (int i = 0; i < arrStr.length; i++) {
          sbReturn.append(arrStr[i]);
          sbReturn.append("\n");
        }

      }
      catch (Exception e) {
        e.printStackTrace();
      }
    }
    return sbReturn;
  }



  public static void sendSMS(EcaMsg ecaMsg, String subject, String body) {
    if (ecaMsg.getSendSMS().equals(EcaString.SEND)) {
      StringTokenizer st0 = new StringTokenizer(ecaMsg.getCarrTypeIdList(),
          ",", false);
      StringTokenizer st1 = new StringTokenizer(ecaMsg.getTrackablePhone(),
          ",", false);
      while (st0.hasMoreTokens() && st1.hasMoreTokens()) {
        BaseJob.sendSMS(Integer.parseInt(st0.nextToken()), st1.nextToken(),
            subject, body);
      }
    }
  }



  private static void sendSMS(int strCarrTypeId, String strPhone,
      String strSubject, String strBody) {
    log.debug("sendSMS: CarrTypeId/Phone/Subject = " + strCarrTypeId + "/"
        + strPhone + "/" + strSubject);
    try {
      if (strPhone.length() > 4 && isDigit(strPhone))
        TrackableManager.sendSMS(strCarrTypeId, strPhone, strSubject, strBody);
      else
        log.error("sendAutoStart fail: phone = " + strPhone);
    }
    catch (Exception e) {
      log.error("sendAutoStart fail - excecption: ", e);
    }
  }



  public static void sendSMS(TnpTrackable trackable, String subject, String body)
      throws Exception {
    if (trackable == null) {
      return;
    }
    try {
      //TrackableManager.sendSMS(trackable, strSubject, strBody);

      sendSMS(trackable.getCarrTypeId(), trackable.getTelenav_min(), subject,
          body);
    }
    catch (Exception e) {
      throw e;
    }
  }



  private static void sendAutoStart(int carrTypeId, String phone, String ip,
      int portNum) {
    log.debug("sendAutoStart: CarrTypeId/Phone/IP/PortNum = " + carrTypeId
        + "/" + phone + "/" + ip + "/" + portNum);
    try {
      TrackableManager.startTrackable(carrTypeId, phone, ip, portNum);
    }
    catch (Exception e) {
      log.error("sendAutoStart fail - excecption: ", e);
    }
  }



  public static void sendAutoStart(EcaMsg ecaMsg) throws Exception {
    try {
      if (ecaMsg.getSendAutoStart().equals(EcaString.SEND)) {
        String strCarrTypeId = "";
        String strPhoneNo = "";
        String strPhoneIp = "";
        String strPortNum = "";

        StringTokenizer st0 = null;
        StringTokenizer st1 = null;
        StringTokenizer st2 = null;
        StringTokenizer st3 = null;

        if (ecaMsg.getCarrTypeIdList() != null)
          st0 = new StringTokenizer(ecaMsg.getCarrTypeIdList(), ",", false);

        if (ecaMsg.getTrackablePhone() != null)
          st1 = new StringTokenizer(ecaMsg.getTrackablePhone(), ",", false);

        if (ecaMsg.getTrackablePhoneIp() != null)
          st2 = new StringTokenizer(ecaMsg.getTrackablePhoneIp(), ",", false);

        if (ecaMsg.getPortNumList() != null)
          st3 = new StringTokenizer(ecaMsg.getPortNumList(), ",", false);
        if (st0 != null && st1 != null && st2 != null && st3 != null) {
          while (st0.hasMoreTokens() && st1.hasMoreTokens()
              && st2.hasMoreTokens() && st3.hasMoreTokens()) {
            strCarrTypeId = st0.nextToken();
            strPhoneNo = st1.nextToken();
            strPhoneIp = st2.nextToken();
            strPortNum = st3.nextToken();

            BaseJob.sendAutoStart(Integer.parseInt(strCarrTypeId), strPhoneNo,
                strPhoneIp, Integer.parseInt(strPortNum));
          }
        }
      }
    }
    catch (Exception e) {
      throw e;
    }
  }



  public static void sendAlert(long companyId, Vector receiver,
      String msgSubject, String msgBody, long createTime, boolean adminOnly)
      throws Exception {
    log.debug("sendAlert: companyId/msgSubject= " + companyId + "/"
        + msgSubject);
    AlertManager am = new AlertManager();
    try {
      am.dispatch(companyId,//long tnp_company_id,
          receiver, //receiver,
          msgSubject, //msg_subject,
          msgBody, //msg_body,
          null, //reply_body,
          0, //tnp_p_address_info_id,
          0, //tnp_d_address_info_id,
          createTime, //createTime,
          adminOnly, //admin_only,
          null //Connection
          );
    }
    catch (Exception e) {
      log.error("sendAlert fail - excecption: ", e);
      throw e;
    }
    log.debug("sendAlert - done ");
  }



  public static void sendAlertMsg(EcaMsg ecaMsg, String subject, String body)
      throws Exception {
    if (ecaMsg.getSendToPhone().equals(EcaString.SEND)) {
      StringTokenizer st = new StringTokenizer(ecaMsg.getTrackable(), ",",
          false);
      Vector v = new Vector();
      while (st.hasMoreTokens()) {
        v.add(new Long(st.nextToken()));
      }
      try {
        BaseJob.sendAlert(ecaMsg.getCompanyId(),//tnp_company_id,
            v, //receiver,
            subject, //msg_subject,
            body, //msg_body,
            System.currentTimeMillis(), //createTime,
            false //admin_only
            );

      }
      catch (Exception e) {
        throw e;
      }
    }
  }

}