/**
 * @file EcaSocketClient.java
 * @author Zhou Yuanxi
 * @date Feb. 16, 2004 Copyright (c) 2004 TeleVigation, Inc
 */
package basic.socket.ecax;

import java.net.*;
import java.io.*;
import java.lang.Thread;

import com.televigation.log.TVCategory;

public class EcaSocketClient implements Runnable {
    static public TVCategory logger = (TVCategory) TVCategory
            .getInstance("com.televigation.telenavpro.ecax.EcaSocketClient");

    private EcaHost host;

    private EcaMsg msg;

    private CommHandler commHandler;

    static public void send(EcaHost host, EcaMsg msg, CommHandler commHandler) {
        EcaSocketClient c = new EcaSocketClient(host, msg, commHandler);
        Thread t = new Thread(c);

        t.start();
    }

    private EcaSocketClient(EcaHost host, EcaMsg msg, CommHandler commHandler) {
        this.host = host;
        this.msg = msg;
        this.commHandler = commHandler;
    }

    public void run() {
        EcaMsg resp = connect(this.host, this.msg, 1);
        this.commHandler.clientCallback(resp, this.host, this.msg);
    }

    static public EcaMsg connect(EcaHost host, EcaMsg msg, int iTimes) {

        Socket s = null;
        OutputStream os = null;
        ObjectOutputStream oos = null;
        InputStream is = null;
        ObjectInputStream ois = null;
        EcaMsg resp = null;

        int j = 0;
        while (j < iTimes && resp == null) {
            try {
                logger.debug("connect IP-PORT" + host.getHostIP() + "-" +
                 host.getHostPort());
                s = new Socket(host.getHostIP(), host.getHostPort());

                s.setSoTimeout(host.getTimeout());
                s.setTcpNoDelay(true);

                os = s.getOutputStream();
                oos = new ObjectOutputStream(os);

                EcaSocketServer.write(oos, msg);
                //logger.debug("before flush");
                os.flush();
                //logger.debug("after flush");
                is = s.getInputStream();
                //logger.debug("after getInputStream");

                int i = 0;
                while (i < 6 && is.available() <= 0) {
                    Thread.sleep(5);
                    i++;
                    //logger.debug("because inputStream is inavailable ,let me
                    // wait a while");
                }

                ois = new ObjectInputStream(is);
                resp = (EcaMsg) ois.readObject();
            } catch (Exception e) {
                logger.error("Connect exception for: " + host.getPriority() + "-"
                        + host.getHostIP() + "-" + host.getHostPort());
                logger.error("Connect exception for: ", e);                
           
            } finally {
                if (os != null)
                    try {
                        os.close();
                    } catch (Exception ex) {
                        logger.error("Connect exception for: os.close ", ex);
                    }
                ;
                if (is != null)
                    try {
                        is.close();
                    } catch (Exception ex) {
                        logger.error("Connect exception for: is.close ", ex);
                    }
                ;
                if (s != null)
                    try {
                        s.close();
                    } catch (Exception ex) {
                        logger.error("Connect exception for: s.close ", ex);
                    }
                ;
            }

            try {
                j++;
                if (resp == null)
                    Thread.sleep(6000);
            } catch (InterruptedException e1) {
                logger.error("Connect exception for: ", e1);
            }
        }

        if (resp == null)
            resp = new EcaMsg(EcaString.RESP_NO_REPLY);

        logger.info("request: " + resp.getJobId());
        return resp;
    }

}