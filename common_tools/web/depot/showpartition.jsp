<%@ page contentType="text/html;charset=gb2312" 
import="java.io.*,java.util.*,com.xuanzhi.tools.text.*,com.xuanzhi.tools.statistics.depot.*"%><%

	String className = request.getParameter("cl");
	DepotProject sp = DepotProject.getDepotProject(className);		
	
	
%><html>
<head>
</head>
<body>
<% 
	if("true".equals(request.getParameter("addpartition"))){
		String yearMonth = request.getParameter("yearMonth").trim();
		sp.addPartition(yearMonth);
		out.println("<center><h1>����"+yearMonth+"����ɹ�</h1></center><br/>");
	}
%>
<br><h2>��ʾ<%=sp.getName()%>ͳ����Ŀ���������</h2>
<br/>
<table border='0' cellpadding='0' cellspacing='1' width='100%' bgcolor='#000000' align='center'>
<tr bgcolor='#00FFFF' align='center'><td>λ��</td><td>������</td><td>������������</td><td>�Ѳ�������</td></tr>
<%
	String partitions[][] = sp.getAllPartitionInformation();	

	for(int i = 0 ; i < partitions.length ; i++){
		out.println("<tr bgcolor='#FFFFFF' align='center'>");
		out.println("<td>"+(i+1)+"</td><td>"+partitions[i][0]+"</td><td>"+partitions[i][1]+"</td><td>"+partitions[i][2]+"</td>");
		out.println("</tr>");
	}
%></table>
<br/>
����µķ�����<br/>
<form id='f'><input type='hidden' name='cl' value='<%=className%>'><input type='hidden' name='addpartition' value='true'>
����������������£�<input type='text' name='yearMonth' value=''>&nbsp; ��ʽΪyyyyMM������200901��������ֻ���Ǵ���������ʱ����߽����������͵����ڶ�����֮�䡣
<br><input type='submit' value='��   ��'></form>
</body>
</html> 
