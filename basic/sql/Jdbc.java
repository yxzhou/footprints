package basic.sql;

import java.sql.*;

public class Jdbc
{

  private static void cleanUp(ResultSet rs, Statement stmt, Connection conn) {
    try {
      if (rs != null)
        rs.close();
    }
    catch (Exception e) {
      e.printStackTrace();
    }

    try {
      if (stmt != null)
        stmt.close();
    }
    catch (Exception e) {
      e.printStackTrace();
    }

    try {
      if (conn != null) {
        conn.close();
      }
    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }


  public static void main(String[] args) {

    //init
    String dbUrl = "jdbc:oracle:thin:@127.0.0.1:1521:orcl";
    String theUser = "admin";
    String thePw = "manager";
    
    Connection conn = null;
    Statement stmt = null;
    ResultSet rs = null;
    
    try {
      //load JdBC Driver
      Class.forName("oracle.jdbc.driver.OracleDriver").newInstance(); // oracle.jdbc.driver.OracleDriver
      //create DB Connection
      conn = DriverManager.getConnection(dbUrl, theUser, thePw);
      //create DB Statement
      stmt = conn.createStatement();
      //execute SQL and get ResultSet 
      rs = stmt.executeQuery("select 1 as id from dual");
      
      //got records from ResultSet
      while (rs.next()) {
        System.out.println(rs.getString("id"));
      }
    }
    catch (Exception e) {
      e.printStackTrace();
    }finally{
      //close ResultSet, Statement and Connection
      cleanUp(rs, stmt, conn);
    }
    
  }

}
