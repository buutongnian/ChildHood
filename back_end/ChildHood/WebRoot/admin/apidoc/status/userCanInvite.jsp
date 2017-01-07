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
<title>API文档-状态模块-用户请求周围可邀请用户列表</title>
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
        <li class="active"> <a href="status.jsp"><i class="icon-chevron-down"></i> 状态模块</a> </li>
        <li> <a href="amUserList.jsp"><i class="icon-chevron-right"></i> 匿名用户请求周围用户列表</a> </li>
        <li class="active"> <a href="userCanInvite.jsp"><i class="icon-chevron-right"></i> 用户请求周围可邀请用户列表</a> </li>
        <li> <a href="gameCanJoin.jsp"><i class="icon-chevron-right"></i> 用户请求周围可加入游戏列表</a> </li>
      </ul>
    </div>
    <!--/span-->
    <div class="span9" id="content">
      <div class="row-fluid"> 
        <!-- block -->
        <div class="block">
          <div class="navbar navbar-inner block-header">
            <div class="muted pull-left">用户请求周围可邀请用户列表</div>
          </div>
          <div style="padding-top:10px;padding-left:16px;padding-right:16px;">
            <p><strong>所属模块：</strong></p>
            <ul>
              <li>状态模块</li>
            </ul>
            <p><strong>简要描述：</strong></p>
            <ul>
              <li>用户请求周围可加入游戏列表</li>
            </ul>
            <p><strong>请求URL：</strong></p>
            <ul>
              <li><code>http://123.57.52.135:8080/userCanInvite.do</code></li>
            </ul>
            <p><strong>请求方式：</strong></p>
            <ul>
              <li>GET</li>
            </ul>
            <p><strong>参数：</strong></p>
            <table class="table table-bordered table-hover">
              <thead>
                <tr style="color: rgb(255, 255, 255); background-color: rgb(0, 136, 204);">
                  <th style="min-width: 77px;">参数名</th>
                  <th style="min-width: 77px;">必选</th>
                  <th style="min-width: 77px;">类型</th>
                  <th style="min-width: 77px;">说明</th>
                </tr>
              </thead>
              <tbody>
                <tr>
                  <td>webOrApp</td>
                  <td>是</td>
                  <td>string</td>
                  <td>请求终端</td>
                </tr>
                <tr>
                  <td>userName</td>
                  <td>是</td>
                  <td>string</td>
                  <td>用户名</td>
                </tr>
              </tbody>
            </table>
            <p><strong>返回示例</strong></p>
            <pre><code class=" hljs json">{
    "<span class="hljs-attribute">messageCode</span>": <span class="hljs-value"><span class="hljs-string">"C001"</span></span>,
    "<span class="hljs-attribute">messageContent</span>": <span class="hljs-value">[
        {
            "<span class="hljs-attribute">count</span>": <span class="hljs-value"><span class="hljs-number">1</span></span>,
            "<span class="hljs-attribute">dataList</span>": <span class="hljs-value">[
                {
                    "<span class="hljs-attribute">userName</span>": <span class="hljs-value"><span class="hljs-string">"lcc"</span></span>,
                    "<span class="hljs-attribute">userNickname</span>": <span class="hljs-value"><span class="hljs-string">"刘诚诚"</span></span>,
                    "<span class="hljs-attribute">lastLatitude</span>": <span class="hljs-value"><span class="hljs-number">39.997637</span></span>,
                    "<span class="hljs-attribute">lastLogitude</span>": <span class="hljs-value"><span class="hljs-number">116.434751</span></span>,
                    "<span class="hljs-attribute">distance</span>": <span class="hljs-value"><span class="hljs-number">132</span>
                </span>}
            ]
        </span>}
    ]
</span>}</code></pre>
            <p><strong>返回参数说明</strong></p>
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
                  <td>返回消息头信息</td>
                </tr>
                <tr>
                  <td style="text-align: left;">messageContent</td>
                  <td style="text-align: left;">InfoList
                    <computecaninvite></computecaninvite></td>
                  <td>返回消息</td>
                </tr>
              </tbody>
            </table>
            <p><strong>备注</strong></p>
            <ul>
              <li>返回消息头信息详情见C.java常量文件</li>
              <li>返回信息为附近可加入游戏列表，对应InfoList&lt;computecaninvite&gt;类
              </li>
            </ul>
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