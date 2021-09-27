/**
 * @file EcaServer.java
 * @author Zhou Yuanxi
 * @date Feb. 16, 2004 Copyright (c) 2004 TeleVigation, Inc
 */
package basic.socket.ecax;

import java.net.*;
import java.util.*;
import java.sql.*;

import com.televigation.db.DbUtil;
import com.televigation.util.Configurable;
//import com.televigation.util.Global;
//import com.televigation.util.Timer;

import com.televigation.log.TVCategory;
import com.televigation.db.telenavpro.EcaxEngineManager;
import com.televigation.db.telenavpro.TnpBaseManager;

import com.televigation.telenavpro.ecax.job.MonitorJob;

public class EcaServer extends TnpBaseManager implements CommHandler {
	//final static public String CONFIG_BUNDLE = "com.televigation.telenavpro.EcaConfig";
	final static public String CONFIG_BUNDLE = "EcaConfig.properties";

	static public TVCategory logger =
		(TVCategory) TVCategory.getInstance(
			"com.televigation.telenavpro.ecax.EcaServer");

	//static public ResourceBundle cfg = null;
	private static Configurable cfg = null;

	static private EcaServer instance;

	public static long lInterval = 180000; //3 * 60 * 1000;
	public static long lDelay = 10;

	public long lLatestJobId = -1L;
	//latest validable jobId (for master and slave)
	public long lLastTimeJobId = -1L; //lasttime jobId (for slave)
	public int hostIndex = -1; //store itself index in the file
	public int masterIndex = -1; //store the master index in the table
	public long lLatestTime = EcaString.INITMASTERLASTTIME;
	public long lCurrentTime = 0L;
	public int iCount = EcaString.INITCOUNT;

	private Object commMutex = new Object();
	public Object jobMutexOfMaster = new Object();
	public EcaHost[] hostTable; //store all host in the file
	public Vector replies = null;
	public Hashtable htJobs = new Hashtable();
	// key/value == Long(lJobId)/EcaMsg
	public Vector vNewJobs = new Vector(); //Long(lJobId)
	public Hashtable htWaitingJobs = new Hashtable();
	//Long(lLatestJobId)/String(slaveHostIndex)
	public Vector vFinishedJobs = new Vector(); //Long(lJobId)
	public Vector vMonitorJobs = new Vector(); //Long(lJobId)

	static {
		try {
			//cfg = ResourceBundle.getBundle(CONFIG_BUNDLE);
			cfg = new Configurable(CONFIG_BUNDLE);
		} catch (Exception e) {
			logger.debug("EcaHost::Static block: ", e);
		}
	}

	public EcaServer() throws Exception {

	}

	static public EcaServer getInstance() throws Exception {
		if (instance == null) {
			instance = new EcaServer();
		}
		return instance;
	}

	/**
	 * load the host from EcaConfig.properties
	 * @throws Exception
	 */
	public void loadHostTable() throws Exception {
		String strIP = InetAddress.getLocalHost().getHostAddress();
		logger.fatal("========this host's IP============" + strIP);

		int nPort = Integer.parseInt(cfg.getProperty("PORT").trim());
		int timeout = Integer.parseInt(cfg.getProperty("TIMEOUT").trim());
		//int nPort = Integer.parseInt(cfg.getString("PORT"));
		//int timeout = Integer.parseInt(cfg.getString("TIMEOUT"));

		Vector v = new Vector();
		for (int i = 0; i >= 0; i++) {
			String str = null;
			try {
				str = cfg.getProperty("HOST_" + i).trim();
				//str = cfg.getString("HOST_"+i);
			} catch (Exception e) {

			}
			if (str == null || str.length() == 0)
				break;

			v.addElement(str);
			if (hostIndex == -1 && str.equals(strIP)) {
				this.hostIndex = i;
				logger.fatal("This host index: " + hostIndex);
			}
		}

		if (this.hostIndex == -1) {
			logger.error("This host's IP isn't in the file!");
			System.exit(-1);
		}

		this.hostTable = new EcaHost[v.size()];
		for (int i = 0; i < v.size(); i++) {
			String str = (String) v.elementAt(i);
			this.hostTable[i] = new EcaHost(str, nPort, timeout, i);
		}
	}

	/**
	 * init masterIndex if the existed masterIndex of tnp_eca_masterindex is
	 * equal to -1 then change it to itself's hostIndex else change itself's
	 * masterIndex to the existed masterIndex
	 * 
	 * @return masterIndex
	 * @throws Exception
	 */
	public int initMasterIndex() throws Exception {
		Connection cn = null;
		int oldMasterIndex = -1;

		try {
			cn = getPnaConnection();
			cn.setAutoCommit(false);

			EcaxEngineManager ecaxM = new EcaxEngineManager();
			logger.fatal("initMasterIndex==start lock masterindex table==");
			//lock the table
			ecaxM.lockMasterIndex(cn);

			//get the masterIndex from the table
			oldMasterIndex = ecaxM.fetchMasterIndex(cn);
			logger.fatal("initMasterIndex==oldMasterIndex:" + oldMasterIndex);
			if (oldMasterIndex == -1) {
				ecaxM.updateMasterIndex(cn, this.hostIndex);
				this.masterIndex = this.hostIndex;
			} else {
				this.masterIndex = oldMasterIndex;
			}

			cn.commit();
		} catch (Exception e) {
			if (cn != null)
				cn.rollback();
			logger.debug("initMasterIndex: " + e.toString());
		} finally {
			DbUtil.cleanUp(cn);
		}
		logger.fatal("initMasterIndex==this.masterIndex :" + this.masterIndex);
		return this.masterIndex;
	}

	/**
	 * get masterIndex if the existed masterIndex of tnp_eca_masterindex is
	 * equal to itself's masterIndex then change it to itself's hostIndex else
	 * change itself's masterIndex to the existed masterIndex
	 * 
	 * @return masterIndex
	 * @throws SQLException
	 */
	public int getMasterIndex() throws SQLException {
		Connection cn = null;
		int oldMasterIndex = -1;

		try {
			cn = getPnaConnection();
			cn.setAutoCommit(false);

			EcaxEngineManager ecaxM = new EcaxEngineManager();
			//lock the table
			ecaxM.lockMasterIndex(cn);

			//get the masterIndex from the table
			oldMasterIndex = ecaxM.fetchMasterIndex(cn);

			if (oldMasterIndex == this.masterIndex) {
				ecaxM.updateMasterIndex(cn, this.hostIndex);
				this.masterIndex = this.hostIndex;
			} else {
				this.masterIndex = oldMasterIndex;
			}

			cn.commit();
		} catch (Exception e) {
			if (cn != null)
				cn.rollback();
			logger.debug("getMasterIndex: " + e.toString());

		} finally {
			DbUtil.cleanUp(cn);
		}

		logger.fatal("getMasterIndex===this.masterIndex :" + this.masterIndex);
		return this.masterIndex;
	}

	public long getLatestTime() {
		long lReturn = -1L;
		EcaxEngineManager ecaxM = new EcaxEngineManager();
		try {
			lReturn = ecaxM.fetchLatestTime();
		} catch (Exception e) {
			logger.error("getLatestTime ", e);
		}

		return lReturn;
	}

	public void clientCallback(EcaMsg resp, EcaHost host, EcaMsg msg) {

		EcaReply reply = new EcaReply(host, resp);
		synchronized (commMutex) {
			this.replies.addElement(reply);
		}

	}

	public EcaMsg serverCallback(EcaMsg req) throws Exception {

		int type = req.getMsgType();

		logger.debug(
			"serverCallback msgType--hostIndex " + type + "--" + req.getHostIndex());

		switch (type) {
			case EcaString.REQ_LATESTJOBID :
				{
					return new EcaMsg(EcaString.REQ_LATESTJOBID, this.lLatestJobId);
				}
			case EcaString.REQ_NEXTJOB :
				{
					logger.debug("serverCallback: let me fetch a new job for slave");
					//return getNextJob();
					return (EcaMasterEngine.getInstance()).getNextJob(
						req.getJobId(),
						req.getHostIndex());
				}
			case EcaString.REQ_EXIT_COMMAND :
				{
					return new EcaMsg(EcaString.RESP_ACCEPTED_EXIT);
				}
		}

		return new EcaMsg(EcaString.RESP_NO_REPLY);
	}

	public static synchronized long getCurrentTime() {
		return System.currentTimeMillis();
	}

	public void shutdown(int status) {
		//if it's master, bak current jobs/status
		if (this.masterIndex == this.hostIndex) {

		}
		//exit
		System.exit(status);
	}

	/**
	 * 
	 * @author yxzhou
	 */
	public void process() throws Exception {

		// start socket server
		EcaSocketServer s =
			new EcaSocketServer(hostTable[this.hostIndex].getHostPort(), this);
		Thread t = new Thread(s);
		t.start();

		try {
			logger.fatal("------sleep a while to wait serversock------ 50 ");
			Thread.sleep(50);
		} catch (InterruptedException e) {
			logger.error("sleep a while to wait serversock", e);
			throw e;
		}

		try {
			//getMsterIndex
			this.masterIndex = initMasterIndex();
		} catch (Exception e2) {
			logger.error("initMasterIndex", e2);
			throw e2;
		}

		logger.fatal("INTERVAL = " + EcaServer.lInterval);
		logger.fatal("DELAY = " + EcaServer.lDelay);

		a : while (true) {
			if (this.masterIndex == this.hostIndex) {
				//start masterEngine
				try {
					EcaMasterEngine.setEcaServer(this);
					EcaMasterEngine.getInstance().process();
					break a;

				} catch (Exception e) {
					logger.error("start masterEngine:", e);
					throw e;
				}
			} else {
				//start slaveEngine

				try {
					//EcaSlaveEngine ecaSlaveEngine =
					// EcaSlaveEngine.getInstance();
					EcaSlaveEngine.setEcaServer(this);
					EcaSlaveEngine.getInstance().process();

				} catch (Exception e) {
					logger.error("start slaveEngine:", e);
					throw e;
				}
			}

			//             try { 
			//             	logger.debug("------sleep------ 10 "); 
			//                Thread.sleep(10); }
			//             catch(InterruptedException e) 
			//             { 
			//               	e.printStackTrace(); 
			//             }

		}

	}

	static public void main(String args[]) {
		try {
			EcaServer server = new EcaServer();

			//loadHostTable
			try {
				server.loadHostTable();
			} catch (Exception e1) {
				logger.error("loadHostTable:", e1);
				throw e1;
			}

			//if receive shutdown command 
			try {
				if (args.length == 1 && "shutdown".equalsIgnoreCase(args[0].trim())) {

					server.replies = new Vector();

					EcaSocketClient.send(
						server.hostTable[server.hostIndex],
						new EcaMsg(EcaString.REQ_EXIT_COMMAND),
						server);

					try {
						logger.info("------sleep------ 1000 ");
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}

					MonitorJob.sendToOperation(
						EcaString.NORMALSTATUS,
						null,
						"ECA engine shutdown",
						"",
						null);

					System.exit(-1);
				}
			} catch (Exception e1) {
				logger.error("shutdown:", e1);
			}

			if (args.length != 2)
				logger.fatal("Run as default value INTERVAL = " + EcaServer.lInterval);
			else {
				EcaServer.lInterval = Long.parseLong(args[0]);
				EcaServer.lDelay = Long.parseLong(args[1]);
			}

			//start up statechecking service
			MonitorJob.startupStateChecking();

			server.process();
		} catch (Exception e) {
			logger.debug("EcaServer::main() - exception:", e);

			MonitorJob.sendToOperation(
				EcaString.URGENTSTATUS,
				null,
				"Exception from ECAX",
				"",
				e);

			System.exit(-1);
		}
	}

}