/**
 * @file EcaMasterEngine.java
 * @author Zhou Yuanxi
 * @date Feb. 16, 2004 Copyright (c) 2004 TeleVigation, Inc
 */
package fgafa.basic.socket.ecax;

import java.util.*;

import com.televigation.log.TVCategory;
import fgafa.basic.socket.ecax.job.MonitorJob;
import com.televigation.telenavpro.filter.DateTimeHelper;
import com.televigation.db.telenavpro.EcaxWorkDayManager;
import com.televigation.telenavpro.filter.TimeZoneTypeDef;

public class EcaWorkdayEngine extends TimerTask {

    static public TVCategory logger = (TVCategory) TVCategory
            .getInstance("com.televigation.telenavpro.ecax.EcaWorkdayEngine");

    public EcaWorkdayEngine() {

    }

    /**
     * @param state
     *            is 1 then all zone must do it firstly; else do it when the
     *            hour of zone is 00
     */
    public void process(int state) {
        logger.info("process start: state = " + state);

        //check currentHour, calculate which time_zone_id must be start
        // maintenance.
        Calendar defaultCal = Calendar.getInstance();

        EcaxWorkDayManager exM = new EcaxWorkDayManager();
        try {
            long lTime = EcaServer.getCurrentTime(); //System.currentTimeMillis();

            for (int i = 1; i <= TimeZoneTypeDef.LAST_TYPE; i++) {

                String strHour = DateTimeHelper.dateTime(lTime, "HH", i);
                logger.info("process: strHour = " + strHour);

                defaultCal = Calendar.getInstance();
                TimeZone timeZone = TimeZoneTypeDef.getTimeZone(i);
                defaultCal.setTimeZone(timeZone);

                if (state == 1 || strHour.equals("00")) {
                    exM.maintenanceWorkday(defaultCal, i);
                }
            }
        } catch (Exception e) {
            logger.error("process() ", e);
            
            MonitorJob.sendToOperation(
                    EcaString.URGENTSTATUS,
                    null,
                    "Exception from ECAX",
                    "",
                    e
                	);

            System.exit(-1);            
        }
    }

    public void run() {
        //logger.debug("----------- run() start-------------- ");
        process(0);
    }

}