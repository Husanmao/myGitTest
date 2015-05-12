<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<%@taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE HTML>
<html>
<head>
<base href="<%=basePath%>">

<title>success.jsp</title>
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0,user-scalable=no">


</head>

<body
	style="color:#575757;
			margin-top:100px;margin-right:110px;margin-bottom:110px;margin-left:110px;
			background-repeat:repeat-x;background-color:white;">

	<div
		style="color:#2778ec;
			font-size:38pt;font-weight:300;margin-bottom:20px;vertical-align:bottom;position:relative;">操作成功</div>
</body>
</html>
