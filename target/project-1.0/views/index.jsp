<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="com.xz.base.utils.WebUtil" %>
<%
	String baseUrl = WebUtil.getBasePath(request);
	request.setAttribute("baseUrl", baseUrl);
%>
<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" /> 
		<title>Insert title here</title>
		<base href="${baseUrl}" />
	</head>
	<body>
		<script type="text/javascript">
			window.location.href= '${baseUrl}admin/login.do';
		</script>
	</body>
</html>