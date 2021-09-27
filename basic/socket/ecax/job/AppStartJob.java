/**
 * @file AppStartJob.java
 * @author Zhou Yuanxi
 * @date Feb. 16, 2004 Copyright (c) 2004 TeleVigation, Inc
 */
package basic.socket.ecax.job;

import basic.socket.ecax.EcaMsg;
import com.televigation.log.TVCategory;

public class AppStartJob extends BaseJob implements EcaJob
{
  public static TVCategory log = (TVCategory) TVCategory
      .getInstance("com.televigation.telenavpro.ecax.AppStartJob");

  private static AppStartJob instance;



  //private static EcaMsg ecaMsg = null;

  private AppStartJob() throws Exception {
  }



  private AppStartJob(EcaMsg msg) {
    this.ecaMsg = msg;
  }



  public static synchronized AppStartJob getInstance(EcaMsg msg)
      throws Exception {
    if (instance == null) {
      instance = new AppStartJob(msg);
    }
    instance.ecaMsg = msg;
    return instance;
  }



  public void process() throws Exception {
    log.debug("start: jobid = " + this.ecaMsg.getJobId());

    if (this.ecaMsg == null) {
      log.error("AppStartJob received the ecaMsg is null!");
      throw new Exception("AppStartJob received the ecaMsg is null!");
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

      //send autostart
      sendAutoStart(this.ecaMsg);

      //send job
      sendAlertMsg(this.ecaMsg, subject, sSMSBody);

    }
    catch (Exception e) {
      throw e;
    }

  }

}