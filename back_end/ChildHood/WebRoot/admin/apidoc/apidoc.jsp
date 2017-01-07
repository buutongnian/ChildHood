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
<meta charset="utf-8"/>
<title>API文档</title>
<!-- Bootstrap -->
<link href="../bootstrap/css/bootstrap.min.css" rel="stylesheet"
	media="screen">
<link href="../bootstrap/css/bootstrap-responsive.min.css"
	rel="stylesheet" media="screen">
<link href="../assets/styles.css" rel="stylesheet" media="screen">
<link href="../assets/DT_bootstrap.css" rel="stylesheet" media="screen">
<!-- HTML5 shim, for IE6-8 support of HTML5 elements -->
<!--[if lt IE 9]>
            <script src="http://html5shim.googlecode.com/svn/trunk/html5.js"></script>
        <![endif]-->
<script src="../vendors/modernizr-2.6.2-respond-1.1.0.min.js"></script>
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
								<li><a tabindex="-1" href="adminLogout.do">退出 </a></li>
							</ul></li>
					</ul>
					<ul class="nav">
						<li class=""><a href="../index.jsp">概况</a></li>
						<li><a href="queryVersion.do">版本管理</a></li>
						<li class=""><a href="getFeedback.do?webOrApp=web&moduleName=all&pageNum=1">客户服务</a></li>
						<li class="active"><a href="apidoc.jsp">API文档</a></li>
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
					<li class="active"><a href="apidoc.html"><i
							class="icon-chevron-down"></i> 模块概览</a></li>
					<li><a href="find/find.jsp"><i class="icon-chevron-right"></i>
							寻找童年小伙伴模块</a></li>
					<li><a href="game/game.jsp"><i class="icon-chevron-right"></i>
							游戏规则模块</a></li>
					<li><a href="login/login.jsp"><i
							class="icon-chevron-right"></i> 登录及注册模块</a></li>
					<li><a href="my/my.jsp"><i class="icon-chevron-right"></i>
							我的信息模块</a></li>
					<li><a href="onekey/onekey.jsp"><i
							class="icon-chevron-right"></i> 一键呼唤模块</a></li>
					<li><a href="rank/rank.jsp"><i class="icon-chevron-right"></i>
							排行模块</a></li>
					<li><a href="status/status.jsp"><i
							class="icon-chevron-right"></i> 状态模块</a></li>
					<li><a href="version/version.jsp"><i
							class="icon-chevron-right"></i> 版本更新模块</a></li>
				</ul>
			</div>
			<!--/span-->
			<div class="span9" id="content">
				<div class="row-fluid">
					<!-- block -->
					<div class="block">
						<div class="navbar navbar-inner block-header">
							<div class="muted pull-left">游戏规则模块</div>
						</div>
						<div class="block-content collapse in">
							<div class="span12">
								<table class="table table-bordered">
									<thead>
										<tr>
											<th>模块中文名</th>
											<th>模块英文名</th>
											<th>简介</th>
										</tr>
									</thead>
									<tbody>
										<tr>
											<td>寻找童年小伙伴模块</td>
											<td>find</td>
											<td>家长界面，寻找童年小伙伴各功能对应API</td>
										</tr>
										<tr>
											<td>游戏规则模块</td>
											<td>game</td>
											<td>儿童界面，游戏规则模块各功能对应API</td>
										</tr>
										<tr>
											<td>登录及注册模块</td>
											<td>login</td>
											<td>用户登录、注册和找回密码等功能对应API</td>
										</tr>
										<tr>
											<td>我的信息模块</td>
											<td>my</td>
											<td>用户信息，包含注册信息、详细信息、家长信息、孩子信息的各操作对应API</td>
										</tr>
										<tr>
											<td>一键呼唤模块</td>
											<td>onekey</td>
											<td>一键呼唤，用于发起游戏、加入周围游戏、取消/退出游戏等各功能对应API</td>
										</tr>
										<tr>
											<td>排行模块</td>
											<td>rank</td>
											<td>排行模块，用于获取用户所属大区的游戏排行和孩子王排行数据对应API</td>
										</tr>
										<tr>
											<td>状态模块</td>
											<td>status</td>
											<td>状态模块，用于获得匿名/登录用户周围玩家及游戏对应API</td>
										</tr>
										<tr>
											<td>版本更新模块</td>
											<td>version</td>
											<td>用于检查更新</td>
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
	<script src="../vendors/jquery-1.9.1.min.js"></script>
	<script src="../vendors/jquery-ui-1.10.3.js"></script>
	<script src="../bootstrap/js/bootstrap.min.js"></script>
</body>

</html>