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
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
  <meta charset="utf-8" />

  <title>童年-后台管理页面</title><!-- Bootstrap -->
  <link href="../bootstrap/css/bootstrap.min.css" rel="stylesheet" media="screen" type="text/css" />
  <link href="../bootstrap/css/bootstrap-responsive.min.css" rel="stylesheet" media="screen" type="text/css" />
  <link href="../assets/styles.css" rel="stylesheet" media="screen" type="text/css" />
  <link href="../css/table.css" rel="stylesheet" media="screen" type="text/css" />
  <link rel="stylesheet" href="../js/pagination/pagination.css" />
	
  <!-- HTML5 shim, for IE6-8 support of HTML5 elements -->
  <!--[if lt IE 9]>
            <script src="http://html5shim.googlecode.com/svn/trunk/html5.js"></script>
        <![endif]-->

  <script src="../vendors/jquery-1.9.1.min.js" type="text/javascript"></script>
  <script src="../vendors/jquery-ui-1.10.3.js" type="text/javascript"></script>
  <script src="../bootstrap/js/bootstrap.min.js" type="text/javascript"></script>
  <script src="../vendors/modernizr-2.6.2-respond-1.1.0.min.js" type="text/javascript"></script>
  <script type="text/javascript" src="../js/pagination/jquery-1.11.3.js"></script>
  <script type="text/javascript" src="../js/pagination/jquery.pagination.js"></script>

<script type="text/javascript">
	function handlePaginationClick(new_page_index, pagination_container) {
		var myForm = document.getElementById("pageForm");
		myForm.action = "getFeedback.do?webOrApp=web";
		myForm.method = "post";
		myForm.pageNum.value = new_page_index + 1;
		myForm.submit();
		return false;
	}

	$(function() {
		$("#Pagination").pagination(${feedbackPage.totalRecords}, {
			items_per_page : ${feedbackPage.pageSize}, // 每页显示多少条记录
			current_page : ${feedbackPage.currentPage} - 1, // 当前显示第几页数据
			num_display_entries : 20, // 分页显示的条目数
			next_text : "下一页",
			prev_text : "上一页",
			num_edge_entries : 1, // 连接分页主体，显示的条目数
			callback : handlePaginationClick
		});
  });

</script>

</head>

<body>
  <div class="navbar navbar-fixed-top">
    <div class="navbar-inner">
      <div class="container-fluid">
        <a class="btn btn-navbar" data-toggle="collapse" data-target=".nav-collapse"></a> <a class="brand" href="#">童 年</a>

        <div class="nav-collapse collapse">
         	<ul class="nav pull-right">
          		<li class="dropdown"><a href="#" role="button" class="dropdown-toggle" data-toggle="dropdown">
						<i class="icon-user"></i> ${userName} <i class="caret"></i></a>
            		<ul class="dropdown-menu">
              			<li><a tabindex="-1" href="adminLogout.do"> 退出 </a></li>
            		</ul>
          		</li>
        	</ul>
          <ul class="nav">
            <li class=""><a href="../index.jsp">概况</a></li>

            <li class=""><a href="queryVersion.do">版本管理</a></li>

            <li class="active"><a href="queryVersion.do">客户服务</a></li>

            <li><a href="../apidoc/apidoc.jsp">API文档</a></li>
          </ul>
        </div><!--/.nav-collapse -->
      </div>
    </div>
  </div>

  <div class="container-fluid">
    <div class="row-fluid">
      <div class="span3" id="sidebar">
        <ul class="nav nav-list bs-docs-sidenav nav-collapse collapse">
          <li class="active"><a href="getFeedback.do?webOrApp=web&moduleName=all&pageNum=1">用户反馈处理</a></li>
        </ul>
      </div><!--/span-->

      <div class="span9" id="content">
        <div class="row-fluid">
          <!-- block -->

          <div class="block">
            <div class="navbar navbar-inner block-header">
              <div class="muted pull-left">
                用户反馈处理
              </div>

              <div class="muted pull-right">
                <a href="getFeedback.do?webOrApp=web&pageNum=1&moduleName=all"><button class="btn" style="margin:auto"><i class="icon-refresh"></i></button></a>
              </div>
            </div>

            <div class="block-content collapse in">
              <div class="span12">
                <table cellpadding="0" cellspacing="0" border="0" class="ctltable table table-bordered" id="example">
                  <thead>
                    <tr>
                      <th><center>反馈用户</center></th>

                      <th><center>反馈日期</center></th>

                      <th><center>反馈模块</center></th>

                      <th><center>是否已处理</center></th>

                      <th><center>处理日期</center></th>

                      <th><center>处理人</center></th>

                      <th><center>操作</center></th>
                    </tr>
                  </thead>

                  <tbody>
                  <s:iterator value="#session.feedbackPage.dataList" var="feedback">
                    <tr>
                      <td><center><s:property value="#feedback[1]" /></center></td>

                      <td><center><s:date name="#feedback[2]" format="yyyy-MM-dd" /></center></td>

                      <td><center><s:property value="#feedback[3]" /></center></td>

                      <s:if test="#feedback[4]  == 'N'">
                      	<td><center>是</center></td>
                      	<td><center><s:date name="#feedback[5]" format="yyyy-MM-dd" /></center></td>
                      	<td><center><s:property value="#feedback[6]" /></center></td>
                      </s:if>
                      
                      <s:if test="#feedback[4]  == 'Y'">
                      	<td><center>否</center></td>
                      	<td><center>无</center></td>
                      	<td><center>无</center></td>
                      </s:if>
                      
                      <td><center>
                    	<a href="feedbackDetail.do?webOrApp=web&feedbackId=<s:property value="#feedback[0]" />"><button class="btn" style="margin:auto;padding:auto;">查看详情</button></a>
                      </center></td>
                    </tr>
                    </s:iterator>
                    <form id="pageForm">
						<input type="hidden" name="moduleName" value="all">
						<input type="hidden" name="pageNum">
					</form>
                  </tbody>
                </table>
                <s:if test="#session.feedbackPage.totalPage>1">
              	  <center><div id="Pagination" class="Pagination"></center></div>
                </s:if>
              </div>
              
            </div>
            
          </div><!-- /block -->
        </div>
      </div>
    </div>
    <hr />

    <center>
      <p>&copy; 童年 2016</p>
    </center>
  </div><!--/.fluid-container-->
</body>
</html>
