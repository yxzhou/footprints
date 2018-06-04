/**
 * @file EcaString.java
 * @author Zhou Yuanxi
 * @date Feb. 16, 2004 Copyright (c) 2004 TeleVigation, Inc
 */
package fgafa.basic.socket.ecax;

import com.televigation.statuschecking.ClientWrapper;

public class EcaString {
	//ecamsg type 
	public final static int REQ_NEXTJOB = 10;
	public final static int REQ_LATESTJOBID = 11;
	public final static int REQ_EXIT_COMMAND = 12;
	public final static int RESP_NEXTJOB = 20;
	public final static int RESP_LATESTJOBID = 21;
	public final static int RESP_ACCEPTED_EXIT = 22;
	public final static int RESP_REJECTED = 23;
	public final static int RESP_NO_REPLY = 24;
	public final static int RESP_NO_JOB = 25;

	//variable in EcaServer
	public final static long WAITINGJOBINTERVAL = 10000;
	public final static long INTERVAL_WORKDAY = 3600000;
	//60 * 60 * 1000; //one hour
	public final static long INTERVAL_MONITOR = 900000;
	//15 * 60 * 1000; //INTERVAL
	public final static long INTERVAL_MAX = 18000000; //5 * 60 * 60 * 1000; //
	public final static long HOUR = 86400000; //24 * 60 * 60 * 1000;
	public final static long INITMASTERLASTTIME = 0L;
	public final static long INITSLAVERLASTTIME = 0l;
	public final static int INITCOUNT = 0;

	public static final String SEND = "1";

	public final static String TURE = "true";

	//variable in MonitorJob
	//modified by kye on 2005-08-30
	public final static int NORMALSTATUS = ClientWrapper.NORMALSTATUS; //1; 
	public final static int EXCEPTIONSTATUS = ClientWrapper.EXCEPTIONSTATUS; //2;
	public final static int URGENTSTATUS = ClientWrapper.URGENTSTATUS; //3;

	//clockin
	public static String CLOCKIN_SUBJECT =
		"Late clock in or out of coverage area";
	public static String CLOCKIN_EMAIL_BODY =
		" has not clocked in or is out of coverage at ";
	public static String CLOCKIN_SMS_BODY =
		"Please clock in now. If you aleady clock in for today, please ignore this message. ";

	//appstart
	public static String APPSTART_SUBJECT = "No communication at start of shift";
	public static String APPSTART_EMAIL_BODY =
		" has not started the TeleNavTrack application or is out of coverage area at ";
	public static String APPSTART_SMS_BODY =
		"Please start the TeleNavTrack application. If you are out of coverage and the application is on, all tracking information will be recorded as normal. ";

	//nocomm
	public static String NOCOMM_SUBJECT = "No communication during shift hours";
	public static String NOCOMM_EMAIL_BODY =
		" has not started the TeleNavTrack or is out of coverage area.";
	public static String NOCOMM_SMS_BODY =
		"Please start the TeleNavTrack application. If you are out of coverage and the application is on, all tracking information will be recorded as normal. ";

	//  nocommfordays
	public static String NOCOMMFORDAYS_SUBJECT = "No communication for days";
	public static String NOCOMMFORDAYS_EMAIL_BODY =
		" has not started the TeleNavTrack or is out of coverage area.";
	public static String NOCOMMFORDAYS_SMS_BODY =
		"Please start the TeleNavTrack application. If you are out of coverage and the application is on, all tracking information will be recorded as normal. ";

	//workinghour
	//    public static String whSubject = "";
	//    public static String whEMailBody = "";
	//    public static String whSMSBody = "";

	//clockout
	public static String CLOCKOUT_SUBJECT =
		"Late clock out or out of coverage area";
	public static String CLOCKOUT_EMAIL_BODY = " has not clocked out at ";
	public static String CLOCKOUT_SMS_BODY =
		"Please clock out of the TeleNavTrack. ";

	//appturnoff
	public static String APPTURNOFF_SUBJECT = "App not turned off after shift";
	public static String APPTURNOFF_EMAIL_BODY =
		" has been found not turn off the TeleNavTrack application at ";
	public static String APPTURNOFF_SMS_BODY =
		"Please turn off the TeleNavTrack application. ";

	//geofence
	public static String GEOFENCE_SUBJECT = "";
	public static String GEOFENCE_EMAIL_BODY = "";
	public static String GEOFENCE_SMS_BODY = "";

	//clock in  event alert
	public static String CLOKCK_IN_EVENT_SUBJECT = "";
	public static String CLOKCK_IN_EVENT_EMAIL_BODY = "";
	public static String CLOKCK_IN_EVENT_SMS_BODY = "";
	//	clock out  event alert
	public static String CLOKCK_OUT_EVENT_SUBJECT = "";
	public static String CLOKCK_OUT_EVENT_EMAIL_BODY = "";
	public static String CLOKCK_OUT_EVENT_SMS_BODY = "";
	
	//location note alert
	public static String LOCATION_NOTE_SUBJECT = "";
	public static String LOCATION_NOTE_EMAIL_BODY = "";
	public static String LOCATION_NOTE_SMS_BODY = "";
	//speeding
	public static String SPEED_SUBJECT = "";
	public static String SPEED_EMAIL_BODY = "";
	public static String SPEED_SMS_BODY = "";

	//stop
	public static String STOP_SUBJECT = "";
	public static String STOP_EMAIL_BODY = "";
	public static String STOP_SMS_BODY = "";

	//lowbattery
	public static String LOWBATTERY_SUBJECT = "";
	public static String LOWBATTERY_EMAIL_BODY = "";
	public static String LOWBATTERY_SMS_BODY = "";

	//lowmemory
	public static String LOWMEMORY_Subject = "";
	public static String LOWMEMORY_EMAIL_Body = "";
	public static String LOWMEMORY_SMS_BODY = "";

	//transmission data over limit alert (tdol)
	public static String DATAOVERLIMIT_SUBJECT = "";
	public static String DATAOVERTIMIT_EMAIL_BODY = "";
	public static String DATAOVERTIMIT_SMS_BODY = "";

	//jobstartdelay
	public static String JOBSTARTDELAY_SUBJECT = "Job Start Delay";
	public static String JOBSTARTDELAY_EMAIL_BODY = " has not started job ";
	public static String JOBSTARTDELAY_SMS_BODY = " You have not start job ";

	//joboverwork
	public static String JOBOVERDELAY_SUBJECT = "Job Complete Delay";
	public static String JOBOVERDELAY_EMAIL_BODY = " has not completed job ";
	public static String JOBOVERDELAY_SMS_BODY = " You have not completed job ";

	//jobovertransit
	public static String JOBTRANSITOVER_SUBJECT = "Job Transit Time exceed";
	public static String JOBTRANSITOVER_EMAIL_BODY =
		" has exceeded estimated transit time for job ";
	public static String JOBTRANSITOVER_SMS_BODY =
		" You have exceeded estimated transit time for job ";

	//jobreceivedelay
	public static String JOBRECEIVEDDELAY_SUBJECT = "Job Not Received";
	public static String JOBRECEIVEDDELAY_EMAIL_BODY = " has not received job ";
	public static String JOBRECEIVEDDELAY_SMS_BODY =
		" You have not received job ";

	//jobexpired
	//    public static String jeSubject = "jobexpired";
	//    public static String jeEMailBody = " ";
	//    public static String jeSMSBody = " ";    

	//statecross
	public static String STATECROSS_SUBJECT = " ";
	public static String STATECROSS_EMAIL_BODY = "";
	public static String STATECROSS_SMS_BODY = " ";
}