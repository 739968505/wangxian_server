<%@ page contentType="text/html;charset=gb2312" 
import="java.io.*,java.util.*,com.xuanzhi.tools.text.*,
com.xuanzhi.tools.cache.*,com.xuanzhi.tools.statistics.*"%><%@ page import="com.xuanzhi.tools.statistics.StatisticsTool" %><%
%><html>
<head>
</head>
<body>
<br>
<br/><h3>StatData Cache�����</h3>
<table border='0' width='100%' cellpadding='0' cellspacing='1' bgcolor='#000000' align='center'>
<tr align='center' bgcolor='#00FFFF'><td>������</td><td>���д���</td><td>�ܴ���</td><td>Ԫ�ظ���</td><td>���Ԫ�ظ���</td><td>�����������</td><td>����</td></tr>
<%
	LruMapCache cache = StatisticsTool.getCache();
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
	HashMap<StatisticsTool.DimensionArray,StatisticsTool.Lock> lockMap = StatisticsTool.getLockMap();	
	Iterator<StatisticsTool.DimensionArray> it = lockMap.keySet().iterator();
	int count = 0;
	java.util.LinkedList<StatisticsTool.DimensionArray> al = new java.util.LinkedList<StatisticsTool.DimensionArray>();

	while(it.hasNext()){
		StatisticsTool.DimensionArray da = it.next();
		StatisticsTool.Lock lock = lockMap.get(da);
		if(lock.data != null) 
			al.add(0,da);
		else 
			al.add(da);
	}
	it = al.iterator();
	while(it.hasNext()){
		count ++;
		StatisticsTool.DimensionArray da = it.next();
		StatisticsTool.Lock lock = lockMap.get(da);
		out.println("<tr align='center' bgcolor='#FFFFFF'><td>"+count+"</td><td>"+lock.ref+"</td><td>"+lock.owner+"</td><td>"+lock.data+"</td><td>"+da.dimensionStr+"</td></tr>");
	}
%>
</table>
</body>
</html> 
