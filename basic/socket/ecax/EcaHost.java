/**
 * @file EcaHost.java
 * @author Zhou Yuanxi
 * @date Feb. 16, 2004 Copyright (c) 2004 TeleVigation, Inc
 */
package basic.socket.ecax;

public class EcaHost {

    private String hostIP = null;

    private int hostPort = -1;

    private int timeout = 200;

    private int priority = -1;

    private int masterIndex = -1;

    public EcaHost(String hostIP, int hostPort, int timeout, int priority) {
        this.hostIP = hostIP;
        this.hostPort = hostPort;
        this.timeout = timeout;
        this.priority = priority;
    }

    /**
     * @return Returns the hostIP.
     */
    public String getHostIP() {
        return hostIP;
    }
    /**
     * @return Returns the hostPort.
     */
    public int getHostPort() {
        return hostPort;
    }
    /**
     * @return Returns the masterIndex.
     */
    public int getMasterIndex() {
        return masterIndex;
    }
    /**
     * @return Returns the priority.
     */
    public int getPriority() {
        return priority;
    }
    /**
     * @return Returns the timeout.
     */
    public int getTimeout() {
        return timeout;
    }
    /**
     * @param masterIndex The masterIndex to set.
     */
    public void setMasterIndex(int masterIndex) {
        this.masterIndex = masterIndex;
    }
}