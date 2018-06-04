/**
 * @file EcaMasterEngine.java
 * @author Zhou Yuanxi
 * @date Feb. 16, 2004 Copyright (c) 2004 TeleVigation, Inc
 */
package fgafa.basic.socket.ecax;

import java.sql.SQLException;
//import java.text.SimpleDateFormat;
import java.util.*;

//import com.televigation.telenavpro.ecax.job.BaseJob;
import fgafa.basic.socket.ecax.job.MonitorJob;
//import com.televigation.telenavpro.filter.DateTimeHelper;
import com.televigation.db.telenavpro.EcaxEngineManager;
import com.televigation.log.TVCategory;

public class EcaMasterEngine extends TimerTask {
    static private EcaMasterEngine instance;

    static private EcaServer ecaServer = null;

    static public TVCategory logger = (TVCategory) TVCategory
            .getInstance("com.televigation.telenavpro.ecax.EcaMasterEngine");

    protected EcaMasterEngine() throws Exception {
        MonitorJob.sendToOperation(
                EcaString.NORMALSTATUS,
                null,
                "EcaMasterEngine start",
                "",
                null
            	);   
    }

//    protected EcaMasterEngine(EcaServer ecaServer) throws Exception {
//        EcaMasterEngine.ecaServer = ecaServer;
//    }

    public static synchronized EcaMasterEngine getInstance() throws Exception {
        if (instance == null) {
            instance = new EcaMasterEngine();
        }
        return instance;
    }

    /**
     * start to monitor slaver
     *  
     */
    private void monitorSlave() {
        logger.fatal("monitorSlave---start  ");

        Timer timer = new Timer();
        EcaMonitorEngine eMon = null;
        try {
            eMon = EcaMonitorEngine.getInstance();
            EcaMonitorEngine.setEcaServer(EcaMasterEngine.ecaServer);
        } catch (Exception e) {
            logger.error("process()", e);
        }
        timer.scheduleAtFixedRate(eMon, (long) EcaServer.lDelay,
                (long) EcaString.INTERVAL_MONITOR);

    }

    private void maintenanceWorkday() {
        //logger.info("maintenanceWorkday start----------");

        //long lSum = 0;
        EcaWorkdayEngine ew = new EcaWorkdayEngine();

        try {
            //do it once firstly
            ew.process(1);
        } catch (Exception e) {
            logger.equals("maintenanceWorkday() " + e.toString());

            MonitorJob.sendToOperation(EcaString.URGENTSTATUS,null,
                    "DB Exception from ECAX", "",e);

            System.exit(-1);
        }

        //periodicity (once per hour)
        Timer timer = new Timer();
        Calendar rightNow = Calendar.getInstance();

        rightNow.add(Calendar.HOUR, 1);
        rightNow.set(Calendar.MINUTE, 1);
        rightNow.set(Calendar.SECOND, 0);
        rightNow.set(Calendar.MILLISECOND, 0);

        //logger.info("maintenanceWorkday: starttime=" + rightNow.getTime());

        ew = new EcaWorkdayEngine();
        timer.scheduleAtFixedRate(ew, rightNow.getTime(),
                (long) EcaString.INTERVAL_WORKDAY);

    }

    /**
     * get a new job for slaver
     * 
     * @param latestJobId
     * @param hostIndex
     * @return
     */
    public EcaMsg getNextJob(long latestJobId, int hostIndex) {
        
        logger.info("getNextJob start: LatestJobId/HostIndex = " + latestJobId + "/" + hostIndex);
         
        EcaMsg ecaMsg = new EcaMsg(EcaString.RESP_NO_JOB);

        synchronized (EcaMasterEngine.ecaServer.jobMutexOfMaster) {
            if (latestJobId != -1L) {
                Long lTmp = new Long(latestJobId);
                //remove the old jobs from vWaitingJobs
                EcaMasterEngine.ecaServer.htWaitingJobs.remove(lTmp);

                //put the old jobs into vFinishedJobs
                EcaMasterEngine.ecaServer.vFinishedJobs.addElement(lTmp);
                //this.ecaServer.vNewJobs.removeElement(lTmp);
            }

            //get the next job
            if (EcaMasterEngine.ecaServer.vNewJobs.size() != 0) {
                Long lJobId = (Long) EcaMasterEngine.ecaServer.vNewJobs
                        .elementAt(0);
                ecaMsg = (EcaMsg) EcaMasterEngine.ecaServer.htJobs.get(lJobId);
                EcaMasterEngine.ecaServer.vNewJobs.remove(0);
                EcaMasterEngine.ecaServer.htWaitingJobs.put(new Long(ecaMsg
                        .getJobId()), String.valueOf(hostIndex));
            }
        }
        
        logger.info( "getNextJob end: msgType/jobId = " +ecaMsg.getMsgType() + "/" + ecaMsg.getJobId ());
         
        return ecaMsg;
    }

    /**
     * fetch new job from tnp_eca_job
     * 
     * @param latestJobId
     */
    private void getNewJobs(long latestJobId) throws Exception{
        logger.info("getNewJobs: LatestJobId = " + latestJobId);
        Vector vReturn = new Vector();
        EcaxEngineManager ecaxM = new EcaxEngineManager();
        Long lTmp = new Long(-1);
        try {
            vReturn = ecaxM.fetchEcaJob(latestJobId);

        } catch (SQLException e) {
            logger.error("getNewJobs ", e);
            throw e;
        }

        //put these jobs into htJobs and vNewJobs
        synchronized (EcaMasterEngine.ecaServer.jobMutexOfMaster) {
            for (int i = 0; i < vReturn.size(); i++) {
                EcaMsg ecaM = (EcaMsg) vReturn.elementAt(i);

                lTmp = new Long(ecaM.getJobId());
                //if(!EcaMasterEngine.ecaServer.htJobs.containsKey(lTmp)){
                    EcaMasterEngine.ecaServer.htJobs.put(lTmp, ecaM);
                	EcaMasterEngine.ecaServer.vNewJobs.addElement(lTmp);
                //}	
            }
        }
        if (lTmp.longValue() != -1)
            EcaMasterEngine.ecaServer.lLatestJobId = lTmp.longValue();
    }

    /**
     * create new jobs and store in tnp_eca_job
     * 
     * @param currentTime
     * @param latestTime
     * @throws Exception
     */
    private void createNewJobs(long currentTime, long latestTime)
            throws Exception {
        EcaxEngineManager em = new EcaxEngineManager();

        try {
            em.createNewJobs(currentTime, latestTime);
            logger.info("createNewJobs: CurrentTime/LatestTime: "
                    + currentTime + "/" + latestTime);

        } catch (SQLException e) {
            logger.error("createNewJobs: CurrentTime/LatestTime: "
                    + currentTime + "/" + latestTime);
            //logger.error("createNewJobs ", e);
            throw e;
        }
    }

    /**
     * change these job's state to 1
     * 
     * @param arrJobId
     * @param currentTime
     */
    private void updateJobsState(Object[] arrJobId, long currentTime) throws Exception{
        logger.info("updateJobsState arrJobId.length = " + arrJobId.length);
        EcaxEngineManager ecaM = new EcaxEngineManager();
        try {
            ecaM.updateEcaJob(arrJobId, currentTime);
        } catch (Exception e) {
            logger.error("setJobsCompleted ", e);
            throw e;
        }
    }

    /**
     * 
     *  
     */
    private void setJobsCompleted() throws Exception{
        logger.info("setJobsCompleted start");
        Object[] arrJobId = null;
        synchronized (EcaMasterEngine.ecaServer.jobMutexOfMaster) {
            arrJobId = EcaMasterEngine.ecaServer.vFinishedJobs.toArray();

            //deleteJob from RAM
            EcaMasterEngine.ecaServer.vFinishedJobs.removeAllElements();

            for (int i = 0; i < arrJobId.length; i++) {
                Long jobId = (Long) arrJobId[i];
                EcaMasterEngine.ecaServer.htJobs.remove(jobId);
            }
        }
        //set the job's state to "completed" in the table
        try{
        if (arrJobId != null && arrJobId.length != 0){
           
	        MonitorJob.sendToOperation(EcaString.NORMALSTATUS, 
	                null,
	                "Report from ECAX:" + arrJobId.length + " jobs have been done in last interval.",
	                "",
	                null);
            
            updateJobsState(arrJobId, EcaServer.getCurrentTime());
        }
            
        }catch(Exception e){
            throw e;
        }
    }

    /**
     * get latestTime from tnp_eca_latesttime
     * 
     * @return
     */
    private long getLatestTime() {
        EcaxEngineManager ecaxM = new EcaxEngineManager();

        try {
            if (EcaMasterEngine.ecaServer.lLatestTime == EcaString.INITMASTERLASTTIME) {
                EcaMasterEngine.ecaServer.lLatestTime = ecaxM.fetchLatestTime();
                long lTmp = EcaMasterEngine.ecaServer.lCurrentTime
                        - EcaMasterEngine.ecaServer.lLatestTime;

                if ((lTmp > EcaString.INTERVAL_MAX) || lTmp < 0) {
                    EcaMasterEngine.ecaServer.lLatestTime = EcaMasterEngine.ecaServer.lCurrentTime;
                    //ecaxManager.updateLatestTime(this.ecaServer.lCurrentTime);
                }
            }

        } catch (Exception e) {
            logger.error("getLatestTime ", e);
        }

        return EcaMasterEngine.ecaServer.lLatestTime;
    }

    /**
     * change latestTime to currentTime
     */
    private void savaLatestTime() {
        EcaxEngineManager ecaxM = new EcaxEngineManager();

        try {
            EcaMasterEngine.ecaServer.lLatestTime = EcaMasterEngine.ecaServer.lCurrentTime;
            //logger.debug("savaLatestTime: " + this.ecaServer.lLatestTime);
            ecaxM.updateLatestTime(null,EcaMasterEngine.ecaServer.lLatestTime);

        } catch (Exception e) {
            logger.error("savaLatestTime ", e);
        }
    }

    public void run() {
        //setJobsCompleted
        try{
            setJobsCompleted();
        } catch(Exception e){
            MonitorJob.sendToOperation(
                    EcaString.URGENTSTATUS,
                    null,
                    "DB Exception from ECAX",
                    "",
                    e
                	);
            
        }
        
        //getCurrentTime
        EcaMasterEngine.ecaServer.lCurrentTime = EcaServer.getCurrentTime();

        //getLatestTime
        EcaMasterEngine.ecaServer.lLatestTime = this.getLatestTime();

        try {
            //createNewJobs and saveLatestTime
            createNewJobs(EcaMasterEngine.ecaServer.lCurrentTime,
                    EcaMasterEngine.ecaServer.lLatestTime);

        } catch (Exception e) {
            logger.error("createNewJobs ", e);
            MonitorJob.sendToOperation(
                    EcaString.URGENTSTATUS,
                    null,
                    "DB Exception from ECAX",
                    "",
                    e
                	);            
        }
        //update LatestTime
        EcaMasterEngine.ecaServer.lLatestTime = EcaMasterEngine.ecaServer.lCurrentTime;

        //getNewJobs
        try{
            getNewJobs(EcaMasterEngine.ecaServer.lLatestJobId);
        }catch(Exception e){
            logger.error("getNewJobs ", e);
            MonitorJob.sendToOperation(
                    EcaString.URGENTSTATUS,
                    null,
                    "DB Exception from ECAX",
                    "",
                    e
                	);               
        }

    }

    /**
     * start master engine
     *  
     */
    public void process() {
        logger.fatal("process start =====");
        
        //maintenance Workday
        maintenanceWorkday();

        //getLatestJobId
        //this.iLatestJobId = getLatestJobId();

        //periodicity
        EcaMasterEngine ecaMasterEngine = null;
        try {
            ecaMasterEngine = EcaMasterEngine.getInstance();
        } catch (Exception e) {
            logger.error("process()", e);
        }

        Timer timer = new Timer();

        //this.FIRSTTIME = this.lLatestTime + this.INTERVAL ;
        timer.scheduleAtFixedRate(ecaMasterEngine, (long) EcaServer.lDelay,
                (long) EcaServer.lInterval);

        //monitor slave
        monitorSlave();
    }

    /**
     * @return Returns the ecaServer.
     */
    public static EcaServer getEcaServer() {
        return ecaServer;
    }

    /**
     * @param ecaServer
     *            The ecaServer to set.
     */
    public static void setEcaServer(EcaServer ecaServer) {
        EcaMasterEngine.ecaServer = ecaServer;
    }

}