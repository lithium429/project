<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.xz.base.utils.WebUtil"%>
<%
	String baseUrl = WebUtil.getBasePath(request);
	request.setAttribute("baseUrl", baseUrl);
%>
<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>登录超时</title>
		<base href="${baseUrl}" />
		<link rel="stylesheet" type="text/css" href="static/css/manage.css" />
		<link rel="stylesheet" type="text/css" href="static/js/artDialog/skins/blue.css" />
	
		<script type="text/javascript" src="static/js/jquery-1.7.2.min.js"></script>
		<script type="text/javascript" src="static/js/artDialog/jquery.artDialog.js"></script>
		<script type="text/javascript" src="static/js/artDialog/plugins/iframeTools.js"></script>
		<script type="text/javascript" src="static/js/base.js"></script> 
	</head>
	<body>
		<script type="text/javascript">
			$(function(){
				 showDialog({
			        icon: 'error',
			        content: '登录超时，请重新登录！', 
			        button: [{
			            name: '确定',
			            focus: true,
			            callback: function () {
			            	window.top.location.href = '${baseUrl}admin/login.do';
			            }
			        }],
			        close: function(){
			        	window.top.location.href = '${baseUrl}admin/login.do';
			        }
			    }); 	
			});
		</script>
	</body>
</html>