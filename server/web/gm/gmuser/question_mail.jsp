<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@page import="java.net.URLEncoder"%>
<%@page import="java.net.URLDecoder"%>
<%@page import="com.xuanzhi.boss.game.model.GamePlayer"%>
<%@include file="../authority.jsp" %>
<%@include file="../header.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<title>潜龙GM邮箱</title>
		<link rel="stylesheet" href="../style.css" />
		<script type="text/javascript">
	function $(tag) {
		return document.getElementById(tag);
	}

	function change(mid, tag) {
		//将子定义的内容填入回复框
		var instr = document.getElementById("repcontent" + mid);
		if (tag.value != "") {
			instr.value = tag.value;
		}
	}

	function del() {
		//删除所选的邮件
		var checks = document.getElementsByName("delmid");
		if (window.confirm("你是否确定要删除此模块")) {
			var str = "?action=del"
			for (i = 0; i < checks.length; i++) {
				if (checks[i].checked == true)
					str = str + "&delid=" + checks[i].value;
			}
			window.location.replace(str);
		}
	}
   function deleteall(){
   //清空问题邮件
   if (window.confirm("你是否确定要删除此模块")) {
     window.location.replace("?action=deleteall");
   }
   }
	function resetm() {
		//重置所有的删除选项
		var checks = document.getElementsByName("delmid");
		for (i = 0; i < checks.length; i++)
			checks[i].checked = false;
	}

	function replay(mid) {
		var str = "?action=add&mid=" + mid;
		var mailId = $(mid + "receiver").value;
		str = str + "&mailid=" + mailId;
		var title = $("restitle" + mid).value;
		str = str + "&title=" + title;
		var content = $("repcontent" + mid).value;
		str = str + "&content=" + content;
		var items = getmselect("item" + mid);
		if (items.length > 6)
			alert("发送的附件超过限制");
		else {
			for (i = 0; i < items.length; i++) {
				str = str + "&item=" + items[i];
			}
			window.location.replace(str);
		}
	}
	function send() {
		var str = "?action=add&mid=1";
		var mailname = $("playname").value;
		str = str + "&playname=" + mailname;
		var title = $("restitle" + 1).value;
		str = str + "&title=" + title;
		var content = $("repcontent" + 1).value;
		str = str + "&content=" + content;
		var items = getmselect("item" + 1);
		var mailid = $("mailid").value;
		str = str+"&mailid="+mailid;
		if (items.length > 6)
			alert("发送的附件超过限制");
		else {
			for (i = 0; i < items.length; i++) {
				str = str + "&item=" + items[i];
			}
			window.location.replace(str);
		}
	}
	function getmselect(tagid) {
		var res = new Array();
		var setag = $(tagid);
		for (i = 0; i < setag.length; i++) {
			if (setag.options[i].selected) {
				res[res.length] = setag.options[i].value;
			}
		}
		return res;
	}
	function query(pn) {
		var paname = document.getElementById("paname").value;
		if (pn)
			paname = pn;
		window.location.replace("question_mail.jsp?paname=" + paname);

	}
	function chooseall() {
		var cas = document.getElementsByName("delmid");
		if (cas) {
			for (i = 0; i < cas.length; i++) {
				cas[i].checked = true;
			}

		}
	}
	function unsetall() {
		var cas = document.getElementsByName("delmid");
		if (cas) {
			for (i = 0; i < cas.length; i++) {
				cas[i].checked = false;
			}

		}
	}
	function queryc() {
		var mcontent = $("mcontent").value;
		if (mcontent)
			window.location.replace("question_mail.jsp?mcontent=" + mcontent);
	}

	function querybyId(pid1) {
		var pid = $("pid").value;
		if (pid1)
			pid = pid1;
		if (pid)
			window.location.replace("question_mail.jsp?pid=" + pid);
	}
	function querybyu(){
	    var uname = $("uname").value;
	    if(uname)
	       window.location.replace("question_mail.jsp?uname="+uname);
	    else
	       alert("请输入正确的信息!");
	}
	function jump() {
		var starty = $("starty").value;
		if (starty) {
			window.location.replace("question_mail.jsp?starty=" + starty);
		} else
			alert("页码不能为空！！！！");
	}
</script>
	</head>
	<body>
		<%
			try {
				out
						.print("<input type='button' value='过滤刷新' onclick='window.location.replace(\"question_mail.jsp?a=update\")' />");
				out
						.print("<input type='button' value='刷新' onclick='window.location.replace(\"question_mail.jsp\")' />");
				GmMailReplay gmreplay = GmMailReplay.getInstance();
				PlayerManager pmanager = PlayerManager.getInstance();
				CustomManager cmanager = CustomManager.getInstance();
				GmItemManager gmanager = GmItemManager.getInstance();
				MailManager mmanager = MailManager.getInstance();
				MailRecordManager mrmanager = MailRecordManager.getInstance();
				ArticleManager acmanager = ArticleManager.getInstance();
				ActionManager amanager = ActionManager.getInstance();
				String updatea = request.getParameter("a");
				if(updatea!=null&&"update".equals(updatea)){
				  gmreplay.setQuestionmails("GM02");
				  out.print("更新成功！！<br/>");
				}
				String username = session.getAttribute("username").toString();
				String action = request.getParameter("action");
				int gmid = pmanager.getPlayer("GM02").getId();
				if (action != null) {
					//操作参数不为空
					if ("del".equals(action)) {
						//做删除操作
						String delmids[] = request.getParameterValues("delid");
						if (delmids != null) {
							gmreplay.deleteMail(delmids);
							amanager.save(username, "删除了 邮件，邮件长度为"
									+ delmids.length);
						}
					}
					if("deleteall".equals(action)){
					 List<Mail> mails1 = gmreplay.getAllGmMail("GM02");
					 String[] delids = new String[mails1.size()];
					 int i=0;
					 for(Mail m:mails1)
					  delids[i++]=m.getId()+"";
					 gmreplay.deleteMail(delids);
					  amanager.save(username,"清空了问题邮件！");
					}
					if ("add".equals(action)) {
						//做回复操作
						boolean res = true;
						int mailtoid = -1;
						String mailId = request.getParameter("mailid");
						Mail mail = null;
						int mid = Integer.parseInt(request.getParameter("mid"));
						if (mid != 1)
							mail = mmanager.getMail(mid);
						if (mailId != null&&mailId.trim().length()>0)
							mailtoid = Integer.parseInt(mailId);
						String playname = request.getParameter("playname");
						
							try {
							    if(playname!=null&&playname.trim().length()>0)
								mailtoid = pmanager.getPlayer(playname.trim())
										.getId();
								String title = request.getParameter("title");
								String content = request
										.getParameter("content");
								String items[] = request
										.getParameterValues("item");
								String message = "";
								String istr = "";
								if (items != null) {
									for (String it : items) {
										istr = istr + "|" + it;
										if (acmanager.getArticle(it.trim()) == null) {
											res = false;
											message = it + "物品不存在";
											break;
										}
									}
								}
								boolean yun = false;
								if (res) {
									if (mid != 1) {
										MailRecord mr = new MailRecord();
										mr.setMid(mid + "");
										mr.setGmname(username);
										mr.setTitle(mail.getTitle());
										mr.setQcontent(mail.getContent());
										mr.setRescontent(content);
										mr.setCdate(DateUtil.formatDate(mail
												.getCreateDate(),
												"yyyy-MM-dd HH:mm:ss"));
										if (pmanager
												.getPlayer(mail.getPoster()) != null) {
											Player p = pmanager.getPlayer(mail
													.getPoster());
											mr.setUsername(p.getUsername());
											mr.setPlayername(p.getName());
										}
										mr.setItems(istr);
										mrmanager.save(mr);
									} else if (mid == 1) {
										MailRecord mr = new MailRecord();
										mr.setMid(mid + "");
										mr.setGmname(username);
										mr.setTitle("主动发送");
										mr.setQcontent("主动发送");
										mr.setRescontent(content);
										mr.setCdate(DateUtil.formatDate(
												new java.util.Date(),
												"yyyy-MM-dd HH:mm:ss"));
										if (pmanager.getPlayer(mailtoid) != null) {
											Player p = pmanager
													.getPlayer(mailtoid);
											mr.setUsername(p.getUsername());
											mr.setPlayername(p.getName());
										}
										mr.setItems(istr);
										mrmanager.save(mr);
									}
									//yun = gmreplay.sendMail(gmid,mid, mailtoid,
									//		items, title, content);
								} else {
									out.print(message);
								}
								if (mid != 1)
									amanager
											.save(username, "给 角色账号【 "
													+ mailtoid + "】问题 ：["
													+ mail.getTitle()
													+ "]问题内容:["
													+ mail.getContent()
													+ "]回复[" + content
													+ "]，回复礼品为[" + istr + "]");
								else if (mid == 1)
									amanager.save(username, "主动给 角色账号【 "
											+ mailtoid + "回复[" + content
											+ "]，回复礼品为[" + istr + "]");
								if (!yun)
									out.print("发送失败！");
							    else
							        out.print("发送成功！");
							} catch (Exception e) {
								e.printStackTrace();
								out.print("角色名称不存在或者角色已删除！！");
							}
						}
					}
				String gmName = session.getAttribute("gmid").toString();//判断是否登录
				String paname = request.getParameter("paname");//获取过滤参数
				String pid = request.getParameter("pid");//获取过滤参数
				String mcontent = request.getParameter("mcontent");
				String uname = request.getParameter("uname");
				int start = 0;
				int size = 20;
				long totalsize = gmreplay.getGmMailSize("GM02");//总数为GM02的邮件
				if (request.getParameter("start") != null)
					start = Integer.parseInt(request.getParameter("start"));
				if (request.getParameter("starty") != null) {
					int starty = Integer.parseInt(request
							.getParameter("starty").trim());
					if (starty < 1 || starty > ((totalsize - 1) / 20 + 1))
						out.print("请输入正确的页码");
					else
						start = (starty - 1) * 20;
				}
				List<Mail> mails = new ArrayList<Mail>();
				if ((pid == null || !"".equals(pid.trim()))
						&& (paname == null || !"".equals(paname.trim()))
						&& (mcontent == null || !"".equals(mcontent.trim()))
						&&(uname == null || !"".equals(uname.trim()))
						)//如果没有过滤过程则获取固定长度的邮件
					mails = gmreplay.getGmMail("GM02", start, size);
			  //显示所有的玩家数		
				  Set<String> usercounts = new HashSet<String>();
				  List<Mail> totalmails = gmreplay.getQuestionmails();
				  for(Mail m:totalmails){
				    usercounts.add(pmanager.getPlayer(m.getPoster()).getUsername());
				  }
				  out.print("<br/>玩家个数为："+usercounts.size()+"<br/>");
				 
				 
				 if(uname!=null&&uname.trim().length()>0){
				  out.print("用户名："+uname);
				  List<Mail> ms = gmreplay.getQuestionmails();//获取所有的GM02邮件
					mails = new ArrayList<Mail>();
					for (Mail m : ms) {//如果邮件的发件人和过滤人相同，则获取！
						try {
							if (uname.equals(pmanager.getPlayer(m.getPoster())
									.getUsername())
									&& m.getStatus() != 2)
								mails.add(m);
						} catch (Exception e) {
							continue;
						}
					}
				 
				 } 
			 	
				if (paname != null && !"".equals(paname)) {//如果过滤参数不为空则获取过滤邮件
					out.print(paname);
					// paname = URLDecoder.decode(paname);
					List<Mail> ms = gmreplay.getQuestionmails();//获取所有的GM02邮件
					mails = new ArrayList<Mail>();
					for (Mail m : ms) {//如果邮件的发件人和过滤人相同，则获取！
						try {
							if (paname.equals(pmanager.getPlayer(m.getPoster())
									.getName())
									&& m.getStatus() != 2)
								mails.add(m);
						} catch (Exception e) {
							continue;
						}
					}

				}
				if (pid != null && !"".equals(pid)) {//如果过滤参数不为空则获取过滤邮件
					out.print(pid);
					// paname = URLDecoder.decode(paname);
					List<Mail> ms = gmreplay.getQuestionmails();//获取所有的GM02邮件
					mails = new ArrayList<Mail>();
					for (Mail m : ms) {//如果邮件的发件人和过滤人相同，则获取！
						try {
							if (m.getPoster() == Integer.parseInt(pid.trim())
									&& m.getStatus() != 2)
								mails.add(m);
						} catch (Exception e) {
							continue;
						}
					}

				}
				if (mcontent != null && !"".equals(mcontent.trim())) {//如果过滤参数不为空则获取过滤邮件
					out.print(mcontent);
					// paname = URLDecoder.decode(paname);
					List<Mail> ms = gmreplay.getQuestionmails();//获取所有的GM02邮件
					mails = new ArrayList<Mail>();
					for (Mail m : ms) {//如果邮件的发件人和过滤人相同，则获取！
						try {
							if (m.getContent().toLowerCase().contains(mcontent.toLowerCase()))
								mails.add(m);
						} catch (Exception e) {
							continue;
						}
					}

				}
				List<String> customs = cmanager.getCustoms();//获取所有自定义回复留言
				List<String> gmitems = gmanager.getItemNames();//获取搜有礼品
				out
						.print("<br/>过滤查询(通过角色名)：<input type='text' id='paname' name ='paname' value='' />");
				out
						.print("<input type='button' value='查询' onclick='query();' /><br/>");
				out
						.print("过滤查询(通过角色ID)：<input type='text' id='pid' name ='pid' value='' />");
				out
						.print("<input type='button' value='查询' onclick='querybyId();' /><br/>");
				out
						.print("过滤查询(通过邮件内容)：<input type='text' id='mcontent' name ='mcontent' value='' />");
				out
						.print("<input type='button' value='查询' onclick='queryc();' /><br/>");
			     out
						.print("过滤查询(通过账号名内容)：<input type='text' id='uname' name ='uname' value='' />");
				out
						.print("<input type='button' value='查询' onclick='querybyu();' /><br/>");
				if (paname == null && mcontent == null && pid == null&&uname==null) {//如果没有过滤则显示分页
					//out.print(start + "|" + totalsize);
					out.print("  <a href='?starty=1'>首页</a>  ");
					if (start > 0)
						out.print("  <a href='?start=" + (start - 20)
								+ "'>上一页</a>  ");
					out.print((start / 20 + 1) + "/"
							+ ((totalsize - 1) / 20 + 1));
					if ((start + 20) < totalsize)
						out.print("  <a href='?start=" + (start + 20)
								+ "' >下一页</a>  ");
					out.print("  <a href='?starty=" + ((totalsize - 1) / 20 + 1)
							+ "'>末页</a>  ");
					out
							.print("转到<input type='text' size=5 value='' name='starty' id='starty' /><a href='javascript:jump();'>页</a>");
				} else
					out.print("过滤长度为：" + mails.size());
				StringBuffer tablehead = new StringBuffer(
						"<form action=\"\" name=\"form2\" method=\"post\"><input type=\"hidden\" name=playerName value=\""
								+ gmName
								+ "\" /> <input type=hidden name=stype value=\"delete\">");
				tablehead.append("<table style='TABLE-LAYOUT: fixed; WORD-WRAP: break-word' width='100%' ><tr >");
				tablehead.append("<th  width='5%' nowrap=\"nowrap\">ID</td>");
				tablehead.append("<th  width='5%' nowrap=\"nowrap\">账号</th>");
				tablehead.append("<th  width='5%' nowrap=\"nowrap\">发件时间</th>");
				tablehead.append("<th  width='10%' nowrap=\"nowrap\">发件人</th>");
				tablehead.append("<th  width='10%' nowrap=\"nowrap\">标题</th>");
				tablehead.append("<th  width='20%' nowrap=\"nowrap\">内容</th>");
				tablehead.append("<th  width='15%' nowrap=\"nowrap\">附件</th>");
				tablehead
						.append("<th  width='20%' nowrap=\"nowrap\">回复内容</th>");
				tablehead
						.append("<th  width='10%' nowrap=\"nowrap\">操作</th></tr>");
				out.print(tablehead.toString());
				if (mails != null) {
					for (Mail ml : mails) {//遍历邮件
						
							Mail m = mmanager.getMail(ml.getId());
							StringBuffer tablebody = new StringBuffer("<tr ");
							if (m.getStatus() == 1)
								tablebody.append("style='color:red'  ");
							tablebody.append("><td  ");
							if (m.getStatus() == 1)
								tablebody.append("style='color:red'  ");
							tablebody.append(">" + m.getStatus());
							tablebody.append(
									" <input type='checkbox' name='delmid' value='"
											+ m.getId() + "'").append(
									"/>" + m.getId() + "</td>");
							try {
							     tablebody.append("<td><a href='?uname="+pmanager.getPlayer(m.getPoster())
											.getUsername()+"' >"
									+ pmanager.getPlayer(m.getPoster())
											.getUsername()
									+ "</a></td><td>"
									+ DateUtil.formatDate(m.getCreateDate(),
											"yyyy-MM-dd HH:mm:ss") + "</td>");
							   tablebody.append("<td ");
							   if (m.getStatus() == 1)
								tablebody.append("  style='color:red'  ");
							    tablebody
									.append("><a href='javascript:querybyId(\""
											+ m.getPoster()
											+ "\");' >"
											+ pmanager.getPlayer(m.getPoster())
													.getName()
											+ "("
											+ m.getPoster()
											+ ")</a><input type='button' value='聊天' onclick='window.location.replace(\"gm_chat.jsp?playerid="
											+ ml.getPoster()
											+ "\")' /><input type='hidden' id='"
											+ m.getId() + "receiver' value='"
											+ m.getPoster() + "'</td>");
											} catch (Exception e) {
							     tablebody.append("<td>角色名不存在</td><td>"
									+ DateUtil.formatDate(m.getCreateDate(),
											"yyyy-MM-dd HH:mm:ss") + "</td>");
							   tablebody.append("<td ");
							   if (m.getStatus() == 1)
								tablebody.append("  style='color:red'  ");
							    tablebody
									.append("><a href='javascript:querybyId(\""
											+ m.getPoster()
											+ "\");' >角色名不存在("
											+ m.getPoster()
											+ ")</a><input type='button' value='聊天' onclick='window.location.replace(\"gm_chat.jsp?playerid="
											+ ml.getPoster()
											+ "\")' /><input type='hidden' id='"
											+ m.getId() + "receiver' value='"
											+ m.getPoster() + "'</td>");
						}
							tablebody.append("<td  ");
							if (m.getStatus() == 1)
								tablebody.append("  style='color:red'  ");
							tablebody.append(">" + m.getTitle()
									+ "<input type='hidden' id='restitle"
									+ m.getId() + "' value='回复:" + m.getTitle()
									+ "' /></td><td  ");
							if (m.getStatus() == 1)
								tablebody.append("style='color:red'  ");
							tablebody.append(" ><div>" + m.getContent()
									+ "</div></td><td>");
							tablebody
									.append("<select name='item' id='item"
											+ m.getId()
											+ "' multiple=true  size='5' >");
							if (gmitems != null && gmitems.size() > 0) {
								for (String item : gmitems)
									//遍历gm礼品
									tablebody.append("<option value='" + item
											+ "' >" + item + "</option>");
							}
							tablebody.append("</select></td><td>");
							tablebody
									.append("<select name='repcus' id ='repcus' style='width:98%' onchange='change("
											+ m.getId()
											+ ",this);' ><option value=''>--</option>");
							if (customs != null && customs.size() > 0) {
								for (String cu : customs)
									//遍历gm自定义数据
									tablebody.append("<option value='" + cu
											+ "'>" + cu + "</option>");
							}
							tablebody.append("</select>");
							tablebody
									.append("<textarea style='width:98%' cows='5' name ='repcontent' id='repcontent"
											+ m.getId()
											+ "' value=''></textarea>");

							tablebody.append("</td><td>");
							tablebody
									.append("<input type='button' value='回复' onclick='replay("
											+ m.getId() + ")' /></td></tr>");
							out.print(tablebody.toString());
						
					}

				}

				out
						.print("<tr><td colspan='10' ><input type='button' value='全选' onclick='chooseall();'/><input type='button' value='全消' onclick='unsetall();'/><input type='button' value='删除选中的' onclick='del();'/><input type='button' value='重置' onclick='resetm();' /><input type='button' value='邮件清空' onclick='deleteall();' /></td></tr></table></form>");
				//跳到下载页面
				out.print("<form action='question_down1.jsp' method='get'>");
				if (pid != null && !"".equals(pid.trim()))
					out
							.print("<input type='hidden' id='pid' name='pid' value='"
									+ pid + "' />");
				if (mcontent != null && !"".equals(mcontent.trim()))
					out.print("<input type='hidden' name='mcontent' value='"
							+ mcontent + "' />");
				out
						.print("<input type='hidden' name='gname' id='gname' value='GM02' />");
				out.print("<input type='submit' value='下载' /></form>");
				
				if (paname == null && mcontent == null && pid == null) {//如果没有过滤则显示分页
					//out.print(start + "|" + totalsize);
					out.print("  <a href='?starty=1'>首页</a>  ");
					if (start > 0)
						out.print("  <a href='?start=" + (start - 20)
								+ "'>上一页</a>  ");
					out.print((start / 20 + 1) + "/"
							+ ((totalsize - 1) / 20 + 1));
					if ((start + 20) < totalsize)
						out.print("  <a href='?start=" + (start + 20)
								+ "' >下一页</a>  ");
					out.print("  <a href='?starty=" + ((totalsize - 1) / 20 + 1)
							+ "'>末页</a>  ");
					out
							.print("转到<input type='text' size=5 value='' name='starty' id='starty' /><a href='javascript:jump();'>页</a>");
				}
				//选择发送
				out
						.print("<br/><br/><br/><table width='60%' ><tr><th>角色名</th><td class='top'><input type='text' id='playname' name='playname' value=''/> 或ID:<input type ='text' id='mailid' name='mailid' value='' /></td></tr>");
				out
						.print("<tr><th>标题</th><td><input type='text' id='restitle1' value='有问题' /></td></tr>");
				out
						.print("<tr><th>礼品</th><td><select name='item' id='item1' multiple=true size='5' >");
				for (String item : gmitems)
					out.print("<option value='" + item + "' >" + item
							+ "</option>");
				out.print("</select></td></tr><tr><th>回复内容</th><td>");
				out
						.print("<select name='repcus'  style='width:50%'  id ='repcus' onchange='change(1,this);' ><option value=''>--</option>");
				for (String cu : customs)
					out.print("<option value='" + cu + "'>" + cu + "</option>");
				out
						.print("</select><br/><textarea  style='width:50%' rows='5' name ='repcontent' id='repcontent1' value=''></textarea>");
				out.print("</td></tr>");
				out
						.print("<tr><td colspan=2 ><input type='button' value='发送' onclick='send()' /></td></tr></table>");
			} catch (Exception e) {
				  out.print(StringUtil.getStackTrace(e));
				//RequestDispatcher rdp = request
				//		.getRequestDispatcher("visitfobiden.jsp");
				//rdp.forward(request, response);

			}
		%>
	</body>