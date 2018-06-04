/**
 * @file EcaJob.java
 * @author Zhou Yuanxi
 * @date Feb. 16, 2004 Copyright (c) 2004 TeleVigation, Inc
 */
package fgafa.basic.socket.ecax.job;

//import com.televigation.telenavpro.ecax.EcaMsg;

public interface EcaJob {
    public void process()throws Exception;
}