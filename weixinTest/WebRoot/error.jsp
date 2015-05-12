<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<%@taglib uri="/struts-tags" prefix="s"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE HTML>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<base href="<%=basePath%>">

<title>error</title>
<meta http-equiv="keywords" content="">
<meta http-equiv="description" content="">
<meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0,user-scalable=no">


<style type="text/css">

p{font-size:15px;line-height:20px;height:20px;margin:10px 0px 10px 0px;}

</style>


</head>

<body>
	<%-- <header><%@ include file="header.jsp"%></header> --%>
	<div class="column">
		<div style="font-weight:1000px;margin:0px 0px 0px 30px;">
			<p style="font-weight:300px;margin:50px 0px 50px 200px;font-size:50px;height:50px;line-height:50px;color:#BC0A00;">
			error
			</p>
			<p style="margin:0px 0px 0px 100px;">点击 <a class="aShow" href="index">这里</a>&nbsp;返回首页</p>
		</div>
	</div>
	<%-- <footer><%@ include file="footer.jsp"%></footer> --%>
</body>
</html>
