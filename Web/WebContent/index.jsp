<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'index.jsp' starting page</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<style type="text/css">
		.menu{
			color: blue;
			font-weight: bold;
			font-size: 22px;
			text-decoration:none;
		}
		.filetype{
			color: green;
			font-weight: bold;
			font-size: 22px;
			text-decoration:none;
		}
		.dirtype{
			color: red;
			font-weight: bold;
			font-size: 22px;
			text-decoration:none;
		}
		.lastTime{
			color: gray;
			font-weight: bold;
			font-size: 22px;
		}
		.size{
			color: gray;
			font-weight: bold;
			font-size: 22px;
		}
		.table1{
			width: 100%;
			border-collapse:collapse;
		}
		.td_center{
			text-align: center;
		}
		th{
			color: white;
			font-weight: bold;
			font-size: 22px;
			background-color: green;
		}
	</style>
  </head>
  <%request.setCharacterEncoding("utf-8"); %>
  <body>
  	<a href="test" class="menu">返回到最起点目录</a>
    <br>
    <c:forEach items="${array}" var="f">
    	${f }
    </c:forEach>
  </body>
</html>
