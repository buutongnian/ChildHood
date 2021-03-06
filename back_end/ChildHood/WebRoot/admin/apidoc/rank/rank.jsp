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
        <title>API文档-排行模块</title>
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
                <div class="container-fluid">
                    <a class="btn btn-navbar" data-toggle="collapse" data-target=".nav-collapse"> <span class="icon-bar"></span>
                     <span class="icon-bar"></span>
                     <span class="icon-bar"></span>
                    </a>
                    <a class="brand" href="#">童 年</a>
                    <div class="nav-collapse collapse">
                        <ul class="nav pull-right">
                            <li class="dropdown">
                                <a href="#" role="button" class="dropdown-toggle" data-toggle="dropdown"> <i class="icon-user"></i> ${userName} <i class="caret"></i>

                                </a>
                                <ul class="dropdown-menu">
                                    <li>
                                        <a tabindex="-1" href="adminLogout.do">退出</a>
                                    </li>
                                </ul>
                            </li>
                        </ul>
                        <ul class="nav">
                            <li class="">
                                <a href="../../index.jsp">概况</a>
                            </li>
                          	<li>
                                <a href="queryVersion.do">版本管理</a>
                            </li>
                            <li class=""><a href="getFeedback.do?webOrApp=web&moduleName=all&pageNum=1">客户服务</a></li>
                            <li class="active">
                                <a href="../apidoc.jsp">API文档</a>
                            </li>
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
                        <li class="active">
                            <a href="rank.jsp"><i class="icon-chevron-down"></i> 排行模块</a>
                        </li>
                        <li>
                            <a href="userRank.jsp"><i class="icon-chevron-right"></i> 用户排行</a>
                        </li>
                        <li>
                            <a href="gameRank.jsp"><i class="icon-chevron-right"></i> 游戏排行</a>
                        </li>
                    </ul>
                </div>
                <!--/span-->
                  <div class="span9" id="content">
                      <div class="row-fluid">
                          <!-- block -->
                          <div class="block">
                            <div class="navbar navbar-inner block-header">
                                <div class="muted pull-left">排行模块</div>
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
						                  <td>用户排行</td>
						                  <td>userRank</td>
						                  <td>获取用户所在大区孩子王排行列表，分页信息</td>
						                </tr>
						                <tr>
						                  <td>游戏排行</td>
						                  <td>startGame</td>
						                  <td>获取用户所在大区游戏排行列表，分页信息</td>
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