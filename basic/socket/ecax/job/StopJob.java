/**
 * @file StopJob.java
 * @author Zhou Yuanxi
 * @date Nov. 29, 2004 Copyright (c) 2004 TeleVigation, Inc
 */
package fgafa.basic.socket.ecax.job;

//import java.sql.SQLException;
import java.sql.Connection;
import java.util.HashMap;
import java.util.StringTokenizer;
import java.util.Vector;

import com.televigation.db.telenavpro.AdminManager;
import com.televigation.db.telenavpro.RuleManager;
import com.televigation.db.telenavpro.GeoFencexManager;
import com.televigation.db.telenavpro.TrackableManager;
import com.televigation.db.telenavpro.datatypes.AlertProfileSend;
import com.televigation.db.telenavpro.datatypes.TnpAdmin;
import com.televigation.db.telenavpro.datatypes.TnpAlertProfile;
import com.televigation.db.telenavpro.datatypes.TnpAlertRule;
//import com.televigation.db.telenavpro.datatypes.TnpEcax;
import com.televigation.db.telenavpro.datatypes.TnpTrackable;
import com.televigation.log.TVCategory;
import fgafa.basic.socket.ecax.EcaMsg;
//import com.televigation.telenavpro.filter.DateTimeHelper;
//import com.televigation.telenavpro.servlet.ResourceStrings;
import com.televigation.telenavpro.servlet.TeleNavProStrings;

public class StopJob extends BaseJob implements EcaJob {
    public static TVCategory log = (TVCategory) TVCategory
            .getInstance("com.televigation.telenavpro.ecax.StopJob");

    private static StopJob instance;

    //private static EcaMsg ecaMsg = null;

    //private StopJob() throws Exception {}

    private StopJob(EcaMsg ecaMsg) {
        this.ecaMsg = ecaMsg;
    }

    public static synchronized StopJob getInstance(EcaMsg ecaMsg) throws Exception {
        if (instance == null) {
            instance = new StopJob(ecaMsg);
        }
        instance.ecaMsg = ecaMsg;
        return instance;
    }

    public static void sendAlert(Connection conn,long trackId,
            String strSubject, String strMsg, long timeStamp) throws Exception {
        StringBuffer subject = new StringBuffer();
        StringBuffer msg = new StringBuffer();

        //sSubject.append("StopJob Alert");
        subject.append( strSubject);
        //sMsg.append("please note you have stop for a long time.");
        msg.append(strMsg);

        //fetch the alert information
        try{
            RuleManager rm = new RuleManager();
 
            TnpAlertProfile alertProfile = rm.fetchAlertProfileByTrackId(trackId);
            //fix bug of always send alert even not enable 
            //modified by gaoyun 2006-4-7
            //if (alertProfile == null )
            //    return;
            if(alertProfile == null || !alertProfile.isEnableStopAlert())
                return;            
            //end
            HashMap hmTmp = alertProfile.getEcaActions();
            AlertProfileSend alertProfileSend = (AlertProfileSend)hmTmp.get(new Long(TeleNavProStrings.ECA_STOP_KEY));
            if(alertProfileSend == null)
                return;

            TnpAlertRule alertRule = rm.fetchAlertRuleByTrackID(trackId);
	        if (alertRule != null && alertRule.getShortMessage() != null){
	            msg.append(" ");
	            msg.append(alertRule.getShortMessage());
	        }    

            TrackableManager tm = new TrackableManager();
            TnpTrackable trackable = tm.fetch(trackId); 
            if(trackable == null)
                return;
            AdminManager am = new AdminManager();
            TnpAdmin tnpMasterAdmin = am.fetchMasterByCompanyId(trackable.getCompany_id());
	        
	        //do this alert
	        //send email
	        if (alertRule != null && alertProfileSend.isEnSendMail()) {
	            StringTokenizer st = new StringTokenizer(alertRule.getEmailAddress(),",", false);
	            while (st.hasMoreTokens()) {
	                String emailAddress = st.nextToken();
	                BaseJob.sendMail(emailAddress, subject.toString(),msg.toString());
	            }
	        }
	        //send sms
	        if (alertProfileSend.isEnSendSMS()) {   
	            BaseJob.sendSMS(trackable, subject.toString(),msg.toString());
	        }
	        
	        //send to phone
	        if (alertProfileSend.isEnSendAlert()) {
		        Vector v = new Vector();
		        v.add(new Long(trackId));
		        BaseJob.sendAlert(
		                tnpMasterAdmin.getCompany_id(), //tnp_company_id, 
		                v, //Vector receiver,
		                subject.toString(), //msg_subject, 
		                msg.toString(), //msg_body, 
		                System.currentTimeMillis(), //createTime,
		                false//admin_only
		                );
	         }    
		        
	        //send web
	        GeoFencexManager gf = new GeoFencexManager();
	        gf.insertTnp_eca_job_trace(conn,
	                TeleNavProStrings.ECA_STOP_KEY, //JobTypeId,
	                subject.toString(), //sSubject,
	                msg.toString(), //sMessage,
	                trackId, //TrackableId,
	                timeStamp, //CurrentTime,
	                -1, //EcaRuleId,
	                1, //SendAlertId
	                tnpMasterAdmin.getTnp_Admin_Id(),//alertRule.getAdminId()
	                tnpMasterAdmin.getCompany_id()
	                ); 
        }catch(Exception e){
            throw e;
        } 
        
    }
    
    public void process() throws Exception{}

}