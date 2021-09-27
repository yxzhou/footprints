/**
 * @file MonitorJob.java
 * @author Zhou Yuanxi
 * @date May. 26, 2004 Copyright (c) 2004 TeleVigation, Inc
 * modified by kye on 2005-08-30 for StatusChecking
 */
package basic.socket.ecax.job;

import com.televigation.log.TVCategory;
import com.televigation.statuschecking.ClientWrapper;
import basic.socket.ecax.EcaMsg;
import basic.socket.ecax.EcaString;
import com.televigation.telenavpro.filter.DateTimeHelper;
import com.televigation.telenavpro.servlet.ResourceStrings;

public class MonitorJob extends BaseJob {

	public static TVCategory log =
		(TVCategory) TVCategory.getInstance(
			"com.televigation.telenavpro.ecax.MonitorJob");

	protected MonitorJob() {
	}

	/**
	 * input lTime<0 then return currentTime
	 * 
	 * @param timestamp
	 * @return 'yyyy-MM-dd HH:mm PST'
	 */
	public static String getCurrentTime(long timestamp) {
		if (timestamp < 0)
			timestamp = System.currentTimeMillis();
		return DateTimeHelper.dateTime(
			timestamp,
			ResourceStrings.LONG_DATE_FORMAT_S,
			1)
			+ "PST ";
	}

	public static void sendAlertByM(
		String masterIP,
		String slaveIP,
		EcaMsg ecaMsg) {

		String subjectTmp =
			"ECAXJob ID" + ecaMsg.getJobId() + " taking too long to complete ";
		String bodyTmp =
			"Master IP is "
				+ masterIP
				+ "\n"
				+ "Slave IP is "
				+ slaveIP
				+ "\n"
				+ "Job ID is "
				+ ecaMsg.getJobId()
				+ "\n"
				+ "Timeout at ";

		bodyTmp =
			bodyTmp
				+ MonitorJob.getCurrentTime(-1)
				+ " \n "
				+ "Timeout time is "
				+ EcaString.INTERVAL_MONITOR
				+ " \n ";

		bodyTmp =
			bodyTmp + "ECA Job Type is " + ecaMsg.getJobType() + ecaMsg.toString();

		MonitorJob.sendToOperation(
			EcaString.URGENTSTATUS,
			null,
			subjectTmp,
			bodyTmp,
			null);
		//BaseJob.sendMonitorMail(null, sSubject, sBody);

	}

	public static void sendAlertByS(
		String subject,
		String masterIP,
		String slaveIP) {

		String subjectTmp = "ECAX Slaver timed out connecting to the ECAX Master";
		if (subject != null)
			subjectTmp = subject;

		String body =
			"Master IP is "
				+ masterIP
				+ " \n "
				+ "Slave IP is "
				+ slaveIP
				+ " \n "
				+ "Time at ";

		body = body + MonitorJob.getCurrentTime(-1);

		MonitorJob.sendToOperation(
			EcaString.URGENTSTATUS,
			null,
			subjectTmp,
			body,
			null);
	}

	/**
	public static void sendAlertByS(String sMasterIp, String sSlaveIp) {
	
	    String sSubject = "ECAX Slave Server timed out connecting to the ECA Master";
	    String sBody = "Master IP is " + sMasterIp + " \n " + "Slave IP is "
	            + sSlaveIp + " \n " + "Time at ";
	
	    long lTime = System.currentTimeMillis();
	    sBody = sBody + DateTimeHelper.dateTime(lTime, "yyyy-MM-dd HH:mm", 1)
	            + "PST";
	
	    BaseJob.sendMonitorMail(null, sSubject, sBody);
	
	}
	**/
	/**
	 * start up statecheching service
	 * @throws Exception
	 */
	public static void startupStateChecking() {
		/*if((EcaString.TURE).equalsIgnoreCase(BaseJob.ENABLESTATUSCHECKING)){
		  MonitorJob.startupStateChecking(BaseJob.SERVERNAME );
		}*/
		ClientWrapper.getWrapper().sendMsg(
			EcaString.NORMALSTATUS,
			"ECA engine start",
			null);

	}

	/*public static void startupStateChecking(String serverName) 
	{
	    try{
	        Client.init(serverName);
	    }catch(Exception e){
	        log.error("Client.init",e);
	    }
	}*/

	/**
	 * 
	 * @throws Exception
	 */
	/*public void sendMsgToStateChecking(
	   String serverName, 
	   int status, 
	   String msg,
	   Throwable throwable
	) {
	    String strExceptionTrace = "";
	    String strRemark = "";
	    boolean isFatalErrorOccured = false;
	    
	    switch(status){
	        case EcaString.NORMALSTATUS:
	          strRemark = msg + BaseJob.convertThrowable2String(throwable).toString();
	          break;
	        case EcaString.EXCEPTIONSTATUS:
	          strExceptionTrace = msg + BaseJob.convertThrowable2String(throwable).toString();
	        break;
	        case EcaString.URGENTSTATUS:	
	          isFatalErrorOccured = true;
	          strExceptionTrace = msg + BaseJob.convertThrowable2String(throwable).toString();
	          break;  
	    }
	    
	    try{ 
	      //storeStatus(java.lang.String strExceptionTrace, java.lang.String strRemark, boolean isFatalErrorOccured) 
	      Client client = Client.init(serverName);
	      client.storeStatus(strExceptionTrace,strRemark,isFatalErrorOccured);
	    }catch(Exception e){
	      log.error("client.storeStatus()",e);  
	    }
	}*/

	public static void sendToOperation(
		int status,
		String toEmail,
		String msg,
		String body,
		Throwable throwable) {
		MonitorJob.sendToOperation(
			BaseJob.SERVERNAME,
			status,
			toEmail,
			msg,
			body,
			throwable);
	}

	public static void sendToOperation(
		String serverName,
		int status,
		String toEmail,
		String msg,
		String body,
		Throwable throwable) {
		String msgTmp = MonitorJob.getCurrentTime(-1) + msg;

		if (status != EcaString.NORMALSTATUS)
			BaseJob.sendMonitorMail(toEmail, msgTmp, body, throwable);

		/*MonitorJob mJob = new MonitorJob();
		if ((EcaString.TURE).equalsIgnoreCase(BaseJob.ENABLESTATUSCHECKING))
		    mJob.sendMsgToStateChecking(serverName, status,
		            msgTmp + body, throwable);*/
		ClientWrapper.getWrapper().sendMsg(status, msgTmp + body, throwable);
	}

}