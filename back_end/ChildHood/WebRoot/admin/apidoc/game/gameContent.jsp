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
<title>API文档-游戏规则模块-游戏规则内容信息</title>
<!-- Bootstrap -->
<link href="../../bootstrap/css/bootstrap.min.css" rel="stylesheet" media="screen">
<link href="../../bootstrap/css/bootstrap-responsive.min.css" rel="stylesheet" media="screen">
<link href="../../assets/styles.css" rel="stylesheet" media="screen">
<link href="../../assets/DT_bootstrap.css" rel="stylesheet" media="screen">
<!-- HTML5 shim, for IE6-8 support of HTML5 elements -->
<!--[if lt IE 9]>
            <script src="http://html5shim.googlecode.com/svn/trunk/html5.js"></script>
        <![endif]-->
<script src="../../vendors/modernizr-2.6.2-respond-1.1.0.min.js"></script>
</head>

<body>
<div class="navbar navbar-fixed-top">
  <div class="navbar-inner">
    <div class="container-fluid"> <a class="btn btn-navbar" data-toggle="collapse" data-target=".nav-collapse"> <span class="icon-bar"></span> <span class="icon-bar"></span> <span class="icon-bar"></span> </a> <a class="brand" href="#">童 年</a>
      <div class="nav-collapse collapse">
        <ul class="nav pull-right">
          <li class="dropdown"> <a href="#" role="button" class="dropdown-toggle" data-toggle="dropdown"> <i class="icon-user"></i> ${userName} <i class="caret"></i> </a>
            <ul class="dropdown-menu">
              <li> <a tabindex="-1" href="adminLogout.do">退出</a> </li>
            </ul>
          </li>
        </ul>
        <ul class="nav">
          <li class=""> <a href="../../index.jsp">概况</a> </li>
          <li> <a href="queryVersion.do">版本管理</a> </li>
          <li class=""><a href="getFeedback.do?webOrApp=web&moduleName=all&pageNum=1">客户服务</a></li>
          <li class="active"> <a href="../apidoc.jsp">API文档</a> </li>
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
        <li class="active"> <a href="game.jsp"><i class="icon-chevron-down"></i> 游戏规则模块</a> </li>
        <li> <a href="gameHead.jsp"><i class="icon-chevron-right"></i> 游戏规则头信息</a> </li>
        <li class="active"> <a href="gameContent.jsp"><i class="icon-chevron-right"></i> 游戏规则内容信息</a> </li>
      </ul>
    </div>
    <!--/span-->
    <div class="span9" id="content">
      <div class="row-fluid"> 
        <!-- block -->
        <div class="block">
          <div class="navbar navbar-inner block-header">
            <div class="muted pull-left">游戏规则内容信息</div>
          </div>
          <div style="padding-top:10px;padding-left:16px;padding-right:16px;">
            <p><strong>所属模块：</strong></p>
            <ul>
              <li>游戏规则模块</li>
            </ul>
            <p><strong>简要描述：</strong> </p>
            <ul>
              <li>游戏规则内容信息</li>
            </ul>
            <p><strong>请求URL：</strong> </p>
            <ul>
              <li><code>http://123.57.52.135:8080/gameContent.do</code></li>
            </ul>
            <p><strong>请求方式：</strong></p>
            <ul>
              <li>GET </li>
            </ul>
            <p><strong>参数：</strong> </p>
            <table class="table table-bordered table-hover">
              <thead>
                <tr style="color: rgb(255, 255, 255); background-color: rgb(0, 136, 204);">
                  <th style="text-align: left; min-width: 77px;">参数名</th>
                  <th style="text-align: left; min-width: 77px;">必选</th>
                  <th style="text-align: left; min-width: 77px;">类型</th>
                  <th style="min-width: 77px;">说明</th>
                </tr>
              </thead>
              <tbody>
                <tr>
                  <td style="text-align: left;">webOrApp</td>
                  <td style="text-align: left;">是</td>
                  <td style="text-align: left;">string</td>
                  <td>请求终端</td>
                </tr>
                <tr>
                  <td style="text-align: left;">gameCode</td>
                  <td style="text-align: left;">是</td>
                  <td style="text-align: left;">int</td>
                  <td>游戏编码</td>
                </tr>
              </tbody>
            </table>
            <p><strong>返回示例</strong></p>
            <pre><code class=" hljs json"> {
    "<span class="hljs-attribute">messageCode</span>": <span class="hljs-value"><span class="hljs-string">"G001"</span></span>,
    "<span class="hljs-attribute">messageContent</span>": <span class="hljs-value">[
        {
            "<span class="hljs-attribute">gameCode</span>": <span class="hljs-value"><span class="hljs-number">1</span></span>,
            "<span class="hljs-attribute">gameContent</span>": <span class="hljs-value"><span class="hljs-string">"测试内容"</span></span>,
            "<span class="hljs-attribute">enable</span>": <span class="hljs-value"><span class="hljs-string">"Y"</span>
        </span>}
    ]
</span>}
</code></pre>
            <p><strong>返回参数说明</strong> </p>
            <table class="table table-bordered table-hover">
              <thead>
                <tr style="color: rgb(255, 255, 255); background-color: rgb(0, 136, 204);">
                  <th style="text-align: left; min-width: 77px;">参数名</th>
                  <th style="text-align: left; min-width: 77px;">类型</th>
                  <th style="min-width: 77px;">说明</th>
                </tr>
              </thead>
              <tbody>
                <tr>
                  <td style="text-align: left;">messageCode</td>
                  <td style="text-align: left;">String</td>
                  <td>消息头编码信息</td>
                </tr>
                <tr>
                  <td style="text-align: left;">messageContent</td>
                  <td style="text-align: left;">GameContent</td>
                  <td>游戏内容对象</td>
                </tr>
                <tr>
                  <td style="text-align: left;">gameCode</td>
                  <td style="text-align: left;">Int</td>
                  <td>游戏编码</td>
                </tr>
                <tr>
                  <td style="text-align: left;">gameContent</td>
                  <td style="text-align: left;">String</td>
                  <td>游戏内容</td>
                </tr>
                <tr>
                  <td style="text-align: left;">enable</td>
                  <td style="text-align: left;">Char</td>
                  <td>在此API中无实际意义</td>
                </tr>
              </tbody>
            </table>
            <p><strong>备注</strong> </p>
            <p>-消息头编码信息在java.c文件当中查看</p>
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