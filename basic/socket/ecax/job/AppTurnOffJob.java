/**
 * @file AppTurnOffJob.java
 * @author Zhou Yuanxi
 * @date Nov. 29, 2004 Copyright (c) 2004 TeleVigation, Inc
 */
package basic.socket.ecax.job;

import basic.socket.ecax.EcaMsg;
import com.televigation.log.TVCategory;

public class AppTurnOffJob extends BaseJob implements EcaJob
{
  public static TVCategory log = (TVCategory) TVCategory
      .getInstance("com.televigation.telenavpro.ecax.AppTurnOffJob");

  private static AppTurnOffJob instance;



  //private static EcaMsg ecaMsg = null;

  //private AppTurnOffJob() throws Exception {}

  private AppTurnOffJob(EcaMsg ecaMsg) {
    this.ecaMsg = ecaMsg;
  }



  public static synchronized AppTurnOffJob getInstance(EcaMsg ecaMsg)
      throws Exception {
    if (instance == null) {
      instance = new AppTurnOffJob(ecaMsg);
    }
    instance.ecaMsg = ecaMsg;
    return instance;
  }



  public void process() throws Exception {
    log.debug("start: jobid = " + this.ecaMsg.getJobId());

    if (this.ecaMsg == null) {
      log.error("AppTurnOffJob received the ecaMsg is null!");
      throw new Exception("AppTurnOffJob received the ecaMsg is null!");
      //return;
    }

    //set content
    String subject = BaseJob.getSubject(this.ecaMsg);
    String bodyAll = BaseJob.getEmailBody(this.ecaMsg);
    String sSMSBody = BaseJob.getSmsBody(this.ecaMsg);

    try {
      //send email
      sendMail(this.ecaMsg, subject, bodyAll);

      //send sms
      sendSMS(this.ecaMsg, subject, sSMSBody);

      //send to phone
      sendAlertMsg(this.ecaMsg, subject, sSMSBody);

    }
    catch (Exception e) {
      throw e;
    }

  }

}