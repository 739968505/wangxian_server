<%@ page contentType="text/html;charset=gb2312" 
import="java.io.*,java.util.*,com.xuanzhi.tools.text.*,com.xuanzhi.tools.statistics.depot.*"%><%

	String className = request.getParameter("cl");
	
	DepotProject sp = DepotProject.getDepotProject(className);
		
%><html>
<head>
</head>
<body>
<br><h2>��ʾ<%=sp.getName()%>ͳ����Ŀ�������ά�ȵ�����</h2>
<br/>
<table border='0' cellpadding='0' cellspacing='1' width='100%' bgcolor='#000000' align='center'>
<tr bgcolor='#00FFFF' align='center'><td>����</td><td>�Ѽ�������</td><td>�Ƿ�Ϊ������</td><td>������</td><td>�����ֶ�</td></tr>
<%
	String tables[][] = sp.getAllTableInformation();
	for(int i = 0 ; i < tables.length ; i++){
		String[][] indexs = sp.getIndexs(tables[i][0]);
		for(int j = 0 ; j < indexs.length ; j++){
			out.println("<tr bgcolor='#FFFFFF' align='center'>");
			if(j == 0){
				out.println("<td rowspan='"+indexs.length+"'>"+tables[i][0]+"</td>");
				out.println("<td rowspan='"+indexs.length+"'>"+tables[i][1]+"</td>");
				out.println("<td rowspan='"+indexs.length+"'>"+tables[i][2]+"</td>");
			}
			out.println("<td>"+indexs[j][0]+"</td><td>"+indexs[j][1]+"</td>");
			out.println("</tr>");
		}
	}
%></table>
</body>
</html> 
