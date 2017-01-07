<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html lang="en" class="no-js">

<head>
<meta charset="utf-8">
<title>童年-后台管理</title>
<meta charset="utf-8"/>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="description" content="">
<meta name="author" content="">

<!-- CSS -->
<link rel="stylesheet" href="css/reset.css">
<link rel="stylesheet" href="css/supersized.css">
<link rel="stylesheet" href="css/style.css">

<!-- HTML5 shim, for IE6-8 support of HTML5 elements -->
<!--[if lt IE 9]>
            <script src="http://html5shim.googlecode.com/svn/trunk/html5.js"></script>
        <![endif]-->

<!-- Javascript -->
<script src="js/jquery-1.7.2.min.js" type="text/javascript"></script>
<script src="js/supersized.3.2.7.min.js"></script>
<script src="js/supersized-init.js"></script>

</head>

<body oncontextmenu="return false">
<script>
window.onload = function() {
			$(".connect p").eq(0).animate({
				"left" : "0%"
			}, 600);
			$(".connect p").eq(1).animate({
				"left" : "0%"
			}, 400);
		}
		function is_hide() {
			$(".alert").animate({
				"top" : "-40%"
			}, 300)
		}
		function is_show() {
			$(".alert").show().animate({
				"top" : "45%"
			}, 300)
		}
</script>
	<%
	String err=(String)session.getAttribute("errorMsg");
	if(err!=null){
		if(err.equals("username")){
	%>
	<script>
		$(document).ready(function(){
			$("#ts").html("用户名错误");
				is_show();
		})
	</script>
	<%
		session.removeAttribute("errorMsg");
		}else if(err.equals("password")){
	%>
	<script>
		$(document).ready(function(){
			$("#ts").html("密码错误");
				is_show();
		})
	</script>
	<%
		session.removeAttribute("errorMsg");
		}
	}
	String userName=(String)session.getAttribute("userName");
	if(userName!=null){
		response.sendRedirect(request.getContextPath()+"/admin/index.jsp");	
	}
 	%>
	<div class="page-container">
		<h1>童 年 - 后 台 管 理</h1>
		<form action="adminLogin.do" method="post">
			<div>
				<s:fielderror theme="simple" fieldName="errorField"></s:fielderror>
				<input type="text" name="userName" class="username"
					placeholder="Username" autocomplete="off" />
			</div>
			<div>
				<input type="password" name="userPwd" class="password"
					placeholder="Password" oncontextmenu="return false"
					onpaste="return false" />
			</div>
			<button id="submit" type="submit">登 录</button>
		</form>
		<div class="connect">
			<p>One dream , from childhood to the old.</p>
			<p style="margin-top:20px;">一 个 梦 - 从 童 年 做 到 老</p>
		</div>
	</div>
	<div class="alert" style="display:none">
		<h2>消息</h2>
		<div class="alert_con">
			<p id="ts"></p>
			<p style="line-height:70px">
				<a class="btn">确定</a>
			</p>
		</div>
	</div>
	<script>
		$(".btn").click(function() {
			is_hide();
		})
		var u = $("input[name=userName]");
		var p = $("input[name=userPwd]");
		$("#submit").live('click', function() {
			if (u.val() == '' || p.val() == '') {
				$("#ts").html("用户名或密码不能为空");
				is_show();
				return false;
			}
		});
	</script>
</body>

</html>
