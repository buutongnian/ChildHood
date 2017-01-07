<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
	<link type="text/css" rel="stylesheet" href="css/404.css" />
	<script type="text/javascript" src="js/404.js"></script>
</head>
<div class="content">
  <canvas id="snow" class="snow"></canvas>
  <div class="main-text">
    <h1>糟 糕 ！<br/>页 面 走 丢 了 ！</h1><a href="index.jsp" class="home-link">点击返回主页.</a>
  </div>
  <div class="ground">
    <div class="mound"> 
      <div class="mound_text">404</div>
      <div class="mound_spade"></div>
    </div>
  </div>
</div>