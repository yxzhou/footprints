/**
 * @file EcaMsg.java
 * @author Zhou Yuanxi
 * @date Feb. 16, 2004 Copyright (c) 2004 TeleVigation, Inc
 */
package basic.socket.ecax;

import java.io.Serializable;

public class EcaMsg implements Serializable {

    private int msgType;
    private int hostIndex; //sender
    private long jobId = -1;
    private long jobRuleid = -1;
    private int jobType = -1; //show in table tnp_eca_type_def
    private long companyId = -1;
    private long startDay;
    private long startTime;
    private long endDay;
    private long endTime;
    private String sendSMS = ""; //1 is send , 0 is not send
    private String sendEmail = ""; //1 is send , 0 is not send
    private String sendAutoStart = ""; //1 is send , 0 is not send
    //private String sSendAlert = ""; //
    private String emailAddress = "";
    private String subject = "";
    private String message = "";
    private String pollValue = "";
    private String recordValue = "";
    //public Vector vTrackables = null;
    private String trackable = "";
    private String trackablePhone = "";
    private String trackablePhoneIp = "";
    private String labelList = "";
    private String carrTypeIdList = "";
    private String portNumList = "";
    
    private String sendToPhone = ""; //1 is send , 0 is not send

    public EcaMsg(int iMsgType) {
        this.msgType = iMsgType;
    }

    public EcaMsg(int iMsgType, long iJobId) {
        this.msgType = iMsgType;
        this.jobId = iJobId;
    }

    public EcaMsg(int iMsgType, int iHostIndex, long iJobId) {
        this.msgType = iMsgType;
        this.hostIndex = iHostIndex;
        this.jobId = iJobId;
    }

    //for test
    /**
    public EcaMsg(int iMsgType, long iJobId, long lJobRuleid) {
        this.iMsgType = iMsgType;
        this.iJobId = iJobId;
        //this.lJobRuleid = lJobRuleid;
    }
    **/
    public EcaMsg(int msgType, long jobId, long jobRuleId, int jobType,
            long companyId, long startDay, long startTime, long endDay,
            long endTime, String sendSMS, String sendEmail,
            String sendAutoStart, String sendAlert, String emailAddress,
            String subject, String message, String pollValue,
            String recordValue,String trackable, String trackablePhone,
            String trackablePhoneIp, String labelList, String carrTypeIdList,
            String portNumList, String sendToPhone) {
        this.msgType = msgType;
        this.jobId = jobId;
        this.jobRuleid = jobRuleId;
        this.jobType = jobType;
        this.companyId = companyId;
        this.startDay = startDay;
        this.startTime = startTime;
        this.endDay = endDay;
        this.endTime = endTime;
        this.sendSMS = sendSMS;
        this.sendEmail = sendEmail;
        this.sendAutoStart = sendAutoStart;
        //this.sSendAlert = sSendAlert;
        this.emailAddress = emailAddress;
        this.subject = subject;
        this.message = message;
        this.pollValue = pollValue;
        this.recordValue = recordValue;
        //this.vTrackables = vTrackables;
        this.trackable = trackable;
        this.trackablePhone = trackablePhone;
        this.trackablePhoneIp = trackablePhoneIp;
        this.labelList = labelList;
        this.carrTypeIdList = carrTypeIdList;
        this.portNumList = portNumList;
        this.sendToPhone = sendToPhone;
    }

    public long getJobId() {
        return jobId;
    }
    public int getJobType() {
        return jobType;
    }
    public int getMsgType() {
        return msgType;
    }
    public String getEmailAddress() {
        return emailAddress;
    }
    public String getMessage() {
        return message;
    }
    public String getPollValue() {
        return pollValue;
    }
    public String getRecordValue() {
        return recordValue;
    }
    public String getSendEmail() {
        return sendEmail;
    }
    public String getSendSMS() {
        return sendSMS;
    }
    public String getSubject() {
        return subject;
    }
    public String getTrackable() {
        return trackable;
    }
    public int getHostIndex() {
        return hostIndex;
    }
    public long getJobRuleid() {
        return jobRuleid;
    }
    public String getTrackablePhone() {
        return trackablePhone;
    }
    public String getSendAutoStart() {
        return sendAutoStart;
    }
    public String getTrackablePhoneIp() {
        return trackablePhoneIp;
    }
    public long getCompanyId() {
        return companyId;
    }
    public long getEndDay() {
        return endDay;
    }
    public long getEndTime() {
        return endTime;
    }
    public long getStartDay() {
        return startDay;
    }
    public long getStartTime() {
        return startTime;
    }
    public String getLabelList() {
        return labelList;
    }
    public String getCarrTypeIdList() {
        return carrTypeIdList;
    }  
    public String getSendToPhone() {
        return sendToPhone;
    }
    public String getPortNumList() {
        return portNumList;
    }
}