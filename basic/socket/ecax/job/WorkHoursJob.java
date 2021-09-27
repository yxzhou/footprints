/**
 * @file WorkHoursJob.java
 * @author Zhou Yuanxi
 * @date Feb. 16, 2004 Copyright (c) 2004 TeleVigation, Inc
 */
package basic.socket.ecax.job;

import java.sql.SQLException;
import java.util.Collections;
import java.util.StringTokenizer;
import java.util.Vector;

import basic.socket.ecax.EcaMsg;
import com.televigation.db.telenavpro.TrackableManager;
import com.televigation.log.TVCategory;

//import com.televigation.telenavpro.servlet.TeleNavProStrings;

public class WorkHoursJob extends BaseJob implements EcaJob
{

  public static TVCategory log = (TVCategory) TVCategory
      .getInstance("com.televigation.telenavpro.ecax.WorkHoursJob");

  private static WorkHoursJob instance;



  //private static EcaMsg ecaMsg = null;

  //private WorkHoursJob() {}

  private WorkHoursJob(EcaMsg ecaMsg) {
    this.ecaMsg = ecaMsg;
  }



  public static synchronized WorkHoursJob getInstance(EcaMsg ecaMsg)
      throws Exception {
    if (instance == null) {
      instance = new WorkHoursJob(ecaMsg);
    }
    instance.ecaMsg = ecaMsg;
    return instance;
  }



  public void process() throws Exception {
    log.debug("WorkHoursJob start: jobid = " + this.ecaMsg.getJobId());
    if (this.ecaMsg == null) {
      log.error("WorkHoursJob received the ecaMsg is null!");
      throw new Exception("WorkHoursJob received the ecaMsg is null!");
      //return;
    }

    Vector vTmp = new Vector();
    StringTokenizer st = new StringTokenizer(this.ecaMsg.getTrackable(), ",");
    while (st.hasMoreTokens()) {
      vTmp.addElement(Long.valueOf(st.nextToken()));
    }
    Collections.sort(vTmp);
    TrackableManager tm = new TrackableManager();
    try {
      tm.updateIntervals(vTmp, Long.parseLong(this.ecaMsg.getPollValue()), Long
          .parseLong(this.ecaMsg.getRecordValue()));
    }
    catch (NumberFormatException e) {
      log.error("WorkHoursJob:", e);
      throw e;
    }
    catch (SQLException e) {
      log.error("WorkHoursJob:", e);
      throw e;
    }

  }

}