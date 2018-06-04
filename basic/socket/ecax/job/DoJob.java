/**
 * @file BaseJob.java
 * @author Zhou Yuanxi
 * @date Feb. 16, 2004 Copyright (c) 2004 TeleVigation, Inc
 */
package televigation.telenavpro.ecax.job;

//import java.lang.*;
import com.televigation.telenavpro.ecax.EcaMsg;
import com.televigation.telenavpro.servlet.TeleNavProStrings;

public class DoJob {

    public BaseJob getJob(EcaMsg ecaMsg) throws Exception {
        int jobType = ecaMsg.getJobType();
        switch (jobType) {
        case TeleNavProStrings.ECA_CLODKIN_KEY: 
            return ClockInJob.getInstance(ecaMsg);
        case TeleNavProStrings.ECA_APPSTART_KEY: 
            return AppStartJob.getInstance(ecaMsg);
        case TeleNavProStrings.ECA_NOCOMM_KEY: 
            return NoCommJob.getInstance(ecaMsg);
        case TeleNavProStrings.ECA_NOCOMMFORDAYS_KEY: 
            return NoCommForDaysJob.getInstance(ecaMsg);
        case TeleNavProStrings.ECA_CLOCKOUT_KEY: 
            return ClockOutJob.getInstance(ecaMsg);
        case TeleNavProStrings.ECA_TURNOFF_KEY: 
            return AppTurnOffJob.getInstance(ecaMsg);
        case TeleNavProStrings.ECA_WORKINGHOUR_KEY: 
            return WorkHoursJob.getInstance(ecaMsg);
        case TeleNavProStrings.ECA_GEOFENCE_KEY: 
            return GeofenceJob.getInstance(ecaMsg);
//        case TeleNavProStrings.ECA_SPEEDING_KEY: 
//        	return SpeedingJob.getInstance(ecaMsg);
//        case TeleNavProStrings.ECA_STOP_KEY: 
//        	return StopJob.getInstance(ecaMsg);
//        case TeleNavProStrings.ECA_LOWBATTERY_KEY: 
//        	return LowBatteryJob.getInstance(ecaMsg);
//        case TeleNavProStrings.ECA_LOWMEMORY_KEY: 
//        	return LowBatteryJob.getInstance(ecaMsg);        
        case TeleNavProStrings.ECA_JOBDELAY_KEY: 
        	return JobStartDelayJob.getInstance(ecaMsg);
        case TeleNavProStrings.ECA_JOBOVERTIME_KEY: 
        	return JobOverWorkJob.getInstance(ecaMsg);
        case TeleNavProStrings.ECA_JOBLONGERTRANSIT_KEY: 
        	return JobOverTransitJob.getInstance(ecaMsg);
        case TeleNavProStrings.ECA_JOBDELAYRECEIVE_KEY: 
        	return JobReceiveDelayJob.getInstance(ecaMsg);
//        case TeleNavProStrings.ECA_JOBEXPIRED_KEY: 
//        	return JobExpiredJob.getInstance(ecaMsg);
        case TeleNavProStrings.ECA_STATECROSS_KEY: 
            return StateCrossJob.getInstance(ecaMsg);
        default:
            throw new Exception("Your eca type is wrong!");
        
        }
    }
}