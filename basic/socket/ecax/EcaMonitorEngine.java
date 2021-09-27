/**
 * @file EcaMonitorEngine.java
 * @author Zhou Yuanxi
 * @date May. 26, 2004 Copyright (c) 2004 TeleVigation, Inc
 */
package basic.socket.ecax;

import java.util.*;

import com.televigation.log.TVCategory;
import basic.socket.ecax.job.MonitorJob;

public class EcaMonitorEngine extends TimerTask {
    static private EcaMonitorEngine instance;

    static private EcaServer ecaServer = null;

    static public TVCategory logger = (TVCategory) TVCategory
            .getInstance("com.televigation.telenavpro.ecax.EcaMonitorEngine");

    protected EcaMonitorEngine() throws Exception {

    }

    public static synchronized EcaMonitorEngine getInstance() throws Exception {
        if (instance == null) {
            instance = new EcaMonitorEngine();
        }
        return instance;
    }

    public void run() {
        logger.info("run start");

        synchronized (EcaMonitorEngine.ecaServer.jobMutexOfMaster) {
            //check
            for (int i = 0; i < EcaMonitorEngine.ecaServer.vMonitorJobs.size(); i++) {
                Long lTmp = (Long) EcaMonitorEngine.ecaServer.vMonitorJobs
                        .elementAt(i);
                if (EcaMonitorEngine.ecaServer.htWaitingJobs.containsKey(lTmp)) {
                    String sHostIndex = (String) EcaMonitorEngine.ecaServer.htWaitingJobs
                            .get(lTmp);
                    MonitorJob
                            .sendAlertByM(
                                    EcaMonitorEngine.ecaServer.hostTable[EcaMonitorEngine.ecaServer.masterIndex]
                                            .getHostIP(),
                                    EcaMonitorEngine.ecaServer.hostTable[Integer
                                            .parseInt(sHostIndex)].getHostIP(),
                                    (EcaMsg) EcaMonitorEngine.ecaServer.htJobs
                                            .get(lTmp));

                    EcaMonitorEngine.ecaServer.vNewJobs.add(0, lTmp);
                    EcaMonitorEngine.ecaServer.htWaitingJobs.remove(lTmp);
                }
            }
            //update the monitor vector
            EcaMonitorEngine.ecaServer.vMonitorJobs.removeAllElements();
            for (Enumeration e = EcaMonitorEngine.ecaServer.htWaitingJobs
                    .keys(); e.hasMoreElements();) {
                EcaMonitorEngine.ecaServer.vMonitorJobs.add((Long) e
                        .nextElement());
            }

        }
    }

    public void process() {
        logger.info("process start =====");

    }

    /**
     * @param ecaServer
     *          The ecaServer to set.
     */
    public static void setEcaServer(EcaServer ecaServer) {
        EcaMonitorEngine.ecaServer = ecaServer;
    }

}