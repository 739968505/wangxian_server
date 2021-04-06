<%@ page contentType="text/html;charset=gb2312" import="java.util.*,com.xuanzhi.tools.text.*,com.xuanzhi.tools.statistics.*"%><%@ page import="java.util.Calendar" %><%

	String className = request.getParameter("cl");
	boolean usingOracle = String.valueOf("true").equals(request.getParameter("uo")); 
	
	final StatProject sp = StatProject.getStatProject(className);
	if(usingOracle)
		sp.setUsingOracle(usingOracle);
	
	Date d = new Date(System.currentTimeMillis() - 24 * 3600 * 1000L);
	String yearMonth = DateUtil.formatDate(d,"yyyy_MM");
	String dayStr = DateUtil.formatDate(d,"yyyyMMdd");
	
	if(request.getParameter("yearMonth") != null){
		yearMonth = request.getParameter("yearMonth").trim();
	}
	
	if(request.getParameter("dayStr") != null){
		dayStr = request.getParameter("dayStr").trim();
	}
		
%><html>
<head>
</head>
<body>
<br><h2><%=sp.getName()%>��Ŀ�������(<%=yearMonth+","+dayStr%>)</h2>
<br/>
<form id="f"><input type='hidden' name='cl' value='<%=className%>'>
<input type='hidden' name='uo' value='<%=usingOracle%>'>
�������·ݣ�<input type="type" size="20" name="yearMonth" value="<%=yearMonth%>">����2007_11<br/>
���������ڣ�<input type="type" size="20" name="dayStr" value="<%=dayStr%>">����20071105<br/>
��������<input type="password" size="20" name="passwd" value="">&nbsp;<input type="submit" value="��  ��">&nbsp;<br/>
</form><br>
<%
	String passwd = request.getParameter("passwd");
	
	String errorMessage = null;
	if(passwd != null && passwd.equals("@xuanzhi")){

		if(!dayStr.startsWith(yearMonth.replace("_",""))){
			throw new IllegalArgumentException("dayStr["+dayStr+"] not match the yearMonthStr["+yearMonth+"]");
		}
		long startTime = System.currentTimeMillis();
		out.println("<center>�����Ѿ�����������ִ�У���鿴�����־</center>");
		out.flush();
		final String _yearMonth = yearMonth;
		final String _dayStr = dayStr;
		Thread thread = new Thread(new Runnable(){
			public void run(){
				sp.dataConstruct(_yearMonth,_dayStr);
				//System.out.println("[snapshot] ["+sp.getName()+"] ["+yearMonth+"] ["+dayStr+"] [finished]");
			}
		},sp.getName() + "-SnapShot-Thread");
		thread.start();
	}else if(passwd != null){
		out.println("<center>�������</center>");
	}
%>
</body>
</html> 
