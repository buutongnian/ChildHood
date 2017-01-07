<%@ page language="java"
	import="java.util.*,edu.buu.childhood.version.pojo.VersionBean"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
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
<title>童年-后台管理页面</title>
<!-- Bootstrap -->
<link href="bootstrap/css/bootstrap.min.css" rel="stylesheet"
	media="screen">
<link href="bootstrap/css/bootstrap-responsive.min.css" rel="stylesheet"
	media="screen">
<link href="assets/styles.css" rel="stylesheet" media="screen">
<link href="css/upload.css" rel="stylesheet" media="screen">
<!-- HTML5 shim, for IE6-8 support of HTML5 elements -->
<!--[if lt IE 9]>
            <script src="http://html5shim.googlecode.com/svn/trunk/html5.js"></script>
        <![endif]-->
<script src="vendors/jquery-1.9.1.min.js"></script>
<script src="vendors/jquery-ui-1.10.3.js"></script>
<script src="bootstrap/js/bootstrap.min.js"></script>
<script src="vendors/modernizr-2.6.2-respond-1.1.0.min.js"></script>

</head>

<body>
<div class="navbar navbar-fixed-top">
  <div class="navbar-inner">
    <div class="container-fluid"> <a class="btn btn-navbar" data-toggle="collapse"
					data-target=".nav-collapse"> <span class="icon-bar"></span> <span
					class="icon-bar"></span> <span class="icon-bar"></span> </a> <a class="brand" href="#">童 年</a>
      <div class="nav-collapse collapse">
        <ul class="nav pull-right">
          <li class="dropdown"><a href="#" role="button"
							class="dropdown-toggle" data-toggle="dropdown"> <i
								class="icon-user"></i> ${userName} <i class="caret"></i> </a>
            <ul class="dropdown-menu">
              <li><a tabindex="-1" href="adminLogout.do"> 退出 </a></li>
            </ul>
          </li>
        </ul>
        <ul class="nav">
          <li class=""><a href="index.jsp">概况</a></li>
          <li class="active"><a href="queryVersion.do">版本管理</a></li>
          <li class=""><a href="getFeedback.do?webOrApp=web&moduleName=all&pageNum=1">客户服务</a></li>
          <li><a href="apidoc/apidoc.jsp">API文档</a></li>
        </ul>
      </div>
      <!--/.nav-collapse --> 
    </div>
  </div>
</div>
<div class="container-fluid">
  <div class="row-fluid">
    <div class="span3" id="sidebar">
      <ul class="nav nav-list bs-docs-sidenav nav-collapse collapse">
        <li class="active"><a href="queryVersion.do"><i class="icon-chevron-right"></i> 历史版本</a></li>
        <li><a href="upload.jsp"><i
							class="icon-chevron-right"></i> 版本上传</a></li>
      </ul>
    </div>
    <!--/span-->
    <div class="span9" id="content">
      <div class="row-fluid">
        <!-- block -->
        <div class="block">
          <div class="navbar navbar-inner block-header">
            <div class="muted pull-left">历史版本</div>
            <div class="muted pull-right"><a href="queryVersion.do"><button class="btn" style="margin:auto"><i class="icon-refresh"></i></button></a></div>
          </div>
          <div class="block-content collapse in">
			<div class="span12">
            	<table cellpadding="0" cellspacing="0" border="0" class="table table-striped table-bordered" id="example">
										<thead>
											<tr>
												<th><center>版本号</center></th>
												<th><center>最后更改日期</center></th>
												<th><center>安装包地址</center></th>
												<th><center>备注</center></th>
												<th><center>系统代码</center></th>
                                                <th><center>操作</center></th>
											</tr>
										</thead>
									<tbody>
										<s:iterator value="#session.versionBeans" var="versionBean">
											<tr>
												<td	class="center"><input id="ver_ipt_<s:property value="#versionBean.versionId" />" type="text" style="display:none" /><span id="ver_sp_<s:property value="#versionBean.versionId" />"><s:property
														value="#versionBean.version" /></span></td>
												<td class="center"><input id="mod_ipt_<s:property value="#versionBean.versionId" />" type="text" style="display:none" /><span id="ver_sp_<s:property value="#versionBean.versionId" />"><s:date name="#versionBean.modifyTime"
														format="yyyy-MM-dd hh:mm:ss" /></span></td>
												<td class="center"><input id="pku_ipt_<s:property value="#versionBean.versionId" />" type="text" style="display:none" /><span id="ver_sp_<s:property value="#versionBean.versionId" />"><s:property
														value="#versionBean.packageUrl" /></span></td>
												<td class="center"><input id="nte_ipt_<s:property value="#versionBean.versionId" />" type="text" style="display:none" /><span id="ver_sp_<s:property value="#versionBean.versionId" />"><s:property value="#versionBean.note" /></span></td>
												<td class="center"><input id="sys_ipt_<s:property value="#versionBean.versionId" />" type="text" style="display:none" /><span id="ver_sp_<s:property value="#versionBean.versionId" />"><s:property value="#versionBean.system" /></span></td>
												<td class="center">
                                                	<center>
                                                		<a href="#"><button class="btn" style="margin:auto;padding:auto;">修改</button></a>
                                                		<a href="#"><button class="btn" style="margin:auto;padding:auto;">删除</button></a>
                                                	</center>
                                                </td>
											</tr>
										</s:iterator>
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
</body>
</html>
