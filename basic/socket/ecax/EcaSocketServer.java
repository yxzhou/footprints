/**
 * @file EcaSocketServer.java
 * @author Zhou Yuanxi
 * @date Feb. 16, 2004 Copyright (c) 2004 TeleVigation, Inc
 */
package fgafa.basic.socket.ecax;

import java.io.*;
import java.net.*;

import com.televigation.log.TVCategory;
//import com.televigation.telenavpro.ecax.job.BaseJob;
import fgafa.basic.socket.ecax.job.MonitorJob;

public class EcaSocketServer implements Runnable {
    protected static TVCategory logger = (TVCategory) TVCategory
            .getInstance("com.televigation.telenavpro.ecax.EcaSocketServer");

    private CommHandler commHandler;

    private int port;

    public EcaSocketServer(int port, CommHandler commHandler) {
        this.commHandler = commHandler;
        this.port = port;
    }

    public void run() {
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(this.port);
            while (true) {
                //logger.debug("listening on port: "+this.nPort);
                Socket socket = serverSocket.accept();
                // logger.debug("processing connection from:" + socket.getInetAddress());
                SocketHandler csh = new SocketHandler(socket);
                Thread t = new Thread(csh);
                t.start();
            }
        } catch (Exception e) {
            logger.error("Exception in ServerSocket Listen", e);
            MonitorJob.sendToOperation(
                    EcaString.URGENTSTATUS,
                    null,
                    "ServerSocket Exception from ECAX",
                    "",
                    e
                	);             
            
        } finally {
            try {
                if (serverSocket != null)
                    serverSocket.close();
            } catch (Exception e) {
                logger.error("Exception in ServerSocket close", e);
            }

            System.exit(-1);
        }
    }

    static public void write(ObjectOutputStream oos, EcaMsg msg)
            throws Exception {
        oos.writeObject(msg);
    }

    static public EcaMsg read(InputStream is) throws Exception {
        ObjectInputStream ois = new ObjectInputStream(is);

        return (EcaMsg) ois.readObject();
    }

    class SocketHandler implements Runnable {
        private Socket socket = null;

        private InputStream is = null;

        private OutputStream os = null;

        /**
         * SocketHandler constructor
         */
        public SocketHandler(Socket socket) {
            this.socket = socket;
            try {
                this.is = socket.getInputStream();
                this.os = socket.getOutputStream();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public void close() {
            try {
                if (os != null)
                    os.close();
                if (is != null)
                    is.close();
                if (socket != null)
                    socket.close();
                os = null;
                is = null;
                socket = null;
            } catch (Exception e) {
                logger.error("Exception in close(): ", e);
            }
        }

        public void run() {
            EcaMsg req  = null;
            EcaMsg resp = null;
            try {
                req = read(this.is);
                logger.debug("EcaSocketServer request" + req.getMsgType());
                
                resp = commHandler.serverCallback(req);
                
                logger.info("response: Msgtype-jobId" + resp.getMsgType() + "-" + resp.getJobId());
                
                write(new ObjectOutputStream(os), resp);
            } catch (Exception e) {
                logger.error("Exception while processing connection: ", e);

            } finally {
                try {
                    //logger.debug("closing connection from:
                    // "+socket.getInetAddress());
                    if (this.is != null)
                        this.is.close();
                    if (this.os != null)
                        this.os.close();
                    if (this.socket != null)
                        this.socket.close();
                    //this.close();
                } catch (Exception e) {
                    logger.error("Exception while closing connection: ", e);
                }
            }
            
            if(EcaString.RESP_ACCEPTED_EXIT == resp.getMsgType()){
                logger.fatal("shutdown this serivce!");
                try{
                    EcaServer ecaServer = EcaServer.getInstance();
                    
                    try {
                        logger.info("------sleep------ 100 ");
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    
                    ecaServer.shutdown(0);                    
                }catch(Exception e){
                    
                }
            }
                
        }
    }

}