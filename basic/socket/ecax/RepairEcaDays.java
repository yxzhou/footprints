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
import com.televigation.telenavpro.pc.EcaPC;
import com.televigation.db.telenavpro.EcaxManager;
import com.televigation.db.telenavpro.EcaxWorkDayManager;
import com.televigation.db.telenavpro.TnpBaseManager;

public class RepairEcaDays extends TnpBaseManager {
    static public TVCategory logger = (TVCategory) TVCategory
            .getInstance("com.televigation.telenavpro.ecax.InitMasterIndex");

    static private RepairEcaDays instance;

    public RepairEcaDays() throws Exception {

    }

    static public RepairEcaDays getInstance() throws Exception {
        if (instance == null) {
            instance = new RepairEcaDays();
        }
        return instance;
    }

    public void process(boolean reStart) throws Exception {
        Connection cn = null;
        try {
            cn = getPnaConnection();
            cn.setAutoCommit(false);

            EcaxManager ecaxManager = new EcaxManager();
            EcaxWorkDayManager ecaxWorkdayManager = new EcaxWorkDayManager();
            //deleteAllTnp_eca_day 
            long lReturn = ecaxWorkdayManager.deleteTnp_eca_dayByDeleted(cn,EcaxWorkDayManager.DELETED);
            System.out.println("deleteTnp_eca_dayByDeleted" + lReturn);

            //workinghour 
            ecaxWorkdayManager.deleteTnp_eca_dayByEcaRuleTypeId(cn, 
                    EcaPC.ECA_WORKINGHOUR_KEY, 
                    Integer.parseInt(EcaPC.RECURTYPEDAILY ));
            
            ecaxWorkdayManager.insertEcaDayOfInterval(cn);
            System.out.println("workinghour");
            //nocomm 
            ecaxWorkdayManager.deleteTnp_eca_dayByEcaRuleTypeId(cn, 
                    EcaPC.ECA_NOCOMM_KEY, 
                    Integer.parseInt(EcaPC.RECURTYPEWEEKLY ));
            ecaxWorkdayManager.insertEcaDayOfNoComm(cn);
            System.out.println("nocomm");
            //geofence
            ecaxWorkdayManager.deleteTnp_eca_dayByEcaRuleTypeId(cn, 
                    EcaPC.ECA_GEOFENCE_KEY, 
                    Integer.parseInt(EcaPC.RECURTYPEWEEKLY ));
            ecaxWorkdayManager.insertEcaDayOfGeofence(cn,EcaPC.ECA_GEOFENCE_KEY);
            System.out.println("geofence");
            //statecross
            ecaxWorkdayManager.deleteTnp_eca_dayByEcaRuleTypeId(cn, 
                    EcaPC.ECA_STATECROSS_KEY, 
                    Integer.parseInt(EcaPC.RECURTYPEWEEKLY ));
            ecaxWorkdayManager.insertEcaDayOfGeofence(cn,EcaPC.ECA_STATECROSS_KEY);
            System.out.println("statecross");
            cn.commit();
        } catch (Exception e) {
        	if(cn!=null)
        		cn.rollback();
            logger.debug("process: " + e.toString());
            e.printStackTrace();
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

            RepairEcaDays server = new RepairEcaDays();
            server.process(bReStart);

            System.exit(-1);
        } catch (Exception e) {
            logger.debug("RepairEcaDays::main() - exception:", e);
        }
    }

}