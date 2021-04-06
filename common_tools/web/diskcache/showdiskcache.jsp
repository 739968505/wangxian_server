<%@ page language="java" pageEncoding="GBK" contentType="text/html;charset=GBK" 
import="java.util.*,com.xuanzhi.tools.cache.diskcache.concrete.*,com.xuanzhi.tools.page.* "%><%@ page import="com.xuanzhi.tools.text.*" %><!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<title>Show All List Cache</title>

</head>
<%
	AbstractDiskCache caches[] = DiskCacheHelper.getAllDiskCache();
	DefaultDiskCache cache = null;
	String message = null;
	int id = Integer.parseInt(request.getParameter("cache"));
	cache = (DefaultDiskCache)caches[id];

	message = "����";
	
	int start = ParamUtils.getIntParameter(request,"s",0);
	int pageSize = 1024;
	
%>
<body>
<br/><center>
<b>Cache <%=cache.getName() %> DataBlock Status Table</b>
</center><br/>
<%
	if(message != null){
		out.println("<b>"+message+"</b>�����<a href='./showalldiskcache.jsp'>����</a>ˢ��ҳ��<br/>");
	}
%>
<table border="0" align="center" cellpadding="0" cellspacing="1" bgcolor="#000000" width="100%">
<tr align="center" bgcolor="#EE8E9A">
	<td>���</td>
	<td>λ��</td>
	<td>��С</td>
	<td>����</td>
	<td>��������</td>
	<td>���г�ʱ</td>
	<td>ɢ��</td>
	<td>����</td>
	<td>ֵ��</td>
</tr>
<%
	DataBlock dbs[] = cache.getDataBlocks();
	for(int i = start*pageSize ; i < start*pageSize + pageSize && i < dbs.length ; i++){
		String color="#EEBBEE";
		if(dbs[i].containsData == false) color="#FFFFFF";
		%><tr align="center" bgcolor="<%=color%>">
		<td><%=i+1%></td>
		<td><%= StringUtil.addcommas(dbs[i].offset) %></td>
		<td><%= StringUtil.addcommas(dbs[i].length) %></td>
		<td><%= dbs[i].containsData %></td>
		<td><%= dbs[i].containsData?StringUtil.addcommas((dbs[i].lifetime - System.currentTimeMillis())):"0" %></td>
		<td><%= dbs[i].containsData?StringUtil.addcommas((dbs[i].idletime - System.currentTimeMillis())):"0" %></td>
		<td><%= dbs[i].hashcode %></td>
		<td><%= dbs[i].keyLength %></td>
		<td><%= dbs[i].valueLength %></td>
		</tr><%
	}
	
	String sss = PageUtil.makePageHTML("./showdiskcache.jsp?cache="+id+"",10,"s",start,1024,dbs.length);
%>	
</table>
<%= sss %>	
</body>
</html>
