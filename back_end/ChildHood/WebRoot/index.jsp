<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%
	if (session.getAttribute("userName") == null) {
		response.sendRedirect(request.getContextPath()
				+ "/admin/login.jsp");
	}
%>
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'index.jsp' starting page</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	
  </head>
  
  <body>
    <h2>It works!</h2>
    <form action="uploadHeadImage.do?webOrApp=app&userName=joe" enctype="multipart/form-data" method="post">
    	<textarea name="userHeadImage"></textarea>
    	<input type="submit" value="upload"/>
    </form>
  </body>
</html>
