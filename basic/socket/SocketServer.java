package basic.socket;

import java.net.*;
import java.io.*;

public class SocketServer
{

  private ServerSocket ss;
  private Socket socket;
  private BufferedReader in;
  private PrintWriter out;



  public SocketServer() {
    try {
      //listening on port: 
      ss = new ServerSocket(10000);
      
      while (true) {
        //processing connection from client
        socket = ss.accept();
        String RemoteIP = socket.getInetAddress().getHostAddress();
        String RemotePort = ":" + socket.getLocalPort();
        System.out.println("A client come in!IP:" + RemoteIP + RemotePort);
        
        //read and write
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        String line = in.readLine();
        System.out.println("Cleint send is :" + line);
        out = new PrintWriter(socket.getOutputStream(), true);
        out.println("Your Message Received!");
        
        //close connection
        out.close();
        in.close();
        socket.close();
      }
    }
    catch (IOException e) {
      out.println("wrong");
    }
  }



  public static void main(String[] args) {
    new SocketServer();
  }

}
