<%@ page contentType="text/html;charset=gb2312" 
import="java.io.*,java.util.*,com.xuanzhi.tools.text.*,com.xuanzhi.tools.statistics.*"%>
<%@ page import="com.xuanzhi.tools.statistics.StatProject" %><%

	String className = request.getParameter("cl");
	boolean usingOracle = String.valueOf("true").equals(request.getParameter("uo")); 
	
	StatProject sp = StatProject.getStatProject(className);
	if(usingOracle)
		sp.setUsingOracle(usingOracle);
			
	String yearMonth = request.getParameter("year_month");
	if(yearMonth == null ) 
		yearMonth = (String)session.getAttribute("year_month");
	else
		session.setAttribute("year_month",yearMonth);	
	if(yearMonth == null) yearMonth = DateUtil.formatDate(new Date(),"yyyy_MM");
	
	HashMap<String,String> submitMap = new HashMap<String,String>();	
	
	Enumeration en = request.getParameterNames();
	while(en.hasMoreElements()){
		String name = (String)en.nextElement();
		String value = request.getParameter(name);
		if(name != null && value != null && value.trim().length() > 0)
			submitMap.put(name,value);
	}

	String unAG[] = sp.getUnAccumulateGranulas();
	for(int i = 0 ; unAG != null && i < unAG.length ; i++){
		if(!submitMap.containsKey(unAG[i])){
			submitMap.put(unAG[i],Dimension.LISTMARK);
		}
	}
	
	File tmpFile = sp.getQuickLinkFile();
	if(tmpFile != null)
		WebUtils.updateQuickLinks(tmpFile,request);
	
	int column = 6;
	
	String selectPage = "./granula.jsp?uo="+usingOracle+"&cl="+sp.getSelectGranulaProjectClassName();
	
		Properties props = (Properties)session.getAttribute(com.xuanzhi.tools.servlet.AuthorizedServletFilter.PROPERTIES);
		if(props == null) props = new Properties();
		
		Dimension ds[] = sp.getDimensions();
		long startTime = System.currentTimeMillis();
		StringBuffer errorMessage = new StringBuffer();
		
		StringBuffer limitCondition = new StringBuffer();
		
		String filterCondition = "";
		
		for(int i = 0 ; i < ds.length ; i++){
			String gs[] = ds[i].getAllGranula();
			for(int j = 0 ; j < gs.length ; j++){
				String value = submitMap.get(gs[j]);
				if(value != null && value.trim().length() > 0){
					try{
						ds[i].setValue(gs[j],value);
					}catch(IllegalArgumentException e){
						errorMessage.append("����ά��["+ds[i].getName()+"]������[" + ds[i].getGranulaDisplayName(gs[j]) +"]�������������ô����ȣ�Ȼ���������С����\n");
					}
				}

				if(props.containsKey(gs[j])){

					String v = props.getProperty(gs[j]);

					limitCondition.append("ά��["+ds[i].getName()+"]������[" + ds[i].getGranulaDisplayName(gs[j]) +"]������ֵ��"+v+"\n");

					String ss[] = v.split(",");
					value = ds[i].getValue(gs[j]);
					if(ss.length == 1 && !ss[0].equals(Dimension.LISTMARK)){
						ds[i].setValue(gs[j],ss[0]);
					}
					else if(value == null){
						String filter = "";
						for(int k = 0; k < ss.length ; k++){
							if(filter.length() == 0) 
								filter = gs[j] + "='"+ss[k]+"'";
							else
								filter += " or " + gs[j] + "='"+ss[k]+"'";
						}
						if(filter.length() > 0) filter = "(" + filter+")";
						
						if(filterCondition.length() == 0)
							filterCondition = filter;
						else
							filterCondition += " and " + filter;

					}else{

						boolean b = false;
						for(int k = 0; k < ss.length ; k++){
							if(ss[k].equals(value)) b = true;
						}
						if(b == false){
						errorMessage.append("����ά��["+ds[i].getName()+"]������[" + ds[i].getGranulaDisplayName(gs[j]) +"]�������Ȩ��ֻ������["+v+"]������ֵ����Ȩ����");	
						}
					}
				}
			}
		}
		
		
	
		StringBuffer sb  = new StringBuffer();
		sb.append("<form id='form_submit' name='form_submit'>");
		sb.append("<input id='submitted' name='submitted' type='hidden' value='true'>");
		sb.append("<input id='cl' name='cl' type='hidden' value='"+className+"'>");
		sb.append("<input id='uo' name='uo' type='hidden' value='"+usingOracle+"'>");
		sb.append(WebUtils.getDimensionInputTable(ds,false,true,true,selectPage));
		sb.append("\n<br/>");
		sb.append("�����������ʽ��<input name='filterexpression' id='filterexpression' size='40' value=\""+(submitMap.get("filterexpression")==null?"":submitMap.get("filterexpression"))+"\"><input type='button' value='ѡ���������' onclick=\"on_filter_select('filterexpression')\"> ����Ϊ���ӱ��ʽ");
		sb.append("\n<br/>");
		sb.append("���������ʽ��<input name='orderexpression' id='orderexpression' size='30' value=\""+(submitMap.get("orderexpression")==null?"":submitMap.get("orderexpression"))+"\"><input type='button' value='ѡ���������' onclick=\"on_order_select('orderexpression')\">");
		sb.append("����<input type='radio' name='desc' id='desc' "+("1" == submitMap.get("desc")?"selected":"")+" value=1>&nbsp;����<input type='radio' name='desc' id='desc' "+("0" == submitMap.get("desc")?"selected":"")+" value=0> ����Ϊ���ӱ��ʽ");
		sb.append("\n<br/>");
		sb.append("<input type='submit' value='��    ѯ'></form>");
		sb.append("\n<br/>");
		sb.append("\n<br/>");
		
		boolean submit = false;
		if(String.valueOf("true").equals(submitMap.get("submitted")))
			submit = true;
		
		if(submit && errorMessage.length() == 0){
			String filterExpression = submitMap.get("filterexpression");
			String orderExpression = submitMap.get("orderexpression");
			boolean desc = ("0".equals(submitMap.get("desc")));
			
			if(filterCondition.length() > 0)
			{
				if(filterExpression != null && filterExpression.trim().length() > 0)
					filterExpression = filterCondition + " and (" + filterExpression + ")";
				else
					filterExpression = filterCondition;
			}
			try{
				int maxQueryNum = 2;
				if(session.getAttribute("current.query.num") != null){
					Integer qn = (Integer)session.getAttribute("current.query.num");
					if(qn.intValue() >= maxQueryNum){
						StringBuffer querySb = new StringBuffer();
						for(int i = 1 ; i <= maxQueryNum ; i++){
							String ss = (String)session.getAttribute("current.query."+i);
							querySb.append("��"+i+"�β�ѯ��"+ss+"\n");
						}
						throw new Exception("���ѳ���ͬʱ��ѯ�����Ŀ�����ơ�"+maxQueryNum+"�Ρ�����ȴ�����Ĳ�ѯ�����������ԣ�\n" + querySb);
					}
				}
				Integer qn = (Integer)session.getAttribute("current.query.num");
				if(qn == null) qn = new Integer(0);
				
				qn = new Integer(qn.intValue()+1);
				
				StringBuffer querySb = new StringBuffer();
				querySb.append("��ѯʱ��:"+ DateUtil.formatDate(new Date(),"yyyy-MM-dd HH:mm:ss") +"; ���£�" +yearMonth+"; ");
				for(int i = 0 ; i < ds.length ; i++){
					querySb.append(ds[i]+"; ");
				}
				
				session.setAttribute("current.query."+qn,querySb.toString());
				session.setAttribute("current.query.num",qn);
				Map<Map<String,String>,StatData> resultMap = null;
				try{
					resultMap = sp.query(yearMonth,ds,sp.getStatData(),filterExpression,orderExpression,desc);
				}finally{
					session.removeAttribute("current.query."+qn);
					
					qn = (Integer)session.getAttribute("current.query.num");
					if(qn == null) qn = new Integer(0);
					qn = new Integer(qn.intValue()-1);
					session.setAttribute("current.query.num",qn);
				}
				sb.append("<h3>��ѯ������£���"+resultMap.size()+"����¼����ʱ "+((System.currentTimeMillis() - startTime)/1000f)+" �룺</h3><br/>");
				sb.append(WebUtils.getTableOfQueryResult(ds,sp.getDisplayStatData(),resultMap));
				
			}catch(Exception e){
				sb.append("<h3 color=red>��ѯ������ʱ "+((System.currentTimeMillis() - startTime)/1000f)+" �룬��ϸ��Ϣ���£��뱣��ҳ����ϵ������Ա�����</h3><br/>");
				ByteArrayOutputStream bout = new ByteArrayOutputStream();
				e.printStackTrace(new PrintStream(bout));
				sb.append("<pre>");
				sb.append(new String(bout.toByteArray()));
				sb.append("</pre>");
			}
		}else if(errorMessage.length() > 0){
			sb.append("<h3 color=red>���벻��������ο�������Ϣ��</h3><br/>");
			sb.append("<pre>");
			sb.append(errorMessage.toString());
			sb.append("</pre>");
		}else if(limitCondition.length() > 0){
			sb.append("<h3 color=red>��ǰ�û�Ϊ"+session.getAttribute(com.xuanzhi.tools.servlet.AuthorizedServletFilter.USERNAME)+"����ѯ��������</h3><br/>");
			sb.append("<pre>");
                        sb.append(limitCondition.toString());
                        sb.append("</pre>");
		}
	
%><html>
<head>
<style type="text/css">
@charset "GBK";
body,td,th {font-size: 14px;}
a:link {color: #006699; text-decoration: none;}
a:visited {color: #006699; text-decoration: none;}
a:hover { color: #0000FF; text-decoration: underline;}
a:active { text-decoration: none; color: #0000FF;}
.redDot {color: #FF0000; font-size: 12px;}
/* Sortable tables */
table.sortable a.sortheader {
    background-color:#eee;
    color:#666666;
    font-weight: bold;
    text-decoration: none;
    display: block;
}
table.sortable span.sortarrow {
    color:black;
    text-decoration: none;
}
</style>
<SCRIPT src="./sorttable.js" type="text/javascript">
</SCRIPT>

<SCRIPT type="text/javascript">
function open_granula(granulapage){
		var value;
<%
	for(int i = 0 ; i < ds.length ; i++){
		String gs[] = ds[i].getAllGranula();
		for(int j = 0 ; j < gs.length ; j++){
			out.println("value = document.getElementById('"+gs[j]+"').value;");
			out.println("if(value != null && value.length > 0 && value != '***'){granulapage+='&"+gs[j]+"='+value;}");
		}
	}
%>
		window.open(granulapage,'selectgranula','height=500, width=500, top=150, left=262,scrollbars=yes');
		return true;
	} 
</SCRIPT>
</head>
<body>
<center>
<b><%=sp.getName()%>ͳ����Ŀ��ѯ����</b><br/></center>
<form id="f"><input id='cl' name='cl' type='hidden' value='<%=className%>'><input id='uo' name='uo' type='hidden' value='<%=usingOracle%>'>
�������·ݣ�<input type="text" size="20" name="year_month" value="<%=yearMonth==null?"":yearMonth%>">&nbsp;<input type="submit" value="��  ��">&nbsp;��ʽyyyy_MM������2007_09��2007_10<br/>
</form>
<%
	if(tmpFile != null){
		out.println("<center>"+WebUtils.getTableOfQuickLinks(className,tmpFile,column,submit,request.getQueryString())+"<a href='./txtEdit.jsp?fn="+tmpFile.getAbsolutePath()+"'>�޸Ŀ��ٲ�ѯ����</a></center>");
	}
%>
<center><%=sb.toString()%></center>
</body>
</html> 
