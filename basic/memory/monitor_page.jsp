<%@page import="java.util.*"%>
<%@page import="java.net.InetAddress"%>
<%@page import="com.telenav.tntplatform.commons.db.TnpDbUtil"%>
<%@page import="com.telenav.tntplatform.client.measserver.measurement.client.pool.MeasurementClientManager"%>

<html>
  <%
  
  %>
	<head>
		<title>monitor page</title>
	</head>
	<body>
    <div id="aaa" style="position:absolute;top:0;left:0;background-color:#FFFFFF;width:100%;height:100%">
    <table>
	    <tr><td>DB Connection pool:</td></tr>
    	<tr><td><%=TnpDbUtil.THREADCONNECTIONMAP.toString()%></td></tr>
	</table>
	<br>

	<table>		
		<tr>
		   <td colspan=2 >Measurement Connection pool size</td>
		</tr>
		<tr>
		   <td>pool active size</td>
		   <td>active idle size</td>
		</tr>
		<tr>
		   <td><%=MeasurementClientManager.getPoolNumActive()%></td>
   		   <td><%=MeasurementClientManager.getPoolNumIdle()%></td>
		</tr>
    </table>	
    <br>

	<table>
	    <tr><td>it's in :</td></tr>
    	<tr><td>
		  <% 

			try {
			  byte[] ipAddr = InetAddress.getLocalHost().getAddress();

				// Convert to dot representation
				String ipAddrStr = "";
				for (int i=0; i<ipAddr.length; i++) {
					if (i > 0) {
						ipAddrStr += ".";
					}
					ipAddrStr += ipAddr[i]&0xFF;
				}

			  
			  out.println(ipAddrStr);
			}
			catch (Exception e) {
			  // TODO Auto-generated catch block
			  e.printStackTrace();
			}
		  
		  %>
		
		</td></tr>
	</table>
	<br>
    
	</div>
	</body>
</html>
