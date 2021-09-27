package basic.socket;

import java.io.*;
import java.net.*;

public class SocketClient
{
  Socket socket;
  BufferedReader in;
  PrintWriter out;



  public SocketClient() {
    try {
      //create connection to server
      System.out.println("Try to Connect to 127.0.0.1:10000");
      socket = new Socket("127.0.0.1", 10000);
      System.out.println("The Server Connected!");

      //read from keyboard and write to server.
      System.out.println("Please enter some Character:");
      BufferedReader line = new BufferedReader(new InputStreamReader(System.in));
      out = new PrintWriter(socket.getOutputStream(), true);
      out.println(line.readLine());
      in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
      System.out.println(in.readLine());
      
      //close connection.
      out.close();
      in.close();
      socket.close();
    }
    catch (IOException e) {
      out.println("Wrong");
    }
  }



  public static void main(String[] args) {
    new SocketClient();
  }

}
