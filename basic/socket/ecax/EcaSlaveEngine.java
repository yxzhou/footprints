/**
 * @file EcaSlaveEngine.java
 * @author Zhou Yuanxi
 * @date Feb. 16, 2004 Copyright (c) 2004 TeleVigation, Inc
 */
package basic.socket.ecax;

import java.sql.SQLException;
import java.util.Vector;

import com.televigation.db.telenavpro.EcaxEngineManager;
import com.televigation.log.TVCategory;
import basic.socket.ecax.job.*;

public class EcaSlaveEngine {
    static private EcaServer ecaServer = null;

    static private EcaSlaveEngine instance;

    static public TVCategory logger = (TVCategory) TVCategory
            .getInstance("com.televigation.telenavpro.ecax.EcaSlaveEngine");

    protected EcaSlaveEngine() throws Exception {
        MonitorJob.sendToOperation(
                EcaString.NORMALSTATUS,
                null,
                "EcaSlaveEngine start",
                "",
                null
            	);
    }

    public static synchronized EcaSlaveEngine getInstance() throws Exception {
        if (instance == null) {
            instance = new EcaSlaveEngine();
        }
        return instance;
    }

    /**
     * get latestJobId from all other slaver
     */
    private long getLatestJobId() {
        EcaSlaveEngine.ecaServer.replies = new Vector();

        int repliesSize = EcaSlaveEngine.ecaServer.hostTable.length - 1;
        for (int i = 0; i < EcaSlaveEngine.ecaServer.hostTable.length; i++) {
            if (i != EcaSlaveEngine.ecaServer.hostIndex)
                EcaSocketClient.send(EcaSlaveEngine.ecaServer.hostTable[i],
                        new EcaMsg(EcaString.REQ_LATESTJOBID),
                        EcaSlaveEngine.ecaServer);
        }

        // wait here for all messages to come in
        int j = 0;
        while (EcaSlaveEngine.ecaServer.replies.size() < repliesSize && j < 5) {
            try {
                Thread.sleep(100);
                j++;
            } catch (InterruptedException e) {
                logger.error("getLatestJobId: ", e);
            }
        }

        logger.info("getLatestJobId replies.size="
                + EcaSlaveEngine.ecaServer.replies.size());

        long mIndex = -1;
        long respIndex = 0;
        for (int i = 0; i < EcaSlaveEngine.ecaServer.replies.size(); i++) {
            EcaReply reply = (EcaReply) EcaSlaveEngine.ecaServer.replies
                    .elementAt(i);
            EcaMsg ecaMsg = reply.getMsg();
            if (ecaMsg.getMsgType() == EcaString.RESP_LATESTJOBID) {
                respIndex = ecaMsg.getJobId();
                mIndex = (mIndex > respIndex) ? mIndex : respIndex;
            }
        }
        logger.info("getLatestJobId " + mIndex);
        
        EcaSlaveEngine.ecaServer.replies = null;
        return mIndex;
    }

    /**
     * @param lLatestJobId
     */
    private void reNewJobs(long lLatestJobId) throws Exception{
        logger.info("reNewJobs(lLatestJobId)  " + lLatestJobId);
        Vector vReturn = new Vector();
        EcaxEngineManager ecaxM = new EcaxEngineManager();
        try {
            vReturn = ecaxM.fetchEcaJob(-1);

        } catch (SQLException e) {
            logger.error("getNewJobs ", e);
            throw e;
        }

        //put these jobs into htJobs and vNewJobs
        synchronized (EcaSlaveEngine.ecaServer.jobMutexOfMaster) {
            for (int i = 0; i < vReturn.size(); i++) {
                EcaMsg ecaM = (EcaMsg) vReturn.elementAt(i);

                EcaSlaveEngine.ecaServer.htJobs.put(new Long(ecaM.getJobId()),
                        ecaM);
                if (lLatestJobId >= ecaM.getJobId())
                    EcaSlaveEngine.ecaServer.vFinishedJobs.addElement(new Long(
                            ecaM.getJobId()));
                else
                    EcaSlaveEngine.ecaServer.vNewJobs.addElement(new Long(ecaM
                            .getJobId()));
            }
        }
    }

    /**
     * start slaver
     *  
     */
    public void process() {
        //logger.debug("process start");
        
        EcaMsg resp = EcaSocketClient
                .connect(
                        EcaSlaveEngine.ecaServer.hostTable[EcaSlaveEngine.ecaServer.masterIndex],
                        new EcaMsg(EcaString.REQ_NEXTJOB,
                                EcaSlaveEngine.ecaServer.hostIndex,
                                EcaSlaveEngine.ecaServer.lLastTimeJobId), 10);

        if (resp.getMsgType() == EcaString.RESP_NEXTJOB) {
            //do the job
            try {
                //logger.debug("let me do the job");

                DoJob doJob = new DoJob();
                BaseJob baseJob = doJob.getJob(resp);

                EcaSlaveEngine.ecaServer.lLatestJobId = resp.getJobId();
                EcaSlaveEngine.ecaServer.lLastTimeJobId = EcaSlaveEngine.ecaServer.lLatestJobId;
                EcaSlaveEngine.ecaServer.iCount = EcaString.INITCOUNT;

                baseJob.process();

            } catch (Exception e) {
                logger.error("EcaSlavaEngine: process: RESP_NEXTJOB:", e);

                MonitorJob.sendToOperation(
                        EcaString.EXCEPTIONSTATUS,
                        null,
                        "ECAXJOB Exception",
                        "The jobId is " + EcaSlaveEngine.ecaServer.lLatestJobId,
                        e
                    	);    
            }
        } else if (resp.getMsgType() == EcaString.RESP_NO_JOB) {
            try {
                //logger.debug("let me sleep a while,then get a job again");
                Thread.sleep(EcaString.WAITINGJOBINTERVAL);

                EcaSlaveEngine.ecaServer.lLastTimeJobId = -1L;
                EcaSlaveEngine.ecaServer.iCount = EcaString.INITCOUNT;
            } catch (InterruptedException e) {
                logger.error("EcaSlavaEngine: process: RESP_NO_JOB: ", e);
            }
        } else {
            MonitorJob.sendAlertByS(
                    null,
                    EcaSlaveEngine.ecaServer.hostTable[EcaSlaveEngine.ecaServer.masterIndex].getHostIP(),
                    EcaSlaveEngine.ecaServer.hostTable[EcaSlaveEngine.ecaServer.hostIndex].getHostIP());

            //i Count
            if (EcaSlaveEngine.ecaServer.iCount < 5) {
                //when iCount == 0, get the lastTime
                if (EcaSlaveEngine.ecaServer.iCount == EcaString.INITCOUNT)
                    EcaSlaveEngine.ecaServer.lLatestTime = EcaSlaveEngine.ecaServer
                            .getLatestTime();
                EcaSlaveEngine.ecaServer.iCount++;
                return;
            }

            EcaSlaveEngine.ecaServer.iCount = EcaString.INITCOUNT;
            //when the slave cann't connect master,and the master can connect DB, sending alert
            if (EcaSlaveEngine.ecaServer.getLatestTime() == EcaSlaveEngine.ecaServer.lLatestTime) {
                MonitorJob
                        .sendAlertByS(
                                "ECAX Slaver timed out connecting to ECAX Master, while the Master Server can connect to DB Server properly",
                                EcaSlaveEngine.ecaServer.hostTable[EcaSlaveEngine.ecaServer.masterIndex].getHostIP(),
                                EcaSlaveEngine.ecaServer.hostTable[EcaSlaveEngine.ecaServer.hostIndex].getHostIP());
                return;
            }
            
            // master is down - alert and elect new master
            logger.fatal("let's vote a new master");
            //alert
            MonitorJob
                    .sendAlertByS(
                            "ECAX Master is not working properly, and a new master will appear",
                            EcaSlaveEngine.ecaServer.hostTable[EcaSlaveEngine.ecaServer.masterIndex].getHostIP(),
                            EcaSlaveEngine.ecaServer.hostTable[EcaSlaveEngine.ecaServer.hostIndex].getHostIP());

            //elect new master
            try {
                //setLatestTime
                EcaSlaveEngine.ecaServer.lLatestTime = EcaString.INITSLAVERLASTTIME;

                EcaSlaveEngine.ecaServer.masterIndex = EcaSlaveEngine.ecaServer
                        .getMasterIndex();
            } catch (SQLException e) {
                logger.error("getMasterIndex: ", e);
                MonitorJob.sendToOperation(
                        EcaString.EXCEPTIONSTATUS,
                        null,
                        "DB Exception from ECAX",
                        "",
                        e
                    	);                    
            }
            //if I become a master,
            if (EcaSlaveEngine.ecaServer.masterIndex == EcaSlaveEngine.ecaServer.hostIndex) {
                ///then need to get the LatestJobId
                EcaSlaveEngine.ecaServer.lLatestJobId = this.getLatestJobId();
                //and renew the jobs
                try{
                    this.reNewJobs(EcaSlaveEngine.ecaServer.lLatestJobId);
                }catch(Exception e){
                    logger.error("reNewJobs: ",e);
                    MonitorJob.sendToOperation(
                            EcaString.EXCEPTIONSTATUS,
                            null,
                            "DB Exception from ECAX",
                            "",
                            e
                        	);                     
                }
                
            }

        }
    }

    /**
     * @return Returns the ecaServer.
     */
    public static EcaServer getEcaServer() {
        return ecaServer;
    }

    /**
     * @param ecaServer
     *          The ecaServer to set.
     */
    public static void setEcaServer(EcaServer ecaServer) {
        EcaSlaveEngine.ecaServer = ecaServer;
    }

}