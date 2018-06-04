/**
 * @file CommHandler.java
 * @author Zhou Yuanxi
 * @date Feb. 16, 2004 Copyright (c) 2004 TeleVigation, Inc
 */
package fgafa.basic.socket.ecax;

public interface CommHandler {

    public void clientCallback(EcaMsg resp, EcaHost host, EcaMsg msg);

    public EcaMsg serverCallback(EcaMsg req) throws Exception;
}