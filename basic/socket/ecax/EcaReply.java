/**
 * @file EcaReply.java
 * @author Zhou Yuanxi
 * @date Feb. 16, 2004 Copyright (c) 2004 TeleVigation, Inc
 */
package basic.socket.ecax;

public class EcaReply {
    private EcaHost host;

    private EcaMsg msg;

    public EcaReply(EcaHost host, EcaMsg msg) {
        this.host = host;
        this.msg = msg;
    }

    /**
     * @return Returns the host.
     */
    public EcaHost getHost() {
        return host;
    }
    /**
     * @return Returns the msg.
     */
    public EcaMsg getMsg() {
        return msg;
    }
}