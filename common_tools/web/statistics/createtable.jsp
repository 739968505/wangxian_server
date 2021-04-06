<%@ page contentType="text/html;charset=gb2312" 
import="java.io.*,java.util.*,com.xuanzhi.tools.text.*,com.xuanzhi.tools.statistics.*"%><%

	String className = request.getParameter("cl");

	boolean usingOracle = String.valueOf("true").equals(request.getParameter("uo")); 
	
	StatProject sp = StatProject.getStatProject(className);
	if(usingOracle)
		sp.setUsingOracle(usingOracle);
	
	String month = request.getParameter("month");
	if(month != null && month.trim().length() == 0) month = null;
	
	String createtable = request.getParameter("createtable");
	boolean showCreateTableButton = false;
	if(createtable == null && month != null) showCreateTableButton = true;
	
	String errorMessage = null;
	
	if(createtable != null && createtable.equals("true") && month != null){
		try{
			sp.createTableAndIndexs(month);
			errorMessage = "����"+sp.getName()+"��Ŀ��������ɹ���";
		}catch(Exception e){
			errorMessage = "����"+sp.getName()+"��Ŀ�������ʧ�ܣ�������Ϣ��<br>";
			ByteArrayOutputStream bout = new ByteArrayOutputStream();
				e.printStackTrace(new PrintStream(bout));
				errorMessage += "<pre>"+new String(bout.toByteArray())+"</pre>";
		}
	}
%><html>
<head>
</head>
<body>
<br><h2>����<%=sp.getName()%>ͳ����Ŀ�������ά�ȵ������ű�</h2>
<br/>
<form id="f"><input type="hidden" name="cl" value="<%=className%>"><input id='uo' name='uo' type='hidden' value='<%=usingOracle%>'>
�������·ݣ�<input type="text" size="20" name="month" value="<%=month==null?"":month%>">&nbsp;<input type="submit" value="��  ��">&nbsp;��ʽyyyy_MM������2007_09��2007_10<br/>
</form><br>
<%
	
	if(errorMessage == null && month != null){
		month = month.trim();
		int k = month.indexOf("_");
		try{
		if(k > 0){
			int y = Integer.parseInt(month.substring(0,k));
			int m = Integer.parseInt(month.substring(k+1));
			if(y < 2007 || y > 2020) errorMessage = "��ݲ�������Ӧ����2007��2020��֮��";
			if(m < 1 || m > 12) errorMessage = "�·ݲ��ԣ��·�Ӧ����01��12֮��";
		}else{
			errorMessage = "��ʽ����Ӧ����yyyy_MM";
		}
		}catch(Exception e){
			errorMessage = "��ʽ����Ӧ����yyyy_MM������yyyy��MM����ʾ����";
		}
	}
	
	if(errorMessage != null){
		out.println("<h3 color='red'>"+errorMessage+"</h3>");
	}else{
		if(month == null) month = "%yyyy_MM%";
		
		String sql = sp.constructCreateTableSQL(month);
		%>�������SQL��<pre><%= sql %></pre><%
		
		String sqls[] = sp.constructCreateIndexSQL(month);
		sql = "";
		
		for(int i = 0 ; i < sqls.length ;i ++)
			sql += sqls[i] + ";\n";
		%>����������SQL��<pre><%=sql%></pre><%
		
		if(showCreateTableButton){
			%><form id="ffff"><input type="hidden" name="cl" value="<%=className%>"><input id='uo' name='uo' type='hidden' value='<%=usingOracle%>'><input type="hidden" name="month" value="<%=month%>"><input type="hidden" name="createtable" value="true">
				<input type="submit" value="����<%=month%>�ı������"></form><br><%
		}
	}
%>
</body>
</html> 
