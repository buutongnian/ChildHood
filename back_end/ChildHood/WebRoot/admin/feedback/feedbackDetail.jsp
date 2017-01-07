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
  <link href="../css/upload.css" rel="stylesheet" media="screen" type="text/css" /><!-- HTML5 shim, for IE6-8 support of HTML5 elements -->
  <!--[if lt IE 9]>
            <script src="http://html5shim.googlecode.com/svn/trunk/html5.js"></script>
        <![endif]-->

  <script src="../vendors/jquery-1.9.1.min.js" type="text/javascript"></script>
  <script src="../vendors/jquery-ui-1.10.3.js" type="text/javascript"></script>
  <script src="../bootstrap/js/bootstrap.min.js" type="text/javascript"></script>
  <script src="../vendors/modernizr-2.6.2-respond-1.1.0.min.js" type="text/javascript"></script>

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

            <li class="active"><a href="getFeedback.do?webOrApp=web&moduleName=all&pageNum=1">客户服务</a></li>

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
                反馈内容
              </div>
            </div>
            <div class="block-content collapse in form-horizontal">
              <div class="span12">
                <h4><legend>反馈信息</legend></h4>

                <fieldset>
                  <div class="control-group">
                    <label class="control-label" for="userName"><strong>反馈用户</strong></label>

                    <div class="controls">
                      <input class="input-xlarge" id="userName" type="text" value="${feedback.userName}" readonly="readonly" style="height:auto;cursor:default;" />
                    </div>
                  </div>

                  <div class="control-group">
                    <label class="control-label" for="feedbackContent"><strong>反馈内容</strong></label>

                    <div class="controls">
                      <textarea id="feedbackContent" class="input-xxlarge focused" type="text" readonly="readonly" style="height:30%;cursor:default;">${feedback.feedbackContent}</textarea>
                    </div>
                  </div>

                  <div class="control-group">
                    <label class="control-label" for="feedbackModule"><strong>反馈模块</strong></label>

                    <div class="controls">
                      <input class="input-xlarge" id="feedbackModule" type="text" value="${feedback.feedbackModule}" readonly="readonly" style="height:auto;cursor:default;" />
                    </div>
                  </div>
                </fieldset>

                <h4><legend>反馈处理</legend></h4>

				<form action="dealFeedback.do?webOrApp=web" method="post">
					<input type="hidden" name="feedbackId" value="${feedback.feedbackId }">
               	 <div class="control-group">
                	  <label class="control-label" for="select01"><strong>处理意见</strong></label>
                	  <div class="controls">
                  	    <textarea name="result" id="result" class="input-xxlarge focused" type="text" style="height:30%;">${feedback.result }</textarea>
                	  </div>

                 	 <div class="form-actions">
                 	 <s:if test="#session.feedback.processible == 'Y'">
                  	  <button id="submit" type="submit" class="btn btn-primary">处理</button>
                  	 </s:if>
                  	 <s:if test="#session.feedback.processible == 'N'">
                  	  <button id="submit" type="submit" class="btn btn-primary">更改</button>
                  	 </s:if>
                 	  <button id="back" class="btn" onclick="javascript:history.back(-1);">返回</button>
                	 </div>
               	 </div>
                </form>
                
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
