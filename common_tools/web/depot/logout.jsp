<%@ page contentType="text/html;charset=gb2312" 
import="java.util.*,com.xuanzhi.tools.text.*,com.xuanzhi.tools.statistics.*"%><%@ page import="com.xuanzhi.tools.servlet.AuthorizedServletFilter" %><%
	session.removeAttribute(AuthorizedServletFilter.USERNAME);
	session.removeAttribute(AuthorizedServletFilter.PASSWORD);
	session.removeAttribute(AuthorizedServletFilter.PROPERTIES);
	
	
    out.println("<br><br><h1><center>���Ѿ��ɹ��˳��������µ�¼ʹ��ϵͳ</center></h1>");
    	 
%>