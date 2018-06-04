/**
 * @file InitMasterIndex.java
 * @author Zhou Yuanxi
 * @date Apr. 6, 2004 Copyright (c) 2004 TeleVigation, Inc
 */
package fgafa.basic.socket.ecax;

import java.sql.*;

import com.televigation.db.DbUtil;
import com.televigation.log.TVCategory;
//import com.televigation.util.Global;
import com.televigation.db.telenavpro.EcaxEngineManager;
import com.televigation.db.telenavpro.TnpBaseManager;

public class InitMasterIndex extends TnpBaseManager {
    static public TVCategory logger = (TVCategory) TVCategory
            .getInstance("com.televigation.telenavpro.ecax.InitMasterIndex");

    static private InitMasterIndex instance;

    public InitMasterIndex() throws Exception {

    }

    static public InitMasterIndex getInstance() throws Exception {
        if (instance == null) {
            instance = new InitMasterIndex();
        }
        return instance;
    }

    public void process(boolean reStart) throws Exception {
        Connection cn = null;
        try {
            cn = getPnaConnection();
            cn.setAutoCommit(false);

            EcaxEngineManager ecaxM = new EcaxEngineManager();

            //lock the table
            //ecaxManager.lockMasterIndex(cn);

            //set the masterIndex -1
            //because here it doesn't know if the masterindex existed
            //ecaxManager.updateMasterIndex(cn, -1);
            ecaxM.deleteMasterIndex(cn);
            ecaxM.insertMasterIndex(cn, -1);

            if (reStart) {
                ecaxM.deleteEcaJob(cn);
                ecaxM.updateLatestTime(cn, 0);
            }

            cn.commit();
        } catch (Exception e) {
        	if(cn!=null)
        		cn.rollback();
            logger.debug("process: " + e.toString());
        } finally {
            DbUtil.cleanUp(cn);
        }
    }

    static public void main(String args[]) {
        boolean bReStart = false;
        try {
            if (args.length > 1) {
                logger.debug("Your arguments are wrong.");
                System.exit(-1);
            } else if (args.length == 1) {
                bReStart = true;
            }

            InitMasterIndex server = new InitMasterIndex();
            server.process(bReStart);

            System.exit(-1);
        } catch (Exception e) {
            logger.debug("EcaServer::main() - exception:", e);
        }
    }

}