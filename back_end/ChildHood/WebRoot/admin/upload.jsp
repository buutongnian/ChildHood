<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%
	if(session.getAttribute("userName")==null){
		response.sendRedirect(request.getContextPath()+"/admin/login.jsp");
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
<script src="js/upload.js"></script>

<script type="text/javascript">
	<%
		if(session.getAttribute("updateSuccess") != null){
			boolean success = (boolean)session.getAttribute("updateSuccess");
			session.removeAttribute("updateSuccess");
			if(success){
	%>
	$(function(){
		alert_show();
	});
	<%
			}
			else{
	%>
	$(function(){
		setAlert('失败','版本提交失败，同名文件或已存在！');
		alert_show();
	});
	<%
			}
		}
		%>
</script>

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
        <li><a href="queryVersion.do"><i class="icon-chevron-right"></i> 历史版本</a></li>
        <li class="active"><a href="upload.jsp"><i
							class="icon-chevron-right"></i> 版本上传</a></li>
      </ul>
    </div>
    <!--/span-->
    <div class="span9" id="content">
      <div class="row-fluid">
        <!-- block -->
        <div class="block">
          <div class="navbar navbar-inner block-header">
            <div class="muted pull-left">版本上传</div>
          </div>
          <div style="padding-top:10px;padding-left:16px;padding-right:16px;">
            <div id="success-alert" class="alert alert-success" style="display:none">
              <button type="button" class="close" onClick="alert_hide()">&times;</button>
              <h4>成功</h4>
              <span>版本提交成功！</span> </div>
          </div>
          
          <div class="block-content collapse in form-horizontal">
            <div class="span12">
              <legend>
              <h4>版本信息</h4>
              </legend>
              <fieldset>
                <div class="control-group">
                  <label class="control-label" for="versionInput"><strong>版本号</strong></label>
                  <div class="controls">
                    <input class="input-xlarge" id="versionInput" type="text" placeholder="版本号" style="height:auto">
                  </div>
                </div>
                <div class="control-group">
                  <label class="control-label"><strong>备注</strong></label>
                  <div class="controls">
                    <textarea id="noteInput" class="input-xlarge focused" type="text" placeholder="备注信息" ></textarea>
                  </div>
                </div>
                <div class="control-group">
                  <label class="control-label" for="select01"><strong>适用系统</strong></label>
                  <div class="controls">
                    <select id="select01" class="chzn-select">
                      <option value="Android">Android</option>
                      <option value="IOS">IOS</option>
                    </select>
                  </div>
                </div>
              </fieldset>
              <legend>
              <h4>文件上传</h4>
              </legend>
              <div class="control-group">
                <div class="uploader white controls">
                  <input type="text" class="filename" readonly/>
                  <input type="button" class="button" value="浏览..."/>
                  <form id="uploadForm" action="addVersion.do" enctype="multipart/form-data" method="post">
          			<input id="version" name="version" type="hidden" />
                	<input id="note" name="note" type="hidden" />
               	 	<input id="system" name="system" type="hidden" />
                  	<input id="uploadPkg" name="uploadPkg" type="file" size="30"/>
	          	  </form>
                </div>
                <div class="form-actions">
                  <button id="submit"  class="btn btn-primary" onClick="submit()">上传</button>
                  <button id="reset"  class="btn" onClick="reset_input()">清空</button>
                </div>
              </div>
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

<script>
		$(function(){
			$("input[type=file]").change(function(){
				if($(this).val()!=""){
					$(this).parents(".uploader").find(".filename").val($(this).val());
				}else{
					$(this).parents(".uploader").find(".filename").val("没有选中文件...");
				}
				}
				);
			$("input[type=file]").each(function(){
			if($(this).val()==""){$(this).parents(".uploader").find(".filename").val("没有选中文件...");}
			});
		});
	</script>
</body>
</html>
