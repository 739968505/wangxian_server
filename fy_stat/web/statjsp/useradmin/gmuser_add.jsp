<%@ page contentType="text/html; charset=gbk"%>
<%@page import="com.xuanzhi.boss.gmuser.model.*"%>
<%@ include file="header.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" lang="zh-cn">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=gbk">
			<title>��ɫ���</title>
			<link rel="stylesheet" href="../css/style.css" />
			<script type="text/javascript">
	function sub() {

		var username = document.getElementById("username").value;
		var password = document.getElementById("password").value;
		var relname = document.getElementById("relname").value;
		if (username && password && relname) {

			var f1 = document.getElementById("f1");
			f1.submit();
		} else {
			alert("ֵ����Ϊ��");
		}
	}
	function overTag(tag) {
		tag.style.color = "red";
		tag.style.backgroundColor = "lightcyan";
	}

	function outTag(tag) {
		tag.style.color = "black";
		tag.style.backgroundColor = "white";
	}
</script>
	</head>
	<body bgcolor="#FFFFFF">
		<h1 align="d">
			��ɫ���
		</h1>
		<%
			out.print("<input type='button' value='ˢ��' onclick='window.location.replace(\"gmuser_add.jsp\")' />");
			ActionManager amanager = ActionManager.getInstance();
			GmUserManager gmanager = GmUserManager.getInstance();
			XmlRoleManager rmanager = XmlRoleManager.getInstance();
			Set<String> gmnames = gmanager.getGmnames();
			String addstr = request.getParameter("add");
			if (addstr != null) {
				Gmuser gmuser = new Gmuser();
				String username = request.getParameter("username");
				String password = request.getParameter("password");
				String relname = request.getParameter("relname");
				String rids[] = request.getParameterValues("rid");
				String gmname = request.getParameter("gmname");
				List<Authority> as = new ArrayList<Authority>();
				if (rids != null) {
					for (String rid : rids) {
						Authority auth = new Authority();
						auth.setRoleid(rid);
						as.add(auth);
					}
				}
				gmuser.setUsername(username);
				gmuser.setPassword(password);
				gmuser.setRealname(relname);
				gmuser.setAuthos(as);
				gmuser.setGmname(gmname);
				gmanager.creatGmuser(gmuser);
				amanager.save(session.getAttribute("username").toString(),
						" �����һgm�˺�:" + username);
				response.sendRedirect("gmuser_list.jsp");
			}
			out.print("<form action='?add=true' method ='post' id ='f1'><table><tr><th>�û���</th><td class='top'> ");
			out.print("<input type='text' name='username' id='username' value='' /></td></tr>");
			out.print("<tr><th>����</th><td><input type='password' name='password' id='password' value='' /></td></tr>");
			out.print("<tr><th>��ʵ����</th><td><input type='text' name='relname' id='relname' /></td></tr>");
			out.print("<tr><th>gm��ɫ��</th><td>��ѡ���ɫ��<select id ='gmname' name='gmname' >");
			for (int i = 0; i < 999; i++) {
				String gn = "GM" + (i > 9 ? "" : "0") + i;
				if (!gmnames.contains(gn)) {
					out.print("<option value='" + gn + "' >" + gn
									+ "</option>");
				}
			}
			out.print("</select></td></tr>");
			out.print("<tr><th>ӵ�н�ɫȨ��</th><td>");
			List<XmlRole> roles = rmanager.getRoles();
			for (XmlRole role : roles) {
				out.print("<input type='checkbox' name='rid' value='"
						+ role.getId() + "' />" + role.getId() + "        "
						+ role.getName());
			}
			out.print("</td></tr><tr><td colspan=2 ><input type='button' onclick='sub();' value='����' />");
			out.print("<input type='button' onclick='window.location.replace(\"gmuser_list.jsp\")' value='����gm�˻��б�' /></td></tr></table></form>");
		%>


	</body>
</html>