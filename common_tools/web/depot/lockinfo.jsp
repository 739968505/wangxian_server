<%@ page contentType="text/html;charset=gb2312" 
import="java.io.*,java.util.*,com.xuanzhi.tools.text.*,
com.xuanzhi.tools.cache.*,com.xuanzhi.tools.statistics.*"%>
<%@ page import="com.xuanzhi.tools.statistics.depot.*" %><%
%><html>
<head>
</head>
<body>
<br>
<br/><h3>Dimension Cache�����</h3>
<table border='0' width='100%' cellpadding='0' cellspacing='1' bgcolor='#000000' align='center'>
<tr align='center' bgcolor='#00FFFF'><td>������</td><td>���д���</td><td>�ܴ���</td><td>Ԫ�ظ���</td><td>���Ԫ�ظ���</td><td>�����������</td><td>����</td></tr>
<%
	LruMapCache cache = DepotTool.getCache();
	if(String.valueOf("clear_cache").equals(request.getParameter("action"))){
		cache.clear();
	}
	if(cache != null){
		long hits = cache.getCacheHits();
		long miss = cache.getCacheMisses();
		long total = hits + miss;
		if(total == 0) total = 1;
		int num = cache.getNumElements();
%><tr align='center' bgcolor='#FFFFFF'><td><%=hits*100f/total%>%</td><td><%=hits%></td><td><%=total%></td><td><%=num%></td><td><%=cache.getMaxSize() %></td><td><%=StatisticsTool.maxCacheLifeTime/1000%>��</td><td><a href='./lockinfo.jsp?action=clear_cache'>���Cache</a></td></tr><%		
	}
%>
</table>
<h3>�ڴ��и����������</h3>
<br/>
<table border='0' width='100%' cellpadding='0' cellspacing='1' bgcolor='#000000' align='center'>
<tr align='center' bgcolor='#00FFFF'><td>���</td><td>�����ô���</td><td>��ӵ����</td><td>��������</td><td>ά������</td></tr>
<%
	HashMap<DepotTool.DimensionArray,DepotTool.Lock> lockMap = DepotTool.getLockMap();	
	Iterator<DepotTool.DimensionArray> it = lockMap.keySet().iterator();
	int count = 0;
	java.util.LinkedList<DepotTool.DimensionArray> al = new java.util.LinkedList<DepotTool.DimensionArray>();

	while(it.hasNext()){
		DepotTool.DimensionArray da = it.next();
		DepotTool.Lock lock = lockMap.get(da);
		al.add(da);
	}
	it = al.iterator();
	while(it.hasNext()){
		count ++;
		DepotTool.DimensionArray da = it.next();
		DepotTool.Lock lock = lockMap.get(da);
		out.println("<tr align='center' bgcolor='#FFFFFF'><td>"+count+"</td><td>"+lock.ref+"</td><td>"+lock.owner+"</td><td>-</td><td>"+da.dimensionStr+"</td></tr>");
	}
%>
</table>
</body>
</html> 
