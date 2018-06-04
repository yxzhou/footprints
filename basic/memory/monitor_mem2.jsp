
  <%@page contentType="text/html" %>
  <%@page import="java.lang.management.*"%>
  <%@page import="java.util.*"%>


<html>
  <head>
    <title>JVM Memory Monitor</title>
    <style type="text/css">
      td {
        text-align: right;
      }
    </style>
  </head>
<body>
  <%
    MemoryMXBean memoryBean = ManagementFactory.getMemoryMXBean();
    //pageContext.setAttribute("memoryBean", memoryBean);
  
    //List poolBeans = ManagementFactory.getMemoryPoolMXBeans();
    //pageContext.setAttribute("poolBeans", poolBeans);
  %>


<h3>Total Memory</h3>
  <table border="1" width="100%">
	<tr>
      <th>usage</th>
      <th>init</th>
      <th>used</th>
      <th>committed</th>
      <th>max</th>
    </tr>
    <tr>
      <td style="text-align: left">Heap Memory Usage</td>
      <td><%=memoryBean.getHeapMemoryUsage().getInit() / (1024 * 1024)%> Mb</td>
      <td><%=memoryBean.getHeapMemoryUsage().getUsed() / (1024 * 1024)%> Mb</td>
      <td><%=memoryBean.getHeapMemoryUsage().getCommitted() / (1024 * 1024)%> Mb</td>
      <td><%=memoryBean.getHeapMemoryUsage().getMax() / (1024 * 1024)%> Mb</td>
    </tr>
    <tr>
      <td style="text-align: left">Non-heap Memory Usage</td>
      <td><%=memoryBean.getNonHeapMemoryUsage().getInit() / (1024 * 1024)%> Mb</td>
      <td><%=memoryBean.getNonHeapMemoryUsage().getUsed() / (1024 * 1024)%> Mb</td>
      <td><%=memoryBean.getNonHeapMemoryUsage().getCommitted() / (1024 * 1024)%> Mb</td>
      <td><%=memoryBean.getNonHeapMemoryUsage().getMax() / (1024 * 1024)%> Mb</td>
    </tr>
  </table>

  <h3>Memory Pools</h3>
  <table border="1" width="100%">
    <tr>
      <th>name</th>
      <th>usage</th>
      <th>init</th>
      <th>used</th>
      <th>committed</th>
      <th>max</th>
    </tr>


	<%
        Iterator iter = ManagementFactory.getMemoryPoolMXBeans().iterator();
        while (iter.hasNext()) {
            MemoryPoolMXBean bean = (MemoryPoolMXBean) iter.next();
    %>

      <tr>
        <td style="text-align: left"><%=bean.getName()%></td>
        <td style="text-align: left">Memory Usage</td>
        <td><%=bean.getUsage().getInit() / (1024 * 1024)%> Mb</td>
        <td><%=bean.getUsage().getUsed() / (1024 * 1024)%> Mb</td>
        <td><%=bean.getUsage().getCommitted() / (1024 * 1024)%> Mb</td>
        <td><%=bean.getUsage().getMax() / (1024 * 1024)%> Mb</td>
      </tr>
      <tr>
        <td></td>
        <td style="text-align: left">Peak Usage</td>
        <td><%=bean.getPeakUsage().getInit() / (1024 * 1024)%> Mb</td>
        <td><%=bean.getPeakUsage().getUsed() / (1024 * 1024)%> Mb</td>
        <td><%=bean.getPeakUsage().getCommitted() / (1024 * 1024)%> Mb</td>
        <td><%=bean.getPeakUsage().getMax() / (1024 * 1024)%> Mb</td>
      </tr>
    <%}%>
  
  </table>

</body>

</html>
