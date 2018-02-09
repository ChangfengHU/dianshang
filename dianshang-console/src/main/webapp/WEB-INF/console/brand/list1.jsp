<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>

<!-- 在登录页面，如果用户已登录，跳转到首页 -->



 <!-- 判断当前是星期几 -->
<c:set var="xingqi" value="3" />
<c:choose>
	<c:when test="${ xingqi eq 1 }">星期一</c:when>
	<c:when test="${ xingqi eq 2 }">星期二</c:when>
	<c:when test="${ xingqi eq 3 }">星期三</c:when>
	<c:when test="${ xingqi eq 4 }">星期四</c:when>
	<c:when test="${ xingqi eq 5 }">星期五</c:when>
	<c:when test="${ xingqi eq 6 }">星期六</c:when>
	<c:otherwise>星期日</c:otherwise>
</c:choose>




<!-- 其他标签介绍 -->
<c:forTokens items="1,2,3" delims="," var="i">
${i } 
</c:forTokens>

</body>
</html>