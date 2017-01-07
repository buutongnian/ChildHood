<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%> <%
String path = request.getContextPath(); String basePath =
request.getScheme() + "://" + request.getServerName() + ":" +
request.getServerPort() + path + "/"; %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<% if (session.getAttribute("userName") == null) {
response.sendRedirect(request.getContextPath() + "/admin/login.jsp"); }
%>
<html>

<head>
<meta charset="utf-8"/>
<title>API文档-一键呼唤模块</title>
<!-- Bootstrap -->
<link href="../../bootstrap/css/bootstrap.min.css" rel="stylesheet"
	media="screen">
<link href="../../bootstrap/css/bootstrap-responsive.min.css"
	rel="stylesheet" media="screen">
<link href="../../assets/styles.css" rel="stylesheet" media="screen">
<link href="../../assets/DT_bootstrap.css" rel="stylesheet"
	media="screen">
<!-- HTML5 shim, for IE6-8 support of HTML5 elements -->
<!--[if lt IE 9]>
            <script src="http://html5shim.googlecode.com/svn/trunk/html5.js"></script>
        <![endif]-->
<script src="../../vendors/modernizr-2.6.2-respond-1.1.0.min.js"></script>
</head>

<body>
	<div class="navbar navbar-fixed-top">
		<div class="navbar-inner">
			<div class="container-fluid">
				<a class="btn btn-navbar" data-toggle="collapse"
					data-target=".nav-collapse"> <span class="icon-bar"></span> <span
					class="icon-bar"></span> <span class="icon-bar"></span>
				</a> <a class="brand" href="#">童 年</a>
				<div class="nav-collapse collapse">
					<ul class="nav pull-right">
						<li class="dropdown"><a href="#" role="button"
							class="dropdown-toggle" data-toggle="dropdown"> <i
								class="icon-user"></i> ${userName} <i class="caret"></i>

						</a>
							<ul class="dropdown-menu">
								<li><a tabindex="-1" href="adminLogout.do">退出</a></li>
							</ul></li>
					</ul>
					<ul class="nav">
						<li class=""><a href="../../index.jsp">概况</a></li>
						<li><a href="queryVersion.do">版本管理</a></li>
						<li class=""><a href="getFeedback.do?webOrApp=web&moduleName=all&pageNum=1">客户服务</a></li>
						<li class="active"><a href="../apidoc.jsp">API文档</a></li>
					</ul>
				</div>
				<!--/.nav-collapse -->
			</div>
		</div>
	</div>
	<div class="container-fluid">
		<div class="row-fluid">
			<div class="span3" id="sidebar" role="navigation">
				<ul class="nav nav-list bs-docs-sidenav nav-collapse collapse nav">
					<li class="active"><a href="onekey.jsp"><i
							class="icon-chevron-down"></i> 一键呼唤模块</a></li>
					<li><a href="onekeyConvene.jsp"><i
							class="icon-chevron-right"></i> 一键呼唤</a></li>
					<li><a href="startGame.jsp"><i class="icon-chevron-right"></i>
							开始游戏</a></li>
					<li><a href="joinGame.jsp"><i class="icon-chevron-right"></i>
							加入游戏</a></li>
					<li><a href="changeGameInfo.jsp"><i
							class="icon-chevron-right"></i> 更改游戏信息</a></li>
					<li><a href="founderCancelGame.jsp"><i
							class="icon-chevron-right"></i> 游戏发起者取消/解散游戏</a></li>
					<li><a href="finishGame.jsp"><i class="icon-chevron-right"></i>
							结束游戏</a></li>
					<li><a href="userExitGame.jsp"><i
							class="icon-chevron-right"></i> 玩家取消/退出游戏</a></li>
					<li><a href="scoreGame.jsp"><i class="icon-chevron-right"></i>
							评价游戏</a></li>
				</ul>
			</div>
			<!--/span-->
			<div class="span9" id="content">
				<div class="row-fluid">
					<!-- block -->
					<div class="block">
						<div class="navbar navbar-inner block-header">
							<div class="muted pull-left">一键呼唤模块</div>
						</div>
						<div class="block-content collapse in">
							<div class="span12">
								<table class="table table-bordered">
									<thead>
										<tr>
											<th>API中文名</th>
											<th>API英文名</th>
											<th>简介</th>
										</tr>
									</thead>
									<tbody>
										<tr>
											<td>一键呼唤</td>
											<td>onekeyConvene</td>
											<td>用于APP中呼唤按钮请求API</td>
										</tr>
										<tr>
											<td>开始游戏</td>
											<td>startGame</td>
											<td>游戏发起者在人数符合最低人数时可开始游戏，开始游戏所请求API</td>
										</tr>
										<tr>
											<td>加入游戏</td>
											<td>joinGame</td>
											<td>用户在地图上选择游戏加入，或同意邀请后请求API</td>
										</tr>
										<tr>
											<td>更改游戏信息</td>
											<td>changeGameInfo</td>
											<td>游戏开始后，游戏发起者可以更改自定义信息，更改后请求该API更新数据库记录</td>
										</tr>
										<tr>
											<td>游戏发起者取消/解散游戏</td>
											<td>founderCancelGame</td>
											<td>游戏发起者取消或解散游戏，请求该API更新数据库中用户状态</td>
										</tr>
										<tr>
											<td>结束游戏</td>
											<td>finishGame</td>
											<td>游戏发起者正常结束游戏，请求该API更新数据库中用户状态</td>
										</tr>
										<tr>
											<td>玩家取消/退出游戏</td>
											<td>userExitGame</td>
											<td>玩家退出游戏时请求该API刷新数据库内状态</td>
										</tr>
										<tr>
											<td>评价游戏</td>
											<td>scoreGame</td>
											<td>用户正常完成游戏后会收到评价推送消息，评价后请求该API更新数据库中用户评分记录</td>
										</tr>
									</tbody>
								</table>
							</div>
						</div>
					</div>
					<!-- /block -->
				</div>
			</div>
		</div>
		<hr>
		<center>
			<footer>
			<p>&copy; 童年 2016</p>
			</footer>
		</center>
	</div>
	<!--/.fluid-container-->
	<script src="../../vendors/jquery-1.9.1.min.js"></script>
	<script src="../../vendors/jquery-ui-1.10.3.js"></script>
	<script src="../../bootstrap/js/bootstrap.min.js"></script>
</body>

</html>